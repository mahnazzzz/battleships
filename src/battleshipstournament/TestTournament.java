package battleshipstournament;

import battleship.implementations.Battleships;
import battleship.interfaces.BattleshipsPlayer;
import java.util.ArrayList;
import java.util.Collection;
import tournament.Tournament;
import tournament.impl.simpleui.TextTournamentUI;
import tournament.player.PlayerFactory;

/**
 *
 * @author Tobias Grundtvig
 */

public class TestTournament
{
    public static void main(String[] args) throws Exception
    {
        //Turn of Player IO
        TextTournamentUI.turnOffIO();
        //TODO: Path to your tournament directory (jar-files)
        String path = "C:/ai";
        Loader loader = new Loader(path);
        
        Collection<PlayerFactory<BattleshipsPlayer>> allAIs = new ArrayList<>();
        Collection<PlayerFactory<BattleshipsPlayer>> examples = loader.loadCategory("E", 50);
        Collection<PlayerFactory<BattleshipsPlayer>> green = loader.loadCategory("G", 100);
        Collection<PlayerFactory<BattleshipsPlayer>> yellow = loader.loadCategory("Y", 100);
        Collection<PlayerFactory<BattleshipsPlayer>> red = loader.loadCategory("R", 100);
        Collection<PlayerFactory<BattleshipsPlayer>> test = loader.loadCategory("T", 100);
        allAIs.addAll(examples);
        allAIs.addAll(green);
        allAIs.addAll(yellow);
        allAIs.addAll(red);
        
        green.addAll(examples);
        yellow.addAll(examples);
        red.addAll(examples);
        
        
        TextTournamentUI.turnOnIO();
        System.out.println("\nStart GREEN tournament...\n");
        TextTournamentUI.turnOffIO();
        Tournament.run(Battleships.getGameFactory(), green);
        /*
        TextTournamentUI.turnOnIO();
        System.out.println("\nStart YELLOW tournament...\n");
        TextTournamentUI.turnOffIO();
        Tournament.run(Battleships.getGameFactory(), yellow);
        
        TextTournamentUI.turnOnIO();
        System.out.println("\nStart RED tournament...\n");
        TextTournamentUI.turnOffIO();
        Tournament.run(Battleships.getGameFactory(), red);
        
        TextTournamentUI.turnOnIO();
        System.out.println("\nStart TOTAL WAR tournament...\n");
        TextTournamentUI.turnOffIO();
        Tournament.run(Battleships.getGameFactory(), allAIs);
        
        TextTournamentUI.turnOnIO();
        */
        System.out.println("Goodbye!");
    }
}