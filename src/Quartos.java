import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Quartos {
    private final int numero;
    private boolean ocupado;
    private boolean emLimpeza;
    private boolean chaveNaRecepcao;
    private final Lock lock = new ReentrantLock();
    private final Condition disponivelParaLimpeza = lock.newCondition();
    private final Condition disponivelParaHospede = lock.newCondition();

    public Quartos(int numero) {
        this.numero = numero;
        this.ocupado = false;
        this.emLimpeza = false;
        this.chaveNaRecepcao = true;
    }

    public void checkIn(int numeroDeHospedes) throws InterruptedException {
        lock.lock();
        try {
            
            while (ocupado || emLimpeza) {
                disponivelParaHospede.await();
            }
            ocupado = true;
            chaveNaRecepcao = false; 
            System.out.println("Quarto " + numero + " ocupado por " + numeroDeHospedes + " hóspedes.");
        } finally {
            lock.unlock();
        }
    }

    public void checkOut() throws InterruptedException {
        lock.lock();
        try {
            ocupado = false;
            chaveNaRecepcao = true;
            
            disponivelParaLimpeza.signal();
            System.out.println("Quarto " + numero + " desocupado e esperando limpeza.");
        } finally {
            lock.unlock();
        }
    }

    public void limpar() throws InterruptedException {
        lock.lock();
        try {
            while (ocupado || emLimpeza) {
                disponivelParaLimpeza.await();
            }
            emLimpeza = true;
            System.out.println("Limpeza iniciada no quarto " + numero);
            
            Thread.sleep(1000);
            
            emLimpeza = false;
            System.out.println("Limpeza concluída no quarto " + numero);
            
            disponivelParaHospede.signal();
        } finally {
            lock.unlock();
        }
    }

    public boolean isDisponivel() {
        return !ocupado && !emLimpeza && chaveNaRecepcao;
    }

}