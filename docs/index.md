<!--suppress HtmlDeprecatedAttribute-->
<img width="100%" src="/images/cover.png" alt="logo"/><br>

Welcome to the documentation for LibrGetter!

Get a grasp of all the features of the mod, how to use it and how to customize it to your needs:

* Adjusting the list of goals: [<kbd>Goals</kbd>](/libr-getter/goals)
* Starting, stopping and continuing the process: [<kbd>Process</kbd>](/libr-getter/process)
* Configuration and customization of the process: [<kbd>Configuration</kbd>](/libr-getter/config)
* Compatibility with other mods and plugins: [<kbd>Compatibility</kbd>](/libr-getter/compatibility)
* Developer guide: [<kbd>For Developers</kbd>](/libr-getter/for-developers)

# What is LibrGetter?

LibrGetter is a Minecraft mod for automating the process of finding enchantments on librarian
villagers. It is designed to be as customizable as possible, allowing you to adjust the process
to your needs, playstyle and server rules. LibrGetter is also designed to be compatible with
as many other mods as possible, by modifying the client as little as possible and providing
compatibility configs for different mods.

# Why is LibrGetter?

Finding enchantments on librarian villagers can be a very time-consuming process,
especially if you are looking for specific enchantments or are playing on a server
with a large player base, where the demand for certain enchantments is high.
LibrGetter automates this process, allowing you to find the desired enchantments much
faster and with much less effort.

# How is LibrGetter?

LibrGetter works by breaking and replacing the lectern of a librarian villager,
which causes the villager to update its trades. This process has been very carefully
optimized to be as fast and efficient as possible by implementing such features as
automatically selecting the most efficient tool for breaking the lectern, or
putting the lecterns in the offhand for higher efficiency, but also many more.

Furthermore, LibrGetter automatically clicks the villager after resetting the trades,
decreasing the time before the next try and reducing the amount of actions and eye strain
required from the player to a minimum. LibrGetter can also lock the trades
after finding a match, allowing you to continue working on other stuff while the mod
is doing its job, without worrying about losing the found enchantment.

Over the years of development, the mod has gone through many iterations and optimizations,
making it very efficient, fast, stable and fully customizable. LibrGetter is stable against
client-side and server-side lag and TPS drops, and has many features to adjust the process
to your conditions.

---

## Build

You can also build the project yourself.
For this just clone the repository and run `./gradlew build`.
Note, that you'll need `Node.js` installed on your system.
The compiled mod jar can be found under `build/libs/`.

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
        * 1 book and an amount of emeralds equaling to the most expensive expected book
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
You'd use `ATLEAST`, whenever you want to effectively find some enchantments from your goals list, while tolerating wasted trade offers.

Some example scenarios:

* You'd like to get a villager with any matching offer.
  Then you'd use `matchMode ATLEAST` and `matchAtLeast 1`
* You'd like to get a villager with his offers ideally covering the whole goals list at once.
  Then you'd use `matchMode ATLEAST` and `matchAtLeast N` where `N` is the amount of items in your goals list

### Compatibility Config

This list of configs enables/disables compatibility with different client-side mods, possibly modifying the process of finding an enchantment.
All compatibility configs are disabled by default.
Compatibility configs are grayed out when the corresponding mod is not installed.
You can read more about what each mod changes

* `/librget config tradeCycling <true/false>` - Trade Cycling integration

## View Config

You can use `/librget config <config>` to print out the currently set value.
In addition to that you can modify the config file manually. The file is located at `<minecraft folder>/config/librgetter.json`

## Config Menu

LibrGetter provides a keybind to open a book GUI with clickable configs
and their short description. By default, the keybind is set to the letter <kbd>K</kbd>.
The menu provides an overview of all togglable configs and grays out configs which have no effect with the current setup.

# Compatibility with Other Mods

## Client Side Mods

LibrGetter tries to be compatible with all other mods, by modifying the client as little as possible.

Mods that previously were not compatible and were made explicitly compatible:

* ~~OffersHUD ([<kbd>Modrinth</kbd>](https://modrinth.com/mod/offershud))~~ incompatible

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

In a previous update, a system with fallback for dynamic plugin support was developed allowing me to explicitly add support for new plugins.

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
