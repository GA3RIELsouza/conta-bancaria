package interfaces;

import models.Evento;
import enums.Situacao;

public interface IEventoDAO extends IDAO<Evento> {
    Evento consultarPorSituacao(Situacao situacao);
}
