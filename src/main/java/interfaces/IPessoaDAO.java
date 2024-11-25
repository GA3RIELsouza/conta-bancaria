package interfaces;

import models.Pessoa;
import enums.Situacao;

public interface IPessoaDAO<P extends Pessoa> extends IDAO<P> {
    P consultarPorSituacao(Situacao situacao);
}
