package com.chat.executor;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class LoginController {

    @FXML
    private StackPane root;

    @FXML
    private TextField nomeField;

    @FXML
    private ImageView backgroundImage;

    @FXML
    public void initialize() {
        URL imgURL = LoginController.class.getResource("/images/background.png");
        if (imgURL == null) {
            System.err.println("❌ Imagem não encontrada: /images/background.png");
            return;
        }

        Image image = new Image(imgURL.toExternalForm());
        backgroundImage.setImage(image);

        // Faz o backgroundImage acompanhar o tamanho da janela
        backgroundImage.fitWidthProperty().bind(root.widthProperty());
        backgroundImage.fitHeightProperty().bind(root.heightProperty());
        backgroundImage.setPreserveRatio(false);
    }

    @FXML
    private void entrar() {
        String nome = nomeField.getText().trim();
        if (!nome.isEmpty()) {
            System.out.println("Entrando como: " + nome);
            // Chama o ClienteSocket se desejar
        }
    }
}
