package interfaces;

import models.Cotacao;

public interface ICotacaoDAO extends IDAO<Cotacao> {
    Cotacao consultarPorIndiceRemuneracao(long id);
}
