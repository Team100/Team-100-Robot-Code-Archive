package testprograms;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * @author Me
 */
public class Calculator {
    JFrame frame;
    JTextField field;
    String buttonChars = "123+456-789*C0./=";
    double storedNumber = 0;
    char storedSign = '0';
    boolean replace = true;
    
    class MyListener implements MouseListener{
        char number;
        MyListener(char i){
            number = i;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            buttonPressed(number);
        }

        @Override
        public void mousePressed(MouseEvent e) {
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
    
    Calculator(){        
        field = new JTextField(0);
        field.setEditable(false);
        
        frame = new JFrame("Calculator");
        frame.setSize(215, 335);
        frame.setLayout(null);
        frame.add(field);
        for (int i=0; i<17; i++){
            JButton b = new JButton(buttonChars.charAt(i)+"");
            b.addMouseListener(new MyListener(buttonChars.charAt(i)));
            frame.add(b);
            b.setBounds((i%4)*50, (i/4)*50+50, 50, 50);
        }
        field.setBounds(0,0,250,50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public void buttonPressed(char number) {
        switch (number) {
            case '=':
                compute();
                replace = true;
                break;
            case 'C':
                if ("".equals(field.getText())) {
                    storedNumber = 0;
                }
                field.setText("");
                storedSign = '0';
                break;
            case '+':
                compute();
                storedSign = '+';
                field.setText("");
                break;
            case '-':
                compute();
                storedSign = '-';
                field.setText("");
                break;
            case '*':
                compute();
                storedSign = '*';
                field.setText("");
                break;
            case '/':
                compute();
                storedSign = '/';
                field.setText("");
                break;
            case '.':
                if(!field.getText().contains(".")){
                    field.setText(field.getText() + number);
                }
                break;
            default:
                if(replace){
                    field.setText(number+"");
                    replace = false;
                }
                else{
                    field.setText(field.getText() + number);
                }
        }
    }
    
    public void compute(){
        switch(storedSign){
            case '+':
                field.setText((double)storedNumber+Double.parseDouble(field.getText())+"");
                break;
            case '-':
                field.setText((double)storedNumber-Double.parseDouble(field.getText())+"");
                break;
            case '*':
                field.setText((double)storedNumber*Double.parseDouble(field.getText())+"");
                break;
            case '/':
                field.setText((double)storedNumber/Double.parseDouble(field.getText())+"");
                break;
        }
        storedNumber = Double.parseDouble(field.getText());
        storedSign = '0';
    }
}
