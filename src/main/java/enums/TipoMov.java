package enums;

public enum TipoMov
{
    aumentaSaldo(0),
    diminuiSaldo(1),
    naoAlteraSaldo(2);

    private final int id;

    TipoMov(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
}