package com.company;

public interface MazeSolver
{
	void solveMaze();

	Maze.Cell getCurrentCell();

	Maze.Cell getCellToMove();

	void addGraphics( MazeGraphics graphics );

//	Maze.Cell getNextCell();

//	void moveTo( Maze.Cell next );
}
