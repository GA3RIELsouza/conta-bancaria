package interfaces;

import java.util.List;
import models.PessoaFisica;

public interface IPessoaFisicaDAO {
    void incluir(PessoaFisica pessoaFisica);

    long incluir(PessoaFisica pessoaFisica, boolean retornaChave);

    void atualizar(PessoaFisica pessoaFisica);

    void excluir(long id);

    PessoaFisica consultarPorId(long id);

    List<PessoaFisica> consultarTodos();
}
