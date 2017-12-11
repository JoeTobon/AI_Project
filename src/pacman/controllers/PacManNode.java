package pacman.controllers;

import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class PacManNode 
{
	int depth;
	Game gameState;
	
	public PacManNode(Game gamestate, int depth)
	{
		this.depth = depth;
		this.gameState = gamestate.copy();
	}
}
