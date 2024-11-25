package interfaces;

import models.PessoaFisica;

public interface IPessoaFisicaDAO extends IPessoaDAO<PessoaFisica> {
    PessoaFisica consultarPorCpf(String cpf);
}
