package application;

import java.io.File;
import java.io.IOException;

import org.fxmisc.richtext.StyleClassedTextArea;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class MainController {
	@FXML
	protected StyleClassedTextArea textArea;
	
	@FXML
	private Label wordCount;

	@FXML
	private ToolBar toolBar;

	@FXML
	private HBox infoBar;

	@FXML
	private ComboBox<String> fontFamBox;

	@FXML
	private ComboBox<String> hColorBox;

	@FXML
	private ComboBox<String> fontSizeBox;

	private File currentFile;
	private String currentContent;
	private String fileContent = "";
	private boolean modified = false;
	private int wCount;
	private static final String DEFAULT_TITLE = "Untitled";
	private static final String DEFAULT_TEXT_SIZE = "Medium";
	private static final String RTFX_FILE_EXTENSION = ".rtfx";
	private static final String RTF_FILE_EXTENSION = ".rtf";

	BooleanProperty selectedToolBar;
	BooleanProperty selectedInfoBar;

	public void initialize() {
		selectedToolBar = new SimpleBooleanProperty(true);
		toolBar.managedProperty().bind(selectedToolBar);
		selectedInfoBar = new SimpleBooleanProperty(true);
		infoBar.managedProperty().bind(selectedInfoBar);
		Main.getStage().setTitle(DEFAULT_TITLE);

		//Font Text Box Handling
		ObservableList<String> fonts = FXCollections.observableArrayList(Font.getFamilies());
		fontFamBox.getItems().addAll(fonts);
		fontFamBox.valueProperty().setValue("Arial");
		
		//Hightlight Tex Box Handling
		ObservableList<String> highlights = FXCollections.observableArrayList("transparent", "yellow", "lime",
				"orangered", "orange", "cyan");
		hColorBox.setItems(highlights);
		Callback<ListView<String>, ListCell<String>> factory = new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new ColorRectCell();
			}
		};
		hColorBox.setCellFactory(factory);
		hColorBox.setButtonCell(factory.call(null));

		//textArea.setStyle("-fx-font-size: " + DEFAULT_TEXT_SIZE + ";");
		textArea.setStyle("fs-medium");
		
		//Font size handling with comboBox
		ObservableList<String> fontSizes = FXCollections.observableArrayList("XX-Small", "X-Small", "Small",
				"Medium", "Large", "X-Large", "XX-Large");
		fontSizeBox.setItems(fontSizes);
		fontSizeBox.valueProperty().setValue(DEFAULT_TEXT_SIZE);
		
		//Word count Handling
		wordCount.textProperty().setValue("(0 words)");
		//Listening for changes in textArea to update word Count
		textArea.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
	            updateWordCount(newValue);
	        }
	    });
		
		//Listening for changes of Caret Position to update toolBar (under developement)
		textArea.caretPositionProperty().addListener((observable, oldValue,
	            newValue) -> {
	            	
	            });
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
			wCount = 0;
		}
	}

	public void openFile() throws IOException {
		String initialDir = System.getProperty("user.dir");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load document");
		fileChooser.setInitialDirectory(new File(initialDir));
		fileChooser.setSelectedExtensionFilter(
				new FileChooser.ExtensionFilter("*" + RTFX_FILE_EXTENSION, "*" + RTF_FILE_EXTENSION));
		File selectedFile = fileChooser.showOpenDialog(Main.getStage());
		if (selectedFile != null) {
			textArea.clear();
			FileOperations.open(selectedFile, textArea);
		}
		setFileTitle(selectedFile.getName());
		setFile(selectedFile);
	}

	public void saveFile() throws IOException {
		String initialDir = System.getProperty("user.dir");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save document");
		fileChooser.setInitialDirectory(new File(initialDir));
		fileChooser.setInitialFileName("Untitled" + RTFX_FILE_EXTENSION);
		File selectedFile = fileChooser.showSaveDialog(Main.getStage());
		if (selectedFile != null) {
			FileOperations.save(selectedFile, textArea);
		}
	}

	public void saveFileAs() throws IOException {
		setFile(FileOperations.create(textArea.getText()));
	}
	
	public void updateFileType() {
		
	}
	
	public void updateWordCount(String newContent) {
		int wCount = 0;
		if (newContent != null) {
			wCount = newContent.split("\\s+").length;
		}
		wordCount.textProperty().setValue("(" + wCount + " words" + ")");
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

	public void selectAll() {
		textArea.selectAll();
	}

	public void closeApp(ActionEvent event) {
		Platform.exit(); // Exit from JavaFx
		System.exit(0);
	}

	// Should I update content to match
	
	public void toggleBold() {
		StyleOperations.updateStyleInSelection(textArea, "bold", textArea.getSelection());
	}

	public void toggleItalic() {
		StyleOperations.updateStyleInSelection(textArea, "italic", textArea.getSelection());
	}

	public void toggleUnderline() {
		StyleOperations.updateStyleInSelection(textArea, "underline", textArea.getSelection());
	}

	public void toggleStrike() {
		StyleOperations.updateStyleInSelection(textArea, "strike", textArea.getSelection());
	}

	public void toggleAlignLeft() {
		StyleOperations.updateParagraphStyleInSelection(textArea, "align-left", textArea.getSelection());
	}

	public void toggleAlignCenter() {
		StyleOperations.updateParagraphStyleInSelection(textArea, "align-center", textArea.getSelection());
	}

	public void toggleAlignRight() {
		StyleOperations.updateParagraphStyleInSelection(textArea, "align-right", textArea.getSelection());
	}

	public void toggleJustify() {
		StyleOperations.updateParagraphStyleInSelection(textArea, "justify", textArea.getSelection());
	}

	public void updateFontFam() {
		textArea.setStyle("-fx-font-family: " + fontFamBox.getValue() + ";");
	}

	public void updateFontSize() {
		String newSize = fontSizeBox.getValue();
		StyleOperations.updateStyleInSelection(textArea, "fs-" + newSize.toLowerCase(), textArea.getSelection());
	}

	public void updateHColor() {
		String newHColor = hColorBox.getValue();
		StyleOperations.updateStyleInSelection(textArea, "h-" + newHColor, textArea.getSelection());
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

	static String colorToCss(Color color) {
		int red = (int) (color.getRed() * 255);
		int green = (int) (color.getGreen() * 255);
		int blue = (int) (color.getBlue() * 255);
		return "rgb(" + red + ", " + green + ", " + blue + ")";
	}
	
	//Colorizing Highlighter Combobox
	static class ColorRectCell extends ListCell<String> {
		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			Rectangle rect = new Rectangle(13, 15);
			if (item != null) {
				rect.setFill(Color.web(item));
				setGraphic(rect);
			}
		}
	}

}
