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
 * Will head to nearest power pill and patrol
 * If all powerpills gone chase pacman
 */

public class KamikazeController extends Controller<EnumMap<GHOST,MOVE>>
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
		powerPills = game.getPowerPillIndices();
		
		for(GHOST ghost : GHOST.values())
		{
			if(game.doesGhostRequireAction(ghost))
			{
				if(closeToPower(game, ghost))	//go towards pill if close to power pill
				{	
					moveToPower(game, ghost);
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
	
	private boolean powerStillAvailable(Game game)
	{
		int[] powerPills = game.getPowerPillIndices();
		int count = 0;
		
		for(int i=0;i<powerPills.length;i++)
		{
			if(game.isPowerPillStillAvailable(i))
			{
				count++;
			}
		}
		
		if(count == 4)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
