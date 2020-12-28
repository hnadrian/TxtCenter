package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Formatter;

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
	
	public static File save(String newContent, File currentFile) throws IOException {
		Formatter form = new Formatter(currentFile);
		form.format("%s", newContent);
		form.close();
		return currentFile;
	}
	
	public static File create(String content) throws IOException {
		String initialDir = System.getProperty("user.dir");
		FileChooser fc = new FileChooser();
		fc.setTitle("Open File");
		fc.setInitialDirectory(new File(initialDir));
		File newFile = fc.showSaveDialog(null);
		Formatter form = new Formatter(newFile);
		form.format("%s", content);
		form.close();
		return newFile;
	}
	
	public static String readFile(File file) throws IOException {
		StringBuilder text = new StringBuilder();
		String fileContent = "";
		
		return Files.readString(file.toPath());
	}
}
