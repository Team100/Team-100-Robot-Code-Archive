package testprograms;

/**
 * @author Me
 */
public class Main {
    
    public static void main(String[] args) {
        
        int program = 8;
        
        switch (program) {
            case 1:
                new MazeRunner();
                break;
            case 2:
                new Clock();
                break;
            case 3:
                new Calculator();
                break;
            case 4:
                new WordGame();
                break;
            case 5:
                new RainbowButton();
                break;
            case 6:
                new RainbowPainter();
                break;
            case 7:
                new FileStuff();
                break;
            case 8:
                new DodgeWindow();
                break;
        }
    }
}