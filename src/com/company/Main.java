package com.company;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        int size = 30;
        Maze maze = Maze.kruskalMaze( size, size );
        MouseSolver mouse = new MouseSolver( maze );
        WallFolower follower1 = new RightHandSolver( maze );
//        WallFolower follower2 = new LeftHandSolver( maze );
        UserSolver user = new UserSolver( maze );
        ArrayList<MazeSolver> solvers = new ArrayList<>();
//        solvers.add( mouse );
//        solvers.add( follower1 );
//        solvers.add( follower2 );
        solvers.add( user );

        MazeGraphics graphics = new MazeGraphics( maze, solvers, 900, 950 );

//        maze.getCellNeighbors( maze.getCell( 0, 0 ) ).forEach( cell ->
//                System.out.println( cell.toString() ) );

//        maze.getWallMap().forEach( ( id, wall ) -> System.out.println( id + ": " + wall.toString() ) );

//        EventQueue.invokeLater( () ->
//        {
            MazeFrame ex = new MazeFrame( graphics );
            ex.setVisible( true );
//            ex.startSolvers();
//        } );

//        graphics.startTimer();

//        mouse.solveMaze();


//        try
//        {
//            follower1.solveMaze();
//            follower2.solveMaze();
//        }
//        catch ( Exception e )
//        {
//            e.printStackTrace();
//        }
    }
}
