# For Developers

<sup>[<kbd>← Back to Starting Page</kbd>](/libr-getter)</sup>

This page is intended for developers who want to contribute to the development of LibrGetter,
or just want to understand how it works under the hood. It contains information about the architecture
of the mod, how to set up the development environment and how to contribute.

> <sup><b>⚠️ Warning</b></sup><br>
> This documentation is still **work in progress**.
> Stuff may change without notice, and some of the features may not be documented yet.

# Build

You can build the project yourself.
For this just clone the repository and run `./gradlew build`.
The compiled mod jar can be found under `build/libs/`.

Note, that you'll need `Node.js` installed on your system,
to be able to run [<kbd>Version't</kbd>](https://github.com/gXLg/versiont-toolchain).

# Code Style

LibrGetter follows a code style based on the default IntelliJ code style for Java, with some adjustments
in favor of readability and personal preferences. The code style settings are included in the repository,
under the path `styles/IntelliJ_IDEA.xml`. You can import this file to your IDE to automatically apply
the code style settings.

If your IDE does not support IntelliJ style files, you can also auto-format the code using a headless IntelliJ
installation. This section will be added in the future, but for now, you could look into how to do that by
following the CI/CD action for [<kbd>IntelliJ formatting</kbd>](https://github.com/gXLg/intellij-idea-format).

# Internal Architecture

## Task Flow

<div align="center">
    <img width="70%" src="/libr-getter/images/taskflow.png" alt="Flow of LibrGetter tasks"/>
</div>
