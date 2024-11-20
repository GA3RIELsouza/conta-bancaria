package interfaces;

import java.util.List;

import models.Pessoa;

public interface IPessoaDAO {
    void incluir(Pessoa pessoa);

    long incluir(Pessoa pessoa, boolean retornaChave);

    void atualizar(Pessoa pessoa);

    void excluir(long id);

    Pessoa consultarPorId(Pessoa pessoa);

    List<Pessoa> consultarTodos();
}
