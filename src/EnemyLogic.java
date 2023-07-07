import java.util.ArrayList;

public class EnemyLogic {
    private ArrayList<Circle> circles;
    private Circle playerCircle;

    public EnemyLogic(ArrayList<Circle> circles, Circle playerCircle) {
        this.circles = circles;
        this.playerCircle = playerCircle;
    }

    public void update() {
        ArrayList<Circle> allCircles = new ArrayList<Circle>(circles);
        allCircles.add(playerCircle);
        for (Circle circle : allCircles) {
            if (circle == playerCircle) continue;
            if (circle.isFood()) continue;
            Circle closestEatable = null;
            double closestEatableDistance = 0;
            for (Circle enemyCircle : allCircles) {
                if ((enemyCircle.isFood() || circle.canEat(enemyCircle)) && (closestEatableDistance == 0 || circle.getDistanceTo(enemyCircle) < closestEatableDistance)) {
                    closestEatable = enemyCircle;
                    closestEatableDistance = circle.getDistanceTo(enemyCircle);
                }
            }
            if (closestEatable != null)
                circle.setTargetPosition(closestEatable.getX(), closestEatable.getY());
        }
    }
}
