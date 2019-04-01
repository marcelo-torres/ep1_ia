package ep1.fitness;

import ep1.Cromossomo;

public interface Fitness {
    
    public double calcularFitness(Cromossomo individuo);
    
    public boolean individuoApto(Cromossomo individuo);
    
}
