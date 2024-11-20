package interfaces;

import java.util.List;

import models.Telefone;

public interface ITelefoneDAO {
    void incluir(Telefone telefone);

    long incluir(Telefone telefone, boolean retornaChave);

    void atualizar(Telefone telefone);

    void excluir(long id);

    Telefone consultarPorId(long id);

    List<Telefone> consultarTodos();

    List<Telefone> consultarTodosPorPessoa(long idPessoa);
}
