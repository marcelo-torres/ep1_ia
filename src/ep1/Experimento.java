package ep1;

import ep1.algoritmo.AlgoritmoGenetico;
import ep1.cruzamento.Cruzamento;
import ep1.cruzamento.CruzamentoDeDoisPontos;
import ep1.fitness.Fitness;
import ep1.fitness.Rastrigins;
import ep1.mutacao.Mutacao;
import ep1.mutacao.MutacaoMultipla;
import ep1.selecao_aleatoria.MiniTorneio;
import ep1.selecao_aleatoria.SelecaoAleatoria;
import java.util.LinkedList;
import java.util.Random;

public class Experimento {
    
    private static boolean[] gerarDNAaleatorio(int tamanhoDoDNA) {
    
        Random gerador = new Random();
        boolean[] DNA = new boolean[tamanhoDoDNA];
        
        for(int i = 0; i < tamanhoDoDNA; i++) {
            DNA[i] = gerador.nextBoolean();
        }
        
        return DNA;
    }
    
    public static LinkedList<Cromossomo> gerarPopulacaoAleatoria(int tamanho, int tamanhoDoDNA) {
    
        if(tamanho <= 0) {
            throw new IllegalArgumentException("O tamanho da populacao deve ser maior do que 0");
        }
        
        LinkedList<Cromossomo> populacao = new LinkedList<>();
        
        for(int i = 0; i < tamanho; i++) {
            boolean[] DNA = gerarDNAaleatorio(tamanhoDoDNA);
            Cromossomo individuo = new Cromossomo(DNA);
            populacao.add(individuo);
        }
        
        return populacao;
    }
    
    public static void main(String[] args) {
    
        // Parametros
        double precisao = 0.01;
        int tamanhoDoTorneio = 3;
        double porcentagemMaximaDeAlteracaoDoDNA = 0.25;
        double chanceDeCruzamento = 0.85;
        double chanceDeMutacao = 0.1;
        int limiteDeGeracoes = 500;
        
        
        Fitness fitness = new Rastrigins(precisao);
        SelecaoAleatoria selecaoAleatoria = new MiniTorneio(tamanhoDoTorneio, fitness);
        Cruzamento cruzamento = new CruzamentoDeDoisPontos();
        Mutacao mutacao = new MutacaoMultipla(porcentagemMaximaDeAlteracaoDoDNA);
        
        
        AlgoritmoGenetico algoritmo = new AlgoritmoGenetico(
                                                            chanceDeCruzamento,
                                                            chanceDeMutacao,
                                                            limiteDeGeracoes,
                                                            fitness,
                                                            selecaoAleatoria,
                                                            cruzamento,
                                                            mutacao
                                                            );
        
        LinkedList<Cromossomo> populacao = gerarPopulacaoAleatoria(1000, 20);
        
        Cromossomo individuo = algoritmo.gerarIndividuoApto(populacao);
        
        System.out.println(individuo + "(" + individuo.obterValorInteiroDoPrimeiro() + ", "
                + individuo.obterValorInteiroDoSegundo() + ") => " + fitness.calcularFitness(individuo));
        
        /*for(int i = 0; i < populacao.size(); i++) {
        
            Cromossomo selecionado = populacao.get(i);
            System.out.println(selecionado + " => " + fitness.calcularFitness(selecionado));
            
        }
        
        System.out.println("\n\n");
        
        for(int i = 0; i < 20; i++) {
        
            Cromossomo selecionado = selecaoAleatoria.selecionarIndividuo(populacao);
            System.out.println(selecionado + " => " + fitness.calcularFitness(selecionado));
            
        }*/
        
        
    }
    
    
}
