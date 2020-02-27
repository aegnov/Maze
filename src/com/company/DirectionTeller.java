package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class DirectionTeller
{
	public enum directions { up, right, down, left }
	private static ArrayList<directions> dirList = new ArrayList<>();

	public static directions turnRight( directions dir, Collection<directions> options )
	{
		directions closestRight = dir;
		while ( true )
		{
			closestRight = turnRight( closestRight );
			if ( options.contains( closestRight ) ) break;
			else closestRight = getOpposite( closestRight );
		}
		return closestRight;
	}

	public static directions turnRight( directions dir )
	{
		dirList.addAll( Arrays.asList( directions.values() ) );
		int dirIndex = dirList.indexOf( dir );
		int nextIndex = ( dirIndex + 1 ) % dirList.size();
		return dirList.get( nextIndex );
	}

	public static directions getOpposite( directions dir )
	{
		dirList.addAll( Arrays.asList( directions.values() ) );
		int dirIndex = dirList.indexOf( dir );
		int nextIndex = ( dirIndex + 2 ) % dirList.size();
		return dirList.get( nextIndex );
	}

	public static directions turnLeft( directions dir )
	{
		dirList.addAll( Arrays.asList( directions.values() ) );
		int dirIndex = dirList.indexOf( dir );
		int nextIndex = ( dirIndex + dirList.size() - 1 ) % dirList.size();
		return dirList.get( nextIndex );
	}

	public static directions turnLeft( directions dir, Collection<directions> options )
	{
		directions closestLeft = dir;
		while ( true )
		{
			closestLeft = turnLeft( closestLeft );
			if ( options.contains( closestLeft ) ) break;
			else closestLeft = getOpposite( closestLeft );
		}
		return closestLeft;
	}

	public static directions turn( directions turnDir, directions faceDir, Collection<directions> options )
	{
		switch ( turnDir )
		{
			case right:
				return turnRight( faceDir, options );
			case left:
				return turnLeft( faceDir, options );
			default:
				return null;
		}
	}
}
