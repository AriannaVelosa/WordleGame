package view_controller;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.AccountCollection;

public class MainGUI extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	LoginPane loginPane;
	BorderPane everything;

	private AccountCollection accounts = new AccountCollection();

	public void start(Stage primaryStage) {
		LayoutGUI();
		// Scene scene = new Scene(everything, 700, 500);
		// primaryStage.setScene(scene);
		// primaryStage.show();
		
		primaryStage.setOnCloseRequest((event) -> {
			//saveChanges();
		});
	}

	private void LayoutGUI() {
		everything = new BorderPane();
		loginPane = new LoginPane();
		// everything.setTop(loginPane);
		everything.setPadding(new Insets(0, 0, 0, 10));
		//everything.setCenter(loginPane);
	}
}
