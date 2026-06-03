package com.example.laba2_4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.Pane;

import static com.example.laba2_4.HelloApplication.CELL_COUNT;
import static com.example.laba2_4.HelloApplication.CELL_SIZE;

public class CanvasView implements View {
    private final GraphicsContext gc;
    private final Canvas canvas;
    private final Pane root;
    private OnClick onClick;
    private OnClick_right onClick_right;

    public CanvasView() {
        this.canvas = new Canvas(CELL_SIZE * CELL_COUNT, CELL_SIZE * CELL_COUNT);
        this.gc = canvas.getGraphicsContext2D();
        this.root = new Pane(canvas);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (onClick == null || onClick_right == null) {
                return;
            }
            int col = (int) (event.getX() / CELL_SIZE);
            int row = (int) (event.getY() / CELL_SIZE);

            if (row >= 0 && row < CELL_COUNT && col >= 0 && col < CELL_COUNT) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    onClick.onClick(row, col);
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    onClick_right.onClick_right(row, col);
                }
            }
        });
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
        gc.setFill(Color.web("#BDBDBD"));
        gc.fillRect(0, 0, CELL_SIZE * CELL_COUNT, CELL_SIZE * CELL_COUNT);

        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                Model.Cell cell = field[i][j];
                double x = j * CELL_SIZE;
                double y = i * CELL_SIZE;

                if (cell.isOpen) {
                    if (cell.isMine) {
                        drawMine(x, y);
                    } else {
                        gc.setFill(Color.web("#E0E0E0"));
                        gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                        gc.setStroke(Color.web("#9E9E9E"));
                        gc.setLineWidth(1);
                        gc.strokeRect(x, y, CELL_SIZE, CELL_SIZE);

                        if (cell.countMines > 0) {
                            drawNumber(x, y, cell.countMines);
                        }
                    }
                } else if (cell.isFlag) {
                    drawFlag(x, y);
                } else {
                    draw3DButton(x, y);
                }
            }
        }
    }

    private void draw3DButton(double x, double y) {
        gc.setFill(Color.web("#BDBDBD"));
        gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeLine(x, y, x + CELL_SIZE, y);
        gc.strokeLine(x, y, x, y + CELL_SIZE);

        gc.setStroke(Color.web("#7B7B7B"));
        gc.strokeLine(x + CELL_SIZE - 1, y, x + CELL_SIZE - 1, y + CELL_SIZE);
        gc.strokeLine(x, y + CELL_SIZE - 1, x + CELL_SIZE, y + CELL_SIZE - 1);
    }

    private void drawNumber(double x, double y, int count) {
        Color numColor = switch (count) {
            case 1 -> Color.BLUE;
            case 2 -> Color.web("#388E3C");
            case 3 -> Color.RED;
            case 4 -> Color.web("#1A237E");
            case 5 -> Color.web("#7B1FA2");
            default -> Color.web("#006064");
        };

        gc.save();
        gc.setFill(numColor);
        gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, CELL_SIZE * 0.65));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(Integer.toString(count), x + CELL_SIZE * 0.5, y + CELL_SIZE * 0.72);
        gc.restore();
    }

    private void drawMine(double x, double y) {
        gc.setFill(Color.web("#EF5350"));
        gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        gc.setStroke(Color.web("#9E9E9E"));
        gc.strokeRect(x, y, CELL_SIZE, CELL_SIZE);

        gc.setFill(Color.BLACK);
        double r = CELL_SIZE * 0.5;
        double cx = x + r;
        double cy = y + r;

        gc.fillOval(cx - r * 0.5, cy - r * 0.5, r, r);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(cx - r * 0.7, cy, cx + r * 0.7, cy);
        gc.strokeLine(cx, cy - r * 0.7, cx, cy + r * 0.7);

        gc.setFill(Color.WHITE);
        gc.fillOval(cx - r * 0.2, cy - r * 0.2, r * 0.2, r * 0.2);
    }

    private void drawFlag(double x, double y) {
        draw3DButton(x, y);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(x + CELL_SIZE * 0.35, y + CELL_SIZE * 0.25, x + CELL_SIZE * 0.35, y + CELL_SIZE * 0.75);
        gc.strokeLine(x + CELL_SIZE * 0.25, y + CELL_SIZE * 0.75, x + CELL_SIZE * 0.6, y + CELL_SIZE * 0.75);

        gc.setFill(Color.RED);
        double[] xPoints = { x + CELL_SIZE * 0.35, x + CELL_SIZE * 0.7, x + CELL_SIZE * 0.35 };
        double[] yPoints = { y + CELL_SIZE * 0.25, y + CELL_SIZE * 0.4, y + CELL_SIZE * 0.5 };
        gc.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public javafx.scene.Parent getRoot() {
        return root;
    }

    @Override
    public void drawWin() {
        drawEndGameOverlay("Вы победили!", Color.web("#2E7D32"));
    }

    @Override
    public void drawLoss() {
        drawEndGameOverlay("Взрыв! ИГРА ОКОНЧЕНА", Color.web("#C62828"));
    }

    private void drawEndGameOverlay(String text, Color bannerColor) {
        gc.save();
        double totalWidth = CELL_SIZE * CELL_COUNT;
        double totalHeight = CELL_SIZE * CELL_COUNT;

        gc.setFill(Color.rgb(0, 0, 0, 0.4));
        gc.fillRect(0, 0, totalWidth, totalHeight);

        double bannerH = CELL_SIZE * 1.8;
        double bannerY = totalHeight / 2.0 - bannerH / 2.0;
        gc.setFill(bannerColor);
        gc.fillRect(0, bannerY, totalWidth, bannerH);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.5);
        gc.strokeRect(0, bannerY, totalWidth, bannerH);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, CELL_SIZE * 0.65));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(text, totalWidth / 2.0, bannerY + bannerH * 0.62);
        gc.restore();
    }
}