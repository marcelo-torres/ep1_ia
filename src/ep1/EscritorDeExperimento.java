package ep1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.JFileChooser;

public class EscritorDeExperimento {
    
    private final NumberFormat FORMATADOR = new DecimalFormat("000");
    
    private final String PREFIXO = "simulacao_";
    
    private final String regex = "[a-zA-Z_0-9]+" + "_" + "[0-9]+";
    
    private static final String caminho = "/home/marcelo/Desktop/dadosDeExperimentos/";
    
    private FileWriter escritor;
    
    
    public EscritorDeExperimento(String codigoDoExperimento) {
        
        try {
            File raiz = new File(caminho);
            
            for(File f: raiz.listFiles()) {
                if(f.isFile()) {
                    System.out.println(f.getName());
                }
            }
            
        } catch(Error e) {
            
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
            char primeiroDigito = arquivo.charAt(13);
            char segundoDigito = arquivo.charAt(14);
            char terceiroDigito = arquivo.charAt(15);
            
            int numero = Integer.valueOf(primeiroDigito + "" + segundoDigito + terceiroDigito);
            
            if(numero > maiorNumero) {
                maiorNumero = numero;
            }
        }
        
        return (maiorNumero + 1);
    }
    
    private String gerarNomeParaOExperimento(String codigoDoExperimento) {
    
        LinkedList<String> listaDeNomesCriados = this.obterNomeDeArquivosEm(this.caminho);
        int proximoNumero = this.proximoNumero(listaDeNomesCriados);
        
        String nome = this.PREFIXO
                      + codigoDoExperimento 
                      + "_" + this.FORMATADOR.format(proximoNumero);
        
        return nome;
    }
    
    public void iniciarEscrita() {
    
        //this.escritor = new FileWriter();
        
    }
    
    public void salvarEscrita() {
        try {
            this.escritor.close();
        } catch(IOException ioe) {
            throw new RuntimeException("Nao foi possivel fechar o arquivo: " + ioe.getMessage());
        }
    }
    
    
    public static void main(String[] args) {
        //new EscritorDeExperimento("");
        
        String s = "ccc_00000";
        
        System.out.println(s.matches("[a-zA-Z_0-9]+_[0-9]{5}"));
        
        NumberFormat formatter;
        String number;
         
        // 0 --> a digit or 0 if no digit present
        formatter = new DecimalFormat("00000");
        number = formatter.format(-1234.567);
        System.out.println("Number 1: " + number);
    }
}
