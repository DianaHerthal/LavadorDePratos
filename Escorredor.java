import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public class Escorredor {

    private static final Logger logger = Logger.getLogger(Escorredor.class.getName());

    private final Queue<Prato> fila; 
    private final long max; 

    public Escorredor(long max) {
        this.max = max;
        this.fila = new LinkedList<>(); 
    }

    public synchronized void colocarPrato(Prato prato) throws InterruptedException {
        while (fila.size() == max) {
            wait();
        }
        fila.add(prato);
        if (fila.size() > max || fila.size() < 0) {
            logger.severe("ERRO FATAL: Limites do escorredor violados!");
            System.exit(1); 
        }
        if (fila.size() == max) {
            logger.fine("Escorredor CHEIO! Quantidade de pratos: " + fila.size());
        }
        notifyAll();
    }

    public synchronized Prato retirarPrato() throws InterruptedException {
        while (fila.isEmpty()) {
            wait();
        }
        Prato prato = fila.poll();
        if (fila.size() > max || fila.size() < 0) {
            logger.severe("ERRO FATAL: Limites do escorredor violados!");
            System.exit(1);
        }
        if (fila.isEmpty()) {
            logger.fine("Escorredor VAZIO! Quantidade de pratos: " + fila.size());
        }
        notifyAll();
        return prato;
    }
}