package a03a.e2;

import java.util.List;

public interface Logic {
    public Position start();

    public List<Position> hit(Position hit);

    public boolean isOver();
}
