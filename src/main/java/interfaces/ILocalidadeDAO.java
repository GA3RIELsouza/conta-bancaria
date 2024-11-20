package interfaces;

import java.util.List;
import models.Localidade;
import enums.Estado;

public interface ILocalidadeDAO {
    void incluir(Localidade localidade);

    long incluir(Localidade localidade, boolean retornaChave);

    void atualizar(Localidade localidade);

    void excluir(long id);

    Localidade consultarPorId(long id);

    Localidade consultarPorCep(long cep);

    List<Localidade> consultarTodos();

    List<Localidade> consultarTodosPorUf(Estado uf);
}
