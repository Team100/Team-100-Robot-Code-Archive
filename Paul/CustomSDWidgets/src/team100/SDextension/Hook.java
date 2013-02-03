/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team100.SDextension;

import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Student
 */
public class Hook extends Widget{
    public static final String NAME = "Hooked/Unhooked";
    public static final DataType[] TYPES = { DataType.BOOLEAN };
    
    private Boolean value = false;
    BufferedImage hooked = null;
    BufferedImage unhooked = null;
    
    @Override
    public void setValue(Object value) {
        this.value = (Boolean)value;

        repaint();
    }

    @Override
    public void init() {
        setPreferredSize(new Dimension(200, 200));
        try {
            hooked = ImageIO.read(new File("C:/Users/Student/Documents/NetBeansProjects/CustomSDWidgets/src/team100/SDextension/hooked.png"));
            unhooked = ImageIO.read(new File("C:/Users/Student/Documents/NetBeansProjects/CustomSDWidgets/src/team100/SDextension/unhooked.png"));
        } catch (IOException ex) {
        }
    }

    @Override
    public void propertyChanged(Property property) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension size = getSize();
        if (value) {
            g.drawImage(hooked, 0, 0, size.width, size.height, this);
        }
        else {
            g.drawImage(unhooked, 0, 0, size.width, size.height, this);
        }
    }
}