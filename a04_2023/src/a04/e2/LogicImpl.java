package a04.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicImpl implements Logic {

    private final int width;
    private final List<Position> markedCells = new ArrayList<>();
    private final Position start;

    public LogicImpl(int width) {
        this.width = width;
        Random r = new Random();
        start = new Position(r.nextInt(width), 0);
        markedCells.add(start);
    }

    @Override
    public Position start() {
        return start;
    }

    @Override
    public List<Position> hit(Position hit) {
        if (hit.Y() != 0) {
            markedCells.add(hit);
        }
        return new ArrayList<>(markedCells);
    }

    @Override
    public boolean isOver() {
        int i = 1;
        int j = start.X();
        while (i != width) {
            if (!markedCells.contains(new Position(j - 1, i)) && !markedCells.contains(new Position(j + 1, i))) {
                return false;
            } else {
                if (markedCells.contains(new Position(j - 1, i))) {
                    j--;
                } else {
                    j++;
                }
            }
            i++;
        }
        return true;
    }

}
