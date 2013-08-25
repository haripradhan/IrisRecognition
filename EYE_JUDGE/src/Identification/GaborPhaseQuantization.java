package Identification;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author STARLING
 */
public class GaborPhaseQuantization {
    private BufferedImage im;
    private static int h,w;
    static int templateSize,Size;
    double S,F,W,P;
    double[][] templateEven;
    double[][] templateOdd;
    int[][] outputReal;
    int[][] outputImg;
    double[] leftReal,leftImg,rightReal,rightImg;
    double[] real,img;
    double[][] input;

    public GaborPhaseQuantization(BufferedImage map){
       im = map;
       h=im.getHeight();
       w=im.getWidth();
       Size=31;
       templateSize=2*Size+1;
       S=0.05;
       F=0.025;
       W=0;
       P=0;
       templateEven=new double[Size*2+1][Size*2+1];
       templateOdd=new double[Size*2+1][Size*2+1];
       input=new double[w][h];
    }

    //create the template of Gabor Filter
    public void createTemplate(){
        double c=-Math.PI*(F/S)*(F/S);
        double a,b;
        for(int y =-Size; y <= Size; y++) {
                for(int x =-Size; x <=Size; x++) {
                         a=Math.exp(-Math.PI*S*S*(x*x+y*y));
                         b=2*Math.PI*F*(x*Math.cos(W)+y*Math.sin(W))+P;
                        templateEven[Size+x][Size+y] = (a * Math.cos(b) - a * Math.exp(c) * Math.cos(P));
                        templateOdd[Size+x][Size+y]=  (a * Math.sin(b) - a * Math.exp(c) * Math.sin(P));
                        //System.out.print(templateEven[x][y]+" ");//+ templateOdd[x][y]+"\t");
              }
               // System.out.println("");
          }
        transposeTemplate();
        rotateTemplate();
    }
    //transpose the template
    public void transposeTemplate(){
        double temp=0;
         for(int y=0;y<templateSize;y++){
            for(int x=0;x<templateSize;x++){
                if(x>y){
                    temp=templateEven[y][x];
                    templateEven[y][x]=templateEven[x][y];
                    templateEven[x][y]=temp;

                    temp=templateOdd[y][x];
                    templateOdd[y][x]=templateOdd[x][y];
                    templateOdd[x][y]=temp;
                }
            }
        }
     
        for(int y=0;y<templateSize;y++){
            for(int x=0;x<templateSize;x++){
               //System.out.print(templateEven[x][y]+" ");//+ templateOdd[x][y]+"\t");
            }
       }
    }

    //get graylevel value from  bufferedimage
    public void createPixel(){
       for (int y =0; y<h;y++){
          for (int x =0; x<w;x++){
                    Color cl = new Color(im.getRGB(x,y));
                    int rd=cl.getRed();
                    int gn=cl.getGreen();
                    int bl=cl.getBlue();
                    int graylevel=(int) Math.max(0,Math.min(255, 0.299*rd + 0.587*gn + 0.114*bl));
                  //input[index1]=(255-graylevel)<<24;    //for display
                    input[x][y]=graylevel;        //for global thresholding
                    //System.out.print(graylevel+"\t");
          }
        }
    }

    //rotate the template
    public void rotateTemplate(){
        double temp=0;int flag=0;
        for(int y=0;y<templateSize/2+1;y++){
            for(int x=0;x<templateSize;x++){

                if(y==templateSize/2&&x==y){
                    flag=1;
                    break;

                }
                temp=templateEven[x][y];
                templateEven[x][y]=templateEven[templateSize-1-x][templateSize-1-y];
                templateEven[templateSize-1-x][templateSize-1-y]=temp;

                temp=templateOdd[x][y];
                templateOdd[x][y]=templateOdd[templateSize-1-x][templateSize-1-y];
                templateOdd[templateSize-1-x][templateSize-1-y]=temp;
            }
            if (flag==1) break;
        }
    }

    //convolution of the gabor filter with image
    public void convolve(){
        double[][] tempImage=new double[w+(templateSize/2)*2][h+(templateSize/2)*2] ;
        for(int y=0;y<h+(templateSize/2)*2;y++){
            for(int x=0;x<w+(templateSize/2)*2;x++){
                tempImage[x][y]=0;
            }
        }
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                tempImage[x+templateSize/2][y+templateSize/2]=input[x][y];
            }
        }
        
        outputReal=new int[w][h];
        outputImg=new int[w][h];
        
        real=new double[w*h];
        img=new double[w*h];
        int index=0,index1=0;
        for(int y=templateSize/2; y<h+templateSize/2;y++){
            for(int x=templateSize/2; x<w+templateSize/2;x++){
                //outputReal[x-templateSize/2][y-templateSize/2]=singlePixelConvolution(tempImage,x,y,templateEven,templateSize,templateSize);
                //outputImg[x-templateSize/2][y-templateSize/2]=singlePixelConvolution(tempImage,x,y,templateOdd,templateSize,templateSize);
                //System.out.println("sdfa");
                //System.out.print(outputReal[x-templateSize/2][y-templateSize/2]+" "+outputImg[x-templateSize/2][y-templateSize/2]+"\t");
                if(singlePixelConvolution(tempImage,x,y,templateEven,templateSize,templateSize)>0){
                    outputReal[x-templateSize/2][y-templateSize/2] = 1;
                    real[index++]=1;
                }
                 else{
                   outputReal[x-templateSize/2][y-templateSize/2]=0;
                   real[index++]=0;
                 }
                if(singlePixelConvolution(tempImage,x,y,templateOdd,templateSize,templateSize)>0){
                    outputImg[x-templateSize/2][y-templateSize/2]= 1;
                    img[index1]=1;
                }
                 else{
                    outputImg[x-templateSize/2][y-templateSize/2]=0;
                    img[index1]=0;
                 }
                //System.out.print(outputReal[j][i]+" "+outputImg[j][i]+"\t");
         }
      }
    }
    //returns the convolved value of each pixel of an image
    //(convolution of single pixel with gabor filter's template)
    public  double singlePixelConvolution(double [][] tempImage,int x, int y,double [][] k,int kernelWidth, int kernelHeight){
                double output=0;
                for(int y1=0;y1<templateSize;y1++) {
                        for(int x1=0;x1<templateSize;x1++) {
                                int x2 = (x-templateSize/2+x1);
                                int y2 = (y-templateSize/2+y1);
                                double value = (tempImage[x2][y2] * (k[x1][y1]));
                                output += value;
                         }
               }
      return output;
  }
    public int[][] getOutputReal(){
        return outputReal;
    }
    public int[][] getOutputImg(){
        return outputImg;
    }
    
}

