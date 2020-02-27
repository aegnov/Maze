package com.company;

import javax.swing.*;


class MazeFrame extends JFrame
{
    MazeGraphics graphics;
//    public MazeFrame( Maze maze, Collection<MazeSolver> solvers )
//    {
//        initUI( maze, solvers );
//    }
    public MazeFrame( MazeGraphics graphics )
    {
        this.graphics = graphics;
        initUI();
    }

    private void initUI()
    {
        add( graphics );

        setTitle( "Maze" );
        setSize( graphics.getWidth(), graphics.getHeight() );

        setLocationRelativeTo( null );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

//    public void startSolvers()
//    {
//        graphics.startSolvers();
//    }


//    private void initUI( Maze maze, Collection<MazeSolver> solvers )
//    {
//        final MazeGraphics surface = new MazeGraphics( maze );
//        add(surface);
//
//        setTitle("Maze");
//
//        int width = 900, height = 950;
//        setSize( width, height );
//
//        surface.setCellSize( width );
//        surface.initSolvers( solvers );
//
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
}