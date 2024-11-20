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

    public static Situacao fromId(int id) {
        for (Situacao situacao : Situacao.values())
            if (situacao.getId() == id)
                return situacao;
        throw new RuntimeException("Situação inválida: " + id);
    }
}
