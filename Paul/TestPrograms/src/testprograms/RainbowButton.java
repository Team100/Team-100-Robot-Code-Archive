package testprograms;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * @author Me
 */
public class RainbowButton {
    
    public RainbowButton(){
        execute();
    }

    JButton rainbowButton;
    JFrame frame;

    class MyListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent me) {
            if (rainbowButton.getBackground().equals(Color.RED)) {
                rainbowButton.setBackground(Color.ORANGE);
            }
            else if (rainbowButton.getBackground().equals(Color.ORANGE)) {
                rainbowButton.setBackground(Color.YELLOW);
            }
            else if (rainbowButton.getBackground().equals(Color.YELLOW)) {
                rainbowButton.setBackground(Color.GREEN);
            }
            else if (rainbowButton.getBackground().equals(Color.GREEN)) {
                rainbowButton.setBackground(Color.BLUE);
            }
            else if (rainbowButton.getBackground().equals(Color.BLUE)) {
                rainbowButton.setBackground(Color.MAGENTA);
            }
            else if (rainbowButton.getBackground().equals(Color.MAGENTA)) {
                rainbowButton.setBackground(Color.RED);
            }
        }

        @Override
        public void mousePressed(MouseEvent me) {
        }

        @Override
        public void mouseReleased(MouseEvent me) {
        }

        @Override
        public void mouseEntered(MouseEvent me) {
        }

        @Override
        public void mouseExited(MouseEvent me) {
        }
    }

    private void execute() {
        frame = new JFrame("Rainbow Button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rainbowButton = new JButton("PRESS ME!");
        rainbowButton.addMouseListener(new MyListener());
            
        rainbowButton.setBackground(Color.RED);

        frame.setSize(300, 300);
        frame.add(rainbowButton);
        frame.setVisible(true);
    }
}
