# Some sort of shooter

*By Mohamed Ben El Mostapha, Clément Blavy, Anthony Fernandes and Yacob Zitouni*

## Report

[Link to Notion website containing the project's report.](https://fair-bottom-1ad.notion.site/Projet-de-Programmation-SR1a-af69d2673ded4220984fbb3651d71f32?pvs=4)

## Compile and run instruction

### Using make

You can only compile the game using `make` and then run the main class manually (see below).

To directly run the game with make, use `make run`.

### Manual compilation

For manual compilation, use the following commands in order:

```bash
# make the directory to store compiled classes
mkdir -p build

# compile
javac -d build --class-path src/main/java src/main/java/controller/MainController.java

# add resources
cp -r src/main/resources/* build

# run
cd build && java controller.MainController
```

## Controls

### In-game controls

Move around using <kbd>Z</kbd> <kbd>Q</kbd> <kbd>S</kbd> <kbd>D</kbd> (sorry if you have a QWERTY keyboard).

Use <kbd>right click</kbd> to shoot and <kbd>left click</kbd> to dash.

Press <kbd>E</kbd> to pick up a weapon, <kbd>A</kbd> to switch between the two held weapons and <kbd>esc</kbd> to end the game (instant game over).

### Editor controls

Use the two combo-box on top of the menu to select the size of the map.

Use <kbd>right click</kbd> on a tile to get the next tile type and <kbd>left click</kbd> to get the previous one.

Tiles types organised are in the following order:
1. Standard
2. Water
3. Void
4. Breakable barrier
5. Safe

A valid map must contain exactly one spawn point, use <kbd>middle click</kbd> to set it.

*See map [format documentation](docs/mapFormat.md) for more details.*

### Scenario creation
Custom scenarios may be made in the form of XML files. They can then be loaded in the game by unchecking the "Marathon Mode"
checkbox and loading the custom scenario. More information on the format of these files can be found in the [scenario format documentation](docs/scenarioFormat.md).