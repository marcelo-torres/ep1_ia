package ep1;

import java.util.Collection;
import java.util.Iterator;

public class Cromossomo {
    
    private final boolean [] GENES;
    
    private int quantidadeDeVezesSelecionado;
    
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
    
    
    @Override
    public String toString() {
        String s = "[";
        
        for(int i = this.GENES.length - 1; i >= 0; i--) {
            boolean gene = this.GENES[i];
            if(gene) {
                s += '1';
            } else {
                s += '0';
            }
        }
        
        return s + "]";
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
    
    
    public void incrementarVezesSelecionado() {
        this.quantidadeDeVezesSelecionado++;
    }
    
    public int vezesSelecionado() {
        return this.quantidadeDeVezesSelecionado;
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
    
    private double calcularValorReal(int inicio, int fim) {
        
        int valorInteiro = this.converterParaInteiro(inicio, fim);
        double valorReal = (valorInteiro * 0.00978) - 5;
        
        return valorReal;
    }
    
    public double obterValorInteiroDoPrimeiro() {
        return this.calcularValorReal(0, (this.GENES.length/2) - 1);
    }
    
    public double obterValorInteiroDoSegundo() {
        return this.calcularValorReal(this.GENES.length/2, (this.GENES.length) - 1);
    }
}
