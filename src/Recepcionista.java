import java.util.Queue;
import java.util.LinkedList;

public class Recepcionista extends Thread {
    private String nome;
    private Queue<Hospede> filaEspera;
    private Hotel hotel;

    public Recepcionista(String nome, Hotel hotel) {
        this.nome = nome;
        this.hotel = hotel;
        this.filaEspera = new LinkedList<>();
    }

    public void run() {
        System.out.println("Recepcionista " + nome + " iniciou seu turno.");
        while (!hotel.isFechado()) {
            if (!filaEspera.isEmpty()) {
                atenderHospede();
            }
        }
        System.out.println("Recepcionista " + nome + " encerrou seu turno.");
    }

    public synchronized void receberHospede(Hospede hospede) {
        filaEspera.add(hospede);
        System.out.println("Recepcionista " + nome + " recebeu o hospede " + hospede.getNome() + ".");
        notifyAll(); // Notifica as threads que estão esperando na fila
    }

    private synchronized void atenderHospede() {
        Hospede hospede = filaEspera.poll();
        System.out.println("Recepcionista " + nome + " está atendendo o hospede " + hospede.getNome() + ".");
        boolean sucesso = hotel.alocarQuarto(hospede);
        if (!sucesso) {
            System.out.println("Hospede " + hospede.getNome() + " não pôde ser acomodado. Deixando uma reclamação.");
            hotel.deixarReclamacao(hospede);
        }
    }
}
