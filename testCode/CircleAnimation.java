package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.shape.DrawMode;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.scene.shape.Arc;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.scene.Group;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Arc test");
        float[] position = new float[2];
        position[0] = 100;
        position[1] = 100;
        float[] radius= new float[2];
        radius[0] = 50;
        radius[1] = 50;
        float[] angles = new float[5];
        angles[0] = 0;
        angles[1] = 90;
        angles[2] = 180;
        angles[3] = 270;
        angles[4] = 360;
        Group root = new Group();
        Color[] colors = new Color[4];
        colors[0] = Color.HOTPINK;
        colors[1] = Color.SKYBLUE;
        colors[2] = Color.YELLOW;
        colors[3] = Color.PURPLE;
        String[] colorString = new String[4];
        colorString[0] = "HOTPINK";
        colorString[1] = "SKYBLUE";
        colorString[2] = "YELLOW";
        colorString[3] = "PURPLE";

        Text text = new Text();
        text.setText(colorString[2]);
        text.setFill(Color.LIME);
        text.setX(200);
        text.setY(200);
        root.getChildren().add(text);
        for(int i = 0; i < 4; i++){
            Arc arc = new Arc(position[0],position[1],radius[0],radius[1],i*90,90);
            arc.setFill(Color.rgb(0,0,0,0));
            arc.setStrokeWidth(10);
            arc.setStroke(colors[i]);
            Rotate rotate = new Rotate(0,position[0], position[1]);
            rotate.setAxis(Rotate.Z_AXIS);
            arc.getTransforms().add(rotate);
            final Timeline rotationAnimation = new Timeline();
            rotationAnimation.getKeyFrames()
                    .add(
                            new KeyFrame(
                                    Duration.seconds(5),
                                    new KeyValue(
                                            rotate.angleProperty(),
                                            360
                                    )
                            )
                    );

            Timer timer = new Timer();
            int begin = 10; //timer starts after 1 second.
            int timeinterval = 10; //timer executes every 10ms.
            int finalI = i;
            // 0 : 90 to 180
            // 1 : 180 to 270
            // 2 : 270 to 360
            // 3 : 0 to 90
            //270 and 360 180 - 270 = 90 and 90 - 270 = -180 = 270 and 180
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    for (Transform transform : arc.getTransforms()) {
                        if (transform instanceof Rotate) {
                            int startAngle = 90 + finalI*90;
                            int endAngle = 180 + finalI*90;
                            if((90 + finalI*90) >= 360){
                                startAngle = 90 + finalI*90 - 360;
                                endAngle = 180 + finalI*90 - 360;
                            }
                            if(((Rotate)transform).getAngle() >= startAngle && ((Rotate)transform).getAngle() <= endAngle){
                                text.setText(colorString[finalI]);
//                                System.out.print(colorString[finalI] + " ");
//                                System.out.println(((Rotate)transform).getAngle());
                            }
//                            System.out.println(((Rotate)transform).getAngle());
                        }
                    }
                }
            },begin, timeinterval);
            rotationAnimation.setCycleCount(Animation.INDEFINITE);
            rotationAnimation.play();
            root.getChildren().add(arc);
        }

//        arc.setTranslateX(100);
//        arc.setTranslateY(100);
        primaryStage.setScene(new Scene(root, 300, 275, Color.BLACK));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
