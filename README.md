# LibrGetter
![logo](https://repository-images.githubusercontent.com/494582079/ff4c06f7-2c03-4f56-bf4d-6ec8d95e0345)

A fabric mod which allows you to bruteforce
a librarian villager for the desired enchantment.

# Installation
Download the jar from the [releases page](https://github.com/gXLg/libr-getter/releases/latest).

Or clone the repository and run `./gradlew build`.
The compiled jar should be under build/libs/.

# Usage

## Enchantments in the goals list
1. Add enchantments to the goals list through `/librget add <desired enchantment> [desired level] [maximum  price in emeralds]`,
   where the desired level parameter is optional and defaults to the max level
   and price parameter is optional and defaults to 64
2. The goals may be removed through `/librget remove <enchantment> <level>`
   or fully cleared using `/librget clear`
3. To list all added enchantments use `/librget list`. The output will contain `(remove)`-button
   after each enchantment, which can be clicked.

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

# Features
* Stops the process, when an enchantment from the goals list is found.
* All the configs and the goals list gets saved to a file, so you don't have to set up the mod every time.
* Stops when you are too far from the villager.
* Stops when villager accidentally picked another job during the process.
* Checks if villager was already traded.
* Checks if enchantment can be obtained from a villager and complains if not.
* Supports enchantments tags in the Warden release.
* Stable against multiple lag types.

# Naming
Since the mod isn't that big, multiple versions of Minecraft can support the same version of LibrGetter.
I included multi version compiled mods, and named them as follows:

| Included Versions                  | Name          |
|------------------------------------|---------------|
| 1.16.4<br>1.16.5                   | Netherite     |
| 1.17                               | Axolotl       |
| 1.17.1<br>1.18<br>1.18.1<br>1.18.2 | Copper        |
| 1.19<br>1.19.1<br>1.19.2           | Sculk         |
| 1.19.3<br>1.19.4<br>1.20<br>1.20.1 | Warden        |
| 1.20.2<br>1.20.3<br>1.20.4         | CherryBlossom |

I think these names are pretty cute and easy to use -
just search for your version and grab the right `libr-getter-<version>-<name>.jar` from the releases!