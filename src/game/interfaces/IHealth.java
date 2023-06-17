package game.interfaces;

/**
 * Interface for elements that have health
 */
public interface IHealth {
    /**
     * Adds a certain amount of health
     * @param amount The amount to add
     */
    void addHealth(int amount);

    /**
     * Returns the current amount of health
     * @return The current amount of health
     */
    int getHealth();
}
