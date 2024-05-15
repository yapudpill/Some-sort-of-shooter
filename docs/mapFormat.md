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
|         #               #   #                                                 |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         #   #   #   #   #               S                                     |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                             #   #   #   #   #   #   #         |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                         #   #                                 |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         /   /   /                       #   #                                 |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         /   /   /                       #                   r                 |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         /   /                                                                 |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                                               |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                                               |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                                 V   V   V   V |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                                 V           V |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                                             V |
+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
```

The first and last lines and the first and last column are the limits of the map
the different tiles are separated by `+` at every corner.

Meaning of the characters:
| character   | meaning                                       | can enter                    |
| ----------- | --------------------------------------------- | ---------------------------- |
| `+`         | corner of the grid                            |                              |
| `---`       | horizontal border of the map                  |                              |
| `\|`        | vertical border of the map                    |                              |
|             |                                               |                              |
| ` ` (space) | default empty tile                            | all entities                 |
| `#`         | water tile                                    | bullets only                 |
| `V`         | void tile                                     | nothing                      |
| `S`         | spawn tile, one by map, only player can enter | player only                  |
| `/`         | default tile with breakable barrier           | nothing then all when broken |
| `b`, `r`    | does nothing, just for the show               |                              |

*Note that only the character in the center of each square is read by the
program, all separators around squares are actually optionals.*