package com.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Game {
    private int stageAmount;
    private int worldSize;
    private boolean[][] firstStageOfWorld;
    private boolean[][] secondStageOfWorld;
    private boolean[][][] temporaryStageOfWorld;

    Game(int worldSize, int amountOfStages) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("out.txt", false));
        bw.write(getDate() + "\n\n");
        bw.close();

        this.stageAmount = amountOfStages;
        this.worldSize = worldSize;
        this.firstStageOfWorld = createWorld(worldSize);
        this.secondStageOfWorld = firstStageOfWorld;
        this.temporaryStageOfWorld = updateStages(firstStageOfWorld, worldSize);
    }

    public void startLife() throws IOException {
        for (int i = 0; i < stageAmount / 2; i++) {
            createStage(firstStageOfWorld, secondStageOfWorld, temporaryStageOfWorld);
            createStage(secondStageOfWorld, firstStageOfWorld, temporaryStageOfWorld);
        }
    }

    private boolean[][][] updateStages(boolean[][] firstLife, int worldSize) {
        boolean[][][] tempLife = new boolean[worldSize][worldSize][8];
        int n = firstLife.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tempLife[i][j][0] = firstLife[(i + n - 1) % n][(j + n - 1) % n];
                tempLife[i][j][1] = firstLife[(i + n - 1) % n][(j + n) % n];
                tempLife[i][j][2] = firstLife[(i + n - 1) % n][(j + n + 1) % n];
                tempLife[i][j][3] = firstLife[(i + n) % n][(j + n - 1) % n];
                tempLife[i][j][4] = firstLife[(i + n) % n][(j + n + 1) % n];
                tempLife[i][j][5] = firstLife[(i + n + 1) % n][(j + n - 1) % n];
                tempLife[i][j][6] = firstLife[(i + n + 1) % n][(j + n) % n];
                tempLife[i][j][7] = firstLife[(i + n + 1) % n][(j + n + 1) % n];
                /*Zapis wszystkich stanów komórek sąsiadujących
                *   x x x
                *   x # x
                *   x x x */
            }
        }
        return tempLife;
    }

    private void createStage(boolean[][] firstLife, boolean[][] secondLife, boolean[][][] tempLife) throws IOException {
        int count = 0;
        for (int k = 0; k < secondLife.length; k++) {
            for (int m = 0; m < secondLife.length; m++) {
                for (int n = 0; n < 8; n++) {
                    count += Boolean.compare(tempLife[k][m][n], false);
                }
                if (!firstLife[k][m] && count == 3) secondLife[k][m] = true;
                else if (firstLife[k][m] && count > 3 || count < 2) secondLife[k][m] = false;
                count = 0;
            }
        }
        this.temporaryStageOfWorld = updateStages(secondLife, worldSize);
        drawWorld(secondLife);
    }

    private boolean[][] createWorld(int x) {
        boolean[][] table = new boolean[x][x];

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                table[i][j] = false;
            }
        }

        //wzorzec wiecznie żywy:
        table[1][4] = true;
        table[2][5] = true;
        table[3][3] = true;
        table[3][4] = true;
        table[3][5] = true;

        return table;
    }

    private void drawWorld(boolean[][] table) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("out.txt", true));
        StringBuilder sb = new StringBuilder();
        for (boolean[] aTable : table) {
            for (int j = 0; j < table.length; j++) {
                if (aTable[j]) sb.append(" # ");
                else sb.append(" . ");
            }
            sb.append("\n");
        }
        sb.append("\n");
        bw.write(sb.toString());
        bw.close();
    }

    private String getDate() {
        Date date = new Date();
        return date.toString();
    }
}
