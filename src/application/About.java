package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class About extends Application {

	@Override
	public void start(Stage arg0) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/fxml/About.fxml"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
