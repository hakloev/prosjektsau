package gui;

import java.awt.EventQueue;
import java.security.Principal;
import java.util.Enumeration;

import sun.net.www.content.text.plain;
import utils.Constants;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.javafx.application.PlatformImpl;
import com.sun.media.jfxmediaimpl.platform.PlatformManager;

import utils.Constants;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.web.WebViewBuilder;


/**
 * Class to generate map-panel
 
 * @author Håkon Ødegård Løvdal
 */
public class MapPanel extends JPanel {
	
	private ProgramFrame programFrame;
	private JFXPanel fxPanel;
	private WebEngine webEngine;
	private WebView webView;
	
	public MapPanel(ProgramFrame programFrame) {
		this.programFrame = programFrame;
		initAndShowMap();
	}

	/**
	 * Initiates and show map. Will invoke a JavaFX-panel that works inside the 
	 * swing application
	 * 
	 */
	private void initAndShowMap() {
		fxPanel = new JFXPanel();
		
		PlatformImpl.startup(new Runnable() {
			
			@Override
			public void run() {
				initFX();
			}
		});
		
		add(fxPanel);
		setSize(600, 400);
		setVisible(true);
	}
	
	/**
	 * Initiates the JavaFX-panel
	 * 
	 */
	private void initFX() {
		Group root = new Group();
		Scene scene = new Scene(root, 698, 500);
		webView = WebViewBuilder.create().prefHeight(698).prefWidth(1500).build();
		buildWebEngine(Constants.pathToHtml);
		root.getChildren().add(webView);
		fxPanel.setScene(scene);
    }
	
	/**
	 * Builds the WebEngine
	 * Takes the url to to map.html as a parameter
	 * 
	 * @param url
	 */
	private void buildWebEngine(String url) {
	    webEngine = webView.getEngine();
	    webEngine.javaScriptEnabledProperty().set(true); 
		webEngine.load(MapPanel.class.getResource(url).toExternalForm());
	}
	
	/**
	 * Adds a marker to the map, by calling a JavaScript in map.html
	 * 
	 * @param title
	 * @param lat
	 * @param lng
	 */
	
	public void addMarker(final String title, final double lat, final double lng) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				webEngine.executeScript("addMarker('" + title + "', " + lat + ", " + lng + ")");
			}
		});
	}
	
	protected void deleteMarkers() {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
			webEngine.executeScript("deleteMarkers()");
			}
		});
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

