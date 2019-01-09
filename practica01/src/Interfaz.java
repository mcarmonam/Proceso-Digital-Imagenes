import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.PopupWindow;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

public class Interfaz extends Application{
      
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        Text desc = new Text("Editando: ");
        BorderPane border = new BorderPane();
        
        GridPane grid = new GridPane();
        border.setTop(addMenu(primaryStage,border,grid));
        
        
        Group root = new Group(border);
        
        Scene scene = new Scene(root, 1070, 650);
        scene.setFill(Color.FLORALWHITE);
        primaryStage.setTitle("Proceso Digital de Imágenes");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
    
    public HBox addMenu(Stage primaryStage, BorderPane bp, GridPane grid){
      HBox hbox = new HBox();
      
      Menu mfile = new Menu("Archivo");
      MenuItem nim = new MenuItem("Seleccionar Imagen");
        nim.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                final File f = selImagen(primaryStage);
                try {
                    if (f != null) {
                        GridPane gp = addGridPane(grid);
                        Image im = addImages(new FileInputStream(f),grid);
                        bp.setCenter(gp);
                    }
                } catch(FileNotFoundException fe){}
            }
        });
      mfile.getItems().add(nim);
      
      Filtros filter = new Filtros();
      
      Menu mfilt = new Menu("Filtros");
      MenuItem ms = new MenuItem("Mosaico");
        ms.setOnAction(
        new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setMosaicPopUp(primaryStage,grid);
            }
         });
    
      MenuItem tg1 = new MenuItem("Tono de Gris (Promedio)");
        tg1.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) grid.getChildren().get(1);
            Image im = imv.getImage();
            ImageView iv = new ImageView(filter.grey1(im));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            grid.add(iv,1,0);
          }
        });
      MenuItem tg2 = new MenuItem("Tono de Gris (R,R,R)");
        tg2.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) grid.getChildren().get(1);
            Image im = imv.getImage();
            ImageView iv = new ImageView(filter.grey2(im));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            grid.add(iv,1,0);
          }
        });
      MenuItem tg3 = new MenuItem("Tono de Gris (G,G,G)");
        tg3.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) grid.getChildren().get(1);
            Image im = imv.getImage();
            ImageView iv = new ImageView(filter.grey3(im));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            grid.add(iv,1,0);
          }
        });
      MenuItem tg4 = new MenuItem("Tono de Gris (B,B,B)");
        tg4.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) grid.getChildren().get(1);
            Image im = imv.getImage();
            ImageView iv = new ImageView(filter.grey4(im));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            grid.add(iv,1,0);
          }
        }); 
      MenuItem tg5 = new MenuItem("Tono de Gris (Constante)");
        tg5.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) grid.getChildren().get(1);
            Image im = imv.getImage();
            ImageView iv = new ImageView(filter.grey5(im));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            grid.add(iv,1,0);
          }
        });
        
      MenuItem in = new MenuItem("Inverso");
        in.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) grid.getChildren().get(1);
            Image im = imv.getImage();
            ImageView iv = new ImageView(filter.inverse(im));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            grid.add(iv,1,0);
          }
        });
      MenuItem ct = new MenuItem("Alto Contraste");
        ct.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) grid.getChildren().get(1);
            Image im = imv.getImage();
            ImageView iv = new ImageView(filter.contrast(im));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            grid.add(iv,1,0);
          }
        });
      MenuItem br = new MenuItem("Brillo");
      br.setOnAction(
        new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setBrightPopUp(primaryStage,grid);
            }
         });
      MenuItem crgb = new MenuItem("Componente RGB");
        crgb.setOnAction(
        new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setRGBPopUp(primaryStage,grid);
            }
         });
      MenuItem r = new MenuItem("Rojo");
        r.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) grid.getChildren().get(1);
            Image im = imv.getImage();
            ImageView iv = new ImageView(filter.red(im));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            grid.add(iv,1,0);
          }
        });
      MenuItem v = new MenuItem("Verde");
        v.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) grid.getChildren().get(1);
            Image im = imv.getImage();
            ImageView iv = new ImageView(filter.green(im));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            grid.add(iv,1,0);
          }
        });
      MenuItem a = new MenuItem("Azul");
        a.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) grid.getChildren().get(1);
            Image im = imv.getImage();
            ImageView iv = new ImageView(filter.blue(im));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            grid.add(iv,1,0);
          }
        });
      
      mfilt.getItems().addAll(ms,tg1,tg2,tg3,tg4,tg5,in,ct,br,crgb,r,v,a);
        
      MenuBar mbar = new MenuBar();
      mbar.getMenus().addAll(mfile,mfilt);
      hbox.getChildren().add(mbar);
      return hbox;
    }
    
    public void setMosaicPopUp(Stage primaryStage, GridPane biggrid){
        
      Stage dialog = new Stage();
      dialog.setTitle("Tamaño mosaico");
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(primaryStage);
      
      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(25, 25, 25, 25));
      
      Text spec = new Text("Tamano del área");
      Label w = new Label("Ancho:");
      Label h = new Label("Alto:");
      grid.add(spec, 0,0,2,1);
      grid.add(w,0,1);
      grid.add(h,0,2);
      
      TextField wi = new TextField();
      grid.add(wi,1,1);
      TextField he = new TextField();
      grid.add(he,1,2);
      
      Filtros filter = new Filtros();
      Button btn = new Button("Aplicar");
      btn.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) biggrid.getChildren().get(1);
            Image im = imv.getImage();
            int width = Integer.parseInt(wi.getText());
            int height = Integer.parseInt(he.getText());
            ImageView iv = new ImageView(filter.mosaic(im,width,height));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            biggrid.add(iv,1,0);
            dialog.hide();
          }
        });
      HBox hbBtn = new HBox(10);
      hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
      hbBtn.getChildren().add(btn);
      grid.add(hbBtn, 1, 4);
      

      Scene scene = new Scene(grid, 300, 275);
      dialog.setScene(scene);
      dialog.show();
    }
    
    public void setBrightPopUp(Stage primaryStage, GridPane biggrid){
        
      Stage dialog = new Stage();
      dialog.setTitle("Constante de brillo");
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(primaryStage);
      
      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(25, 25, 25, 25));
      
      Text spec = new Text("Constante de brillo:");
      Label b = new Label("Brillo:");
      grid.add(spec, 0,0,1,1);
      grid.add(b,0,1);
      
      TextField br = new TextField();
      grid.add(br,1,1);

      
      Filtros filter = new Filtros();
      Button btn = new Button("Aplicar");
      btn.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) biggrid.getChildren().get(1);
            Image im = imv.getImage();
            int bri = Integer.parseInt(br.getText());
            ImageView iv = new ImageView(filter.bright(im,bri));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            biggrid.add(iv,1,0);
            dialog.hide();
          }
        });
      HBox hbBtn = new HBox(10);
      hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
      hbBtn.getChildren().add(btn);
      grid.add(hbBtn, 1, 4);
      

      Scene scene = new Scene(grid, 300, 275);
      dialog.setScene(scene);
      dialog.show();
    }
    
    public void setRGBPopUp(Stage primaryStage, GridPane biggrid){
        
      Stage dialog = new Stage();
      dialog.setTitle("RGB");
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(primaryStage);
      
      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(25, 25, 25, 25));
      
      Text spec = new Text("Valores RGB");
      Label rl = new Label("R:");
      Label gl = new Label("G:");
      Label bl = new Label("B:");
      grid.add(spec, 0,0);
      grid.add(rl,0,1);
      grid.add(gl,0,2);
      grid.add(bl,0,3);
      
      TextField rt = new TextField();
      grid.add(rt,1,1);
      TextField gt = new TextField();
      grid.add(gt,1,2);
      TextField bt = new TextField();
      grid.add(bt,1,3);
      
      Filtros filter = new Filtros();
      Button btn = new Button("Aplicar");
      btn.setOnAction(new EventHandler<ActionEvent>(){
          public void handle(ActionEvent t) {
            ImageView imv = (ImageView) biggrid.getChildren().get(1);
            Image im = imv.getImage();
            int red = Integer.parseInt(rt.getText());
            int green = Integer.parseInt(gt.getText());
            int blue = Integer.parseInt(bt.getText());
            ImageView iv = new ImageView(filter.selRGB(im,red,green,blue));
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            biggrid.add(iv,1,0);
            dialog.hide();
          }
        });
      HBox hbBtn = new HBox(10);
      hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
      hbBtn.getChildren().add(btn);
      grid.add(hbBtn, 1, 4);
      

      Scene scene = new Scene(grid, 300, 275);
      dialog.setScene(scene);
      dialog.show();
    }
    
    public File selImagen(Stage primaryStage){
      FileChooser fc = new FileChooser();
      configureFileChooser(fc);
      File file = fc.showOpenDialog(primaryStage);
      return file;
    }
    
    
    private static void configureFileChooser(final FileChooser fileChooser){                           
        fileChooser.setTitle("Seleccionar foto");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        ); 
    }
    public GridPane addGridPane(GridPane grid){
      
      grid.setAlignment(Pos.CENTER);
      grid.setGridLinesVisible(true);
      grid.setHgap(20.0);
      grid.setVgap(20.0);
      grid.setPadding(new Insets(25,25,25,25));
      
      return grid;
    }
    
    public Image addImages(FileInputStream fis, GridPane grid){
      Image image1 = new Image(fis);
      ImageView iv1 = new ImageView(image1);
      iv1.setFitWidth(500);
      iv1.setPreserveRatio(true);
      ImageView iv2 = new ImageView(image1);
      iv2.setFitWidth(500);
      iv2.setPreserveRatio(true);
      
      grid.add(iv1,0,0);
      grid.add(iv2,1,0);
      
      return image1;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
