package tournament.player;

/**
 *
 * @author Tobias
 * Edit Dec 2015 Peter Lorensen: 
 * -added JavaDoc comments
 * 
 * @param <PlayerType>
 */
public interface PlayerFactory<PlayerType> extends PlayerInfo
{
    /**
     * Must return an instance of you BattleshipPlayer
     * Example:
     * 
     * @Override
     * public BattleshipsPlayer getNewInstance()
     * {
     *        return new RandomPlayer();
     * }
     * 
     * @return 
     */
    public PlayerType getNewInstance();
}
