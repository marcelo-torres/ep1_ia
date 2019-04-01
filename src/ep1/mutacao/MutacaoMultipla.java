package ep1.mutacao;

import ep1.Cromossomo;
import java.util.Random;

public class MutacaoMultipla implements Mutacao {

    private final double PORCENTAGEM_DE_MUTACAO;
    
    public MutacaoMultipla(double porcentagemDeMutacao) {
        if(porcentagemDeMutacao < 0 || porcentagemDeMutacao > 1) {
            throw new IllegalArgumentException("Porcentagem de mutacao deve estar entre 0 e 1");
        }
        this.PORCENTAGEM_DE_MUTACAO = porcentagemDeMutacao;
    }
    
    
    @Override
    public void mutar(Cromossomo individuo) {
        
        int quantidadeMaximaDeMutacoes = (int)Math.ceil(individuo.quantidadeDeGenes() * this.PORCENTAGEM_DE_MUTACAO);
        int quantidadeDeMutacoes = new Random().nextInt(quantidadeMaximaDeMutacoes + 1);
        
        int[] listaDePosicoes = this.listaDePosicoes(quantidadeDeMutacoes, individuo.quantidadeDeGenes());
        
        for(int locus : listaDePosicoes) {
            boolean novoGene = !individuo.obterGeneDe(locus);
            individuo.definirGeneEm(novoGene, locus);
        }
    }
    
    private int[] listaDePosicoes(int quantidade, int limiteSuperior) {
    
        Random gerador = new Random();
        int[] posicoes = new int[quantidade];
        
        for(int i = 0; i < quantidade; i++) {
            int posicaoSorteada = 0;
            boolean posicaoRepetida = false;
            do {
                posicaoSorteada = gerador.nextInt(limiteSuperior);

                posicaoRepetida = false;
                for(int j = 0; j < i; j++) {
                    if(posicoes[j] == posicaoSorteada) {
                        posicaoRepetida = true;
                        break;
                    }
                }
            } while(posicaoRepetida);
            
            posicoes[i] = posicaoSorteada;
        }
        
        return posicoes;
    }
}
