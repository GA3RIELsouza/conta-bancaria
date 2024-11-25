package interfaces;

import java.util.List;

import models.ContaBancaria;

public interface IContaBancariaDAO<CB extends ContaBancaria> extends IDAO<CB> {
    List<CB> consultarPorTitular(long idTitular);

    List<CB> consultarPorBanco(long codigoBanco);
}
