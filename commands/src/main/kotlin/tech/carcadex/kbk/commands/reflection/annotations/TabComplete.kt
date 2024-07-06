package tech.carcadex.kbk.commands.reflection.annotations

@Retention(AnnotationRetention.RUNTIME)
annotation class TabComplete(val complete: Array<String>)
