package solv;

import elementForGame.Coordinates;

import java.util.HashSet;
import java.util.Set;


public class MineGroups{
    int minesAround;
    Set<Coordinates> group;

    MineGroups(int minesAround) {
        this.minesAround = minesAround;
        group = new HashSet<>();
    }

    void minusAssign(MineGroups other) {
        minesAround -= other.minesAround;
        group.removeAll(other.group);
    }

    boolean contains(MineGroups other) {
        return group.containsAll(other.group);
    }


    @Override
    public boolean equals(Object other){
        if (other instanceof MineGroups) {
            final MineGroups otherGroup = (MineGroups) other;
            boolean eg = group.equals(otherGroup.group);
            if (eg && minesAround != otherGroup.minesAround) {
                minesAround = Math.max(minesAround, otherGroup.minesAround);
            }
            return minesAround == otherGroup.minesAround && eg;
        }
        return false;
    }

}
