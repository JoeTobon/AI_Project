package pacman.controllers;
import java.util.Random;

import pacman.game.Constants.MOVE;
import pacman.game.Game;

//Moves randomly right of left
public class RightLeftController extends Controller
{	
	public MOVE getMove(Game game, long timeDue) 
	{
		Random rand = new Random();
		
		if(rand.nextDouble() < .75)
		{
			return MOVE.LEFT;
		}
		else
		{
			return MOVE.RIGHT;
		}
	}

}
