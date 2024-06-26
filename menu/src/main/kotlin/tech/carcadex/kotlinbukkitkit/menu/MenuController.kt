package tech.carcadex.kotlinbukkitkit.menu

import tech.carcadex.kotlinbukkitkit.architecture.PluginDisableAware
import tech.carcadex.kotlinbukkitkit.architecture.PluginDisableAwareController
import tech.carcadex.kotlinbukkitkit.extensions.KListener
import tech.carcadex.kotlinbukkitkit.extensions.onlinePlayers
import tech.carcadex.kotlinbukkitkit.extensions.registerEvents
import tech.carcadex.kotlinbukkitkit.menu.slot.MenuPlayerSlotInteract
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerPickupItemEvent
import org.bukkit.plugin.Plugin

internal object MenuControllerPlugins : PluginDisableAwareController<MenuController>() {
    override val factory: (plugin: Plugin) -> MenuController = { MenuController(it) }
}

internal class MenuController(
    override val plugin: Plugin,
) : KListener<Plugin>, PluginDisableAware {

    init {
        registerEvents(plugin)
    }

    override fun onDisable() {
        onlinePlayers.forEach {
            val menu = getMenuFromPlayer(it)
                ?.takeIf { it.plugin.name == plugin.name }
            menu?.close(it, true)
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun clickInventoryEvent(event: InventoryClickEvent) {
        if (event.view.type == InventoryType.CHEST) {
            val player = event.whoClicked as Player
            val inv = event.inventory

            val menu = getMenuFromInventory(event.inventory)?.takeIfHasPlayer(player)

            if (menu != null) {
                if (menu.plugin.name != plugin.name) return

                if (event.rawSlot == event.slot) {
                    val clickedSlot = event.slot + 1
                    val slot = menu.slotOrBaseSlot(clickedSlot)

                    val interact = MenuPlayerSlotInteract(
                        menu, clickedSlot, slot,
                        player, inv, slot.cancel,
                        event.click, event.action,
                        event.currentItem, event.cursor,
                        event.hotbarButton,
                    )

                    slot.eventHandler.interact(interact)

                    if (interact.canceled) event.isCancelled = true
                } else {
                    // TODO move item
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun clickInteractEvent(event: InventoryDragEvent) {
        if (event.view.type == InventoryType.CHEST) {
            val player = event.whoClicked as Player
            val menu = getMenuFromInventory(event.inventory)?.takeIfHasPlayer(player)
            if (menu != null) {
                if (menu.plugin.name != plugin.name) return
                val pass = event.inventorySlots.firstOrNull { it in event.rawSlots }
                if (pass != null) event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        if (event.view.type == InventoryType.CHEST) {
            val player = event.player as Player
            val menu = getMenuFromPlayer(player)
            if (menu != null) {
                if (menu.plugin.name != plugin.name) return
                menu.close(player, false)
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onPickupItemEvent(event: PlayerPickupItemEvent) {
        val menu = getMenuFromPlayer(event.player)
        if (menu != null) {
            if (menu.plugin.name != plugin.name) return
            event.isCancelled = true
        }
    }
}
