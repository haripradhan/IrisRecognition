
package Detection;

import java.util.Arrays;

/**
 *
 * @author STARLING
 */
public class ImageProcess {
    private int[][] temp;
    private int w;
    private int h;
    private int[] pix;
    

    public ImageProcess(int width,int height,int[][] pix){
        w=width;
        h=height;
        temp=pix;
        initArray();
    }
    private void initArray(){
        pix=new int[w*h];
       
    }
    //applying image enhancement technique
    public void imageSmooth(){
        performFilt();performFilt();
        equalizeGrayScale();
        System.out.println("working fine");
    }
    public int[] getPixel(){
        return pix;
    }
    public int[][] getTemp(){
        return temp;
    }
    //performing median filter to remove spike noises
    private void performFilt(){
       int window=5;
       int edgeX=window/5;
       int edgeY=window/5;
       for(int x=edgeX;x<w-window;x++){
           for(int y=edgeY;y<h-window;y++){
               int[] temp1=new int[window*window];
               int count=0;
               for(int fx=0;fx<window;fx++){
                   for(int fy=0;fy<window;fy++){
                       temp1[count]=temp[x+fx-edgeX][y+fy-edgeY];
                       count++;
                   }
               }
               Arrays.sort(temp1);
               int pos=(int)(window*window/2)+1;
               //outputPixelValue[x][y]=temp1[pos];
               temp[x][y]=temp1[pos];
           }
       }
       convertToOne();
   }

    //converting pixels in 2D array to that in 1D array
    private void convertToOne(){
      int t=0;
     
      for(int i=0;i<h;i++)
        for(int j=0;j<w;j++)
              pix[t++]=temp[j][i];//(255-temp[j][i])<<24;
     

       }
     //converting pixels in 1D array to that in 2D array
    private void convertToTwo(){
      int t=0;
     
      for(int i=0;i<h;i++)
        for(int j=0;j<w;j++)
              temp[j][i]=pix[t++];//(255-temp[j][i])<<24;
     

       }

    //performing histogram equalization 
    private void equalizeGrayScale(){

		int L=256;
        int[] hist=new int[L];


       int max_x=w;

       int max_y=h;

       	for (int i = 0; i < pix.length; i++) {
			hist[pix[i]]++;
		}

		int sum = 0;

        for(int x = 0; x < hist.length; x++){
			sum += hist[x];
			hist[x] = sum * L /(max_x*max_y);
		}
		
        for (int i = 0; i < pix.length; i++) {
			  pix[i]=hist[pix[i]];// (255-hist[pix[i]])<<24;
		}

       convertToTwo();


	}
}
