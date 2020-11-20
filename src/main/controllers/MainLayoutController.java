package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController extends AnchorPane implements Initializable {
	@FXML
	private AnchorPane heading;
	@FXML
	private HeadingController headingController;
	@FXML
	private AnchorPane landing;
	@FXML
	private LandingController landingController;

//	public MainLayoutController() {
//		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/MainLayout.fxml"));
//		fxmlLoader.setRoot(this);
//
//		try {
//			fxmlLoader.load();
//		} catch (IOException exception) {
//			throw new RuntimeException(exception);
//		}
//	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		System.out.println("Is fx thread?:");
	}
}
