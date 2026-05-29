import java.util.Random;
import java.util.logging.Logger;

public class Enxugador implements Runnable { 

    private static final Logger logger = Logger.getLogger(Enxugador.class.getName());

    private final Escorredor escorredor;
    private boolean finalizado = false;

    public Enxugador(Escorredor escorredor) {
        this.escorredor = escorredor;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    @Override
    public void run() {
        while (!finalizado) {
            try {
                Prato prato = escorredor.retirarPrato();
                logger.fine("Enxugador pegou o prato #" + prato.getNSerie() + " do escorredor.");
                long tempoSecagem = new Random().nextInt(8) + 3; 
                // Thread.sleep(tempoSecagem);
                logger.fine("Enxugador terminou de secar o prato #" + prato.getNSerie() + 
                            " em " + tempoSecagem + "ms.");
            } catch (InterruptedException e) {
                logger.fine("Enxugador foi interrompido e está parando o trabalho.");
                Thread.currentThread().interrupt();
                break;
            }
        }
        logger.fine("Enxugador encerrou seu turno de trabalho.");
    }
}