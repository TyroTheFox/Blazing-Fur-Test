package core;

/**
 * Action
 * A programmable effect that can be mapped to a button
 * @author Kieran Clare
 *
 */

public interface Action {
	/**
	 * Action Effect
	 * An effect that happens instantaneously
	 * @param p
	 * @param e
	 * @param delta
	 */
	public void actionEffect(Player p, Enemy e, int delta);

	/**
	 * Lingering Effect
	 * An effect that happens over an amount of time
	 * @param p
	 * @param e
	 * @param delta
	 */
	public void lingeringEffect(Player p, Enemy e, int delta);
	
}
