
package Detection;

/**
 *
 * @author STARLING
 */
public class FindCircle {
    private int h;
    private int w;
    private int[][] temp;
    private int pupil_x;
    private int pupil_y;
    private float pupil_dia;
    private float inner_rad;
    private int iris_x,iris_y;
    private float outer_rad;

    public float getIrisRad(){
        return outer_rad;
    }

     public float getPupilDia(){
        return pupil_dia;
    }
     public float getPupil_x(){
         return pupil_x;
     }
     public float getPupil_y(){
         return pupil_y;
     }
     public float getIris_x(){
         return iris_x;
     }
     public float getIris_y(){
         return iris_y;
     }

    public FindCircle(int width,int height,int[][] temPix){
        w=width;
        h=height;
        temp=temPix;
        
    }

    //finds the pupil boundary
    public void findPupilEdge(){
        int prev=0,now,cnt=0,x1=0,y1=0,x=0,y=0,dia=0;
        for (int j=0;j<h;j++)
            for (int i=0;i<w;i++){
                now=temp[i][j];
                if(now==prev&&now<150){
                    cnt++;
                    if (cnt==1) {x1=i;y1=j;}
                }
                else{
                    if (cnt>dia){
                        dia=cnt;
                        x=x1;y=y1;
                    }
                    cnt=0;
                }
                prev=now;
            }
            pupil_x=x+dia/2;
            pupil_y=y;
            pupil_dia=dia;
            inner_rad=pupil_dia/2;
            System.out.println("pupil_x: "+pupil_x);
            System.out.println("pupil_y: "+pupil_y);
            System.out.println("inner_rad: "+inner_rad);
       }

    //finds the iris boundary
     public void findIrisEdge(){
         intDiff();
     }

     //apply the circular approximation technique
    public void intDiff(){
       int max=0,sum;
       for(int i=pupil_x-3;i<=pupil_x+3;i++){
           for(int j=pupil_y-3;j<=pupil_y+3;j++){
               for(float r=pupil_dia;r<(1.25*pupil_dia);r++){
                   sum=calculateSum(i, j, r);
                   if(sum>max){
                       max=sum;
                       iris_x=i;
                       iris_y=j;
                       outer_rad=r;
                    }
               }
           }
       }
       System.out.println("Iris_x: "+iris_x);
       System.out.println("Iris_y: "+iris_y);
       System.out.println("outer_rad: " +outer_rad);
   }

    //returns the sum of grayvalue of  pixels on the circle
    public int calculateSum(int xc,int yc,float r){
       //create 2D pixel array and initialize Upper limit CS
       int CS=(int) (2 * Math.PI * r);
       double alpha;
       int x,y,I1,I2,I3,I4;
       int sum=0;
       for(int j=0;j<CS;j++){
           alpha= (double) (2 * Math.PI * j / CS);
           x=(int) (xc + r * Math.cos(alpha));
           y=(int) (yc + r * Math.sin(alpha));
           I1=(int) ((temp[x + 1][y] - temp[x - 1][y]) * Math.cos(alpha));
           I2=(int) ((temp[x][y+1] - temp[x][y-1]) * Math.sin(alpha));
           I3=(int) ((temp[x-1][y+1] - temp[x+1][y-1]) * Math.sin(Math.toRadians(45)+alpha));
           I4=(int) ((temp[x+1][y+1] - temp[x-1][y-1]) * Math.cos(Math.toRadians(45)+alpha));
           sum+=(I1+I2+I3+I4);
        }
       return sum;
    }



}
