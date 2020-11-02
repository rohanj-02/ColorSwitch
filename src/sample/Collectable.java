package sample;

public interface Collectable {
	/**
	 * Returns whether the object has been collected or not
	 * @return boolean
	 */
	public boolean isCollected();

	/**
	 * Function to be called after the object has been collected.
	 */
	public void afterCollect();
}
