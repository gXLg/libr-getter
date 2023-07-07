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
1. Add enchantments to the goals list through `/librget add <desired enchantment> <desired level> [maximum  price in emeralds]`,
   where the price parameter is optional and defaults to 64
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
* `/librget notify <true/false>` - enable/disable sound notification when finding an enchantment.
* `/librget autotool <true/false>` - enable/disable automatic tool selection:
  * When true: each time before breaking the lectern mod chooses most efficient tool, which has more than 10 durability points
  * When false: mod remembers which tool you are holding when starting the process and switches to exactly this tool each time before breaking. If holding nothing or a non-tool item, no tool will be picked.

# Features
* Stops the process, when an enchantment from the goals list is found.
* All the configs and the goals list gets saved to a file, so you don't have to set up the mod every time.
* Stops when you are too far from the villager.
* Stops when villager accidentally picked another job during the process.
* Checks if villager was already traded.
* Checks if enchantment can be obtained from a villager and complains if not.