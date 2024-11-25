package interfaces;

import java.util.List;
import models.Localidade;
import enums.Estado;

public interface ILocalidadeDAO extends IDAO<Localidade> {
    Localidade consultarPorCep(long cep);

    List<Localidade> consultarPorUf(Estado uf);
}
