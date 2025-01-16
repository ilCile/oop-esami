package a03a.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicImpl implements Logic {

    private final int width;
    private final int height;
    private Position target;
    private Position arrived;

    public LogicImpl(int width, int height) {
        this.height = height;
        this.width = width;
        Random ran = new Random();
        target = new Position(width - 1,ran.nextInt(height));
    }

    @Override
    public Position start() {
        return target;
    }

    @Override
    public List<Position> hit(Position hit) {
        List<Position> markedCells = new ArrayList<>();
        boolean goingUp;
        int i = hit.Y();
        int j = hit.X();
        if (i != 0) {
            goingUp = true;
        } else {
            goingUp = false;
        }
        while (j != width) {
            markedCells.add(new Position(j, i));
            j++;
            if (goingUp) {
                i--;
            } else {
                i++;      
            }
            if (i == 0) {
                goingUp = false;
            }
            if (i == height - 1) {
                goingUp = true;
            }
        }
        arrived = new Position(width - 1, markedCells.getLast().Y());
        return new ArrayList<>(markedCells);
    }

    @Override
    public boolean isOver() {
        return arrived.equals(target);
    }

}
