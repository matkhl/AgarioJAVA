import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class EnemySpawner {
    private long lastFoodSpawnTime;
    private long lastEnemySpawnTime;
    private Random rand;
    private ViewportHandler viewport;
    private ArrayList<Circle> circles;
    private Circle playerCircle;
    
    public EnemySpawner(ViewportHandler viewport, ArrayList<Circle> circles, Circle playerCircle) {
        lastFoodSpawnTime = 0;
        lastEnemySpawnTime = 0;
        rand = new Random();
        this.viewport = viewport;
        this.circles = circles;
        this.playerCircle = playerCircle;
    }

    public void updateFood() {
        long currentTime = System.currentTimeMillis();
        if (currentTime < lastFoodSpawnTime + Globals.FOOD_SPAWN_DELAY) return;

        spawnFood(viewport.worldX(0), viewport.worldY(0), viewport.worldX(Globals.WINDOW_WIDTH), viewport.worldY(Globals.WINDOW_HEIGHT));
        lastFoodSpawnTime = currentTime;
    }

    public void updateEnemies() {
        long currentTime = System.currentTimeMillis();

        //Remove enemies that are out of bounds
        double screenHeightWorld = viewport.worldRange(Globals.WINDOW_HEIGHT);
        for (Circle circle : circles) {
            if (circle.getX() < viewport.worldX(0) - screenHeightWorld * 0.5 ||
                circle.getX() > viewport.worldX(Globals.WINDOW_WIDTH) + screenHeightWorld * 0.5 ||
                circle.getY() < viewport.worldY(0) - screenHeightWorld * 0.5 ||
                circle.getY() > viewport.worldY(Globals.WINDOW_HEIGHT) + screenHeightWorld * 0.5) {
                    removeEnemy(circle);
                    break;
                }
        }

        //Spawn enemies
        if (currentTime < lastEnemySpawnTime + Globals.ENEMY_SPAWN_DELAY) return;
        
        //    ##############
        //####-------1------####
        //####-0--screen--2-####
        //####-------3------####
        //    ##############
        int borderId = rand.nextInt(4);

        double fromX, fromY, toX, toY;
        switch (borderId) {
            case 0:
                fromX = viewport.worldX(0) - screenHeightWorld * 0.5;
                fromY = viewport.worldY(0);
                toX = viewport.worldX(0);
                toY = viewport.worldY(Globals.WINDOW_HEIGHT);
                break;
            case 2:
                fromX = viewport.worldX(Globals.WINDOW_WIDTH);
                fromY = viewport.worldY(0);
                toX = viewport.worldX(Globals.WINDOW_WIDTH) + screenHeightWorld * 0.5;
                toY = viewport.worldY(Globals.WINDOW_HEIGHT);
                break;
            case 1:
                fromX = viewport.worldX(0);
                fromY = viewport.worldY(0) - screenHeightWorld * 0.5;
                toX = viewport.worldX(Globals.WINDOW_WIDTH);
                toY = viewport.worldY(0);
                break;
            default:
                fromX = viewport.worldX(0);
                fromY = viewport.worldY(Globals.WINDOW_HEIGHT);
                toX = viewport.worldX(Globals.WINDOW_WIDTH);
                toY = viewport.worldY(Globals.WINDOW_HEIGHT) + screenHeightWorld * 0.5;
                break;
        }

        spawnEnemy(fromX, fromY, toX, toY);
        lastEnemySpawnTime = currentTime;
    }

    public void spawnFood(double fromX, double fromY, double toX, double toY) {
        int rangeX = (int) Math.round(toX - fromX);
        int rangeY = (int) Math.round(toY - fromY);
        circles.add(new Circle(fromX + rand.nextInt(rangeX), fromY + rand.nextInt(rangeY), Globals.START_WEIGHT_FOOD, Color.MAGENTA, true));
    }

    public void spawnEnemy(double fromX, double fromY, double toX, double toY) {
        int rangeX = (int) Math.round(toX - fromX);
        int rangeY = (int) Math.round(toY - fromY);
        int weightMin = (int) (playerCircle.getWeight() - playerCircle.getWeight() * 0.5);
        int weightMax = (int) (playerCircle.getWeight() + playerCircle.getWeight() * 0.5);
        int rangeWeight = weightMax - weightMin;
        final float hue = rand.nextFloat();
        // Saturation between 0.1 and 0.3
        final float saturation = (rand.nextInt(2000) + 1000) / 10000f;
        final float luminance = 0.9f;
        final Color color = Color.getHSBColor(hue, saturation, luminance);
        circles.add(new Circle(fromX + rand.nextInt(rangeX), fromY + rand.nextInt(rangeY), weightMin + rand.nextInt(rangeWeight), color, false));
    }

    public void removeEnemy(Circle circle) {
        circles.remove(circle);
    }
}
