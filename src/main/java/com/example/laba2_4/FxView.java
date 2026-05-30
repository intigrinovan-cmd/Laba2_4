package com.example.laba2_4;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.example.laba2_4.HelloApplication.CELL_COUNT;
import static com.example.laba2_4.HelloApplication.CELL_SIZE;

public class FxView implements View {
    private final GridPane gridPane;
    private OnClick onClick;
    private OnClick_right onClick_right;
    private final Rectangle[][] cells = new Rectangle[CELL_COUNT][CELL_COUNT];
    private final Text[][] texts = new Text[CELL_COUNT][CELL_COUNT];

    public FxView() {
        gridPane = new GridPane();
        gridPane.setPrefSize(CELL_SIZE * CELL_COUNT, CELL_SIZE * CELL_COUNT);

        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(Color.GRAY);
                rect.setStroke(Color.BLACK);

                Text text = new Text();
                text.setVisible(false);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rect, text);

                final int x = i;
                final int y = j;

                stackPane.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (onClick != null) {
                            onClick.onClick(x, y);
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        if (onClick_right != null) {
                            onClick_right.onClick_right(x, y);
                        }
                    }
                });

                gridPane.add(stackPane, j, i);
                cells[i][j] = rect;
                texts[i][j] = text;
            }
        }
    }

    @Override
    public Object getRoot() {
        return gridPane;
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
        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                Model.Cell cell = field[i][j];
                Rectangle rect = cells[i][j];
                Text text = texts[i][j];

                if (cell.isOpen) {
                    if (cell.isMine) {
                        rect.setFill(Color.BLACK);
                        text.setVisible(false);
                    } else {
                        rect.setFill(Color.WHITE);
                        if (cell.CountMines == 0) {
                            continue;
                        }
                        text.setText(String.valueOf(cell.CountMines));
                        text.setVisible(true);
                    }
                } else if (cell.isFlag) {
                    rect.setFill(Color.RED);
                    text.setVisible(false);
                } else {
                    rect.setFill(Color.GRAY);
                    text.setVisible(false);
                }
            }
        }
    }

    @Override
    public void drawWin() {
        showMessage("Победа!", "Поздравляем! Вы выиграли!");
    }

    @Override
    public void drawLoss() {
        showMessage("Проигрыш", "К сожалению, вы проиграли(");
    }

    private void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType finishButton = new ButtonType("Выход");
        alert.getButtonTypes().setAll(finishButton);

        alert.showAndWait().ifPresent(buttonType -> {System.exit(0);});
    }
}
