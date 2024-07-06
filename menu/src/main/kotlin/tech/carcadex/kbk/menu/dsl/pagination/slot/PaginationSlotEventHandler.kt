package tech.carcadex.kbk.menu.dsl.pagination.slot

import tech.carcadex.kbk.menu.Menu
import tech.carcadex.kbk.menu.slot.MenuPlayerInventorySlot
import tech.carcadex.kbk.menu.slot.MenuPlayerSlotInteract
import tech.carcadex.kbk.menu.slot.MenuPlayerSlotRender
import tech.carcadex.kbk.menu.slot.MenuPlayerSlotUpdate
import tech.carcadex.kbk.menu.slot.Slot
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

public typealias MenuPlayerSlotPageChangeEvent<T> = MenuPlayerSlotPageChange.(T?) -> Unit
public typealias MenuPlayerPageSlotInteractEvent<T> = MenuPlayerSlotInteract.(T?) -> Unit
public typealias MenuPlayerPageSlotRenderEvent<T> = MenuPlayerSlotRender.(T?) -> Unit
public typealias MenuPlayerPageSlotUpdateEvent<T> = MenuPlayerSlotUpdate.(T?) -> Unit

public class PaginationSlotEventHandler<T> {
    internal val pageChangeCallbacks = mutableListOf<MenuPlayerSlotPageChangeEvent<T>>()
    internal val interactCallbacks = mutableListOf<MenuPlayerPageSlotInteractEvent<T>>()
    internal val renderCallbacks = mutableListOf<MenuPlayerPageSlotRenderEvent<T>>()
    internal val updateCallbacks = mutableListOf<MenuPlayerPageSlotUpdateEvent<T>>()

    public fun handlePageChange(currentItem: T?, pageChange: MenuPlayerSlotPageChange) {
        for (callback in pageChangeCallbacks) {
            callback(pageChange, currentItem)
        }
    }

    public fun handleRender(currentItem: T?, render: MenuPlayerSlotRender) {
        for (callback in renderCallbacks) {
            callback(render, currentItem)
        }
    }

    public fun handleUpdate(currentItem: T?, update: MenuPlayerSlotUpdate) {
        for (callback in updateCallbacks) {
            callback(update, currentItem)
        }
    }

    public fun handleInteract(currentItem: T?, interact: MenuPlayerSlotInteract) {
        for (callback in interactCallbacks) {
            callback(interact, currentItem)
        }
    }
}

public class MenuPlayerSlotPageChange(
    override val menu: Menu<*>,
    override val slotPos: Int,
    override val slot: Slot,
    override val player: Player,
    override val inventory: Inventory,
) : MenuPlayerInventorySlot
