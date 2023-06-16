import javax.swing.JFrame;

public class GuiHandler {
    public GuiHandler() {
        JFrame frame = new JFrame("Agar.io Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new WorldPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}