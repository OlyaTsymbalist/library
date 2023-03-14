module com.example.dbjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.dbjavafx to javafx.fxml;
    opens com.example.dbjavafx.dao to javafx.fxml;
    opens com.example.dbjavafx.domain to javafx.fxml;

    exports com.example.dbjavafx;
    exports com.example.dbjavafx.dao;
    exports com.example.dbjavafx.domain;

}