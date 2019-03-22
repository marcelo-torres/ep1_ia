package ep1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JFileChooser;

public class EscritorDeExperimento {
    
    private static final String caminho = "/home/marcelo/Desktop/dadosDeExperimentos/";
    
    private FileWriter escritor;
    
    
    public EscritorDeExperimento(String nomeDoExperimento) {
        
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
        
        return lista;
    }
    
    private String gerarNomeParaOExperimento(String nomeDoExperimento) {
    
        
        
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
        new EscritorDeExperimento("");
    }
}
