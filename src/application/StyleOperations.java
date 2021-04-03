package application;

import static org.fxmisc.richtext.model.TwoDimensional.Bias.Backward;
import static org.fxmisc.richtext.model.TwoDimensional.Bias.Forward;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.StyleSpans;

import javafx.scene.control.IndexRange;

public class StyleOperations {
	public static void updateStyleInSelection(StyleClassedTextArea textArea, String styleToAdd, IndexRange selection) {
		if (selection.getLength() != 0) {
			StyleSpans<Collection<String>> styles = textArea.getStyleSpans(selection);
			StyleSpans<Collection<String>> newStyles = styles.mapStyles(styleCol -> {
				ArrayList<String> combineStyles = new ArrayList<>(styleCol);
				if (styleToAdd.startsWith("h-")) {
					for (String colorStyle : combineStyles) {
						if (colorStyle.startsWith("h-")) {
							combineStyles.remove(colorStyle);
							break;
						}
					}
					combineStyles.add(styleToAdd);

				} else {
					if (combineStyles.contains(styleToAdd)) {
						combineStyles.remove(styleToAdd);
					} else {
						combineStyles.add(styleToAdd);
					}

				}
				return combineStyles;

			});
			textArea.setStyleSpans(selection.getStart(), newStyles);
		}
	}

	// For future references Notice the .add method, -> not possible
//	public static void updateSizeInSelection(StyleClassedTextArea textArea, int sizeToAdd, IndexRange selection) {
//		if (selection.getLength() != 0) {
//			StyleSpans<Collection<String>> styles = textArea.getStyleSpans(selection);
//			StyleSpans<Collection<String>> newStyles = styles.mapStyles(styleCol -> {
//				ArrayList<String> combineStyles = new ArrayList<>(styleCol);
//				combineStyles.add("-fx-font-size: " + sizeToAdd + ";");
//				return combineStyles;
//			});
//			textArea.setStyleSpans(selection.getStart(), newStyles);
//		}
//	}

	public static void updateParagraphStyleInSelection(StyleClassedTextArea textArea, String styleToAdd,
			IndexRange selection) {
		int startPar = textArea.offsetToPosition(selection.getStart(), Forward).getMajor();
		int endPar = textArea.offsetToPosition(selection.getEnd(), Backward).getMajor();

		for (int i = startPar; i <= endPar; ++i) {
			textArea.setParagraphStyle(i, Collections.singleton(styleToAdd));
		}
	}
}
