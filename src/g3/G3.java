/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package g3;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

/**
 *
 * @author Tobias Grundtvig
 */
public class G3 implements PlayerFactory<BattleshipsPlayer>
{

    public G3(){}
    
    
    @Override
    public BattleshipsPlayer getNewInstance()
    {
        return new Player();
    }

    @Override
    public String getID()
    {
        return "G3";
    }

    @Override
    public String getName()
    {
        return "G3 player";
    }
    
}
