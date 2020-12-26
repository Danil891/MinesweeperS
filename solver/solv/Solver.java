package solv;

import elementForGame.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Solver {
    private Game state;
    private Bomb bombs;
    private Flag flags;
    private CoordAction action;

    public Solver(CoordAction action){
        this.action = action;
    }

    public interface CoordAction {
        void performCoordAction(Coordinates coord);
    }

    public void update(Game newstate){
        assert newstate != null;
        state = newstate;
        bombs = newstate.getBomb();
        flags = newstate.getFlag();
    }

    private List<MineGroups> genGroups() {
        List<MineGroups> groups = new ArrayList<>();
        for (Coordinates coord : FieldsFiller.getAllCoords()) {
            if (flags.get(coord) == Box.OPENED) {
                int number = bombs.bombsN(coord) - countCellsAround(coord);
                if (number == 0) continue;
                MineGroups group = new MineGroups(number);
                Set<Coordinates> coords = group.group;
                for (Coordinates around : FieldsFiller.getCoordsAround(coord)) {
                    if (flags.get(around) == Box.CLOSED) {
                        coords.add(around);
                    }
                }
                groups.add(group);
            }
        }
        boolean changes;
        do {
            changes = false;
            for (int i = 0; i < groups.size(); i++) {                // Проходим по списку групп
                MineGroups groupI = groups.get(i);
                for (int j = i + 1; j < groups.size(); j++) {        // Сравниваем
                    MineGroups groupJ = groups.get(j);
                    if (groupI.equals(groupJ)) {
                        groups.remove(j--);
                        continue;
                    }
                    MineGroups bigGroup;                               // Большая группа
                    MineGroups smallGroup;                                // Меньшая группа
                    if (groupI.group.size() > groupJ.group.size()) {
                        bigGroup = groupI;
                        smallGroup = groupJ;
                    } else {
                        bigGroup = groupJ;
                        smallGroup = groupI;
                    }
                    if (bigGroup.contains(smallGroup)) {                    // Вычитаем из большей меньшую
                        bigGroup.minusAssign(smallGroup);
                        changes = true;                              // Фиксируем факт изменения групп
                    }

                }
            }
        } while (changes);  // Повторяем до тех пор, пока не будет производиться никаких изменений
        return groups;
    }

    private boolean logicalMove(List<MineGroups> groups) {
        for (MineGroups group : groups) {
            if (group.minesAround == 0) {                            // Количество мин равно нулю
                openBox(group.group.iterator().next());
                return true;
            } else if (group.minesAround == group.group.size()) {    // Количество мин равно количеству ячеек в группе
                flagBox(group.group.iterator().next());
                return true;
            }
        }
        return false;
    }


    public Moves step() {

        Coordinates size = FieldsFiller.getSize();

        if (flags.getCountOfClosedBoxes() == size.getX() * size.getY()){
            openBox(FieldsFiller.getRandomCoord());
            return Moves.First;
        }
        List<MineGroups> groups = genGroups();
        if (logicalMove(groups)) {
            return Moves.Logical;
        } else randomMove();
        return Moves.Random;
    }

    private void openBox(Coordinates coord){
        action.performCoordAction(coord);
        state.pressLeftButton(coord);
    }

    private void flagBox(Coordinates coord){
        action.performCoordAction(coord);
        state.pressRightButton(coord);
        for (Coordinates place : FieldsFiller.getCoordsAround(coord)){
            if (flags.get(place) != Box.CLOSED && state.getState() == GameState.PLAYED)
                state.pressLeftButton(place);
        }
    }

    private int countCellsAround(Coordinates coord){
        int n = 0;
        for (Coordinates place : FieldsFiller.getCoordsAround(coord)){
            if (flags.get(place) == Box.FLAGED)
                n++;
        }
        return n;
    }

    private void randomMove(){
        Optional<Coordinates> move = FieldsFiller.getAllCoords()
                .stream()
                .filter(coord -> flags.get(coord) == Box.CLOSED)
                .findFirst();
        if (move.isPresent())
            openBox(move.get());
        else
            throw new IllegalStateException("There must be at least 1 closed cell");
    }

}
