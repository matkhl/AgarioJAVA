import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class FoodSpawner {
    private long lastSpawnTime;
    private Random rand;
    private ViewportHandler viewport;
    private ArrayList<Circle> circles;
    
    public FoodSpawner(ArrayList<Circle> circles, ViewportHandler viewport) {
        lastSpawnTime = 0;
        rand = new Random();
        this.viewport = viewport;
        this.circles = circles;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime < lastSpawnTime + Globals.FOOD_SPAWN_DELAY) return;

        spawnNew(circles, viewport.worldX(0), viewport.worldY(0), viewport.worldX(Globals.WINDOW_WIDTH), viewport.worldY(Globals.WINDOW_HEIGHT));
        lastSpawnTime = currentTime;
    }

    public void spawnNew(ArrayList<Circle> circles, double fromX, double fromY, double toX, double toY) {
        int rangeX = (int) Math.round(toX - fromX);
        int rangeY = (int) Math.round(toY - fromY);
        circles.add(new Circle(fromX + rand.nextInt(rangeX), fromY + rand.nextInt(rangeY), Globals.START_WEIGHT_FOOD, Color.MAGENTA, true));
    }
}
