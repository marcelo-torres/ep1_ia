package ep1.algoritmo;

import ep1.Cromossomo;
import ep1.EscritorDeExperimento;
import ep1.cruzamento.Cruzamento;
import ep1.fitness.Fitness;
import ep1.mutacao.Mutacao;
import ep1.selecao_aleatoria.SelecaoAleatoria;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class AlgoritmoGenetico {
    
    public static class Retorno {
    
        public Cromossomo individuo;
        public int quantidadeDeGeracoes;
        
        public Retorno(Cromossomo individuo, int quantidadeDeGeracoes) {
            this.individuo = individuo;
            this.quantidadeDeGeracoes = quantidadeDeGeracoes;
        }
    }
    
    private final double CHANCE_DE_CRUZAMENTO;
    private final double CHANCE_DE_MUTACAO;
    private final int LIMITE_DE_GERACOES;
    
    
    private final Fitness FITNESS;
    private final SelecaoAleatoria SELECAO_ALEATORIA;
    private final Cruzamento CRUZAMENTO;
    private final Mutacao MUTACAO;
    
    
    private final EscritorDeExperimento ESCRITOR;
            
    
    public AlgoritmoGenetico(double chanceDeCruzamento,
                             double chanceDeMutacao,
                             int limiteDeGeracoes,
                             Fitness fitness,
                             SelecaoAleatoria selecaoAleatoria,
                             Cruzamento cruzamento,
                             Mutacao mutacao,
                             EscritorDeExperimento escritor) {
        
        this.CHANCE_DE_CRUZAMENTO = chanceDeCruzamento;
        this.CHANCE_DE_MUTACAO = chanceDeMutacao;
        this.LIMITE_DE_GERACOES = limiteDeGeracoes;
        
        this.FITNESS = fitness;
        this.SELECAO_ALEATORIA = selecaoAleatoria;
        this.CRUZAMENTO = cruzamento;
        this.MUTACAO = mutacao;
        
        this.ESCRITOR = escritor;
    }
    
    public Retorno gerarIndividuoApto(Collection<Cromossomo> populacao) {
    
        this.ESCRITOR.iniciarEscrita();
        this.ESCRITOR.escrever("Geracao,Media,Melhor,\n");
        
        Cromossomo individuoMaisApto =  null;
        int geracao = 0;
        int quantidadeDeMutacoes = 0;
        
        while(!this.FITNESS.individuoApto(individuoMaisApto) && geracao < this.LIMITE_DE_GERACOES) {
            
            Collection<Cromossomo> populacaoNova = new ArrayList<>(populacao.size());
            
            double somaDosFitness = 0;
            
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
            
                somaDosFitness += this.FITNESS.calcularFitness(filho);
                populacaoNova.add(filho);
            }
            
            populacao = populacaoNova;
            geracao++;
            
            Cromossomo individuoMaisAptoAtual = this.procurarMaisApto(populacao);
            
            /*boolean individuoEhApto = this.FITNESS.individuoApto(individuoMaisApto);
            if(individuoEhApto) {
                individuoApto = individuoMaisApto;
            }*/
            if(individuoMaisApto == null) {
                individuoMaisApto = individuoMaisAptoAtual;
            } else if(this.FITNESS.calcularFitness(individuoMaisAptoAtual) > this.FITNESS.calcularFitness(individuoMaisApto)) {
                individuoMaisApto = individuoMaisAptoAtual;
            }
            
            //System.out.println(geracao + " - Melhor fitness: " + this.FITNESS.calcularFitness(individuoMaisAptoAtual));
            double mediaDosFitness = somaDosFitness / populacao.size();
            this.ESCRITOR.escrever(geracao + ", " + mediaDosFitness + "," + this.FITNESS.calcularFitness(individuoMaisAptoAtual) + ",\n");
        }
        
        try {
            this.ESCRITOR.close();
        } catch(IOException ioe) {
            System.out.println("Erro - nao foi possivel fechar o arquivo: " + ioe.getMessage());
        }
        
        //System.out.println("Quantidade de geracoes: " + geracao + "\n"
        //                 + "Quantidade de mutacoes: " + quantidadeDeMutacoes);
        
        return new Retorno(individuoMaisApto, geracao);
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
