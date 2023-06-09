# ChipDrop
AI Connect 4 bot designed to play like a human, written in Java

* Warning! ChipDrop is no longer maintained, and a Rust-based rewrite is being worked on.

![GitHub last commit](https://img.shields.io/github/last-commit/MrPiggyPegasus/ChipDrop?style=for-the-badge)

## Features
- Move generation from positions notated by a PGN string (concatenation of moves starting at 0)
- CLI system to play against the engine
- GUI for visual position analasys

## Usage
After compiling ChipDrop, run the .jar with no arguments to show the integrated GUI.
The "nogui" command will run the CLI version of the program.
```
java -jar ChipDrop.jar-1.0 nogui
```

## Compiling ChipDrop
ChipDrop is written in Java 19.0.2 with the OpenJDK and Maven; Compatability with other versions is not guaranteed.
On any Maven supported system, ChipDrop can be compiled using the command:
```
mvn package
```
in the root directory. The compiled jar will appear in /ChipDrop/target/
