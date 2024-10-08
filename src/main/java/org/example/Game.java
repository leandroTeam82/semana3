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

    private int x = 10;
    private int y = 10;

    public void run(){
        try {
            draw();

        } catch (IOException e) {

            System.err.println("Erro ao desenhar no terminal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void draw() throws IOException {


            TerminalSize terminalSize = new TerminalSize(40, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();

            Screen screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();

        KeyStroke key = screen.readInput();
        processKey(key);

            screen.clear();
            screen.setCharacter(x, y, TextCharacter.fromCharacter(key.getCharacter())
                    [0]);
            screen.refresh();


    }

    private void processKey(KeyStroke key) {
        System.out.println(key);
    }
}
