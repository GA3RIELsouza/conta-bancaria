package interfaces;

import models.IndiceRemuneracao;
import enums.Situacao;

public interface IIndiceRemuneracaoDAO extends IDAO<IndiceRemuneracao> {
    IndiceRemuneracao consultarPorSituacao(Situacao situacao);
}
