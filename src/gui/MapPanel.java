package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.scene.web.WebViewBuilder;

/**
 * Klasse for å generere kartpanel
 * 
 * @author Håkon Løvdal
 */
public class MapPanel {

	private static void initAndShow() {
		JFrame frame = new JFrame("Kartpanel");
		final JFXPanel fxPanel = new JFXPanel();
		frame.add(fxPanel);
		frame.setSize(660, 511);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				initFX(fxPanel);
			}
		});
	}

   
   private static void initFX(JFXPanel fxPanel) {
      Scene scene = createScene();
      fxPanel.setScene(scene);
   }
   
   private static Scene createScene() {
    	 return new Scene(buildWebView("/maps/kart.html"), 658, 498, Color.WHITE);
   }
   
   private static WebView buildWebView(String url) {
	      WebView webView = WebViewBuilder.create().prefHeight(480).prefWidth(640).build();
	      webView.getEngine().javaScriptEnabledProperty().set(true);
		  webView.getEngine().load(MapPanel.class.getResource(url).toExternalForm());
	      return webView;
	   }


   /**
    * Main-funksjon for testing  av dette JavaFX karteksempelet.
    * 
    * @param args (Fra command-line, ingen forventet)
    */
   public static void main(String[] args) {
	   SwingUtilities.invokeLater(new Runnable() {
		
		@Override
		public void run() {
			initAndShow();
		}
	});
   }
}