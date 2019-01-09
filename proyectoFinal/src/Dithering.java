import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.Random;

public class Dithering {
    
    public Image scale(Image source, int w, int h) {
	ImageView imageView = new ImageView(source);
	imageView.setFitWidth(w);
	imageView.setFitHeight(h);
	return imageView.snapshot(null, null);
    }
    
    public Image dithering(Image im){
        Filtros filt = new Filtros();
        Image imagen = filt.grey1(im);
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        int areaw = 3; 
        int areah = 3;
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
                int caso = 0;
                if (promedio >= 0.0 && promedio <= 0.1 ) caso = 0;
                if (promedio > 0.1 && promedio <= 0.2 ) caso = 1;
                if (promedio > 0.2 && promedio <= 0.3 ) caso = 2;
                if (promedio > 0.3 && promedio <= 0.4 ) caso = 3;
                if (promedio > 0.4 && promedio <= 0.5 ) caso = 4;
                if (promedio > 0.5 && promedio <= 0.6 ) caso = 5;
                if (promedio > 0.6 && promedio <= 0.7 ) caso = 6;
                if (promedio > 0.7 && promedio <= 0.8 ) caso = 7;
                if (promedio > 0.8 && promedio <= 0.9 ) caso = 8;
                if (promedio > 0.9 && promedio <= 1.0 ) caso = 9;
                for(int m = 0; m < areaw; m++){
		    for(int n = 0; n < areah; n++){
			Color nucol = Color.color(1,1,1);
			switch (caso) {
			case 9: break;
			case 8: if (m == 1 && n == 1) nucol = Color.color(0,0,0);
			    break;
			case 7: if ((m == 1 && n == 1) || (m == 0 && n == 1)) nucol = Color.color(0,0,0);
			    break;
			case 6: if ((m == 1 && n == 1) || (m == 0 && n == 1) || (m == 1 && n == 2)) nucol = Color.color(0,0,0);
			    break;
			case 5: if ((n == 1) || (m == 1 && n == 2)) nucol = Color.color(0,0,0);
			    break;
			case 4: if ((n == 1) || (m == 1 && n == 2) || (m == 2 && n == 0)) nucol = Color.color(0,0,0);
			    break;
			case 3: if ((n == 1) || (m == 1 && n == 2) || (m == 2 && n == 0) || (m == 0 && n == 2)) nucol = Color.color(0,0,0);
			    break;
			case 2: if ((n == 1) || (m == 1 && n == 2) || (m == 2 && n == 0) || (m == 0 && n == 2) || (m == 0 && n == 0) ) nucol = Color.color(0,0,0);
			    break;
			case 1: if ((n == 1) || (m == 1 && n == 2) || (m == 2 && n == 0) || (m == 0 && n == 2) || (m == 0 && n == 0) || (m == n)) nucol = Color.color(0,0,0);
			    break;
			case 0: if ((n >= 0) || (m >=0)) nucol = Color.color(0,0,0);
			    break;
			}
			if (i+m < imagen.getWidth() && j+n < imagen.getHeight() ) pw.setColor(i+m,j+n,nucol);
                    }
                }
            }
        }
        return (Image) wi;
	
    }

    
    
    
    public Image randomDither(Image im){
	Filtros filt = new Filtros();
	Random rnd = new Random();
	Image imagen = filt.grey1(im);
	PixelReader pr = imagen.getPixelReader();
	WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	double ancho = imagen.getWidth();
	double alto = imagen.getHeight();
	
	for(int i = 0; i < ancho; i++){
            for (int j = 0; j < alto; j++){
                double rando = rnd.nextDouble();
                double r = 0;
                Color col = pr.getColor(i,j);    
                r = col.getRed();
                
                if (r > rando) r = 1;
                else r = 0;
                
                Color nucol = Color.gray(r);
                
                pw.setColor(i, j, nucol);
                
            }   
        }
        return (Image) wi;
	
    }
    
    public Image fsDither(Image im){
	Filtros filt = new Filtros();
	Image imagen = filt.grey5(im);
	PixelReader pr = imagen.getPixelReader();
	WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	int ancho = (int)imagen.getWidth();
	int alto = (int)imagen.getHeight();
	int[][] aagregar = new int[ancho][alto];      
        
        for(int i = 0; i < ancho; i++){
            for (int j = 0; j < alto; j++){
                Color col = pr.getColor(i,j);   
                int r = (int) (col.getRed()*255);
                int add = aagregar[i][j];
                int newpix = 0;
                r = r+add;
                if (r > 128) newpix = 255;
                int error = r - newpix;
                
                Color nucol = Color.grayRgb(newpix);
                int nucol1 = error*7/16;
                int nucol2 = error*3/16;
                int nucol3 = error*5/16;
                int nucol4 = error*1/16;
                
                pw.setColor(i, j, nucol);
                if (i+1 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+1][j] += nucol1;
                if (i-1 > -1 && j+1 < imagen.getHeight() ) aagregar[i-1][j+1] += nucol2;
                if (i < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i][j+1] += nucol3;
                if (i+1 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+1][j+1] += nucol4;
                
            }   
        }
        return (Image) wi;
	
    }
    
    public Image ffsDither(Image im){
	Filtros filt = new Filtros();
	Image imagen = filt.grey5(im);
	PixelReader pr = imagen.getPixelReader();
	WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	int ancho = (int)imagen.getWidth();
	int alto = (int)imagen.getHeight();
	int[][] aagregar = new int[ancho][alto];      
        
        for(int i = 0; i < ancho; i++){
            for (int j = 0; j < alto; j++){
                Color col = pr.getColor(i,j);   
                int r = (int) (col.getRed()*255);
                int add = aagregar[i][j];
                int newpix = 0;
                r = r+add;
                if (r > 128) newpix = 255;
                int error = r - newpix;
                
                Color nucol = Color.grayRgb(newpix);
                int nucol1 = error*3/8;
                int nucol2 = error*3/8;
                int nucol3 = error*2/8;
                
                pw.setColor(i, j, nucol);
                if (i+1 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+1][j] += nucol1;
                if (i < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i][j+1] += nucol2;
                if (i+1 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+1][j+1] += nucol3;
                
            }   
        }
        return (Image) wi;
	
    }
    
    public Image jjnDither(Image im){
	Filtros filt = new Filtros();
	Image imagen = filt.grey5(im);
	PixelReader pr = imagen.getPixelReader();
	WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	int ancho = (int)imagen.getWidth();
	int alto = (int)imagen.getHeight();
	int[][] aagregar = new int[ancho][alto];      
        
        for(int i = 0; i < ancho; i++){
            for (int j = 0; j < alto; j++){
                Color col = pr.getColor(i,j);   
                int r = (int) (col.getRed()*255);
                int add = aagregar[i][j];
                int newpix = 0;
                r = r+add;
                if (r > 128) newpix = 255;
                int error = r - newpix;
                
                Color nucol = Color.grayRgb(newpix);
                int nucol1 = error*7/48;
                int nucol2 = error*5/48;
                int nucol3 = error*3/48;
                int nucol4 = error*5/48;
                int nucol5 = error*7/48;
                int nucol6 = error*5/48;
                int nucol7 = error*3/48;
                int nucol8 = error*1/48;
                int nucol9 = error*3/48;
                int nucol10 = error*5/48;
                int nucol11 = error*3/48;
                int nucol12 = error*1/48;
                
                pw.setColor(i, j, nucol);
                if (i+1 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+1][j] += nucol1;
                if (i+2 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+2][j] += nucol2;
                if (i-2 > -1 && j+1 < imagen.getHeight() ) aagregar[i-2][j+1] += nucol3;
                if (i-1 > -1 && j+1 < imagen.getHeight() ) aagregar[i-1][j+1] += nucol4;
                if (i < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i][j+1] += nucol5;
                if (i+1 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+1][j+1] += nucol6;
                if (i+2 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+2][j+1] += nucol7;
                if (i-2 > -1 && j+2 < imagen.getHeight() ) aagregar[i-2][j+2] += nucol8;
                if (i-1 > -1 && j+2 < imagen.getHeight() ) aagregar[i-1][j+2] += nucol9;
                if (i < imagen.getWidth() && j+2 < imagen.getHeight() ) aagregar[i][j+2] += nucol10;
                if (i+1 < imagen.getWidth() && j+2 < imagen.getHeight() ) aagregar[i+1][j+2] += nucol11;
                if (i+2 < imagen.getWidth() && j+2 < imagen.getHeight() ) aagregar[i+2][j+2] += nucol12;
                
            }   
        }
        return (Image) wi;
	
    }
    
    public Image sDither(Image im){
	Filtros filt = new Filtros();
	Image imagen = filt.grey5(im);
	PixelReader pr = imagen.getPixelReader();
	WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	int ancho = (int)imagen.getWidth();
	int alto = (int)imagen.getHeight();
	int[][] aagregar = new int[ancho][alto];      
        
        for(int i = 0; i < ancho; i++){
            for (int j = 0; j < alto; j++){
                Color col = pr.getColor(i,j);   
                int r = (int) (col.getRed()*255);
                int add = aagregar[i][j];
                int newpix = 0;
                r = r+add;
                if (r > 128) newpix = 255;
                int error = r - newpix;
                
                Color nucol = Color.grayRgb(newpix);
                int nucol1 = error*8/42;
                int nucol2 = error*4/42;
                int nucol3 = error*2/42;
                int nucol4 = error*4/42;
                int nucol5 = error*8/42;
                int nucol6 = error*4/42;
                int nucol7 = error*2/42;
                int nucol8 = error*1/42;
                int nucol9 = error*2/42;
                int nucol10 = error*4/42;
                int nucol11 = error*2/42;
                int nucol12 = error*1/42;
                
                pw.setColor(i, j, nucol);
                if (i+1 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+1][j] += nucol1;
                if (i+2 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+2][j] += nucol2;
                if (i-2 > -1 && j+1 < imagen.getHeight() ) aagregar[i-2][j+1] += nucol3;
                if (i-1 > -1 && j+1 < imagen.getHeight() ) aagregar[i-1][j+1] += nucol4;
                if (i < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i][j+1] += nucol5;
                if (i+1 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+1][j+1] += nucol6;
                if (i+2 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+2][j+1] += nucol7;
                if (i-2 > -1 && j+2 < imagen.getHeight() ) aagregar[i-2][j+2] += nucol8;
                if (i-1 > -1 && j+2 < imagen.getHeight() ) aagregar[i-1][j+2] += nucol9;
                if (i < imagen.getWidth() && j+2 < imagen.getHeight() ) aagregar[i][j+2] += nucol10;
                if (i+1 < imagen.getWidth() && j+2 < imagen.getHeight() ) aagregar[i+1][j+2] += nucol11;
                if (i+2 < imagen.getWidth() && j+2 < imagen.getHeight() ) aagregar[i+2][j+2] += nucol12;
                
            }   
        }
        return (Image) wi;
	
    }
    
    public Image aDither(Image im){
	Filtros filt = new Filtros();
	Image imagen = filt.grey5(im);
	PixelReader pr = imagen.getPixelReader();
	WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	int ancho = (int)imagen.getWidth();
	int alto = (int)imagen.getHeight();
	int[][] aagregar = new int[ancho][alto];      
        
        for(int i = 0; i < ancho; i++){
            for (int j = 0; j < alto; j++){
                Color col = pr.getColor(i,j);   
                int r = (int) (col.getRed()*255);
                int add = aagregar[i][j];
                int newpix = 0;
                r = r+add;
                if (r > 128) newpix = 255;
                int error = r - newpix;
                
                Color nucol = Color.grayRgb(newpix);
                int nucol1 = error*1/8;
                
                pw.setColor(i, j, nucol);
                if (i+1 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+1][j] += nucol1;
                if (i+2 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+2][j] += nucol1;
                if (i-1 > -1 && j+1 < imagen.getHeight() ) aagregar[i-1][j+1] += nucol1;
                if (i < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i][j+1] += nucol1;
                if (i+1 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+1][j+1] += nucol1;
                if (i < imagen.getWidth() && j+2 < imagen.getHeight() ) aagregar[i][j+2] += nucol1;
                
            }   
        }
        return (Image) wi;
	
    }
    
    public Image bDither(Image im){
	Filtros filt = new Filtros();
	Image imagen = filt.grey5(im);
	PixelReader pr = imagen.getPixelReader();
	WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	int ancho = (int)imagen.getWidth();
	int alto = (int)imagen.getHeight();
	int[][] aagregar = new int[ancho][alto];      
        
        for(int i = 0; i < ancho; i++){
            for (int j = 0; j < alto; j++){
                Color col = pr.getColor(i,j);   
                int r = (int) (col.getRed()*255);
                int add = aagregar[i][j];
                int newpix = 0;
                r = r+add;
                if (r > 128) newpix = 255;
                int error = r - newpix;
                
                Color nucol = Color.grayRgb(newpix);
                int nucol1 = error*8/32;
                int nucol2 = error*4/32;
                int nucol3 = error*2/32;
                int nucol4 = error*4/32;
                int nucol5 = error*8/32;
                int nucol6 = error*4/32;
                int nucol7 = error*2/32;
                
                pw.setColor(i, j, nucol);
                if (i+1 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+1][j] += nucol1;
                if (i+2 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+2][j] += nucol2;
                if (i-2 > -1 && j+1 < imagen.getHeight() ) aagregar[i-2][j+1] += nucol3;
                if (i-1 > -1 && j+1 < imagen.getHeight() ) aagregar[i-1][j+1] += nucol4;
                if (i < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i][j+1] += nucol5;
                if (i+1 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+1][j+1] += nucol6;
                if (i+2 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+2][j+1] += nucol7;
                
            }   
        }
        return (Image) wi;
	
    }
    
    public Image sierraDither(Image im){
	Filtros filt = new Filtros();
	Image imagen = filt.grey5(im);
	PixelReader pr = imagen.getPixelReader();
	WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	int ancho = (int)imagen.getWidth();
	int alto = (int)imagen.getHeight();
	int[][] aagregar = new int[ancho][alto];      
        
        for(int i = 0; i < ancho; i++){
            for (int j = 0; j < alto; j++){
                Color col = pr.getColor(i,j);   
                int r = (int) (col.getRed()*255);
                int add = aagregar[i][j];
                int newpix = 0;
                r = r+add;
                if (r > 128) newpix = 255;
                int error = r - newpix;
                
                Color nucol = Color.grayRgb(newpix);
                int nucol1 = error*5/32;
                int nucol2 = error*3/32;
                int nucol3 = error*2/32;
                int nucol4 = error*4/32;
                int nucol5 = error*5/32;
                int nucol6 = error*4/32;
                int nucol7 = error*2/32;
                int nucol8 = error*2/32;
                int nucol9 = error*3/32;
                int nucol10 = error*2/32;
                
                pw.setColor(i, j, nucol);
                if (i+1 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+1][j] += nucol1;
                if (i+2 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+2][j] += nucol2;
                if (i-2 > -1 && j+1 < imagen.getHeight() ) aagregar[i-2][j+1] += nucol3;
                if (i-1 > -1 && j+1 < imagen.getHeight() ) aagregar[i-1][j+1] += nucol4;
                if (i < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i][j+1] += nucol5;
                if (i+1 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+1][j+1] += nucol6;
                if (i+2 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+2][j+1] += nucol7;
                if (i-1 > -1 && j+2 < imagen.getHeight() ) aagregar[i-1][j+2] += nucol8;
                if (i < imagen.getWidth() && j+2 < imagen.getHeight() ) aagregar[i][j+2] += nucol9;
                if (i+1 < imagen.getWidth() && j+2 < imagen.getHeight() ) aagregar[i+1][j+2] += nucol10;
                
            }   
        }
        return (Image) wi;
	
    }
    
    public Image trSierraDither(Image im){
	Filtros filt = new Filtros();
	Image imagen = filt.grey5(im);
	PixelReader pr = imagen.getPixelReader();
	WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	int ancho = (int)imagen.getWidth();
	int alto = (int)imagen.getHeight();
	int[][] aagregar = new int[ancho][alto];      
        
        for(int i = 0; i < ancho; i++){
            for (int j = 0; j < alto; j++){
                Color col = pr.getColor(i,j);   
                int r = (int) (col.getRed()*255);
                int add = aagregar[i][j];
                int newpix = 0;
                r = r+add;
                if (r > 128) newpix = 255;
                int error = r - newpix;
                
                Color nucol = Color.grayRgb(newpix);
                int nucol1 = error*4/16;
                int nucol2 = error*3/16;
                int nucol3 = error*1/16;
                int nucol4 = error*2/16;
                int nucol5 = error*3/16;
                int nucol6 = error*2/16;
                int nucol7 = error*1/16;
		
                pw.setColor(i, j, nucol);
                if (i+1 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+1][j] += nucol1;
                if (i+2 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+2][j] += nucol2;
                if (i-2 > -1 && j+1 < imagen.getHeight() ) aagregar[i-2][j+1] += nucol3;
                if (i-1 > -1 && j+1 < imagen.getHeight() ) aagregar[i-1][j+1] += nucol4;
                if (i < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i][j+1] += nucol5;
                if (i+1 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+1][j+1] += nucol6;
                if (i+2 < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i+2][j+1] += nucol7;
                
            }   
        }
        return (Image) wi;
	
    }
    
    public Image sierraLDither(Image im){
	Filtros filt = new Filtros();
	Image imagen = filt.grey5(im);
	PixelReader pr = imagen.getPixelReader();
	WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	int ancho = (int)imagen.getWidth();
	int alto = (int)imagen.getHeight();
	int[][] aagregar = new int[ancho][alto];      
        
        for(int i = 0; i < ancho; i++){
            for (int j = 0; j < alto; j++){
                Color col = pr.getColor(i,j);   
                int r = (int) (col.getRed()*255);
                int add = aagregar[i][j];
                int newpix = 0;
                r = r+add;
                if (r > 128) newpix = 255;
                int error = r - newpix;
                
                Color nucol = Color.grayRgb(newpix);
                int nucol1 = error*2/4;
                int nucol2 = error*1/4;
                int nucol3 = error*1/4;
		
                pw.setColor(i, j, nucol);
                if (i+1 < imagen.getWidth() && j < imagen.getHeight() ) aagregar[i+1][j] += nucol1;
                if (i-1 > -1 && j+1 < imagen.getHeight() ) aagregar[i-1][j+1] += nucol2;
                if (i < imagen.getWidth() && j+1 < imagen.getHeight() ) aagregar[i][j+1] += nucol3;
                
            }   
        }
        return (Image) wi;
	
    }
}
