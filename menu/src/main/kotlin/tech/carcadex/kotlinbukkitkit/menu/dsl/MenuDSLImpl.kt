package tech.carcadex.kotlinbukkitkit.menu.dsl

import tech.carcadex.kotlinbukkitkit.architecture.extensions.WithPlugin
import tech.carcadex.kotlinbukkitkit.extensions.task
import tech.carcadex.kotlinbukkitkit.menu.MenuControllerPlugins
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayerClose
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayerOpen
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayerPreOpen
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayerUpdate
import tech.carcadex.kotlinbukkitkit.menu.dsl.slot.SlotDSL
import tech.carcadex.kotlinbukkitkit.menu.dsl.slot.SlotDSLImpl
import tech.carcadex.kotlinbukkitkit.menu.dsl.slot.SlotEventHandlerDSL
import tech.carcadex.kotlinbukkitkit.menu.rangeOfSlots
import tech.carcadex.kotlinbukkitkit.menu.slot.MenuPlayerSlotRender
import tech.carcadex.kotlinbukkitkit.menu.slot.MenuPlayerSlotUpdate
import tech.carcadex.kotlinbukkitkit.menu.slot.Slot
import tech.carcadex.kotlinbukkitkit.menu.slotOrBaseSlot
import tech.carcadex.kotlinbukkitkit.menu.viewersFromPlayers
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import java.util.*

public inline fun WithPlugin<*>.menu(
    displayName: String,
    lines: Int,
    cancelOnClick: Boolean = true,
    block: MenuDSL.() -> Unit,
): MenuDSL = plugin.menu(displayName, lines, cancelOnClick, block)

public inline fun Plugin.menu(
    displayName: String,
    lines: Int,
    cancelOnClick: Boolean = true,
    block: MenuDSL.() -> Unit,
): MenuDSL = menu(displayName, lines, this, cancelOnClick, block)

public inline fun menu(
    displayName: String,
    lines: Int,
    plugin: Plugin,
    cancelOnClick: Boolean = true,
    block: MenuDSL.() -> Unit,
): MenuDSL = MenuDSLImpl(plugin, displayName, lines, cancelOnClick).apply(block)

public class MenuDSLImpl(
    public override val plugin: Plugin,
    override var title: String,
    override var lines: Int,
    override var cancelOnClick: Boolean,
) : MenuDSL {

    init {
        MenuControllerPlugins.ensureInitialized(plugin)
    }

    private var task: BukkitTask? = null
    override var updateDelay: Long = 0
        set(value) {
            field = value
            removeTask()
            if (value > 0 && viewers.isNotEmpty()) {
                setNewTask()
            }
        }

    override val viewers: MutableMap<Player, Inventory> = WeakHashMap()
    override val slots: TreeMap<Int, SlotDSL> = TreeMap()

    override val data: WeakHashMap<String, Any> = WeakHashMap<String, Any>()
    override val playerData: WeakHashMap<Player, WeakHashMap<String, Any>> = WeakHashMap<Player, WeakHashMap<String, Any>>()

    override val eventHandler: MenuEventHandlerDSL = MenuEventHandlerDSL(this)

    override var baseSlot: SlotDSL = SlotDSLImpl(null, cancelOnClick, SlotEventHandlerDSL())

    override fun setSlot(slot: Int, slotObj: SlotDSL) {
        slots.put(slot, slotObj)
    }

    override fun update(players: Set<Player>) {
        val viewers = viewersFromPlayers(players)
        for ((player, inventory) in viewers) {
            val update = MenuPlayerUpdate(this, player, inventory, title)
            eventHandler.update(update)

            // TODO title
            /*if(update.title != title)*/

            for (i in rangeOfSlots()) {
                val slot = slotOrBaseSlot(i)
                updateSlotOnlyPos(i, slot, player, inventory)
            }
        }
    }

    override fun updateSlot(slot: Slot, players: Set<Player>) {
        val slots: Map<Int, SlotDSL> = if (slot === baseSlot) {
            rangeOfSlots().mapNotNull { if (slots[it] == null) it to slot else null }.toMap()
        } else {
            rangeOfSlots().mapNotNull { if (slot === slots[it]) it to slot else null }.toMap()
        }

        for ((player, inventory) in viewersFromPlayers(players)) {
            for ((pos, s) in slots) {
                updateSlotOnlyPos(pos, s, player, inventory)
            }
        }
    }

    private fun updateSlotOnlyPos(pos: Int, slot: Slot, player: Player, inventory: Inventory) {
        val slotUpdate = MenuPlayerSlotUpdate(this, pos, slot, player, inventory)
        slot.eventHandler.update(slotUpdate)
    }

    override fun openToPlayer(vararg players: Player) {
        for (player in players) {
            close(player, false)

            try {
                val inv = inventory

                viewers.put(player, inv)

                val preOpen = MenuPlayerPreOpen(this, player)
                eventHandler.preOpen(preOpen)

                if (preOpen.canceled) return

                for (i in rangeOfSlots()) {
                    val slot = slotOrBaseSlot(i)

                    val render = MenuPlayerSlotRender(this, i, slot, player, inv)

                    slot.eventHandler.render(render)
                }

                player.openInventory(inv)

                val open = MenuPlayerOpen(this, player, inventory)
                eventHandler.open(open)

                if (task == null && updateDelay > 0 && viewers.isNotEmpty()) {
                    setNewTask()
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                removePlayer(player, true)
            }
        }
    }

    override fun getInventory(): Inventory {
        val slots = rangeOfSlots()
        val inventory = Bukkit.createInventory(this, slots.endInclusive, title)

        for (i in slots) {
            val slot = slotOrBaseSlot(i)

            val item = slot.item?.clone()
            inventory.setItem(i - 1, item)
        }

        return inventory
    }

    private fun removePlayer(player: Player, closeInventory: Boolean): Boolean {
        if (closeInventory) player.closeInventory()

        val viewing = viewers.remove(player) != null
        if (viewing) {
            clearPlayerData(player)
        }

        return viewing
    }

    override fun close(player: Player, closeInventory: Boolean) {
        if (removePlayer(player, closeInventory)) {
            val menuClose = MenuPlayerClose(this, player)
            eventHandler.close(menuClose)

            if (task != null && updateDelay > 0 && viewers.isEmpty()) {
                removeTask()
            }
        }
    }

    private fun setNewTask() {
        task = task(repeatDelay = updateDelay) { update() }
    }

    private fun removeTask() {
        task?.cancel()
        task = null
    }
}
