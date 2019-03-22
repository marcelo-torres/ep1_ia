package ep1;

import java.util.Collection;
import java.util.Iterator;

public class Cromossomo {
    
    private final boolean [] GENES;
    
    public Cromossomo(int tamanho) {
        this.GENES = new boolean[tamanho];
    }
    
    public Cromossomo(boolean[] genes) {
        this.GENES = java.util.Arrays.copyOf(genes, genes.length);
    }
    
    public Cromossomo(Collection<Boolean> genes) {
        int tamanho = genes.size();
        this.GENES = new boolean[tamanho];
        
        Iterator<Boolean> iterador = genes.iterator();
        for(int i = 0; i < tamanho; i++) {
            boolean gene = iterador.next();
            this.GENES[i] = gene;
        }
    }
    
    
    public boolean obterGeneDe(int locus) {
        return this.GENES[locus];
    }
    
    public void definirGeneEm(boolean gene, int locus) {
        this.GENES[locus] = gene;
    }
    
    
    public int quantidadeDeGenes() {
        return this.GENES.length;
    }
    
    
    private int converterParaInteiro(int inicio, int fim) {
        
        int soma = 0;
        int p = 1;
        
        for(int i = fim; i >= inicio; i--) {
            boolean gene = this.GENES[i];
            if(gene) {
                soma += p;
            }
            p *= 2;
        }
        
        return soma; 
    }
    
    public int obterValorInteiroDoPrimeiro() {
        return this.converterParaInteiro(0, (this.GENES.length/2) - 1);
    }
    
    public int obterValorInteiroDoSegundo() {
        return this.converterParaInteiro(this.GENES.length/2, (this.GENES.length) - 1);
    }
}
