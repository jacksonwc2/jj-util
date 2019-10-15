package com.jj.util.config;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Classe responsável por armazenar e encapsular as configurações do sistema.
 *
 */
public abstract class Configuration {

    private static Logger logger = Logger.getLogger(Configuration.class.getName());

    /**
     * Esse objeto armazena todas as configurações do sistema.
     */
    private static final Map<String, Object> SYSTEM_CONFIG = new TreeMap<String, Object>();

    private Configuration() {

    }

    /**
     * Adquire a string de configuração pelo seu nome de acesso.
     * 
     * @param configName
     * @return Uma String com a configuração. <br>
     *         Retorna null caso ele não exista.
     */
    public static String getConfig(String configName) {

        logger.fine("Retornando a string de configuração: " + configName);

        Object retorno = SYSTEM_CONFIG.get(configName);

        if (retorno == null) {
            logger.fine("A configuração " + configName + " não foi configurada.");
        }

        if (retorno instanceof String) {
            return (String) retorno;
        }

        return null;
    }

    /**
     * Adiciona a string de configuração pelo seu nome de acesso. Se o configName já existe, o seu valor será substituído.
     * 
     * @param configName
     * @param configValue
     */
    public static void addConfig(String configName, String configValue) {

        logger.fine("Adicionando a string de configuração: " + configName);

        SYSTEM_CONFIG.put(configName, configValue);
    }

    /**
     * Adquire a lista de string de configuração pelo seu nome de acesso.
     * 
     * @param configName
     * @return Uma String com a configuração. <br>
     *         Retorna null caso ele não exista.
     */
    @SuppressWarnings("unchecked")
    public static List<String> getConfigList(String configName) {

        logger.fine("Retornando a lista de strings de configuração: " + configName);

        Object retorno = SYSTEM_CONFIG.get(configName);

        if (retorno instanceof List) {
            return (List<String>) retorno;
        }

        return (List<String>) retorno;
    }

    /**
     * Adiciona a lista de strings de configuração pelo seu nome de acesso. Se o configName já existe, o seu valor será substituído.
     * 
     * @param configName
     * @param configValueList
     */
    public static void addConfig(String configName, List<String> configValueList) {

        logger.fine("Adicionando a lista de strings de configuração: " + configName);

        SYSTEM_CONFIG.put(configName, configValueList);
    }
}
