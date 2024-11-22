package interfaces;

import java.util.List;
import models.ContaBancaria;

public interface IContaBancariaDAO {
    void incluir(ContaBancaria contaBancaria);

    long incluir(ContaBancaria contaBancaria, boolean retornaChave);

    void atualizar(ContaBancaria contaBancaria);

    void excluir(long id);

    ContaBancaria consultarPorId(ContaBancaria contaBancaria);

    List<ContaBancaria> consultarTodosPorTitular(List<ContaBancaria> contasBancarias);

    List<ContaBancaria> consultarTodosPorBanco(List<ContaBancaria> contasBancarias);

    List<ContaBancaria> consultarTodos();
}
