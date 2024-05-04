import java.util.Arrays;

public class Hotel {
    public static Hotel sharedHotel = new Hotel();
    private Guest[] guests;
    private Receptionist[] receptionists;
    private Hotel() {
        guests = new Guest[50];
        receptionists = new Receptionist[5];
    }
}
