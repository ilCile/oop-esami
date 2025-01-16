package a02c.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LogicImpl implements Logic {

    private final int size;
    private Position leftUp = null;
    private Position rightDown = null;
    private Position rightUp = null;
    private Position leftDown = null;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public List<Position> hit(Position hit) {
        if (leftUp == null) {
            leftUp = new Position(hit.X() - 1, hit.Y() - 1);
            rightDown = new Position(hit.X() + 1, hit.Y() + 1);
            rightUp = new Position(hit.X() + 1, hit.Y() - 1);
            leftDown = new Position(hit.X() - 1, hit.Y() + 1);
        }
        if (isCorner(hit)) {
            modifyCorners(hit);
        }

        return draw();
    }   

    private boolean isCorner(Position hit) {
        return hit.equals(rightDown) || hit.equals(rightUp) || hit.equals(leftDown) || hit.equals(leftUp);
    }

    private void modifyCorners(Position hit) {
        if (hit.equals(rightDown)) {
            rightDown = new Position(hit.X() + 1, hit.Y() + 1);
            rightUp = new Position(rightUp.X() + 1, rightUp.Y());
            leftDown = new Position(leftDown.X(), leftDown.Y() + 1);
        }
        if (hit.equals(rightUp)) {
            rightUp = new Position(hit.X() + 1, hit.Y() - 1);
            rightDown = new Position(rightDown.X() + 1, rightDown.Y());
            leftUp = new Position(leftUp.X(), leftUp.Y() - 1);
        }
        if (hit.equals(leftDown)) {
            leftDown = new Position(hit.X() - 1, hit.Y() + 1);
            leftUp = new Position(leftUp.X() - 1, leftUp.Y());
            rightDown = new Position(rightDown.X(), rightDown.Y() + 1);
        }
        if (hit.equals(leftUp)) {
            leftUp = new Position(hit.X() - 1, hit.Y() - 1);
            leftDown = new Position(leftDown.X() - 1, leftDown.Y());
            rightUp = new Position(rightUp.X(), rightUp.Y() - 1);
        }
    }

    private List<Position> draw() {
        List<Position> markedCells = new ArrayList<>();
        markedCells.clear();
        for (int i = leftUp.Y(); i <= rightDown.Y(); i++) {
            markedCells.add(new Position(leftUp.X(), i));
        }
        for (int i = leftUp.Y(); i <= rightDown.Y(); i++) {
            markedCells.add(new Position(rightDown.X(), i));
        }
        for (int i = leftUp.X(); i <= rightDown.X(); i++) {
            markedCells.add(new Position(i, leftUp.Y()));
        }
        for (int i = leftUp.X(); i <= rightDown.X(); i++) {
            markedCells.add(new Position(i, rightDown.Y()));
        }
        return new ArrayList<>(markedCells);
    }

    @Override
    public boolean isOver() {
        return Stream.of(rightDown, rightUp, leftDown, leftUp)
        .anyMatch(corner -> corner.X() == 0 || corner.Y() == 0 || corner.X() == size - 1 || corner.Y() == size - 1);
    }
}
