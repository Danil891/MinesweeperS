package elementForGame;

public class Bomb {
    private Field bombMap;
    private int totalBombs;

    Bomb (int totalBombs){
        this.totalBombs = totalBombs;
        bombMap = new Field(Box.ZERO);
    }

    public Bomb(int totalBombs, Field bombs) {
        this.totalBombs = totalBombs;
        bombMap = bombs;
    }

    void start (){
        bombMap = new Field(Box.ZERO);
        for (int j = 0; j < totalBombs; j ++)
            placeBomb();
    }

    Box get (Coordinates coord){
        return bombMap.get(coord);
    }

    public int bombsN(Coordinates coord){
        return get(coord).getNumber();
    }

    private void placeBomb(){
        while (true) {
            Coordinates coord = FieldsFiller.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord))
                continue;
            placeBombAt(coord);
            break;
        }
    }

    private void placeBombAt(Coordinates coord) {
        bombMap.set(coord, Box.BOMB);
        incNumbersAroundBomb(coord);
    }

    private void incNumbersAroundBomb (Coordinates coord){
        for (Coordinates around : FieldsFiller.getCoordsAround(coord))
            if (Box.BOMB != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
    }

    public int getTotalBombs() {              //Получить общее количество бомб
        return totalBombs;
    }

}
