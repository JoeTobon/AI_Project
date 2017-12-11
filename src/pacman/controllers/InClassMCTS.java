package pacman.controllers;

import java.util.LinkedList;
import java.util.Queue;

import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class InClassMCTS extends Controller<MOVE>
{
	public static StarterGhosts ghosts = new StarterGhosts();
	
	int pacManMoveDepth = 7;
	
	public MOVE getMove(Game game, long timeDue) 
	{
		int highScore = 0;
		MOVE highMove = MOVE.NEUTRAL;
		
		//1. Take in game state and advance game
		//Up
		Game upGameState = game.copy();
		upGameState.advanceGame(MOVE.UP, ghosts.getMove(upGameState, 0));
		PacManNode pmnUp = new PacManNode(upGameState, 1);
		int upScore = bfs_amy(pmnUp, pacManMoveDepth);
		
		//Down
		Game downGameState = game.copy();
		downGameState.advanceGame(MOVE.DOWN, ghosts.getMove(downGameState, 0));
		PacManNode pmnDown = new PacManNode(downGameState, 1);
		int downScore = bfs_amy(pmnDown, pacManMoveDepth);
		
		//Left
		Game leftGameState = game.copy();
		leftGameState.advanceGame(MOVE.LEFT, ghosts.getMove(leftGameState, 0));
		PacManNode pmnLeft = new PacManNode(leftGameState, 1);
		int leftScore = bfs_amy(pmnLeft, pacManMoveDepth);
		
		//Right
		Game rightGameState = game.copy();
		rightGameState.advanceGame(MOVE.RIGHT, ghosts.getMove(rightGameState, 0));
		PacManNode pmnRight = new PacManNode(rightGameState, 1);
		int rightScore = bfs_amy(pmnRight, pacManMoveDepth);
		
		//2. Find highScore
		if(upScore > highScore)
		{
			highScore = upScore;
			highMove = MOVE.UP;
		}
		if(downScore > highScore)
		{
			highScore = downScore;
			highMove = MOVE.DOWN;
		}
		if(rightScore > highScore)
		{
			highScore = rightScore;
			highMove = MOVE.RIGHT;
		}
		if(leftScore > highScore)
		{
			highScore = leftScore;
			highMove = MOVE.LEFT;
		}
		
		System.out.println("HighMove is: " + highMove);
		return highMove;
	}
	
	public int bfs_amy(PacManNode rootGameState, int maxdepth)
	{
	           MOVE[] allMoves=Constants.MOVE.values();
	           int depth = 0;
	           int highScore = -1;
	        
	           Queue<PacManNode> queue = new LinkedList<PacManNode>();
	           queue.add(rootGameState);

	        //System.out.println("Adding Node at Depth: " + rootGameState.depth);
	               
	 
	           while(!queue.isEmpty())
	           {
	               PacManNode pmnode = queue.remove();
	               //System.out.println("Removing Node at Depth: " + pmnode.depth);
	                   
	               if(pmnode.depth >= maxdepth)
	               {
	                   int score = pmnode.gameState.getScore();
	                   if (highScore < score)
	                       highScore = score;
	               }
	               else
	               {
	                   //GET CHILDREN
	                   for(MOVE m: allMoves)
	                   {
	                           Game gameCopy = pmnode.gameState.copy();
	                           gameCopy.advanceGame(m, ghosts.getMove(gameCopy, 0));
	                           PacManNode node = new PacManNode(gameCopy, pmnode.depth+1);
	                           queue.add(node);
	                   }
	               }
	           }
	               
	           return highScore;
	}
}
