package interfaces;

import java.util.List;

import models.Telefone;

public interface ITelefoneDAO extends IDAO<Telefone> {
    List<Telefone> consultarPorPessoa(long idPessoa);
}
