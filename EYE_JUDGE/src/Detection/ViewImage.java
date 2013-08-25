package Detection;

import java.awt.image.*;
import javax.swing.*;
/**
 *
 * @author STARLING
 */
public class ViewImage {
    
    public ViewImage(BufferedImage img) {
	    JFrame f = new JFrame();
	    JLabel l = new JLabel();
	    l.setIcon(new ImageIcon(img));
	    f.getContentPane().add(l);
	    f.pack();
	    f.show();
    }
}
