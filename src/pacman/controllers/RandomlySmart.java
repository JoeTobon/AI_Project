package pacman.controllers;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Random;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.controllers.Controller;
import pacman.myLogger;

/*
 * The Class RandomGhosts.
 */
public final class RandomlySmart extends Controller<EnumMap<GHOST,MOVE>>
{	
	private final static int PILL_PROXIMITY=15;		//if Ms Pac-Man is this close to a power pill, back away
	
	
	int m;
	private EnumMap<GHOST,MOVE> moves = new EnumMap<GHOST,MOVE>(GHOST.class);
	private MOVE[] allMoves=MOVE.values();
	private Random rnd=new Random();
	
	/* (non-Javadoc)
	 * @see pacman.controllers.Controller#getMove(pacman.game.Game, long)
	 */
	public EnumMap<GHOST,MOVE> getMove(Game game,long timeDue)
	{	
		moves.clear();
		
		for(GHOST ghost : GHOST.values())
		{
			if(game.doesGhostRequireAction(ghost))
			{
				if(game.getGhostEdibleTime(ghost)>0 || closeToPower(game))	//retreat from Ms Pac-Man if edible or if Ms Pac-Man is close to power pill
				{
					moves.put(ghost,game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghost),
							game.getPacmanCurrentNodeIndex(),game.getGhostLastMoveMade(ghost),DM.PATH));
				}
				else
				{
					m = rnd.nextInt(allMoves.length);
					moves.put(ghost,allMoves[m]);
				}
			}
		}
		
		
		
		
		return moves;
	}
		
	//This helper function checks if Ms Pac-Man is close to an available power pill
	private boolean closeToPower(Game game)
	{
		int[] powerPills=game.getPowerPillIndices();
		    	
		for(int i=0;i<powerPills.length;i++)
			if(game.isPowerPillStillAvailable(i) && game.getShortestPathDistance(powerPills[i],game.getPacmanCurrentNodeIndex())<PILL_PROXIMITY)
				return true;
	
		return false;
    }
}