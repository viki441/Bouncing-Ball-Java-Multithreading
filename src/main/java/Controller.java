package main.java;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;

/**
 * Controller for the Bouncing Balls playground.
 * <p>
 * Handles user interactions, playground setup, ball creation, and the main animation loop.
 * Uses JavaFX AnimationTimer to update ball positions every frame.
 * </p>
 * <p>
 * Balls are managed via a {@link BallController}, and the playground includes an {@link Obstacle}.
 * </p>
 *
 * @version 1.0
 * @see BallController
 * @see Ball
 * @see Obstacle
 */
public class Controller {

    /** Animation loop responsible for updating all ball positions */
    private AnimationTimer gameLoop;

    /** Pane where balls are displayed */
    @FXML private Pane playground;

    /** Color picker for selecting new ball colors */
    @FXML private ColorPicker colorPicker;

    /** Slider controlling the speed of new balls */
    @FXML private Slider speedSlider;

    /** Text field displaying the number of balls currently on screen */
    @FXML private TextField threadsCounter;

    /** Toggle button to pause/resume animation */
    @FXML private ToggleButton toggle;

    /** List of all balls currently in the playground */
    private List<Ball> balls = new ArrayList<>();

    /** Random number generator used for initial velocities and randomization */
    private Random random = new Random();

    /** Single obstacle in the playground (can be extended for multiple obstacles) */
    private Obstacle obstacle;

    /** Controller responsible for ball movement and collisions */
    private BallController ballController;

    /**
     * Initializes the controller after FXML loading.
     * <p>
     * Sets up the playground, disables editing on the counter, and starts the main animation loop.
     * </p>
     */
    @FXML
    public void initialize() {
        // Prevent manual editing of the balls counter
        threadsCounter.setEditable(false);

        // Setup playground when its width is known
        playground.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) setupPlayground();
        });

        // Main animation loop: moves all balls each frame (~60 FPS)
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Ball ball : balls) {
                    ballController.moveBall(ball);
                }
            }
        };

        gameLoop.start();
    }

    /**
     * Sets up the main playground, including the central obstacle and BallController.
     * Ensures obstacle is only created once.
     */
    private void setupPlayground() {
        if (obstacle != null) return; // Already set up

        double pWidth = playground.getPrefWidth();
        double pHeight = playground.getPrefHeight();
        double rectWidth = 10;
        double rectHeight = pHeight / 5;

        double x = pWidth / 2 - rectWidth / 2;
        double y = pHeight / 2 - rectHeight / 2;

        // Create and add obstacle to the playground
        obstacle = new Obstacle(x, y, rectWidth, rectHeight);
        playground.getChildren().add(obstacle.getRect());

        // Initialize BallController for ball movement
        ballController = new BallController(playground, obstacle, balls);
    }

    /**
     * Adds a new ball at the position of a mouse click on the playground.
     * Ensures balls are not spawned inside walls or obstacles.
     *
     * @param event MouseEvent containing the click coordinates
     */
    @FXML
    private void addBall(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        final double HEALTHY_DISTANCE = 10;

        // Prevent spawning inside the obstacle
        if (x >= obstacle.getLeft() - HEALTHY_DISTANCE && x <= obstacle.getRight() + HEALTHY_DISTANCE
                && y >= obstacle.getTop() - HEALTHY_DISTANCE && y <= obstacle.getBot() + HEALTHY_DISTANCE) {
            x = HEALTHY_DISTANCE;
            y = HEALTHY_DISTANCE;
        }

        // Prevent spawning too close to walls
        if (x < HEALTHY_DISTANCE) x = HEALTHY_DISTANCE;
        else if (x > playground.getWidth() - HEALTHY_DISTANCE) x = playground.getWidth() - HEALTHY_DISTANCE;
        if (y < HEALTHY_DISTANCE) y = HEALTHY_DISTANCE;
        else if (y > playground.getHeight() - HEALTHY_DISTANCE) y = playground.getHeight() - HEALTHY_DISTANCE;

        // Ball properties
        double speed = speedSlider.getValue();
        Color color = colorPicker.getValue() != null ? colorPicker.getValue() : Color.BLACK;
        double dx = 1;
        double dy = 1;

        // Create new ball and add to the playground
        Ball ball = new Ball(x, y, dx, dy, speed, color, ballController);
        balls.add(ball);
        playground.getChildren().add(ball.view);

        // Update balls counter
        threadsCounter.setText("balls on screen: " + balls.size());
    }

    /**
     * Toggles the main animation loop between paused and running.
     * Bound to the toggle button in the UI.
     */
    @FXML
    private void togglePause() {
        if (toggle.isSelected()) gameLoop.stop();
        else gameLoop.start();
    }

    /**
     * Gracefully quits the application, stopping the animation loop and exiting the platform.
     */
    @FXML
    private void quit() {
        gameLoop.stop();
        Platform.exit();
    }
}