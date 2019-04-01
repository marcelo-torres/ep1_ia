package ep1.algoritmo;

import ep1.Cromossomo;
import ep1.cruzamento.Cruzamento;
import ep1.fitness.Fitness;
import ep1.mutacao.Mutacao;
import ep1.selecao_aleatoria.SelecaoAleatoria;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class AlgoritmoGenetico {
    
    private final double CHANCE_DE_CRUZAMENTO;
    private final double CHANCE_DE_MUTACAO;
    private final int LIMITE_DE_GERACOES;
    
    
    private final Fitness FITNESS;
    private final SelecaoAleatoria SELECAO_ALEATORIA;
    private final Cruzamento CRUZAMENTO;
    private final Mutacao MUTACAO;
    
    public AlgoritmoGenetico(double chanceDeCruzamento,
                             double chanceDeMutacao,
                             int limiteDeGeracoes,
                             Fitness fitness,
                             SelecaoAleatoria selecaoAleatoria,
                             Cruzamento cruzamento,
                             Mutacao mutacao) {
        
        this.CHANCE_DE_CRUZAMENTO = chanceDeCruzamento;
        this.CHANCE_DE_MUTACAO = chanceDeMutacao;
        this.LIMITE_DE_GERACOES = limiteDeGeracoes;
        
        this.FITNESS = fitness;
        this.SELECAO_ALEATORIA = selecaoAleatoria;
        this.CRUZAMENTO = cruzamento;
        this.MUTACAO = mutacao;
    }
    
    public Cromossomo gerarIndividuoApto(Collection<Cromossomo> populacao) {
    
        Cromossomo individuoApto = null;
        int geracao = 0;
        int quantidadeDeMutacoes = 0;
        
        while(!this.FITNESS.individuoApto(individuoApto) && geracao < this.LIMITE_DE_GERACOES) {
            
            Collection<Cromossomo> populacaoNova = new ArrayList<>(populacao.size());
            
            while(populacaoNova.size() < populacao.size()) {
                
                Cromossomo filho;
                
                if(this.sortearChance(this.CHANCE_DE_CRUZAMENTO)) {
                    Cromossomo progenitorPrimeiro = this.SELECAO_ALEATORIA.selecionarIndividuo(populacao);
                    Cromossomo progenitorSegundo = this.SELECAO_ALEATORIA.selecionarIndividuo(populacao);

                    filho = this.CRUZAMENTO.cruzar(progenitorPrimeiro, progenitorSegundo);
                    if(this.sortearChance(this.CHANCE_DE_MUTACAO)) {
                        this.MUTACAO.mutar(filho);
                        quantidadeDeMutacoes++;
                    }
                } else {
                    filho = this.SELECAO_ALEATORIA.selecionarIndividuo(populacao);
                }
                
                populacaoNova.add(filho);
            }
            
            populacao = populacaoNova;
            geracao++;
            
            Cromossomo individuoMaisApto = this.procurarMaisApto(populacao);
            
            System.out.println(this.FITNESS.calcularFitness(individuoMaisApto));
            
            /*boolean individuoEhApto = this.FITNESS.individuoApto(individuoMaisApto);
            if(individuoEhApto) {
                individuoApto = individuoMaisApto;
            }*/
            if(individuoApto == null) {
                individuoApto = individuoMaisApto;
            } else {
                if(this.FITNESS.calcularFitness(individuoMaisApto) > this.FITNESS.calcularFitness(individuoApto)) {
                    individuoApto = individuoMaisApto;
                }
            }
        }
        
        System.out.println("Quantidade de geracoes: " + geracao + "\n"
                         + "Quantidade de mutacoes: " + quantidadeDeMutacoes);
        
        return individuoApto;
    }
    
    
    private boolean sortearChance(double probabilidade) {
        double pontoAleatorio = new Random().nextDouble();
        return (pontoAleatorio <= probabilidade);
    }
    
    private Cromossomo procurarMaisApto(Collection<Cromossomo> populacao) {
    
        Iterator<Cromossomo> iterador = populacao.iterator();
        Cromossomo individuoMaisApto = iterador.next();
        double melhorFitness = this.FITNESS.calcularFitness(individuoMaisApto);
        
        while(iterador.hasNext()) {
            Cromossomo individuo = iterador.next();
            double fitness = this.FITNESS.calcularFitness(individuo);
            
            if(fitness > melhorFitness) {
                individuoMaisApto = individuo;
                melhorFitness = fitness;
            }
        }
        
        return individuoMaisApto;
    }
}
