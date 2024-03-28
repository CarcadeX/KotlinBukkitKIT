package tech.carcadex.kotlinbukkitkit.menu.dsl.pagination

import tech.carcadex.kotlinbukkitkit.menu.MenuPlayer

public typealias MenuPlayerPageChangeEvent = MenuPlayer.() -> Unit
public typealias MenuPlayerPageAvailableEvent = MenuPlayer.() -> Unit

public class PaginationEventHandler {
    internal val pageChangeCallbacks: MutableList<MenuPlayerPageChangeEvent> = mutableListOf()
    internal val pageAvailableCallbacks: MutableList<MenuPlayerPageAvailableEvent> = mutableListOf()

    public fun pageChange(pageChange: MenuPlayer) {
        for (callback in pageChangeCallbacks) {
            callback(pageChange)
        }
    }

    public fun pageAvailable(pageAvailable: MenuPlayer) {
        for (callback in pageAvailableCallbacks) {
            callback(pageAvailable)
        }
    }
}
