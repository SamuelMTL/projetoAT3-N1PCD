package hotel;

import guest.Guest;
import maid.Maid;
import receptionist.Receptionist;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private final int numRooms = 10;
    private final int numGuests = 50;
    private final int numMaids = 10;
    private final int numReceptionists = 5;

    private final List<Room> rooms = new ArrayList<>();
    private final List<Thread> guests = new ArrayList<>();
    private final List<Thread> maids = new ArrayList<>();
    private final List<Thread> receptionists = new ArrayList<>();

    public Hotel() {
        // Initialize rooms
        for (int i = 0; i < numRooms; i++) {
            rooms.add(new Room(i));
        }

        // Initialize guests
        for (int i = 0; i < numGuests; i++) {
            guests.add(new Thread(new Guest("Guest " + i, rooms)));
        }

        // Initialize maids
        for (int i = 0; i < numMaids; i++) {
            maids.add(new Thread(new Maid("Maid " + i, rooms)));
        }

        // Initialize receptionists
        for (int i = 0; i < numReceptionists; i++) {
            receptionists.add(new Thread(new Receptionist("Receptionist " + i, rooms)));
        }
    }

    public void startSimulation() {
        // Start all threads
        for (Thread guest : guests) {
            guest.start();
        }

        for (Thread maid : maids) {
            maid.start();
        }

        for (Thread receptionist : receptionists) {
            receptionist.start();
        }
    }
}