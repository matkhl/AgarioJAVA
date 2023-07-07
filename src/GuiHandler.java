import javax.swing.JFrame;

public class GuiHandler {

    private int score = 0;
    private int highScore = 0;

    public GuiHandler() {
        JFrame frame = new JFrame("Agar.io Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            Startpanel panel = new Startpanel(score, highScore);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            while(true) {
                Thread.onSpinWait();
                if(panel.getGamestart()) {
                    break;
                }
            }

            frame.remove(panel);
            
            WorldPanel world = new WorldPanel();
            frame.add(world);
            frame.remove(panel);
            frame.pack();

            while(true) {
                Thread.onSpinWait();
                if(!world.isGameRunning()) {
                    score = world.getScore();
                    if (score > highScore)
                        highScore = score;
                    break;
                }
            }

            frame.remove(world);
        }
    }
}