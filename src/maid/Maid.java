package maid;

import hotel.Room;

import java.util.List;
import java.util.Random;

public class Maid implements Runnable {
    private final String name;
    private final List<Room> rooms;
    private final Random random = new Random();

    public Maid(String name, List<Room> rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(10000)); // Simulate cleaning time
                synchronized (rooms) {
                    for (Room room : rooms) {
                        synchronized (room) {
                            if (room.isCleaningInProgress() && (!room.isOccupied() || (room.isOccupied() && room.isKeyAvailable()))) {
                                room.cleanRoom(); // Clean the room
                            }
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
