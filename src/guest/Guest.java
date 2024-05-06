package guest;

import hotel.Room;

import java.util.List;
import java.util.Random;

public class Guest implements Runnable {
    private final String name;
    private final List<Room> rooms;
    private int attempts = 0; // Contador de tentativas de alugar um quarto
    private final Random random = new Random();

    public Guest(String name, List<Room> rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(5000)); // Simulate arrival time
            System.out.println(name + " is looking for a room.");
            while (attempts < 2) { // Limite de 2 tentativas
                Room availableRoom = findAvailableRoom();
                if (availableRoom != null) {
                    //synchronized (availableRoom) {
                        int remainingCapacity = availableRoom.getCapacity();
                        while (remainingCapacity > 0) {
                            int guestsToCheckIn = Math.min(remainingCapacity, 4); // Check in up to 4 guests or remaining capacity
                            availableRoom.checkIn(guestsToCheckIn); // Check in guests
                            System.out.println(name + " checked into room " + availableRoom.getNumber() + " with " + guestsToCheckIn + " guests");
                            remainingCapacity -= guestsToCheckIn;
                            if (remainingCapacity == 0) break; // If room is fully occupied, break
                        }

                        // Aqui vai ser a lógica de estadia.
                        // Decidir aleatoriamente se o hóspede sairá para passear ou não

                        Thread.sleep(random.nextInt(10000)); // Simula um tempo de espera para sair para passear

                        // Decide se ele vai passear, ou apenas fazer check-out
                        if (random.nextBoolean()) {
                        sairParaPassear(availableRoom); // Chamada do método para simular o passeio
                        } else {
                            System.out.println(name + " decidiu não sair para passear.");
                            availableRoom.checkOut();
                        }

                        return; // Se conseguiu alugar um quarto, retorna
                    //}
                } else {
                    System.out.println(name + " is waiting for a room.");
                    Thread.sleep(random.nextInt(10000)); // Simulate waiting time
                    attempts++; // Incrementa o contador de tentativas
                    if (attempts == 2) {
                        System.out.println(name + " couldn't rent a room after 2 attempts. Leaving a complaint and going away.");
                        return; // Se não conseguiu alugar um quarto após 2 tentativas, deixa uma reclamação e vai embora
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Room findAvailableRoom() {
        synchronized (rooms) {
            for (Room room : rooms) {
                synchronized (room) {
                    if (!room.isOccupied() && room.isKeyAvailable() && !room.isCleaningInProgress()) {
                        return room;
                    }
                }
            }
            return null;
        }
    }

    // Método para simular o hóspede saindo para passear
    private void sairParaPassear(Room room) throws InterruptedException {
        System.out.println(name + " has going to a walk.");

        room.leaveKeyAtReception(); // Deixa a chave na recepção
        room.cleanRoom();

        Thread.sleep(random.nextInt(10000)); // Simula um tempo de espera para passeio
        // Espera até a camareira notificar que pode voltar ao quarto
        synchronized (room) {
            while (room.isCleaningInProgress()) {
                room.wait();
            }

                room.takeKeyFromReception(); // Pega a chave de volta
                System.out.println(name + " came back to the room.");

        }
        Thread.sleep(random.nextInt(10000)); // Simula um tempo de estadia para check-out
        room.checkOut(); // Hóspede faz checkout

        //}
    }
}

