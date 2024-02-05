package model.level;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MapModel {
    private static TileModel[][] tiles;


    /**
     * the tiles attribute can be loaded from a .txt file in map
     * about the .txt map format
     * here is an example of what a map could look like
     * <pre>
     * +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
     * |         #                                                                 |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #                                                       b   b     |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #       r                                               b   b     |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #               #   #   #   #                               b     |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #               #   #           s   s                             |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #   #   #   #   #           s   s                                 |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                             #   #   #   #   #   #   #     |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                         #   #                             |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         /   /   /                       #   #                             |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         /   /   /                       #                   r             |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         /   /                                                             |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                           |
     * +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
     * </pre>
     * the first and last lines and the first and last column are the limits of the map
     * the different tiles are separated by + at every corner
     * in this file, the characters are :
     * <ul>
     *      <li> '+' is at every corner, it's used to show the grid  </li>
     *      <li> '---' is a horizontal border of the map </li>
     *      <li> '|' (pipe) is a vertical border of the map </li>
     *      <li> '.' indicates the default empty tile (no effect + walkable) </li>
     *      <li> '#' indicates the default wall tile (no effect + non walkable) </li>
     *      <li> 'b', '/', 'r' and 's' are just for the show, no actual meaning for now </li>
     * </ul>
     */


    public static String[][] parseMap(List<String> lines){
        int height = lines.size();
        int width = 2 * ((lines.get(0).length()-1)/4) + 1;
        String[][] arr = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

            }
        }
        return arr;
    }
    public static void loadMap(String path) throws IOException {
        try {
            InputStream file = Objects.requireNonNull(MapModel.class.getResourceAsStream(path));
            byte[] buffer = file.readAllBytes();
            String stringData = new String(buffer, StandardCharsets.UTF_8);
            List<String> list = Arrays.asList(stringData.split("\n"));
            parseMap(list);
        }
        catch ( NullPointerException e) { e.printStackTrace(); }
        
    }


}