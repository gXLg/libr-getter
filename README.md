# LibrGetter
> <img width="100" src="https://repository-images.githubusercontent.com/494582079/ff4c06f7-2c03-4f56-bf4d-6ec8d95e0345" alt="logo"/><br>
> Over-engineered librarian trade finder for fabric.

A highly configurable fabric mod to automatically cycle a librarian villager for the desired enchantment by replacing his lectern.

<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
  <hr>
  <a href="https://modrinth.com/mod/libr-getter"><img alt="Modrinth badge" src="https://img.shields.io/badge/dynamic/json?labelColor=black&color=565&label=Modrinth&suffix=%20downloads&query=downloads&url=https://api.modrinth.com/v2/project/6Ts2vJ13&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAAIGNIUk0AAHomAACAhAAA+gAAAIDoAAB1MAAA6mAAADqYAAAXcJy6UTwAAAJPUExURQAAABvZahWnUha1WAYzGQlHIxvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZav///9ScwmYAAADDdFJOUwAAAAAAAA8zW3uOYwIBK3rB6Pn+ml18KiGL5HEDquOIH07R/UzKz2zu+uLHIibtafWkVCMNBRqg7/RQuT8EQbvT+5ETDBSU/NAgCSdZlcQKii7mtxJY5fF/7D1SRkB+EcWh4UilOOtPMdTCR1PqN969vmGDCw7G4DSSsAcGHrSPr3bds5CEwDKoXumcZdwcG4KmjayX32A79pjOqRjIFoXynVYVgPi6qxDnL78p2obVJFquNbGZCPdyvHPZ1yhuh8s+iRzcsrEAAAABYktHRMQUDBvhAAAAB3RJTUUH5wQXDwgZWDUtiQAAAqRJREFUOMttU/k7lFEU/k4L4hsxtNAnhRgiJZOiSfbI2oJpmRFCi2kV0aaNVLTIEpVISmnf97r/WO+534yZnqfzw73vOe+559xz7rmKIoVYvLx95vj6qUSqVBW3sGrwnxsQaAwSwcDz5i9g0wxPfmFIqJCyiEgLE4vDl7iD8PGlETotIqOQahlAdJTLA5sWY5JsbNzysHiihEBWViRqRDOlgyFmJVtMq5JWJ5vhvyZlLevrEmUILKl8PihtvYWcYt6QLmNwFjjEb4SSkZnlZLO5yJzcPBg35bODwZfPZxYwWbC5sKjYzwBUkssxSg1wKNsCtHUb89vTy3GwopBxDmcJ9YdDJYB1B9t2WmUtcbtkqt18Mxsp9irsezS2VEu+uka/mqUWSrFd2VsnhLEetoZ9TFv3HwCexdUdjBWi0aH4YTuEvtoPM3/EO1nnuTpEjD2qHIM54DhR0wmA5hZySzIntyknsbZyi8IB0tp07tRpBDKfgeWsdDjHDlFGRIjnl3O0t573gcMFUBeVS1gvlxB1dOZxv0pIu9IF01UDZRdjr1SuZQhxvbvnRrmsMePmrdsrGPRaKP8OLtmn9KM7A4N3xb8yNExUb5RlZt2btpZbXajzPoYkBaDKrtADp3HkYdloo56ndgx37UcHxSP0tIWBGH9cg1nx6TJFTNhS7eCfcA1PJ+GgDbLDs2GuX3V05Ohj0xYSBGumyq/yfApw4EWTRxPJqxQvIF6+ks9Gr0d4nN+8Nbhoi+NdJEwVSc6hVNvHOUvg+5qeDxoP3GQz6x8/qXLuZ6Op7SPy+gNTnydSELabk1Z8Mbs/hlr4dbobfUgxJMS3UdXza9H33jqnA3/OH41FCZ7/l7HW8vOXiWc9GvPf06D953/T2O/EP8HBNtcH0Zm/lqFNUgTAex4AAAAldEVYdGRhdGU6Y3JlYXRlADIwMjMtMDQtMjNUMTU6MDg6MjQrMDA6MDAE5dOaAAAAJXRFWHRkYXRlOm1vZGlmeQAyMDIzLTA0LTIzVDE1OjA4OjI0KzAwOjAwdbhrJgAAACh0RVh0ZGF0ZTp0aW1lc3RhbXAAMjAyMy0wNC0yM1QxNTowODoyNSswMDowMITaQU0AAAAASUVORK5CYII=&style=flat-square" align="center"></a>&nbsp;
  <img alt="Client-side badge" src="https://img.shields.io/endpoint?url=https%3A%2F%2Fgxlg.github.io%2Fbadges%2Fclientside.json" align="center">&nbsp;
  <a href="https://github.com/gXLg/MultiVersion"><img alt="MultiVersion badge" src="https://img.shields.io/endpoint?url=https%3A%2F%2Fgxlg.github.io%2Fbadges%2Flibrgetter-multi.json" align="center"></a>&nbsp;

  <a href="https://github.com/gXLg/libr-getter/issues"><img alt="Issues" src="https://img.shields.io/github/issues-search?query=repo%3AgXLg%2Flibr-getter%20is%3Aissue%20state%3Aclosed&style=flat-square&logo=github&logoColor=fafbfc&label=Issues%20closed&labelColor=24292d&color=fafbfc" align="center"></a>
  <a href="https://discord.gg/aYBrWe5Jtt"><img alt="Discord" src="https://img.shields.io/discord/1401376830916788306?style=flat-square&logo=discord&logoColor=black&label=gXLg%20Lounge&labelColor=orange&color=black" align="center"></a>
  <a href="https://www.paypal.com/donate?hosted_button_id=DVC2UQP2AXR68"><img alt="PayPal" src="https://img.shields.io/endpoint?url=https%3A%2F%2FgXLg.github.io%2Fbadges%2Fdonate.json" align="center"></a>
  <hr>
</div>

# Installation
You can download the mod on the [<kbd>releases page</kbd>](https://github.com/gXLg/libr-getter/releases/latest) on GitHub
or from Modrinth: [<kbd>Modrinth page</kbd>](https://modrinth.com/mod/libr-getter)

You can also build the project yourself, for this just clone the repository and run `./gradlew build`.
The compiled jar should be under `build/libs/`.

# Usage
## Enchantments in the goals list
1. Add enchantments to the goals list through `/librget add <desired enchantment> [desired level] [maximum price in emeralds]`,
   where the desired level parameter is optional and defaults to the max level
   and price parameter is optional and defaults to 64
2. The goals may be removed through `/librget remove <enchantment> <level>`
   or fully cleared using `/librget clear`
3. To list all added enchantments use `/librget list`. The output will contain `(remove)`-button
   after each enchantment, which can be clicked.

## Customization
### Levels
Datapacks/Mods may modify villagers to trade with higher levels of
enchantments than what is set in vanilla. You may enter any value in the `level` argument
to add an enchantment with a greater level to the goals list.

### Enchantments
By default, LibrGetter only parses enchantments available on the client.
If you play on a sever, the list of enchantments may be modified through a server-side
plugin to which the client has no access to.

LibrGetter `add` command has an edge case where you can add custom enchantments in the form
of a minecraft id. To use this feature, your enchantment has to be put in double quotes like
`"enchantmentsolution:sand_veil"`.  Note, that when adding a custom enchantment to the goals list,
the level argument is required too, since the highest level is unknown.

You can read more about plugins compatibility [<kbd>further below</kbd>](#Compatibility)

### Warning
Adding custom enchantments or levels exceeding the vanilla maximum sends a warning into the chat by default.
This warning also covers non-tradable enchantments. You can enable/disable all the warnings with the config option `warning`.

## Start
### Automatic
`/librget auto` automatically selects the closest lectern in a 4 blocks radius and the librarian closest to the lectern,
and starts the process. If you stand close to two or more villagers and lecterns, LibrGetter might select the wrong targets.

### Manual
1. Face a librarian and type `/librget` (client-side command)
2. Face his lectern and type `/librget` once again
3. You can then start by running `/librget start`

## Stop
Stopping a process is as simple as writing `/librget stop`.
To continue the process after stopping, the recommended way is to use `/librget continue`,
which works the same way as `/librget start`, but will not reset the counter.
Restarting with `/librget auto` will not keep the counter as well.

## Config
### Process
* `/librget config autoTool <true/false>` - enable/disable automatic tool selection (default: `true`)
  * When true: each time before breaking the lectern mod chooses most efficient tool, which has more than 10 durability points
  * When false: mod remembers which tool you are holding when starting the process and switches to exactly this tool each time before breaking
    If holding nothing or a non-tool item, no tool will be picked
  * This config has no effect when manual mode is enabled
* `/librget config offhand <true/false>` - enable/disable putting the lectern in the offhand for higher efficiency (default: `false`)
  * The process becomes a bit more efficient if you don't have to switch slots in order to place the lectern
  * Note: you will need to have more than one lectern in your inventory in order to increase efficiency
  * Note: you can also place the lectern in the offhand without the `offhand` config and it will remain there
  * This config has no effect when manual mode is enabled
* `/librget config manual <true/false>` - enable/disable manual mode (default: `false`)
  * Manual mode disables the automatic breaking and replacing of lecterns as well as trade locking and clicking the librarian for you, and gives you more control about the functionality
  * Even though automatic clicking is disabled, you will only have to click the villager but the trades will be automatically parsed and the merchant screen will be automatically closed
  * When enabled, upon finding an enchantment from the goals list, the mod will switch to a state, where you can not break any lecterns
    To finish the process you will have to use `/librget stop`
* `/librget config waitLose <true/false>` - enable/disable waiting for villager to lose profession (default: `false`)
  * Villagers will usually update the profession, when the lectern is replaced
    Although, in some situations, server-side lag may be so high, that this doesn't happen
    If you experience lags, this option is recommended to turn on
  * The waiting mode will slow down the process by around 1-2 ticks without lag, but will guarantee, that the villager updates its profession
  * When manual mode is enabled, during this waiting the placing of the lectern is disabled on the client
* `/librget config safeChecker <true/false>` - enable/disable the safe checker (default: `true`)
  * Checks if the villager could interfere with the placing process by running over the lecterns position
  * If the villager is inside a vehicle, the test if positive by default
* `/librget config timeout <int [0; 20]>` - Time in seconds to wait for a villager to update its profession before replacing the lectern
  * If set to `0`, this option is disabled (default)
  * If set to any other number, the mod will hang in a state, where it waits for the villager to accept the librarian profession
    for the specified amount of time in seconds, until it reaches the timeout, then it will break and replace the lectern again
  * When manual mode is enabled, the lectern is to be replaced manually
* `/librget config rotationMode <NONE/INSTANT/SMOOTH>` - How to change head rotation between breaking the lectern and talking to the villager (default: `INSTANT`)
  * `NONE` - don't change head rotation; may trigger AntiCheats
  * `INSTANT` - rotate the head instantly; should bypass most simple AntiCheats
  * `SMOOTH` - imitate natural player head rotation; slows down the process drastically but should bypass even some advanced AntiCheats
  * This config has no effect when manual mode is enabled

### Actions on success
* `/librget config notify <true/false>` - enable/disable sound notification when finding an enchantment (default: `false`)
* `/librget config lock <true/false>` - enable/disable locking the trades after match (default: `false`)
  * In order to lock a trade you will need to have one of the following inventory configurations:
    * 24 paper to sell and 9 emeralds to buy a bookshelf
    * 1 book and an amount of emeralds equalling to the most expensive expected book
  * This config has no effect when manual mode is enabled
* `/librget config removeGoal <true/false>` - enable/disable removing the goal from goals list when being found (default: `false`)
  * When false: when an enchantment is found, the `(remove)`-button will appear in the message

### Messages
* `/librget config logMode <NONE/CHAT/ACTIONBAR>` - where to log found enchantments (default: `CHAT`)
  * `NONE` - don't log anywhere
  * `CHAT` - log in the client chat
  * `ACTIONBAR` - log into the action bar
* `/librget config checkUpdate <true/false>` - enable/disable checking if a new update has been released (default: `true`)
  * An update message will appear in the chat once on the first world/server join. If you have [<kbd>Mod Menu</kbd>](https://modrinth.com/mod/modmenu),
    it will show you a blue dot whenever a new version is available, regardless of this config
* `/librget config warning <true/false>` - enable/disable warning when custom enchantments format is used (default: `true`)

### Goal matching
* `/librget config fallback <true/false>` - Fallback option to search for unsupported enchantments (default: `false`)
  * Takes all the data given about trades from the villager, convert it to text and search for all enchantment IDs currently in the goals list
  * It is not guaranteed to make a match, and can cause false positives
  * May cause lag with a large list of enchantments
  * Use only as a temporary solution, if the plugin you are playing with is not supported
* `/librget config matchMode <VANILLA/PERFECT/NUMBER>` - When to stop cycling (default: `VANILLA`)
  * `VANILLA` - the first or the second trade matches the goals list
  * `PERFECT` - all offered trades match the goals list
  * `ATLEAST` - at least N trades must match the goals list, N is given by `matchAtLeast`
* `/librget config matchAtLeast <int [1; inf)>` - Number of unique trades which must match the goals list to consider the attempt a success
  * If `matchAtLeast` is greater than the amount of items in the goals list, LibrGetter will use that amount instead.
  * Note: the search time is expected to grow exponentially with increase of this config

#### Match Mode
While `VANILLA` is pretty intuitive, the other two Match Modes may be a bit confusing.
You'd use `PERFECT`, whenever you want to get a perfect villager without any wasted trade offers.
You'd use `ATLEAST`, whenever you want to effectively find all enchantments from your goals list, while tolerating wasted trade offers.

Some example scenarios:
* You'd like to get a villager with any matching offer.
  Then you'd use `matchMode ATLEAST` and `matchAtLeast 1`
* You'd like to get a villager with his offers ideally covering the whole goals list at once.
  Then you'd use `matchMode ATLEAST` and `matchAtLeast N` where `N` is the amount of items in your goals list

### Compatibility
* `/librget config tradeCycling <true/false>` - Trade Cycling integration

This list of configs enables/disables compatibility with different client-side mods, possibly modifying the process of finding an enchantment.
All compatibility configs are disabled by default.
To learn more about what each mod compatibility changes, read further below.
Compatibility configs are grayed out when the corresponding mod is not installed.


## View Config
You can use `/librget config <config>` to print out the currently set value.
In addition to that you can modify the config file manually. The file is located at `<minecraft folder>/config/librgetter.json`

## Config Menu
LibrGetter provides a keybind to open a book GUI with clickable configs
and their short description. By default, the keybind is set to the letter <kbd>K</kbd>.
The menu provides an overview of all togglable configs and grays out configs which have no effect with the current setup.

# Compatibility
## Client Side Mods
LibrGetter tries to be compatible with all other mods, by modifying the client as little as possible.

Mods that previously were not compatible and were made explicitly compatible:
* OffersHUD ([<kbd>Modrinth</kbd>](https://modrinth.com/mod/offershud))

Mods that add/change functionality of LibrGetter:
### Trade Cycling
With Trade Cycling ([<kbd>Modrinth</kbd>](https://modrinth.com/mod/trade-cycling)) installed on both the server and the client,
instead of replacing the lectern, LibrGetter will push the cycling button, until an enchantment is found.
Since Trade Cycling requires you to have the Merchant screen open, the process can't be stopped using commands,
instead you can close the trading screen to automatically stop.

### Visible Traders
With Visible Traders ([<kbd>Modrinth</kbd>](https://modrinth.com/mod/visible-traders)) or any similar mod,
it is now possible to customize the search even further by adjusting the [`matchMode`](#match-mode) config.

## Plugins
In a previous update, a system with fallback for dynamic plugin support was developed, which allows me to explicitly add new plugins as quick as possible.

The finding of enchantments goes through following steps:
1. Goes over every plugin added explicitly in LibrGetter (see below) and searches for plugin specific enchantments.
2. Tries to find a vanilla enchantment.
3. (optionally) Takes all the data available on the villager and searches for the enchantment string.

Explicitly added plugins:
* Enchantment Solution ([<kbd>SpigotMC</kbd>](https://www.spigotmc.org/resources/enchantment-solution.59556/))

Plugins, that are known to be compatible without further adjustments:
* ExcellentEnchants ([<kbd>Modrinth</kbd>](https://modrinth.com/plugin/excellentenchants))

You can request support for more plugins in the [<kbd>issues</kbd>](https://github.com/gXLg/libr-getter/issues).
If possible, please provide the whole output of the `/data get entity <villager>` command,
where `villager` is a librarian selling at least one custom enchantment provided by the plugin.

# Localization
LibrGetter supports localization and currently implements the following languages:
* English 🇺🇸/🇬🇧
* German 🇩🇪
* Russian 🇷🇺
* Vitenamese 🇻🇳 (thanks [@ToanAnh312](https://github.com/ToanAnh312))
* Japanese 🇯🇵 (thanks [@H1ggsK](https://github.com/H1ggsK))
* Spanish 🇪🇸

You may request further languages or suggest improvements in the [<kbd>issues</kbd>](https://github.com/gXLg/libr-getter/issues) or
the [<kbd>Discord Community</kbd>](https://discord.gg/aYBrWe5Jtt).

# Features
* Stops the process, when an enchantment from the goals list is found.
* All the configs and the goals list gets saved to a file, so you don't have to set up the mod every time.
* Stops when you are too far from the villager.
* Stops when villager accidentally picked another job during the process.
* Checks if villager was already traded.
* Stable against multiple lag types.
* Checks if enchantment can be obtained from a villager and complains if not.
* Supports enchantments tags up from `1.19.3`.
* Single JAR for all versions from `1.17` up to `1.21.10`.

# About Me
I am a computer science student in Germany and have a part-time job at a tech company.
Apart from that, I enjoy my free time by spending it with friends, chatting online or gaming.

If you want to keep this project alive, found it helpful or just want to support and motivate me to go on,
you could consider making a small [<kbd>☕ donation</kbd>](https://www.paypal.com/donate?hosted_button_id=DVC2UQP2AXR68).
