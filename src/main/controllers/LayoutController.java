package main.controllers;

import javafx.scene.input.MouseEvent;

public abstract class LayoutController {

	protected MainLayoutController parentController;

	public void setParentController(MainLayoutController parentController) {
		this.parentController = parentController;
	}

	public void increaseStage(MouseEvent mouseEvent) {
		this.parentController.increaseStage();
	}

}
