package com.example.laba2_4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static com.example.laba2_4.HelloApplication.CELL_COUNT;
import static com.example.laba2_4.HelloApplication.CELL_SIZE;

public class CanvasView implements View {
    private final GraphicsContext gc;
    private OnClick onClick;
    private OnClick_right onClick_right;
    private final Canvas canvas;

    public CanvasView(Canvas canvas) {
        this.gc = canvas.getGraphicsContext2D();
        this.canvas = canvas;

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (onClick == null || onClick_right == null) {
                return;
            }
            int x = (int) (event.getX() / CELL_SIZE);
            int y = (int) (event.getY() / CELL_SIZE);

            if (event.getButton() == MouseButton.PRIMARY) {
                if (x >= 0 && x < CELL_COUNT && y >= 0 && y < CELL_COUNT) {
                    onClick.onClick(y, x);
                }
            }
            else if (event.getButton() == MouseButton.SECONDARY) {
                if (x >= 0 && x < CELL_COUNT && y >= 0 && y < CELL_COUNT) {
                    onClick_right.onClick_right(y, x);
                }
            }
        });
    }


    @Override
    public Object getRoot() {
        return canvas;
    }


    @Override
    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }


    @Override
    public void setOnClick_right(OnClick_right onClick_right) {
        this.onClick_right = onClick_right;
    }


    @Override
    public void drawField(Model.Cell[][] field) {
        for(int i = 0; i < CELL_COUNT; i++){
            for(int j = 0; j < CELL_COUNT; j++){
                Model.Cell cell = field[i][j];

                if(cell.isOpen){
                    if(cell.isMine){
                        drawMine(i, j);
                    }else{
                        gc.setFill(Color.WHITE);
                        gc.fillRect(j*CELL_SIZE,i*CELL_SIZE,CELL_SIZE,CELL_SIZE);
                        gc.strokeRect(j*CELL_SIZE,i*CELL_SIZE,CELL_SIZE,CELL_SIZE);
                        if (cell.CountMines == 0) {
                            continue;
                        }
                        String countStr = Integer.toString(cell.CountMines);
                        gc.strokeText(countStr,j*CELL_SIZE + CELL_SIZE / 2 - 5, i*CELL_SIZE + CELL_SIZE / 2 + 5);
                    }
                }else if(cell.isFlag){
                    drawFlag(i, j);
                }else{
                    gc.setFill(Color.GRAY);
                    gc.setStroke(Color.BLACK);
                    gc.fillRect(j*CELL_SIZE,i*CELL_SIZE,CELL_SIZE,CELL_SIZE);
                    gc.strokeRect(j*CELL_SIZE,i*CELL_SIZE,CELL_SIZE,CELL_SIZE);
                }
            }
        }
    }


    private void drawMine(int i, int j){
        gc.setFill(Color.WHITE);
        gc.fillRect(j*CELL_SIZE,i*CELL_SIZE,CELL_SIZE,CELL_SIZE);
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(j*CELL_SIZE,i*CELL_SIZE,CELL_SIZE,CELL_SIZE);
        gc.fillOval(j*CELL_SIZE+5,i*CELL_SIZE+5,CELL_SIZE-10,CELL_SIZE-10);
    }


    private void drawFlag(int i, int j){
        gc.setFill(Color.RED);
        gc.fillRect(j*CELL_SIZE,i*CELL_SIZE,CELL_SIZE,CELL_SIZE);
        gc.strokeLine(j*CELL_SIZE+5,i*CELL_SIZE+5,j*CELL_SIZE+CELL_SIZE-5,i*CELL_SIZE+CELL_SIZE-5);
        gc.strokeLine(j*CELL_SIZE+CELL_SIZE-5,i*CELL_SIZE+5,j*CELL_SIZE+5,i*CELL_SIZE+CELL_SIZE-5);
    }


    @Override
    public void drawWin(){
        gc.save();
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, CELL_SIZE));
        gc.fillText("Победа!!!",CELL_SIZE * CELL_COUNT / 2 - CELL_SIZE*3, CELL_SIZE * CELL_COUNT / 2);
        gc.restore();
    }


    @Override
    public void drawLoss(){
        gc.save();
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, CELL_SIZE));
        gc.fillText("Проигрыш(",CELL_SIZE * CELL_COUNT / 2 - CELL_SIZE*3, CELL_SIZE * CELL_COUNT / 2);
        gc.restore();
    }


}
