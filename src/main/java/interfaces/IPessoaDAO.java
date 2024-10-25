package interfaces;

import java.util.List;
import models.Pessoa;

public interface IPessoaDAO
{
    boolean inserir(Pessoa p);

    boolean atualizar(Pessoa p);

    boolean deletar(Pessoa p);

    boolean deletar(long id);

    Pessoa retornarPorId(long id);

    List<Pessoa> retornarTodos();
}