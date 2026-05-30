package com.example.laba2_4;

public class Controller implements CanvasView.OnClick, View.OnClick_right {
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
    public void onClick(int x, int y) {

        if (Win || Loss || field[x][y].isFlag || field[x][y].isOpen) return;

        if(firstClick){
            firstClick=false;
            model.repositionMine(x,y);
        }

        field[x][y].isOpen =true;

        if(field[x][y].isMine){
            Loss = true;
            model.showMinesAll(field);
        }else{
            if (field[x][y].CountMines == 0) {
                model.openNeighbors(x, y);
            }
            checkWin();
        }
        view.drawField(field);

        if (Win) {
            view.drawWin();
        }
        if (Loss) {
            view.drawLoss();
        }
    }


    @Override
    public void onClick_right(int x, int y) {
        if (Win || Loss || field[x][y].isOpen) return;

        field[x][y].isFlag =!field[x][y].isFlag;
        view.drawField(field);
    }


    private void checkWin() {
        boolean won = true;
        for(Model.Cell[] cells : field){
            for(Model.Cell cell : cells){
                if(!cell.isMine && !cell.isOpen){
                    won = false;
                    break;
                }
            }
            if (!won) break;
        }
        if(won){
            Win = true;
        }
    }
}
