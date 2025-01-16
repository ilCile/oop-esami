package a03b.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicImpl implements Logic {

    private final int width;
    private final int height;
    private final Position target;
    private final List<Position> markedCells = new ArrayList<>();

    public LogicImpl(int width, int height) {
        this.width = width;
        this.height = height;
        Random ran = new Random();
        target = new Position(ran.nextInt(width), ran.nextInt(height));
    }

    @Override
    public Position start() {
        return target;
    }

    @Override
    public List<Position> hit(Position hit) {
        int offset = 0;
        while (true) {
            if (hit.Y() - offset < 0 || hit.Y() + offset > height - 1 || hit.X() + offset > width - 1) {
                break;
            }
            for (int i = hit.Y() - offset; i <= hit.Y() + offset; i++) {
                markedCells.add(new Position(hit.X() + offset, i));
            }
            offset++;
        }
        return new ArrayList<>(markedCells);
    }

    @Override
    public boolean isOver() {
        return markedCells.contains(target);
    }

}
