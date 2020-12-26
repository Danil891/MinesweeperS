package elementForGame;

public class Flag {

    private Field flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Field(Box.CLOSED);
        countOfClosedBoxes = FieldsFiller.getSize().x * FieldsFiller.getSize().y;
    }

    public Box get(Coordinates coord) {
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coordinates coord) {
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes--;
    }

    void toggleFlagedToBox(Coordinates coord) {
        switch (flagMap.get(coord)){
            case FLAGED: setClosedToBox(coord); break;
            case CLOSED: setFlagedToBox(coord); break;

        }
    }

    private void setClosedToBox(Coordinates coord) {
        flagMap.set(coord, Box.CLOSED);
    }

    private void setFlagedToBox(Coordinates coord) {
        flagMap.set(coord, Box.FLAGED);
    }

    public int getCountOfClosedBoxes() {                           //Получить количество закрытых клеток
        return countOfClosedBoxes;
    }

    void setBombedToBox(Coordinates coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    void setOpenedToClosedBombBox(Coordinates coord) {
        if (flagMap.get(coord) == Box.CLOSED)
            flagMap.set(coord, Box.OPENED);
    }

    void setNobombToFlagedSafeBox(Coordinates coord) {
        if (flagMap.get(coord) == Box.FLAGED)
            flagMap.set(coord, Box.NOBOMB);
    }

    int getCountOfFlagedBoxesAround(Coordinates coord) {
        int count = 0;
        for (Coordinates around : FieldsFiller.getCoordsAround(coord))
            if (flagMap.get(around) == Box.FLAGED)
                count++;
        return count;
    }
}
