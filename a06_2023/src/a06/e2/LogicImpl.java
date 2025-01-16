package a06.e2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LogicImpl implements Logic {

    private final int size;
    private final Map<Position, Integer> cells = new HashMap<>();
    private boolean firstClick = true;
    private Position firstCard;
    private Position secondCard;

    public LogicImpl(int width) {
        this.size = width;
    }

    @Override
    public void start() {
        Random ran = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.cells.put(new Position(j, i), ran.nextInt(6) + 1);
            }
        }
    }

    @Override
    public String getCard(Position hit) {
        if (firstClick) {
            firstCard = hit;
            firstClick = false;
        } else {
            secondCard = hit;
            firstClick = true;
        }
        return this.cells.get(hit).toString();
    }

    @Override
    public boolean win() {
        return this.cells.get(firstCard) == this.cells.get(secondCard);
    }

    @Override
    public boolean hasTwoCards() {
        if (firstCard != null && secondCard != null) {
            return true;
        }
       return false;
    }

    @Override
    public void reset() {
        firstCard = null;
        secondCard = null;
    }

    @Override
    public Set<Position> disable() {
        Set<Position> out = new HashSet<>();
        out.add(firstCard);
        out.add(secondCard);
        cells.remove(firstCard);
        cells.remove(secondCard);
        return new HashSet<>(out);
    }

    @Override
    public boolean isOver() {
        return cells.values().stream().distinct().count() == cells.values().size();
    }

}
