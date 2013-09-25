package gui;

import maps.Constants;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
 * @author Max er best
 */
public class MapPanel extends JPanel {
	
	public MapPanel() {
		initAndShowMap();
	}

	private static void initAndShowMap() {
		JFrame frame = new JFrame("Kartpanel");
		final JFXPanel fxPanel = new JFXPanel();
		frame.add(fxPanel);
		frame.setSize(660, 520);
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
    	return new Scene(buildWebView(Constants.pathToHtml), 658, 498, Color.WHITE);
    }
   
	private static WebView buildWebView(String url) {
	    WebView webView = WebViewBuilder.create().prefHeight(480).prefWidth(640).build();
	    webView.getEngine().javaScriptEnabledProperty().set(true);
		webView.getEngine().load(MapPanel.class.getResource(url).toExternalForm());
	    return webView;
	}
	
//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//		
//		@Override
//		public void run() {
//			MapPanel p = new MapPanel();
//		}
//	});
//   }
}