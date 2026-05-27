import java.util.Random;

public class Prato {
    private final int nSerie;
    private final Sujeira sujeira;
    private static int contadorSerie = 1;

    public Prato() {
        this.nSerie = contadorSerie++;
        int sorteio = new Random().nextInt(100) + 1;
        if (sorteio <= 30) {
            this.sujeira = Sujeira.BAIXO;
        } else if (sorteio <= 90) {
            this.sujeira = Sujeira.MEDIO;
            
        } else {
            this.sujeira = Sujeira.ENGORDURADO; 
        }
    }

    public int getNSerie() {
        return nSerie;
    }

    public Sujeira getSujeira() {
        return sujeira;
    }
}