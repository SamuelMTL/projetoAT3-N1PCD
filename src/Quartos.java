import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Quartos {
    private final int numero;
    private boolean ocupado;
    private boolean emLimpeza;
    private final Lock lock = new ReentrantLock();
    private final Condition disponivelParaLimpeza = lock.newCondition();
    private final Condition disponivelParaHospede = lock.newCondition();

    public Quartos(int numero) {
        this.numero = numero;
        this.ocupado = false;
        this.emLimpeza = false;
    }

    public void checkIn(int numeroDeHospedes) throws InterruptedException {
        lock.lock();
        try {
            
            while (ocupado || emLimpeza) {
                disponivelParaHospede.await();
            }
            ocupado = true;
            System.out.println("Quarto " + numero + " ocupado por " + numeroDeHospedes + " hóspedes.");
        } finally {
            lock.unlock();
        }
    }

    public void checkOut() throws InterruptedException {
        lock.lock();
        try {
            ocupado = false;
            
            disponivelParaLimpeza.signal();
            System.out.println("Quarto " + numero + " desocupado e esperando limpeza.");
        } finally {
            lock.unlock();
        }
    }

}