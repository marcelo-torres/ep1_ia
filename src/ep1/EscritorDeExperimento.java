package ep1;

import ep1.fitness.Rastrigins;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.LinkedList;

public class EscritorDeExperimento implements Closeable {
    
    private final NumberFormat FORMATADOR = new DecimalFormat("000");
    private final String PREFIXO = "simulacao_";
    private final String regexCodigo = "[A-Z]{3}";
    
    private static final String caminho = "/home/marcelo/Desktop/dadosDeExperimentos/";
    
    private FileWriter escritor;
    
    
    @Override
    public void close() throws IOException {
        if(escritor != null) {
            escritor.close();
        }
    }
    
    private LinkedList<String> obterNomeDeArquivosEm(String caminho) {
    
        LinkedList<String> lista = new LinkedList<>();
        
        File diretorio = new File(caminho);
            
        for(File arquivo: diretorio.listFiles()) {
            if(arquivo.isFile()) {
                lista.add(arquivo.getName());
            }
        }
        
        Collections.sort(lista);
        
        return lista;
    }
    
    private int proximoNumero(LinkedList<String> listaDeNomesCriados) {
        
        int maiorNumero = 0;
        
        for(String arquivo : listaDeNomesCriados) {
            if(arquivo.length() < 17) {
                continue;
            }
            char primeiroDigito = arquivo.charAt(14);
            char segundoDigito = arquivo.charAt(15);
            char terceiroDigito = arquivo.charAt(16);
            
            int numero = Integer.valueOf(primeiroDigito + "" + segundoDigito + terceiroDigito);
            
            if(numero > maiorNumero) {
                maiorNumero = numero;
            }
        }
        
        return (maiorNumero + 1);
    }
    
    private String gerarNomeParaOExperimento(String codigoDoExperimento) {
    
        if(!codigoDoExperimento.matches(this.regexCodigo)) {
            throw new IllegalArgumentException("O codigo do experimento deve estar no formato: " + this.regexCodigo);
        }
        
        LinkedList<String> listaDeNomesCriados = this.obterNomeDeArquivosEm(this.caminho);
        int proximoNumero = this.proximoNumero(listaDeNomesCriados);
        
        String nome = this.PREFIXO
                      + codigoDoExperimento 
                      + "_" + this.FORMATADOR.format(proximoNumero)
                      + ".csv";
        
        return nome;
    }
    
    public void iniciarEscrita(String codigoDoExperimento) {
    
        String nomeDoArquivo = this.gerarNomeParaOExperimento(codigoDoExperimento);
        
        System.out.println(nomeDoArquivo);
        File arquivo = new File(this.caminho + nomeDoArquivo);
        
        
        try {
            this.escritor = new FileWriter(arquivo, false);
        } catch(IOException ioe) {
            throw new RuntimeException("Erro ao criar escritor do arquivo: " + ioe);
        }
        
    } 
    
    public void escrever(String s) {
        try {
            this.escritor.write(s);
        } catch(IOException ioe) {
            throw new RuntimeException("Nao foi possivel escrever no arquivo: " + ioe.getMessage());
        }
    }
    
    public static void main(String[] args) {
        //new EscritorDeExperimento("");
        
        /*String s = "ccc_00000";
        
        System.out.println(s.matches("[a-zA-Z_0-9]+_[0-9]{5}"));
        
        
        NumberFormat formatter;
        String number;
         
        // 0 --> a digit or 0 if no digit present
        formatter = new DecimalFormat("00000");
        number = formatter.format(-1234.567);
        System.out.println("Number 1: " + number);*/
        
        EscritorDeExperimento a = new EscritorDeExperimento();
        a.iniciarEscrita("AAA");

        
        try {
            a.escrever("aaa");
            a.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        
        boolean T = true;
        boolean F = false;
        boolean[] dna = {T, T, T, T, T, T, T, T, T, T, 
                         F, F, F, F, F, F, F, F, F, F};
        
        Cromossomo c = new Cromossomo(dna);
        
        dna = new boolean[]{T, T, F, F, F, F, F, F, F, F, 
                            F, T, F, F, F, F, F, F, F, F};
        
        System.out.println(c.obterValorInteiroDoPrimeiro() + ", "
                            + c.obterValorInteiroDoSegundo() + " = " 
                            + new Rastrigins(0.001).calcularFitness(c));
    }

}
