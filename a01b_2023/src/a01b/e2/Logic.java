package a01b.e2;

import java.util.List;

public interface Logic {
    List<Pair<Position, Integer>> hit(Position hit);

    boolean timeToMove();

    boolean isOver();
}
