package main.java;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents a single ball in the bouncing balls playground.
 * Each ball has a position, velocity, speed, radius, and a visual representation (JavaFX Circle).
 * <p>
 * This class is intended to be managed by a {@link BallController}, which handles movement,
 * collisions, and interactions with other balls or obstacles.
 * </p>
 *
 * @version 1.0
 * @see BallController
 */
public class Ball {

    /** JavaFX Circle representing the ball in the scene */
    public Circle view;

    /** Horizontal velocity of the ball */
    public double dx;

    /** Vertical velocity of the ball */
    public double dy;

    /** Speed multiplier for the ball movement */
    public final double SPEED;

    /** Radius of the ball */
    public final double RADIUS = 5.0;

    /** Reference to the controller that manages this ball */
    private BallController controller;

    /**
     * Creates a new Ball at a specified position with a given velocity, speed, color, and controller.
     *
     * @param x          initial X position of the ball
     * @param y          initial Y position of the ball
     * @param dx         initial horizontal velocity
     * @param dy         initial vertical velocity
     * @param speed      speed multiplier
     * @param color      color of the ball
     * @param controller controller responsible for handling this ball's movement
     */
    public Ball(double x, double y, double dx, double dy, double speed,
                Color color, BallController controller) {

        // Create the JavaFX circle for visualization
        this.view = new Circle(RADIUS, color);
        this.view.setCenterX(x);
        this.view.setCenterY(y);

        // Set initial velocities and speed
        this.dx = dx;
        this.dy = dy;
        this.SPEED = speed;

        // Save reference to the controller for movement updates
        this.controller = controller;
    }
}