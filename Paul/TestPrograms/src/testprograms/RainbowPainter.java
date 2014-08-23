package testprograms;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 * @author Me
 */
public class RainbowPainter extends JFrame{
    int x=0, y=0;
    String mode="rainbow";
    @Override
    public void paint(Graphics g){
        x=getMousePosition().x;
        y=getMousePosition().y;
        if ("rainbow".equals(mode)){
            g.setColor(Color.red);
            g.fillRect(x, y, 10, 10);
            g.setColor(Color.orange);
            g.fillRect(x+10, y+10, 10, 10);
            g.setColor(Color.yellow);
            g.fillRect(x+20, y+20, 10, 10);
            g.setColor(Color.green);
            g.fillRect(x+30, y+30, 10, 10);
            g.setColor(Color.blue);
            g.fillRect(x+40, y+40, 10, 10);
            g.setColor(Color.magenta);
            g.fillRect(x+50, y+50, 10, 10);
        }
        if ("black".equals(mode)){
            g.setColor(Color.black);
            g.fillRect(x, y, 20, 20);
        }
        if ("square".equals(mode)){
            super.paint(g);
            g.setColor(Color.black);
            g.fillRect(x, y, 50, 50);
        }
        if ("random".equals(mode)){
            switch((int)(java.lang.Math.random()*10)){
                case 1: g.setColor(Color.red); break;
                case 2: g.setColor(Color.orange); break;
                case 3: g.setColor(Color.yellow); break;
                case 4: g.setColor(Color.green); break;
                case 5: g.setColor(Color.blue); break;
                case 6: g.setColor(Color.magenta); break;
                case 7: g.setColor(Color.yellow); break;
                case 8: g.setColor(Color.cyan); break;
                case 9: g.setColor(Color.red); break;
                case 0: g.setColor(Color.blue); break;
            }
            g.fillRect(x, y, 20, 20);
        }
        if ("segment".equals(mode)){
            switch((getMousePosition().y/50)%6){
                case 1: g.setColor(Color.red); break;
                case 2: g.setColor(Color.orange); break;
                case 3: g.setColor(Color.yellow); break;
                case 4: g.setColor(Color.green); break;
                case 5: g.setColor(Color.blue); break;
                case 0: g.setColor(Color.magenta); break;
            }
            g.fillRect(x, y, 20, 20);
        }
        if (getMousePosition().x<10&&getMousePosition().y<10){
            g.clearRect(0, 0, 10000, 10000);
        }
    }
    
    RainbowPainter(){
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(10000,10000);
        setVisible(true);
        while(true){
            repaint();
        }
    }
}
