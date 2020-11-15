package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    // * JAVAFX Boilerplate
    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        // Add circle object
        Group root = new Group();
        Circle myCircle = new Circle(new Point(100,100), 30);
        Button button = new Button("Show Circle");
        Button button2 = new Button("Rotate Circle");
        button.setOnMouseClicked(e -> myCircle.renderCircle(root));
        button2.setOnMouseClicked(e -> myCircle.rotate());
        button.setLayoutX(200);button.setLayoutY(200);
        button2.setLayoutX(300);button2.setLayoutY(100);
        root.getChildren().addAll(myCircle.circleRoot.getChildren());
        root.getChildren().add(button2);
        root.getChildren().add(button);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
        myCircle.rotate();
    }
    //
}
