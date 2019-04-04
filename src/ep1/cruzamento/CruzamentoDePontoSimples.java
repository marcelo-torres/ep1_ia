package ep1.cruzamento;

import ep1.Cromossomo;
import java.util.Random;

public class CruzamentoDePontoSimples implements Cruzamento {

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
        
        int meio = progenitorPrimeiro.quantidadeDeGenes() / 2;
        
        pontoPrimeiro = aleatorio.nextInt(meio);
        pontoSegundo = aleatorio.nextInt(meio) + meio;
        
        // Criacao do novo DNA
        
        boolean[] DNA = new boolean[progenitorPrimeiro.quantidadeDeGenes()];
        
        int locus = 0;
        for(int i = 0; i < pontoPrimeiro; i++, locus++) {
            DNA[locus] = progenitorPrimeiro.obterGeneDe(i);
        }
        for(int i = pontoPrimeiro; i < meio; i++, locus++) {
            DNA[locus] = progenitorSegundo.obterGeneDe(i);
        }
        for(int i = meio; i < pontoSegundo; i++, locus++) {
            DNA[locus] = progenitorPrimeiro.obterGeneDe(i);
        }
        for(int i = pontoSegundo; i < progenitorPrimeiro.quantidadeDeGenes(); i++, locus++) {
            DNA[locus] = progenitorSegundo.obterGeneDe(i);
        }
        
        return new Cromossomo(DNA);
    
    }
}
