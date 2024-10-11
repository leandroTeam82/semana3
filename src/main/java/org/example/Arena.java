package org.example;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arena {

    private Hero hero;
    private int width;
    private int height;
    private List<Wall> walls;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(2, 2);
        this.walls = createWalls();
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    public void moveHero(Position position) {
        if (canHeroMove(position)) hero.setPosition(position);
    }

    public void draw(TextGraphics graphics) throws IOException {

        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width * 2, height * 2), ' ');

        for (Wall wall : walls) {
            wall.draw(graphics);
        }

        hero.draw(graphics);

    }

    private boolean canHeroMove(Position position) {

        if (position.getX() < 0 || position.getX() >= width - 1 || position.getY() < 0 || position.getY() >= height - 1) {
            return false;
        }


        for (Wall wall : walls) {
            if (
                    (wall.getX() == position.getX() * 2 && wall.getY() == position.getY() * 2) ||  // Posição superior esquerda
                            (wall.getX() == position.getX() * 2 && wall.getY() == position.getY() * 2 + 1) ||  // Inferior esquerda
                            (wall.getX() == position.getX() * 2 + 1 && wall.getY() == position.getY() * 2) ||  // Superior direita
                            (wall.getX() == position.getX() * 2 + 1 && wall.getY() == position.getY() * 2 + 1)  // Inferior direita
            ) {
                return false;
            }
        }

        return true;
    }


    public void processKey(KeyStroke key) {
        Position newPosition = null;

        if (key.getKeyType() == KeyType.ArrowUp) {
            newPosition = hero.moveUp();
        } else if (key.getKeyType() == KeyType.ArrowDown) {
            newPosition = hero.moveDown();
        } else if (key.getKeyType() == KeyType.ArrowLeft) {
            newPosition = hero.moveLeft();
        } else if (key.getKeyType() == KeyType.ArrowRight) {
            newPosition = hero.moveRight();
        }

        if (newPosition != null && canHeroMove(newPosition)) {
            moveHero(newPosition);
        }
    }


}
