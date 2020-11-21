package main.controllers;

public abstract class LayoutController {

	protected MainLayoutController parentController;

	public void setParentController(MainLayoutController parentController) {
		this.parentController = parentController;
	}

	public void increaseStage() {
		this.parentController.increaseGameStage();
	}

	public MainLayoutController getParentController() {
		return parentController;
	}
}
