import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.scene.image.WritableImage;

public class Filtros {
    
    public Image mosaic(Image imagen, int areaw, int areah){
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
    
    public Image bright(Image imagen, int br){
        double brf = ((double)br)/255;
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        for(int i = 0; i < imagen.getWidth();i++){
            for (int j = 0; j < imagen.getHeight(); j++){
            Color col = pr.getColor(i,j);
            double r = col.getRed();
            if (r+brf >= 1) {
                r = r-brf;
            }
            double g = col.getGreen();
            if (g+brf >= 1) {
                g = g-brf;
            }
            double b = col.getBlue();
            if (b+brf >= 1) {
                b = b-brf;
            }
              Color ncol = Color.color(r+brf,g+brf,b+brf);
              pw.setColor(i,j, ncol);
            
            }
        }
        return (Image) wi;
    }
    

    
    public Image selRGB(Image imagen, int red, int green, int blue){
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
