package tech.carcadex.kotlinbukkitkit.menu.dsl

import tech.carcadex.kotlinbukkitkit.menu.Menu
import tech.carcadex.kotlinbukkitkit.menu.calculateSlot
import tech.carcadex.kotlinbukkitkit.menu.dsl.slot.SlotDSL
import org.bukkit.inventory.ItemStack

public inline fun MenuDSL.slot(
    line: Int,
    slot: Int,
    item: ItemStack?,
    block: SlotDSL.() -> Unit = {},
): SlotDSL = slot(calculateSlot(line, slot), item, block)

public inline fun MenuDSL.slot(
    slot: Int,
    item: ItemStack?,
    block: SlotDSL.() -> Unit = {},
): SlotDSL = baseSlot.clone(item).apply(block).also {
    setSlot(slot, it)
}

public interface MenuDSL : Menu<SlotDSL> {

    override val eventHandler: MenuEventHandlerDSL

    public fun onUpdate(update: MenuPlayerUpdateEvent) {
        eventHandler.updateCallbacks.add(update)
    }

    public fun onClose(close: MenuPlayerCloseEvent) {
        eventHandler.closeCallbacks.add(close)
    }

    public fun onMoveToMenu(moveToMenu: MenuPlayerMoveToEvent) {
        eventHandler.moveToMenuCallbacks.add(moveToMenu)
    }

    public fun preOpen(preOpen: MenuPlayerPreOpenEvent) {
        eventHandler.preOpenCallbacks.add(preOpen)
    }

    public fun onOpen(open: MenuPlayerOpenEvent) {
        eventHandler.openCallbacks.add(open)
    }
}
