# Config

<sup>[<kbd>← Back to Starting Page</kbd>](/libr-getter)</sup>

The config is the core of LibrGetter's flexibility. It allows you to customize various aspects
of mod's behavior, such as modifying the process, adjusting the messages displayed in the
chat and much more.

The config is stored in a file named `librgetter.json` located in the `config` folder of your Minecraft
directory. You can edit this file manually with any text editor, but make sure to follow the correct JSON
format and to edit the file while Minecraft is closed to avoid any issues.

However, editing config manually is rarely needed, as all the config options can be changed in-game
with commands and a handy config GUI.

# Managing Configs With Commands

<div align="center">
    <img width="70%" src="/libr-getter/images/config.jpeg" alt="Managing configs with a command">
</div>

All config options can be managed in-game with the command `/librget config`.

* To see the currently set value for a config option, simply
  type `/librget config <config name>`
* To change the value of a config option,
  type `/librget config <config name> <new value>`

Note, that most configs can be changed while the process is running, while a few others can't
and require you to stop the process before changing them.

# Config GUI

<div align="center">
    <img width="70%" src="/libr-getter/images/gui.jpeg" alt="Managing configs with the GUI">
</div>

The preferred way of managing configs is through the in-game GUI. The GUI provides a user-friendly
interface for changing config options, seeing the currently set values and get visual feedback on
what each config option does. To open the config GUI, use the keybind <kbd>K</kbd>.

The GUI looks like a vanilla Minecraft written book. Each page of the book contains different
config options, with their name, description, current value and buttons that you can click to
change the value.

On the first page you'll see a list of config categories. Clicking on them will navigate you to
the page with config options from that category. You can navigate back to the categories list
with the <kbd>↩</kbd> button at the top of each page.

Each page starting with the second one contains a list of config options. Each option is
displayed in the same way:

```
<config> <reset button>
<toggles>
```

Hovering your mouse over the config will display a tooltip with a description of what the
config does and how to use it.

You can click on the toggles to change the value. If the config is not set to the default value,
the reset button <kbd>↩</kbd> will appear next to the name, which you can click to reset the config
to its default value.

In some cases, the config option will be grayed out. This means that this config option currently
is not effective, and changing its value won't have any effect until you change some other
config option. You can still hover over it to see the tooltip and can even change the value.

# Config Categories

Config options are divided into categories to make it easier to find the one you are looking for
and to provide a better overview of the available options. Each category contains config options
related to a specific aspect of LibrGetter's behavior. Here is a list of all available categories
and a brief description of what they contain:

* **Process** - configs related to the process of obtaining the enchanting books, such as changing the
  behavior of the process and how it interacts with the game
* **Success** - configs related to what happens when you obtain a desired enchantment, such as playing
  a sound or automatically locking the trade
* **Messages** - configs related to various messages displayed by LibrGetter
* **Matching** - configs related to how LibrGetter matches the obtained enchantments with your goals,
  such as allowing partial matches or having a custom matching algorithm
* **Compatibility** - configs related to compatibility with other mods

In following sections you can find a detailed description of each config option available in each category.

## Process

> <sup><b>💡 Tip</b></sup><br>
> More about the process of finding an enchantment: [<kbd>Process</kbd>](/libr-getter/process)

### Auto Tool

Enable/disable automatic tool selection.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>autoTool</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>true, false</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>true</td>
        </tr>
        <tr>
            <td><b>Only Effective</b></td>
            <td>when <code>manual = false</code> and <code>tradeCycling = false</code></td>
        </tr>
    </tbody>
</table>

When true: each time before breaking a lectern, LibrGetter chooses the most efficient tool that has more
than 10 durability points to break the lectern, and switches to it. If no such tool is found,
no tool will be picked.

When false: LibrGetter remembers which tool you are holding when starting the process and switches to exactly
this tool each time before breaking. If holding nothing or a non-tool item, no tool will be picked.

### Offhand Lectern

Enable/disable putting the lectern in the offhand for higher efficiency.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>offhand</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>true, false</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>false</td>
        </tr>
        <tr>
            <td><b>Only Effective</b></td>
            <td>when <code>manual = false</code> and <code>tradeCycling = false</code></td>
        </tr>
    </tbody>
</table>

The process becomes a bit more efficient if you don't have to switch slots in order to place the lectern.

> <sup><b>ℹ️ Note</b></sup><br>
> You will need to have more than one lectern in your inventory in order to increase efficiency.

> <sup><b>ℹ️ Note</b></sup><br>
> If you place the lectern in the offhand yourself, it will remain there regardless of this config.

### Manual Mode

Enable/disable manual mode.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>manual</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>true, false</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>false</td>
        </tr>
        <tr>
            <td><b>Only Effective</b></td>
            <td>when <code>tradeCycling = false</code></td>
        </tr>
    </tbody>
</table>

Manual mode disables the automatic breaking and replacing of lecterns as well as trade locking and clicking the
librarian for you, and gives you more control about the functionality. Even though automatic clicking is disabled,
you will have to just click the villager but the trades will be automatically parsed and the merchant screen will
be automatically closed, reducing the time spent on each cycle to move the mouse and identify the offered trades.

When enabled, upon finding an enchantment from the goals list, the mod will switch to a
state, where you can not break any lecterns - to finish the process you will have to use `/librget stop` or press
<kbd>J</kbd>.

This mode is recommended for players who want to have more control over the process, or for those who are
playing on servers with strict rules or AntiCheats, where the automatic breaking and placing of lecterns
may trigger them.

### Wait Profession Lose

Enable/disable waiting for the librarian to lose profession.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>waitLose</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>true, false</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>false</td>
        </tr>
        <tr>
            <td><b>Only Effective</b></td>
            <td>when <code>tradeCycling = false</code></td>
        </tr>
    </tbody>
</table>

Villagers will usually update the profession, when the lectern is replaced. Although, in some situations,
server-side lag may be so high, that this doesn't happen. If you experience lags, this option is recommended
to be turned on.

The waiting mode will slow down the process by around 1-2 ticks without lag, but will
guarantee, that the villager updates its profession. When manual mode is enabled, during this waiting the
placing of the lectern is disabled on the client.

### Safe Checker

Enable/disable the safe checker.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>safeChecker</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>true, false</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>true</td>
        </tr>
        <tr>
            <td><b>Only Effective</b></td>
            <td>when <code>tradeCycling = false</code></td>
        </tr>
    </tbody>
</table>

Checks if the villager could interfere with the placing process by running over the lecterns position.
If the villager is inside a vehicle, the test returns safe by default.

### Timeout

Time in seconds to wait for a villager to update its profession before replacing the lectern again.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>timeout</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>integers from 0 to 20</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>0</td>
        </tr>
        <tr>
            <td><b>Only Effective</b></td>
            <td>when <code>tradeCycling = false</code></td>
        </tr>
    </tbody>
</table>

If set to `0`, this option is disabled. If set to any other number, LibrGetter will hang in a state,
where it waits for the villager to accept the librarian profession for the specified amount of time in seconds,
until it reaches the timeout, then it will break and replace the lectern again. When manual mode is enabled,
the lectern is to be replaced manually

### Rotation Mode

How to change head rotation between breaking the lectern and talking to the villager.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>rotationMode</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>NONE, INSTANT, SMOOTH</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>INSTANT</td>
        </tr>
        <tr>
            <td><b>Only Effective</b></td>
            <td>when <code>manual = false</code> and <code>tradeCycling = false</code></td>
        </tr>
    </tbody>
</table>

* NONE - don't change head rotation; may trigger AntiCheats
* INSTANT - rotate the head instantly; should bypass most simple AntiCheats
* SMOOTH - imitate natural player head rotation; slows down the process drastically but should bypass even some advanced AntiCheats

## Success

### Notify

Enable/disable sound notification when finding an enchantment.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>notify</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>true, false</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>false</td>
        </tr>
    </tbody>
</table>

### Lock Trades

Enable/disable locking the trades after match.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>lock</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>true, false</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>false</td>
        </tr>
        <tr>
            <td><b>Only Effective</b></td>
            <td>when <code>manual = false</code></td>
        </tr>
    </tbody>
</table>

In order to lock a trade you will need to have one of the following inventory configurations:

* 24 paper to sell and 9 emeralds to buy a bookshelf
* 1 book and an amount of emeralds equaling to the most expensive expected book

### Remove Goal

Enable/disable removing the goal from goals list when being found.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>removeGoal</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>true, false</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>false</td>
        </tr>
    </tbody>
</table>

When false: when an enchantment is found, the "remove" button will appear in the message.

When true: the goal will be automatically removed from the goals list with a regular removal
message, and the "remove" button won't appear in the message.

## Messages

### Log Mode

Where to log found enchantments.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>logMode</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>NONE, CHAT, ACTIONBAR</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>CHAT</td>
        </tr>
    </tbody>
</table>

* NONE - don't log anywhere
* CHAT - log in the client chat
* ACTIONBAR - log into the actionbar

### Check Update

Enable/disable checking if a new update has been released.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>checkUpdate</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>true, false</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>true</td>
        </tr>
    </tbody>
</table>

When enabled, an update message will appear in the chat once on the first world/server join if a new version
of LibrGetter has been released.

If you have [<kbd>Mod Menu</kbd>](https://modrinth.com/mod/modmenu), it will show you a blue dot whenever
a new version is available, regardless of this config.

### Warning

Enable/disable warning when custom enchantments format is used.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>warning</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>true, false</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>true</td>
        </tr>
    </tbody>
</table>

When enabled, a warning message will appear in the chat each time you try to add a goal that:

* ... isn't registered on the client (e.g. a custom enchantment from a mod or plugin)
* ... isn't registered as traded by villagers
* ... has a level that is higher than the maximum registered level for that enchantment

More info about warnings for goals: [<kbd>Goals | Warnings</kbd>](/libr-getter/goals#warnings)

## Matching

### Fallback

Fallback option to search for unsupported enchantments.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>fallback</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>true, false</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>false</td>
        </tr>
    </tbody>
</table>

Takes all the data given about trades from the villager, convert it to text and search for all enchantment IDs
currently in the goals list. It is not guaranteed to make a match, and can cause false positives. May cause lag
with a large list of enchantments.

Use only as a temporary solution, if the plugin you are playing with is not supported by LibrGetter yet.
In this case, consider opening an issue [<kbd>here</kbd>](https://github.com/gXLg/libr-getter/issues)
with information about the plugin, so that it can be supported in future updates.

> <sup><b>💡 Tip</b></sup><br>
> Related topics:
> * Support for custom enchantments: [<kbd>Goals | Custom Enchantments</kbd>](/libr-getter/goals#custom-enchantments)
> * Compatibility with mods and plugins: [<kbd>Compatibility</kbd>](/libr-getter/compatibility)

### Matching Algorithm

#### Match Mode

Matching mode to determine when to stop the process of cycling trades and consider the attempt a success.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>matchMode</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>VANILLA, PERFECT, ATLEAST</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>VANILLA</td>
        </tr>
    </tbody>
</table>

* VANILLA - the first or the second trade matches the goals list
* PERFECT - all offered trades match the goals list
* ATLEAST - at least N trades must match the goals list, N is given by the `matchAtLeast` config

#### Match At Least

Number of unique trades which must match the goals list to consider the attempt a success.

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>matchAtLeast</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>Integers from 1</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>1</td>
        </tr>
        <tr>
            <td><b>Only Effective</b></td>
            <td>when <code>matchMode = ATLEAST</code></td>
        </tr>
    </tbody>
</table>

If `matchAtLeast` is greater than the amount of items in the goals list, LibrGetter will use that amount instead.

> <sup><b>⚠️ Warning</b></sup><br>
> The search time is expected to grow exponentially with increase of this config

#### Matching Explanation

The configs `matchMode` and `matchAtLeast` allow you to customize the matching algorithm used by LibrGetter to
determine when to stop the process of cycling trades and consider the attempt a success.

If you are running a vanilla setup, then only the VANILLA mode makes sense, since you can only see
exactly no or one enchantment trade. With server-side addition of mods such as
[<kbd>Visible Traders</kbd>](https://modrinth.com/mod/visible-traders) however, you will be able to potentially
see more than just a single enchantment. In this case, you can use the other two modes to make the process more
customizable and rewarding.

You'd use the PERFECT mode, whenever you want to get a perfect villager without any wasted trade offers.
This means, that all the offered trades have to match the goals list, and if there is even one trade that
doesn't match, the process will continue.

You'd use the ATLEAST mode, whenever you want to effectively find as many enchantments from your goals list as
possible, while tolerating wasted trade offers.

Some example scenarios:

* You'd like to get a villager with any matching offer, in other words, you want the villager to have at least
  one matching trade offer. Then you'd use `matchMode ATLEAST` and `matchAtLeast 1`
* You'd like to get a villager with his offers ideally covering the whole goals list at once.
  Then you'd use `matchMode ATLEAST` and `matchAtLeast N` where `N` is the amount of items in your goals list.

## Compatibility

This list of configs enables/disables compatibility with different client-side mods,
possibly modifying the process of finding an enchantment. All compatibility configs are disabled by default.
Compatibility configs are grayed out when the corresponding mod is not installed.
You can read more about what each mod changes in the compatibility documentation.

### Trade Cycling

[<kbd>Trade Cycling</kbd>](/libr-getter/compatibility#trade-cycling) integration

<table>
    <tbody>
        <tr>
            <td><b>Config</b></td>
            <td><code>tradeCycling</code></td>
        </tr>
        <tr>
            <td><b>Values</b></td>
            <td>true, false</td>
        </tr>
        <tr>
            <td><b>Default</b></td>
            <td>false</td>
        </tr>
        <tr>
            <td><b>Changing</b></td>
            <td>cannot be changed while running</td>
        </tr>
    </tbody>
</table>
