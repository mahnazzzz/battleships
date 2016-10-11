package battleshipstournament;

import battleship.implementations.BattleshipGameInstaceSingleRound;
import battleship.interfaces.BattleshipsPlayer;
import tournament.game.GameResult;
import tournament.player.PlayerFactory;

/**
 *
 * @author Tobias Grundtvig & Peter Lorensen
 */
public class SingleRoundVisualizer
{
    public static void main(String[] args)
    {
        /*******  OVERWRITE AI HERE (with your own):  *******/
        PlayerFactory<BattleshipsPlayer> playerAFactory = Loader.loadPlayer("C:\\ai\\G3.jar", "g3.G3");
        PlayerFactory<BattleshipsPlayer> playerBFactory = Loader.loadPlayer("C:\\ai\\E3.jar", "e3.E3");
          
        //Initializing and getting the BattleshipPlayer object from the factory:
        BattleshipsPlayer playerA = playerAFactory.getNewInstance();
        BattleshipsPlayer playerB = playerBFactory.getNewInstance();
        
        //Initializing and getting the GameInstance that runs the game:
        int[] ships = {2,3,3,4,5};
        BattleshipGameInstaceSingleRound verbInstance = new BattleshipGameInstaceSingleRound(10, 10, ships);
        GameResult res = verbInstance.run( playerA, playerB);  //Running the game
        
        //Printing out end result:
        printWinner(res, playerAFactory.getName(), playerBFactory.getName());
        
        System.out.println("--- Details: ---");
        System.out.println("");
        System.out.println("Shots fired:");
        System.out.println( verbInstance.getShotsFiredA() + ": " + playerAFactory.getName() + ", (PlayerA)." );
        System.out.println( verbInstance.getShotsFiredB() + ": " + playerBFactory.getName() + ", (PlayerB)."  );
        System.out.println("");
        System.out.println("Ships wrecked before game start:");
        System.out.println( verbInstance.getShipWrecks_AtStart_A() + ": " + playerAFactory.getName() + ", (PlayerA)." );
        System.out.println( verbInstance.getShipWrecks_AtStart_B() + ": " + playerBFactory.getName() + ", (PlayerB)."  );
    }
    
    /**
     * Helper method to print the winner in nice red color.
     * @param res the GameResult
     * @param nameOfPlayerA String with name of the player
     * @param nameOfPlayerB String with name of the player
     */ 
    public static void printWinner(GameResult res, String nameOfPlayerA , String nameOfPlayerB ){
        //Color constants (sets the color in the output area text):
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m"; 
        
        String toPrint = "THE WINNER IS: "; //Creating a string
        toPrint += ANSI_RED; //Changing color
        if( res.majorPointsA > res.majorPointsB )  //Getting the correct winner
           {  toPrint += nameOfPlayerA; }
        else if( res.majorPointsA < res.majorPointsB )
           {   toPrint += nameOfPlayerB; }
        else
           {   toPrint += "No one";  }             
        toPrint += ANSI_RESET;
        System.out.println(toPrint);
    } 
}
