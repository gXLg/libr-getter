[<kbd>Back to Starting Page</kbd>](/libr-getter)

# Config

The config is the core of LibrGetter's flexibility. It allows you to customize various aspects
of mod's behavior, such as modifying the process, adjusting the messages displayed in the
chat and much more. The config is stored in a file named `librgetter.json` located in the `config`
folder of your Minecraft directory. You can edit this file manually with any text editor, but make
sure to follow the correct JSON format and to edit the file while Minecraft is closed to avoid any
issues. However, editing config manually is rarely needed, as most of the config options can be changed
in-game with commands and a handy config GUI.

# Managing Configs With Commands

<div align="center">
    <img width="70%" src="/libr-getter/images/config.jpeg" alt="Managing configs with a command">
</div>


All config options can be managed in-game with the command `/librget config`.

* To see the currently set value for a config option, simply
  type `/librget config <option_name>`
* To change the value of a config option,
  type `/librget config <option_name> <new_value>`

Note, that some configs can be changed while the process is running, while some others can't
and require you to stop the process before changing them.

# Config GUI

<div align="center">
    <img width="70%" src="/libr-getter/images/gui.jpeg" alt="Managing configs with the GUI">
</div>

The preferred way of managing configs is through the in-game GUI. The GUI provides a user-friendly
interface for changing config options, seeing the currently set values and get visual feedback on
what each config option does. To open the config GUI, use the keybind <kbd>K</kbd>.

The GUI looks like a vanilla Minecraft written book. Each page of the book contains a different
config option, with its name, description, current value and buttons which you can click to
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

You can click on the toggles to change the value. If the config is not default, the reset
button <kbd>↩</kbd> will appear next to the name, which you can click to reset the config
to its default value.

In some cases, the config option will be grayed out. This means that this config option currently
is not effective, and changing its value won't have any effect until you change some other
config option. You can still hover over it to see the tooltip and can even change the value.