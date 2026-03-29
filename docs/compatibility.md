# Compatibility

<sup>[<kbd>← Back to Starting Page</kbd>](/libr-getter)</sup>

LibrGetter tries to be compatible with as many other mods as possible, by modifying the client as little
as possible. In addition, there are some mods, that extended the functionality of librarians, that received
an explicit integration in LibrGetter, allowing you to use the features of both mods at the same time.
This page lists all the mods and plugins that are known to be compatible with LibrGetter, as well as the ones that received
an explicit integration, as well as some mods that are known to be incompatible. Note, that the list of
compatible mods is not exhaustive, and there may be other mods that are compatible with LibrGetter without
any issues.

## OffersHUD

OffersHUD - [<kbd>Modrinth</kbd>](https://modrinth.com/mod/offershud)

* LibrGetter was previously incompatible with OffersHUD
* From version `2.1.0` of LibrGetter, it is no longer incompatible, but the compatibility was not perfect,
  and some features of LibrGetter might have not worked as expected
* From version `1.9.0` of OffersHUD, the compatibility is now better, but not guaranteed to be perfect

## Trade Cycling

With Trade Cycling ([<kbd>Modrinth</kbd>](https://modrinth.com/mod/trade-cycling)) installed on both the server and the client,
instead of replacing the lectern, LibrGetter will push the cycling button, until an enchantment is found.
Since Trade Cycling requires you to have the Merchant screen open, the process can't be stopped using commands,
instead you can close the trading screen to automatically stop.

## Visible Traders

With Visible Traders ([<kbd>Modrinth</kbd>](https://modrinth.com/mod/visible-traders)) or any similar mod,
it is possible to customize the search process even further by adjusting
the matching algorithm. More information: [<kbd>Config | Matching Algorithm</kbd>](/libr-getter/config#matching-algorithm).

## Plugins

The finding of enchantments goes through following steps:

1. Goes over every plugin added explicitly in LibrGetter (see below) and searches for plugin specific enchantments.
2. Tries to find a vanilla enchantment.
3. Optionally, takes all the data available on the villager and searches for the enchantment string ([<kbd>Config | Fallback</kbd>](/libr-getter/config#fallback))

Explicitly added plugins:

* Enchantment Solution ([<kbd>SpigotMC</kbd>](https://www.spigotmc.org/resources/enchantment-solution.59556/))

Plugins, that are known to be compatible without further adjustments:

* ExcellentEnchants ([<kbd>Modrinth</kbd>](https://modrinth.com/plugin/excellentenchants))

You can request support for more plugins in the [<kbd>issues</kbd>](https://github.com/gXLg/libr-getter/issues).
If possible, please provide the whole output of the `/data get entity <villager>` command,
where `villager` is a librarian selling at least one custom enchantment provided by the plugin.
