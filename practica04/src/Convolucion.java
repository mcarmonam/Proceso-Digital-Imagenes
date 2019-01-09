import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.scene.image.WritableImage;
import java.lang.Math;

public class Convolucion {

    public Convolucion() {
    }
    
    public Image conv(Image imagen, double[][] filter, int hw, double factor, double bias) {
      PixelReader pr = imagen.getPixelReader();
      WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
      PixelWriter pw = wi.getPixelWriter();
      
      int filterh = hw;
      int filterw = hw;
      double w = imagen.getWidth();
      double h = imagen.getHeight();
      
      for(int i = 0; i < w; i++){
        for (int j = 0; j < h; j++){
          double red = 0.0;
          double green = 0.0;
          double blue = 0.0;
          
            for(int filterY = 0; filterY < filterh ; filterY++)
              for(int filterX = 0; filterX < filterw ; filterX++){
                  int imageX = (int) ((i - filterw / 2 + filterX + w) % w);
                  int imageY = (int) ((j - filterh / 2 + filterY + h) % h);
                  Color col = pr.getColor(imageX,imageY);
                  double r = col.getRed();
                  double g = col.getGreen();
                  double b = col.getBlue();
                  red += (r*255) * filter[filterY][filterX];
                  green += (g*255) * filter[filterY][filterX];
                  blue += (b*255) * filter[filterY][filterX];
                  
              }
        int nr = Math.min(Math.max( (int) (red*factor + bias), 0), 255);
        int ng = Math.min(Math.max( (int) (green*factor + bias), 0), 255);
        int nb = Math.min(Math.max( (int) (blue*factor + bias), 0), 255);
        Color ncol = Color.rgb(nr,ng,nb);
        pw.setColor(i, j, ncol);
        
        }       
      }
      
      return (Image) wi;
    }
    
    public Image blur3(Image imagen){
        double[][] filter = {{0,1,0}, {1,1,1}, {0,1,0}};
        return conv(imagen,filter,3,1/5.0,0.0);
    }
    
    public Image blur5(Image imagen){
        double[][] filter = {{0,0,1,0,0}, {0,1,1,1,0}, {1,1,1,1,1}, {0,1,1,1,0}, {0,0,1,0,0}};
        return conv(imagen,filter,5,1/13.0,0.0);
    }
    
    public Image mblur3(Image imagen){
        double[][] filter = {{1,0,0}, {0,1,0}, {0,0,1}};
        return conv(imagen,filter,3,1/3.0,0.0);
    }
    
    public Image mblur9(Image imagen, int hw){
        double[][] filter = new double[hw][hw];
        for(int i = 0; i < hw ; i++)
          for(int j = 0; j < hw ; j++){
              if (j == 1) filter[i][j] = 1;
          }
        return conv(imagen,filter,hw,1/9.0,0.0);
    }
    
    public Image edgesv(Image imagen){
        double[][] filter = {{0,0,-1,0,0}, {0,0,-1,0,0}, {0,0,4,0,0}, {0,0,-1,0,0}, {0,0,-1,0,0}};
        return conv(imagen,filter,5,1,0.0);
    }
    
    public Image edgesh(Image imagen){
        double[][] filter = {{0,0,-1,0,0}, {0,0,-1,0,0}, {0,0,2,0,0}, {0,0,0,0,0}, {0,0,0,0,0}};
        return conv(imagen,filter,5,1,0.0);
    }
    
    public Image edges45(Image imagen){
        double[][] filter = {{-1,0,0,0,0}, {0,-2,0,0,0}, {0,0,6,0,0}, {0,0,0,-2,0}, {0,0,0,0,-1}};
        return conv(imagen,filter,5,1,0.0);
    }
    
    public Image edgese(Image imagen){
        double[][] filter = {{-1,-1,-1}, {-1,8,-1}, {-1,-1,-1}};
        return conv(imagen,filter,3,1,0.0);
    }
    
    public Image sharpen3(Image imagen){
        double[][] filter = {{-1,-1,-1}, {-1,9,-1}, {-1,-1,-1}};
        return conv(imagen,filter,3,1,0.0);
    }
    
    public Image sharpen5(Image imagen){
        double[][] filter = {{-1,-1,-1,-1,-1}, {-1,2,2,2,-1}, {-1,2,8,2,-1}, {-1,2,2,2,-1},{-1,-1,-1,-1,-1}};
        return conv(imagen,filter,5,1/8.0,0.0);
    }
    
    public Image emboss3(Image imagen){
        double[][] filter = {{-1,-1,0}, {-1,0,1}, {0,1,1}};
        return conv(imagen,filter,3,1,128.0);
    }
    
    public Image emboss5(Image imagen){
        double[][] filter = {{-1,-1,-1,-1,0}, {-1,-1,-1,0,1}, {-1,-1,0,1,1}, {-1,0,1,1,1},{0,1,1,1,1}};
        return conv(imagen,filter,5,1,128.0);
    }
}
