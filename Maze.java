import java.util.*;
import java.io.*;
public class Maze{
    private char[][] maze;
    private boolean animate;//false by default
    private int[] movesR = {-1, 0, 1, 0};
    private int[] movesC = {0, -1, 0, 1};

    /*Constructor loads a maze text file, and sets animate to false by default.
      1. The file contains a rectangular ascii maze, made with the following 4 characters:
        '#' - Walls - locations that cannot be moved onto
        ' ' - Empty Space - locations that can be moved onto
        'E' - the location of the goal (exactly 1 per file)
        'S' - the location of the start(exactly 1 per file)
      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!
      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:
         throw a FileNotFoundException or IllegalStateException
    */
    public Maze(String filename) throws FileNotFoundException{
        //COMPLETE CONSTRUCTOR
        File text = new File(filename);
        Scanner input = new Scanner(text);
        ArrayList<String> lines = new ArrayList<String>();
        while (input.hasNextLine()) {
          lines.add(input.nextLine());
        }
        maze = new char[lines.size()][lines.get(0).length()]; //Creating array
        for (int i = 0; i < lines.size(); i++) {
          for (int j = 0; j < lines.get(i).length(); j++) {
            maze[i][j] = lines.get(i).charAt(j);
          }
        }
    }

    public String toString() {
      String ans = "";
      for (int i = 0; i < maze.length; i++) {
        for (int j = 0; j < maze[i].length; j++) {
          ans += maze[i][j];
        }
        ans += '\n';
      }
      return ans;
    }

    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }


    public void setAnimate(boolean b){
        animate = b;
    }


    public void clearTerminal(){
        //erase terminal, go to top left of screen.
        System.out.println("\033[2J\033[1;1H");
    }


    /*Wrapper Solve Function returns the helper function
      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.
    */
    public int solve(){
      //find the location of the S.
      //erase the S
      //and start solving at the location of the s.
      //return solve(???,???);
      for (int i = 0; i < maze.length; i++) {
        for (int j = 0; j < maze[i].length; j++) {
          if (maze[i][j] == 'S') {
            return solve(i,j,0);
          }
        }
      }
      return 0;
    }

    /*
      Recursive Solve function:
      A solved maze has a path marked with '@' from S to E.
      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.

      Postcondition:
        The S is replaced with '@' but the 'E' is not.
        All visited spots that were not part of the solution are changed to '.'
        All visited spots that are part of the solution are changed to '@'
    */
    private int solve(int row, int col, int total){ //you can add more parameters since this is private
        //automatic animation! You are welcome.
        if(animate){
            clearTerminal();
            System.out.println(this);
            wait(20);
        }
        //COMPLETE SOLVE
        if (maze[row][col] == 'E') {
          return total;
        } else {
          if (move(row,col)) {
            if (move(row - 1, col)) {
              return solve(row - 1, col, total + 1);
            } else if (move(row, col - 1)) {
              return solve(row, col - 1, total + 1);
            } else if (move(row + 1, col)) {
              return solve(row + 1, col, total + 1);
            } else if (move(row, col + 1)) {
              return solve(row, col + 1, total + 1);
            }
          }
        }
        return 0; //so it compiles
    }

    public boolean move(int row, int col) {
      if (maze[row][col] == ' ' || maze[row][col] == 'S') {
        maze[row][col] = '@';
        return true;
      } else if (maze[row][col] == 'E') {
        return true;
      } else {
        return false;
      }
    }

    public boolean remove(int row, int col) {
      if (maze[row][col] == '@') {
        maze[row][col] = ' ';
        return true;
      } else {
        return false;
      }
    }
}
