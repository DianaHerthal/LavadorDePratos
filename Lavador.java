import java.util.logging.Logger;

public class Lavador implements Runnable { 
    private static final Logger logger = Logger.getLogger(Lavador.class.getName());

    private final Escorredor escorredor;
    private final PratosSujosFactory fabricaPratosSujos;
    private boolean finalizado = false; 

    public Lavador(Escorredor escorredor, PratosSujosFactory fabrica) {
        this.escorredor = escorredor;
        this.fabricaPratosSujos = fabrica;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    @Override
    public void run() {
        while (!finalizado) {
            try {
                Prato prato = fabricaPratosSujos.criarPrato();
                logger.fine("Lavador pegou o prato #" + prato.getNSerie() + 
                            " (Sujeira: " + prato.getSujeira() + ")");
                long tempoLavagem = 0;
                switch (prato.getSujeira()) {
                    case BAIXO:
                        tempoLavagem = 3; 
                        break;
                    case MEDIO:
                        tempoLavagem = 5; 
                        break;
                    case ENGORDURADO:
                        tempoLavagem = 10; 
                        break;
                }
                Thread.sleep(tempoLavagem); 
                logger.fine("Lavador terminou de lavar o prato #" + prato.getNSerie());
                escorredor.colocarPrato(prato);
                logger.fine("Lavador colocou o prato #" + prato.getNSerie() + " no escorredor.");
            } catch (InterruptedException e) {
                logger.fine("Lavador foi interrompido e está parando o trabalho.");
                Thread.currentThread().interrupt(); 
                break; 
            }
        }
        logger.fine("Lavador encerrou seu turno de trabalho.");
    }
}