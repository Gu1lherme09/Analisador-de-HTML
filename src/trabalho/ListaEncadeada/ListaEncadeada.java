package ListaEncadeada;

public class ListaEncadeada<T> {

    private NoLista<T> primeiro;

    public ListaEncadeada() {
        primeiro = null;
    }

    public NoLista<T> getPrimeiro() {
        return primeiro;
    }

    public void inserir(T info) {
        NoLista<T> novo = new NoLista<T>();
        novo.setInfo(info);
        novo.setProximo(primeiro);
        primeiro = novo;
    }

    public void inserirEmQualquerPosicao(T info, int posicao) {
        posicao--;
        if (posicao < 0 || posicao > this.obterComprimento()) {
            throw new IndexOutOfBoundsException();
        }

        NoLista<T> novo = new NoLista<>();
        novo.setInfo(info);

        if (posicao == 0) {
            novo.setProximo(primeiro);
            primeiro = novo;
        } else {
            NoLista<T> anterior = obterNo(posicao - 1);
            NoLista<T> proximo = anterior.getProximo();

            novo.setProximo(proximo);
            anterior.setProximo(novo);
        }
    }

    public boolean estaVazia() {
        return primeiro == null;
    }

    public NoLista<T> buscar(T info) {
        NoLista<T> atual = this.primeiro;
        while (atual != null) {
            if (atual.getInfo().equals(info)) {
                return atual;
            }
            atual = atual.getProximo();
        }
        return null;
    }

    /* public void retirar(T info) {
        if (this.primeiro.getInfo().equals(info)) {
            this.primeiro = this.primeiro.getProximo();
        }

        NoLista<T> anterior = this.primeiro;
        NoLista<T> atual = anterior.getProximo();

        while (atual != null) {
            if (atual.getInfo().equals(info)) {
                anterior.setProximo(atual.getProximo());
            }
            anterior = atual;
            atual = atual.getProximo();
        }
    } */
    public void retirar(T valor) {
        NoLista<T> anterior = null;
        NoLista<T> p = primeiro;
    
        while (p != null && !p.getInfo().equals(valor)) {
            anterior = p;
            p = p.getProximo();
        }
    
        if (p != null) {
            if (p == primeiro) {
                primeiro = primeiro.getProximo();
            } else {
                anterior.setProximo(p.getProximo());
            }
        }
    }

   

    public int obterComprimento() {
        int comprimento = 0;
        NoLista<T> atual = this.primeiro;
        while (atual != null) {
            comprimento++;
            atual = atual.getProximo();
        }
        return comprimento;
    }

    public NoLista<T> obterNo(int posicao) {
        if (posicao < 0 || posicao >= this.obterComprimento()) {
            throw new IndexOutOfBoundsException();
        }

        NoLista<T> atual = this.primeiro;

        for (int i = 0; i < posicao; i++) {
            atual = atual.getProximo();
        }
        return atual;
    }

    @Override
    public String toString() {
        String resultado = "";

        NoLista<T> atual = this.primeiro;
        while (atual != null) {
            resultado += atual.getInfo().toString();
            if (atual.getProximo() != null) {
                resultado += ",";
            }
            atual = atual.getProximo();
        }
        return resultado;
    }
}
