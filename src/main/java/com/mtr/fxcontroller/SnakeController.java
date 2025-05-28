package com.mtr.fxcontroller;

import java.util.LinkedList;
import java.util.Random;

import org.springframework.stereotype.Component;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

@Component
public class SnakeController {

    @FXML
    public AnchorPane anchorPane;

    private final int width = 600;
    private final int height = 600;
    private final int unit_size = 20;

    private LinkedList<int[]> snake = new LinkedList<>();
    private int foodX = 10, foodY = 10;
    private boolean foodVisible = true;
    private String direction = "RIGHT";

    private GraphicsContext gc;
    private Timeline gameLoop;
    private Timeline foodBlink;

    private final Random random = new Random();

    public void initialize() {
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();

        drawGrid();
        anchorPane.getChildren().add(canvas);

        snake.add(new int[]{5, 5}); // starting position

        Platform.runLater(() -> {
            anchorPane.getScene().setOnKeyPressed(this::handleKeyPress);
        });

        startGameLoop();
        startFoodBlinkLoop();
    }

    private void drawGrid() {
        gc.setStroke(Color.GREY);
        gc.setLineWidth(1);
        gc.clearRect(0, 0, width, height);

        for (int x = 0; x < width; x += unit_size) {
            gc.strokeLine(x, 0, x, height);
        }

        for (int y = 0; y < height; y += unit_size) {
            gc.strokeLine(0, y, width, y);
        }
    }

    private void drawGame() {
        drawGrid();

        // Draw snake
        gc.setFill(Color.GREEN);
        for (int[] part : snake) {
            gc.fillRect(part[0] * unit_size, part[1] * unit_size, unit_size, unit_size);
        }

        // Draw food
        if (foodVisible) {
            gc.setFill(Color.RED);
            gc.fillRect(foodX * unit_size, foodY * unit_size, unit_size, unit_size);
        }
    }

    private void startGameLoop() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            moveSnake();
            drawGame();
        }));
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();
    }

    private void startFoodBlinkLoop() {
        foodBlink = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            foodVisible = !foodVisible;
            drawGame();
        }));
        foodBlink.setCycleCount(Animation.INDEFINITE);
        foodBlink.play();
    }

    private void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case UP:
                if (!direction.equals("DOWN")) direction = "UP";
                break;
            case DOWN:
                if (!direction.equals("UP")) direction = "DOWN";
                break;
            case LEFT:
                if (!direction.equals("RIGHT")) direction = "LEFT";
                break;
            case RIGHT:
                if (!direction.equals("LEFT")) direction = "RIGHT";
                break;
        }
    }
    
    private void moveSnake() {
        int[] head = snake.getFirst();
        int newX = head[0];
        int newY = head[1];

        switch (direction) {
            case "UP": newY--; break;
            case "DOWN": newY++; break;
            case "LEFT": newX--; break;
            case "RIGHT": newX++; break;
        }

        // Wrap around screen
        int maxX = width / unit_size;
        int maxY = height / unit_size;
        if (newX < 0) newX = maxX - 1;
        if (newY < 0) newY = maxY - 1;
        if (newX >= maxX) newX = 0;
        if (newY >= maxY) newY = 0;

        int[] newHead = new int[]{newX, newY};

        // Check self-collision
        for (int i = 0; i < snake.size(); i++) {
            if (snake.get(i)[0] == newX && snake.get(i)[1] == newY) {
                gameOver();
                return;
            }
        }

        snake.addFirst(newHead);

        // Eat food?
        if (newX == foodX && newY == foodY) {
            spawnFood();
        } else {
            snake.removeLast(); // move forward normally
        }
    }
    
    private void spawnFood() {
        int maxX = width / unit_size;
        int maxY = height / unit_size;

        while (true) {
            int x = random.nextInt(maxX);
            int y = random.nextInt(maxY);

            boolean onSnake = snake.stream().anyMatch(p -> p[0] == x && p[1] == y);
            if (!onSnake) {
                foodX = x;
                foodY = y;
                break;
            }
        }
    }

    private void gameOver() {
        gameLoop.stop();
        foodBlink.stop();
        gc.setFill(Color.BLACK);
        gc.fillText("Game Over", width / 2.0 - 30, height / 2.0);
    }


}
