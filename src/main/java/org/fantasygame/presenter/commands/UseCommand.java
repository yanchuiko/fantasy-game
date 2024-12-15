package org.fantasygame.presenter.commands;

import org.fantasygame.model.services.PlayerService;

/**
 * Command to use a spell.
 */
public class UseCommand implements Command {
    private final String spellName;

    /**
     * Constructs a UseCommand with the specified spell name.
     *
     * @param spellName the name of the spell to use
     */
    public UseCommand(String spellName) {
        this.spellName = spellName;
    }

    /**
     * Getter for the spell name.
     *
     * @return the name of the spell to pick up
     */
    public String getSpellName() {
        return spellName;
    }

    /**
     * Executes the use command using the provided PlayerService.
     *
     * @param playerService the service to handle player actions
     * @return the result of the use action
     */
    @Override
    public String execute(PlayerService playerService) {
        return playerService.use(spellName);
    }
}
