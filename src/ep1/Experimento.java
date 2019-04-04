package ep1;

import ep1.algoritmo.AlgoritmoGenetico;
import ep1.algoritmo.AlgoritmoGenetico.Retorno;
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
        double chanceDeCruzamento = 1;
        double chanceDeMutacao = 0.1;
        int limiteDeGeracoes = 100;
        
        
        Fitness fitness = new Rastrigins(precisao);
        SelecaoAleatoria selecaoAleatoria = new MiniTorneio(tamanhoDoTorneio, fitness);
        Cruzamento cruzamento = new CruzamentoDeDoisPontos();
        //Cruzamento cruzamento = new CruzamentoDePontoSimples();
        Mutacao mutacao = new MutacaoMultipla(porcentagemMaximaDeAlteracaoDoDNA);
        

        int[] tamanhosDaPopulacao = {100, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500};
        int[] minimosLocais = new int[tamanhosDaPopulacao.length];
        int[] quantidadeDeGeracoes = new int[tamanhosDaPopulacao.length];
        
        int quantidadeDeTestes = 1;
        
        for(int t = 0; t < tamanhosDaPopulacao.length; t++) {
            
            String nome = (char)('A' + t) + "" + (char)('A' + t) + "" + (char)('A' + t) + "";
            
            AlgoritmoGenetico algoritmo = new AlgoritmoGenetico(
                                                            chanceDeCruzamento,
                                                            chanceDeMutacao,
                                                            limiteDeGeracoes,
                                                            fitness,
                                                            selecaoAleatoria,
                                                            cruzamento,
                                                            mutacao,
                                                            new EscritorDeExperimento(nome)
                                                            );
            
            
            int tamanho = tamanhosDaPopulacao[t];
            int quantidadeDeMinimosLocais = 0;
            
            System.out.print("Tamanho: " + tamanho + "\n[");
            
            for(int i = 0; i < quantidadeDeTestes; i++) {
                
                System.out.print(".");
                
                LinkedList<Cromossomo> populacao = gerarPopulacaoAleatoria(tamanho, 20);
                Retorno retorno = algoritmo.gerarIndividuoApto(populacao);
                Cromossomo individuo = retorno.individuo;

                if(!(Math.abs(fitness.calcularFitness(individuo)) < precisao)) {
                    quantidadeDeMinimosLocais++;
                }
                
                quantidadeDeGeracoes[t] += retorno.quantidadeDeGeracoes;
            }
            
            System.out.println("]");
            
            minimosLocais[t] = quantidadeDeMinimosLocais;
            
            System.out.println("Minimos locais: " + quantidadeDeMinimosLocais + " => " + (double)(quantidadeDeMinimosLocais) / quantidadeDeTestes + "\n");
        }
        
        System.out.println("---- Sumario Geral ----");
        System.out.println("precisao = " + precisao + "\n" +
                           "tamanhoDoTorneio = " + tamanhoDoTorneio + "\n" +
                           "porcentagemMaximaDeAlteracaoDoDNA = " + porcentagemMaximaDeAlteracaoDoDNA + "\n" +
                           "chanceDeCruzamento = " + chanceDeCruzamento + "\n" +
                           "chanceDeMutacao = " + chanceDeMutacao + "\n" +
                           "limiteDeGeracoes = " + limiteDeGeracoes + "\n");
        for(int i = 0; i < tamanhosDaPopulacao.length; i++) {
            int tamanho = tamanhosDaPopulacao[i];
            System.out.println("Tamanho: " + tamanho);
            
            double p = (double) minimosLocais[i] / quantidadeDeTestes;
            double m = (double) quantidadeDeGeracoes[i] / quantidadeDeTestes;
            System.out.println("\t% de erro: " + p);
            System.out.println("\tMedia de geracoes: " + m + "\n");
        }
    }
    
    
}
