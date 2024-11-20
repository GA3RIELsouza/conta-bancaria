package enums;

public enum Situacao {
    Ativo(0),
    Inativo(1);

    private final int id;

    Situacao(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
