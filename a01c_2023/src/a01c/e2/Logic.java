package a01c.e2;

import java.util.List;

public interface Logic {

    List<Pair<Integer, Integer>> hit(Pair<Integer, Integer> pos);

    boolean firstCells();

    boolean isOver();

}
