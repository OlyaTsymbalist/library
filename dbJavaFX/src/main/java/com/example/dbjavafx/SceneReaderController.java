package com.example.dbjavafx;

import com.example.dbjavafx.domain.PersonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class SceneReaderController {
    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;

    @FXML
    void switchToScene1(ActionEvent event) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 770, 380);
        stage.setTitle("Book library!");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneAddReader (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("sceneAddReader.fxml")));
        Parent loader = fxmlLoader.load();
        AddReaderController sceneAddReader = fxmlLoader.getController();
        Image image = new Image(getClass().getResourceAsStream("/survey.png"));
        stage = new Stage();
        stage.getIcons().add(image);
        stage.setScene(new Scene(loader));
        stage.setTitle("Add new reader");
        stage.show();
    }

    @FXML
    private Button addReaderBtn;
    @FXML
    private Button deleteReaderBtn;
    @FXML
    private Button editReaderBtn;
    @FXML
    private Button rfrButton;

    @FXML
    private CheckBox editTableCheckBox;

    @FXML
    void isCheckedBox(ActionEvent event) {
        if(editTableCheckBox.isSelected()) {
            addReaderBtn.setDisable(false);
            deleteReaderBtn.setDisable(false);
            editReaderBtn.setDisable(false);
        }
        else {
            addReaderBtn.setDisable(true);
            deleteReaderBtn.setDisable(true);
            editReaderBtn.setDisable(true);
        }
    }

    @FXML
    private TableColumn<PersonReader, Long> col_id;
    @FXML
    private TableColumn<PersonReader, String> col_firstName;

    @FXML
    private TableColumn<PersonReader, String> col_middleName;

    @FXML
    private TableColumn<PersonReader, String> col_lastName;

    @FXML
    private TableColumn<PersonReader, String> col_phone;

    @FXML
    private TableColumn<PersonReader, Date> col_birthDate;

    @FXML
    private TableColumn<PersonReader, String> col_serialPassport;

    @FXML
    private TableColumn<PersonReader, Integer> col_numberPassport;

    @FXML
    private TableColumn<PersonReader, String> col_address;

    @FXML
    private TableView<PersonReader> table;

    ObservableList<PersonReader> obList = FXCollections.observableArrayList();
    Connection connection;
    PersonReader personReader = new PersonReader();
    PreparedStatement ps;
    ResultSet rs;

    @FXML
    void refreshReaderTableBtn(ActionEvent event) {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_middleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_birthDate.setCellValueFactory(new PropertyValueFactory<>("birthDt"));
        col_serialPassport.setCellValueFactory(new PropertyValueFactory<>("serialOfPassport"));
        col_numberPassport.setCellValueFactory(new PropertyValueFactory<>("numOfPassport"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));

        refreshTable();
    }

    @FXML
    void deleteSelectedReader(ActionEvent event) {
        try {
            connection = ConnectionFactory.getConnection();
            personReader = table.getSelectionModel().getSelectedItem();
            ps = connection.prepareStatement("DELETE FROM personreader WHERE id ="+personReader.getId());
            ps.executeUpdate();

            refreshTable();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void refreshTable() {
        try {
            obList.clear();
            connection = ConnectionFactory.getConnection();
            rs = connection.createStatement().executeQuery("SELECT * FROM personreader");

            while (rs.next()) {
                obList.add(new PersonReader(rs.getLong("ID"),
                                            rs.getString("firstName"),
                                            rs.getString("middleName"),
                                            rs.getString("lastName"),
                                            rs.getString("phone"),
                                            rs.getDate("birthDt"),
                                            rs.getString("serialOfPassport"),
                                            rs.getInt("numOfPassport"),
                                            rs.getString("address")));
                }

            table.setItems(obList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void editSelectedReader(ActionEvent event) {
        personReader = table.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("sceneAddReader.fxml")));

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AddReaderController sceneEditReaderController = fxmlLoader.getController();
        sceneEditReaderController.setUpdate(true);
        sceneEditReaderController.setReaderTextField(personReader.getId(), personReader.getFirstName(),
                personReader.getMiddleName(), personReader.getLastName(), personReader.getPhone(),
                personReader.getBirthDt(), personReader.getSerialOfPassport(),
                personReader.getNumOfPassport(), personReader.getAddress());
        refreshTable();

        Image image = new Image(getClass().getResourceAsStream("/survey.png"));
        Parent root = fxmlLoader.getRoot();
        stage = new Stage();
        stage.getIcons().add(image);
        stage.setScene(new Scene(root));
        stage.setTitle("Edit reader");
        stage.show();
    }


}
