package com.company;

import java.util.*;

public class Maze
{
	public static class Cell
	{
		private int row, col;
		private int visited;

		public Cell( int row, int col )
		{
			this.row = row;
			this.col = col;
			visited = 0;
		}

		public void markVisited()
		{ visited++; }

		public int getVisited()
		{ return visited; }

		public void resetVisited()
		{ visited = 0; }

		@Override
		public String toString()
		{
			return row + ":" + col;
		}

		public int getColumn()
		{ return col; }

		public int getRow()
		{ return row; }

		public int getDistance( Cell other )
		{
			return Math.abs( other.row - row ) + Math.abs( other.col - col );
		}

		public static boolean areCellsInRow( Cell cell1, Cell cell2 )
		{
			return cell1.row == cell2.row;
		}
	}

	public static class Wall
	{
		private Cell f1, f2;

		public Wall( Cell f1, Cell f2 )
		{
			this.f1 = f1;
			this.f2 = f2;
		}

		public Cell getF1()
		{ return f1; }

		public Cell getF2()
		{ return f2; }

		public boolean isVertical()
		{ return Cell.areCellsInRow( f1, f2 ); }

		@Override
		public String toString()
		{
			return f1.toString() + " | " + f2.toString();
		}

		public static String computeID( Cell cell1, Cell cell2 )
		{
			if ( cell1.getRow() < cell2.getRow() || cell1.getColumn() < cell2.getColumn() )
				return cell1.toString() + "|" + cell2.toString();
			else
				return cell2.toString() + "|" + cell1.toString();
		}
	}

	private int height, width;
	private HashMap<String, Wall> wallMap;
	private Cell[][] cells;
	private Cell startPoint, endPoint;

	public Maze( int height, int width )
	{
		this.height = height;
		this.width = width;

		wallMap = new HashMap<>();
		cells = new Cell[height][width];

		for ( int i = 0; i < height; i++ )
		{
			for ( int j = 0; j < width; j++ )
			{
				cells[i][j] = new Cell( i, j );
			}
		}

		startPoint = cells[0][0];
		endPoint = cells[height-1][width-1];
	}

	public int getWidth()
	{ return width; }

	public int getHeight()
	{ return height; }

	public Cell getCell( int i, int j )
	{ return cells[i][j]; }

	public Cell getStartPoint()
	{ return startPoint; }

	public Cell getEndPoint()
	{ return endPoint; }

	public HashMap<String,Wall> getWallMap()
	{ return wallMap; }

	public static Maze fullGrid( int height, int width )
	{
		Maze maze = new Maze( height, width );

		for ( int i = 0; i < height; i++ )
		{
			for ( int j = 0; j < width; j++ )
			{
				if ( i > 0 )
					maze.wallMap.put( Wall.computeID( maze.cells[i][j], maze.cells[i-1][j] ),
							new Wall( maze.cells[i][j], maze.cells[i-1][j] ) );
				if ( j > 0 )
					maze.wallMap.put( Wall.computeID( maze.cells[i][j], maze.cells[i][j-1] ),
							new Wall( maze.cells[i][j], maze.cells[i][j-1] ) );
			}
		}

		return maze;
	}

	public static Maze kruskalMaze( int height, int width )
	{
		Maze maze = fullGrid( height, width );

		UnionFind sets = new UnionFind( height * width, width );

		LinkedList<String> wallKeys = new LinkedList<>( maze.wallMap.keySet() );
		Collections.shuffle( wallKeys );

		for ( String wallDesc : wallKeys )
		{
			Wall wall = maze.wallMap.get( wallDesc );
			int set1 = sets.findByCell( wall.f1 );
			int set2 = sets.findByCell( wall.f2 );
			if ( set1 != set2 )
			{
				maze.wallMap.remove( wallDesc );
				sets.union( set1, set2, set2 );
			}
		}

		return maze;
	}

	public HashMap<DirectionTeller.directions,Cell> getCellNeighbours( Cell cell )
	{
		HashMap<DirectionTeller.directions,Cell> neighbors = new HashMap<>();

		if ( cell.row > 0 )
		{
			Cell neighbor = cells[cell.row - 1][cell.col];
			if ( !wallMap.containsKey( Wall.computeID( cell, neighbor ) ) )
			{
				neighbors.put( DirectionTeller.directions.up, neighbor );
			}
		}
		if ( cell.row < height - 1 )
		{
			Cell neighbor = cells[cell.row + 1][cell.col];
			if ( !wallMap.containsKey( Wall.computeID( cell, neighbor ) ) )
			{
				neighbors.put( DirectionTeller.directions.down, neighbor );
			}
		}
		if ( cell.col > 0 )
		{
			Cell neighbor = cells[cell.row][cell.col - 1];
			if ( !wallMap.containsKey( Wall.computeID( cell, neighbor ) ) )
			{
				neighbors.put( DirectionTeller.directions.left, neighbor );
			}
		}
		if ( cell.col < width - 1 )
		{
			Cell neighbor = cells[cell.row][cell.col + 1];
			if ( !wallMap.containsKey( Wall.computeID( cell, neighbor ) ) )
			{
				neighbors.put( DirectionTeller.directions.right, neighbor );
			}
		}

		return neighbors;

	}

	public boolean canMoveRight( Cell cell )
	{
		if ( cell.getColumn() == width - 1 ) return false;
		else return !containsWall( cell, getRightNeighbor( cell ) );
	}

	public boolean canMoveLeft( Cell cell )
	{
		if ( cell.getColumn() == 0 ) return false;
		else return !containsWall( cell, getLeftNeighbor( cell ) );
	}

	public boolean canMoveUp( Cell cell )
	{
		if ( cell.getRow() == 0 ) return false;
		else return !containsWall( cell, getUpNeighbor( cell ) );
	}

	public boolean canMoveDown( Cell cell )
	{
		if ( cell.getRow() == height - 1 ) return false;
		else return !containsWall( cell, getDownNeighbor( cell ) );
	}

	public Cell getRightNeighbor( Cell cell )
	{
		if ( cell.getColumn() == width - 1 ) return null;
		else return cells[cell.getRow()][cell.getColumn() + 1];
	}

	public Cell getLeftNeighbor( Cell cell )
	{
		if ( cell.getColumn() == 0 ) return null;
		else return cells[cell.getRow()][cell.getColumn() - 1];
	}

	public Cell getUpNeighbor( Cell cell )
	{
		if ( cell.getRow() == 0 ) return null;
		else return cells[cell.getRow() - 1][cell.getColumn()];
	}

	public Cell getDownNeighbor( Cell cell )
	{
		if ( cell.getRow() == height - 1 ) return null;
		else return cells[cell.getRow() + 1][cell.getColumn()];
	}

	public void resetCellsVisited()
	{
		for ( Cell[] row : cells )
		{
			for ( Cell cell : row )
			{
				cell.resetVisited();
			}
		}
	}

	public ArrayList<Cell> getCellNeighbors( Cell cell )
	{
		ArrayList<Cell> neighbors = new ArrayList<>();

		if ( cell.row > 0 )
		{
			Cell neighbor = cells[cell.row - 1][cell.col];
			if ( !wallMap.containsKey( Wall.computeID( cell, neighbor ) ) )
			{
				neighbors.add( neighbor );
			}
		}
		if ( cell.row < height - 1 )
		{
			Cell neighbor = cells[cell.row + 1][cell.col];
			if ( !wallMap.containsKey( Wall.computeID( cell, neighbor ) ) )
			{
				neighbors.add( neighbor );
			}
		}
		if ( cell.col > 0 )
		{
			Cell neighbor = cells[cell.row][cell.col - 1];
			if ( !wallMap.containsKey( Wall.computeID( cell, neighbor ) ) )
			{
				neighbors.add( neighbor );
			}
		}
		if ( cell.col < width - 1 )
		{
			Cell neighbor = cells[cell.row][cell.col + 1];
			if ( !wallMap.containsKey( Wall.computeID( cell, neighbor ) ) )
			{
				neighbors.add( neighbor );
			}
		}

		return neighbors;
	}

	public boolean containsWall( Cell cell1, Cell cell2 )
	{
		System.out.println( cell1.toString() + cell2.toString() );
		return wallMap.containsKey( Wall.computeID( cell1, cell2 ) );
	}
}
