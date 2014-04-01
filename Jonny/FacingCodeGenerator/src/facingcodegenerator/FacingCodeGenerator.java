/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facingcodegenerator;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Jonathan Wilfong
 */
public class FacingCodeGenerator {

    static JFrame frame = new JFrame();
    static JTextField length = new JTextField("0.0");
    static JTextField width = new JTextField("0.0");
    static JTextField stockHeight = new JTextField("0.0");
    static JTextField finishHeight = new JTextField("0.0");
    static JTextField cuttingDepth = new JTextField("0.0");
    static JTextField stepOver = new JTextField("0.0");
    static JTextField cuttingSpeed = new JTextField("2500");
    static JTextField cuttingFeed = new JTextField("6.0");
    static JCheckBox finish = new JCheckBox();
    static JTextField finishCuttingSpeed = new JTextField("2500");
    static JTextField finishCuttingFeed = new JTextField("4.0");
    static JTextField finishCuttingDepth = new JTextField("0.005");
    static JTabbedPane pane = new JTabbedPane();
    static JPanel steps = new JPanel();
    static JPanel stockSize = new JPanel();
    static JTabbedPane output = new JTabbedPane();
    static JTextArea area = new JTextArea();
    static JScrollPane outputPanel = new JScrollPane(area);
    static JButton generateButton = new JButton("Generate");
    static JButton outputButton = new JButton("Output");
    static JPanel buttons = new JPanel();

    static class MyListener implements MouseListener {

        boolean generate;
        
        MyListener(boolean generate){
            this.generate=generate;
        }
        
        @Override
        public void mouseClicked(MouseEvent me) {
            if(generate){
                generate();
            }
            else{
                output();
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        frame.setLayout(new GridLayout(3, 1));
        frame.add(pane);
        frame.add(output);
        frame.add(buttons);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);

        output.add("Output", outputPanel);

        pane.addTab("Steps", steps);
        pane.add("Stock Size", stockSize);

        steps.setLayout(new GridLayout(7, 2));
        steps.add(new JLabel("Length"));
        steps.add(length);
        steps.add(new JLabel("Width"));
        steps.add(width);
        steps.add(new JLabel("Stock Height"));
        steps.add(stockHeight);
        steps.add(new JLabel("Finish Height"));
        steps.add(finishHeight);

        stockSize.setLayout(new GridLayout(8,2));
        stockSize.add(new JLabel("Cutting Depth"));
        stockSize.add(cuttingDepth);
        stockSize.add(new JLabel("Step Over"));
        stockSize.add(stepOver);
        stockSize.add(new JLabel("Cutting Speed"));
        stockSize.add(cuttingSpeed);
        stockSize.add(new JLabel("Cutting Feed"));
        stockSize.add(cuttingFeed);
        stockSize.add(new JLabel("Finish"));
        stockSize.add(finish);
        stockSize.add(new JLabel("Finish Cutting Speed"));
        stockSize.add(finishCuttingSpeed);
        stockSize.add(new JLabel("Finish Cutting Feed"));
        stockSize.add(finishCuttingFeed);
        stockSize.add(new JLabel("Finish Cutting Depth"));
        stockSize.add(finishCuttingDepth);

        buttons.setLayout(new GridLayout(1,2));
        buttons.add(generateButton);
        buttons.add(outputButton);
        generateButton.addMouseListener(new MyListener(true));
        outputButton.addMouseListener(new MyListener(false));
    }

    public static void generate() {
        //Put code here
        String text = "";
        text +=
            "M03 S"+cuttingSpeed.getText()+";\n"+
            "M06 T10\n";
        for(int i=0; i<toDouble(stockHeight)-toDouble(finishHeight)/toDouble(cuttingDepth)){
            text+="G54 G90 G00 G83 H10 Z"+
            (toDouble(stockHeight)-toDouble(finishHeight)/toDouble(cuttingDepth));
        }
        area.setText(text);
    }
    
    public static void output() {
        try(PrintWriter writer = new PrintWriter("src/facingcodegenerator/output.txt", "UTF-8")){
            writer.println(area.getText());
        }
        catch(FileNotFoundException | UnsupportedEncodingException ex){
            System.out.println("File Writing Error!");
        }
    }
    
    public static double toDouble(JTextField field){
        return Double.parseDouble(field.getText());
    }
}
