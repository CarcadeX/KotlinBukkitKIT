package tech.carcadex.kotlinbukkitkit.commands.annotations

@Retention(AnnotationRetention.RUNTIME)
annotation class TabComplete(val complete: Array<String>)
