package gui;

import utils.Constants;

import javax.swing.JPanel;

import com.sun.javafx.application.PlatformImpl;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.web.WebViewBuilder;

/**
 * Class to generate map-panel
 * @author Håkon Ødegård Løvdal
 * @author Thomas Mathiesen
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
	 * @param url URL path to the map.html-file
	 */
	private void buildWebEngine(String url) {
		webEngine = webView.getEngine();
		webEngine.javaScriptEnabledProperty().set(true); 
		webEngine.load(MapPanel.class.getResource(url).toExternalForm());

	}

	/**
	 * Adds a marker to the map, by calling a JavaScript in map.html
	 * @param title Sheeps nickname
	 * @param lat latiude position
	 * @param lng longtiude position
	 */
	public void addMarker(final String title, final double lat, final double lng) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				webEngine.executeScript("addMarker('" + title + "', " + lat + ", " + lng + ")");
			}
		});
	}

	
	/**
	 * Adds a polygon-area to the map, by calling a JavaScript in map.html
	 */
	public void addArea() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				
				String yolo ="62.219955,9.555143,62.075598,9.139037,61.828282,9.518065,62.134708,10.005584,62.219956,9.555144";

				webEngine.executeScript("addPoly('" + yolo + "')");
				webEngine.executeScript("showAreas()");
			}
		});
	}
	
	


	/**
	 * Removes polygon from map by calling a JavaScript in map.html
	 */
	public void removePoly() {/////////////////////FUNGERER IKKE FORELØPIG, VENTER PÅ USER-P/////////////
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				webEngine.executeScript("removePoly()");
			}
		});
	}


	/**
	 * Deletes all markers from the map by calling a JavaScript in map.html
	 */
	protected void deleteMarkers() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				webEngine.executeScript("deleteMarkers()");
			}
		});
	}

	
	/**
	 * Deletes all markers from the map by calling a JavaScript in map.html
	 */
	public void addTestSheep() {///////////////////////////////////////////////////KUN TIL TEST////////////////////////
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				webEngine.executeScript("addTestSheep()");
			}
		});
	}



	/**
	 * Main method for testing 
	 * 
	 * @param String[] args
	 */
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

