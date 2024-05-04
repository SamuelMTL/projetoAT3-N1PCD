public class Receptionist extends Thread {
    public Receptionist() {}

    public void checkGuestIn(Guest guest) {
        // Atualizar isso para pegar um quarto vago e atualizar seu status para IN_USE
        Room room = new Room();
        guest.setRoom(room);
        guest.start();
    }

    public void checkGuestOut(Guest guest) {
        guest.interrupt();
        guest.setRoom(null);
    }
}
