/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Detection;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.Arrays;

/**
 *
 * @author STARLING
 */

public class Segment {
   private BufferedImage im;
   private int pix[];
   private int pix1[];
   private Image img;
   private int[][] temp;
   private int w,h;
  
   private int thres_val;                       //threshold value
   private float rad;
    private int[] pixGlobal;
    private int n_w=360,n_h=80;
    private BufferedImage map;

   public Segment(BufferedImage img){
        im=img;
        w=im.getWidth();
        h=im.getHeight();
        initArray();
   }
   
   public void initArray(){
        pix=new int[w*h];
        pix1=new int[w*h];
        temp=new int[w][h];
        pixGlobal=new int[w*h];
   }

   public int[] getPixel(){
       return pix;
   }
   
   public int[][] getTemp(){
       return temp;
   }

   public BufferedImage getNormaliseImage(){
       return this.map;
   }


   public void performDetection(){
       //findGrayLevel();
       temp=getGraylevel(im);
       //thres_val=globalThres();
      // ImageProcess process=new ImageProcess(w,h,temp);
     //  process.imageSmooth();
    //   pix=process.getPixel();
       //temp=process.getTemp();
   }
   private void shiftPix(){
       int t=0;
       for(int i=0;i<h;i++){
        for(int j=0;j<w;j++){
              pix[t]=(255-pix[t])<<24;
              temp[j][i]=(255-temp[j][i])<<24;
              t++;
        }
       }
   }

   //gets the graylevel value of image
    public int[][] getGraylevel(BufferedImage bImg){
            int[][] twoD=new int[w][h];
            int index=0;   //count the no of pixel
            for (int y =0; y<h;y++){
                 for (int x =0; x<w;x++){
                        Color c = new Color(bImg.getRGB(x,y));
                        int r=c.getRed();
                        int g=c.getGreen();
                        int b=c.getBlue();
                        int a=c.getAlpha();
                        int graylevel=(int) Math.max(0,Math.min(255, 0.299*r + 0.587*g + 0.114*b));
                        twoD[x][y]=graylevel;
                        pix[index]=(255-graylevel)<<24;    //for display
                        pixGlobal[index]=graylevel;        //for global thresholding
                        //pix[index1]=graylevel;//(255-graylevel)<<24;
                        index++;
                 }
            }
            return twoD;
   }

    //apply global thresholding method to calculate the threshold value
     private int globalThres(){
       float min=0,max=0,S1=0,S2=0,C1=0,C2=0,T=0;
       int[] p=new int[w*h];
       for(int i=0;i<w*h;i++){
           p[i]=pixGlobal[i];
       }
       Arrays.sort(p);
       min=p[0];
       max=p[p.length-1];
       do{
           T=(min+max)/2;
           S1=0;S2=0;C1=0;C2=0;
           for(int i=0;i<w*h;i++){
               if(pixGlobal[i]>T){
                  S1=S1 +pixGlobal[i];
                  C1++;
               }
               else{
                   S2=S2 +pixGlobal[i];
                  C2++;
               }
           }
           min=S1/C1;
           max=S2/C2;
           System.out.println(T);
       }while(T!=(min+max)/2);
      return (int) T;
   }

     //normalise the iris annular region into rectangular template
     public void normalizeIris(float pupil_x, float pupil_y, float inner_rad, float outer_rad){
            map = new BufferedImage(n_w, n_h, BufferedImage.TYPE_INT_RGB);
            int iris_width = (int) (outer_rad - inner_rad);
            for (double i=0; i<n_w; i++) { // every 360/256 degrees
                final double radians = Math.toRadians((i)*360/n_w+1);
                for (double j=0; j<n_h; j++) { // every 1/32th out
                    double r = inner_rad +(iris_width)*(j+1)/n_h;
                    int x = (int) (pupil_x +r*Math.sin(radians));
                    int y = (int) (pupil_y +r*Math.cos(radians));
                    try {
                            map.setRGB((int)i,(int)j,im.getRGB(x,y));
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }
            for (double i=n_w*.25; i<n_w*.75; i++) { // every 360/256 degrees
                for (double j=0; j<n_h; j++) { // every 1/32th out
                            map.setRGB((int)i,(int)j,0<<255);
                }
            }
            //new ViewImage(map);
            Image ima=Toolkit.getDefaultToolkit().createImage(map.getSource());
            int[] orig=new int[w*h];
            PixelGrabber grabber = new PixelGrabber(ima, 0, 0, n_w, n_h, orig, 0, n_w);
            try {
                    grabber.grabPixels();
            }
            catch(InterruptedException e2) {
                    System.out.println("error: " + e2);
            }
            //img = createImage(new MemoryImageSource(n_w, n_h, orig,0,n_w));
   }
}
