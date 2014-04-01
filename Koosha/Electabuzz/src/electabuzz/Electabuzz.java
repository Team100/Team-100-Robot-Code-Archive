/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package electabuzz;

import java.awt.Point;

/**
 *
 * @author Student
 */
public class Electabuzz
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Serial a=new Serial(),b=new Serial(),c=new Serial();
        System.out.println(a.get());
        System.out.println(b.get());
        System.out.println(c.get());
        
        //you start at 0,0
        //you want a path to the 1
        //output should look like down down right right left etc.
        //you cannot go through 2s
        //program should work for any array
        //use recursion
        
        int[][] map = {{0,2,0,0,0},
                       {0,0,2,2,0},
                       {0,2,0,0,0},
                       {0,0,0,2,1},
                       {0,0,0,2,0}};
        
        System.out.print(findPath(map, new Point(0, 0)));
    }

    static char[] findPath(int[][] map, Point start)
    {
        Point pos = start;
        Point prev = new Point(0,0);
        
        if(map[pos.x+1][pos.y]==0)
        {
            prev = pos;
            pos.translate(1, 0);
        }
        else if(map[pos.x][pos.y+1]==0)
        {
            prev = pos;
            pos.translate(0, 1);
        }
        else
        {
            pos=prev;
            pos.translate(0, 1);
        }

        return null;
    }
}
