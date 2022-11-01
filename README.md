# LibrGetter
![logo](https://repository-images.githubusercontent.com/494582079/ff4c06f7-2c03-4f56-bf4d-6ec8d95e0345)

A fabric mod which allows you to bruteforce
a librarian villager for the desired enchantment.

Mod supports version up from 1.16.5 to 1.18 exclusive.

# Installation
Download the jar from the [releases page](https://github.com/gXLg/libr-getter/releases/latest).

Or clone the repository and run `./gradlew build`.
The compiled jar should be under build/libs/.

# Usage
1. Face a librarian and type `/librget` (client-side command)
2. Face his lectern and type `/librget` once again
3. Add enchantments to the goals list through `/librget add <desired enchantment> <desired level>`
4. The goals may be removed through `/librget remove <enchantment> <level>`
or fully cleared using `/librget remove`
5. To list all added enchantments use `/librget list`. The output will contain `(remove)`-button
after each enchantment, which can be clicked.
6. Now make sure, that there is a block underneath the lectern
and that you are able to pick up the lectern item once it breaks
6. Type `/librget start`
7. If you wish to stop the process, type `/librget stop`

If anything is not set, the mod should complain. The mod also checks whether the villager can
upgrade his offers and whether the enchantment may be obtained.