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
        while (!Thread.interrupted()) {
            try {
                takeAWalk();
                while (room.isBeingCleaned()) {
                    Thread.sleep(cleaningWaitTime);
                }
                if (!isOutside) {
                    numberOfWalksTaken++;
                }
            } catch (InterruptedException e) {
                System.out.println("Failed to execute Guest thread action " + e.getMessage());
                break;
            }
        }
    }

    public void takeAWalk() {
        try {
            isOutside = true;
            Thread.sleep(walkTime);
            isOutside = false;
        } catch (InterruptedException e) {
            System.out.println("Failed to execute Guest thread action " + e.getMessage());
        }
    }
}
