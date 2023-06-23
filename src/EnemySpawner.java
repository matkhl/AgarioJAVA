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

        spawnFood(circles, viewport.worldX(0), viewport.worldY(0), viewport.worldX(Globals.WINDOW_WIDTH), viewport.worldY(Globals.WINDOW_HEIGHT));
        lastFoodSpawnTime = currentTime;
    }

    public void updateEnemies() {
        long currentTime = System.currentTimeMillis();
        if (currentTime < lastEnemySpawnTime + Globals.ENEMY_SPAWN_DELAY) return;
        
        //    ##############
        //####-------1------####
        //####-0--screen--2-####
        //####-------3------####
        //    ##############
        int borderId = rand.nextInt(4);

        double fromX, fromY, toX, toY;

        double screenHeightWorld = viewport.worldRange(Globals.WINDOW_HEIGHT);
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

        spawnEnemy(circles, fromX, fromY, toX, toY);
        lastEnemySpawnTime = currentTime;
    }

    public void spawnFood(ArrayList<Circle> circles, double fromX, double fromY, double toX, double toY) {
        int rangeX = (int) Math.round(toX - fromX);
        int rangeY = (int) Math.round(toY - fromY);
        circles.add(new Circle(fromX + rand.nextInt(rangeX), fromY + rand.nextInt(rangeY), Globals.START_WEIGHT_FOOD, Color.MAGENTA, true));
    }

    public void spawnEnemy(ArrayList<Circle> circles, double fromX, double fromY, double toX, double toY) {
        int rangeX = (int) Math.round(toX - fromX);
        int rangeY = (int) Math.round(toY - fromY);
        int weightMin = (int) (playerCircle.getWeight() - playerCircle.getWeight() * 0.5);
        int weightMax = (int) (playerCircle.getWeight() + playerCircle.getWeight() * 0.5);
        int rangeWeight = weightMax - weightMin;
        circles.add(new Circle(fromX + rand.nextInt(rangeX), fromY + rand.nextInt(rangeY), weightMin + rand.nextInt(rangeWeight), Color.GREEN, false));
    }
}
