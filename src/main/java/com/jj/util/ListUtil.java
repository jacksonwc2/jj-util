package com.jj.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jj.util.enumerador.ConvertTo;
import com.jj.util.typesafe.StaticType;

@SuppressWarnings(value = "all")
public class ListUtil {

    private Logger logger = Logger.getLogger(ListUtil.class.getName());

    private Object origem;;

    /**
     * O objeto this.getOrigem() pode ser uma classe que contem uma lista ou uma lista de objetos.<br>
     * <br>
     * Ex 1: List<String> listaRuas = ListUtil.from(usuario).getListOf("enderecos.rua", String.class); <br>
     * No exemplo número 1 estamos buscando uma lista de strings que corresponde ao nome da rua que está na lista de endereços do usuario. <br>
     * Ex 2:List<String> listaRuas = ListUtil.from(enderecos).getListOf("rua", String.class); <br>
     * No exemplo número 2 estamos buscando uma lista de strings que corresponde ao nome da rua está na lista de endereços chamada 'endereços'.
     * 
     * @param this.getOrigem()
     * @return
     */
    public static ListUtil from(Object origem) {

        ListUtil listUtil = new ListUtil();
        listUtil.setOrigem(origem);

        return listUtil;
    }

    /**
     * Obtém a lista de acordo com o parâmetro passado.
     * 
     * @param field
     * @param returnType
     * @return
     */
    public <T> List<T> getListOf(StaticType<T> field) {
        return getListOf(field.getFullName(), field.getType());

    }

    private <T> List<T> getListOf(String field, Class<T> returnType) {

        List<T> retorno = null;
        try {
            List<?> list = null;
            String listField = "";
            String fieldName = field;
            Method method;

            if (this.getOrigem() instanceof Collection<?>) {
                list = (List<?>) this.getOrigem();
            } else {
                String[] parm = fieldName.split("\\.");

                if (parm.length != IntegerUtil.DOIS) {
                    logger.severe("Atributo da classe não informado.");
                    return retorno;
                }
                listField = parm[0];
                fieldName = parm[1];
                method = this.getOrigem().getClass().getDeclaredMethod("get" + StringUtil.primeiraMaiscula(listField), new Class[] {});

                list = (List<?>) method.invoke(this.getOrigem(), new Object[] {});
                if (list == null) {
                    return retorno;
                }
            }

            retorno = new ArrayList<T>();
            for (Object item : list) {

                method = item.getClass().getDeclaredMethod("get" + StringUtil.primeiraMaiscula(fieldName), new Class[] {});

                Object atributoValidar = method.invoke(item, new Object[] {});
                if (atributoValidar == null || atributoValidar.getClass().isAssignableFrom(returnType)) {
                    retorno.add((T) atributoValidar);
                } else {
                    throw new Exception("O tipo de retorno solicitado é " + returnType.getSimpleName() + ", e o atributo '" + fieldName
                            + "' é do tipo " + atributoValidar.getClass().getSimpleName() + ".");
                }
            }
        } catch (Exception e) {
            retorno = null;
            logger.severe("Falha ao recuperar a lista de objetos. " + e.getMessage());
        }

        return retorno;
    }

    public List<?> getListOf(String field) {

        List<Object> retorno = null;
        try {
            List<?> list = null;
            String listField = "";
            String fieldName = field;
            Method method;

            if (this.getOrigem() instanceof Collection<?>) {
                list = (List<?>) this.getOrigem();
            } else {
                String[] parm = fieldName.split("\\.");

                if (parm.length != IntegerUtil.DOIS) {
                    logger.severe("Atributo da classe não informado.");
                    return retorno;
                }
                listField = parm[0];
                fieldName = parm[1];
                method = this.getOrigem().getClass().getDeclaredMethod("get" + StringUtil.primeiraMaiscula(listField), new Class[] {});

                list = (List<?>) method.invoke(this.getOrigem(), new Object[] {});
                if (list == null) {
                    return retorno;
                }
            }

            retorno = new ArrayList<Object>();
            for (Object item : list) {

                method = item.getClass().getDeclaredMethod("get" + StringUtil.primeiraMaiscula(fieldName), new Class[] {});

                Object atributoValidar = method.invoke(item, new Object[] {});
                if (atributoValidar != null) {
                    retorno.add(atributoValidar);
                }
            }
        } catch (Exception e) {
            retorno = null;
            logger.severe("Falha ao recuperar a lista de objetos. " + e.getMessage());
        }

        return retorno;
    }

    /**
     * Transforma uma lista em array.
     * 
     * @param returnType
     * @return
     */
    public <T> T[] toArray(Class<T> returnType) {
        T[] array = null;
        List<?> list = null;
        if (this.getOrigem() instanceof Collection<?>) {
            list = (List<?>) this.getOrigem();
        } else {
            logger.severe("Objeto this.getOrigem() não é uma lista válida.");
            return array;
        }
        array = (T[]) Array.newInstance(returnType, list.size());
        return list.toArray(array);
    }

    public static <T> T[] toArray(List<?> lista, Class<T> returnType) {
        T[] array = null;

        array = (T[]) Array.newInstance(returnType, lista.size());
        return lista.toArray(array);
    }

    /**
     * Ordena uma lista por determinado atributo.
     * 
     * @param fieldName
     */
    public <T> void sortBy(String fieldName, ConvertTo convertTo) {
        sortBy(fieldName, Boolean.TRUE, convertTo, null);
    }

    public <T> void sortBy(String fieldName) {
        sortBy(fieldName, Boolean.TRUE, null, null);
    }

    public <T> void sortBy(String fieldName, Boolean direcaoAsc) {
        sortBy(fieldName, direcaoAsc, null, null);
    }

    public <T> void sortBy(String fieldName, Boolean direcaoAsc, ConvertTo convertTo, String format) {

        List<T> list = null;
        if (this.getOrigem() instanceof Collection<?>) {
            list = (List<T>) this.getOrigem();
        } else {
            logger.severe("Objeto this.getOrigem() não é uma lista válida.");
            return;
        }
        Collections.sort(list, new Comparator<T>() {

            @Override
            public int compare(T a, T b) {
                int retorno = 0;
                try {
                    Method methodGet = a.getClass().getDeclaredMethod("get" + StringUtil.primeiraMaiscula(fieldName), new Class[] {});
                    Object valor1 = methodGet.invoke(a, new Object[] {});

                    valor1 = convertTo(valor1, convertTo, format);

                    Object valor2 = methodGet.invoke(b, new Object[] {});
                    valor2 = convertTo(valor2, convertTo, format);

                    Method method3 = valor1.getClass().getDeclaredMethod("compareTo", valor1.getClass());
                    if (direcaoAsc) {
                        retorno = (int) method3.invoke(valor1, valor2);
                    } else {
                        retorno = (int) method3.invoke(valor2, valor1);
                    }
                } catch (Exception e) {
                    logger.severe("Falha ao realizar a ordenação dos objetos da lista. " + e.getMessage());
                }
                return retorno;
            }

            private Object convertTo(Object valor, ConvertTo converTo, String format) {

                if (converTo != null) {
                    switch (converTo) {
                        case DAY_OF_WEEK_NUMBER:
                            valor = (format != null && format.isEmpty()) ? DateUtil.dayOfWeekNumber(DateUtil.parseDate(valor))
                                    : DateUtil.dayOfWeekNumber(DateUtil.parseDate(valor, format));
                            break;
                        case DAY_OF_MONTH_NUMBER:
                            valor = (format != null && format.isEmpty()) ? DateUtil.dayOfMonthNumber(DateUtil.parseDate(valor))
                                    : DateUtil.dayOfMonthNumber(DateUtil.parseDate(valor, format));
                            break;
                        case INTEGER:
                            valor = Integer.valueOf(valor.toString());
                            break;
                        case LONG:
                            valor = Long.valueOf(valor.toString());
                            break;
                        default:
                            break;
                    }
                }
                return valor;
            }
        });

    }

    public <T> T findItem(String fieldName, Object valor) {

        List<T> list;
        T ret = null;
        if (this.getOrigem() instanceof Collection<?>) {
            list = (List<T>) this.getOrigem();
        } else {
            logger.severe("Objeto this.getOrigem() não é uma lista válida.");
            return ret;
        }

        Optional<T> lista = list.stream().filter(x -> compareTo(getValue(fieldName, x), valor)).findAny();
        if (lista.isPresent()) {
            return lista.get();
        }
        return null;
    }

    private <T> Object getValue(String fieldName, T item) {
        Object ret = null;
        String[] fields = fieldName.split("\\.");
        if (fields.length > 1) {
            Object valueItem = item;
            for (String string : fields) {
                valueItem = getValue(string, valueItem);
            }
            ret = valueItem;
        } else {
            try {
                ret = item.getClass().getDeclaredMethod("get" + StringUtil.primeiraMaiscula(fieldName), new Class[] {}).invoke(item, new Object[] {});
            } catch (Exception e) {
                logger.severe("Atributo '" + fieldName + "' não localizado." + e.getMessage());
            }
        }
        return ret;
    }

    public <T> List<T> find(MapValue<String, Object> condicoes) {

        List<T> list = null;

        if (this.getOrigem() instanceof Collection<?>) {
            list = (List<T>) this.getOrigem();
        } else {
            logger.severe("Objeto this.getOrigem() não é uma lista válida.");
            return list;
        }

        for (ItemMap<String, Object> item : condicoes.getItens()) {
            list = ListUtil.from(list).find(item.getKey(), item.getValue());
        }
        return list;

    }

    public <T> List<T> find(String fieldName, Object valor) {
        List<T> list = null;
        List<T> ret = null;

        if (this.getOrigem() instanceof Collection<?>) {
            list = (List<T>) this.getOrigem();
        } else {
            logger.severe("Objeto this.getOrigem() não é uma lista válida.");
            return ret;
        }

        ret = list.stream().filter(w -> compareTo(getValue(fieldName, w), valor)).collect(Collectors.toList());

        return ret;
    }

    private Boolean compareTo(Object value1, Object value2) {
        if (value1 != null && value2 != null) {
            return value1.equals(value2);
        } else {
            return value1 == value2;
        }
    }

    public <T> Boolean isNullOrEmpty() {

        List<T> list;
        if (this.getOrigem() == null) {
            return Boolean.TRUE;
        }
        if (this.getOrigem() instanceof Collection<?>) {
            list = (List<T>) this.getOrigem();
        } else {
            logger.severe("Objeto this.getOrigem() não é uma lista válida.");
            return Boolean.TRUE;
        }
        if (list == null || list.isEmpty()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static boolean isNullOrEmpty(List<?> list) {

        if (list == null || list.isEmpty()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public static boolean isNullOrEmpty(Set<?> list) {
        if (list == null) {
            return Boolean.TRUE;
        }
        return ListUtil.from(list).isNullOrEmpty();
    }

    /**
     * Retorna False quando: lista for nula, vazia. Retorna validação do sizeEquals quando não for nula nem vazia.
     * 
     * @param List<?>, Integer
     * @return boolean
     */
    public static boolean sizeEquals(List<?> list, Integer numero) {
        if (!isNullOrEmpty(list)) {
            if (list.size() == numero) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Retorna False quando: lista for nula, vazia. Retorna validação do sizeMoreEquals a lista for maior ou igual ao numero.
     * 
     * @param List<?>, Integer
     * @return boolean
     */
    public static boolean sizeMoreEquals(List<?> list, Integer numero) {
        if (!isNullOrEmpty(list)) {
            if (list.size() >= numero) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static <T> List<T> toList(T[] array) {
        List<T> lista = new ArrayList<T>();
        for (T t : array) {
            lista.add(t);
        }
        return lista;
    }

    public static <T, M> List<M> toList(T[] array, Class<M> classConvert) {
        List<M> lista = new ArrayList<M>();

        for (T t : array) {
            lista.add(Json.getInstance().fromJson(classConvert, t.toString().trim()));
        }
        return lista;
    }

    public static <T, M> List<M> toList(Class<M> classConvert, T... array) {
        List<M> lista = new ArrayList<M>();

        for (T t : array) {
            lista.add(Json.getInstance().fromJson(classConvert, t.toString().trim()));
        }
        return lista;
    }

    /**
     * Retorna False quando: lista for nula, vazia. Retorna validação do sizeMore a lista for maior ao numero.
     * 
     * @param List<?>, Integer
     * @return boolean
     */
    public static boolean sizeMore(List<?> list, Integer numero) {
        if (!isNullOrEmpty(list)) {
            if (list.size() > numero) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Retorna False quando: lista for nula, vazia. Retorna validação do sizeMore a lista for menor ao numero.
     * 
     * @param List<?>, Integer
     * @return boolean
     */
    public static boolean sizeLess(List<?> list, Integer numero) {
        if (!isNullOrEmpty(list) && numero < list.size()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Retorna True quando: a lista for nula, vazia ou maior que o numero
     * 
     * @param List<?>, Integer
     * @return boolean
     */
    public static boolean isNullOrEmptyOrMore(List<?> list, Integer numero) {
        if (isNullOrEmpty(list)) {
            return Boolean.TRUE;
        }
        if (sizeMore(list, numero)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static boolean eq(List<?> lista1, List<?> lista2) {

        int hashCodeLista1 = getHashCodeLista(lista1);

        int hashCodeLista2 = getHashCodeLista(lista2);

        return hashCodeLista1 == hashCodeLista2;
    }

    private static int getHashCodeLista(List<?> lista1) {
        int hashTotal = 0;
        for (Object object : lista1) {
            hashTotal += object.hashCode();
        }
        return hashTotal;
    }

    public static String join(List<?> ids, String string) {

        StringBuilder stringBuilder = new StringBuilder();

        for (Object codigo : ids) {

            if (stringBuilder.length() > 0) {
                stringBuilder.append(string);
            }
            stringBuilder.append(codigo.toString());
        }

        return stringBuilder.toString();
    }

    public Object getOrigem() {
        return origem;
    }

    public void setOrigem(Object origem) {
        this.origem = origem;
    }

}