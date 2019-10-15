package com.jj.util.cache;

import java.util.List;

import com.jj.util.exception.BusinessServerException;

/**
 * Gerenciador de cache.
 *
 */
public interface EhCacheManager {

    /**
     * Recupera o cache a ser manipulado.
     * @param cacheName
     * @return
     */
    public abstract EhCacheManager from(String cacheName);
    
    /**
     * Recupera um objeto do tipo parametrizado (returnType).
     * @param elementName
     * @param returnType
     * @return
     * @throws BusinessServerException
     */
    public abstract <T> T getObject(String elementName, Class<T> returnType);

    /**
     * Recupera uma lista de objetos do tipo parametrizado (returnType).
     * @param elementName
     * @param returnType
     * @return
     * @throws BusinessServerException
     */
    public abstract <T> List<T> getList(String elementName, Class<T> returnType);

    /**
     * Insere objetos identificados por um nome.
     * @param elementName
     * @param obj
     * @throws BusinessServerException
     */
    public abstract void put(String elementName, Object obj);
    
    /**
     * Remove todos os elementos de um determinado cache.
     * @throws BusinessServerException
     */
    public abstract void removeAll() throws BusinessServerException;

    /**
     * Remove elementos identicados pelo nome de um determinado cache.
     * @throws BusinessServerException
     */
    public abstract void remove(String elementName);
    
    /**
     * Efetua a remoção de elementos em todos os caches.
     */
    public abstract void clearAll();

}