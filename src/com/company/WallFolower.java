package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class WallFolower implements MazeSolver
{
	private Maze.Cell currentCell, cellToMove;
	private Maze maze;
	private MazeGraphics graphics;
	private DirectionTeller.directions turnDir;

	public WallFolower( Maze maze, DirectionTeller.directions turnDir )
	{
		this.maze = maze;
		currentCell = maze.getStartPoint();
		cellToMove = currentCell;
		this.turnDir = turnDir;
	}

	public void addGraphics( MazeGraphics graphics )
	{ this.graphics = graphics; }

	@Override
	public void solveMaze()
	{
		DirectionTeller.directions faceDir = DirectionTeller.directions.right;
		DirectionTeller.directions dirToMove;

		while ( !currentCell.equals( maze.getEndPoint() ) )
		{
			HashMap<DirectionTeller.directions,Maze.Cell> cellNeighbors = maze.getCellNeighbours( currentCell );
			if ( cellNeighbors.size() > 1 )
			{
				cellNeighbors.remove( DirectionTeller.getOpposite( faceDir ) );
			}

			dirToMove = DirectionTeller.turn( turnDir, faceDir, cellNeighbors.keySet() );
			cellToMove = cellNeighbors.get( dirToMove );
			graphics.moveSolver( this );
			faceDir = dirToMove;
			currentCell = cellToMove;
		}

		System.out.println( "Solved!" );
	}

	@Override
	public Maze.Cell getCurrentCell()
	{
		return currentCell;
	}

	@Override
	public Maze.Cell getCellToMove()
	{
		return cellToMove;
	}
}

class RightHandSolver extends WallFolower
{
	public RightHandSolver( Maze maze )
	{
		super( maze, DirectionTeller.directions.right );
	}
}

class LeftHandSolver extends WallFolower
{
	public LeftHandSolver( Maze maze )
	{
		super( maze, DirectionTeller.directions.left );
	}
}
