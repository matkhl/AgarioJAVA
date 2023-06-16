public class Camera {
    private double x;
    private double y;
    private double zoom;

    public Camera(double x, double y, double zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZoom() {
        return zoom;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
