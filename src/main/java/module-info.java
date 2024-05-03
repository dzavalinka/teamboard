module com.dzavalinskii {
    requires transitive javafx.graphics;
    requires transitive javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens com.dzavalinskii to javafx.fxml;
    exports com.dzavalinskii;
}
