import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class App {

    private static final Logger logger = Logger.getLogger(App.class.getName());
    private static final long MAX = 500;


    private final Lavador lavador;
    private final Escorredor escorredor;
    private final Enxugador enxugador;
    private Thread threadLavador;
    private Thread threadEnxugador;

    public App() {
        this.escorredor = new Escorredor(MAX);
        PratosSujosFactory fabrica = new PratosSujosFactory();
        this.lavador = new Lavador(escorredor, fabrica);
        this.enxugador = new Enxugador(escorredor);
    }

    public void work() {
        threadLavador = new Thread(lavador, "Thread-Lavador");
        threadEnxugador = new Thread(enxugador, "Thread-Enxugador");

        logger.info("Iniciando o turno de lavagem de pratos...");
        
        threadLavador.start();
        threadEnxugador.start();
    }

    public void stop() {
        logger.info("Fim do expediente! Encerrando os trabalhos...");
        
        lavador.setFinalizado(true);
        if (threadLavador != null) {
            threadLavador.interrupt();
        }

        try {
            logger.fine("Aguardando o Enxugador terminar os pratos restantes...");
            escorredor.aguardarEsvaziar(); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("Escorredor vazio! Liberando o Enxugador...");
        enxugador.setFinalizado(true);
        if (threadEnxugador != null) {
            threadEnxugador.interrupt();
        }
    }

    public static void main(String[] args) {
        try (InputStream stream = App.class.getClassLoader().getResourceAsStream("log.properties")) {
            if (stream != null) {
                LogManager.getLogManager().readConfiguration(stream);
            } else {
                logger.warning("Aviso: Arquivo log.properties não encontrado. Verifique se ele está na pasta raiz do src.");
            }
        } catch (Exception e) {
            logger.severe("Erro ao carregar configuração de log: " + e.getMessage());
        }
        App app = new App();
        app.work();
        try {
            logger.info("O programa vai rodar por 20 segundos...");
            Thread.sleep(20000); 
        } catch (InterruptedException e) {
            logger.severe("A thread principal foi interrompida inesperadamente!");
            Thread.currentThread().interrupt();
        }
        app.stop();
    }
}