package interfaces;

import java.util.List;

public interface IDAO<O extends Object> {
    void incluir(O objeto);

    long incluir(O objeto, boolean retornaChave);

    void atualizar(O objeto);

    void excluir(long id);

    O consultarPorId(long id);

    List<O> consultarTodos();
}
