import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.image.WritableImage;
import java.lang.Math;

public class OleoAtt {
    
    public OleoAtt(){
	
    }
    
    public Image oleo(Image imagen, int hw, int intensity) {
	PixelReader pr = imagen.getPixelReader();
	WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	
	int filterh = hw;
	int filterw = hw;
	double w = imagen.getWidth();
	double h = imagen.getHeight();
	
	for(int i = 0; i < w; i++){
	    for (int j = 0; j < h; j++){
		int[] intensities = new int[intensity];
		int[] reds = new int[intensity];
		int[] greens = new int[intensity];
		int[] blues = new int[intensity];
		
		for(int filterY = 0; filterY < filterh ; filterY++)
		    for(int filterX = 0; filterX < filterw ; filterX++){
			int imageX = (int) ((i - filterw / 2 + filterX + w) % w);
			int imageY = (int) ((j - filterh / 2 + filterY + h) % h);
			Color col = pr.getColor(imageX,imageY);
			double r = col.getRed() * 255;
			double g = col.getGreen() * 255;
			double b = col.getBlue() * 255;
			int intenrgb = (int) Math.floor((((r+g+b)/3)*intensity)/255);
			if (intenrgb == intensity) intenrgb = intensity-1;
			intensities[intenrgb] += 1;
			reds[intenrgb] += r;
			greens[intenrgb] += g;
			blues[intenrgb] += b;
			
		    }
		int mostintense = getIndexOfLargest(intensities);
		int frequency = intensities[mostintense];
		int r = reds[mostintense] / frequency;
		int g = greens[mostintense] / frequency;
		int b = blues[mostintense] / frequency;
		Color ncol = Color.rgb(r,g,b);
		pw.setColor(i, j, ncol);
		
	    }       
	}
	
	return (Image) wi;
    }
    
    public int getIndexOfLargest( int[] array ){
	if ( array == null || array.length == 0 ) return -1;
        int largest = 0;
        for ( int i = 1; i < array.length; i++ ){
	    if ( array[i] > array[largest] ) largest = i;
        }
	return largest;
    }
    
    public Image att(Image img, int region){
	WritableImage wi = new WritableImage((int)img.getWidth(), (int)img.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	
	Filtros filt = new Filtros();
	Image im = filt.contrast(img);
	PixelReader pr = im.getPixelReader();
	
	int areah = region*2+1;
	int areaw = 1;
	
	double w = im.getWidth();
	double h = im.getHeight();
        for(int i = 0; i < im.getWidth(); i++){
            for (int j = 0; j < im.getHeight(); j += areah){
                int contadornegros = 0;
                Color ncol;
		for(int n = 0; n < areah; n++){
		    if (i+1 < im.getWidth() && j+n < im.getHeight() ) {
			Color col = pr.getColor(i+1,j+n);    
			double blanco = col.getRed();
			if (blanco == 0.0) contadornegros += 1;
		    }
		}
                
                int bordearriba = (j + region) - (contadornegros/2);
                int bordeabajo = (j + region) + (contadornegros/2);
                for(int m = 0; m < areaw; m++){
		    for(int n = 0; n < areah; n++){            
			if (j+n > bordearriba && j+n < bordeabajo){
			    ncol = Color.color(0, 0, 0);   
			} else {ncol = Color.color(1, 1, 1);}
			if (i+m < w && j+n < h ) pw.setColor(i+m, j+n, ncol);
		    }
                    if (j+region < h) pw.setColor(i+m, j+region, Color.color(0, 0, 0));
                }
                
            }
        }
        return (Image) wi;
    }
    
    
    public Image luznegra(Image imagen, int depth){
	PixelReader pr = imagen.getPixelReader();
	WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
	PixelWriter pw = wi.getPixelWriter();
	for(int i = 0; i < imagen.getWidth();i++){
	    for (int j = 0; j < imagen.getHeight(); j++){
		Color col = pr.getColor(i,j);
		double r = col.getRed()*255;
		double g = col.getGreen()*255;
		double b = col.getBlue()*255;
		double lum = (222*r + 707*g + 71*b) / 1000;
		double nr = Math.abs(r-lum) * depth;
		double ng = Math.abs(g-lum) * depth;
		double nb = Math.abs(b-lum) * depth;
		if(nr > 255) nr = 255;
		if(ng > 255) ng = 255;
		if(nb > 255) nb = 255;
		Color ncol = Color.rgb((int)nr,(int)ng,(int)nb);
		pw.setColor(i,j, ncol);
		
            }
        }
        return (Image) wi;
    }
    
}
