package com.company;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class HotelManagementSystem {
    static class Hotel {
        String name;
        String address;
        int roomnum;
        int period;
        String roomtype;
    }

    public static void main(String[] args) {
        // Ensure the file exists
        try {
            File file = new File("Hotel.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int ch = -1;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\n---------------------------------------------------------------------------------");
            System.out.println("1. Allocate New Room");
            System.out.println("2. Display Allocated Rooms");
            System.out.println("3. Delete Room Allocation");
            System.out.println("0. Exit");
            System.out.println("\n---------------------------------------------------------------------------------");
            System.out.print("Enter your choice: ");
            
            try {
                ch = scanner.nextInt();
                switch (ch) {
                    case 1:
                        Customer.create();
                        break;
                    case 2:
                        Customer.display();
                        break;
                    case 3:
                        Customer.delete();
                        break;
                    case 0:
                        System.out.println("<<<<Thank you For Visiting Us>>>>");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        } while (ch != 0);
    }
}

class Customer {
    static class Hotel {
        String name;
        String address;
        int roomnum;
        int period;
        String roomtype;
    }

    public static void create() {
        Hotel h = new Hotel();
        int roomno;
        int lower1 = 101, upper1 = 105, lower2 = 106, upper2 = 110, lower3 = 201, upper3 = 210, lower4 = 301, upper4 = 310;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Name: ");
        h.name = scanner.nextLine();
        System.out.println("Enter address (Only city): ");
        h.address = scanner.nextLine();
        System.out.println("Enter Period for how many Days: ");
        h.period = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over
        int a, b;
        System.out.println("Which Type of Rooms you would like prefer\n1.Normal(Ac/Non-Ac)\n2.Deluxe\n3.Suite");
        a = scanner.nextInt();

        if (a == 1) {
            System.out.print("Do you prefer 1.Ac 2.Non-Ac: ");
            b = scanner.nextInt();
            if (b == 1) {
                do {
                    roomno = (int) (Math.random() * (upper1 - lower1 + 1)) + lower1;
                    h.roomtype = "Ac";
                    h.roomnum = roomno;
                } while (!isRoomAvailable(roomno));
                System.out.println("The Ac room allocated is: " + h.roomnum);
            } else if (b == 2) {
                do {
                    roomno = (int) (Math.random() * (upper2 - lower2 + 1)) + lower2;
                    h.roomtype = "Non-Ac";
                    h.roomnum = roomno;
                } while (!isRoomAvailable(roomno));
                System.out.println("The Non-Ac room allocated is: " + h.roomnum);
            } else {
                System.out.println("Invalid option, please select again.");
                return;
            }
        } else if (a == 2) {
            do {
                roomno = (int) (Math.random() * (upper3 - lower3 + 1)) + lower3;
                h.roomtype = "Deluxe";
                h.roomnum = roomno;
            } while (!isRoomAvailable(roomno));
            System.out.println("The Deluxe room allocated is: " + h.roomnum);
        } else if (a == 3) {
            do {
                roomno = (int) (Math.random() * (upper4 - lower4 + 1)) + lower4;
                h.roomtype = "Suite";
                h.roomnum = roomno;
            } while (!isRoomAvailable(roomno));
            System.out.println("The Suite room allocated is: " + h.roomnum);
        } else {
            System.out.println("Invalid option, please select again.");
            return;
        }

        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter("Hotel.txt", true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println(h.name + "," + h.address + "," + h.period + "," + h.roomnum + "," + h.roomtype);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
                if (bw != null) bw.close();
                if (fw != null) fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isRoomAvailable(int roomno) {
        BufferedReader br = null;
        try {
            File file = new File("Hotel.txt");
            if (!file.exists()) {
                return true;  // If file doesn't exist, assume room is available
            }
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[3]) == roomno) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void display() {
        BufferedReader br = null;
        boolean hasEntries = false; // Flag to check if there are any entries
        try {
            br = new BufferedReader(new FileReader("Hotel.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println("\n-----------------------------------------------------------------------------------------------------------");
                System.out.println("Name: " + parts[0] + " | Address: " + parts[1] + " | Stay Duration: " + parts[2] + " days | Room Number: " + parts[3] + " | Room Type: " + parts[4]);
            }
        } catch (IOException e) {
            System.out.println("No rooms allocated yet");
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!hasEntries) {
            // Print the message if no entries were found
            System.out.println("No rooms allocated yet.");
        }
    }
   

    public static void delete() {
        Scanner scanner = new Scanner(System.in);
        List<String> entries = new ArrayList<>();
        String line;

        System.out.println("Enter Room Number to delete: ");
        int roomToDelete = scanner.nextInt();

        // Read all entries and store in a list except the one to delete
        try (BufferedReader br = new BufferedReader(new FileReader("Hotel.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[3]) != roomToDelete) {
                    entries.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the remaining entries back to the file
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Hotel.txt")))) {
            for (String entry : entries) {
                out.println(entry);
            }
            System.out.println("Room allocation deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
