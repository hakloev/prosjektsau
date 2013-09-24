package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.scene.web.WebViewBuilder;
import javafx.stage.Stage;

/**
 * Klasse for å generere kartpanel
 * 
 * @author Håkon Løvdal
 */
public class MapPanel extends Application {

   /**
    * Lag en WebView-instans
    * 
    * @param url Path til kart.html
    * @return WebView med innhold basert på url/path
    */
   private WebView buildWebView(String url) {
      WebView webView = WebViewBuilder.create().prefHeight(480).prefWidth(640).build();
      webView.getEngine().javaScriptEnabledProperty().set(true);
	  webView.getEngine().load(MapPanel.class.getResource(url).toExternalForm());
      return webView;
   }

   /**
    * JavaFX 2.0's Application.start(Stage) metode.
    * 
    * @param stage Primary stage.
    * @throws Exception 
    */
   @Override
   public void start(Stage stage) throws Exception {
      stage.setTitle("Google Maps: Prosjekt SAU");
      Scene scene = new Scene(buildWebView("/maps/kart.html"), 658, 498, Color.WHITE);
      stage.setScene(scene);
      stage.show();
   }

   /**
    * Main-funksjon for testing  av dette JavaFX karteksempelet.
    * 
    * @param args (Fra command-line, ingen forventet)
    */
   public static void main(String[] args) {
      Application.launch(args);
   }
}