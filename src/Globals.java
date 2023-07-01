import java.awt.Color;

public class Globals {
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;
    public static final int GRID_SIZE = 20;
    public static final int FRAME_RATE = 60;
    public static final long FRAME_TIME = 1000L / FRAME_RATE;

    public static final int START_WEIGHT_CIRCLE = 1000;
    public static final int START_WEIGHT_FOOD = 400;
    public static final int BASE_SPEED_CIRCLE = 300;
    public static final double CIRCLE_CONSUMPTION_TOLERANCE = 0.1;
    public static Color playerColor = Color.CYAN;

    public static final int FOOD_SPAWN_DELAY = 1000;
    public static final int ENEMY_SPAWN_DELAY = 3000;
}
