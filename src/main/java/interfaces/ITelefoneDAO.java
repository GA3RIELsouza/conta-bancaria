package interfaces;

import java.util.List;

import models.Telefone;

public interface ITelefoneDAO {
    void incluir(Telefone telefone);

    long[] incluir(Telefone telefone, boolean retornaChave);

    void atualizar(Telefone telefone);

    void excluir(long id, long idPessoa);

    Telefone consultarPorId(long id, long idPessoa);

    List<Telefone> consultarTodos();

    List<Telefone> consultarTodosPorPessoa(long idPessoa);
}
