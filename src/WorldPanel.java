import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

public class WorldPanel extends JPanel {

    private Circle playerCircle;
    private ArrayList<Circle> enemyCircles = new ArrayList<Circle>();
    private EnemySpawner enemySpawner;
    private EnemyLogic enemyLogic;
    private Camera camera;
    private long lastUpdateTime;
    private ViewportHandler viewport;

    public WorldPanel() {
        setPreferredSize(new Dimension(Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT)); 
        setFocusable(true);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updatePlayerCircleDirection(e.getX(), e.getY());
            }
        });
        

        playerCircle = new Circle(0, 0, Globals.START_WEIGHT_CIRCLE, Globals.playerColor, false);
        camera = new Camera(playerCircle.getX(), playerCircle.getY(), 1);
        viewport = new ViewportHandler(camera);
        enemySpawner = new EnemySpawner(viewport, enemyCircles, playerCircle);
        enemyLogic = new EnemyLogic(enemyCircles, playerCircle);
        
        initEnemyCircles(10);

        lastUpdateTime = System.currentTimeMillis();

        Thread gameLoop = new Thread(() -> {
        
            
            
            while (true) {
                long startTime = System.currentTimeMillis();

                enemySpawner.updateFood();
                enemySpawner.updateEnemies();
                enemyLogic.update();
                updateCircleDirections();
                updateCirclePositions();
                updateGameLogic();
                updateCameraZoom();
                repaint();

                long elapsedTime = System.currentTimeMillis() - startTime;

                long sleepTime = Globals.FRAME_TIME - elapsedTime;
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
            }
        });
        
        gameLoop.start();
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2d);

        ArrayList<Circle> sortedCircles = new ArrayList<Circle>(enemyCircles);
        sortedCircles.add(playerCircle);
        Collections.sort(sortedCircles, (a, b) -> a.getWeight() - b.getWeight());

        for (Circle circle : sortedCircles) {
            drawCircle(g2d, circle);
        }
    }

    private void initEnemyCircles(int count) {
        for (int i = 0; i < count; i++) {
            enemySpawner.spawnFood(viewport.worldX(0), viewport.worldY(0), viewport.worldX(Globals.WINDOW_WIDTH), viewport.worldY(Globals.WINDOW_HEIGHT));
        }
    }

    private void updatePlayerCircleDirection(int mouseX, int mouseY) {
        playerCircle.setDirection(Math.atan2(mouseY - Globals.WINDOW_HEIGHT / 2, mouseX - Globals.WINDOW_WIDTH / 2));
    }

    private void updateCircleDirections() {
        for (Circle circle : enemyCircles) {
            if (circle.isFood()) continue;
            double dx = circle.getTargetX() - circle.getX();
            double dy = circle.getTargetY() - circle.getY();
            circle.setDirection(Math.atan2(dy, dx));
        }
    }

    private void updateCameraZoom() {
        camera.setZoom(1 - Math.min(playerCircle.getSize() / 200.0, 0.2));
    }

    private void updateCirclePositions() {
        long currentTime = System.currentTimeMillis();
        double elapsedTimeInSeconds = (currentTime - lastUpdateTime) / 1000.0;

        for (Circle circle : enemyCircles) {
            if (circle.isFood()) continue;
            updateCirclePosition(circle, elapsedTimeInSeconds);
        }
        updateCirclePosition(playerCircle, elapsedTimeInSeconds);
        camera.setPosition(playerCircle.getX(), playerCircle.getY());

        lastUpdateTime = currentTime;
    }

    public void updateCirclePosition(Circle circle, double elapsedTimeInSeconds) {
        double distance = circle.getSpeed() * elapsedTimeInSeconds;

        double dx = distance * Math.cos(circle.getDirection());
        double dy = distance * Math.sin(circle.getDirection());

        double newX = circle.getX() + dx;
        double newY = circle.getY() + dy;
        circle.setPosition(newX, newY);
    }

    private void updateGameLogic() {
        ArrayList<Circle> allCircles = new ArrayList<Circle>(enemyCircles);
        allCircles.add(playerCircle);

        for (Circle circle : allCircles) {
            if (circle.isFood()) continue;
            for (Circle enemyCircle : allCircles) {
                if (!circle.isCollidingWith(enemyCircle)) continue;

                if (circle.canEat(enemyCircle))
                {
                    circle.setWeight(circle.getWeight() + (int)(enemyCircle.getWeight() / 2.0));
                    if (enemyCircle == playerCircle) {
                        // player died
                    } else {
                        enemyCircles.remove(enemyCircle);
                    }
                    break;
                }
            }
        }
    }

    private void drawBackground(Graphics2D g2d) {
        g2d.setColor(new Color(200, 200, 200));
        int gridSizeScreen = viewport.screenRange(Globals.GRID_SIZE);
        for (int x = -gridSizeScreen; x < Globals.WINDOW_WIDTH + gridSizeScreen; x += gridSizeScreen) {
            for (int y = -gridSizeScreen; y < Globals.WINDOW_HEIGHT + gridSizeScreen; y += gridSizeScreen) {
                int gridX = x - (int) (viewport.screenRange(camera.getX()) % gridSizeScreen);
                int gridY = y - (int) (viewport.screenRange(camera.getY()) % gridSizeScreen);
                g2d.drawRect(gridX, gridY, gridSizeScreen, gridSizeScreen);
            }
        }
    }

    private void drawCircle(Graphics2D g2d, Circle circle) {
        int circleScreenSize = viewport.screenRange(circle.getSize());
        int circleScreenX = viewport.screenX(circle.getX()) - circleScreenSize;
        int circleScreenY = viewport.screenY(circle.getY()) - circleScreenSize;
        int borderWidth = (int) (circleScreenSize / 10.0) + viewport.screenRange(5);

        g2d.setColor(new Color((int) (circle.getColor().getRed() / 1.5), (int) (circle.getColor().getGreen() / 1.5), (int) (circle.getColor().getBlue() / 1.5)));
        g2d.fillOval(circleScreenX - borderWidth, circleScreenY - borderWidth, circleScreenSize * 2 + borderWidth * 2, circleScreenSize * 2 + borderWidth * 2);

        g2d.setColor(circle.getColor());
        g2d.fillOval(circleScreenX, circleScreenY, circleScreenSize * 2, circleScreenSize * 2);
    }
}