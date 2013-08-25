package eye_judge;


import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private Image img;

    public ImagePanel(File file) throws IOException {
        if(file!=null){
            img = ImageIO.read(file);
         }
        else
            img=null;
    }
    @Override
    public void paint(Graphics g) {
        if (img != null) {
            g.drawImage(img, 0, 0, this);
        }
        else{
            System.err.println("null image");
        }
    }
   
}
