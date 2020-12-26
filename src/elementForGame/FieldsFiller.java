package elementForGame;

import java.util.ArrayList;
import java.util.Random;

public class FieldsFiller {

    private static Coordinates size;
    private static ArrayList<Coordinates> allCoords;
    private static Random random = new Random();

    public static void setSize(Coordinates _size) {
        size = _size;
        allCoords = new ArrayList<>();
        for (int y = 0 ; y < size.y; y++)
            for (int x = 0; x < size.x; x++)
                allCoords.add(new Coordinates(x, y));
    }

    public static Coordinates getSize() {
        return size;
    }

    public static ArrayList<Coordinates> getAllCoords() {
        return allCoords;
    }

    static boolean inField(Coordinates coord){
        return coord.x >= 0 && coord.x < size.x &&
                coord.y >= 0 && coord.y < size.y;
    }

    public static Coordinates getRandomCoord() {
        return new Coordinates(random.nextInt(size.x),random.nextInt(size.y));
    }

    public static ArrayList<Coordinates> getCoordsAround(Coordinates coord){
        Coordinates around;
        ArrayList<Coordinates> list = new ArrayList<>();
        for (int x = coord.x - 1; x <= coord.x + 1; x++)
            for (int y = coord.y - 1; y <= coord.y + 1; y++)
                if (inField(around = new Coordinates(x,y)))
                    if (!around.equals(coord))
                        list.add(around);
        return list;
    }
}
