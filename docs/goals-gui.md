# Goals (GUI)

<sup>[<kbd>← Back to Starting Page</kbd>](/libr-getter)</sup>

Goals in LibrGetter represent the enchanting books that you want to obtain from
librarian villagers. Each goal consists of an enchantment ID (e.g. `minecraft:efficiency`),
a level (e.g. `5`), and the price limit in emeralds (e.g. `32`). Interactions with goals
list described on this page happen through an in-game GUI.

Please also read the [<kbd>Goals</kbd>](/libr-getter/goals) page to understand how goals work
and what the differences between Minecraft enchantments and custom enchantments are.

> <sup><b>⚠️ Warning</b></sup><br>
> The GUI is fully supported in newer versions of Minecraft, but it may have
> minor placement and scaling issues in older versions. It is not recommended
> to resize the game window while the GUI is open, as it may cause some visual glitches
> on older versions. To fix such issues, simply close the GUI and open it again.

# Goals List Screen

<div align="center">
    <img width="70%" src="/libr-getter/images/gui-goal-list.png" alt="GUI for the list of goals">
</div>

You can open the goals list screen by pressing <kbd>,</kbd> (comma) key in the game.
This screen allows you to view all your goals, add new goals and delete goals.
The keybind may be not present on your keyboard, and it is recommended to change it
in the "Controls" menu.

After opening the goals list screen, you will see all the goals that you have previously added.
You can scroll through the list and navigate it with both mouse and keyboard.

To delete an entry, select it with the mouse and press the <kbd>Delete</kbd> key on your keyboard.

To add a new entry, click the <kbd>Add new goal</kbd> button at the bottom of the screen.
This will open a new "Select Enchantment" screen.

To save the changes, simply close the screen by either clicking the <kbd>Done</kbd> button at
the bottom of the screen or by pressing the <kbd>Esc</kbd> key on your keyboard.

# Select Enchantment Screen

<div align="center">
    <img width="70%" src="/libr-getter/images/gui-enchantment-selection.png" alt="GUI for the selection of the enchantment">
</div>

After opening this screen, you will see a searchable list of all enchantments registered
on the client. You can scroll through the list and navigate it with both mouse and keyboard.

To find the needed enchantment, focus on the search field at the top of the screen and type
the search query. The list will be filtered by comparing your query to both enchantment IDs
and enchantment names.

To select an enchantment, select it from the list and click the <kbd>Select</kbd> button at
the bottom of the screen.

To add a custom enchantment, click on the <kbd>Add custom...</kbd> button.

To cancel, close the screen by pressing <kbd>Esc</kbd>.
You will return to the goals list screen without adding a new goal.

# Add Enchantment Screen

In this screen you will see three labeled fields:

* Enchantment
* Level
* Price

If you selected a Minecraft enchantment in the previous screen:

* The "Enchantment" field will be filled with the selected enchantment and become not-editable
* The "Level" field will have a hint with the maximum level of the selected enchantment registered on the client,
  but it will be editable, allowing you to enter a custom level if needed

If you clicked on the <kbd>Add custom...</kbd> button:

* The "Enchantment" field will be empty and editable, allowing you to enter a custom enchantment ID
* The "Level" field will be empty and editable, making it mandatory to enter a custom level

The price field always have a hint with the maximum price limit of 64 emeralds (in Vanilla),
but it is editable, allowing you to enter a different price limit if needed.

> <sup><b>ℹ️ Note</b></sup><br>
> Compared to the command interface, you shouldn't use quotes for the custom
> enchantments in this GUI, just type the ID as it is, e.g. `modid:enchantment_name`

To save the new goal, click the <kbd>Add</kbd> button at the bottom of the screen.

To cancel, close the screen by pressing <kbd>Esc</kbd>.
You will return to the "Select Enchantment" screen without adding a new goal.