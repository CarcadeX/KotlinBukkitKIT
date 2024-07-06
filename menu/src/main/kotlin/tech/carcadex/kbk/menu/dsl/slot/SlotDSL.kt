package tech.carcadex.kbk.menu.dsl.slot

import tech.carcadex.kbk.menu.slot.Slot
import org.bukkit.inventory.ItemStack

public interface SlotDSL : Slot {

    override val eventHandler: SlotEventHandlerDSL

    public fun onClick(click: MenuPlayerSlotInteractEvent) {
        eventHandler.interactCallbacks.add(click)
    }

    public fun onRender(render: MenuPlayerSlotRenderEvent) {
        eventHandler.renderCallbacks.add(render)
    }

    public fun onUpdate(update: MenuPlayerSlotUpdateEvent) {
        eventHandler.updateCallbacks.add(update)
    }

    public fun onMoveToSlot(moveToSlot: MenuPlayerSlotMoveToEvent) {
        eventHandler.moveToSlotCallbacks.add(moveToSlot)
    }

    override fun clone(item: ItemStack?): SlotDSL
}
