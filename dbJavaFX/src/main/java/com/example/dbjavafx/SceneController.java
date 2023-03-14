package com.example.dbjavafx;

import com.example.dbjavafx.domain.Book;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.net.URI;


public class SceneController {

    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;

    // головне вікно
    public void switchToScene1 (ActionEvent event) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 770, 420);
        stage.setTitle("Book library!");
        stage.setScene(scene);
        stage.show();
    }
    // вікно із списком книжок
    public void switchToSceneBookList (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sceneBookList.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 830, 380);
        stage.setTitle("Book List");
        stage.setScene(scene);
        stage.show();
    }
    // вікно із списком читачів
    public void switchToSceneBookReaderList (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sceneReaderList.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 974, 400);
        stage.setTitle("Reader List");
        stage.setScene(scene);
        stage.show();
    }
    // вікно для перегляду регістру
    public void switchToSceneRegisterList (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("sceneRegisterList.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 974, 400);
        stage.setTitle("Register List");
        stage.setScene(scene);
        stage.show();
    }
    // вікно для запису нової книги
    public void switchToSceneAddBook (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("sceneAddBook.fxml")));
        Parent loader = fxmlLoader.load();
        AddBookController sceneAddBook = fxmlLoader.getController();
        Image image = new Image(getClass().getResourceAsStream("/survey.png"));
        stage = new Stage();
        stage.getIcons().add(image);
        stage.setScene(new Scene(loader));
        stage.setTitle("Add new book");
        stage.show();
    }




    @FXML
    private Button onBookListButtonClick;
    @FXML
    private Button rfrButton;
    @FXML
    private Button addBookBtn;
    @FXML
    private Button deleteBookBtn;
    @FXML
    private Button editBookBtn;
    @FXML
    private Button onNotReturnedBooksClick;

    @FXML
    private Button onRegisterListClick;

    @FXML
    private Button onReturnedBooksClick;

    @FXML
    private CheckBox editTableCheckBox;

    @FXML
    private MenuItem helpMenu;



    @FXML
    void isCheckedBox(ActionEvent event) {
        if(editTableCheckBox.isSelected()) {
            addBookBtn.setDisable(false);
            deleteBookBtn.setDisable(false);
            editBookBtn.setDisable(false);
        }
        else {
            addBookBtn.setDisable(true);
            deleteBookBtn.setDisable(true);
            editBookBtn.setDisable(true);
        }
    }

    @FXML
    private TableView<Book> table;
    @FXML
    private TableColumn<Book, Long> col_id;
    @FXML
    private TableColumn<Book, String> col_title;
    @FXML
    private TableColumn<Book, String> col_author;
    @FXML
    private TableColumn<Book, Integer> col_printYear;
    @FXML
    private TableColumn<Book, Integer> col_countOfPages;

    ObservableList<Book> obList = FXCollections.observableArrayList();
    Connection connection;
    Book book = new Book();
    PreparedStatement ps;
    ResultSet rs;

    @FXML
    private void refreshBookTableBtn(ActionEvent event) {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_printYear.setCellValueFactory(new PropertyValueFactory<>("printYear"));
        col_countOfPages.setCellValueFactory(new PropertyValueFactory<>("countOfPages"));

        refreshTable();

    }

    @FXML
    void deleteSelectedBook(ActionEvent event) throws SQLException {
        try {
            connection = ConnectionFactory.getConnection();
            book = table.getSelectionModel().getSelectedItem();
            ps = connection.prepareStatement("DELETE FROM book WHERE id ="+book.getId());
            ps.executeUpdate();

            refreshTable();
        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    void editBook(ActionEvent event) throws IOException {
        book = table.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("sceneAddBook.fxml")));

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AddBookController sceneEditBookController = fxmlLoader.getController();
        sceneEditBookController.setUpdate(true);
        sceneEditBookController.setBookTextField(book.getId(), book.getTitle(), book.getAuthor(),
                                             book.getPrintYear(), book.getCountOfPages());
        refreshTable();
        Image image = new Image(getClass().getResourceAsStream("/survey.png"));
        Parent root = fxmlLoader.getRoot();
        stage = new Stage();
        stage.getIcons().add(image);
        stage.setScene(new Scene(root));
        stage.setTitle("Modify book");
        stage.show();
    }

    private void refreshTable() {
        try {
            obList.clear();
            connection = ConnectionFactory.getConnection();
            rs = connection.createStatement().executeQuery("SELECT * FROM book");

            while (rs.next()) {
                obList.add(new Book(rs.getLong("id"),
                                    rs.getString("title"),
                                    rs.getString("author"),
                                    rs.getInt("printYear"),
                                    rs.getInt("countOfPages")));
            }

            table.setItems(obList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}