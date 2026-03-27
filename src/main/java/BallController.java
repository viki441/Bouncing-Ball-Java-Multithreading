package main.java;

import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Random;

/**
 * Controller responsible for managing the movement and interactions of balls
 * within a JavaFX playground.
 * <p>
 * Handles collisions with walls, obstacles, and other balls, as well as random
 * adjustments to prevent perfectly vertical or horizontal movement (floating-point drift).
 * </p>
 *
 * @version 1.0
 * @see Ball
 * @see Obstacle
 */
public class BallController {

    /** JavaFX Pane where balls are displayed */
    private Pane playground;

    /** Obstacle in the playground used for collision detection */
    private Obstacle obstacle;

    /** List of balls being managed by this controller */
    private List<Ball> balls;

    /** Random generator used for direction randomization */
    private Random random = new Random();

    /**
     * Creates a new BallController with the given playground, obstacle, and list of balls.
     *
     * @param playground the Pane where balls will be displayed
     * @param obstacle   the Obstacle to check collisions against
     * @param balls      the list of Ball objects to control
     */
    public BallController(Pane playground, Obstacle obstacle, List<Ball> balls) {
        this.playground = playground;
        this.obstacle = obstacle;
        this.balls = balls;
    }

    /**
     * Randomizes the direction of a ball to prevent perfectly horizontal or vertical movement.
     * This avoids floating-point drift that can make balls “stick” or not move visually.
     *
     * @param ball the Ball object to randomize
     */
    private void randomizeDirection(Ball ball) {
        double speed = Math.sqrt(ball.dx * ball.dx + ball.dy * ball.dy);
        double angle = Math.random() * 2 * Math.PI;

        ball.dx = Math.cos(angle) * speed;
        ball.dy = Math.sin(angle) * speed;
    }

    /**
     * Moves a ball according to its velocity and handles collisions with walls, obstacles,
     * and other balls. Also fixes cases where the ball might move perfectly horizontally
     * or vertically.
     *
     * @param ball the Ball object to move
     */
    public void moveBall(Ball ball) {
        // Current position
        double x = ball.view.getCenterX();
        double y = ball.view.getCenterY();

        // Compute next position
        double nextX = x + ball.dx * ball.SPEED;
        double nextY = y + ball.dy * ball.SPEED;

        final double R = ball.RADIUS;
        double w = playground.getWidth();
        double h = playground.getHeight();

        // ----- COLLISION WITH WALLS -----
        if (nextX < R || nextX >= w - R) {
            ball.dx *= -1;                  // bounce horizontally
            ball.dy *= random.nextDouble(0.7, 1.1); // slightly adjust vertical
        }
        if (nextY < R || nextY >= h - R) {
            ball.dy *= -1;                  // bounce vertically
            ball.dx *= random.nextDouble(0.7, 1.1); // slightly adjust horizontal
        }

        // ----- COLLISION WITH OBSTACLE -----
        double left = obstacle.getLeft();
        double right = obstacle.getRight();
        double top = obstacle.getTop();
        double bot = obstacle.getBot();

        if (nextX + R >= left && nextX - R <= right && nextY + R >= top && nextY - R <= bot) {
            ball.dx *= -1;
            ball.dy *= -1;
        }

        // ----- COLLISION WITH OTHER BALLS -----
        for (Ball other : balls) {
            if (other == ball) continue;

            double dx = other.view.getCenterX() - nextX;
            double dy = other.view.getCenterY() - nextY;
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist <= ball.RADIUS + other.RADIUS) {
                ball.dx *= -1;
                ball.dy *= -1;
            }
        }

        // ----- AVOID PERFECTLY HORIZONTAL OR VERTICAL MOVEMENT -----
        if (Math.floor(nextX * 100) / 100 == Math.floor(x * 100) / 100) {
            System.out.println("Vertical avoided");
            randomizeDirection(ball);
        }

        if (Math.floor(nextY * 100) / 100 == Math.floor(y * 100) / 100) {
            System.out.println("Horizontal avoided");
            randomizeDirection(ball);
        }

        // ----- UPDATE BALL POSITION -----
        nextX = x + ball.dx * ball.SPEED;
        nextY = y + ball.dy * ball.SPEED;

        ball.view.setCenterX(nextX);
        ball.view.setCenterY(nextY);
    }
}