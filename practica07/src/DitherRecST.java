import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class DitherRecST {
    
    
    public Image halftone(Image im){
        Filtros filt = new Filtros();
        Image imagen = filt.grey1(im);
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        int areaw = 4; 
        int areah = 4;
        double ancho = imagen.getWidth();
        double alto = imagen.getHeight();
        
        for(int i = 0; i < ancho; i += areaw){
            for (int j = 0; j < alto; j += areah){
                double r = 0;
                double g = 0;
                double b = 0;
                for(int m = 0; m < areaw; m++){
                   for(int n = 0; n < areah; n++){
                       if (i+m < ancho && j+n < alto ) {
                       Color col = pr.getColor(i+m,j+n);    
                       double re = col.getRed();
                       r+= re;
                       }
                   }
                }
                
        
                
                double promedio = r/(areaw*areah);
                int caso = 1;
                if (promedio >= 0.2 && promedio < 0.4 ) caso = 2;
                if (promedio >= 0.4 && promedio < 0.6 ) caso = 3;
                if (promedio >= 0.6 && promedio < 0.8 ) caso = 4;
                if (promedio >= 0.8 && promedio <= 1 ) caso = 5;
                for(int m = 0; m < areaw; m++){
                   for(int n = 0; n < areah; n++){
                       Color nucol = Color.color(1,1,1);
                       switch (caso) {
                           case 5: break;
                           case 4: if (m == 0 && n == 0) nucol = Color.color(0,0,0);
                                   break;
                           case 3: if ((m == 0 && n == 0) || (m == 1 && n == 0) || (m == 0 && n == 1)) nucol = Color.color(0,0,0);
                                   break;
                           case 2: if ((m >= 0 && m <= 1) && (n >= 0 && n <= 1)) nucol = Color.color(0,0,0);
                                   break;
                           case 1: if (((m >= 0 && m < 3) && (n >= 0 && n < 3))) nucol = Color.color(0,0,0);
                                   break;
                       }
                       if (i+m < imagen.getWidth() && j+n < imagen.getHeight() ) pw.setColor(i+m,j+n,nucol);
                    }
                }
            }
        }
        return (Image) wi;
    }
}   
