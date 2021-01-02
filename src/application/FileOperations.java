package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Formatter;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.reactfx.util.Tuple2;
import org.fxmisc.richtext.model.Codec;
import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
import org.fxmisc.richtext.model.StyledDocument;
import org.fxmisc.richtext.model.StyledSegment;

import javafx.stage.FileChooser;

public class FileOperations {
	public static File open() throws IOException {
		String initialDir = System.getProperty("user.dir");
		FileChooser fc = new FileChooser();
		fc.setTitle("Open File");
		fc.setInitialDirectory(new File(initialDir));
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Plain Text Files", "Arbitrary RTFX file", "*.txt", "*.rtf", "*.rtfx"));
		File selectedFile = fc.showOpenDialog(null);
		return selectedFile;
	}

	public static File save(String newContent, File currentFile) throws IOException {
		Formatter form = new Formatter(currentFile);
		form.format("%s", newContent);
		form.close();
		return currentFile;
	}

	// FOR FORMAT

	public static void save(File file, StyleClassedTextArea textArea) {

		StyledDocument<Collection<String>, String, Collection<String>> doc = textArea.getDocument();
		textArea.getStyleCodecs().ifPresent(codecs -> {
			Codec<StyledDocument<Collection<String>, String, Collection<String>>> codec = ReadOnlyStyledDocument
					.codec(codecs._1, codecs._2, textArea.getSegOps());
			try {
				FileOutputStream fos = new FileOutputStream(file);
				DataOutputStream dos = new DataOutputStream(fos);
				codec.encode(dos, doc);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	// FOR FORMAT:

	public static void open(File file, StyleClassedTextArea textArea) {
		if (textArea.getStyleCodecs().isPresent()) {
			Tuple2<Codec<Collection<String>>, Codec<StyledSegment<String, Collection<String>>>> codecs = textArea
					.getStyleCodecs().get();
			Codec<StyledDocument<Collection<String>, String, Collection<String>>> codec = ReadOnlyStyledDocument
					.codec(codecs._1, codecs._2, textArea.getSegOps());

			try {
				FileInputStream fis = new FileInputStream(file);
				DataInputStream dis = new DataInputStream(fis);
				StyledDocument<Collection<String>, String, Collection<String>> doc = codec.decode(dis);
				fis.close();

				if (doc != null) {
					textArea.replaceSelection(doc);
					return;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
