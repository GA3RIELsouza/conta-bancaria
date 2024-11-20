package interfaces;

import java.util.List;
import models.PessoaJuridica;

public interface IPessoaJuridicaDAO {
    void incluir(PessoaJuridica pessoaJuridica);

    long incluir(PessoaJuridica pessoaJuridica, boolean retornaChave);

    void atualizar(PessoaJuridica pessoaJuridica);

    void excluir(long id);

    PessoaJuridica consultarPorId(long id);

    List<PessoaJuridica> consultarTodos();
}
