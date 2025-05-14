package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static int income;

    static void printCinema(char[][] room) {
        System.out.println("Cinema:");
        // Nagłówek kolumn
        int rows = room.length;
        int cols = room[0].length;
        System.out.print(" ");
        for (int i = 1; i <= cols; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        // Wiersze z numeracją i miejscami
        for (int i = 0; i < rows; i++) {
            System.out.print((i + 1));
            for (int j = 0; j < cols; j++) {
                System.out.print(" " + room[i][j]);
            }
            System.out.println();
        }
    }

    static int calcIncome(int rows, int cols) {
        int seats = rows * cols;
        if (seats <= 60) {
            return seats * 10;
        } else {
            int firstHalf = rows / 2 * cols;
            int secondHalf = seats - firstHalf;
            return firstHalf * 10 + secondHalf * 8;
        }
    }

    static int checkPrice(char[][] room, int row, int col) {
        int rows = room.length;
        int cols = room[0].length;

        int seats = rows * cols;
        if (seats <= 60) {
            return 10;
        } else {
            int firstHalf = rows / 2;
            if (row <= firstHalf) {
                return 10;
            } else {
                return 8;
            }
        }

    }

    static char[][] reserve(char[][] room, int row, int col) {
        room[row - 1][col - 1] = 'B';
        return room;
    }

    static void printMenu() {
        System.out.println("""
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit""");
    }

    static void printStatistics(char[][] room) {

        int rows = room.length;
        int cols = room[0].length;

        int seats = rows * cols;

        int seatsReserved = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (room[i][j] == 'B') {
                    seatsReserved++;
                }
            }
        }

        float percentage = (float) seatsReserved / seats * 100;


        System.out.printf("Number of purchased tickets: %d\n", seatsReserved);
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.printf("Current income: $%d\n", income);
        System.out.printf("Total income: $%d\n", calcIncome(rows, cols));

    }

    static char[][] buyTicket(char[][] room) {

        int selectedRow;
        int selectedCol;

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter a row number:");
            selectedRow = sc.nextInt();
            System.out.println("Enter a seat number in that row:");
            selectedCol = sc.nextInt();

            if (selectedRow - 1 < 0 || selectedRow - 1 >= room.length || selectedCol - 1 < 0 || selectedCol - 1 >= room[0].length) {
                System.out.println("Wrong input!");
            } else if (room[selectedRow - 1][selectedCol - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");
            } else {
                break;
            }
            System.out.println();
        }

        int price = checkPrice(room, selectedRow, selectedCol);
        income += price;

        System.out.println("Ticket price: $" + price);

        System.out.println();

        return reserve(room, selectedRow, selectedCol);
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int cols = sc.nextInt();

        char[][] room = new char[rows][cols];
        for (char[] row : room)
            Arrays.fill(row, 'S');

        int choice;

        while (true) {
            System.out.println();
            printMenu();
            choice = sc.nextInt();
            System.out.println();
            switch (choice) {
                case 1:
                    printCinema(room);
                    break;
                case 2:
                    room = buyTicket(room);
                    break;
                case 3:
                    printStatistics(room);
                    break;
                case 0:
                    return;
            }

        }
    }
}