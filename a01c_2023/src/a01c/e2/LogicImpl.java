package a01c.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicImpl implements Logic {

    private final List<Pair<Integer, Integer>> rect = new ArrayList<>();
    private Pair<Integer, Integer> first;
    private Pair<Integer, Integer> second;
    private int counter = 0;
    private final int size;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public List<Pair<Integer, Integer>> hit(Pair<Integer, Integer> pos) {
        List<Pair<Integer, Integer>> toUpdate = new ArrayList<>();
        counter++;
        if (firstCells()) {
            rect.add(pos);
            if (counter == 1) {
                first = pos;
            } else {
                second = pos;
            }
            toUpdate.add(pos);
        } else if (createRect()) {
            for (int i = first.getX(); i <= second.getX(); i++) {
                for (int j = first.getY(); j <= second.getY(); j++) {
                    rect.add(new Pair<Integer,Integer>(i, j));
                    toUpdate.add(new Pair<Integer,Integer>(i, j));
                }
            }
        } else {
            first = new Pair<Integer,Integer>(first.getX() - 1, first.getY() - 1);
            second = new Pair<Integer,Integer>(second.getX() + 1, second.getY() + 1);
            for (int i = first.getX(); i <= second.getX(); i++) {
                for (int j = first.getY(); j <= second.getY(); j++) {
                    if (i >= 0 && i < size && j >= 0 && j < size) {
                        rect.add(new Pair<Integer,Integer>(i, j));
                        toUpdate.add(new Pair<Integer,Integer>(i, j));
                    }       
                }
            }
        }
        return new ArrayList<>(toUpdate);
    }

    private boolean createRect() {
        return counter == 3;
    }


    @Override
    public boolean firstCells() {
       return counter <= 2;
    }

    @Override
    public boolean isOver() {
       for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!rect.contains(new Pair<>(i, j))) {
                    return false;
                }
            }
       }
       return true;
    }

}
