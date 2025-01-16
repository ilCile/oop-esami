package a01b.e2;

import java.util.List;
import java.util.ArrayList;

public class LogicsImpl implements Logic {

    private List<Pair<Position, Integer>> cells = new ArrayList<>();
    private int counter = 1;
    private final int size;
    private boolean moveLeft = true;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public List<Pair<Position, Integer>> hit(Position hit) {
        if (cells.size() != 5) {
            cells.add(new Pair<Position,Integer>(hit, counter));
            counter++;
            return new ArrayList<>(cells);
        }
        if (moveLeft) {
            move(-1);
            if (cells.stream().anyMatch(pair -> pair.getX().x() == 0)) {
                moveLeft = false;
            }
        } else {
            move(1);
        }
        return new ArrayList<>(cells);
    }

    @Override
    public boolean timeToMove() {
        return cells.size() == 5;
    }

    @Override
    public boolean isOver() {
        return cells.stream().anyMatch(pair -> pair.getX().x() == size - 1);
    }
    
    private void move(int offset) {
        cells = cells.stream().map(pair -> new Pair<>(new Position(pair.getX().x() + offset, pair.getX().y()), pair.getY())).toList();
    }

}
