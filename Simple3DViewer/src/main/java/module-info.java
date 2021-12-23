module com.vsu.cgcourse {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;
    requires junit;
    requires javafx.graphics;


    opens com.vsu.cgcourse to javafx.fxml;
    exports com.vsu.cgcourse;
    exports com.vsu.cgcourse.render_engine;
    opens com.vsu.cgcourse.render_engine to javafx.fxml;
}