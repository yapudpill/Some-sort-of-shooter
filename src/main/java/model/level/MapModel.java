package model.level;


import model.level.Tiles.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapModel {
    private TileModel[][] tiles;

    public MapModel(String path){
        tiles = loadMap(path);
    }

    /**
     * the tiles attribute can be loaded from a .txt file in map
     * about the .txt map format
     * here is an example of what a map could look like
     * <pre>
     * +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
     * |         #                                                                     |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #                                                       b   b         |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #       r                                               b   b         |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #               #   #   #   #                               b         |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #               #   #           s   s                                 |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #   #   #   #   #           s   s                                     |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                             #   #   #   #   #   #   #         |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                         #   #                                 |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         /   /   /                       #   #                                 |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         /   /   /                       #                   r                 |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         /   /                                                                 |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
     * </pre>
     * the first and last lines and the first and last column are the limits of the map
     * the different tiles are separated by + at every corner
     * in this file, the characters are :
     * <ul>
     *      <li> '+' is at every corner, it's used to show the grid  </li>
     *      <li> '---' is a horizontal border of the map </li>
     *      <li> '|' (pipe) is a vertical border of the map </li>
     *      <li> ' ' indicates the default empty tile (no effect + walkable) </li>
     *      <li> '#' indicates the default wall tile (no effect + non walkable) </li>
     *      <li> 'b', '/', 'r' and 's' are just for the show, no actual meaning for now </li>
     * </ul>
     */

    /**
     *
     * @param lines String corresponding to each line of the .txt
     * @return array containing the character corresponding to the content of each tile
     * parseMap ignores everything except the center of each square, so it ignores 1/2 lines and 3/4 columns
     */
    public static char[][] parseMap(String[] lines){
        int height = lines.length/2;
        int width = (lines[0].length())/4;
        String current;
        char[][] arr = new char[height][width];
        for (int i = 0; i < height; i++) {
            current = lines[i*2+1];
            for (int j = 0; j < width; j++) {
                arr[i][j] = current.charAt(j*4+2);
            }
        }
        return arr;
    }

    /**
     *
     * @param path is the path of the map.txt (located in the maps directory in the model.level package of resources)
     * @return a TileModel[][] containing the tiles corresponding to the loaded map.txt
     */
    public static TileModel[][] loadMap(String path){
        InputStream in = MapModel.class.getResourceAsStream(path);
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String[] tab = reader.lines().toArray(String[]::new);
            char[][] parsedMap = parseMap(tab);

            TileModel[][] tiles = new TileModel[parsedMap.length][parsedMap[0].length];
            for (int i = 0; i < parsedMap.length; i++) {
                for (int j = 0; j < parsedMap[0].length; j++) {
                    switch (parsedMap[i][j]){
                        case '#' -> tiles[i][j] = new WallTileModel();
                        default -> tiles[i][j] = new DefaultTileModel();
                    }
                }
            }
            return tiles;
        }
        catch (NullPointerException e){
            System.out.println("invalid path");
        }
        return null;
    }
/*
    may be useful for tests

    public static void display(char[][] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    public static void display(String[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
 */
}