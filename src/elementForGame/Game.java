package elementForGame;

public class Game {
    private Bomb bomb;
    private Flag flag;
    private GameState state;
    private boolean firstStep;

    public Game(Bomb bombs) {
        bomb = bombs;
        flag = new Flag();
    }

    public GameState getState() {
        return state;
    }

    Game(int cols, int rows, int bombs) {
        FieldsFiller.setSize(new Coordinates(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    void start() {
        bomb.start();
        flag.start();
        firstStep = true;
        state = GameState.PLAYED;
    }


    Box getBox(Coordinates coord) {
        if (flag.get(coord) == Box.OPENED)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    public Bomb getBomb() {
        return bomb;
    }

    public Flag getFlag() {
        return flag;
    }

    private boolean isFirstStep() {                   //Первых ход
        return firstStep;
    }

    public void pressLeftButton(Coordinates coord) {
        if (gameOver()) return;
        if (isFirstStep()) {
            while (bomb.get(coord) == Box.BOMB)
                bomb.start();
            openBox(coord);
            firstStep = false;
        } else
            openBox(coord);
        checkWinner();
    }

    private void checkWinner() {                       //Проверка на победу
        if (state == GameState.PLAYED)
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs())
                state = GameState.WINNER;
    }

    private void openBox (Coordinates coord) {
        if(flag.get(coord) == null) return;
        switch (flag.get(coord)) {
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord); return;
            case FLAGED: return;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO: openBoxesAround(coord); return;
                    case BOMB: openBombs(coord); return;
                    default  : flag.setOpenedToBox(coord);
                }
        }

    }

    private void setOpenedToClosedBoxesAroundNumber(Coordinates coord) {
        if (bomb.get(coord) != Box.BOMB)
            if (flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
                for (Coordinates around : FieldsFiller.getCoordsAround(coord))
                    if (flag.get(around) == Box.CLOSED)
                        openBox(around);
    }


    private void openBombs(Coordinates bombed) {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coordinates coord : FieldsFiller.getAllCoords())
            if (bomb.get(coord) == Box.BOMB)
                flag.setOpenedToClosedBombBox(coord);
            else
                flag.setNobombToFlagedSafeBox(coord);
    }

    private void openBoxesAround(Coordinates coord) {
        flag.setOpenedToBox(coord);
        for (Coordinates around : FieldsFiller.getCoordsAround(coord))
            if(flag.get(around) != Box.OPENED)
                openBox(around);
    }

    public void pressRightButton(Coordinates coord) {
        if (gameOver()) return;
        flag.toggleFlagedToBox(coord);
    }

    private boolean gameOver() {
        switch (state) {
            case PLAYED:
                return false;
            case BOMBED:
                SweepeR.showRestartDialog("Вы проиграли");
                break;
            case WINNER:
                SweepeR.showRestartDialog("Вы выиграли!");
                break;
        }
        return true;
    }
}
