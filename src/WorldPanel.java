import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.*;

public class WorldPanel extends JPanel {

    private Circle playerCircle;
    private ArrayList<Circle> enemyCircles = new ArrayList<Circle>();
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

        playerCircle = new Circle(0, 0, Globals.START_WEIGHT_CIRCLE, Color.CYAN, false);
        initEnemyCircles(10);
        camera = new Camera(playerCircle.getX(), playerCircle.getY(), 1);
        viewport = new ViewportHandler(camera);

        lastUpdateTime = System.currentTimeMillis();

        Thread gameLoop = new Thread(() -> {
            while (true) {
                long startTime = System.currentTimeMillis();

                updateCirclePosition();
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
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            enemyCircles.add(new Circle(rand.nextInt(200) - 100, rand.nextInt(200) - 100, Globals.START_WEIGHT_FOOD, Color.MAGENTA, true));
        }
    }

    private void updateCirclePosition() {
        long currentTime = System.currentTimeMillis();
        double elapsedTimeInSeconds = (currentTime - lastUpdateTime) / 1000.0;

        double distance = playerCircle.getSpeed() * elapsedTimeInSeconds;

        double dx = distance * Math.cos(playerCircle.getDirection());
        double dy = distance * Math.sin(playerCircle.getDirection());

        double newX = playerCircle.getX() + dx;
        double newY = playerCircle.getY() + dy;
        playerCircle.setPosition(newX, newY);
        camera.setPosition(playerCircle.getX(), playerCircle.getY());

        lastUpdateTime = currentTime;
    }

    private void updatePlayerCircleDirection(int mouseX, int mouseY) {
        double angle = Math.atan2(mouseY - Globals.WINDOW_HEIGHT / 2, mouseX - Globals.WINDOW_WIDTH / 2);
        playerCircle.setDirection(angle);
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