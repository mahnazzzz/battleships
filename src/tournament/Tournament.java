/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tournament;

import tournament.impl.executor.MatchExecutor;
import tournament.ui.TournamentUI;
import tournament.game.GameFactory;
import java.util.Collection;
import tournament.player.PlayerFactory;
import tournament.impl.executor.MultiThreadMatchExecutor;
import tournament.impl.TournamentImpl;
import tournament.impl.simpleui.TextTournamentUI;

/**
 *
 * @author Tobias
 */
public class Tournament
{

    public static <PlayerType> void run(GameFactory<PlayerType> gameFactory,
                                        Collection<PlayerFactory<PlayerType>> players)
    {
        //Choose UI
        TournamentUI ui = new TextTournamentUI();
        //Choose executor
        MatchExecutor executor = new MultiThreadMatchExecutor(16);
        
        //Create tournament instance
        TournamentImpl t = new TournamentImpl(ui, executor);
        
        //Run tournament...
        t.runTournament(gameFactory, players);
        
    }
    
    /**
     *
     * @param <PlayerType>
     * @param gameFactory
     * @param players
     * @param numberOfThreads
     */
    public static <PlayerType> void run(GameFactory<PlayerType> gameFactory,
                                        Collection<PlayerFactory<PlayerType>> players,
                                        int numberOfThreads)
    {
        //Choose UI
        TournamentUI ui = new TextTournamentUI();
        //Choose executor
        MatchExecutor executor = new MultiThreadMatchExecutor(numberOfThreads);
        
        //Create tournament instance
        TournamentImpl t = new TournamentImpl(ui, executor);
        
        //Run tournament...
        t.runTournament(gameFactory, players);
    }
    
    public static <PlayerType> void run(GameFactory<PlayerType> gameFactory,
                                        Collection<PlayerFactory<PlayerType>> players,
                                        int numberOfThreads, TournamentUI ui)
    {
        //Choose executor
        MatchExecutor executor = new MultiThreadMatchExecutor(numberOfThreads);
        
        //Create tournament instance
        TournamentImpl t = new TournamentImpl(ui, executor);
        
        //Run tournament...
        t.runTournament(gameFactory, players);
    }
}
