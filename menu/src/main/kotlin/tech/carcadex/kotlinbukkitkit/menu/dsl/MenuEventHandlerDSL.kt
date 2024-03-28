package tech.carcadex.kotlinbukkitkit.menu.dsl

import tech.carcadex.kotlinbukkitkit.menu.MenuEventHandler
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayerClose
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayerMoveTo
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayerOpen
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayerPreOpen
import tech.carcadex.kotlinbukkitkit.menu.MenuPlayerUpdate

public typealias MenuPlayerUpdateEvent = MenuPlayerUpdate.() -> Unit
public typealias MenuPlayerCloseEvent = MenuPlayerClose.() -> Unit
public typealias MenuPlayerMoveToEvent = MenuPlayerMoveTo.() -> Unit

public typealias MenuPlayerPreOpenEvent = MenuPlayerPreOpen.() -> Unit
public typealias MenuPlayerOpenEvent = MenuPlayerOpen.() -> Unit

public class MenuEventHandlerDSL(public val menu: MenuDSL) : MenuEventHandler {

    internal val updateCallbacks: MutableList<MenuPlayerUpdateEvent> = mutableListOf()
    internal val closeCallbacks: MutableList<MenuPlayerCloseEvent> = mutableListOf()
    internal val moveToMenuCallbacks: MutableList<MenuPlayerMoveToEvent> = mutableListOf()
    internal val preOpenCallbacks: MutableList<MenuPlayerPreOpenEvent> = mutableListOf()
    internal val openCallbacks: MutableList<MenuPlayerOpenEvent> = mutableListOf()

    override fun update(update: MenuPlayerUpdate) {
        for (callback in updateCallbacks) {
            callback(update)
        }
    }

    override fun close(close: MenuPlayerClose) {
        for (callback in closeCallbacks) {
            callback(close)
        }
    }

    override fun moveToMenu(moveToMenu: MenuPlayerMoveTo) {
        for (callback in moveToMenuCallbacks) {
            callback(moveToMenu)
        }
    }

    override fun preOpen(preOpen: MenuPlayerPreOpen) {
        for (callback in preOpenCallbacks) {
            callback(preOpen)
        }
    }

    override fun open(open: MenuPlayerOpen) {
        for (callback in openCallbacks) {
            callback(open)
        }
    }
}
