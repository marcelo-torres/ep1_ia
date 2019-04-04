package ep1.selecao_aleatoria;

import ep1.Cromossomo;
import ep1.fitness.Fitness;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class MiniTorneio implements SelecaoAleatoria {

    private final int K;

    private final Fitness FITNESS;
    
    public MiniTorneio(int k, Fitness fitness) {
        
        if(fitness == null) {
            throw new IllegalArgumentException("Selecione um fitness");
        }
        
        this.K = k;
        this.FITNESS = fitness;
    }
    
    @Override
    public Cromossomo selecionarIndividuo(Collection<Cromossomo> populacao) {
 
        int[] listaDePosicoes = this.sortearNumeros(this.K, 0, populacao.size() - 1);

        double fitnessMelhor = 0;
        Cromossomo melhorIndividuo = null;

        Iterator<Cromossomo> iterador = populacao.iterator();
        int i = 0;

        int j = 0;
        while(i < listaDePosicoes.length) {
            while(j < listaDePosicoes[i] && iterador.hasNext()) {
                iterador.next();
                j++;
            }
            j++;
            i++;

            if(iterador.hasNext()) {
                Cromossomo individuo = iterador.next();

                if(melhorIndividuo == null || this.FITNESS.calcularFitness(individuo) > fitnessMelhor) {
                    melhorIndividuo = individuo;
                    fitnessMelhor = this.FITNESS.calcularFitness(melhorIndividuo);
                }
            }
        }
            
        return melhorIndividuo;
    }

    
    private int[] sortearNumeros(int quantidade, int inicioIntervalo, int fimIntervalo) {
    
        Random gerador = new Random();
        int[] numeros = new int[quantidade];
        
        for(int i = 0; i < quantidade; i++) {
            int numeroSorteado = 0;
            boolean numeroRepetido = false;
            do {
                numeroSorteado = gerador.nextInt(fimIntervalo + 1) + inicioIntervalo;

                numeroRepetido = false;
                for(int j = 0; j < i; j++) {
                    if(numeros[j] == numeroSorteado) {
                        numeroRepetido = true;
                        break;
                    }
                }
            } while(numeroRepetido);
            
            numeros[i] = numeroSorteado;
        }
        
        return numeros;
    }
    
}
