package pacman.controllers;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Random;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * The Class KamikazeController
 * Ghost will randomly move around the map. 
 * Others will follow that main ghost
 */

public class ConvoyController extends Controller<EnumMap<GHOST,MOVE>>
{
	Random rnd=new Random();
	EnumMap<GHOST,MOVE> myMoves=new EnumMap<GHOST,MOVE>(GHOST.class);
	MOVE[] moves=MOVE.values();
	
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) 
	{	
		myMoves.clear();
		
		int targetNode  = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		int targetNode2 = game.getPacmanCurrentNodeIndex();
		
		if(game.doesGhostRequireAction(GHOST.BLINKY))
			myMoves.put(GHOST.BLINKY,
					game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(GHOST.BLINKY),targetNode2,game.getGhostLastMoveMade(GHOST.BLINKY),DM.PATH));
		
		if(game.doesGhostRequireAction(GHOST.INKY))
			myMoves.put(GHOST.INKY,
					game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(GHOST.INKY),targetNode,game.getGhostLastMoveMade(GHOST.INKY),DM.MANHATTAN));
		
		if(game.doesGhostRequireAction(GHOST.PINKY))
			myMoves.put(GHOST.PINKY,
					game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(GHOST.PINKY),targetNode,game.getGhostLastMoveMade(GHOST.PINKY),DM.EUCLID));
		
		if(game.doesGhostRequireAction(GHOST.SUE))
			myMoves.put(GHOST.SUE,
					game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(GHOST.PINKY),targetNode,game.getGhostLastMoveMade(GHOST.PINKY),DM.EUCLID));
		
		return myMoves;
	}

}
