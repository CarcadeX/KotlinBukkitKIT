/*
ORIGINAL PACKAGE: package br.com.devsrsouza.kotlinbukkitapi.extensions
ORIGINAL REPOSITORY: https://github.com/DevSrSouza/KotlinBukkitAPI
AUTHOR: https://github.com/DevSrSouza

Thanks DevSrSouza for KotlinBukkitAPI
 */

package tech.carcadex.kbk.extensions

import org.bukkit.permissions.Permissible

public fun Permissible.anyPermission(vararg permissions: String): Boolean = permissions.any { hasPermission(it) }

public fun Permissible.allPermission(vararg permissions: String): Boolean = permissions.all { hasPermission(it) }

public fun Permissible.hasPermissionOrStar(permission: String): Boolean = hasPermission(permission) || hasPermission(permission.replaceAfterLast('.', "*"))
