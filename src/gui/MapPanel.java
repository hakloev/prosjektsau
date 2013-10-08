package gui;

import javax.swing.JPanel;

import utils.Constants;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.web.WebViewBuilder;


/**
 * Class to generate map-panel
 * 
 * @author Håkon Ødegård Løvdal
 */
public class MapPanel extends JPanel {
	
	private ProgramFrame programFrame;
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
		final JFXPanel fxPanel = new JFXPanel();
		add(fxPanel);
		setSize(660, 520);
		setVisible(true);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				initFX(fxPanel);
			}
		});
	}
	
	/**
	 * Initiates the JavaFX-panel. Takes the panel as an argument
	 * 
	 * @param fxPanel
	 */
	private void initFX(JFXPanel fxPanel) {
		Scene scene = createScene();
		fxPanel.setScene(scene);
    }
   
	/**
	 * Returns the scene inside the JavaFX-panel
	 * 
	 * @return scene
	 */
	private Scene createScene() {
    	return new Scene(buildWebView(Constants.pathToHtml), 658, 498, Color.WHITE);
    }
	
	/**
	 * Builds and returns the WebEngine and WebView
	 * Takes the url to to map.html as a parameter
	 * 
	 * @param url
	 * @return webView
	 */
	private WebView buildWebView(String url) {
	    webView = WebViewBuilder.create().prefHeight(480).prefWidth(640).build();
	    webView.getEngine().javaScriptEnabledProperty().set(true); 
		webView.getEngine().load(MapPanel.class.getResource(url).toExternalForm());
		webEngine = webView.getEngine();
	    return webView;
	}
	
	/**
	 * Adds a marker to the map, by calling a JavaScript in map.html
	 * 
	 * @param title
	 * @param lat
	 * @param lng
	 */
	protected void addMarker(final String title,final double lat, final double lng){
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				webEngine.getLoadWorker().stateProperty().addListener(
						new ChangeListener<State>(){
							public void changed(ObservableValue ov, State oldState, State newState){
								if(newState == State.SUCCEEDED){
									webEngine.executeScript("addMarker('" + title + "', " + lat + ", " + lng + ")");
								}
							}
						}
					);
			}
		});
	}
	
	protected void deleteMarkers() {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				webEngine.getLoadWorker().stateProperty().addListener(
						new ChangeListener<State>(){
							public void changed(ObservableValue ov, State oldState, State newState){
								if(newState == State.SUCCEEDED){
									webEngine.executeScript("deleteMarkers()");
								}
							}
						}
						);
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
 //  }
}