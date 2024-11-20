package enums;

public enum TipoTel {
    Movel(0),
    Fixo(1),
    Comercial(2);

    private final int id;

    TipoTel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TipoTel fromId(int id) {
        for (TipoTel tipoTel : TipoTel.values())
            if (tipoTel.getId() == id)
                return tipoTel;
        throw new RuntimeException("Tipo de telefone inválido: " + id);
    }
}
