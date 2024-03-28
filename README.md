# LibrGetter
<img width="100" src="https://repository-images.githubusercontent.com/494582079/ff4c06f7-2c03-4f56-bf4d-6ec8d95e0345"  alt="logo"/>

A fabric mod which allows you to bruteforce
a librarian villager for the desired enchantment.

# Installation
## GitHub
You can download the mod on the [<kbd>releases page</kbd>](https://github.com/gXLg/libr-getter/releases/latest) on GitHub.

## Modrinth
The mod is also available on Modrinth: [<kbd>Modrinth page</kbd>](https://modrinth.com/mod/libr-getter)

## Build
You can build the project yourself, for this just clone the repository and run `./gradlew build`.
The compiled jar should be under `build/libs/`.

# Usage
## Enchantments in the goals list
1. Add enchantments to the goals list through `/librget add <desired enchantment> [desired level] [maximum  price in emeralds]`,
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
of a minecraft id. To use this feature, your enchantment has to be put in double quotes like `"enchantmentsolution:sand_veil"`.
Note, that when adding a custom enchantment to the goals list, the level argument is required too.

Currently, the mod can parse enchantments from following plugins:
* Enchantment Solution

You can request support for more plugins in the [<kbd>issues</kbd>](https://github.com/gXLg/libr-getter/issues).
Please make sure to follow the [<kbd>guide</kbd>](https://gxlg.github.io/multi-mod) to help me.

### Warning
Adding custom enchantments or levels exceeding the vanilla maximum sends a warning into the chat by defualt.
This warning also includes non-tradable enchantments. You can enable/disable all the warnings with the config option `warning`.

## Start
### Manual
1. Face a librarian and type `/librget` (client-side command)
2. Face his lectern and type `/librget` once again
3. You can then start by running `/librget start`

### Automatic
`/librget auto` automatically selects a lectern and a librarian in 4 blocks radius and starts the process.
This should only be used when there is only one villager and only one lectern near you, or else
the mod could make a wrong selection.

## Stop
Stopping a process is as simple as writing `/librget stop`. After stopping the process you can continue
with `/librget start`.

## Config
* `/librget config notify <true/false>` - enable/disable sound notification when finding an enchantment (default: false).
* `/librget config autotool <true/false>` - enable/disable automatic tool selection (default: true):
  * When true: each time before breaking the lectern mod chooses most efficient tool, which has more than 10 durability points
  * When false: mod remembers which tool you are holding when starting the process and switches to exactly this tool each time before breaking.
    If holding nothing or a non-tool item, no tool will be picked.
* `/librget config actionbar <true/false>` - enable/disable logging found enchantments into the action bar instead of chat (default: false).
* `/librget config lock <true/false>` - enable/disable locking the trades after match (default: false).
* `/librget config removegoal <true/false>` - enable/disable removing the goal from goals list when being found (default: false).
* `/librget config checkupdate <true/false>` - enable/disable checking if a new update has been released (default: true).
* `/librget config warning <true/false>` - enable/disable warning when custom enchantments format is used (default: true).

# Localization
LibrGetter supports localization and currently implement the following languages:
* English :us:/:gb:
* German :de:
* Russian :ru:

You may request further languages in the [<kbd>issues</kbd>](https://github.com/gXLg/libr-getter/issues).

# Features
* Stops the process, when an enchantment from the goals list is found.
* All the configs and the goals list gets saved to a file, so you don't have to set up the mod every time.
* Stops when you are too far from the villager.
* Stops when villager accidentally picked another job during the process.
* Checks if villager was already traded.
* Checks if enchantment can be obtained from a villager and complains if not.
* Supports enchantments tags up from `1.19.3`.
* Stable against multiple lag types.
* Single mod for all versions from `1.16.4` up to `1.20.4`.