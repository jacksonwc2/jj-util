package com.jj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * De forma semelhante ao HashMap nativo o MapValue, possui chave e valor, porém neste utilitário é possivel definir se a chave vai ser única ou não, por padrão será false.
 *
 * @param <K>
 * @param <V>
 */
public class MapValue<K, V> {

    private Boolean uniqueKey = Boolean.FALSE;
    private Logger logger = Logger.getLogger(MapValue.class.getName());

    private List<ItemMap<K, V>> itens = new ArrayList<ItemMap<K, V>>();

    public void put(K key, V value) {
        ItemMap<K, V> item = new ItemMap<K, V>(key, value);

        if (uniqueKey && itens.contains(item)) {
            logger.severe("A chave '" + key + "' já existe neste HashTable. Por favor verifique.");
            return;
        }
        itens.add(item);
    }

    public void remove(int index) {
        itens.remove(index);
    }

    public List<ItemMap<K, V>> getItens() {
        return itens;
    }

    public Boolean getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(Boolean uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public ItemMap<K, V> get(int index) {
        return itens.get(index);

    }

    public V getValue(K key) {
        Optional<ItemMap<K, V>> lista = itens.stream().filter(x -> x.getKey() == key).findAny();

        if (lista.isPresent()) {
            return lista.get().getValue();
        }
        return null;
    }

    public void sort() {
        ListUtil.from(itens).sortBy("key");
    }

    public V getLastValue() {

        if (!itens.isEmpty()) {
            return itens.get(itens.size() - 1).getValue();
        }
        return null;
    }

}
