package testprograms;

/**
 * @author Me
 */
class MazeRunner {

    //U=up, L=left, R=right, D=down
    private String path = "";
    //0=empty, 1=wall, 2=start, 3=end
    private byte[][] maze1 = {
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 2, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 1},
        {1, 0, 0, 0, 1, 0, 0, 1},
        {1, 0, 1, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 0, 1, 3, 1},
        {1, 1, 1, 1, 1, 1, 1, 1}
    };
    
    private byte[][] maze2 = {
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 1, 1},
        {1, 0, 1, 1, 1, 2, 1, 1},
        {1, 0, 1, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 1},
        {1, 0, 0, 0, 1, 0, 0, 1},
        {1, 0, 1, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 0, 1, 3, 1},
        {1, 1, 1, 1, 1, 1, 1, 1}
    };

    public MazeRunner() {
        System.out.println((findPath(maze2)));
    }
    
    private String findPath(byte[][] maze){
        path="";
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                if (maze[y][x] == 2) {
                    if (run(x, y, maze)) {
                        return(path);
                    } else {
                        return("Path Not Found");
                    }
                }
            }
        }
        return ("No start location");
    }

    private boolean run(int x, int y, byte[][] maze) {
        if (maze[y][x] == 1) {//wall
            return false;
        }
        if (maze[y][x] == 3) {//end
            return true;
        }
        maze[y][x] = 1;
        if (run(x + 1, y, maze)) {
            path = "R" + path;
            return true;
        }
        if (run(x - 1, y, maze)) {
            path = "L" + path;
            return true;
        }
        if (run(x, y - 1, maze)) {
            path = "U" + path;
            return true;
        }
        if (run(x, y + 1, maze)) {
            path = "D" + path;
            return true;
        }
        return false;
    }
}
