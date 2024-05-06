package hotel;

import guest.Guest;
import maid.Maid;
import receptionist.Receptionist;

import java.util.ArrayList;
import java.util.List;

class HotelSimulation {
    public static void main(String[] args) {

        final int numRooms = 10;
        final int numGuests = 50;
        final int numMaids = 10;
        final int numReceptionists = 5;

        List<Room> rooms = new ArrayList<>();
        List<Thread> guests = new ArrayList<>();
        List<Thread> maids = new ArrayList<>();
        List<Thread> receptionists = new ArrayList<>();

        // Create rooms
        for (int i = 1; i <= 10; i++) {
            rooms.add(new Room(i));
        }

        System.out.println("Rooms created.");

        // Create guests
        for (int i = 1; i <= 50; i++) {
            guests.add(new Thread(new Guest("Guest " + i, rooms)));
        }

        System.out.println("Guests created.");

        // Create maids
        for (int i = 1; i <= 10; i++) {
            maids.add(new Thread(new Maid("Maid " + i, rooms)));
        }

        System.out.println("Maids created.");

        // Create receptionists
        for (int i = 1; i <= 5; i++) {
            receptionists.add(new Thread(new Receptionist("Receptionist " + i, rooms)));
        }

        System.out.println("Recepcionists created.");

        // Start threads
        guests.forEach(Thread::start);
        maids.forEach(Thread::start);
        receptionists.forEach(Thread::start);
    }
}
