package g3;

import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.io.PrintStream;
import java.util.Arrays;

public class PlaceShipHandler {

    public int[][] board;

    public PlaceShipHandler() {
        board = new int[10][10];

    }

    public boolean shipPlacement(Position pos, Ship ship, boolean vertical) {

        for (int i = 0; i < ship.size(); i++) {
            if (!vertical)//ofoghi 
            {
                if (board[(pos.x + i)][pos.y] != 0) {
                    return false;
                }
            } else if (board[pos.x][(pos.y + i)] != 0) {
                return false;
            }
        }
        if (!vertical) {
            for (int i = 0; i < ship.size(); i++) {
                board[(pos.x + i)][pos.y] = 1;
            }
        } else {
            
            for (int i = 0; i < ship.size(); i++) {
                board[pos.x][(pos.y + i)] = 1;
            }
        }
        return true;
    }

//    
    
}
