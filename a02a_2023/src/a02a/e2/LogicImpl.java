package a02a.e2;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.lang.Math;

public class LogicImpl implements Logic {

    private final Set<Pair<Integer, Integer>> validCells = new HashSet<>();
    private final List<Pair<Integer, Integer>> lastFourMarkedCells = new ArrayList<>();

    @Override
    public void start(Pair<Integer, Integer> elem) {
        validCells.add(elem);
    }

    @Override
    public Boolean hit(Pair<Integer, Integer> pos) {
        if (validCells.contains(pos)) {
            if (lastFourMarkedCells.size() == 4) {
                lastFourMarkedCells.removeLast();
            }
            lastFourMarkedCells.addFirst(pos);
            System.out.println(lastFourMarkedCells);
            return true;
        }
        return false;
    }

    @Override
    public Boolean isOver() {
        int i = 0;
        if (lastFourMarkedCells.size() < 4) {
            System.out.println("non ancora");
            return false;
        }
        for (int j = 0; j < lastFourMarkedCells.size(); j++) {
            if (j == lastFourMarkedCells.size() - 1) {
                i = 0;
            }
            if (Math.abs(lastFourMarkedCells.get(j).getX() -  lastFourMarkedCells.get(i + 1).getX()) > 2|| 
            Math.abs(lastFourMarkedCells.get(j).getY() - lastFourMarkedCells.get(i + 1).getY()) > 2 ) {
                return false;
            }
            i++;
        }
        return true;
    }
}
