package application;

import java.io.File;

import org.fxmisc.richtext.StyleClassedTextArea;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class MainController {
	@FXML
	private StyleClassedTextArea textA;

	@FXML
	private ToolBar toolBar;
	
	@FXML
	private HBox infoBar;
	
	BooleanProperty selectedToolBar;
	BooleanProperty selectedInfoBar;
	
	public void initialize() {
		selectedToolBar = new SimpleBooleanProperty();
		selectedToolBar.set(true);
		toolBar.managedProperty().bind(selectedToolBar);
		selectedInfoBar = new SimpleBooleanProperty();
		selectedInfoBar.set(true);
		infoBar.managedProperty().bind(selectedInfoBar);
	}

	public void newFile(ActionEvent event) {

	}

	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Plain Text Files", "*.txt", "*.rtf"));
		File selectedFile = fc.showOpenDialog(null);
	}

	public void closeApp(ActionEvent event) {
		Platform.exit(); // Exit from JavaFx
		System.exit(0);
	}
	
	public void toolBarVisible(ActionEvent event) {
		selectedToolBar.set(!selectedToolBar.get());
	}
	
	public void infoBarVisible(ActionEvent event) {
		selectedInfoBar.set(!selectedInfoBar.get());
	}
}
