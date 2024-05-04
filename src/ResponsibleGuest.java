public class ResponsibleGuest extends Guest {
    private static int numberOfWalksUntilCheckout = 3;

    @Override
    public void run() {
        super.run();
        if (isOutside) {
            room.setStatus(RoomStatus.NOT_IN_USE);
        } else {
            if (numberOfWalksTaken >= numberOfWalksUntilCheckout) {
                Hotel.sharedHotel.vacateRoom(room);
            } else {
                room.setStatus(RoomStatus.IN_USE);
            }
        }
    }
}
