package pacman.controllers;

import java.util.EnumMap;
import java.util.Random;
import pacman.controllers.Controller;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

import static pacman.game.Constants.*;

/*
 *  RandomLegacy Class.
 *  Combines the Legacy and RandomGhosts controllers 
 */


//Change print statements to log file
//Indicate which ghost has which movement
public class RandomLegacy extends Controller<EnumMap<GHOST,MOVE>>
{
	EnumMap<GHOST,MOVE> myMoves=new EnumMap<GHOST,MOVE>(GHOST.class);
	MOVE[] moves=MOVE.values();
	Random rnd = new Random();
	
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) 
	{
		int select = 0;
		myMoves.clear();
		
		int targetNode=game.getPacmanCurrentNodeIndex();
		
		for(GHOST ghostType : GHOST.values())	
		{
			select = rnd.nextInt(4) + 1;
			
			//If ghost requires action randomly choose between one of the four legacy ghost movements
			if(game.doesGhostRequireAction(ghostType))
			{
				switch(select)
				{
					case 1:		//Blinky Movement
						myMoves.put(ghostType,
								game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType),targetNode,game.getGhostLastMoveMade(ghostType),DM.PATH));
						
						System.out.println("Blinky Chosen");
						
					case 2:		//Inky Movement
						myMoves.put(ghostType,
								game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType),targetNode,game.getGhostLastMoveMade(ghostType), DM.MANHATTAN));
						
						System.out.println("Inky Chosen");
						
					case 3:		//Pinky Movement
						myMoves.put(ghostType,
								game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType),targetNode,game.getGhostLastMoveMade(ghostType), DM.EUCLID));
					
						System.out.println("Pinky Chosen");
					
					case 4: 	//Sue Movement
						myMoves.put(ghostType,moves[rnd.nextInt(moves.length)]);
						
						System.out.println("Sue Chosen");
				}
			}
		}
		
		return myMoves;
	}
}
