
import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.smartdashboard.properties.IPAddressProperty;
import edu.wpi.first.wpijavacv.WPIColor;
import edu.wpi.first.wpijavacv.WPIColorImage;
import edu.wpi.first.wpijavacv.WPIImage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sam
 */
public class CameraOverlay extends WPICameraExtension{
    
   
    @Override
    public WPIImage processImage(WPIColorImage rawimage){
        
        rawimage.drawRect(50, 50, 50, 50, WPIColor.BLACK, 2);
        
        return super.processImage(rawimage);
        
    }
    
}