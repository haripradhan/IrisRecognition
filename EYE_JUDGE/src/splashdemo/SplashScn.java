/*package splashdemo;

import eye_judge.resources.LoginFrame;
import java.awt.*;
import java.awt.geom.Rectangle2D.Double;

public class SplashScn{

     private Double splashProgressArea;
     private SplashScreen splash;
     private Graphics2D g;

     void renderSplashFrame(Graphics2D g, int frame) {
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120,140,200,40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.drawString("Loading...", 120, 130);
        splashProgress(frame);

    }

    public SplashScn() {
        splash = SplashScreen.getSplashScreen();
       

        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        Dimension ssDim = splash.getSize();
        int semi_height = ssDim.height/2;
        System.out.println(semi_height);
        int width = ssDim.width;
        splashProgressArea = new Double(width * .2, semi_height*.92, width*0.6,5 );
         g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }


        for(int i=0; i<100; i++) {
            renderSplashFrame(g,i);
            splash.update();
            try {
                Thread.sleep(90);
            }
            catch(InterruptedException e) {
            }
        }
        splash.close();

    }

    public void splashProgress(int pct)
    {
        if (splash != null && splash.isVisible())
        {

            // Note: 3 colors are used here to demonstrate steps
            //erase the old one
            g.setPaint(Color.gray);
            g.fill(splashProgressArea);

            // draw an outline
            g.setPaint(Color.BLUE);
            g.draw(splashProgressArea);

            // Calculate the width corresponding to the correct percentage
            int x = (int) splashProgressArea.getMinX();
            int y = (int) splashProgressArea.getMinY();
            int wid = (int) splashProgressArea.getWidth();
            int hgt = (int) splashProgressArea.getHeight();

            int doneWidth = Math.round(pct*wid/100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, wid-1));  // limit 0-width

            // fill the done part one pixel smaller than the outline

            g.setPaint(Color.darkGray);
            g.fillRect(x, y+1, doneWidth, hgt);

        }
    }



 public static void main (String args[]) {
        SplashScn test = new SplashScn();
        //new LoginFrame().setVisible(true);
    }
}
*/