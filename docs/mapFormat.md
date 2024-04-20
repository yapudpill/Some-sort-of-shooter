Maps are loaded via the `MapSelector` class that reads either internal maps
located in resources/model/level/maps, or external maps created by the user and
located on the disk.

Here is an example of what a map file could look like.

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
|         #               #   #           s   s                                 |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|         #   #   #   #   #           s   s                                     |
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
|                                                                               |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                                               |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                                               |
+   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
|                                                                               |
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
- `+` is at every corner, it's used to show the grid
- `---` is a horizontal border of the map
- `|` (pipe) is a vertical border of the map
- ` ` (space) indicates the default empty tile (no effect + all entity can enter)
- `#` indicates the water tile (no effect + only bullets can enter)
- `S` indicates the player's spawn point
- `b`, `/`, `r` and `s` are just for the show, no actual meaning for now

*Note that only the character in the center of each square is read by the
program, all separators around squares are actually optionals.*