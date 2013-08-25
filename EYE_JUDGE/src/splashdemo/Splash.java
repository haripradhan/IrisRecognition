

//package splashdemo;
/*
 * SplashDemo.java
 *
 */
/*
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D.Double;

public class Splash extends Frame implements ActionListener {

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

    public Splash() {
        super("SplashScreen demo");
        setSize(300, 200);
        setLayout(new BorderLayout());
        Menu m1 = new Menu("File");
        MenuItem mi1 = new MenuItem("Exit");
        m1.add(mi1);
        mi1.addActionListener(this);
        this.addWindowListener(closeWindow);
        MenuBar mb = new MenuBar();
        setMenuBar(mb);
        mb.add(m1);
        
         splash = SplashScreen.getSplashScreen();
         /*Dimension ssDim = splash.getSize();
        int semi_height = ssDim.height/2;
        System.out.println(semi_height);
        int width = ssDim.width;
        splashProgressArea = new Double(width * .2, semi_height*.92, width*0.6,5 );
        */
/*        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
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
        setVisible(true);
        toFront();
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

            // make sure it's displayed
           // splash.update();
        }
    }
    public void actionPerformed(ActionEvent ae) {
        System.exit(0);
    }

    private static WindowListener closeWindow = new WindowAdapter(){
        @Override
        public void windowClosing(WindowEvent e){
            e.getWindow().dispose();
        }
    };

    public static void main (String args[]) {
        Splash test = new Splash();
    }
}
*/