import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.Math;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Mosaico {
    
    File[] dirs;
    
    public Image scale(Image source, int w, int h) {
    ImageView imageView = new ImageView(source);
    imageView.setFitWidth(w);
    imageView.setFitHeight(h);
    return imageView.snapshot(null, null);
    }
    
    public int closest(int num, int[] arrayofn){
      int actual = arrayofn[0];
      int indice = 0;
      for(int i = 1; i < arrayofn.length; i++){
        if(Math.abs(num - arrayofn[i]) < Math.abs(num - actual)){
            actual = arrayofn[i];
            indice = i;
        }
      }
      return indice;
    }
    
    public Image mosaico(Image imagen, File library, int areaw, int areah) throws FileNotFoundException {
      Filtros f = new Filtros();
      int[] valoreslin = processLibrary(library,areaw,areah);
      PixelReader pr = imagen.getPixelReader();
      int ancho = (int) imagen.getWidth();
      int alto = (int) imagen.getHeight();
      WritableImage wi = new WritableImage(ancho, alto);
      PixelWriter pw = wi.getPixelWriter();
      int aa = areaw*areah;
      
      
        for(int i = 0; i < ancho; i += areaw){
            for (int j = 0; j < alto; j += areah){
                double r = 0;
                double g = 0;
                double b = 0;
                for(int m = 0; m < areaw; m++){
                   for(int n = 0; n < areah; n++){
                       if (i+m < ancho && j+n < alto) {
                       Color col = pr.getColor(i+m,j+n);
                       double re = col.getRed()*255;
                       r += re;
                       double gr = col.getGreen()*255;
                       g += gr;
                       double bl = col.getBlue()*255;
                       b += bl;
                       }
                   }
                }
                int aaplicar = (int) (((r+g+b)/(3*aa)) - 128);
                r = r*65535;
                g = g*256;
                int promedio = (int) ((r+g+b)/aa);
                int indice = closest(promedio,valoreslin);
                Image ausaresc = scale(new Image(new FileInputStream(dirs[indice])),areaw,areah);
                Image ausar = f.bright(ausaresc, aaplicar);
                PixelReader prr = ausar.getPixelReader();
                for(int m = 0; m < areaw; m++){
                   for(int n = 0; n < areah; n++){
                       Color col = prr.getColor(m, n);
                       double nr = col.getRed();
                       double ng = col.getGreen();
                       double nb = col.getBlue();
                       Color nucol = Color.color(nr,ng,nb);
                       if (i+m < imagen.getWidth() && j+n < imagen.getHeight() ) pw.setColor(i+m,j+n,nucol);
                    }
                }
            }
        }
    
      return (Image) wi;
    }
    
    public int[] processLibrary(File library, int areaw, int areah) throws FileNotFoundException {
      File[] direcciones = library.listFiles();
      int[] valores = new int[direcciones.length];
      dirs = direcciones;
      for (int i = 0; i < direcciones.length; i++){
        double promedio = 0;
        Image im = scale(new Image(new FileInputStream(direcciones[i])),areaw,areah);
        int ancho = (int) im.getWidth();
        int alto = (int) im.getHeight();
        PixelReader pr = im.getPixelReader();
        for(int n = 0; n < ancho;n++){
            for (int m = 0; m < alto; m++){
            Color col = pr.getColor(n,m);
            double r = col.getRed()*255*65535;
            double g = col.getGreen()*255*256;
            double b = col.getBlue()*255;
            promedio += (r+g+b)/(ancho*alto);
            }
        }
        valores[i] = (int) promedio;
      }
      return valores;
    }
    
}
