package testprograms;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Me
 */
public class Clock {
    
    JFrame frame;
    JPanel panel;
    Image clock, hand;

    double hour = 0;
    double minute = 0;
    
    class MyPanel extends JPanel {
        @Override
        public void update(Graphics g){
            paintComponent(g);
        }
        
        @Override
        public void paintComponent(Graphics g){
            draw(g);
        }
    }
    
    public Clock(){
        frame = new JFrame("Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(515, 535);
        panel = new MyPanel();
        frame.add(panel);
        frame.setVisible(true);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask(){

            @Override
            public void run() {
                update();
            }
            
        }, 0, 10);
        try {
            clock = ImageIO.read(Clock .class.getResourceAsStream("stuff/clock.PNG"));
            hand = ImageIO.read(Clock .class.getResourceAsStream("stuff/hand.PNG"));
        } catch (IOException ex) {
            System.out.println("ERROR");
        }
    }
    
    public void draw(Graphics g1){
        Graphics2D g = (Graphics2D)g1;
        g.drawImage(clock, 0, 0, 500, 500, null);
        g.translate(250, 250);
        g.rotate(hour*Math.PI/6);
        g.drawImage(hand, -15, -15, 30, 100, null);
        g.rotate(-hour*Math.PI/6);
        g.rotate(minute*Math.PI/30);
        g.drawImage(hand, -15, -15, 30, 150, null);
        g.rotate(-minute*Math.PI/30);
        minute+=.01;
        hour+=.01/60;
    }
    
    public void update() {
        frame.repaint();
    }
}
