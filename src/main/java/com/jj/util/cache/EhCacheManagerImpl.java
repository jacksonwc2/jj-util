package com.jj.util.cache;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.jj.util.logger.LoggerUtil;

public class EhCacheManagerImpl implements EhCacheManager {

    private static final String CACHE_NAO_LOCALIZADO = "Cache não localizado. No método from(<NomeCache>) informe o nome do cache configurado no arquivo ehcache.xml.";

    @Inject
    @LoggerUtil
    private Logger logger;

    private Cache cache;

    public EhCacheManagerImpl() {

    }

    @Override
    public EhCacheManager from(String cacheName) {

        this.setCache(CacheManager.getInstance().getCache(cacheName));

        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getObject(String elementName, Class<T> returnType) {

        T obj = null;
        if (!cacheValido("getObject()")) {
            return obj;
        }

        Element element = this.obterElemento(elementName);
        if (element != null) {
            obj = (T) element.getObjectValue();
        }
        return obj;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(String elementName, Class<T> returnType) {
        List<T> obj = null;

        if (!cacheValido("getList()")) {
            return obj;
        }

        Element element = this.obterElemento(elementName);

        if (element != null) {
            obj = (List<T>) element.getObjectValue();
        }
        return obj;
    }

    @Override
    public void put(String elementName, Object obj) {

        if (!cacheValido("put()")) {
            return;
        }

        Class<?>[] interfaces = obj.getClass().getInterfaces();
        if (!Arrays.asList(interfaces).contains(Serializable.class)) {

            if (logger != null) {
                logger.severe("ERRO! Método: put classe especificada: " + obj.getClass().getName()
                        + " não contém implementação da interface Serializable.class");
            }
            return;
        }

        Element element = new Element(elementName, obj);
        this.getCache().put(this.clonar(element));

    }

    @Override
    public void removeAll() {
        if (!cacheValido("removeAll()")) {
            return;
        }
        this.getCache().removeAll();

    }

    @Override
    public void clearAll() {
        CacheManager.getInstance().clearAll();

    }

    @Override
    public void remove(String elementName) {
        if (!cacheValido("remove()")) {
            return;
        }
        this.getCache().remove(elementName);

    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    private boolean cacheValido(String methodName) {
        if (cache == null) {
            if (logger != null) {
                logger.info("ERRO! Método:" + methodName + ". ==> " + CACHE_NAO_LOCALIZADO);
            }
            return false;
        }
        return true;
    }

    private Element obterElemento(String elementName) {
        return this.clonar(this.getCache().get(elementName));
    }

    private Element clonar(Element element) {
        if (element != null) {
            try {
                return (Element) element.clone();
            } catch (Exception e) {
                if (logger != null) {
                    logger.info("ERRO! Método: clonar ==> " + CACHE_NAO_LOCALIZADO + " | " + e.getMessage());
                }
            }
        }
        return null;
    }

}
