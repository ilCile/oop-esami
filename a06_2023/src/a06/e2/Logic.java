package a06.e2;

import java.util.Set;

public interface Logic {
    public void start();

    public String getCard(Position hit);

    public boolean win();

    public boolean hasTwoCards();

    public void reset();

    public Set<Position> disable();

    public boolean isOver();

}
