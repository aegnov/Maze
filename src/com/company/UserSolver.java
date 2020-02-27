package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserSolver implements MazeSolver, KeyListener
{
	private Maze.Cell currentCell, cellToMove;
	private Maze maze;
	private MazeGraphics graphics;

	public UserSolver( Maze maze )
	{
		this.maze = maze;
		currentCell = maze.getStartPoint();
		cellToMove = currentCell;
	}

	public void addGraphics( MazeGraphics graphics )
	{ this.graphics = graphics; }

	@Override
	public void solveMaze() {}

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

	private void moveRight()
	{
		if ( maze.canMoveRight( currentCell ) )
		{
			cellToMove = maze.getRightNeighbor( currentCell );
			graphics.moveSolver( this );
			currentCell = cellToMove;
		}
	}

	private void moveLeft()
	{
		if ( maze.canMoveLeft( currentCell ) )
		{
			cellToMove = maze.getLeftNeighbor( currentCell );
			graphics.moveSolver( this );
			currentCell = cellToMove;
		}
	}

	private void moveUp()
	{
		if ( maze.canMoveUp( currentCell ) )
		{
			cellToMove = maze.getUpNeighbor( currentCell );
			graphics.moveSolver( this );
			currentCell = cellToMove;
		}
	}

	private void moveDown()
	{
		if ( maze.canMoveDown( currentCell ) )
		{
			cellToMove = maze.getDownNeighbor( currentCell );
			graphics.moveSolver( this );
			currentCell = cellToMove;
		}
	}

	@Override
	public void keyTyped( KeyEvent keyEvent ) {}

	@Override
	public void keyPressed( KeyEvent keyEvent )
	{
		int code = keyEvent.getKeyCode();
		switch ( code )
		{
			case KeyEvent.VK_UP:
				moveUp();
				break;
			case KeyEvent.VK_DOWN:
				moveDown();
				break;
			case KeyEvent.VK_LEFT:
				moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				moveRight();
				break;
		}
	}

	@Override
	public void keyReleased( KeyEvent keyEvent ) {}
}
