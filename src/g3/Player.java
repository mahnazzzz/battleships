/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package g3;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Board;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Tobias
 */
public class Player implements BattleshipsPlayer {

    private final static Random rnd = new Random();
    private int sizeX;
    private int sizeY;
    private Board myBoard;

    private PlaceShipHandler pHandler;

    private int hits = 0;
    private int nextX = 0;
    private int nextY = 0;
    private int numberOfShips_BeforeLastShot;
    private boolean shipHit;
    private boolean shipStillALive;
    private Position lastNormalShot;
    private int initialEnemyFleetSize = 0;

    private ArrayList<Position> shotListArray;
    private ArrayList<Position> temporaryHitList;

    private Position newShot;
    private int turn = 1;
    private int x;
    private int y;

    public Player() {
        lastNormalShot = new Position(nextX, nextY);
        pHandler = new PlaceShipHandler();
        newShot = new Position(nextX, nextY);
        shotListArray = new ArrayList();
        temporaryHitList = new ArrayList();

    }

    /**
     * The method called when its time for the AI to place ships on the board
     * (at the beginning of each round).
     *
     * The Ship object to be placed MUST be taken from the Fleet given (do not
     * create your own Ship objects!).
     *
     * A ship is placed by calling the board.placeShip(..., Ship ship, ...) for
     * each ship in the fleet (see board interface for details on placeShip()).
     *
     * A player is not required to place all the ships. Ships placed outside the
     * board or on top of each other are wrecked.
     *
     * @param fleet Fleet all the ships that a player should place.
     * @param board Board the board were the ships must be placed.
     */
    @Override
    public void placeShips(Fleet fleet, Board board) {

        myBoard = board;
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        for (int i = 0; i < fleet.getNumberOfShips(); ++i) {
            Ship s = fleet.getShip(i);

            boolean vertical = rnd.nextBoolean();
            Position pos;
            if (vertical) {
                int x = rnd.nextInt(sizeX);
                int y = rnd.nextInt(sizeY - (s.size() - 1));
                pos = new Position(x, y);
            } else {
                int x = rnd.nextInt(sizeX - (s.size() - 1));
                int y = rnd.nextInt(sizeY);
                pos = new Position(x, y);
            }

            while (!this.pHandler.shipPlacement(pos, s, vertical)) {

                if (vertical) {
                    int x = rnd.nextInt(sizeX);
                    int y = rnd.nextInt(sizeY - (s.size() - 1));
                    pos = new Position(x, y);
                } else {
                    int x = rnd.nextInt(sizeX - (s.size() - 1));
                    int y = rnd.nextInt(sizeY);
                    pos = new Position(x, y);
                }
            }
            board.placeShip(pos, s, vertical);
        }
    }

    /**
     * Called every time the enemy has fired a shot.
     *
     * The purpose of this method is to allow the AI to react to the enemy's
     * incoming fire and place his/her ships differently next round.
     *
     * @param pos Position of the enemy's shot
     */
    @Override
    public void incoming(Position pos) {

    }

    /**
     * hitFeedBack(...) is called right after this method. * Called by the Game
     * application to get the Position of your shot.
     *
     *
     * @param enemyShips Fleet the enemy's ships. Compare this to the Fleet
     * supplied in the hitFeedBack(...) method to see if you have sunk any
     * ships.
     *
     * @return Position of you next shot.
     */
    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        //Startupp checks:
        numberOfShips_BeforeLastShot = enemyShips.getNumberOfShips();
if (shotListArray.size()<1){
    initialEnemyFleetSize = fleetSize(enemyShips);
}
        if (enemyShips.getNumberOfShips() == 0) {
        }

        Position shot = new Position(nextX, nextY);

        if (!shipHit && initialEnemyFleetSize - hits == fleetSize(enemyShips)) {

            shot = getNormalShot();
            //evt tilføj kode der husker hvor du kom fra
            lastNormalShot = shot;

        } else  {

            shot = getNextHitFromList();
        }
        x = shot.x;
        y = shot.y;
        shotListArray.add(shot);
        System.out.println("shot: " + shot.x + ", " + shot.y);
        return shot;
    }

    /**
     * Called right after getFireCoordinates(...) to let your AI know if you hit
     * something or not.
     *
     * Compare the number of ships in the enemyShips with that given in
     * getFireCoordinates in order to see if you sunk a ship.
     *
     * @param hit boolean is true if your last shot hit a ship. False otherwise.
     * @param enemyShips Fleet the enemy's ships.
     */
    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit) {
            shipHit = true;
            hits++;
            
            if (enemyShips.getNumberOfShips() < numberOfShips_BeforeLastShot && fleetSize(enemyShips) == initialEnemyFleetSize-hits) //Hvis antallet af skibe er mindre end tidligere, så er ét skib sunet 
            {                                                                 //Vi har ramt et skib og det er sunket:
                shipStillALive = false;  // Nu har vi sænket det sårede skib
                temporaryHitList = new ArrayList();
            } else {  //VI har ramt et skib, men det er stadig i live:
                shipStillALive = true;
                addNearbyPositionsToHitList();
            }
        } else {
            shipHit = false;
        }

    }

    private Position getShipKillerShot() {
        Position p = new Position(1, 1);

        if (x < sizeX - 1 && turn == 1) {

            x++;

            p = new Position(x, nextY);

            if (x == sizeX - 1 || !shipHit && x <= nextX + 4) {
                turn = 2;
            }
        }
        if (x > 0 && turn == 2) {

            x--;
            p = new Position(x, nextY);
            if (x == 0 || !shipHit && x == nextX - 1) {
                turn = 3;
            }
        }
        if (y > 0 && turn == 3) {
            y--;
            p = new Position(nextX, y);
            if (y == 0 || !shipHit && y == nextY - 1) {
                turn = 4;
            }
        }

        if (y < sizeY - 1 && turn == 4 && y <= nextY + 4) {

            y++;
            p = new Position(nextX, y);
        }

        if (!shotListArray.contains(p)) {

            shotListArray.add(p);
        } else {
            getShipKillerShot();
        }
        return p;
    }

    private Position getNormalShot() {
        newShot = lastNormalShot;

        while (shotListArray.contains(newShot)) {
            nextX = newShot.x +2;
            nextY = newShot.y;
            if (nextX >= sizeX) {
                ++nextY;
                if (nextY % 2 == 0) {
                    nextX = 0;
                } else {
                    nextX = 1;
                }
                if (nextY >= sizeY) {
                    nextY = 0;
                }
            }
        newShot = new Position(nextX, nextY);
        }
       
        return newShot;
    }

    /**
     * Called in the beginning of each match to inform about the number of
     * rounds being played.
     *
     * @param rounds int the number of rounds i a match
     */
    @Override
    public void startMatch(int rounds) {
        //Do nothing
    }

    /**
     * Called at the beginning of each round.
     *
     * @param round int the current round number.
     */
    @Override
    public void startRound(int round) {
        shipStillALive = false;
        shipHit = false;
        nextX = 0;
        nextY = 0;
        numberOfShips_BeforeLastShot = 0;
        //lastNormalShot = null;
    }

    /**
     * Called at the end of each round to let you know if you won or lost.
     * Compare your points with the enemy's to see who won.
     *
     * @param round int current round number.
     * @param points your points this round: 100 - number of shot used to sink
     * all of the enemy's ships.
     *
     * @param enemyPoints int enemy's points this round.
     */
    @Override
    public void endRound(int round, int points, int enemyPoints) {
        //Do nothing
    }

    /**
     * Called at the end of a match (that usually last 1000 rounds) to let you
     * know how many losses, victories and draws you scored.
     *
     * @param won int the number of victories in this match.
     * @param lost int the number of losses in this match.
     * @param draw int the number of draws in this match.
     */
    @Override
    public void endMatch(int won, int lost, int draw) {
        //Do nothing
    }

    public void addNearbyPositionsToHitList() {
        Position up = new Position(x, y + 1);
        Position down = new Position(x, y - 1);
        Position left = new Position(x - 1, y);
        Position right = new Position(x + 1, y);

        if (x - 1 >= 0 && !shotListArray.contains(left) && !temporaryHitList.contains(left)) {
            temporaryHitList.add(left);
        }
        if (y - 1 >= 0 && !shotListArray.contains(down) && !temporaryHitList.contains(down)) {
            temporaryHitList.add(down);
        }
        if (x + 1 < sizeX && !shotListArray.contains(right) && !temporaryHitList.contains(right)) {
            temporaryHitList.add(right);
        }
        if (y + 1 < sizeY && !shotListArray.contains(up) && !temporaryHitList.contains(up)) {
            temporaryHitList.add(up);
        }
    }

    public Position getNextHitFromList() {
        return temporaryHitList.remove(temporaryHitList.size() - 1);
    }
    public int fleetSize (Fleet enemyShips){
        int result = 0;
        for (Ship enemyShip : enemyShips) {
            
            result += enemyShip.size();
        }
        return result;
    }
}
