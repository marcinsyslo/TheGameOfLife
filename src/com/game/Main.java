package com.game;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Game game = new Game(6, 4);
        game.startLife();
    }
}