public class Room {
    private RoomStatus status;
    private boolean isBeingCleaned;

    public Room() {
        this.status = RoomStatus.VACANT;
        this.isBeingCleaned = false;
    }

    /// Garante que operações assíncronas dependente do status acessem seu valor atualizado.
    // Exemplo: Caso o hóspede e a recepcionista acessem o valor do status ao mesmo tempo,
    // o synchronized garante a consistência do dado.
    public synchronized RoomStatus status() {
        return status;
    }

    public synchronized void setStatus(RoomStatus status) {
        boolean isRoomCurrentlyInUse = this.status == RoomStatus.IN_USE;
        boolean willGuestsLeaveTheRoom = status == RoomStatus.NOT_IN_USE || status == RoomStatus.VACANT;
        if (isRoomCurrentlyInUse && willGuestsLeaveTheRoom) {
            // Notify clean keeper to start cleaning the room
        }
        this.status = status;
    }

    public boolean isBeingCleaned() {
        return isBeingCleaned;
    }
}