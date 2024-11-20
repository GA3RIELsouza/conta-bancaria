package interfaces;

import java.util.List;
import models.Pessoa;

public interface IPessoaDAO {
    boolean inserir(Pessoa p);

    boolean atualizar(Pessoa p);

    boolean excluir(long id);

    Pessoa retornarPorId(long id);

    List<Pessoa> retornarTodos();
}
