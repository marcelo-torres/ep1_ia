package ep1.fitness;

import ep1.Cromossomo;

public interface Fitness {
    
    public int calcularFitness(Cromossomo individuo);
    
    public boolean individuoApto(Cromossomo individuo);
    
}
