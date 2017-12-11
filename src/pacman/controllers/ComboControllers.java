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
 * The Class ComboControllers
 * Will assign AI different controllers
 */


public class ComboControllers extends Controller<EnumMap<GHOST,MOVE>>
{
	private final static int PILL_PROXIMITY = 20;		//if ghost is this close to a power pill, guard it
	
	int m;
	int[] powerPills;
	private final static float CONSISTENCY = 1.0f;	//carry out intended move with this probability
	private EnumMap<GHOST,MOVE> moves = new EnumMap<GHOST,MOVE>(GHOST.class);
	private MOVE[] allMoves=MOVE.values();
	Random rnd = new Random();
	
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) 
	{
		moves.clear();
		
		int select = 0;
		int targetNode  = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		int targetNode2 = game.getPacmanCurrentNodeIndex();

		if(game.doesGhostRequireAction(GHOST.BLINKY)) //RandomlySmart Controller
		{
			if(game.getGhostEdibleTime(GHOST.BLINKY)>0 || closeToPowerRL(game))	//retreat from Ms Pac-Man if edible or if Ms Pac-Man is close to power pill
			{
				moves.put(GHOST.BLINKY,game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(GHOST.BLINKY),
						game.getPacmanCurrentNodeIndex(),game.getGhostLastMoveMade(GHOST.BLINKY),DM.PATH));
			
			}
			else
			{
				m = rnd.nextInt(allMoves.length);
				moves.put(GHOST.BLINKY, allMoves[m]);
			}
		}
		
		if(game.doesGhostRequireAction(GHOST.INKY)) //KamakazeController
		{
			if(closeToPower(game, GHOST.INKY))	//go towards pill if close to power pill
			{	
				moveToPower(game, GHOST.INKY);
			}
			else
			{
				m = rnd.nextInt(allMoves.length);
				moves.put(GHOST.INKY, allMoves[m]);
			}
		}	
			
		if(game.doesGhostRequireAction(GHOST.PINKY)) //RandomLegacy Controller
		{
			if(closeToPower(game, GHOST.PINKY))	//go towards pill if close to power pill
			{	
				moveToPower(game, GHOST.PINKY);
			}
			else
			{
				m = rnd.nextInt(allMoves.length);
				moves.put(GHOST.PINKY, allMoves[m]);
			}
		}
			
		if(game.doesGhostRequireAction(GHOST.SUE)) //RandomlySmart Controller
		{
			if(game.getGhostEdibleTime(GHOST.SUE)>0 || closeToPowerRL(game))	//retreat from Ms Pac-Man if edible or if Ms Pac-Man is close to power pill
			{
				moves.put(GHOST.SUE,game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(GHOST.SUE),
						game.getPacmanCurrentNodeIndex(),game.getGhostLastMoveMade(GHOST.SUE),DM.PATH));
			
			}
			else
			{
				m = rnd.nextInt(allMoves.length);
				moves.put(GHOST.SUE,allMoves[m]);
			}
		}
			
		return moves;
	}
	
	//This helper function checks if Ms Pac-Man is close to an available power pill
	private boolean closeToPowerRL(Game game)
	{
		int[] powerPills=game.getPowerPillIndices();
			    	
		for(int i=0;i<powerPills.length;i++)
			if(game.isPowerPillStillAvailable(i) && game.getShortestPathDistance(powerPills[i],game.getPacmanCurrentNodeIndex())<PILL_PROXIMITY)
				return true;
		
		return false;
   }
	
	private boolean closeToPower(Game game, GHOST ghost)
	{
		int[] powerPills=game.getPowerPillIndices();
    	
    	for(int i=0;i<powerPills.length;i++)
    		if(game.isPowerPillStillAvailable(i) && game.getShortestPathDistance(powerPills[i],game.getGhostCurrentNodeIndex(ghost))<PILL_PROXIMITY)
    			return true;

        return false;
	}

	//This helper function checks if Ms Pac-Man is close to an available power pill
	private void moveToPower(Game game, GHOST ghost)
	{
		int[] powerPills = game.getPowerPillIndices();
			    	
		for(int i=0;i<powerPills.length;i++)
		{
			if(game.isPowerPillStillAvailable(i) && game.getShortestPathDistance(powerPills[i],game.getGhostCurrentNodeIndex(ghost))<PILL_PROXIMITY)
			{
				moves.put(ghost,game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),
						powerPills[i],game.getGhostLastMoveMade(ghost),DM.PATH));
			}
		}
	}

}
