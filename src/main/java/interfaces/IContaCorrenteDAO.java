package interfaces;

import java.util.List;

import models.ContaCorrente;

public interface IContaCorrenteDAO {
    void incluir(ContaCorrente contaCorrente);

    long incluir(ContaCorrente contaCorrente, boolean retornaChave);

    void atualizar(ContaCorrente contaCorrente);

    void excluir(long id_conta_bancaria);

    ContaCorrente consultarPorId(long id_conta_bancaria);

    List<ContaCorrente> consultarTodos();
}
