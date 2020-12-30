package application;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.StyleSpans;

import javafx.scene.control.IndexRange;

public class TextOperations {
	public static void updateStyleInSelection(StyleClassedTextArea textArea, String styleToAdd, IndexRange selection) {
		if (selection.getLength() != 0) {
			StyleSpans<Collection<String>> styles = textArea.getStyleSpans(selection);
			StyleSpans<Collection<String>> newStyles = styles.mapStyles(styleCol -> {
				ArrayList<String> combineStyles = new ArrayList<>(styleCol);
				if (combineStyles.contains(styleToAdd)) {
					combineStyles.remove(styleToAdd);
				} else {
					combineStyles.add(styleToAdd);
				}
				return combineStyles;
			});
			textArea.setStyleSpans(selection.getStart(), newStyles);
		}
	}

}
