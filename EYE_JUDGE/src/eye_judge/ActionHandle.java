/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eye_judge;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author STARLING
 */
public class ActionHandle {

      public ActionHandle(){}
      public File getImageFile(){
        JFileChooser fc=new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        fc.setFileFilter(new ImageFilter());
        int result=fc.showOpenDialog(null);
        File file=null;
        if(result==JFileChooser.APPROVE_OPTION){
            file=fc.getSelectedFile();
            return file;
        }
        else
            return null;

    }

      private class ImageFilter extends FileFilter{

        @Override
        public boolean accept(File f) {
            if(f.isDirectory())
                return true;
            String name=f.getName();
            if(name.matches(".*((.jpg)|(.gif)|(.png)|(.bmp))"))
                return true;
            else
                return false;
        }

        @Override
        public String getDescription() {
            return "Image Files(*.jpg,*.gif,*.png,*.bmp)";
        }
      }
}
