package game.interfaces;

/**
 * Interface for elements that have mana
 */
public interface IMana {
    /**
     * Adds mana
     * @param amount Amount to add
     */
    void addMana(int amount);

    /**
     * Returns the current amount of mana
     * @return The current amount of mana
     */
    int getMana();
}
