Maps are loaded via the `MapSelector` class that reads either internal maps
located in resources/model/level/maps, or external maps created by the user and
located on the disk.

Here is an example of what a map file could look like:

```
+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
|         #                                                                     |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         #                                                       b   b         |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         #       r                                               b   b         |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         #               #   #   #   #                               b         |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         #               #   #           /   /                                 |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         #   #   #   #   #           /   /                                     |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                             #   #   #   #   #   #   #         |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                         #   #                                 |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         V   V   V                       #   #                                 |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         V   V   V                       #                   r                 |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         V   V                                                                 |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                                               |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                         s   s   s             |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                         s   S   s             |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                         s   s   s             |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                                               |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                                               |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                                               |
+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
```

The first and last lines and the first and last column are the limits of the map
the different tiles are separated by `+` at every corner.

Meaning of the characters:
| character   | meaning                                       | can enter        |
| ----------- | --------------------------------------------- | ---------------- |
| `+`         | corner of the grid                            |                  |
| `---`       | horizontal border of the map                  |                  |
| `\|`        | vertical border of the map                    |                  |
|             |                                               |                  |
| ` ` (space) | default empty tile                            | all entities     |
| `/`         | default empty tile with breakable barrier     | nothing then all |
| `#`         | water tile                                    | bullets only     |
| `V`         | void tile                                     | nothing          |
| `s`         | safe tile                                     | player only      |
| `S`         | safe tile where the player spawns, one by map | player only      |
| `b`, `r`    | does nothing, just for the show               |                  |

*Note that only the character in the center of each square is read by the
program, all separators around squares are actually optionals.*