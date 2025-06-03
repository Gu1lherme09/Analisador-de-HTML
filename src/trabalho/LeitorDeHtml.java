
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import ListaEncadeada.ListaEncadeada;
import ListaEncadeada.NoLista;
import Lista05.PilhaLista;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Piva
 */
public class LeitorDeHtml {
    
    private PilhaLista TagInicio = new PilhaLista();
    private PilhaLista TagFinal = new PilhaLista();
    private PilhaLista SingletonTags = new PilhaLista();
    private static final String[] SINGLETON_TAGS = {"meta", "base", "br", "col", "command", "embed", "hr", "img", "input", "link", "param", "source", "!doctype"};
    public ListaEncadeada<String> novo = new ListaEncadeada();
    public ListaEncadeada<String> contagens = new ListaEncadeada();
    public void lerDadosArquivo(String html) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(html))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int start = 0;
                while (start != -1) {
                    start = line.indexOf("<", start);
                    if (start != -1) {
                        int end = line.indexOf(">", start);
                        if (end != -1) {
                            String tag = line.substring(start + 1, end).split(" ")[0].toLowerCase();
                            if (tag.startsWith("/")) {
                                String tagFechamento = tag.substring(1);
                                if (!isSingleton(tagFechamento)) {
                                    TagFinal.push(tagFechamento);
                                }
                                else{
                                    SingletonTags.push(tagFechamento);
                                }                         
                            } else {
                                if (!isSingleton(tag)) {
                                    TagInicio.push(tag);
                                }
                                else{
                                    SingletonTags.push(tag);
                                }                               
                            }
                            start = end + 1;
                        }
                    }
                }
            }
        }
        ListaEncadeada<String> fechamento = new ListaEncadeada<>();
        ListaEncadeada<String> abertura = new ListaEncadeada<>();
        ListaEncadeada<String> iguais = new ListaEncadeada<>();

        while (!TagFinal.estaVazia()) {
            fechamento.inserir(TagFinal.pop());
        }

        while (!TagInicio.estaVazia()) {
            abertura.inserir(TagInicio.pop());
        }

        NoLista<String> noFechamento = fechamento.getPrimeiro();
        while (noFechamento != null) {
            String tagFechamento = noFechamento.getInfo();
            NoLista<String> noAbertura = abertura.getPrimeiro();
            while (noAbertura != null) {
                String tagAbertura = noAbertura.getInfo();
                if (tagFechamento.equals(tagAbertura)) {
                    iguais.inserir(tagFechamento);
                    fechamento.retirar(tagFechamento);
                    abertura.retirar(tagAbertura);
                    break;
                }
                noAbertura = noAbertura.getProximo();
            }
            noFechamento = noFechamento.getProximo();
        }
        novo = compararTags(abertura,fechamento);
        contagens = contagem(abertura,fechamento,iguais);
    }

    private boolean isSingleton(String tag) {
        for (String singleton : SINGLETON_TAGS) {
            if (tag.equals(singleton)) {
                return true;
            }
        }
        return false;
    }
   
    private ListaEncadeada compararTags(ListaEncadeada abertura, ListaEncadeada fechamento) {
        ListaEncadeada<String> erros = new ListaEncadeada();
        NoLista<String> noFinal = fechamento.getPrimeiro();
        while (noFinal != null) {
            String tagFechamento = noFinal.getInfo();
            if (!abertura.equals(tagFechamento)) {
                erros.inserir("/" + tagFechamento + " não possui abertura ");
            }
            noFinal = noFinal.getProximo();
        }

        NoLista<String> noInicio = abertura.getPrimeiro();
        while (noInicio != null) {
            String tagAbertura = noInicio.getInfo();
            if (!fechamento.equals(tagAbertura)) {
                erros.inserir(tagAbertura + " não possui fechamento ");
            }
            noInicio = noInicio.getProximo();
        }

        if (abertura.estaVazia() && fechamento.estaVazia()) {
            erros.inserir("o arquivo está bem formatado");
        }

        return erros;
    }

    private ListaEncadeada contagem(ListaEncadeada abertura, ListaEncadeada fechamento, ListaEncadeada iguais) {
        ListaEncadeada<String> Ocorrencias = new ListaEncadeada<>();
       
        while (! SingletonTags.estaVazia()) {
            iguais.inserir( SingletonTags.pop());
        }
       
        NoLista<String> noAtual = iguais.getPrimeiro();
        while (noAtual != null) {
            String elemento = noAtual.getInfo();

            if (elemento != null) {
                if (elemento.startsWith("!")) {
                    elemento = elemento.substring(1); 
                }

                int contagem = 1;
                NoLista<String> noComparacao = noAtual.getProximo();

                while (noComparacao != null) {
                    if (elemento.equals(noComparacao.getInfo())) {
                        contagem++;
                        noComparacao.setInfo(null);
                    }
                    noComparacao = noComparacao.getProximo();
                }

                Ocorrencias.inserir(elemento + "," + contagem);
            }

            noAtual = noAtual.getProximo();
        }

        Ocorrencias = ordenarLista(Ocorrencias);
        return Ocorrencias;
    }

    private ListaEncadeada<String> ordenarLista(ListaEncadeada<String> lista) {
        if (lista.getPrimeiro() == null || lista.getPrimeiro().getProximo() == null) {
            return lista;
        }
    
        boolean trocou;
        do {
            trocou = false;
            NoLista<String> atual = lista.getPrimeiro();
            while (atual != null && atual.getProximo() != null) {
                NoLista<String> proximo = atual.getProximo();
                if (atual.getInfo().compareTo(proximo.getInfo()) > 0) {
                    String temp = atual.getInfo();
                    atual.setInfo(proximo.getInfo());
                    proximo.setInfo(temp);
                    trocou = true;
                }
                atual = atual.getProximo();
            }
        } while (trocou);
    
        return lista;
    }

    public static void main(String[] args) throws IOException {
        LeitorDeHtml c = new LeitorDeHtml();
        c.lerDadosArquivo("C:\\Users\\piva\\Downloads\\main.html");
        System.out.println(c.novo.toString());
        System.out.println(c.contagens.toString());
    }
}
