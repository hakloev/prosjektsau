package gui;

import maps.Constants;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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
import javafx.concurrent.Worker.State;


/**
 * Klasse for �� generere kartpanel
 * 
 * @author Max er best
 */
public class MapPanel extends JPanel {
	
	private ProgramFrame programFrame;
	
	private WebEngine webEngine;

	private WebView webView;
	
	public MapPanel(ProgramFrame programFrame) {
		this.programFrame = programFrame;
		initAndShowMap();
		addMarker("bob",63,10);
	}

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
	
	private void initFX(JFXPanel fxPanel) {
		Scene scene = createScene();
		fxPanel.setScene(scene);
    }
   
	private Scene createScene() {
    	return new Scene(buildWebView(Constants.pathToHtml), 658, 498, Color.WHITE);
    }
	
	public void addMarker(final String title,final double lat, final double lng){
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
   
	private WebView buildWebView(String url) {
	    webView = WebViewBuilder.create().prefHeight(480).prefWidth(640).build();
	    webView.getEngine().javaScriptEnabledProperty().set(true); 
		webView.getEngine().load(MapPanel.class.getResource(url).toExternalForm());
		webEngine = webView.getEngine();
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
 //  }
}