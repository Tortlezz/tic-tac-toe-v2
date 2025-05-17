import java.util.*;

public class Board {
    private char[][] gameBoard;
    private final int sideLength;
    private int turnNum = 0;
    private final int winBy;

    public Board(int sides, int consec) {
        sideLength = sides;
        gameBoard = new char[sideLength][sideLength];
        winBy = consec;

        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                gameBoard[i][j] = '-';
            }
        }

        printBoard();
    }

    public void play() {
        Scanner sc = new Scanner(System.in);
        while (turnNum < sideLength * sideLength) {
            int row;
            int col;

            //Takes in input and checks validity
            while (true) {
                System.out.println("Enter Row and Column (row, col): ");
                row = sc.nextInt() - 1;
                col = sc.nextInt() - 1;

                if (row >= sideLength || col >= sideLength || row < 0 || col < 0) {
                    System.out.println("INVALID INPUT");
                }
                else if (gameBoard[row][col] != '-') {
                    System.out.println("ALREADY PLAYED");
                }
                else break;
            }

            //updates board
            if (turnNum % 2 == 0) {
                gameBoard[row][col] = 'O';
                printBoard();
                turnNum++;
            }
            else {
                gameBoard[row][col] = 'X';
                printBoard();
                turnNum++;
            }

            //checks win
            if (checkWin(row, col)) {//will use turnNum to determine victor
                if (turnNum % 2 == 1) {
                    System.out.println("O WINS!");
                    break;
                }
                else {
                    System.out.println("X WINS!");
                    break;
                }
            }
        }

        if (turnNum == sideLength * sideLength) {
            System.out.println("DRAW!");
        }
    }

    public boolean checkWin(int row, int col) { //GOATed CHECKER ICPC LEVELS OF EFFICIENCY
        char player = gameBoard[row][col];

        int[][] vectors = {
                {0, 1},   // Horz check
                {1, 0},   // Vert check
                {1, 1},   // -Diag check
                {1, -1}   // +Diag check
        };

        for (int[] dir : vectors) {
            int count = 1;

            count += checkDir(row, col, dir[0], dir[1], player);

            count += checkDir(row, col, -dir[0], -dir[1], player);

            if (count >= winBy) {
                return true;
            }
        }

        return false;

    }

    private int checkDir(int row, int col, int dRow, int dCol, char player) {
        int count = 0;
        int r = row + dRow;
        int c = col + dCol;

        while (r >= 0 && r < sideLength && c >= 0 && c < sideLength && gameBoard[r][c] == player) {
            count++;
            r += dRow;
            c += dCol;
        }

        return count;
    }

    public void printBoard() {
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                System.out.print("| " + gameBoard[i][j] + " ");
            }
            System.out.println("|");
        }
    }
}
