import java.util.InputMismatchException;
import java.util.Scanner;

public class Battleship {
    private char[][] board;
    private String name;
    private int shipLength;
    private boolean isAvailable;

    public Battleship() {
        System.out.println("Creating Battleship game board.");
        board = createBoard();
    }

    public Battleship(String shipName, int size, boolean exists) {
        System.out.println("Creating game ship: " + shipName + " of length " + size + ".");
        name = shipName;
        shipLength = size;
        isAvailable = exists;
    }

    public char[][] createBoard() {
        System.out.println("Creating Base Board.");
        board = new char[20][20];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = '~';
            }
        }
        return board;
    }

    public void placeShips(Battleship[] ships, Scanner input) {
        int i = 0;
        while (i < ships.length) {
            System.out.println("These are all of the created ships to place:");
            System.out.println("'0': " + ships[0].name + "\t'1': " + ships[1].name + "\t'2': " + ships[2].name +
                    "\t'3': " + ships[3].name);

            int pick = 0; int x = 0; int y = 0; int z = 0;
            try {
                System.out.println("Choose a ship to place.");
                pick = input.nextInt();
                System.out.println("Enter x coordinate for " + ships[pick].name + ".");
                x = input.nextInt();
                System.out.println("Enter y coordinate for " + ships[pick].name + ".");
                y = input.nextInt();
                System.out.println("Enter O for horizontal, 1 for vertical.");
                z = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.next();
                continue;
            }
            if (inputsAreValid(ships,pick,x,y,z)) {
                System.out.println("Input was valid. Placing: " + ships[pick].name);
                for (int j = 0; j < ships[pick].shipLength; j++) {
                    if (z == 0) {
                        board[x][y + j] = 'S';
                    }else if (z == 1) {
                        board[x + j][y] = 'S';
                    }
                }
                //printBoard();
                ships[pick].isAvailable = false;
                i++;
            }
        }
    }

    public boolean inputsAreValid(Battleship[] ships, int pick, int x, int y, int z) {
        //checks initial inputs
        if (x < 0 || x >= board.length) {
            System.out.println("Invalid index (x) input.");
            return false;
        } else if (y < 0 || y >= board.length) {
            System.out.println("Invalid column (y) input.");
            return false;
        } else if (z < 0 || z > 1) {
            System.out.println("Invalid input for direction.");
            return false;
        }
        //check for pick
        if (pick < 0 || pick > ships.length) {
            System.out.println("This was not an available option for ship.");
            return false;
        } else if (ships[pick].isAvailable == false){
            System.out.println("This ship has already been placed.");
            return false;
        }
        //checks if boat fits, if there is already a ship on board position
        if(z==0) {
            if (x + ships[pick].shipLength > board.length) {
                System.out.println("Ship length will be out of bounds of board index.");
                return false;
            }
            for (int i = 0; i < ships[pick].shipLength; i++) {
                if (board[x+i][y] != '~') {
                    System.out.println("You already have a ship placed here.");
                    return false;
                }
            }
        } else {
            if (y + ships[pick].shipLength > board.length) {
                System.out.println("Ship length will be out of bounds of board column.");
                return false;
            }
            for (int i = 0; i < ships[pick].shipLength; i++){
                if (board[x][y+i] != '~') {
                    System.out.println("You already have a ship placed here.");
                    return false;
                }
            }
        }
        return true;
    }
    public void printBoard() {
        System.out.println("Printing Current Board.");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public void fireShot(int shots, Scanner input) {
        //int numShots = 1;
        int x, y;
        while(shots>0) {
            try {
                System.out.println("Enter x coordinate for fire.");
                x = input.nextInt();
                System.out.println("Enter y coordinate for fire.");
                y = input.nextInt();
                if (board[x][y] == 'S') {
                    System.out.println("Nice! You hit a ship!");
                    board[x][y] = 'X';
                } else if (board[x][y] == '~') {
                    System.out.println("You missed!");
                    board[x][y] = '?';
                }
                shots--;
                printBoard();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for fire. Please enter a digit.");
                input.next();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid input for fire. Please enter a number between 0-19");
                input.next();
            }
        }
    }

    public static void main(String[] args) {
        Battleship gameInstance = new Battleship();
        gameInstance.printBoard();

        Battleship shipD = new Battleship("Destroyer", 8, true);
        Battleship shipB = new Battleship("Battleship", 2, true);
        Battleship shipC1 = new Battleship("Cruiser #1", 4, true);
        Battleship shipC2 = new Battleship("Cruiser #2", 4, true);
        Battleship[] allShips = {shipD,shipB,shipC1,shipC2};

        Scanner input = new Scanner(System.in);

        gameInstance.placeShips(allShips, input);
        gameInstance.printBoard();
        int numShots = 1;
        gameInstance.fireShot(numShots, input);
    }




}


