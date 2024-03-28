package tech.carcadex.kotlinbukkitkit.menu.dsl.slot

import tech.carcadex.kotlinbukkitkit.menu.slot.MenuPlayerSlotInteract
import tech.carcadex.kotlinbukkitkit.menu.slot.MenuPlayerSlotMoveTo
import tech.carcadex.kotlinbukkitkit.menu.slot.MenuPlayerSlotRender
import tech.carcadex.kotlinbukkitkit.menu.slot.MenuPlayerSlotUpdate
import tech.carcadex.kotlinbukkitkit.menu.slot.SlotEventHandler

public typealias MenuPlayerSlotInteractEvent = MenuPlayerSlotInteract.() -> Unit
public typealias MenuPlayerSlotRenderEvent = MenuPlayerSlotRender.() -> Unit
public typealias MenuPlayerSlotUpdateEvent = MenuPlayerSlotUpdate.() -> Unit
public typealias MenuPlayerSlotMoveToEvent = MenuPlayerSlotMoveTo.() -> Unit

public class SlotEventHandlerDSL : SlotEventHandler {

    internal val interactCallbacks = mutableListOf<MenuPlayerSlotInteractEvent>()
    internal val renderCallbacks = mutableListOf<MenuPlayerSlotRenderEvent>()
    internal val updateCallbacks = mutableListOf<MenuPlayerSlotUpdateEvent>()
    internal val moveToSlotCallbacks = mutableListOf<MenuPlayerSlotMoveToEvent>()

    override fun interact(interact: MenuPlayerSlotInteract) {
        for (callback in interactCallbacks) {
            callback(interact)
        }
    }

    override fun render(render: MenuPlayerSlotRender) {
        for (callback in renderCallbacks) {
            callback(render)
        }
    }

    override fun update(update: MenuPlayerSlotUpdate) {
        for (callback in updateCallbacks) {
            callback(update)
        }
    }

    override fun moveToSlot(moveToSlot: MenuPlayerSlotMoveTo) {
        for (callback in moveToSlotCallbacks) {
            callback(moveToSlot)
        }
    }

    override fun clone(): SlotEventHandlerDSL {
        return SlotEventHandlerDSL().also {
            it.interactCallbacks.addAll(interactCallbacks)
            it.renderCallbacks.addAll(renderCallbacks)
            it.updateCallbacks.addAll(updateCallbacks)
            it.moveToSlotCallbacks.addAll(moveToSlotCallbacks)
        }
    }
}
