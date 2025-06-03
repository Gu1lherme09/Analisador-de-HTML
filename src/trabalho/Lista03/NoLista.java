package Lista03;

public class NoLista<T> {

    private T info;
    private NoLista<T> proximo;

    public NoLista(T t) {
        this.setInfo(t);
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T t) {
        info = t;
    }

    public NoLista getProximo() {
        return proximo;
    }

    public void setProximo(NoLista<T> no) {
        proximo = no;
    }

    @Override
    public String toString() {
        return info.toString();
    }
}
