package application;

import java.io.File;
import javafx.stage.FileChooser;

public class FileOperations {
	public File open() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Plain Text Files", "*.txt"));
		File selectedFile = fc.showOpenDialog(null);
		return selectedFile;
	}
}
