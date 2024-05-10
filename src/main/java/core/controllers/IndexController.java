package core.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import static core.utils.ImageLoader.loadImage;

public class IndexController {
    @FXML
    private ImageView personImage;

    @FXML
    private ImageView companyImage;

    @FXML
    private ImageView asignationImage;

    @FXML
    public void initialize() {
        loadImage(personImage, "/media/Study-abroad-pana.png");
        loadImage(companyImage, "/media/City-skyline-pana.png");
        loadImage(asignationImage, "/media/Back-to-back-pana.png");
    }
}
