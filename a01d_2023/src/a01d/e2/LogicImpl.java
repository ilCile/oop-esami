package a01d.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicImpl implements Logic {

    private final int size;
    private boolean firstClick = true;
    private final List<Position> markedCells = new ArrayList<>();
    private Position center;
    private boolean isOver = false;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public List<Position> hit(Position hit) {
        if (firstClick) {
            create(hit);
            firstClick = false;
        } else {
            move(hit);
        }
        return new ArrayList<>(markedCells);
    }

    public void create(Position cen) {
        markedCells.clear();
        for (int i = cen.Y() - 2; i <= cen.Y() + 2; i++) {
            for (int j = cen.X() - 2; j <= cen.X() + 2; j++) {
                markedCells.add(new Position(j, i));
            }
        }
        isOver =  markedCells.stream()
        .anyMatch(cell -> cell.X() == 0 || cell.Y() == 0 || cell.Y() == size - 1 || cell.X() == size - 1);
        center = cen;
    }

    public void move(Position hit) {
        if (hit.X() >= center.X() - 1 && hit.X() <= center.X() + 1 && hit.Y() == center.Y() - 2) {
            create(new Position(center.X(), center.Y() - 1));
        }
        if (hit.X() >= center.X() - 1 && hit.X() <= center.X() + 1 && hit.Y() == center.Y() + 2) {
            create(new Position(center.X(), center.Y() + 1));
        }
        if (hit.Y() >= center.Y() - 1 && hit.Y() <= center.Y() + 1 && hit.X() == center.X() - 2) {
            create(new Position(center.X() - 1, center.Y()));
        }
        if (hit.Y() >= center.Y() - 1 && hit.Y() <= center.Y() + 1 && hit.X() == center.X() + 2) {
            create(new Position(center.X() + 1, center.Y()));
        }
    }

    @Override
    public boolean isOver() {
        return isOver;
    }

}
