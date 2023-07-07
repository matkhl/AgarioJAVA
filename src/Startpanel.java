import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class Startpanel extends JPanel{
    private JButton startbutton = new JButton();
    private JButton colorbutton = new JButton();
    private JButton exitbutton = new JButton();
    private JLabel scoreText = new JLabel();
    private JLabel highScoreText = new JLabel();
    private Dimension buttondDimension = new Dimension(Globals.WINDOW_WIDTH / 2, 100);
    private boolean gamestart = false;

    public Startpanel(int score, int highScore) {
        setPreferredSize(new Dimension(Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT));
        setLayout(new GridLayout(5, 1));
        setFocusable(true);

        scoreText.setFont(new Font(TOOL_TIP_TEXT_KEY, 0, 30));
        scoreText.setText("Last score: " + String.valueOf(score));
        scoreText.setAlignmentX(CENTER_ALIGNMENT);
        JScrollPane scoreScrollPane = new JScrollPane(scoreText);
        scoreScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        add(scoreScrollPane);

        highScoreText.setFont(new Font(TOOL_TIP_TEXT_KEY, 0, 30));
        highScoreText.setText("Highscore: " + String.valueOf(highScore));
        highScoreText.setAlignmentX(CENTER_ALIGNMENT);
        JScrollPane highScoreScrollPane = new JScrollPane(highScoreText);
        highScoreScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        add(highScoreScrollPane);

        startbutton.setPreferredSize(buttondDimension);
        startbutton.setText("Start");
        startbutton.addActionListener(new java.awt.event.ActionListener() {
            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gamestart=true;
            }
        });
        add(startbutton);

        colorbutton.setPreferredSize(buttondDimension);
        colorbutton.setText("Farbauswahl");
        colorbutton.addActionListener(new java.awt.event.ActionListener() {
            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
            public void actionPerformed(java.awt.event.ActionEvent e) {
                final JLabel previewLabel = new JLabel( "\u25CF", JLabel.CENTER );
                previewLabel.setFont(new Font("Serif", Font.BOLD | Font.PLAIN, 48));
                previewLabel.setSize(previewLabel.getPreferredSize());
                previewLabel.setBorder(BorderFactory.createEmptyBorder(0,0,1,0));
    
                JColorChooser colorChooser = new JColorChooser();
                colorChooser.setPreviewPanel( previewLabel );
                colorChooser.setColor(Globals.playerColor);
                //Globals.playerColor = colorChooser.showDialog(null, "Wähle eine Farbe", Globals.playerColor);

                JDialog d = JColorChooser.createDialog(null,"Farbauswahl",true,colorChooser,
                    new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            Globals.playerColor = colorChooser.getColor();
                        }
                    },null);
                
                d.setVisible(true);
            }
        });
        add(colorbutton);

        exitbutton.setPreferredSize(buttondDimension);
        exitbutton.setText("Beenden");
        exitbutton.addActionListener(new java.awt.event.ActionListener() {
            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.exit(0);
            }
        });
        add(exitbutton);

        int padding = 100;
        EmptyBorder border = new EmptyBorder(padding, padding, padding, padding);
        setBorder(BorderFactory.createCompoundBorder(getBorder(), border));
    }

    public boolean getGamestart(){
        return gamestart;
    }
}
