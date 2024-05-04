public class Guest extends Thread {
    private static long walkTime = 5000;
    private static long cleaningWaitTime = 1000;

    protected Room room;
    protected boolean isOutside;
    protected int numberOfWalksTaken;

    public Guest() {
        this.isOutside = false;
        this.numberOfWalksTaken = 0;
    }

    @Override
    public void run() {
        // Enquanto a thread não for interrompida
        while (!Thread.interrupted()) {
            // Tenta dar um passeio.
            try {
                takeAWalk();
                // Enquanto o quarto estiver sendo limpo a thread dorme por 1 segundo
                // pois os guests não podem entrar no quarto.
                while (room.isBeingCleaned()) {
                    Thread.sleep(cleaningWaitTime);
                }
                // Se eles não estiverem mais fora o número de caminhadas é incrementado.
                if (!isOutside) {
                    numberOfWalksTaken++;
                }
            } catch (InterruptedException e) {
                System.out.println("Failed to execute Guest thread action " + e.getMessage());
                break;
            }
        }
    }

    // Método responsável pelo guest dar um passeio
    public void takeAWalk() {
        try {
            isOutside = true;
            Thread.sleep(walkTime);
            isOutside = false;
        } catch (InterruptedException e) {
            System.out.println("Failed to execute Guest thread action " + e.getMessage());
        }
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
