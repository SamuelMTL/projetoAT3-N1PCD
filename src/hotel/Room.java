package hotel;

import java.util.Random;

public class Room {
    private final int number;
    private boolean occupied;
    private final int capacity; // Capacidade máxima do quarto
    private int guestsCount; // Número de hóspedes atualmente no quarto
    private boolean keyAvailable; // Indica se a chave do quarto está disponível
    private boolean cleaningInProgress; // Indica se a limpeza está em andamento

    public Room(int number){
        this.number = number;
        this.occupied = false;
        this.capacity = 4; // Capacidade padrão
        this.guestsCount = 0;
        this.keyAvailable = true; // Inicialmente a chave está disponível
        this.cleaningInProgress = false; // Inicialmente a limpeza não está em andamento
    }

    public synchronized void checkIn(int numOfGuests) throws InterruptedException {
        while (occupied || guestsCount + numOfGuests > capacity || !keyAvailable || cleaningInProgress) {
            wait();
        }
        occupied = true;
        guestsCount += numOfGuests;
        keyAvailable = false; // A chave é retirada quando o quarto é ocupado
    }

    public synchronized void checkOut(int numOfGuests) {
        occupied = false;
        guestsCount -= numOfGuests;
        keyAvailable = true; // A chave é devolvida quando o quarto é desocupado
        cleaningInProgress = true; // Indica que a limpeza está em andamento
        notifyAll(); // Notifica camareiras que a limpeza pode começar
    }

    public synchronized void leaveKeyAtReception() {
        keyAvailable = true; // Deixa a chave na recepção
        System.out.println("Room " + number + " key left at the reception.");
        notifyAll(); // Notifica threads que estão esperando por essa chave
    }

    public synchronized void cleanRoom() throws InterruptedException {
        System.out.println("Maid " + Thread.currentThread().getName() + " cleaning room " + number);
        Thread.sleep(new Random().nextInt(5000)); // Simulate cleaning time
        System.out.println("Maid " + Thread.currentThread().getName() + " finished cleaning room " + number);
        cleaningInProgress = false; // Indica que a limpeza foi concluída
        notifyAll(); // Notifica os hóspedes que podem retornar ao quarto
    }

    public boolean isOccupied() {
        return occupied;
    }

    public int getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isKeyAvailable() {
        return keyAvailable;
    }

    public boolean isCleaningInProgress() {
        return cleaningInProgress;
    }
}
