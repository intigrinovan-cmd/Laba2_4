package com.example.laba2_4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.laba2_4.HelloApplication.MINES;
import static com.example.laba2_4.HelloApplication.CELL_COUNT;

public class MineGenerator {
    public static void generateMines(Model.Cell[][] field) {
        int totalCells = CELL_COUNT * CELL_COUNT;
        List<Integer> cellIndices = new ArrayList<>(totalCells);


        for (int i = 0; i < totalCells; i++) {
            cellIndices.add(i);
        }

        Collections.shuffle(cellIndices);

        for (int i = 0; i < MINES; i++) {
            int randomIndex = cellIndices.get(i);
            int r = randomIndex / CELL_COUNT;
            int c = randomIndex % CELL_COUNT;
            field[r][c].isMine = true;
        }
    }
}