package interfaces;

import models.ContaEspecial;
import java.sql.Date;

public interface IContaEspecialDAO extends IContaBancariaDAO<ContaEspecial> {
    void promoverConta(long id, double limiteCredito, Date dataVctoContrato);

    void rebaixarConta(long id);
}
