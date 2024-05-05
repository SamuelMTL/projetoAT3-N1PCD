package receptionist;

import hotel.Room;

import java.util.List;
import java.util.Random;

public class Receptionist implements Runnable {
    private final String name;
    private final List<Room> rooms;
    private final Random random = new Random();

    public Receptionist(String name, List<Room> rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(5000)); // Simulate guests checking out and leaving keys
                synchronized (rooms) {
                    for (Room room : rooms) {
                        synchronized (room) {
                            if (room.isOccupied() && !room.isCleaningInProgress()) {
                                room.leaveKeyAtReception(); // Leave the key at the reception
                                break;
                            }
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
