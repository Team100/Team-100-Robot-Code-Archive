/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package electabuzz;

/**
 *
 * @author Student
 */
public class Serial
{
    private static int no=0;
    private int id;
    
    public Serial()
    {
        id = no;
        no++;
    }
    
    public int get()
    {
        return id;
    }
}
