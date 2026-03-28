import java.io.*;
import java.util.*;

class Room {
    int roomId;
    String category;
    boolean isAvailable;

    Room(int roomId, String category) {
        this.roomId = roomId;
        this.category = category;
        this.isAvailable = true;
    }
}

class Booking {
    int bookingId;
    int roomId;
    String customerName;

    Booking(int bookingId, int roomId, String customerName) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.customerName = customerName;
    }
}

public class Main {

    static List<Room> rooms = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static int bookingCounter = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initialize rooms
        rooms.add(new Room(1, "Standard"));
        rooms.add(new Room(2, "Deluxe"));
        rooms.add(new Room(3, "Suite"));

        while (true) {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewRooms();
                    break;
                case 2:
                    bookRoom(sc);
                    break;
                case 3:
                    cancelBooking(sc);
                    break;
                case 4:
                    System.out.println("Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    static void viewRooms() {
        System.out.println("\nRoom Details:");
        for (Room r : rooms) {
            System.out.println("Room ID: " + r.roomId +
                    " | Category: " + r.category +
                    " | Available: " + (r.isAvailable ? "Yes" : "No"));
        }
    }

    static void bookRoom(Scanner sc) {
        sc.nextLine(); // clear buffer

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter Room ID to book: ");
        int roomId = sc.nextInt();

        for (Room r : rooms) {
            if (r.roomId == roomId && r.isAvailable) {

                // Payment Simulation
                System.out.println("Processing payment...");
                System.out.println("Payment successful!");

                r.isAvailable = false;

                Booking b = new Booking(bookingCounter++, roomId, name);
                bookings.add(b);

                saveBookingToFile(b);

                System.out.println("Booking successful! Your Booking ID: " + b.bookingId);
                return;
            }
        }
        System.out.println("Room not available!");
    }

    static void cancelBooking(Scanner sc) {
        System.out.print("Enter Booking ID to cancel: ");
        int id = sc.nextInt();

        Iterator<Booking> it = bookings.iterator();

        while (it.hasNext()) {
            Booking b = it.next();
            if (b.bookingId == id) {
                it.remove();

                for (Room r : rooms) {
                    if (r.roomId == b.roomId) {
                        r.isAvailable = true;
                    }
                }

                System.out.println("Booking cancelled successfully!");
                return;
            }
        }
        System.out.println("Booking not found!");
    }

    static void saveBookingToFile(Booking b) {
        try {
            FileWriter fw = new FileWriter("bookings.txt", true);
            fw.write("BookingID: " + b.bookingId +
                    ", RoomID: " + b.roomId +
                    ", Name: " + b.customerName + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving booking.");
        }
    }
}
