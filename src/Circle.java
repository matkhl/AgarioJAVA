import java.awt.Color;

public class Circle {
        private double x;
        private double y;
        private double direction;
        private int weight;
        private Color color;
        private boolean food;

        public Circle(double x, double y, int weight, Color color, boolean food) {
            this.x = x;
            this.y = y;
            this.direction = 0;
            this.weight = weight;
            this.color = color;
            this.food = food;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getSpeed() {
            return Globals.BASE_SPEED_CIRCLE - (int) ((Globals.BASE_SPEED_CIRCLE / 2) / weight * 0.001);
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

        public void setDirection(double direction) {
            this.direction = direction;
        }

        public void setPosition(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }