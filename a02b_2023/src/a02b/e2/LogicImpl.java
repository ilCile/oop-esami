package a02b.e2;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LogicImpl implements Logic{
    private final int size;
    private final Set<Position> markedCells = new HashSet<>();
    private final List<Position> directionCells = new ArrayList<>();
    private Position center = null;
    private boolean isNotfirstClick = false;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public List<Position> hit(Position pos) {
        if (center == null) {
            center = pos;
            create(center);
        }
        if (directionCells.contains(pos)) {
            move(directionCells.indexOf(pos));
        }
        return new ArrayList<>(markedCells.stream().filter(p -> !directionCells.contains(p)).toList());
    }

    private void create(Position cen) {
        markedCells.clear();
        directionCells.clear();
        for (int i = cen.Y() - 2; i <= cen.Y() + 2; i++) {
            for (int j = cen.X() - 2; j <= cen.X() + 2; j++) {
                markedCells.add(new Position(j, i));
            }
        }
        directionCells.addAll(List.of(new Position(cen.X() + 1, cen.Y() + 1), new Position(cen.X() + 1, cen.Y() - 1), 
        new Position(cen.X() - 1, cen.Y() + 1), new Position(cen.X() - 1, cen.Y() - 1)));
        center = cen;
    }

    private void move(int direction) {
        isNotfirstClick = true;
        switch (direction) {
            case 0:
                create(new Position(size - 3, size - 3));
                break;
            case 1:
                create(new Position(size - 3, 2));
                break;
            case 2:
                create(new Position(2, size - 3));
                break;
            case 3:
                create(new Position(2, 2));
                break;
        }
    }

    @Override
    public Boolean isOver(Position pos) {
        return center.equals(pos) && isNotfirstClick;
    }

}
