package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class T�ren extends Application{
	
	Stage window;
	Scene doorScene;
	Button zur�ckButton;

	public static void main(String[] args) {
	launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
	window = primaryStage;
	window.setTitle("T�ren/Themen");

	zur�ckButton = new Button("zur�ck");
	
	VBox layout = new VBox(10);
	layout.setPadding(new Insets(10));
	layout.getChildren().addAll(zur�ckButton);
	
	Scene doorScene = new Scene(layout, 800, 450);
	window.setScene(doorScene);
	window.show();
		
	}
	
}