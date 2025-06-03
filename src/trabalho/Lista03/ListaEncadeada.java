package Lista03;

public class ListaEncadeada<T> {

    NoLista<T> primeiro;
    private int tamanho;

    public ListaEncadeada() {
        primeiro = null;
        tamanho = 0;
    }

    public NoLista<T> getPrimeiro() {
        return primeiro;
    }

    public void inserir(T info) {

        NoLista<T> no = new NoLista<>(info);

        no.setProximo(primeiro);

        primeiro = no;
        tamanho++;
    }

    public void retirar(T info) {

        NoLista<T> no = primeiro;
        NoLista<T> anterior = null;
        while (no != null && !(no.getInfo().equals(info))) {
            anterior = no;
            no = no.getProximo();
        }

        if (no != null) {
            if (no == primeiro) {
                primeiro = primeiro.getProximo();
            } else {
                anterior.setProximo(no.getProximo());
            }
            tamanho--;
        }

    }

    public int obterComprimento() {
        return tamanho;
    }

    public boolean estaVazia() {
        return primeiro == null;
    }

    public NoLista<T> buscar(T info) {

        NoLista<T> no = primeiro;

        while (no != null) {
            if (no.getInfo() == info) {
                return no;
            }
            no = no.getProximo();
        }

        return null;

    }

    public NoLista<T> obterNo(int i) {

        if (i < 0) {
            throw new IndexOutOfBoundsException("Não é possível acessar index negativo da lista");
        }

        NoLista<T> no = primeiro;

        int j=0;

        while (j!= i && no != null) {
            j++;
            no = no.getProximo();
        }

        if (no == null) {
            throw new IndexOutOfBoundsException("Index " + i + " fora de alcance para a lista");
        }
        return no;
    }

    @Override
    public String toString() {

        String str = "";

        NoLista<T> no = primeiro;

        while (no != null) {

            str += no.getInfo();

            if (no.getProximo() != null) {
                str += ",";
            }

            no = no.getProximo();
        }
        return str;
    }

    public ListaEncadeada<T> criarSubLista(int inicio, int fim) {

        if (inicio < 0 || fim < 0) {
            throw new IndexOutOfBoundsException("Não é possível acessar um índice menor que 0");
        }

        if (inicio > fim) {
            throw new IllegalArgumentException("O valor do índice final da substring não pode ser maior que o índice inicial");
        }
        ListaEncadeada<T> novaLista = new ListaEncadeada<>();

        NoLista<T> no = primeiro;

        int i=0;

        while (i <= fim) {

            if (no == null) {
                throw new IndexOutOfBoundsException("O índice " + fim + " não existe na lista");
            }

            if (i>=inicio) {
                // Na implementação de ListaEncadeada, o método inserir recebe um objeto da classe
                novaLista.inserir(no.getInfo());
            }
            no = no.getProximo();
            i++;
        }

        return novaLista;
    }
}
