package com.example.laba2_4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.laba2_4.HelloApplication.MINES;

public class MineGenerator {
    public static void generateMines(Model.Cell[][] field) {
        int rows = field.length;
        int cols = field[0].length;
        Random random = new Random();
        List<int[]> minesPositions = new ArrayList<>();

        while (minesPositions.size() < MINES) {
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);

            if (!field[x][y].isMine && !minesPositions.contains(new int[]{x,y})) {
                field[x][y].isMine = true;
                minesPositions.add(new int[]{x, y});
            }
        }
    }
}
