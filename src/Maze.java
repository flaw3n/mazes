import sun.audio.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * this program creates mazes
 * there are two mods
 * first is game mode where you have to solve multiple mazes by yourself, mazes become more complicated
 * second is custom mode where you can choose maze size and solve it using one of 3 available methods
 * there is a nice user interface
 */

public class Maze extends JPanel implements Runnable, KeyListener {
    boolean created = false;     //set to true when maze was created in createMaze()
    int height;                  //frame's height
    int width;                   //frame's width
    double perfectPathLength;    //shortest path length of this maze;is needed for statistics for custom mode and game mode
    int playerPathLength;        //number of moves made by player; is needed for statistics for custom mode and game mode
    int row;                     //number of rows of any cells in maze
    int col;                     //number of columns of any cells in maze
    int[][] maze;
    static int wallDig = 0;
    static int clearDig = 1;
    static int backDig = 2;
    static int currentDig = 3;
    static int pathDig = 4;
    // The value of maze[i][j]
    // is one of the constants wallDig, clearDig, backDig,
    // currentDig or pathDig.
    // Value can also be negative, temporarily,
    //  inside createMaze().
    static Color[] colour = new Color[5];   //list of colours that each cell in maze have
    int[][] fakeMaze;                       //copy of maze; is needed to find perfectPathLength
    static Solution mySolution = new Solution(); //contains info about current player's position

    public static void main(String[] args) throws InterruptedException, IOException {
        OptionsChanges newRowsAndCols = new OptionsChanges();
        JFrame frame = new JFrame("Maze");
        MainMenu myMainMenu = new MainMenu(frame, new Sounds());
        if (myMainMenu.mainMode == 1) {
            goToPlay(frame, newRowsAndCols, myMainMenu);
        }
        if (myMainMenu.mainMode == 2) {
            frame.dispose();
            System.exit(0);
        }
        if (myMainMenu.mainMode == 3) {
            goToOptions(frame, newRowsAndCols);
        }
    }

    public static void goToMainMenu(OptionsChanges newRowsAndCols) throws InterruptedException, IOException {
        JFrame frame = new JFrame("Maze");
        MainMenu myMainMenu = new MainMenu(frame, new Sounds());
        if (myMainMenu.mainMode == 1) {
            goToPlay(frame, newRowsAndCols, myMainMenu);
        }
        if (myMainMenu.mainMode == 2) {
            frame.dispose();
            System.exit(0);
        }
        if (myMainMenu.mainMode == 3) {
            goToOptions(frame, newRowsAndCols);
        }
    }

    public static void goToOptions(JFrame frame, OptionsChanges newRowsAndCols) throws IOException, InterruptedException {
        OptionsMenu menu5 = new OptionsMenu(frame, new Sounds(), newRowsAndCols);
        if (menu5.optionsMode == 3) {
            frame.dispose();
            goToMainMenu(newRowsAndCols);
        }
    }

    public static void goToPlay(JFrame frame, OptionsChanges newRowsAndCols, MainMenu myMainMenu) throws IOException, InterruptedException {
        PlayMenu PlayerManu = new PlayMenu(frame, new Sounds());
        if (PlayerManu.gameMode == 1) {
            Maze myMaze = new Maze(frame, newRowsAndCols.row, newRowsAndCols.col, myMainMenu);
            CustomMenu menu1 = new CustomMenu(frame, myMaze, new Sounds());
            AddSolveByMyselfLabels menu2 = new AddSolveByMyselfLabels();
            menu2.CheckSolveByMyselfLabels(myMaze, frame, menu1);

            myMaze.Solve(menu1.wayToSolveMode, mySolution, myMaze);
            if (menu1.wayToSolveMode == 1) {
                StatisticsMenu menu3 = new StatisticsMenu(frame, menu2.timer, myMaze, new Sounds());
                if (menu3.statMode == 1) {
                    frame.dispose();
                    goToMainMenu(newRowsAndCols);
                }
            } else {
                BackToMenu menu4 = new BackToMenu(frame, new Sounds());
                if (menu4.backMode == 1) {
                    frame.dispose();
                    goToMainMenu(newRowsAndCols);
                }
            }
        }
        if (PlayerManu.gameMode == 2) {
            Maze mazer[] = new Maze[200];
            AddSolveByMyselfLabels leb = new AddSolveByMyselfLabels();
            for (int i = 7; i < 200; i = i + 2) {
                Thread.sleep(500);
                mazer[i] = new Maze(frame, i, i, myMainMenu);
                leb.prep(frame, mazer[i]);
                leb.doSomething(frame, mazer[i]);
                leb.showLevel(frame);
                mazer[i].SolveMyself(mySolution, mazer[i]);
                new AfterGameMenu(frame, leb.timer, mazer[i], new Sounds());
                myMainMenu.checkForNewLevel = true;
                frame.getContentPane().removeAll();
                frame.repaint();
                frame.dispose();
                leb.level++;
            }
        }
        if (PlayerManu.gameMode == 3) {
            frame.dispose();
            goToMainMenu(newRowsAndCols);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }


    /**
     * Listener below changes player's position if certain keys are pressed
     * also every press makes sound "PIP!"
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            try {
                AudioPlayer.player.start((new Sounds()).audioStream1);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mySolution.currentPositionX = mySolution.currentPositionX + 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            try {
                AudioPlayer.player.start((new Sounds()).audioStream1);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mySolution.currentPositionX = mySolution.currentPositionX - 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            try {
                AudioPlayer.player.start((new Sounds()).audioStream1);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mySolution.currentPositionY = mySolution.currentPositionY - 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            try {
                AudioPlayer.player.start((new Sounds()).audioStream1);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mySolution.currentPositionY = mySolution.currentPositionY + 1;
        }
    }
    //sgdrehhg
    /**
     * I don't use situations when keys are released
     * so this is empty
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public Maze(JFrame frame, int rower, int coler, MainMenu g) throws IOException {
        row = rower;
        col = coler;
        colour[0] = new Color(68, 68, 68);
        colour[1] = new Color(249, 253, 255);
        colour[2] = new Color(68, 68, 68);
        colour[3] = new Color(255, 0, 0);
        colour[4] = new Color(255, 187, 179);
        setBackground(colour[backDig]);
        new Thread(this).start();
        if (g.checkForNewLevel == false)
            frame.addKeyListener(this);
    }

    void updateWorld() {
        width = getWidth();
        height = (int) ((getHeight() * 0.9));
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateWorld();
        updateMaze(g);
    }

    //this method repaints maze
    void updateMaze(Graphics g) {
        int sizeX = width / col;
        int sizeY = height / row;
        if (created) {
            for (int j = 0; j < col; j++) {
                for (int i = 0; i < row; i++) {
                    if (maze[i][j] < 0) {
                        g.setColor(colour[clearDig]);
                        g.fillRect((j * sizeX), (i * sizeY) + height / 10, sizeX, sizeY);
                    } else {
                        g.setColor(colour[maze[i][j]]);
                        g.fillRect((j * sizeX), (i * sizeY) + height / 10, sizeX, sizeY);
                    }
                }
            }
        }
    }

    /**
     * Creates maze by using Kruskal's algorithm
     * it creates separated rooms (cells)
     * then method examines walls that separate
     * cells randomly. If destroying wall
     * would create a loop then we leave it.
     * If not we destroy it.
     */
    void createMaze() throws InterruptedException {
        if (maze == null) {
            maze = new int[row][col];
        }
        if (maze != null) {
            for (int k = 0; k < row; k++) {
                maze[k][0] = clearDig;
            }
        }
        int i, j;
        int boxCounter = 0;
        int wallCounter = 0;
        int[] wallPositionY = new int[(row * col) / 2];
        int[] wallPositionX = new int[(row * col) / 2];
        for (i = 0; i < row; i++) {
            for (j = 0; j < col; j++) {
                maze[i][j] = wallDig;
            }
        }
        for (i = 1; i < row - 1; i = i + 2)
            for (j = 1; j < col - 1; j = j + 2) {
                boxCounter++;
                maze[i][j] = -boxCounter;
                if (j < col - 2) {
                    wallPositionY[wallCounter] = i;
                    wallPositionX[wallCounter] = j + 1;
                    wallCounter++;
                }
                if (i < row - 2) {
                    wallPositionY[wallCounter] = i + 1;
                    wallPositionX[wallCounter] = j;
                    wallCounter++;
                }
            }
        created = true;
        repaint();
        int r;
        for (i = wallCounter - 1; i > 0; i--) {
            r = (int) (Math.random() * i);
            destroyWall(wallPositionY[r], wallPositionX[r]);
            wallPositionY[r] = wallPositionY[i];
            wallPositionX[r] = wallPositionX[i];
        }
        for (i = 1; i < row - 1; i++) {
            for (j = 1; j < col - 1; j++) {
                if (maze[i][j] < 0) {
                    maze[i][j] = clearDig;
                }
            }
        }
    }

    void fillWall(int row, int col, int replace, int replaceWith) {
        if (maze[row][col] == replace) {
            maze[row][col] = replaceWith;
            fillWall(row + 1, col, replace, replaceWith);
            fillWall(row - 1, col, replace, replaceWith);
            fillWall(row, col + 1, replace, replaceWith);
            fillWall(row, col - 1, replace, replaceWith);
        }
    }

    void destroyWall(int row, int col) throws InterruptedException {
//        Tear down a wall, unless doing so will form a loop.  Tearing down a wall
//         joins two "rooms" into one "room".  (Rooms begin to look like corridors
//         as they grow.)  When a wall is torn down, the room codes on one side are
//         converted to match those on the other side, so all the cells in a room
//         have the same code.   Note that if the room codes on both sides of a
//         wall already have the same code, then tearing down that wall would
//         create a loop, so the wall is left in place.
        if (row % 2 == 1 && maze[row][col - 1] != maze[row][col + 1]) {
            fillWall(row, col - 1, maze[row][col - 1], maze[row][col + 1]);
            maze[row][col] = maze[row][col + 1];
            repaint();
        } else if (row % 2 == 0 && maze[row - 1][col] != maze[row + 1][col]) {
            fillWall(row - 1, col, maze[row - 1][col], maze[row + 1][col]);
            maze[row][col] = maze[row + 1][col];
            repaint();
        }
    }

    boolean solveByBFS(Solution solution) throws InterruptedException {
        int k = 0;
        int x = 1;
        int y = 1;
        solution.QueueX.add(x);
        solution.QueueY.add(y);
        while (x == 1) {
            if (((int) solution.QueueX.get(k) == row - 2) && ((int) solution.QueueY.get(k) == col - 2)) {
                return true;
            }
            if (maze[(int) solution.QueueX.get(k) - 1][(int) solution.QueueY.get(k)] == clearDig) {
                solution.QueueX.add((int) solution.QueueX.get(k) - 1);
                solution.QueueY.add(solution.QueueY.get(k));
                maze[(int) solution.QueueX.get(k) - 1][(int) solution.QueueY.get(k)] = pathDig;
            }

            if (maze[(int) solution.QueueX.get(k) + 1][(int) solution.QueueY.get(k)] == clearDig) {
                solution.QueueX.add((int) solution.QueueX.get(k) + 1);
                solution.QueueY.add(solution.QueueY.get(k));
                maze[(int) solution.QueueX.get(k) + 1][(int) solution.QueueY.get(k)] = pathDig;
            }

            if (maze[(int) solution.QueueX.get(k)][(int) solution.QueueY.get(k) - 1] == clearDig) {
                solution.QueueX.add(solution.QueueX.get(k));
                solution.QueueY.add((int) solution.QueueY.get(k) - 1);
                maze[(int) solution.QueueX.get(k)][(int) solution.QueueY.get(k) - 1] = pathDig;
            }
            if (maze[(int) solution.QueueX.get(k)][(int) solution.QueueY.get(k) + 1] == clearDig) {
                solution.QueueX.add(solution.QueueX.get(k));
                solution.QueueY.add((int) solution.QueueY.get(k) + 1);
                maze[(int) solution.QueueX.get(k)][(int) solution.QueueY.get(k) + 1] = pathDig;
            }
            repaint();
            if (k % 3 == 1) {
                Thread.sleep(1);
            }
            k = k + 1;
        }
        return false;
    }

    boolean solveByDFS(int thisRow, int thisCol, int[][] somemaze, int interval) throws InterruptedException {
//         solve the maze by continuing current path from position
//        Return true if a solution is found.  The maze is
//         considered to be solved if the path reaches the lower right cell.
        if (somemaze[thisRow][thisCol] == clearDig) {
            somemaze[thisRow][thisCol] = currentDig;      // add this cell to the path
            repaint();
            if (thisRow == row - 2 && thisCol == col - 2) {
                return true;  // path has reached goal
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
            }
            if (solveByDFS(thisRow - 1, thisCol, somemaze, interval) || solveByDFS(thisRow + 1, thisCol, somemaze, interval) || solveByDFS(thisRow, thisCol - 1, somemaze, interval) || solveByDFS(thisRow, thisCol + 1, somemaze, interval) == true) {
                return true;                // try to solve maze by extending path
            }                               // in each possible direction
            somemaze[thisRow][thisCol] = pathDig;   // mark cell as having been visited
            repaint();
            Thread.sleep(interval);
        }
        return false;
    }

    void solveByMyself(Solution solution, Maze thisR) {
        if (thisR.maze[solution.currentPositionY][solution.currentPositionX] == clearDig) {
            thisR.maze[solution.previousPositionY][solution.previousPositionX] = pathDig;
            thisR.maze[solution.currentPositionY][solution.currentPositionX] = currentDig;
            solution.previousPositionX = solution.currentPositionX;
            solution.previousPositionY = solution.currentPositionY;
            playerPathLength++;
        }
        if (thisR.maze[solution.currentPositionY][solution.currentPositionX] == wallDig) {
            solution.currentPositionX = solution.previousPositionX;
            solution.currentPositionY = solution.previousPositionY;
        }
        if (thisR.maze[solution.currentPositionY][solution.currentPositionX] == pathDig) {
            playerPathLength++;
            thisR.maze[solution.currentPositionY][solution.currentPositionX] = currentDig;
            thisR.maze[solution.previousPositionY][solution.previousPositionX] = pathDig;
            solution.previousPositionX = solution.currentPositionX;
            solution.previousPositionY = solution.currentPositionY;
        }
    }

    public boolean didYouWin(Maze g) {
        if (g.maze[row - 2][col - 2] == currentDig) {
            return true;
        } else return false;
    }

    public void SolveMyself(Solution soliter, Maze thisR) throws InterruptedException {
        soliter.currentPositionX = 1;
        soliter.currentPositionY = 1;
        soliter.previousPositionX = 1;
        soliter.previousPositionY = 1;
        findPathLength();
        playerPathLength = 0;
        while (thisR.maze[row - 2][col - 2] != currentDig) {
            solveByMyself(mySolution, thisR);
            repaint();
        }
    }

    public void Solve(int a, Solution soliter, Maze g) throws InterruptedException {
        if (a == 1) {
            SolveMyself(soliter, g);
        }
        if (a == 2) {
            try {
                solveByDFS(1, 1, g.maze, 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
        if (a == 3) {
            solveByBFS(mySolution);
            findPathLength();
            for (int j = 0; j < col; j++) {
                for (int i = 0; i < row; i++) {
                    if (fakeMaze[i][j] == currentDig) {
                        g.maze[i][j] = currentDig;
                    }
                }
            }
        }
    }

    public void findPathLength() throws InterruptedException {
        fakeMaze = new int[row][col];
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                fakeMaze[i][j] = maze[i][j];
            }
        }
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                if (fakeMaze[i][j] == pathDig) {
                    fakeMaze[i][j] = clearDig;
                }
            }
        }
        perfectPathLength = 0;
        solveByDFS(1, 1, fakeMaze, 0);
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                if (fakeMaze[i][j] == currentDig) {
                    perfectPathLength = perfectPathLength + 1;
                }
            }
        }
    }

    public void run() {
        try {
            createMaze();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
//TOTAL 480+79+96+34+80+83+50+32+43+11 18.03.2019 563total было 515 14.03.2019 988 всего 29.03.2019