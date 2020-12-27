package application;

import java.io.File;
import java.io.IOException;

import org.fxmisc.richtext.StyleClassedTextArea;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;

public class MainController {
	@FXML
	private StyleClassedTextArea textArea;

	@FXML
	private ToolBar toolBar;
	
	@FXML
	private HBox infoBar;
	
	BooleanProperty selectedToolBar;
	BooleanProperty selectedInfoBar;
	
	public void initialize() {
		selectedToolBar = new SimpleBooleanProperty(true);
		toolBar.managedProperty().bind(selectedToolBar);
		selectedInfoBar = new SimpleBooleanProperty(true);
		infoBar.managedProperty().bind(selectedInfoBar);
	}

	public void newFile(ActionEvent event) {

	}

	public void openFile() throws IOException {
		File selectedFile = FileOperations.open();
		if (selectedFile != null) {
			textArea.replaceText(FileOperations.readFile(selectedFile));
		}
	}
	
	public void clearAll() {
		textArea.clear();
	}
	
	public void undo() {
		textArea.undo();
	}
	
	public void redo() {
		textArea.redo();
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
