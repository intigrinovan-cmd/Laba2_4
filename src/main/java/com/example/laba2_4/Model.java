package com.example.laba2_4;

import java.util.Random;

import static com.example.laba2_4.HelloApplication.CELL_COUNT;

public class Model {
    private Cell[][] field;


    class Cell {
        boolean isMine;
        boolean isOpen;
        boolean isFlag;
        int CountMines;
    }


    public Cell[][] getField() {
        return field;
    }


    public Model(){
        generateField();
    }


    private void generateField() {
        field = new Cell[CELL_COUNT][CELL_COUNT];

        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                field[i][j] = new Cell();
            }
        }

        MineGenerator.generateMines(field);

        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                if (!field[i][j].isMine) {
                    field[i][j].CountMines = countMines(i,j);
                }
            }
        }
    }


    private int countMines(int x, int y) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++)
            for (int dy = -1; dy <= 1; dy++){
                int nx = x + dx, ny = y + dy;
                if((nx >= 0) && (nx < CELL_COUNT) && (ny>=0) && (ny<CELL_COUNT) && (field[nx][ny].isMine)){
                    count++;
                }
            }
        return count;
    }


    public void openNeighbors(int x, int y ) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int newX = x + dx;
                int newY = y + dy;

                if (newX >= 0 && newX < field.length && newY >= 0 && newY < field[0].length) {
                    if (!field[newX][newY].isOpen && !field[newX][newY].isFlag) {
                        field[newX][newY].isOpen = true;
                        if (field[newX][newY].CountMines == 0 && !field[newX][newY].isMine) {
                            openNeighbors(newX, newY);
                        }
                    }
                }
            }
        }
    }


    public void repositionMine(int x, int y){
        if(field[x][y].isMine){
            field[x][y].isMine=false;
            Random rand=new Random();
            int nx;
            int ny;
            do{
                nx = rand.nextInt(CELL_COUNT);
                ny = rand.nextInt(CELL_COUNT);
            }while(!field[nx][ny].isMine);
            field[nx][ny].isMine=true;
        }
    }


    public void showMinesAll(Cell[][] field){
        for(Cell[] cells : field){
            for(Cell cell : cells){
                if(!cell.isFlag && cell.isMine){
                    cell.isOpen =true;
                }
            }
        }
    }
}
