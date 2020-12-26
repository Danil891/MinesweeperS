package elementForGame;

public class Field {
    private Box[][] field;

    Field(Box defaultBox) {
        field = new Box[FieldsFiller.getSize().x][FieldsFiller.getSize().y];
        for (Coordinates coord : FieldsFiller.getAllCoords()) field[coord.x][coord.y] = defaultBox;
    }

    Box get(Coordinates coord) {
        if (FieldsFiller.inField(coord)) return field[coord.x][coord.y];
        return null;
    }

    void set(Coordinates coord, Box box) {
        if (FieldsFiller.inField(coord)) field[coord.x][coord.y] = box;
    }
}
