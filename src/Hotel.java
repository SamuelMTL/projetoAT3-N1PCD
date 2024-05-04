import java.util.Arrays;

public class Hotel {
    public static Hotel sharedHotel = new Hotel();

    private Guest[] guests;
    private Receptionist[] receptionists;

    private Hotel() {
        guests = new Guest[50];
        receptionists = new Receptionist[5];
    }

    public void vacateRoom(Room room) {
        // Criar lógica para encontrar a recepcionista pra fazer o checkout
        Arrays.stream(guests)
                .filter(guest -> guest.getRoom() == room)
                .forEach(guest -> receptionists[0].checkGuestOut(guest));
    }
}
