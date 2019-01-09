import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.scene.image.WritableImage;

public class Filtros {
    
    public Image mosaic(Image img, int areaw, int areah){
        Convolucion filt = new Convolucion();
        Image imagen = filt.sharpen5(img);
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth(); i = i + areaw){
            for (int j = 0; j < imagen.getHeight(); j = j + areah){
                double r = 0;
                double g = 0;
                double b = 0;
                for(int m = 0; m < areaw; m++){
                   for(int n = 0; n < areah; n++){
                       if (i+m < imagen.getWidth() && j+n < imagen.getHeight() ) {
                       Color col = pr.getColor(i+m,j+n);    
                       double re = col.getRed();
                       r+= re;
                       double gr = col.getGreen();
                       g+= gr; 
                       double bl = col.getBlue();
                       b+= bl;
                       }
                   }
                }
                
                for(int m = 0; m < areaw; m++){
                   for(int n = 0; n < areah; n++){
                       Color nucol = Color.color(r/(areaw*areah),g/(areaw*areah),b/(areaw*areah));
                       if (i+m < imagen.getWidth() && j+n < imagen.getHeight() ) pw.setColor(i+m,j+n,nucol);
                    }
                }
            }
        }
        return (Image) wi;
    }
    
    public Image bright(Image imagen, int brrgb){
        double br = (double) brrgb/255;
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double r = col.getRed();
            if (r+br >= 1) {
                r = 1-br;
            } else if (r+br <= 0) {
                r = br*(-1);
            }
            double g = col.getGreen();
            if (g+br >= 1) {
                g = 1-br;
            } else if (g+br <= 0) {
                g = br*(-1);
            }
            double b = col.getBlue();
            if (b+br >= 1) {
                b = 1-br;
            } else if (b+br <= 0) {
                b = br*(-1);
            }
              Color ncol = Color.color(r+br,g+br,b+br);
              pw.setColor(i,j, ncol);
            }
        }
        return (Image) wi;
    }
    
    public Image selRGB(Image imagen, int red, int green, int blue){
        //double gr = ((double) red)/255;
        //double gg = ((double) green)/255;
        //double gb = ((double) blue)/255;
        if (red > 255) red = 255;
        if (red < 0) red = 0;
        if (green > 255) green = 255;
        if (green < 0) green = 0;
        if (blue > 255) blue = 255;
        if (blue < 0) blue = 0;
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            int r = (int)(col.getRed()*255)&red;
            int g = (int)(col.getGreen()*255)&green;
            int b = (int)(col.getBlue()*255)&blue;
            double grey = (r+g+b)/3;
            Color ncol = Color.rgb(r,g,b);
            pw.setColor(i,j, ncol);
            }
        }
        return (Image) wi;
    }
    
    public Image selRGB1(Image imagen, int red, int green, int blue){
        if (red > 255) red = 255;
        if (red < 0) red = 0;
        if (green > 255) green = 255;
        if (green < 0) green = 0;
        if (blue > 255) blue = 255;
        if (blue < 0) blue = 0;
        double gr = ((double) red)/255;
        double gg = ((double) green)/255;
        double gb = ((double) blue)/255;
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double r = col.getRed();
            double g = col.getGreen();
            double b = col.getBlue();
            double grey = (r+g+b)/3;
            Color ncol = Color.color((r+gr)/2,(g+gg)/2,(b+gb)/2);
            pw.setColor(i,j, ncol);
            
            }
        }
        return (Image) wi;
    }
 
    public Image grey1(Image imagen){
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double r = col.getRed();
            double g = col.getGreen();
            double b = col.getBlue();
            double grey = (r+g+b)/3;
            Color ncol = Color.color(grey,grey,grey);
            pw.setColor(i,j, ncol);
            
            }
        }
        return (Image) wi;
    }
    
    public Image grey2(Image imagen){
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double r = col.getRed();
            Color ncol = Color.color(r,r,r);
            pw.setColor(i,j, ncol);
            
            }
        }
        return (Image) wi;
    }
    
    public Image grey3(Image imagen){
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double g = col.getGreen();
            Color ncol = Color.color(g,g,g);
            pw.setColor(i,j, ncol);
            
            }
        }
        return (Image) wi;
    }
    
    public Image grey4(Image imagen){
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double b = col.getBlue();
            Color ncol = Color.color(b,b,b);
            pw.setColor(i,j, ncol);
            
            }
        }
        return (Image) wi;
    }
    
    public Image grey5(Image imagen){
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double r = col.getRed()*255*0.3;
            double g = col.getGreen()*255*0.59;
            double b = col.getBlue()*255*0.11;
            int rgb = (int) (r+g+b);
            Color ncol = Color.rgb(rgb,rgb,rgb);
            pw.setColor(i,j, ncol);
            
            }
        }
        return (Image) wi;
    }
    
    public Image contrast(Image imagen){
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double r = col.getRed();
            double g = col.getGreen();
            double b = col.getBlue();
            double avg = (r+g+b)/3;
              if (avg < 0.5) {
                pw.setColor(i,j, Color.color(0,0,0));
                
              }else{ 
                pw.setColor(i,j, Color.color(1,1,1));
              }
            }
        }
        return (Image) wi;
    }
    
    public Image inverse(Image imagen){
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double r = col.getRed();
            double g = col.getGreen();
            double b = col.getBlue();
            double avg = (r+g+b)/3;
              if (avg < 0.5) {
                pw.setColor(i,j, Color.color(1,1,1));
                
              }else{ 
                pw.setColor(i,j, Color.color(0,0,0));
              }
            }
        }
        return (Image) wi;
    }
    
    public Image red(Image imagen){
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double r = col.getRed();
            Color ncol = Color.color(r,0,0);
            pw.setColor(i,j, ncol);
            
            }
        }
        return (Image) wi;
    }
    
    public Image green(Image imagen){
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double g = col.getGreen();
            Color ncol = Color.color(0,g,0);
            pw.setColor(i,j, ncol);
            
            }
        }
        return (Image) wi;
    }
    
    public Image blue(Image imagen){
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double b = col.getBlue();
            Color ncol = Color.color(0,0,b);
            pw.setColor(i,j, ncol);
            
            }
        }
        return (Image) wi;
    }
    
   
}
