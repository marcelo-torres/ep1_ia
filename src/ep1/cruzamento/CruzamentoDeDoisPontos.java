package ep1.cruzamento;

import ep1.Cromossomo;
import java.util.Random;

public class CruzamentoDeDoisPontos implements Cruzamento {

    @Override
    public Cromossomo cruzar(Cromossomo progenitorPrimeiro, Cromossomo progenitorSegundo) {
        
        Random aleatorio = new Random();
        
        if(aleatorio.nextBoolean()) {
            Cromossomo auxiliar = progenitorPrimeiro;
            progenitorPrimeiro = progenitorSegundo;
            progenitorSegundo = auxiliar;
        }
        
        int pontoPrimeiro;
        int pontoSegundo;
        
        pontoPrimeiro = aleatorio.nextInt(progenitorPrimeiro.quantidadeDeGenes());
        pontoSegundo = aleatorio.nextInt(progenitorPrimeiro.quantidadeDeGenes() - pontoPrimeiro) + pontoPrimeiro;
        
        // Criacao do novo DNA
        
        boolean[] DNA = new boolean[progenitorPrimeiro.quantidadeDeGenes()];
        
        int locus = 0;
        
        for(int i = 0; i < pontoPrimeiro; i++, locus++) {
            DNA[locus] = progenitorPrimeiro.obterGeneDe(i);
        }
        
        for(int i = pontoPrimeiro; i < pontoSegundo; i++, locus++) {
            DNA[locus] = progenitorSegundo.obterGeneDe(i);
        }
        
        for(int i = pontoSegundo; i < progenitorPrimeiro.quantidadeDeGenes(); i++, locus++) {
            DNA[locus] = progenitorPrimeiro.obterGeneDe(i);
        }
        
        Cromossomo filho = new Cromossomo(DNA);
        
        return filho;
    }
    
    /*public static void main(String[] args) {
    
        // Teste
    
        boolean T = true;
        boolean F = false;
        
        boolean[] dna1 = {T,T,T,T,T,T,T,T,T,T};
        Cromossomo c1 = new Cromossomo(dna1);
        
        boolean[] dna2 = {F,F,F,F,F,F,F,F,F,F};
        Cromossomo c2 = new Cromossomo(dna2);
        
        for(int i = 0; i < 20; i++) {
            
            System.out.println(new CruzamentoDeDoisPontos().cruzar(c1, c2));
            
        }
        
    }*/
}
