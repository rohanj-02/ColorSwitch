package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    // * JAVAFX Boilerplate
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/sample.fxml"));
        Group rootTemp = FXMLLoader.load(getClass().getResource("../resources/fxml/sample.fxml"));
        // Add circle object
//        Group root = new Group();
//        Circle myCircle = new Circle(new Point(100,100), 30);
        //Triangle myTriangle = new Triangle(new Point(100,100), 120);
//        LineObstacle line = new LineObstacle(new Point(0,100), 500);
//        Button button = new Button("Show Circle");
//        Button button2 = new Button("Rotate Circle");
//        button.setOnMouseClicked(e -> line.render(root));
//        button2.setOnMouseClicked(e -> line.play());
//        button.setLayoutX(200);button.setLayoutY(200);
//        button2.setLayoutX(300);button2.setLayoutY(100);
//        root.getChildren().addAll(line.getLineRoot().getChildren());
//        root.getChildren().add(button2);
//        root.getChildren().add(button);
//        root.getChildren().add(line.getLineRoot());
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 700));
        primaryStage.show();
        //myTriangle.rotate();
    }
    //
}
