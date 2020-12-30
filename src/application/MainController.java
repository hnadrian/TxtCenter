package application;

import java.io.File;
import java.io.IOException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.StyleSpans;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;

public class MainController {
	@FXML
	private StyleClassedTextArea textArea;

	@FXML
	private ToolBar toolBar;

	@FXML
	private HBox infoBar;

	private File currentFile;
	private String currentContent;
	private String fileContent = "";
	private boolean modified = false;
	private static final String DEFAULT_TITLE = "Untitled.txt";
	private static final String RTFX_FILE_EXTENSION = ".rtfx";

	BooleanProperty selectedToolBar;
	BooleanProperty selectedInfoBar;

	public void initialize() {
		selectedToolBar = new SimpleBooleanProperty(true);
		toolBar.managedProperty().bind(selectedToolBar);
		selectedInfoBar = new SimpleBooleanProperty(true);
		infoBar.managedProperty().bind(selectedInfoBar);
		Main.getStage().setTitle("Untitled.txt");
	}

	public void newFile(ActionEvent event) {
		updateModified();
		if (modified == true) {
			// Dialog warning
		} else {
			currentFile = null;
			currentContent = "";
			setDefaultTitle();
			clearAll();
		}
	}

	public void openFile() throws IOException {
		File selectedFile = FileOperations.open();
		if (selectedFile != null) {
			textArea.replaceText(FileOperations.readFile(selectedFile));
		} else {
			// Error Message
			return;
		}
		setFileTitle(selectedFile.getName());
		setFile(selectedFile);
	}

	public void saveFile() throws IOException {
		if (currentFile != null) {
			FileOperations.save(textArea.getText(), currentFile);
		} else {
			saveFileAs();
		}
	}

	public void saveFileAs() throws IOException {
		setFile(FileOperations.create(textArea.getText()));
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

	public void toggleBold() {
		TextOperations.updateStyleInSelection(textArea, "bold", textArea.getSelection());
	}

	public void toggleItalic() {
		TextOperations.updateStyleInSelection(textArea, "italic", textArea.getSelection());
	}
	
	public void toggleUnderline() {
		TextOperations.updateStyleInSelection(textArea, "underline", textArea.getSelection());
	}
	
	public void toggleStrike() {
		TextOperations.updateStyleInSelection(textArea, "strike", textArea.getSelection());
	}


	public void toolBarVisible(ActionEvent event) {
		selectedToolBar.set(!selectedToolBar.get());
	}

	public void infoBarVisible(ActionEvent event) {
		selectedInfoBar.set(!selectedInfoBar.get());
	}

	private void setFileTitle(String title) {
		Main.getStage().setTitle(title);
	}

	private void setFile(File newCurrentFile) throws IOException {
		currentFile = newCurrentFile;
		modified = false;
		setTitle(newCurrentFile.getName());
		updateContent(FileOperations.readFile(newCurrentFile));
	}

	private void setTitle(String newTitle) {
		Main.getStage().setTitle(newTitle);
	}

	private void setDefaultTitle() {
		Main.getStage().setTitle(DEFAULT_TITLE);
	}

	private void updateModified() {
		updateContent();
		modified = !currentContent.equals(currentContent);
	}

	private void updateContent() {
		currentContent = textArea.getText();
	}

	private void updateContent(String content) {
		currentContent = content;
	}

}