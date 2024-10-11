package org.example;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {

    private Screen screen;
    private Arena arena;

    public void run() {
        try {

            TerminalSize terminalSize = new TerminalSize(50, 30);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();

            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();


            arena = new Arena(10, 10);


            while (true) {
                draw();

                KeyStroke key = screen.readInput();
                if (key != null) {

                    processKey(key);

                    if (key.getCharacter() != null && key.getCharacter() == 'q') {
                        terminal.close();
                        break;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao desenhar no terminal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processKey(KeyStroke key) {
        arena.processKey(key);
    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }

    private void moveHero(Position position) {
        arena.moveHero(position);
    }



}
