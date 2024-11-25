package interfaces;

import models.PessoaJuridica;

public interface IPessoaJuridicaDAO extends IPessoaDAO<PessoaJuridica> {
    PessoaJuridica consultarPorCnpj(String cnpj);
}
