package pacman.controllers;

import java.util.Random;

import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GameStateFirst extends Controller
{
	public MOVE getMove(Game game, long timeDue) 
	{
		int gameScoreRight, gameScoreLeft;
		Random rand = new Random();
		
		Game simulateRightMove = game.copy();
		simulateRightMove.advanceGame(MOVE.RIGHT, null);
		gameScoreRight = simulateRightMove.getScore();
		
		Game simulateLeftMove = game.copy();
		simulateLeftMove.advanceGame(MOVE.LEFT, null);
		gameScoreLeft = simulateRightMove.getScore();
		
		if(gameScoreLeft < gameScoreRight)
		{
			return MOVE.RIGHT;
		}
		else if(gameScoreLeft > gameScoreRight)
		{
			return MOVE.LEFT;
		}
		else
		{
			return null;
		}
	}

}
