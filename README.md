# Hotel-Management-
import java.io.*;
import java.util.Scanner;

class HotelManagement {
    static class Hotel {
        String name;
        String address;
        int roomnum;
        int period;
        String roomtype;
    }

    public static void main(String[] args) {
        int ch;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\n---------------------------------------------------------------------------------");
            System.out.println("1. Allocate New Room");
            System.out.println("2. Display Allocated Rooms");
            System.out.println("0. Exit");
            System.out.println("\n---------------------------------------------------------------------------------");
            System.out.print("Enter your choice: ");
            ch = scanner.nextInt();

            switch (ch) {
                case 1:
                    Customer.create();
                    break;
                case 2:
                    Customer.display();
                    break;
                case 0:
                    System.out.println("<<<<Thank you For Visiting Us>>>>");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
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
        scanner.nextLine();
        h.name = scanner.nextLine();
        System.out.println("Enter address (Only city): ");
        h.address = scanner.nextLine();
        System.out.println("Enter Period for how many Days: ");
        h.period = scanner.nextInt();
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

        try (FileWriter fw = new FileWriter("Hotel.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(h.name + "," + h.address + "," + h.period + "," + h.roomnum + "," + h.roomtype);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isRoomAvailable(int roomno) {
        try (BufferedReader br = new BufferedReader(new FileReader("Hotel.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[3]) == roomno) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void display() {
        try (BufferedReader br = new BufferedReader(new FileReader("Hotel.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println("\n-----------------------------------------------------------------------------------------------------------");
                System.out.println("Name: " + parts[0] + " | Address: " + parts[1] + " | Stay Duration: " + parts[2] + " hours | Room Number: " + parts[3] + " | Room Type: " + parts[4]);
            }
        } catch (IOException e) {
            System.out.println("No rooms allocated yet");
        }
    }
}
