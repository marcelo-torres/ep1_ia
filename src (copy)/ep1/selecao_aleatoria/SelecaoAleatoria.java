package ep1.selecao_aleatoria;

import ep1.Cromossomo;
import java.util.Collection;

public interface SelecaoAleatoria {
    
    public Cromossomo selecionarIndividuo(Collection<Cromossomo> populacao);
    
}
