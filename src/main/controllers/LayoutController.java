package main.controllers;

public abstract class LayoutController {

	protected MainLayoutController parentController;

	public MainLayoutController getParentController() {
		return parentController;
	}

	public void setParentController(MainLayoutController parentController) {
		this.parentController = parentController;
	}

	/**
	 * Signal sent to the parentController class to move to the next stage
	 */
	public void increaseStage() {
		this.parentController.increaseGameStage();
	}

}
