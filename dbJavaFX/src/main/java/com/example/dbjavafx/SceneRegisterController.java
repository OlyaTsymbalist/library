package com.example.dbjavafx;

import com.example.dbjavafx.dao.*;
import com.example.dbjavafx.domain.Book;
import com.example.dbjavafx.domain.BookRegister;
import com.example.dbjavafx.domain.PersonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class SceneRegisterController {

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

    public void switchToSceneAddRegister (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("sceneAddRegister.fxml")));
        Parent loader = fxmlLoader.load();
        AddRegisterController sceneAddRegister = fxmlLoader.getController();
        Image image = new Image(getClass().getResourceAsStream("/survey.png"));
        stage = new Stage();
        stage.getIcons().add(image);
        stage.setScene(new Scene(loader));
        stage.setTitle("Add new register");
        stage.show();
    }


    @FXML
    private Button addRegisterBtn;
    @FXML
    private Button rfrButton;
    @FXML
    private Button deleteRegisterBtn;
    @FXML
    private Button editRegisterBtn;

    @FXML
    private CheckBox editTableCheckBox;
    @FXML
    private VBox vBox;
    @FXML
    private RadioButton radioNotReturnedBook;
    @FXML
    private RadioButton radioReturnedBook;


    @FXML
    private TableColumn<BookRegister, Long> col_id;
    @FXML
    private TableColumn<BookRegister, Book> col_bookID;
    @FXML
    private TableColumn<BookRegister, Date> col_issuedDt;
    @FXML
    private TableColumn<BookRegister, PersonReader> col_personReaderID;
    @FXML
    private TableColumn<BookRegister, Date> col_returnedDt;
    @FXML
    private TableView<BookRegister> table;

    ObservableList<BookRegister> obList = FXCollections.observableArrayList();
    Connection connection;
    PreparedStatement ps;
    ResultSet rs;

    BookDAO bookDAO =  new BookDAOImpl();
    PersonReaderDAO personReaderDAO =  new PersonReaderDAOImpl();

    Book book = new Book();
    PersonReader personReader = new PersonReader();
    BookRegister bookRegister = new BookRegister();

    // вікно для перегляду неповернених книжок в регістрі
    @FXML
    public void switchToSceneNotReturnedList(ActionEvent event) throws IOException {
        if( !table.getItems().isEmpty())
            table.getItems().clear();
        else {
            try {
                refreshRegisterTableBtn();
                connection = ConnectionFactory.getConnection();
                bookRegister = table.getSelectionModel().getSelectedItem();
                rs = connection.createStatement().executeQuery("SELECT * FROM `bookregister2.0` WHERE isnull(vydanoDt) = 0 AND povernenoDt < 0200");

                while (rs.next()) {
                    book = bookDAO.getBookById(rs.getLong("bookId"));
                    personReader = personReaderDAO.getPersonReaderById(rs.getLong("personReaderId"));

                    bookRegister = new BookRegister();

                    bookRegister.setId(rs.getLong("id"));
                    bookRegister.setBookId(book);
                    bookRegister.setVudanoDt(rs.getDate("vydanoDt"));
                    bookRegister.setPersonReaderId(personReader);
                    bookRegister.setPovernenoDt(rs.getDate("povernenoDt"));
                    obList.add(bookRegister);
                }

                table.setItems(obList);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    // вікно для перегляду повернених книжок в регістрі
    @FXML
    public void switchToSceneReturnedBooks(ActionEvent event) throws IOException {
        if( !table.getItems().isEmpty())
            table.getItems().clear();
        else {
            try {
                obList.clear();
                refreshRegisterTableBtn();
                connection = ConnectionFactory.getConnection();
                bookRegister = table.getSelectionModel().getSelectedItem();
                rs = connection.createStatement().executeQuery("SELECT * FROM `bookregister2.0` WHERE isnull(vydanoDt) = 0 AND povernenoDt > 0200");

                while (rs.next()) {
                    book = bookDAO.getBookById(rs.getLong("bookId"));
                    personReader = personReaderDAO.getPersonReaderById(rs.getLong("personReaderId"));

                    bookRegister = new BookRegister();

                    bookRegister.setId(rs.getLong("id"));
                    bookRegister.setBookId(book);
                    bookRegister.setVudanoDt(rs.getDate("vydanoDt"));
                    bookRegister.setPersonReaderId(personReader);
                    bookRegister.setPovernenoDt(rs.getDate("povernenoDt"));
                    obList.add(bookRegister);
                }

                table.setItems(obList);
                refreshTable();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    @FXML
    void deleteSelectedRegister(ActionEvent event) {
        try {
            connection = ConnectionFactory.getConnection();
            bookRegister = table.getSelectionModel().getSelectedItem();
            ps = connection.prepareStatement("DELETE FROM `bookregister2.0` WHERE id ="+ bookRegister.getId()) ;
            ps.executeUpdate();

            refreshTable();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void editSelectedRegister(ActionEvent event) {
        bookRegister = table.getSelectionModel().getSelectedItem();
        fxmlLoader = new FXMLLoader((getClass().getResource("sceneAddRegister.fxml")));

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AddRegisterController sceneEditRegisterController = fxmlLoader.getController();
        sceneEditRegisterController.setUpdate(true);
        sceneEditRegisterController.setRegisterTextField(bookRegister.getId(),
                                                         bookRegister.getBookId(),
                                                         bookRegister.getVudanoDt(),
                                                         bookRegister.getPersonReaderId(),
                                                         bookRegister.getPovernenoDt());
        refreshTable();
        Image image = new Image(getClass().getResourceAsStream("/survey.png"));
        Parent root = fxmlLoader.getRoot();
        stage = new Stage();
        stage.getIcons().add(image);
        stage.setScene(new Scene(root));
        stage.setTitle("Modify register");
        stage.show();
    }

    @FXML
    void isCheckedBox(ActionEvent event) {
        if(editTableCheckBox.isSelected()) {
            addRegisterBtn.setDisable(false);
            deleteRegisterBtn.setDisable(false);
            editRegisterBtn.setDisable(false);
        }
        else {
            addRegisterBtn.setDisable(true);
            deleteRegisterBtn.setDisable(true);
            editRegisterBtn.setDisable(true);
        }
    }

    @FXML
    void refreshRegisterTableBtn() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_bookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        col_issuedDt.setCellValueFactory(new PropertyValueFactory<>("vudanoDt"));
        col_personReaderID.setCellValueFactory(new PropertyValueFactory<>("personReaderId"));
        col_returnedDt.setCellValueFactory(new PropertyValueFactory<>("povernenoDt"));

        refreshTable();
    }

    private void refreshTable() {
        try {

            obList.clear();
            connection = ConnectionFactory.getConnection();
            rs = connection.createStatement().executeQuery("SELECT * FROM `bookregister2.0`");

            while (rs.next()) {
                book = bookDAO.getBookById(rs.getLong("bookId"));
                personReader = personReaderDAO.getPersonReaderById(rs.getLong("personReaderId"));

                bookRegister = new BookRegister();

                bookRegister.setId(rs.getLong("id"));
                bookRegister.setBookId( book );
                bookRegister.setVudanoDt( rs.getDate("vydanoDt"));
                bookRegister.setPersonReaderId( personReader );
                bookRegister.setPovernenoDt( rs.getDate("povernenoDt"));
                obList.add(bookRegister);
            }

            table.setItems(obList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
