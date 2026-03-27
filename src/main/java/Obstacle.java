package main.java;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a rectangular obstacle in the playground.
 * <p>
 * Provides helper methods for collision detection with balls.
 * </p>
 */
public class Obstacle {

    /** The JavaFX rectangle used for visualization */
    private Rectangle rect;

    /**
     * Constructs a new Obstacle with specified position and size.
     *
     * @param x      the X coordinate of the top-left corner
     * @param y      the Y coordinate of the top-left corner
     * @param width  the width of the obstacle
     * @param height the height of the obstacle
     */
    public Obstacle(double x, double y, double width, double height) {
        rect = new Rectangle(width, height);
        rect.setX(x);
        rect.setY(y);

        // Visual settings
        rect.setFill(Color.TRANSPARENT); // obstacle is see-through
        rect.setStroke(Color.CYAN);      // border color
        rect.setStrokeWidth(3);          // border thickness
    }

    // =======================
    // Helper methods for collision detection
    // =======================

    /** @return the X coordinate of the left side */
    public double getLeft() {
        return rect.getX();
    }

    /** @return the X coordinate of the right side */
    public double getRight() {
        return rect.getX() + rect.getWidth();
    }

    /** @return the Y coordinate of the top side */
    public double getTop() {
        return rect.getY();
    }

    /** @return the Y coordinate of the bottom side */
    public double getBot() {
        return rect.getY() + rect.getHeight();
    }

    /** @return the Rectangle object for adding to the scene */
    public Rectangle getRect() {
        return rect;
    }
}