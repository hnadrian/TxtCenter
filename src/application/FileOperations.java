package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javafx.stage.FileChooser;

public class FileOperations {
	public static File open() throws IOException {
		String initialDir = System.getProperty("user.dir");
		FileChooser fc = new FileChooser();
		fc.setTitle("Open File");
		fc.setInitialDirectory(new File(initialDir));
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Plain Text Files", "Arbitrary RTFX file", "*.txt", "*.rtf"));
		File selectedFile = fc.showOpenDialog(null);
		return selectedFile;
	}
	
	public static String readFile(File file) throws IOException {
		StringBuilder text = new StringBuilder();
		String fileContent = "";
		
		return Files.readString(file.toPath());
	}
}
