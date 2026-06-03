package com.example.laba2_4;

import java.util.Random;
import static com.example.laba2_4.HelloApplication.CELL_COUNT;

public class Model {
    private Cell[][] field;

    public class Cell {
        boolean isMine;
        boolean isOpen;
        boolean isFlag;
        int countMines;
    }

    public Model() {
        generateField();
    }

    public Cell[][] getField() {
        return field;
    }

    private void generateField() {
        field = new Cell[CELL_COUNT][CELL_COUNT];
        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                field[i][j] = new Cell();
            }
        }
        MineGenerator.generateMines(field);
        recalculateDigits();
    }

    private void recalculateDigits() {
        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                if (!field[i][j].isMine) {
                    field[i][j].countMines = countMines(i, j);
                }
            }
        }
    }

    public void repositionMine(int row, int col) {
        if (field[row][col].isMine) {
            field[row][col].isMine = false;
            Random rand = new Random();
            int nx, ny;

            do {
                nx = rand.nextInt(CELL_COUNT);
                ny = rand.nextInt(CELL_COUNT);
            } while (field[nx][ny].isMine || (nx == row && ny == col));

            field[nx][ny].isMine = true;
            recalculateDigits();
        }
    }

    private int countMines(int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nx = row + dr;
                int ny = col + dc;
                if ((nx >= 0) && (nx < CELL_COUNT) && (ny >= 0) && (ny < CELL_COUNT) && (field[nx][ny].isMine)) {
                    count++;
                }
            }
        }
        return count;
    }

    public void openNeighbors(int row, int col) {
        if (row < 0 || row >= CELL_COUNT || col < 0 || col >= CELL_COUNT) return;

        if (field[row][col].isOpen || field[row][col].isFlag || field[row][col].isMine) return;

        field[row][col].isOpen = true;

        if (field[row][col].countMines == 0) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr == 0 && dc == 0) continue;

                    openNeighbors(row + dr, col + dc);
                }
            }
        }
    }

    public void showMinesAll(Cell[][] field) {
        for (Cell[] cells : field) {
            for (Cell cell : cells) {
                if (!cell.isFlag && cell.isMine) {
                    cell.isOpen = true;
                }
            }
        }
    }
}