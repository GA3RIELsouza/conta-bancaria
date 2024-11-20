package enums;

public enum Sexo {
    Masculino(0),
    Feminino(1);

    private final int id;

    Sexo(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static Sexo fromId(int id) {
        for (Sexo sexo : Sexo.values())
            if (sexo.getId() == id)
                return sexo;
        throw new RuntimeException("Sexo inválido: " + id);
    }
}
