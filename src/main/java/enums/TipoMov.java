package enums;

public enum TipoMov {
    aumentaSaldo(0),
    diminuiSaldo(1),
    naoAlteraSaldo(2);

    private final int id;

    TipoMov(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TipoMov fromId(int id) {
        for (TipoMov tipoMov : TipoMov.values())
            if (tipoMov.getId() == id)
                return tipoMov;
        throw new RuntimeException("Tipo de movimentação inválido: " + id);
    }
}
