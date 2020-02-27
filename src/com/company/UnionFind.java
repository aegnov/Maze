package com.company;

import java.util.Arrays;

public class UnionFind
{
	private int[] roots;
	private int[] fathers;
	private int[] sizes;
	private int gridWidth;

	public static int sentinel = -1;

//	public UnionFind( int elements, int sets )
//	{
//		roots = new int[sets + 1];
//		fathers = new int[elements];
//		sizes = new int[sets + 1];
//
//		roots[0] = sentinel;
//		sizes[0] = sentinel;
//
//		for ( int i = 1; i <= sets; i++ )
//		{
//			sizes[i] = 0;
//		}
//	}

	public UnionFind( int elements, int gridWidth )
	{
		this.gridWidth = gridWidth;

		roots = new int[elements + 1];
		fathers = new int[elements];
		sizes = new int[elements + 1];

		roots[0] = sentinel;
		sizes[0] = sentinel;

		initOnePerSet();
	}

	public void initOnePerSet()
	{
		for ( int i = 0; i < fathers.length; i++ )
		{
			roots[i + 1] = i;
			fathers[i] = -(i + 1);
			sizes[i] = 1;
		}
	}

	public int find( int elemNo )
	{
		if ( fathers[elemNo] < 0 )
		{
			return Math.abs( fathers[elemNo] );
		}

		fathers[elemNo] = roots[ find( fathers[elemNo] ) ];
		return Math.abs( fathers[ fathers[elemNo] ] );
	}

	public void union( int set1, int set2, int setOut )
	{
		if ( sizes[set1] > sizes[set2] )
		{
			int tmp = set1;
			set1 = set2;
			set2 = tmp;
		}

		fathers[ roots[set1] ] = roots[set2];
		roots[setOut] = roots[set2];
		fathers[ roots[set2] ] = - setOut;
		sizes[setOut] = sizes[set1] + sizes[set2];
	}

	public void printFathers()
	{
		System.out.println( Arrays.toString( fathers ) );
	}

	public int findByCell( Maze.Cell cell )
	{
		return find( cell.getRow() * gridWidth + cell.getColumn() );
	}
}
