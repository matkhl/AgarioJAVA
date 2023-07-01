import java.awt.Dimension;
import javax.swing.JFrame;

public class GuiHandler {
    public GuiHandler() {
        JFrame startMenu = new JFrame("Agar.io Java");
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Startpanel panel = new Startpanel();
        panel.setPreferredSize(new Dimension(Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT));
        startMenu.add(panel);
        startMenu.pack();
        startMenu.setLocationRelativeTo(null);
        startMenu.setVisible(true);
        while(true){
            Thread.onSpinWait();
            if(panel.getGamestart()==true){
                break;
            }
        
        }
        JFrame frame = new JFrame("Agar.io Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new WorldPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        startMenu.setVisible(false);
        frame.setVisible(true);
        
    }
}