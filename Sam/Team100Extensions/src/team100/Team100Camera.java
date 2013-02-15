package team100;

import edu.wpi.first.smartdashboard.gui.*;
import edu.wpi.first.smartdashboard.properties.*;
import edu.wpi.first.wpijavacv.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

/**
 * @author Sam Bunk
 */
public class Team100Camera extends StaticWidget{

    public static final String NAME = "Team 100 Camera";
    public static Boolean firstcamera = true;

    public class GCThread extends Thread {

        boolean destroyed = false;

        public void Team100Camera(){
           
        }
        @Override
        public void run() {
            while (!destroyed) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                }
                System.gc();
            }
        }

        public void destroy() {
            destroyed = true;
            interrupt();
        }
    }

    /**
     * Thread contains all image retrieval from the camera objects. Uses firstcamera boolean to decide which camera should be used.
     */
    public class BGThread extends Thread {
        

        boolean destroyed = false;
        Runnable draw = new Runnable() {

            public void run() {
                //DashboardFrame.getInstance().getPanel().repaint(getBounds());
                DashboardFrame frame = (DashboardFrame) DashboardFrame.getInstance();
                frame.repaint(); 
            }
        };

        public BGThread() {
            super("Camera Background");
            
        }

        @Override
        public void run() { 
            WPIImage image;
            while (!destroyed) {
                if (cam == null) {
                    cam = new WPICamera(camIP.getSaveValue()); 
                    System.out.println("CAMERA 1 SET");
                } 
                if (cam2 == null){
                    cam2 = new WPICamera(cam2IP.getSaveValue()); 
                    System.out.println("CAMERA 2 SET");
                }
                try {
                    //collect tells the grabber if it should grab images or not
                    //when this is false no images are pulled and that camera does not use any bandwidth
                    //the cameras have a buffer that will empty when switched to; if this buffer is filled up
                    //the connection to the camera will break and the widget will automatically recreate it
                    if(firstcamera){    
                        cam.collect = true;
                        cam2.collect = false;  
                        image = cam.getNewImage();
                             
                    }else{
                        cam.collect = false;
                        cam2.collect = true;     
                        image = cam2.getNewImage();        
                    }         
                    
                    if (image instanceof WPIColorImage) {
                        drawnImage = processImage((WPIColorImage) image).getBufferedImage();
                        SwingUtilities.invokeLater(draw);

                    } else if (image instanceof WPIGrayscaleImage) {
                        drawnImage = processImage((WPIGrayscaleImage) image).getBufferedImage();
                        SwingUtilities.invokeLater(draw);
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    cam.dispose();
                    cam2.dispose();
                    cam = null;
                    cam2 = null;
                    
                    System.out.println("CAMERA CONNECTIONS FAILED - RESETTING");
                    System.out.println("This was most likely caused by the camera emptying it's buffer");
                    drawnImage = null;
                    SwingUtilities.invokeLater(draw);
                }
            }
        }

        @Override
        public void destroy() {
            destroyed = true;
        }
    }
    private boolean resized = false;
    private WPICamera cam;
    private WPICamera cam2;
    private BufferedImage drawnImage;
    private BGThread bgThread = new BGThread();
    private GCThread gcThread = new GCThread();
    
    DashboardPrefs prefs = (DashboardPrefs) DashboardPrefs.getInstance();
    
public final IPAddressProperty camIP = new IPAddressProperty(this, "Camera 1 IP Address", new int[]{10, (prefs.team.getValue() / 100), (prefs.team.getValue() % 100), 11});
public final IPAddressProperty cam2IP = new IPAddressProperty(this, "Camera 2 IP Address", new int[]{10, (prefs.team.getValue() / 100), (prefs.team.getValue() % 100), 12});
NetworkTable table;

    @Override
    public void init() {
        table = NetworkTable.getTable("SmartDashboard");
        setPreferredSize(new Dimension(100, 100));
        bgThread.start();
        gcThread.start();
        revalidate();        
        
        DashboardFrame frame = (DashboardFrame) DashboardFrame.getInstance();
                frame.repaint();
    }

    @Override
    public void propertyChanged(Property property) {
        if (property == camIP) {
            System.out.println("camIP Property changed");
            if (cam != null) {
                cam.dispose();
            }
            try {
                cam = new WPICamera(camIP.getSaveValue());
                System.out.println("camIP IP has been updated");
            } catch (Exception e) {
                e.printStackTrace();
                drawnImage = null;
                setPreferredSize(new Dimension(100, 100));
                revalidate();                
                
                DashboardFrame frame = (DashboardFrame) DashboardFrame.getInstance();
                
                frame.repaint();
            }
        }
        if (property == cam2IP) {
            System.out.println("cam2IP Property changed");
            if (cam2 != null) {
                cam2.dispose();
            }
            try {
                cam2 = new WPICamera(cam2IP.getSaveValue());
                System.out.println("cam2IP IP has been updated");
            } catch (Exception e) {
                e.printStackTrace();
                drawnImage = null;
                setPreferredSize(new Dimension(100, 100));
                revalidate();                
                
                DashboardFrame frame = (DashboardFrame) DashboardFrame.getInstance();
                
                frame.repaint();
            }
        }
    }

    @Override
    public void disconnect() {
        bgThread.destroy();
        gcThread.destroy();
        if(cam != null) {
            cam.dispose();
        }
        super.disconnect();
        System.out.println("DISCONNECTING");
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (drawnImage != null) {
            if (!resized) {
                setPreferredSize(new Dimension(drawnImage.getWidth(), drawnImage.getHeight()));
                revalidate();
            }
            int width = getBounds().width;
            int height = getBounds().height;
            double scale = Math.min((double) width / (double) drawnImage.getWidth(), (double) height / (double) drawnImage.getHeight());
            g.drawImage(drawnImage, (int) (width - (scale * drawnImage.getWidth())) / 2, (int) (height - (scale * drawnImage.getHeight())) / 2,
                    (int) ((width + scale * drawnImage.getWidth()) / 2), (int) (height + scale * drawnImage.getHeight()) / 2,
                    0, 0, drawnImage.getWidth(), drawnImage.getHeight(), null);
        } else {
            g.setColor(Color.ORANGE);
            g.fillRect(0, 0, getBounds().width, getBounds().height);
            g.setColor(Color.BLACK);
            g.drawString("NO CONNECTION", 10, 10);
        }
    }

    /**
     * Draw the crosshair on the image. Default position is the center of the screen but can be changed with the NetworkTable.
     * Crosshair_XPos and Crosshair_YPos are the keys that this looks for.
     * @param rawImage
     * @return
     */
    public WPIImage processImage(WPIColorImage rawImage) {
        if(firstcamera){//draws a crosshair 1/3 size of the screen in the center of the screen 
            
            int XPos = (int)table.getNumber("Crosshair_XPos", rawImage.getWidth()/2);
            int YPos = (int)table.getNumber("Crosshair_YPos", rawImage.getHeight()/2);
            
            rawImage.drawLine(new WPIPoint(XPos,YPos+30), new WPIPoint(XPos,YPos-30), WPIColor.BLACK, 2);
            rawImage.drawLine(new WPIPoint(XPos-30,YPos), new WPIPoint(XPos+30,YPos), WPIColor.BLACK, 2);
        }
        return rawImage;
    }

    
    /**
     * 
     * @param rawImage
     * @return
     */
    public WPIImage processImage(WPIGrayscaleImage rawImage) {
        return rawImage;
    }

    /**
     * Switches the camera boolean.
     */
    public static void SwitchCamera(){
        firstcamera = !firstcamera;    
    }
}