import java.awt.Color;

public class Circle {
        private double x;
        private double y;
        private double direction;
        private int weight;
        private Color color;
        private boolean food;
        
        private double targetX;
        private double targetY;

        public Circle(double x, double y, int weight, Color color, boolean food) {
            this.x = x;
            this.y = y;
            this.direction = 0;
            this.weight = weight;
            this.color = color;
            this.food = food;
            this.targetX = 0.1;
            this.targetY = 0.1;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getSpeed() {
            return Globals.BASE_SPEED_CIRCLE / ((getSize() + 40) / 50.0);
        }

        public double getDirection() {
            return direction;
        }

        public int getWeight() {
            return weight;
        }

        public double getSize() {
            return Math.sqrt(weight / Math.PI);
        }

        public Color getColor() {
            return color;
        }

        public boolean isFood() {
            return food;
        }

        public double getTargetX() {
            return targetX;
        }

        public double getTargetY() {
            return targetY;
        }

        public void setDirection(double direction) {
            this.direction = direction;
        }

        public void setPosition(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public void setTargetPosition(double targetX, double targetY) {
            this.targetX = targetX;
            this.targetY = targetY;
        }

        public boolean isCollidingWith(Circle circle) {
            return this.getDistanceTo(circle) < this.getSize();
        }

        public boolean canEat(Circle circle) {
            return circle.isFood() || this.getWeight() > circle.getWeight() + (circle.getWeight() * Globals.CIRCLE_CONSUMPTION_TOLERANCE);
        }

        public double getDistanceTo(Circle circle) {
            return Math.sqrt(Math.pow(circle.x - this.x, 2) + Math.pow(circle.y - this.y, 2));
        }
    }