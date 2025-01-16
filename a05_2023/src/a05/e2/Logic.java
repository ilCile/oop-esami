package a05.e2;

public interface Logic {
    public Position getPlayer();

    public Position getEnemy();

    public boolean hit(Position hit);

    public boolean isOver();
}
