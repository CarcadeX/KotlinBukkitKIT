# KotlinBukkitKIT
KotlinBukkitKit is an API for Bukkit/SpigotAPI using features Kotlin. 
Thanks for DevSrSouza / KotlinBukkitAPI.


```kotlin
implementation("tech.carcadex:kotlinbukkitkit-architecture:1.0.0.3")
implementation("tech.carcadex:kotlinbukkitkit-extensions:1.0.0.3")
implementation("tech.carcadex:kotlinbukkitkit-serialization:1.0.0.3")
implementation("tech.carcadex:kotlinbukkitkit-messages:1.0.0.3")
implementation("tech.carcadex:kotlinbukkitkit-commands:1.0.0.3")
implementation("tech.carcadex:kotlinbukkitkit-utility:1.0.0.3")
implementation("tech.carcadex:kotlinbukkitkit-exposed:1.0.0.3")

compileOnly("tech.carcadex:kotlinbukkitkit-genref:1.0.0.3")
ksp("tech.carcadex:kotlinbukkitkit-genref:1.0.0.3")
```


Documentation will be there: https://red-tea.gitbook.io/kotlinbukkitkit/


<img src="https://github.com/DevSrSouza/KotlinBukkitAPI/raw/master/icon.png?size=96" alt="KotlinBukkitAPI" title="KotlinBukkitAPI" align="right"/>

## KotlinBukkitKIT

![Kotlin version](https://img.shields.io/static/v1?label=Kotlin&message=1.9.23&color=Orange&style=for-the-badge)
[![GitHub stars](https://img.shields.io/github/stars/CarcadeX/KotlinBukkitKit.svg?style=for-the-badge&color=orange&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAADYklEQVR4XuWbvbPNQBiHn9uo6NFT0GEYHa0/QofOR2MojI8xxmh8VFwVf4RaxTB0KOhdPRUF8zPJmdy1J3l3N9ndxDb3zr0n2exzfu/zJjk5G5Qdh5vp35c6jI1SEzfzbgK/gXOljqMkgF3A1wbAXuBHCQglAehdf9wsWr8rDdlHSQCq+0MdBxzJvnqgFADJ752zYAHILsNSABT3Mw4A/S27DEsAaOW30wHwHcguwxIAuvJzyz67DEsA6MrPBaD/ZZVhbgA++bkQssowNwCf/FwAWWWYE8A6+bkAssowJ4A++RWTYU4AffIrJsNcACzyKyLDXAAs8isiwxwArPIrIsMcAELkl12GOQCEyC+7DAUgpj5LXLpPMeemAOwAXgLHp5ih4n2+Ak62JbCnuRmhn//D2ALUmre6DlAClAQlYsnjJ3ACeK1FuhI8CzxZ8uqbu06rG7C+LrBkKf5zpekDsFQp/pUeoBJYjXXnAUuT4kp6bnn3nQgtRYrbpBcCQK9dghR7b7RaToXnLMXB22sWAHOVold6oSXQvn5uUlwrvVgA2m4uUuyVXgqAuUgx6NMliwN8t6rcDzZrOXselF5qArR9rVI0SW8MANpHbVI0S28sADVJMUh6YwKoRYpB0hsbwEHgQ2ED6hg+xR5DTBfoznUDuB47+Ujb6Rhuxu4rFcBH4EDs5CNtp3dfKYgaKQBqiH+76OgySAFQQ/xbANFlkAKghvi3AKLLIBZATfFPKoNYADXFP6kMYgHUFP+kMogBUGP8o8sgBsBY8f8MXGqO/D6wP6qRb98ouBvEAEiNvx6DuwU8BH41x69L7AvANUBPlMSO4G4QCiAl/vpqzDPgKvBtzQp3A3eB0wmP8gedFIUCiI3/G+A88Nb41h4DHgFHja93r0/M1wahAELjr3f6CvC8+W5QyHp0bEqCEqFkWEdQGYQACIm/blI8AG4DqvmUISfIDXKE9dkFcxmEALDG/wVwEfiSsmrPtvsaqKcM+zV3gxAAQ/Fv25oATDkEYKhtmsvACqAv/r62NiUA7dvSNk1lYAXgi7+lrU0Noq9tmsrACsCNf2hbmxqEr22aysACoBv/lLY2NQRf2xwsAwsARUlnb2O1talBdNvmnaEbphYA94CnE7S1qUGobeozzMt9E/0BUX274HXoySkAAAAASUVORK5CYII=)](https://github.com/DevSrSouza/KotlinBukkitAPI/stargazers)
[![Github Issues](https://img.shields.io/github/issues-raw/CarcadeX/KotlinBukkitKit.svg?style=for-the-badge&color=orange&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAGfklEQVR4Xu2bd6glNRSHv7Vjw46KDRULCqLYFVSwIHZFFCvYKzZU7A17r6CsiqjYG4oigqIidtQ/RCzYOzbsDZUPMst9900myczc3bfuHnj/vElyzvklOTntTmIGp0kzuP7MBGDmCRg9ArMCawMbA6sCKwNLA/OFP7//BnwPfAa8C7wBvAC8BPw5ShFHdQXmALYF9gQ2B+ZvqcQvwBPAXcCDwO8t14lO6xuARYBjgEOAhXoW9jtgMnA58GVfa/cFwDzAacCRwNx9CRdZx+tyNXAu8GNXXn0AsBNwFbBUV2EK538BHA48UDhvzPAuAMwVFD+wiwA9zL05AOHJKKa2ALjbjwBrFHMczYTXge2BT0qXbwPAKsDjwDKlzEY8/vPw4rxVwqcUAJV/Bli0hAnwD/Ai8BTwCvAO4B3+GfgbmBdYIvgI6wCbARsAsxTyOQG4uGROCQAe++cKd/5j4BrgdsAdKqHFgb3D/V42Y+JZwJkZ41oZQQ2enlnunf8aOAW4BfirVKih8bMB+wHnAItF1kopXwEzDqDcE3A9cFCmIrcCRwXXNnNK1rAFw/uvdzlIOcqfESaMG5sDgO/8/RkiutN6gDclxv6b+J6SyWf3WmB2oET5iu2YOSlmGietasrJ+RXYObwOKay6AuD6WwIaS73BGHncq50fHjMFhBQAFwAnJjRy57fLVN6l+gAgBXKT8mNOQhMAPnUfZvj2BwA3piQa+N4FAI2x8/9oufPjTkITAB6vkxOKafD2KVC+ywlQeUNi/QavW12eIGfnxxjQGADG8zoqTSGtT53JjR+mAgCV8lsFXg8Buw49scXK6zfEABDh+xKKaY2Nz0up9AoMK1/xU77dw4lopbwLxQBwcUGIkR7eii2dnBIAYspXct0Z3OrTC3Yh+QzqeZl9MWcXo2Kfu4URTClfoPOUoVmO0HrB7Y0xMLAxqVnq21fr5Z4ADd4ObbSMzKl1muquwHHAJQ2Mnwc27CBYLgDbBA9Ug9yVoh5jHQAatv0bOJ4XAp22QuUC4Po7AvcAXsu21Ogu1wHwbMjhxxhqHLvk4UoAUAafuzsA6wellIoVal8Bvb+m+Ht14M1SSVoYwUEWewA6XSUJkqTyMqg7Ad8mHKCFwyvRFoPSE1Dx2RcwAZqKXxyfpXwMAF1MQ80YzdmxXNUWAOXR+TI30QRCtvLTIwDKbC3ANFsdpZQ/CTh/cOL0dAUG5T46lMgG/5dSXndZ8MYkdOsAmIhGsG639UYvDB9ylDc58gGwfOoEpJ7BXTJTZDEb0sUGDK9pPdLnsSkbPBgoPQ1smgIg5Qh5h1J5grYvRN/zhqPEG4CDUwAcC1zaIInpcYsWE53qQmRL91ekAMgJhiyL2c0xUSmWH7BT5dUUAN4pw+Gmrg4TpRdNUO1jytuCYwOH0ewUijkU9wIauxhZhV1hKiRESjFuygzdDew2vGAMgJxiiEUQvbJS6vMVGOSdSotZPn84FwBjcBMe+v0x+iYkRb0uJTQKAFLKK+uSdSe2yae2GHlqQjPDVCO1EuobgJTyylaUEKmUcfc/AmyAaqLSq9AnAIPeYEzGn0J4rxEcR6nQMqc4YqHC3N2jmcegLwC80yZmUjmCsxtqhMnY2pY3i6OpdhiLo2ZuckDoAwDzhabuDc2bSN9/tdCJWjsudQKcJNJWYlLkSTgi42XoCsChoTstJ08oUI2bkgOAil8HyDiHNIw2TJpZ6pMs09kXYDUoh+xdtFGjkXIB8KiZDl8ztWD4rvJGalaNuzY7m52yAu1d1pPLoZdDYjfJOxcAmfqO2iS1XI4EYcynYddskirt4bNrrGqSStmgQZHeBzbK7ScuAUAmKwHmC2LNSjFs9L9tj3syBCNvh+qzT5Q2wU4UAbbeuC6wCbB+hoUf5vdV2Pn3cjepFIAKBBslS05Crjxdxrnzls+zlZdZGwCq66BfvVYXiXuc6+myTae4jb4tAMquYbwMOKxHRdospbU/vq2x7QJAJazI27+f083ZRsHYHJ0c/Y4c5yvKtw8AXFyP0Zy76WoN2ihJw+mvRuxga9UiPyhcXwBUa+qs6Hx4LXLf7FywDGl1hK7sswu1bwAqZXRetgb2ArYAFsjVcmicEZw/mroNeCz0A7Vcqn7aqAAY5GaOUQ9y8GdzOja24FR5R3/749G290gfwQBMf+O14Rxer9p3eAb7lmOarTc1TsA0Uy6H8UwAclD6P4/5D0siPlDBJvVTAAAAAElFTkSuQmCC)](https://github.com/DevSrSouza/KotlinBukkitAPI/issues)
[![GitHub last commit](https://img.shields.io/github/last-commit/CarcadeX/KotlinBukkitKit/main.svg?style=for-the-badge&color=orange&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADgAAABACAYAAABP97SyAAADZElEQVRoQ+2ZSciOURTHf1+ZZdoZMpdhIUOyJGTe2CpLyRhKYYGwQUghQ1nJ3sZQhpIQGbKhEMk8LMg8Rf/PfdT3ed/nTs/z9Xi7d/Uuzv+c8z//59577nmbaPDV1OD8SAT/d4WTgknBilcgfaIVF8iaXlLQWqKKGyQFKy6QNb2koLVEFTdICkYI1B2YA0wBxgKDgB7G3zvgIXATOAccB95HxKoLLUPBocA6YB7QxTHpT8BRYCvwwBHjZFYkwY7AZmAV0N4p+r9G34AdwCZAv6NXUQQHAseAMdEZ/XFwHZgLPIn1VwTBkWYf9Y5NphX+GTAZuBvjN5Zgf+AK0CcmiRysFJwAPA/1H0NQ++wSMD40uCPuMjAR+OFo38IshuAGcxiExPXF6FTWCeu9Qgn2A+4DnRwi6q47aPbpI2OvO1H34yJgtIOPj4Cun5cOtoUouBtYYQn2GVgOHM6xU4EXAvJnK9Z2YE1bENR99wLomRNM5GYAFxwTmgScspB8A/QFvjv6bDYL+URnm9YqL84Ci3K1sPpc91uSnw6c9iX4ywfgYKs9N87BrrWJii2sy550di+nRROUEjpUQtYSYF8IsB6mDILDgHuBSY4A7gRia8LKIKhDKLRRFvZLIuhRgTIUHB7RIKtxv+2Rv9W0DIKLgQPWyLUNlgF7ArFttgdvmRGF7+msYgs7qmiCvv5mAScsoJCrYimw1+J3GnDGJ+GQTkYnnd5nvXIC6SScCZx3TEYP25OAfNdbrwE1+aW3akpgl5m95OUvkiuBQznNhAqsy32nhZzibAPWOhbsr1mIggKr6dVzqbNDQO0rkdR4UKNCrSHAVPOScNlzei4J88ohXguTUIJyst5M0XxjhthLOSnovWIItgMumpmJd2APgGLoOfXTAxP9iWYOtOmvmk82JL4N89gUUO/PoBWjYBZQDbL2V9GTtadmbBjauDfnVwRB+RlgBr/6D6KIdc0MfkUyahVFUEl0ADYCq83vkMS+Apq9bPG97+oFK5JgFmOwGQ7NB7o6svwAHDEnZTZ5c4Tmm5VBMIvYDVBbl/19JuLZoOqtuRNvAGfNwEkkC19lEiw82RCHiWBI1aqESQpWSY2QXJKCIVWrEiYpWCU1QnJJCoZUrUqYpGCV1AjJJSkYUrUqYRpewd/nrH4569+7pgAAAABJRU5ErkJggg==)](https://github.com/DevSrSouza/KotlinBukkitAPI/commit)
[![Discord](https://img.shields.io/discord/1133859884685856878?style=for-the-badge&color=ORANGE&label=Discord)](https://discord.gg/pyrh4dUPZD)
[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg?color=ORANGE&style=for-the-badge)](https://choosealicense.com/licenses/mit)

KotlinBukkitKit is an API for Bukkit/SpigotAPI using the cool and nifty features Kotlin has to make your life more easier.

### Contents:

* [Documentation](https://red-tea.gitbook.io/kotlinbukkitkit)
* [IDEA Plugin](https://github.com/CarcadeX/KotlinBukkitKit-Tooling)


# Samples

## [We have more Samples in the Documentation](https://red-tea.gitbook.io/kotlinbukkitkit)

KotlinBukkitAPI goes beyond this samples, and you can find all of it in the [wiki/documentation](https://red-tea.gitbook.io/kotlinbukkitkit).

Event DSL sample
```kotlin
plugin.events {
  event<PlayerJoinEvent> {
    player.msg("&3Welcome ${player.name}".translateColor()) 
  }
  
  event<PlayerQuitEvent> {
    broadcast("&eThe player &c${player.name} &eleft :(".translateColor())
  }
}
```

Simple Command DSL example
```kotlin
plugin.simpleCommand("hi") {
  sender.msg("&eHallo".translateColor())
}
```

Item meta DSL and other stuff
```kotlin
val gem = item(Material.DIAMOND).apply {
  amount = 5
  meta<ItemMeta> {
    displayName = "&bGem".translateColor()
  }
}
val encbook = item(Material.ENCHANTED_BOOK).meta<EnchantmentStorageMeta> {
  displayName = "&4&lThe powerful BOOK".translateColor()
  addStoredEnchant(Enchantment.DAMAGE_ALL, 10, true) // putting sharpness 10 to the book
}
```

Another approach:
```kotlin
val gem = item(Material.DIAMOND, amount = 5).displayName("&bGem".translateColor())

val encbook = metadataItem<EnchantmentStorageMeta>(Material.ENCHANTED_BOOK) {
  displayName = "&4&lThe powerful BOOK".translateColor()
    addStoredEnchant(Enchantment.DAMAGE_ALL, 10, true) // putting sharpness 10 to the book
}
```



## IDEA Plugin - project wizard

You can find it on a <a href="https://plugins.jetbrains.com/plugin/24043-kotlinbukkitkit-tooling">official plugin page</a>
<img src="https://red-tea.gitbook.io/~gitbook/image?url=https:%2F%2F630270463-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252F1PBqxCHnbDKJwzj8IhJk%252Fuploads%252Fo2jPveKWzw47uGTxMm2Q%252Fimage.png%3Falt=media%26token=403c70a1-fa12-435d-99c2-aab79493de97&width=768&dpr=4&quality=100&sign=5ca18a6e18c88ceb0b5a355577cfb45cadea88b46003ac032ac4f52d7cfe52b0">
