package tech.carcadex.kotlinbukkitkit.menu.slot

import tech.carcadex.kotlinbukkitkit.menu.Menu
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayer
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayerInteract
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayerInventory
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayerMove
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.WeakHashMap

public val MenuPlayerSlot.rawSlotPos: Int get() = slotPos - 1

public interface MenuPlayerSlot : MenuPlayer {
    public val slotPos: Int
    public val slot: Slot

    public fun putPlayerSlotData(key: String, value: Any) {
        slot.playerSlotData.getOrPut(player) {
            WeakHashMap()
        }[key] = value
    }

    public fun getPlayerSlotData(key: String): Any? = slot.playerSlotData.get(player)?.get(key)
}

public interface MenuPlayerInventorySlot : MenuPlayerSlot, MenuPlayerInventory {

    public var showingItem: ItemStack?
        get() = getItem(slotPos)?.takeUnless { it.type == Material.AIR }
        set(value) = setItem(slotPos, value)

    public fun updateSlotToPlayer() {
        menu.updateSlot(slot, player)
    }

    public fun updateSlot() {
        menu.updateSlot(slot)
    }
}

public open class MenuPlayerSlotInteract(
    menu: Menu<*>,
    override val slotPos: Int,
    override val slot: Slot,
    player: Player,
    inventory: Inventory,
    canceled: Boolean,
    public val click: ClickType,
    public val action: InventoryAction,
    public val clicked: ItemStack?,
    public val cursor: ItemStack?,
    public val hotbarKey: Int,
) : MenuPlayerInteract(menu, player, inventory, canceled), MenuPlayerInventorySlot

public class MenuPlayerSlotMoveTo(
    override val menu: Menu<*>,
    override val slotPos: Int,
    override val slot: Slot,
    override val player: Player,
    override val inventory: Inventory,
    override var canceled: Boolean,
    override val toMoveSlot: Int,
    override var toMoveItem: ItemStack?,
) : MenuPlayerInventorySlot, MenuPlayerMove

public class MenuPlayerSlotRender(
    override val menu: Menu<*>,
    override val slotPos: Int,
    override val slot: Slot,
    override val player: Player,
    override val inventory: Inventory,
) : MenuPlayerInventorySlot

public class MenuPlayerSlotUpdate(
    override val menu: Menu<*>,
    override val slotPos: Int,
    override val slot: Slot,
    override val player: Player,
    override val inventory: Inventory,
) : MenuPlayerInventorySlot
