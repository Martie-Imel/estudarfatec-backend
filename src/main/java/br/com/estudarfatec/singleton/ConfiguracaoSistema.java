package br.com.estudarfatec.singleton;

/**
 * Padrão Singleton — ConfiguracaoSistema
 *
 * Garante que exista apenas UMA instância das configurações
 * globais da aplicação durante todo o ciclo de vida do sistema.
 *
 * Thread-safe via inicialização estática (Initialization-on-demand holder).
 */
public class ConfiguracaoSistema {

    private static final String VERSAO_API   = "1.0.0";
    private static final String NOME_SISTEMA = "EstudarFatec API";

    private int maximoTarefasPorUsuario;
    private boolean modoDebug;

    // Construtor privado — ninguém cria instância diretamente
    private ConfiguracaoSistema() {
        this.maximoTarefasPorUsuario = 100;
        this.modoDebug = false;
    }

    // Holder garante lazy initialization e thread-safety sem synchronized
    private static class Holder {
        private static final ConfiguracaoSistema INSTANCIA = new ConfiguracaoSistema();
    }

    /** Retorna a única instância da configuração. */
    public static ConfiguracaoSistema getInstancia() {
        return Holder.INSTANCIA;
    }

    // -------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------

    public String getVersaoApi() {
        return VERSAO_API;
    }

    public String getNomeSistema() {
        return NOME_SISTEMA;
    }

    public int getMaximoTarefasPorUsuario() {
        return maximoTarefasPorUsuario;
    }

    public void setMaximoTarefasPorUsuario(int maximo) {
        this.maximoTarefasPorUsuario = maximo;
    }

    public boolean isModoDebug() {
        return modoDebug;
    }

    public void setModoDebug(boolean modoDebug) {
        this.modoDebug = modoDebug;
    }

    @Override
    public String toString() {
        return NOME_SISTEMA + " v" + VERSAO_API +
               " | debug=" + modoDebug +
               " | maxTarefas=" + maximoTarefasPorUsuario;
    }
}
