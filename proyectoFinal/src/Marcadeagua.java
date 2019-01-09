import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Marcadeagua {
    
    // Representa a cada pixel de una imagen.
    private static Color pixel;
    // Representa a cada componente de un pixel.
    private static double r, g, b, r_gris, g_gris, b_gris, r_contraste, g_contraste, b_contraste;
    // Representa el ancho de una imagen.
    private static int w;
    // Representa el alto de una imagen.
    private static int h;
    // Nos permite crear un "lienzo" para colocar la imagen procesada.
    private static WritableImage imagen_procesada;
    // Nos permite leer los pixeles de una imagen.
    private static PixelReader p_reader;
    // Nos permite escribir sobre un lienzo.
    private static PixelWriter p_writer;
    
    private static Filtros filt = new Filtros();
    
    public static Image quitarMarca1(Image imagen){
        // Guarda el ancho de la imagen pasada como parametro.
        w = (int)imagen.getWidth();
        // Guarda el alto de la imagen pasada como parametro.
        h = (int)imagen.getHeight();
        // Creamos un "lienzo" del mismo tamanio de la imagen pasada como parametro.
        imagen_procesada = new WritableImage(w, h);
        // Nos permite leer los pixeles de la imagen pasada como parametro.
        p_reader = imagen.getPixelReader();
        // Nos permite escribir sobre el lienzo.
        p_writer = imagen_procesada.getPixelWriter();
        
        // Reconocemos la posicion de la marca de agua.
        PixelReader imagen_revelada = revelarMarca(p_reader, w, h).getPixelReader();
        // Procesamos la imagen con el filtro tonos de gris [R, R, R] (Funcionaba mejor que otros).
        //Nos servira de referencia.
        PixelReader imagen_con_tono_gris = filt.grey2(imagen).getPixelReader();
        // Procesamos la imagen con el filtro alto contraste, con un ajuste de brillo de -3. Nos servira de referencia.
        PixelReader imagen_con_alto_contraste;
        imagen_con_alto_contraste = filt.bright(filt.contrast(imagen), -3).getPixelReader();
        
        // Recorremos los pixeles de la imagen revelada.
        for(int x = 0; x < w; x++){
            for(int y = 0; y < h; y++){
                pixel = imagen_revelada.getColor(x, y);
                // Obtenemos los componentes de cada pixel.
                r = pixel.getRed() * 255;
                g = pixel.getGreen() * 255;
                b = pixel.getBlue() * 255;
                // Si son tonos obscuros los ignoramos (No se tratan de la marca de agua).
                if(r < 11 && b < 11 && g < 11)
                    p_writer.setColor(x, y, p_reader.getColor(x, y));
                // Si son claros si nos interesan.
                else{
                    // Recuperamos los componentes del pixel de la imagen en tonos de gris.
                    r_gris = imagen_con_tono_gris.getColor(x, y).getRed();
                    g_gris = imagen_con_tono_gris.getColor(x, y).getGreen();
                    b_gris = imagen_con_tono_gris.getColor(x, y).getBlue();
                    // Recuperamos los componente de la imagen con alto contraste.
                    r_contraste = imagen_con_alto_contraste.getColor(x, y).getRed();
                    g_contraste = imagen_con_alto_contraste.getColor(x, y).getGreen();
                    b_contraste = imagen_con_alto_contraste.getColor(x, y).getBlue();
                    // Procuramos preservar los pixeles blancos del mismo color.
                    if((r_gris < r_contraste) && (g_gris < g_contraste) && (b_gris < b_contraste))
                        p_writer.setColor(x, y, imagen_con_alto_contraste.getColor(x, y));
                    // Los otros los rellenamos con el color en gris.
                    else
                        p_writer.setColor(x, y, imagen_con_tono_gris.getColor(x, y));                    
                }
            }
        }
        return imagen_procesada;
    }
    
    public static Image quitarMarca2(Image imagen){
        PixelReader pr = imagen.getPixelReader();
        WritableImage wi = new WritableImage((int)imagen.getWidth(), (int)imagen.getHeight());
        PixelWriter pw = wi.getPixelWriter();
        Filtros filt = new Filtros();
        //PixelReader fryrg = filt.grey2(filt.red(imagen)).getPixelReader();
        /**
	   for(int i = 0; i < imagen.getWidth();i++){
	   for (int j = 0; j < imagen.getHeight(); j++){
	   Color col = pr.getColor(i,j);
	   double r = col.getRed();
	   double g = col.getGreen();
	   double b = col.getBlue();
           
	   if (r == g && g == b) {
	   pw.setColor(i, j, col);
	   } else {
	   double avg = ((r+b+g)/3);
	   Color ncol = Color.color(avg,avg,avg);
	   pw.setColor(i,j, ncol);
	   }
           
	   }
	   }*/
        return filt.grey2(filt.red(imagen));
    }
    
    /*
     * Metodo auxiliar que nos ayuda a resaltar la marca de agua de una imagen.
     */
    private static Image revelarMarca(PixelReader pr, int w, int h){
        // Creamos un "lienzo" para colocar la imagen revelada..
        WritableImage imagen_revelada = new WritableImage(w, h);
        // Nos permite escribir sobre un lienzo.
        PixelWriter pw = imagen_revelada.getPixelWriter();
        
        // Recorremos pixel por pixel.
        for(int x = 0; x < w; x++){
            for(int y = 0; y < h; y++){
                pixel = pr.getColor(x, y);
                // Como las carcas de agua de estas imagenes son de color rojo,
                // entonces solo nos interesa reconocer los pixeles con un tono rojizo.
                r = 0;
                g = Math.abs(pixel.getGreen() - pixel.getRed());
                b = Math.abs(pixel.getBlue() - pixel.getRed());
                pw.setColor(x, y, Color.color(r, g, b));
            }
        }
        return imagen_revelada;
    }
}
