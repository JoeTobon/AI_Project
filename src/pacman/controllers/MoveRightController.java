package pacman.controllers;

import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MoveRightController extends Controller
{	
	@Override
	public MOVE getMove(Game game, long timeDue) 
	{
		// TODO Auto-generated method stub
		return MOVE.RIGHT;
	}
}
