package a05.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LogicImpl implements Logic {
    private final int size;
    private Position player;
    private Position enemy;
    private final Random r = new Random();

    public LogicImpl(int width) {
        this.size = width;
        do {
            player = new Position(r.nextInt(size), r.nextInt(size));
            enemy = new Position(r.nextInt(size), r.nextInt(size));
        } while (areClose(player, enemy, 1));
    }

    private boolean areClose(Position p1, Position p2, int offset) {
        for (int i = p1.y() - offset; i <= p1.y() + offset; i++) {
            for (int j = p1.x() - offset; j <= p1.x() + offset; j++) {
                if (p2.y() == i && p2.x() == j) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Position getPlayer() {
        return player;
    }

    @Override
    public Position getEnemy() {
        return enemy;
    }

    @Override
    public boolean hit(Position hit) {
        if (areClose(hit, player, 1)) {
            player = hit;
            if (areClose(player, enemy, 1)) {
                moveEnemy();
            }
            return true;
        }
        return false;
    }

    private void moveEnemy() {
        List<Position> moves = new ArrayList<>();
        for (int i = enemy.y() - 1; i <= enemy.y() + 1; i++) {
            for (int j = enemy.x() - 1; j <= enemy.x() + 1; j++) {
                if (i >= 0 && j >= 0 && i < size && j < size) {
                    if (!areClose(player, new Position(j, i), 1)) {
                        moves.add(new Position(j, i));
                    }
                }
            }
        }
        if (!moves.isEmpty()) {
            Collections.shuffle(moves);
            enemy = moves.get(0);
        }
    }

    @Override
    public boolean isOver() {
        return player.equals(enemy);
    }

}
