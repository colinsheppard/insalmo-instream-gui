/*
 * InSTREAMConfigApp.java
 */

package insalmo;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class InSTREAMConfigApp extends SingleFrameApplication {
    // Check that we are on Mac OS X.  This is crucial to loading and using the OSXAdapter class.
    public static boolean MAC_OS_X = (System.getProperty("os.name").toLowerCase().startsWith("mac os x"));

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
//        show(new InSTREAMConfigView(this));
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
     * @return the instance of InSTREAMConfigApp
     */
    public static InSTREAMConfigApp getApplication() {
        return Application.getInstance(InSTREAMConfigApp.class);
    }

    /**
     * Main method launching the application.
     */
//    public static void main(String[] args) {
//        launch(InSTREAMConfigApp.class, args);
//    	InSTREAMConfigView view = new InSTREAMConfigView(this);
//    }
    
    @Override
    protected void shutdown() {
        super.shutdown();
        // Now perform any other shutdown tasks you need.
        int i=0;
    }

}
