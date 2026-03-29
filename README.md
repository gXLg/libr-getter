<!--suppress HtmlDeprecatedAttribute-->
<img width="100%" src="https://raw.githubusercontent.com/gXLg/libr-getter/master/docs/images/cover.png" alt="LibrGetter by gXLg - cover art"/><br>

# Over-Engineered Librarian Trade Finder for Fabric

A highly configurable fabric mod to automatically cycle a librarian villager for the desired enchantment by replacing his lectern

<div align="center">
    <table>
        <tbody>
            <tr>
                <td></td>
                <td>
                    <div align="center">
                        <br>
                        Works in singleplayer and on servers, and has to be installed only on the client
                        <br>
                        <br>
                        <img alt="Client-side badge" src="https://img.shields.io/endpoint?url=https%3A%2F%2Fgxlg.github.io%2Fbadges%2Fclientside.json" align="center">
                        <br>&nbsp;
                    </div>
                </td>
                <td>
                    <div align="center">
                        <br>
                        All Minecraft versions from <code>1.17</code> up to <code>26.1</code> - with a single JAR file
                        <br>
                        <br>
                        <a href="https://github.com/gXLg/versiont-lib"><img alt="Version't badge" src="https://img.shields.io/endpoint?url=https%3A%2F%2Fgxlg.github.io%2Fbadges%2Fversiont%2Flibrgetter.json" align="center"></a>
                        <br>&nbsp;
                    </div>
                </td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <div align="center">
                        <br>
                        Active community and regular updates
                        <br>
                        <br>
                        <a href="https://discord.gg/aYBrWe5Jtt"><img alt="Discord" src="https://img.shields.io/discord/1401376830916788306?style=flat-square&logo=discord&logoColor=black&label=gXLg%20Lounge&labelColor=orange&color=black" align="center"></a>
                        <a href="https://github.com/gXLg/libr-getter/issues"><img alt="Issues" src="https://img.shields.io/github/issues-search?query=repo%3AgXLg%2Flibr-getter%20is%3Aissue%20state%3Aclosed&style=flat-square&logo=github&logoColor=fafbfc&label=Issues%20closed&labelColor=24292d&color=fafbfc" align="center"></a>
                        <br>&nbsp;
                    </div>
                </td>
                <td>
                    <div align="center">
                        <br>
                        Support me by downloading from Modrinth or making a small donation
                        <br>
                        <br>
                        <a href="https://modrinth.com/mod/libr-getter"><img alt="Modrinth badge" src="https://img.shields.io/badge/dynamic/json?labelColor=black&color=565&label=Modrinth&suffix=%20downloads&query=downloads&url=https://api.modrinth.com/v2/project/6Ts2vJ13&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAAIGNIUk0AAHomAACAhAAA+gAAAIDoAAB1MAAA6mAAADqYAAAXcJy6UTwAAAJPUExURQAAABvZahWnUha1WAYzGQlHIxvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZahvZav///9ScwmYAAADDdFJOUwAAAAAAAA8zW3uOYwIBK3rB6Pn+ml18KiGL5HEDquOIH07R/UzKz2zu+uLHIibtafWkVCMNBRqg7/RQuT8EQbvT+5ETDBSU/NAgCSdZlcQKii7mtxJY5fF/7D1SRkB+EcWh4UilOOtPMdTCR1PqN969vmGDCw7G4DSSsAcGHrSPr3bds5CEwDKoXumcZdwcG4KmjayX32A79pjOqRjIFoXynVYVgPi6qxDnL78p2obVJFquNbGZCPdyvHPZ1yhuh8s+iRzcsrEAAAABYktHRMQUDBvhAAAAB3RJTUUH5wQXDwgZWDUtiQAAAqRJREFUOMttU/k7lFEU/k4L4hsxtNAnhRgiJZOiSfbI2oJpmRFCi2kV0aaNVLTIEpVISmnf97r/WO+534yZnqfzw73vOe+559xz7rmKIoVYvLx95vj6qUSqVBW3sGrwnxsQaAwSwcDz5i9g0wxPfmFIqJCyiEgLE4vDl7iD8PGlETotIqOQahlAdJTLA5sWY5JsbNzysHiihEBWViRqRDOlgyFmJVtMq5JWJ5vhvyZlLevrEmUILKl8PihtvYWcYt6QLmNwFjjEb4SSkZnlZLO5yJzcPBg35bODwZfPZxYwWbC5sKjYzwBUkssxSg1wKNsCtHUb89vTy3GwopBxDmcJ9YdDJYB1B9t2WmUtcbtkqt18Mxsp9irsezS2VEu+uka/mqUWSrFd2VsnhLEetoZ9TFv3HwCexdUdjBWi0aH4YTuEvtoPM3/EO1nnuTpEjD2qHIM54DhR0wmA5hZySzIntyknsbZyi8IB0tp07tRpBDKfgeWsdDjHDlFGRIjnl3O0t573gcMFUBeVS1gvlxB1dOZxv0pIu9IF01UDZRdjr1SuZQhxvbvnRrmsMePmrdsrGPRaKP8OLtmn9KM7A4N3xb8yNExUb5RlZt2btpZbXajzPoYkBaDKrtADp3HkYdloo56ndgx37UcHxSP0tIWBGH9cg1nx6TJFTNhS7eCfcA1PJ+GgDbLDs2GuX3V05Ohj0xYSBGumyq/yfApw4EWTRxPJqxQvIF6+ks9Gr0d4nN+8Nbhoi+NdJEwVSc6hVNvHOUvg+5qeDxoP3GQz6x8/qXLuZ6Op7SPy+gNTnydSELabk1Z8Mbs/hlr4dbobfUgxJMS3UdXza9H33jqnA3/OH41FCZ7/l7HW8vOXiWc9GvPf06D953/T2O/EP8HBNtcH0Zm/lqFNUgTAex4AAAAldEVYdGRhdGU6Y3JlYXRlADIwMjMtMDQtMjNUMTU6MDg6MjQrMDA6MDAE5dOaAAAAJXRFWHRkYXRlOm1vZGlmeQAyMDIzLTA0LTIzVDE1OjA4OjI0KzAwOjAwdbhrJgAAACh0RVh0ZGF0ZTp0aW1lc3RhbXAAMjAyMy0wNC0yM1QxNTowODoyNSswMDowMITaQU0AAAAASUVORK5CYII=&style=flat-square" align="center"></a>
                        <a href="https://www.paypal.com/donate?hosted_button_id=DVC2UQP2AXR68"><img alt="PayPal" src="https://img.shields.io/endpoint?url=https%3A%2F%2FgXLg.github.io%2Fbadges%2Fdonate.json" align="center"></a>
                        <br>&nbsp;
                    </div>
                </td>
                <td></td>
            </tr>
        </tbody>
    </table>
</div>


# Features

* Fully automatic and highly efficient
* Compatible with many other client-side mods for librarian villagers
* Very customizable process with many configuration options
* Stable against multiple lag types
* Supports enchantments tags up from `1.19.3`

# Installation

Download LibrGetter from the [<kbd>Modrinth page</kbd>](https://modrinth.com/mod/libr-getter)
and place the downloaded JAR file in your Minecraft `mods` folder.

Make sure, you also have the following dependencies installed:
* [<kbd>Fabric API</kbd>](https://modrinth.com/mod/fabric-api) - required for communication between the mod and the game
* [<kbd>Version't Library</kbd>](https://modrinth.com/mod/versiont) - required for supporting multiple Minecraft versions with a single JAR file

# Quick Guide

1. Add enchantments to the goals list using `/librget add <enchantment> [level] [price]`
2. Change configs to your liking using the config GUI, open it by pressing <kbd>K</kbd>
3. Stand close to the librarian and his lectern and press <kbd>J</kbd> to start the process
4. Stop the process by pressing <kbd>J</kbd> again
5. ... or wait until the desired enchantment is found
6. Enjoy your new enchantment!

# Documentation

A more detailed explanation can be found in the [<kbd>documentation</kbd>](https://gXLg.github.io/libr-getter).

# Localization

LibrGetter supports localization and currently implements the following languages:

* English 🇺🇸
* German 🇩🇪
* Russian 🇷🇺
* Vietnamese 🇻🇳 (thanks [@ToanAnh312](https://github.com/ToanAnh312))
* Japanese 🇯🇵 (thanks [@H1ggsK](https://github.com/H1ggsK))
* Spanish 🇪🇸

You may request further languages or suggest improvements in the [<kbd>issues</kbd>](https://github.com/gXLg/libr-getter/issues) or
the [<kbd>Discord Community</kbd>](https://discord.gg/aYBrWe5Jtt)

# About Me

I am a computer science student in Germany and have a part-time job at a tech company.
Apart from that, I enjoy my free time by spending it with friends, chatting online or gaming.

If you want to keep this project alive, found it helpful or just want to support and motivate me to go on,
you could consider making a small [<kbd>☕ donation</kbd>](https://www.paypal.com/donate?hosted_button_id=DVC2UQP2AXR68).
