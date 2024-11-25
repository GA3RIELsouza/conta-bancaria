package interfaces;

import models.ContaSalario;
import java.util.List;

public interface IContaSalarioDAO extends IContaBancariaDAO<ContaSalario> {
    List<ContaSalario> consultarPorCnpj(String cnpj);
}
