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

<img width="200px" src="/libr-getter/images/config.jpeg" alt="Managing configs with a command">

All config options can be managed in-game with the command `/librget config`.

* To see the currently set value for a config option, simply
  type `/librget config <option_name>`
* To change the value of a config option,
  type `/librget config <option_name> <new_value>`

Note, that some configs can be changed while the process is running, while some others can't
and require you to stop the process before changing them.

# Config GUI

<img width="200px" src="/libr-getter/images/gui.jpeg" alt="Managing configs with the GUI">

The preferred way of managing configs is through the in-game GUI. The GUI provides a user-friendly
interface for changing config options, seeing the currently set values and get visual feedback on
what each config option does. To open the config GUI, use the keybind <kbd>K</kbd>.

The GUI looks like ...

On the first page ...