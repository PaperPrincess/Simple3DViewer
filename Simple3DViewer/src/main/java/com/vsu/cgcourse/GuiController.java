package com.vsu.cgcourse;

import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.Mesh;
import com.vsu.cgcourse.obj_reader.ObjReader;
import com.vsu.cgcourse.render_engine.Camera;
import com.vsu.cgcourse.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.scene.control.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GuiController {

    final private float TRANSLATION = 0.5F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;
    private Mesh mesh = null;
    private final List<Mesh> meshList = new ArrayList<>();

    private Camera camera = new Camera(
            new Vector3(0, 0, 100),
            new Vector3(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                try {
                    RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");
        fileChooser.setInitialDirectory(new File("src/main/resources/com/vsu/cgcourse/models"));

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }
        Path fileName = Path.of(file.getAbsolutePath());

        String fileContent = null;
        try {
            fileContent = Files.readString(fileName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        mesh = ObjReader.read(fileContent);
        // todo: обработка ошибок
    }

    @FXML
    public void onSaveModelMenuItemClick() throws IOException {
//            Matrix4 matrix4 = GraphicConveyor.rotateScaleTranslate(
//                mesh.value.getX(), mesh.value.getY(), mesh.value.getZ(),
//               mesh.value.getAngleX(), mesh.value.getAngleY(), mesh.value.getAngleZ(),
//               mesh.value.getTx(), mesh.value.getTy(), mesh.value.getTz());
//        ObjWriter.write(matrix4, "MESH_FILE_NAME");
        }

    @FXML
    TextField ScaleX;
    @FXML
    TextField ScaleY;
    @FXML
    TextField ScaleZ;

    @FXML
    private void drawScaleMenu(ActionEvent actionEvent) {
//        Button buttonAccept = new Button("Accept");
//        buttonAccept.setOnAction(actionEvent -> {
            float x = 1;
            float y = 1;
            float z = 1;
            if (ScaleX.getText().length() != 0) {
                x = Float.parseFloat(ScaleX.getText());
            }
            if (ScaleY.getText().length() != 0) {
                y = Float.parseFloat(ScaleY.getText());
            }
            if (ScaleZ.getText().length() != 0) {
                z = Float.parseFloat(ScaleZ.getText());
            }
            mesh.value.setX(x);
            mesh.value.setY(y);
            mesh.value.setZ(z);
//        });
    }

    @FXML
    TextField RotationX;
    @FXML
    TextField RotationY;
    @FXML
    TextField RotationZ;

    @FXML
    private void drawRotateMenu(ActionEvent actionEvent){
//        Button button = new Button("Accept");
//        Button buttonAccept = new Button("Accept");

        //      buttonAccept.setOnAction(actionEvent -> {
//        button.s
            float x = 1;
            float y = 1;
            float z = 1;
            if (RotationX.getText().length() != 0) {
                x = Float.parseFloat(RotationX.getText());
            }
            if (RotationY.getText().length() != 0) {
                y = Float.parseFloat(RotationY.getText());
            }
            if (RotationZ.getText().length() != 0) {
                z = Float.parseFloat(RotationZ.getText());
            }
            mesh.value.setAngleX(x);
            mesh.value.setAngleY(y);
            mesh.value.setAngleZ(z);
//        });
    }

    @FXML
    TextField TranslateX;
    @FXML
    TextField TranslateY;
    @FXML
    TextField TranslateZ;

    @FXML
    private void drawTranslateMenu(ActionEvent actionEvent) {

            float x = 1;
            float y = 1;
            float z = 1;
            if (TranslateX.getText().length() != 0) {
                x = Float.parseFloat(TranslateX.getText());
            }
            if (TranslateY.getText().length() != 0) {
                y = Float.parseFloat(TranslateY.getText());
            }
            if (TranslateZ.getText().length() != 0) {
                z = Float.parseFloat(TranslateZ.getText());
            }
            mesh.value.setTx(x);
            mesh.value.setTy(y);
            mesh.value.setTz(z);

    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3(0, -TRANSLATION, 0));
    }
}