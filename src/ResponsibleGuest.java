public class ResponsibleGuest extends Guest {
    private static int numberOfWalksUntilCheckout = 3;

    private Guest[] dependentGuests;

    public ResponsibleGuest() {
        this.dependentGuests = new Guest[0];
    }

    @Override
    public void run() {
        super.run();
        if (isOutside) {
            room.setStatus(RoomStatus.NOT_IN_USE);
        } else {
            if (numberOfWalksTaken >= numberOfWalksUntilCheckout) {
                checkOut();
            } else {
                room.setStatus(RoomStatus.IN_USE);
            }
        }
    }

    private void checkOut() {
        room.setStatus(RoomStatus.VACANT);
        this.room = null;
        interrupt();
        for (Guest guest : dependentGuests) {
            guest.interrupt();
        }
    }

    public Room getRoom() {
        return room;
    }

    public void setDependentGuests(Guest[] dependentGuests) {
        this.dependentGuests = dependentGuests;
    }

    public void checkInToRoom(Room room) {
        this.room = room;
        room.setStatus(RoomStatus.IN_USE);
        start();
        for (Guest guest : dependentGuests) {
            guest.start();
        }
    }
}
