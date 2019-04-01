package ep1.fitness;

import ep1.Cromossomo;

public class Rastrigins implements Fitness {

    final double PRECISAO;
    
    public Rastrigins(double precisao) {
        this.PRECISAO = precisao;
    }
    
    
    @Override
    public double calcularFitness(Cromossomo individuo) {
        
        if(individuo == null) {
            return Double.NEGATIVE_INFINITY;
        }
        
        double x = individuo.obterValorInteiroDoPrimeiro();
        double y = individuo.obterValorInteiroDoSegundo();
        
        double rastrigins = 20 + (x * x) + (y * y)
                               - 10 * (Math.cos(2 * Math.PI * x)
                                       + Math.cos(2 * Math.PI * y));
        
        return -rastrigins;
    }

    @Override
    public boolean individuoApto(Cromossomo individuo) {
        
        if(individuo == null) {
            return false;
        }
        
        boolean aptidao = (Math.abs(this.calcularFitness(individuo)) < PRECISAO);
        
        return aptidao;
    }
    
}
