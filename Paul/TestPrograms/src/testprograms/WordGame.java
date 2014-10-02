package testprograms;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author Me
 */

public class WordGame extends JFrame{
    int currentLetter=0;
    int level=1;
    String letters="Error!", answer="Error!";
    
    JButton button1 = new JButton("");
    JButton button2 = new JButton("");
    JButton button3 = new JButton("");
    JButton button4 = new JButton("");
    JButton button5 = new JButton("");
    JButton button6 = new JButton("");
    JLabel labela = new JLabel("Level 1:");
    JLabel labelb = new JLabel("");
    JLabel labelc = new JLabel("");
    JLabel labeld = new JLabel("");
    JLabel labele = new JLabel("");
    JLabel labelf = new JLabel("");
    JButton buttonA = new JButton("");
    JButton buttonB = new JButton("");
    JButton buttonC = new JButton("");
    JButton buttonD = new JButton("");
    JButton buttonE = new JButton("");
    JButton buttonF = new JButton("");
    
    public WordGame(String string){
        super(string);
        execute();
    }
    
    public WordGame(){
        super();
        execute();
    }
    
    public void addButtons(){
        setLayout(new GridLayout(3,1));
        
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
        add(button6);
        
        add(labela);
        add(labelb);
        add(labelc);
        add(labeld);
        add(labele);
        add(labelf);
        
        add(buttonA);
        add(buttonB);
        add(buttonC);
        add(buttonD);
        add(buttonE);
        add(buttonF);
        
        setVisible(true);
        pack();
        
        button1.addMouseListener(new MyListener());
        button2.addMouseListener(new MyListener());
        button3.addMouseListener(new MyListener());
        button4.addMouseListener(new MyListener());
        button5.addMouseListener(new MyListener());
        button6.addMouseListener(new MyListener());
        
        buttonA.addMouseListener(new MyListener());
        buttonB.addMouseListener(new MyListener());
        buttonC.addMouseListener(new MyListener());
        buttonD.addMouseListener(new MyListener());
        buttonE.addMouseListener(new MyListener());
        buttonF.addMouseListener(new MyListener());
    }

    public final void execute() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addButtons();
        setSize(500,200);
        newLevel();
    }
    
    public void newLevel(){
        randomWord();
        randomLetters();
        currentLetter=0;
        
        buttonA.setText(letters.substring(0, 1));
        buttonB.setText(letters.substring(1, 2));
        buttonC.setText(letters.substring(2, 3));
        buttonD.setText(letters.substring(3, 4));
        buttonE.setText(letters.substring(4, 5));
        buttonF.setText(letters.substring(5, 6));
        
        button1.setText("");
        button2.setText("");
        button3.setText("");
        button4.setText("");
        button5.setText("");
        button6.setText("");
    }
    
    public void buttonPressed(JButton button){
        if (button.getLocationOnScreen().y>50){//letters
            if (!"".equals(button.getText())){
                switch(currentLetter){
                    case 0: button1.setText(button.getText()); break;
                    case 1: button2.setText(button.getText()); break;
                    case 2: button3.setText(button.getText()); break;
                    case 3: button4.setText(button.getText()); break;
                    case 4: button5.setText(button.getText()); break;
                    case 5: button6.setText(button.getText()); break;
                }
                currentLetter++;
                button.setText("");
                checkWord();
            }
        }
        else {//answer
            if (!"".equals(button.getText())){
                if ("".equals(buttonA.getText())){
                    buttonA.setText(button.getText());
                }
                else if ("".equals(buttonB.getText())){
                    buttonB.setText(button.getText());
                }
                else if ("".equals(buttonC.getText())){
                    buttonC.setText(button.getText());
                }
                else if ("".equals(buttonD.getText())){
                    buttonD.setText(button.getText());
                }
                else if ("".equals(buttonE.getText())){
                    buttonE.setText(button.getText());
                }
                else if ("".equals(buttonF.getText())){
                    buttonF.setText(button.getText());
                }

                button.setText("");
                currentLetter--;
            }
        }
    }

    public void checkWord() {
        String guess=button1.getText()+button2.getText()+button3.getText()+button4.getText()+button5.getText()+button6.getText();
        if (guess.equals(answer)){
            level++;
            labela.setText("Level "+level+":");
            newLevel();
        }
    }

    public void randomLetters() {
        String Answer = answer;
        letters="";
        double letter;
        for (int i=0; i<6;){
            letter=(java.lang.Math.random()*100)%6;
            if (Answer.charAt((int)letter)!='!'){
                letters +=Answer.charAt((int)letter);
                i++;
            }
            Answer = Answer.replaceFirst(Answer.substring((int)letter, (int)letter+1), "!");
        }
    }
    
    public void randomWord(){
        switch ((int)(java.lang.Math.random()*100)){
            case 1: answer = "CHEESE"; break;
            case 2: answer = "PARROT"; break;
            case 3: answer = "MOTORS"; break;
            case 4: answer = "PACMAN"; break;
            case 5: answer = "TOPHAT"; break;
            case 6: answer = "ROBOTS"; break;
            case 7: answer = "BUSHES"; break;
            case 8: answer = "CARROT"; break;
            case 9: answer = "HAMMER"; break;
            case 10: answer = "TURTLE"; break;
            case 11: answer = "ROCKET"; break;
            case 12: answer = "POTATO"; break;
            case 13: answer = "LAPTOP"; break;
            case 14: answer = "BOTTLE"; break;
            case 15: answer = "PENCIL"; break;
            case 16: answer = "SCREEN"; break;
            case 17: answer = "TARGET"; break;
            case 18: answer = "TABLET"; break;
            case 19: answer = "VELCRO"; break;
            case 20: answer = "LIGHTS"; break;
            case 21: answer = "SQUARE"; break;
            case 22: answer = "REMOTE"; break;
            case 23: answer = "PILLOW"; break;
            case 24: answer = "BASKET"; break;
            case 25: answer = "DOCTOR"; break;
            case 26: answer = "VIOLIN"; break;
            case 27: answer = "BATTLE"; break;
            case 28: answer = "PHOTON"; break;
            case 29: answer = "CIRCLE"; break;
            case 30: answer = "SCHOOL"; break;
            case 31: answer = "RABBIT"; break;
            case 32: answer = "CANNON"; break;
            case 33: answer = "WINDOW"; break;
            case 34: answer = "SATURN"; break;
            case 35: answer = "SPIDER"; break;
            case 36: answer = "MIRROR"; break;
            case 37: answer = "KARATE"; break;
            case 38: answer = "JACKET"; break;
            case 39: answer = "ANSWER"; break;
            case 40: answer = "YELLOW"; break;
            case 41: answer = "ORANGE"; break;
            case 42: answer = "SOCCER"; break;
            case 43: answer = "TENNIS"; break;
            case 44: answer = "NEBULA"; break;
            case 45: answer = "VIOLET"; break;
            case 46: answer = "GARDEN"; break;
            case 47: answer = "FALCON"; break;
            case 48: answer = "BRANCH"; break;
            case 49: answer = "CASTLE"; break;
            case 50: answer = "DINNER"; break;
            //add more cases here
            default: newLevel();
        }
    }
}