/*
 * EYE_JUDGEApp.java
 */

package eye_judge;


import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import splashdemo.LoginFrame;




/**
 * The main class of the application.
 */
public class EYE_JUDGEApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */

    private static boolean check=false;
    private static String[] args1;
    @Override protected void startup() {
        show(new EYE_JUDGEView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of EYE_JUDGEApp
     */
    public static EYE_JUDGEApp getApplication() {
        return Application.getInstance(EYE_JUDGEApp.class);
    }

    /**
     * Main method launching the application.
     */
    public void initialize(){
          System.out.println("check");
          check=true;
          launch(EYE_JUDGEApp.class, args1);
   }
    public void create(){
        LoginFrame frame=new LoginFrame(this);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
       
        //SplashScn test = new SplashScn();
        //System.out.println("hari");
        EYE_JUDGEApp ob=new EYE_JUDGEApp();
        ob.create();
        System.out.println(check);
        EYE_JUDGEApp.args1=args;
      
    }
}
