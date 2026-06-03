package com.example.laba2_4;

public class Controller implements View.OnClick, View.OnClick_right {
    private final View view;
    private final Model model;
    private boolean firstClick = true;
    private Model.Cell[][] field;
    private boolean Win = false;
    private boolean Loss = false;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.field = model.getField();
        view.drawField(field);
        view.setOnClick(this);
        view.setOnClick_right(this);
    }

    @Override
    public void onClick(int row, int col) {
        if (Win || Loss || field[row][col].isFlag || field[row][col].isOpen) return;

        if (firstClick) {
            firstClick = false;
            model.repositionMine(row, col);
            this.field = model.getField();
        }

        if (field[row][col].isMine) {
            Loss = true;
            model.showMinesAll(field);
        } else {
            model.openNeighbors(row, col);
            checkWin();
        }

        view.drawField(field);

        if (Win) view.drawWin();
        if (Loss) view.drawLoss();
    }

    @Override
    public void onClick_right(int row, int col) {
        if (Win || Loss || field[row][col].isOpen) return;

        field[row][col].isFlag = !field[row][col].isFlag;
        view.drawField(field);
    }

    private void checkWin() {
        boolean won = true;
        for (int i = 0; i < HelloApplication.CELL_COUNT; i++) {
            for (int j = 0; j < HelloApplication.CELL_COUNT; j++) {
                if (!field[i][j].isMine && !field[i][j].isOpen) {
                    won = false;
                    break;
                }
            }
            if (!won) break;
        }
        if (won) {
            Win = true;
        }
    }
}