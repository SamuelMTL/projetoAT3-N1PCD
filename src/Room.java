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
        // Estado velho
        boolean isRoomCurrentlyInUse = this.status == RoomStatus.IN_USE;
        // Estado novo que eu quero atualizar
        boolean willGuestsLeaveTheRoom = status == RoomStatus.NOT_IN_USE || status == RoomStatus.VACANT;
        // Verifica se o quarto está atualmente em uso e vai para o estado em que os hóspedes saem
        if (isRoomCurrentlyInUse && willGuestsLeaveTheRoom) {
            Hotel.sharedHotel.assignCleanKeeperToCleanRoom(this);
        }
        // Atualiza o valor
        this.status = status;
    }

    public boolean isBeingCleaned() {
        return isBeingCleaned;
    }
}