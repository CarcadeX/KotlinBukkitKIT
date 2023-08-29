package tech.carcadex.kotlinbukkitkit.commands.reflection.annotations

@Retention(AnnotationRetention.RUNTIME)
annotation class TabComplete(val complete: Array<String>)
