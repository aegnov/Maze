package com.company;

import java.util.*;

public class MouseSolver implements MazeSolver
{
	private Maze.Cell currentCell, nextCell;
	private Maze maze;
	private MazeGraphics graphics;

	public MouseSolver( Maze maze )
	{
		this.maze = maze;
		currentCell = maze.getStartPoint();
		nextCell = currentCell;
	}

	public void addGraphics( MazeGraphics graphics )
	{ this.graphics = graphics; }

//	public Maze.Cell getNextCell()
//	{
//		currentCell.markVisited();
//		ArrayList<Maze.Cell> cellNeighbors = maze.getCellNeighbors( currentCell );
//
//		if ( cellNeighbors.size() == 1 )
//		{
//			currentCell.markVisited();
//			nextCell = cellNeighbors.get( 0 );
//			prevCell = currentCell;
//			return nextCell;
//		}
//
//		cellNeighbors.remove( prevCell );
//
//		Optional<Maze.Cell> lessVisited =
//				cellNeighbors.stream().min( Comparator.comparingInt( Maze.Cell::getVisited ) );
//		int lessVisits = lessVisited.map( Maze.Cell::getVisited ).orElse( 0 );
//
//		cellNeighbors.removeIf( cell -> cell.getVisited() != lessVisits );
////		if ( cellNeighbors.size() == 0 )
////			throw new Exception( "Lack of neighbors!" );
//
//		int index = new Random().nextInt( cellNeighbors.size() );
//		nextCell = cellNeighbors.get( index );
//		prevCell = currentCell;
//		return nextCell;
//	}

//	public void moveTo( Maze.Cell next )
//	{
//		currentCell = next;
//	}

	@Override
	public void solveMaze()
	{
		Maze.Cell prevCell = currentCell;

		while ( !currentCell.equals( maze.getEndPoint() ) )
		{
			currentCell.markVisited();
			ArrayList<Maze.Cell> cellNeighbors = maze.getCellNeighbors( currentCell );

			if ( cellNeighbors.size() == 1 )
			{
				currentCell.markVisited();
				nextCell = cellNeighbors.get( 0 );
				graphics.moveSolver( this );
				prevCell = currentCell;
				currentCell = nextCell;
				continue;
			}

			cellNeighbors.remove( prevCell );

			Optional<Maze.Cell> lessVisited =
					cellNeighbors.stream().min( Comparator.comparingInt( Maze.Cell::getVisited ) );
			int lessVisits = lessVisited.map( Maze.Cell::getVisited ).orElse( 0 );

			cellNeighbors.removeIf( cell -> cell.getVisited() != lessVisits );
//			if ( cellNeighbors.size() == 0 )
//				throw new Exception( "Lack of neighbors!" );

			int index = new Random().nextInt( cellNeighbors.size() );
			nextCell = cellNeighbors.get( index );
			graphics.moveSolver( this );
			prevCell = currentCell;
			currentCell = nextCell;
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
		return nextCell;
	}
}
