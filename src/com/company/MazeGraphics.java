package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.HashMap;

class MazeGraphics extends JPanel implements ActionListener
{
    private Maze maze;
    private HashMap<MazeSolver, Ellipse2D.Double> solvers;
    private double margin = 10.0;
    private double cellSize;
    private int width, height;
    private double xMove, yMove;
    private double xDest, yDest;
    private volatile boolean movingDone;
    private Timer timer = new Timer( 1, this );

    public MazeGraphics( Maze maze, Collection<MazeSolver> solvers, int width, int height )
    {
        this.maze = maze;
        this.width = width;
        this.height = height;
        this.cellSize = ( width - 2 * margin ) / maze.getWidth();
        this.solvers = new HashMap<>();
        solvers.forEach( this::addSolver );
        solvers.forEach( solver -> System.out.println( this.solvers.get( solver ).x ) );
        System.out.println( cellSize );
    }

    public int getWidth()
    { return width; }

    public int getHeight()
    { return height; }

//    public void setCellSize( double width )
//    {
//        cellSize = ( width - 2 * margin ) / maze.getWidth();
//    }

//    public void initSolvers( Collection<MazeSolver> solvers )
//    {
//        solvers.forEach( this::addSolver );
//    }

    public void addSolver( MazeSolver solver )
    {
        Maze.Cell currentCell = solver.getCurrentCell();
        double x = margin + currentCell.getColumn() * cellSize;
        double y = margin + currentCell.getRow() * cellSize;
        Ellipse2D.Double solverBall = new Ellipse2D.Double( x, y, cellSize, cellSize );
        solvers.put( solver, solverBall );
        System.out.println( solver.toString() );
        solver.addGraphics( this );
        if ( solver instanceof UserSolver )
        {
            addKeyListener( (KeyListener) solver );
            setFocusable( true );
            setFocusTraversalKeysEnabled( false );
        }
    }

//    public void startTimer()
//    { timer.start(); }

//    public void startSolvers()
//    {
//        solvers.keySet().forEach( solver -> {
//            try
//            {
//                solver.solveMaze();
//            }
//            catch ( Exception e )
//            {
//                e.printStackTrace();
//            }
//        } );
//    }

    private void drawMaze( Graphics2D g2d )
    {
        g2d.setPaint( Color.blue );
        double wallDepth = 2.0;

        Rectangle2D.Double horizontalRect = new Rectangle2D.Double( 0, 0, cellSize, wallDepth );
        Rectangle2D.Double verticalRect = new Rectangle2D.Double( 0, 0, wallDepth, cellSize );

        for ( int i = 0; i < maze.getHeight(); i++ )
        {
            drawLeftWall( g2d, maze.getCell( i, 0 ), verticalRect );
            drawRightWall( g2d, maze.getCell( i, maze.getWidth() - 1 ), verticalRect );
        }

        for ( int j = 0; j < maze.getWidth(); j++ )
        {
            drawTopWall( g2d, maze.getCell( 0, j ), horizontalRect );
            drawBottomWall( g2d, maze.getCell( maze.getHeight() - 1, j ), horizontalRect );
        }

        maze.getWallMap().forEach( ( wallDesc, wall ) ->
        {
            if ( wall.isVertical() )
                    drawVerticalWall( g2d, wall, verticalRect );
            else
                drawHorizontalWall( g2d, wall, horizontalRect );
        } );

//        SolverImage lucyn = new SolverImage( "guinea01.png", 0, 0, 10, 10 );
//        g2d.drawImage( lucyn.getImage(), 0, 0, this );

//        ImageIcon lucyn = new ImageIcon( "guinea01.png" );
//        g2d.drawImage( lucyn.getImage(), 0, 0, this );
    }

    public void drawVerticalWall( Graphics2D g2d, Maze.Wall wall, Rectangle2D.Double rect )
    {
        int column = Math.max( wall.getF1().getColumn(), wall.getF2().getColumn() );
        rect.y = margin + wall.getF1().getRow() * rect.height;
        rect.x = margin + column * rect.height - ( rect.width / 2 );
        g2d.fill( rect );
    }

    public void drawHorizontalWall( Graphics2D g2d, Maze.Wall wall, Rectangle2D.Double rect )
    {
        int row = Math.max( wall.getF1().getRow(), wall.getF2().getRow() );
        rect.y = margin + row * rect.width - ( rect.height / 2 );
        rect.x = margin + wall.getF1().getColumn() * rect.width;
        g2d.fill( rect );
    }

    private void drawLeftWall( Graphics2D g2d, Maze.Cell cell, Rectangle2D.Double rect )
    {
        rect.x = margin + cell.getColumn() * rect.height - ( rect.width / 2 );
        rect.y = margin + cell.getRow() * rect.height;
        g2d.fill( rect );
    }

    private void drawRightWall( Graphics2D g2d, Maze.Cell cell, Rectangle2D.Double rect )
    {
        rect.x = margin + ( cell.getColumn() + 1 ) * rect.height - ( rect.width / 2 );
        rect.y = margin + cell.getRow() * rect.height;
        g2d.fill( rect );
    }

    private void drawBottomWall( Graphics2D g2d, Maze.Cell cell, Rectangle2D.Double rect )
    {
        rect.x = margin + cell.getColumn() * rect.width;
        rect.y = margin + ( cell.getRow() + 1 ) * rect.width - ( rect.height / 2 );
        g2d.fill( rect );
    }

    private void drawTopWall( Graphics2D g2d, Maze.Cell cell, Rectangle2D.Double rect )
    {
        rect.x = margin + cell.getColumn() * rect.width;
        rect.y = margin + cell.getRow() * rect.width - ( rect.height / 2 );
        g2d.fill( rect );
    }

    @Override
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        Graphics2D g2d = (Graphics2D) g;
        drawMaze( g2d );
        g2d.setPaint( Color.red );
        solvers.keySet().forEach( solver -> g2d.fill( solvers.get( solver ) ) );
//        timer.start();
    }

    public void moveSolver( MazeSolver solver )
    {
        Maze.Cell current = solver.getCurrentCell();
        Maze.Cell dest = solver.getCellToMove();
        xDest = dest.getColumn() * cellSize + margin;
        yDest = dest.getRow() * cellSize + margin;

//        System.out.println( current.toString() );
//        System.out.println( dest.toString() );
//        System.out.println( yDest + ", " + xDest );

        if ( Maze.Cell.areCellsInRow( current, dest ) )
        {
            yMove = 0.0;
            xMove = ( dest.getColumn() - current.getColumn() ); // / 40.0;
        }
        else
        {
            yMove = ( dest.getRow() - current.getRow() ); // / 40.0;
            xMove = 0.0;
        }

        movingDone = false;
//        timer.restart();
        timer.start();
//        System.out.println( "timer should start" );
//        System.out.println( "timer running: " + Boolean.toString( timer.isRunning() ) );
//        System.out.println( "timer listener: " + Arrays.toString( timer.getActionListeners() ) );
        if ( !( solver instanceof UserSolver ) )
            while ( !movingDone ) Thread.onSpinWait();
//
////        System.out.println( "moving solver" );
////        Maze.Cell current = solver.getCurrentCell();
////        Maze.Cell dest = solver.getCellToMove();
////        Ellipse2D.Double ball = solvers.get( solver );
////
////        int xMove = dest.getColumn() - current.getColumn();
////        int yMove = dest.getRow() - current.getRow();
////
////        if ( xMove != 0 )
////        {
////            double xDest = dest.getColumn() * cellSize + margin;
////            while ( ball.x != xDest ) moveHorizontally( ball, xMove );
////        }
////        else
////        {
////            double yDest = dest.getRow() * cellSize + margin;
////            while ( ball.y != yDest ) moveVertically( ball, yMove );
////        }
    }

//    private void moveVertically( Ellipse2D.Double ball, double dir )
//    {
//        ball.y += dir * cellSize;
//        repaint();
//    }

//    private void moveHorizontally( Ellipse2D.Double ball, double dir )
//    {
//        ball.x += dir * cellSize;
//        repaint();
//    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
//        for ( MazeSolver solver : solvers.keySet() )
//        {
//            Maze.Cell nextCell = solver.getNextCell();
//            Maze.Cell currentCell = solver.getCurrentCell();
//
//            double xDest = nextCell.getColumn() * cellSize + margin;
//            double yDest = nextCell.getRow() * cellSize + margin;
//
//            Ellipse2D.Double ball = solvers.get( solver );
//            double yToMove = Math.abs( ball.y - yDest );
//            double xToMove = Math.abs( ball.x - xDest );
//            double xMove, yMove;
//
//            if ( Maze.Cell.areCellsInRow( currentCell, nextCell ) )
//            {
//                yMove = 0.0;
//                xMove = ( nextCell.getColumn() - currentCell.getColumn() ); // / 40.0;
//            }
//            else
//            {
//                yMove = ( nextCell.getRow() - currentCell.getRow() ); // / 40.0;
//                xMove = 0.0;
//            }
//
//            if ( xToMove <= 0.5 && yToMove <= 0.5 )
//            {
//                solver.moveTo( nextCell );
//            }
//            else
//            {
//                if ( yToMove > 0.5 )
//                {
//                    ball.y += yMove;
//                }
//                if ( xToMove > 0.5 )
//                {
//                    ball.x += xMove;
//                }
//            }
//        }

        for ( MazeSolver solver : solvers.keySet() )
        {
            Ellipse2D.Double ball = solvers.get( solver );
            double yToMove = Math.abs( ball.y - yDest );
            double xToMove = Math.abs( ball.x - xDest );

//            System.out.println( yToMove + ", " + xToMove );

            if ( xToMove <= 0.5 && yToMove <= 0.5 )
            {
                movingDone = true;
                timer.stop();
            }
            else
            {
                if ( yToMove > 0.5 )
                {
                    ball.y += yMove;
//                System.out.println( "moving vertically" );
                }
                if ( xToMove > 0.5 )
                {
                    ball.x += xMove;
//                    System.out.println( "moving horiznatlly" );
                }
            }
        }

        repaint();
    }
}
