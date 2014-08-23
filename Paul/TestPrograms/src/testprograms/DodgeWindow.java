package testprograms;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * @author Me
 */
class DodgeWindow {

    int score = 0;
    JButton b;
    JFrame window;
    
    public DodgeWindow() {
        window = new JFrame("Catch me if you can!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(250, 250);
        window.setVisible(true);
        window.setResizable(false);
        b = new JButton("Score: 0");
        b.addMouseListener(new MyMouseListener());
        window.add(b);
        while(true){
            window.setLocation((int)(Math.random()*1670), (int)(Math.random()*950));
            try {
                Thread.sleep(1000-score*15);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
        }
    }

    private class MyMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(score<49){
                score++;
                b.setText("Score: "+score);
            }
            else {
                score = 0;
                b.setText("You Win!");
            }
            window.setLocation((int)(Math.random()*1620), (int)(Math.random()*900));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
    
}
