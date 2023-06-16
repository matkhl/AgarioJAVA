public class ViewportHandler {
    
    private Camera camera;
    
    public ViewportHandler(Camera camera) {
        this.camera = camera;
    }

    public int screenX(double worldX) {
        return (int) ((worldX - camera.getX()) * camera.getZoom()) + Globals.WINDOW_WIDTH / 2;
    }

    public int screenY(double worldY) {
        return (int) ((worldY - camera.getY()) * camera.getZoom()) + Globals.WINDOW_HEIGHT / 2;
    }

    public int screenRange(double worldRange) {
        return (int) (worldRange * camera.getZoom());
    }

    public double worldX(int screenX) {
        return (screenX - Globals.WINDOW_WIDTH / 2) / camera.getZoom() + camera.getX();
    }

    public double worldY(int screenY) {
        return (screenY - Globals.WINDOW_HEIGHT / 2) / camera.getZoom() + camera.getY();
    }
}
