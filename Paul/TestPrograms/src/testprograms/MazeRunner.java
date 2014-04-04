package testprograms;

/**
 * Me
 */
class MazeRunner {
    
    //U=up, L=left, R=right, D=down
    private String path = "";
    
    //0=empty, 1=wall, 2=start, 3=end
    private byte[][] maze = {
        {1,1,1,1,1,1,1,1},
        {1,2,0,0,0,0,0,1},
        {1,0,1,1,1,1,0,1},
        {1,0,1,0,0,0,1,1},
        {1,0,0,0,1,0,0,1},
        {1,0,1,1,0,1,0,1},
        {1,0,0,0,0,1,3,1},
        {1,1,1,1,1,1,1,1}
    };

    public MazeRunner() {
        for(int y=0; y<maze.length; y++){
            for(int x=0; x<maze[0].length; x++){
                System.out.println(x+""+y);
            }
        }
        if(run(1,1)){
            System.out.println(path);
        }
        else{
            System.out.println("Path Not Found");
        }
    }
    
    private boolean run(int x, int y){
        if(maze[x][y]==1){//wall
            return false;
        }
        if(maze[x][y]==3){//end
            return true;
        }
        if(run(x+1,y)){
            path="R"+path;
            return true;
        }
        if(run(x-1,y)){
            path="L"+path;
            return true;
        }
        if(run(x,y+1)){
            path="U"+path;
            return true;
        }
        if(run(x,y-1)){
            path="D"+path;
            return true;
        }
        return false;
    }
}
