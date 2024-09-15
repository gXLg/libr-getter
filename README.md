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
Adding custom enchantments or levels exceeding the vanilla maximum sends a warning into the chat by default.
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
  * When true: each time before breaking the lectern mod chooses most efficient tool, which has more than 10 durability points.
  * When false: mod remembers which tool you are holding when starting the process and switches to exactly this tool each time before breaking.
    If holding nothing or a non-tool item, no tool will be picked.
* `/librget config actionbar <true/false>` - enable/disable logging found enchantments into the action bar instead of chat (default: false).
* `/librget config lock <true/false>` - enable/disable locking the trades after match (default: false).
  * In order to lock a trade you will need to have one of the following inventory configurations:
    * 24 paper to sell and 9 emeralds to buy a bookshelf.
    * 1 book and an amount of emeralds equalling to the most expensive expected book.
* `/librget config removegoal <true/false>` - enable/disable removing the goal from goals list when being found (default: false).
  * When false: when an enchantment is found, the `(remove)`-button will appear in the message.
* `/librget config checkupdate <true/false>` - enable/disable checking if a new update has been released (default: true).
  * The update message will appear once on the first world/server join. If you have [<kbd>Mod Menu</kbd>](https://modrinth.com/mod/modmenu),
    it will show you a blue dot whenever a new version is available, regardless of this config.
* `/librget config warning <true/false>` - enable/disable warning when custom enchantments format is used (default: true).
* `/librget config offhand <true/false>` - enable/disable putting the lectern in the offhand for higher efficiency (default: false).
  * The process becomes a bit more efficient if you don't have to switch slots in order to place the lectern.
  * Note: you will need to have more than one lectern in your inventory in order to increase efficiency.
  * Note: you can also place the lectern in the offhand without the `offhand` config and it will remain there.
* `/librget config manual <true/false>` - enable/disable manual mode (default: false).
  * Manual mode disables the automatic breaking and replacing of lecterns as well as trade locking, and gives you more control about the functionality.
  * When switched on, upon finding an enchantment from the goals list, the mod will switch to a state, where you can not break any lecterns.
    To finish the process you will have to use `/librget stop`.

# View Config
You can use `/librget config <config>` to print out the currently set value.
In addition to that you can modify the config file manually. The file is located at `<minecraft folder>/config/librgetter.json`

# Config Menu
LibrGetter provides a keybind to open a book GUI with clickable configs
and their short description. By default, the keybind is set to the letter <kbd>K</kbd>. 

# Localization
LibrGetter supports localization and currently implements the following languages:
* English 🇺🇸/🇬🇧
* German 🇩🇪
* Russian 🇷🇺

You may request further languages in the [<kbd>issues</kbd>](https://github.com/gXLg/libr-getter/issues).

# Features
* Stops the process, when an enchantment from the goals list is found.
* All the configs and the goals list gets saved to a file, so you don't have to set up the mod every time.
* Stops when you are too far from the villager.
* Stops when villager accidentally picked another job during the process.
* Checks if villager was already traded.
* Stable against multiple lag types.
* Compatible with [<kbd>OffersHUD</kbd>](https://modrinth.com/mod/offershud).
* Checks if enchantment can be obtained from a villager and complains if not.
* Supports enchantments tags up from `1.19.3`.
* Single mod for all versions from `1.17` up to `1.21.1`.

# About Me
I am a computer science student in Germany and have a part-time job at a tech company.
Apart from that, I enjoy my free time by spending it with friends, chatting online or gaming.

Therefore, I may not be quick when it comes to bringing out new updates.
If you want to support and motivate me to go on and keep this project alive,
you could consider making a small [<kbd>donation</kbd>](https://www.paypal.com/donate/?hosted_button_id=DVC2UQP2AXR68).
