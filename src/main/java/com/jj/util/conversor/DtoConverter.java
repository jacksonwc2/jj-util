package com.jj.util.conversor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.hibernate.collection.internal.PersistentBag;

import com.jj.util.CharCase;
import com.jj.util.DateUtil;
import com.jj.util.HbUtils;
import com.jj.util.IfNull;
import com.jj.util.IntegerUtil;
import com.jj.util.StringUtil;
import com.jj.util.annotation.Dto;
import com.jj.util.annotation.DtoColumn;
import com.jj.util.annotation.DtoField;
import com.jj.util.annotation.DtoFieldLogic;
import com.jj.util.annotation.DtoFieldView;
import com.jj.util.annotation.DtoPk;
import com.jj.util.annotation.ValorBooleano;
import com.jj.util.exception.BusinessServerException;

@SuppressWarnings(value = "all")
public class DtoConverter {

    private static final String DTO_SCRIPT = "DTO_SCRIPT";
    private static final String FALHA_AO_CONVERTER_DTO_PARA_SCRIPT = "Falha ao converter DTO para script.";
    private static Logger logger = Logger.getLogger(DtoConverter.class.getName());

    private DtoConverter() {

    }

    public static <T> List<T> clone(List<T> obj) {
        List<T> listObjects = new ArrayList<T>();

        if (obj == null) {
            return obj;
        }

        obj.stream().filter(item -> item != null).forEach(item -> listObjects.add(clone(item)));

        return listObjects;

    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(T obj) {
        T objClone = HbUtils.deproxy(obj);

        T cloneObject = null;

        if (objClone == null) {
            return cloneObject;
        }

        try {
            cloneObject = (T) objClone.getClass().newInstance();

            Class<? extends Object> fromClass = objClone.getClass();

            Class<? extends Object> tooClass = cloneObject.getClass();
            Field[] tooFields = getAllFields(tooClass);

            for (Field tooF : tooFields) {
                if (!java.lang.reflect.Modifier.isStatic(tooF.getModifiers())) {
                    tooF.setAccessible(true);
                    Field fromF = getDeclaredField(fromClass, tooF.getName());
                    fromF.setAccessible(true);

                    cloneFields(objClone, cloneObject, tooF, fromF);
                }

            }
        } catch (Exception e) {

            logger.severe(e.getMessage());

        }
        return cloneObject;

    }

    private static <T> void cloneFields(T obj, T cloneObject, Field tooF, Field fromF) throws IllegalAccessException {
        Class<?> ob = fromF.getType();

        if (ob.getName().startsWith("java")) {

            if (ob.isAssignableFrom(List.class)) {
                tooF.set(cloneObject, clone((List<?>) fromF.get(obj)));
            } else {
                tooF.set(cloneObject, fromF.get(obj));
            }

        } else {
            tooF.set(cloneObject, clone(fromF.get(obj)));
        }
    }

    @SuppressWarnings("unchecked")
    public static <E, T> List<T> convert(List<E> orig, Class<T> dest) {
        List<T> retorno = new ArrayList<T>();

        if (orig == null) {
            logger.severe("Não foi possível converter via reflection as classes DTO e Model.  Objeto Origem nulo. ");
            return retorno;
        }

        if (!orig.isEmpty() && orig.get(0).getClass().equals(dest)) {
            return (List<T>) orig;
        }

        orig.stream().filter(item -> item != null).forEach(item -> retorno.add(convert(item, dest)));

        return retorno;
    }

    @SuppressWarnings("unchecked")
    public static <T> T convert(Object orig, Class<T> dest) {

        Object objOrigem = HbUtils.deproxy(orig);

        T classConvert = null;
        Class<?> origem = null;

        if (objOrigem == null) {
            logger.info("Não foi possível converter via reflection as classes DTO e Model. " + "Objeto Origem nulo. ");
            return null;
        }

        if (objOrigem.getClass().equals(Class.class)) {
            origem = (Class<?>) objOrigem;
        } else {
            origem = objOrigem.getClass();
        }

        if (origem.equals(dest)) {
            return (T) objOrigem;
        }

        try {

            classConvert = dest.newInstance();

            Class<?> dto = null;

            if (objOrigem.getClass().getAnnotation(Dto.class) != null && dest.getAnnotation(Dto.class) == null) {
                dto = objOrigem.getClass();
            } else {
                dto = dest;
            }
            Field[] fields = getAllFields(dto);

            converterField(objOrigem, dest, classConvert, dto, origem, fields);

        } catch (Exception ex) {
            logger.info(
                    "Não foi possível converter via reflection as classes DTO e Model. " + "Verifique as anotações e parametros. " + ex.getMessage());
        }
        return classConvert;
    }

    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<Field>();

        Class<?> superClazz = clazz.getSuperclass();

        if (!superClazz.equals(Object.class)) {
            fieldList.addAll(Arrays.asList(getAllFields(superClazz)));
        }

        fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));

        return fieldList.toArray(new Field[fieldList.size()]);

    }

    private static <T> void converterField(Object orig, Class<T> dest, T classConvert, Class<?> dto, Class<?> origem, Field[] fields)
            throws Exception {
        List<Field> logicField = new ArrayList<>();
        List<Field> viewField = new ArrayList<>();
        for (Field field : fields) {
            DtoField dtoField = field.getAnnotation(DtoField.class);
            DtoFieldLogic dtoFieldLogic = field.getAnnotation(DtoFieldLogic.class);
            DtoFieldView dtoFieldView = field.getAnnotation(DtoFieldView.class);
            if (dtoField != null) {
                Field fieldDestino = getFieldDestino(dto, dest, field);

                if (orig instanceof Object[]) {
                    convertFromArray(orig, classConvert, dtoField, fieldDestino);
                } else {

                    convertFromField(orig, classConvert, origem, field, dtoField, fieldDestino);
                }
            }
            if (dtoFieldLogic != null) {
                logicField.add(field);
            }
            if (dtoFieldView != null) {
                viewField.add(field);
            }
        }
        if (!logicField.isEmpty()) {
            computLogicField(logicField, dest, dto, orig, classConvert);
        }
        if (!viewField.isEmpty()) {
            computViewField(viewField, dest, dto, orig, classConvert);
        }
    }

    private static <T> void computViewField(List<Field> viewFields, Class<T> dest, Class<?> dto, Object origem, T classConvert) throws Exception {
        if (dest.equals(dto)) {
            for (Field field : viewFields) {
                field.setAccessible(true);
                DtoFieldView dtoFieldView = field.getAnnotation(DtoFieldView.class);

                Method method, method2;

                method = origem.getClass().getMethod("get" + StringUtil.primeiraMaiscula(dtoFieldView.atributo()), new Class[] {});
                Object atributoValidar = method.invoke(origem, new Object[] {});

                method2 = atributoValidar.getClass().getMethod("get" + StringUtil.primeiraMaiscula(dtoFieldView.field()), new Class[] {});
                Object valorAtributo = method2.invoke(atributoValidar, new Object[] {});

                Object valor = field.get(classConvert);

                if (valor == null) {

                    field.set(classConvert, valorAtributo);
                }
            }
        }
    }

    private static <T> void computLogicField(List<Field> logicField, Class<T> dest, Class<?> dto, Object origem, T classConvert) throws Exception {
        if (dest.equals(dto)) {
            for (Field field : logicField) {
                if (field.getType().isAssignableFrom(Boolean.class)) {
                    field.setAccessible(true);
                    DtoFieldLogic dtoFieldLogic = field.getAnnotation(DtoFieldLogic.class);

                    logicFieldValue(origem, classConvert, field, dtoFieldLogic);
                }
            }
        }
    }

    private static <T> void logicFieldValue(Object origem, T classConvert, Field field, DtoFieldLogic dtoFieldLogic)
            throws Exception, IllegalAccessException {
        if (origem instanceof Object[]) {

            Object[] org = (Object[]) origem;
            Integer columIndex = Integer.valueOf(dtoFieldLogic.atributoValidar());

            if (org.length <= columIndex) {
                throw new Exception("O objeto origem possui menos atributos que o ojeto destino! ");
            }
            Boolean valor = computLogic(org[columIndex], dtoFieldLogic.condicao(), dtoFieldLogic.valorAtributo());
            field.set(classConvert, valor);

        } else {

            Field atributoValidar = getDeclaredField(origem.getClass(), dtoFieldLogic.atributoValidar());

            atributoValidar.setAccessible(true);
            Object value = atributoValidar.get(origem);
            Boolean valor = computLogic(value, dtoFieldLogic.condicao(), dtoFieldLogic.valorAtributo());
            field.set(classConvert, valor);
        }
    }

    private static Boolean computLogic(Object value, String condicao, String valorAtributo) {

        String valorComparar = valorAtributo;
        if ("null".equals(valorAtributo.trim())) {
            valorComparar = "";
        }
        Object valor = IfNull.get(value, "");
        if (valor instanceof List<?> && ((List<?>) value).isEmpty()) {
            valor = "";
        }

        switch (condicao) {
            case "=":
                return valor.toString().trim().equals(valorComparar);
            case "!=":
                return !valor.toString().trim().equals(valorComparar);
            default:

                return false;
        }
    }

    private static <T> void convertFromField(Object orig, T classConvert, Class<?> origem, Field field, DtoField dtoField, Field fieldDestino)
            throws Exception {
        String fieldName = (origem.getAnnotation(Dto.class) != null && dtoField.atributo().isEmpty()) ? field.getName()
                : dtoField.atributo().isEmpty() ? field.getName() : dtoField.atributo();

        Field fieldOrigem = getDeclaredField(origem, fieldName);
        fieldOrigem.setAccessible(true);
        Class<?> ob = fieldOrigem.getType();

        if (ob.isAssignableFrom(List.class)) {
            convertFromList(orig, classConvert, fieldDestino, fieldOrigem);
        } else {
            convertFromObject(orig, classConvert, fieldDestino, fieldOrigem);
        }

    }

    private static Field getDeclaredField(Class<?> origem, String fieldName) {

        List<Field> fieldList = Arrays.asList(getAllFields(origem));

        List<Field> retorno = fieldList.stream().filter(w -> w.getName().equals(fieldName)).collect(Collectors.toList());
        Field fieldDeclared = null;

        if (retorno == null || retorno.isEmpty()) {
            logger.severe("Atributo não encontrado:" + fieldName + ". Verifique a classe: " + origem.getSimpleName());
        } else {
            fieldDeclared = retorno.get(0);
        }
        return fieldDeclared;
    }

    private static <T> void convertFromArray(Object orig, T classConvert, DtoField dtoField, Field fieldDestino) {

        try {
            if (dtoField.atributo().isEmpty()) {
                throw new Exception("Verifique a annotation @DtoField do campo: " + fieldDestino.getName()
                        + ". Aguardando posição do atributo ex:@DtoField(atributo='1')");
            } else if (!StringUtil.isNumeric(dtoField.atributo())) {
                throw new Exception("Verifique a annotation @DtoField do campo: " + fieldDestino.getName()
                        + ". Aguardando valor númerico no atributo ex:@DtoField(atributo='1')");
            }
            Object[] origem = (Object[]) orig;
            Integer columIndex = Integer.valueOf(dtoField.atributo());

            if (origem.length <= columIndex) {
                throw new Exception("O objeto origem possui menos atributos que o ojeto destino! ");
            }
            Object valueObj = origem[columIndex];
            if (valueObj == null) {
                if (!dtoField.ifnull().isEmpty()) {
                    valueObj = fieldDestino.getType().cast(dtoField.ifnull());
                    fieldDestino.set(classConvert, cast(valueObj, fieldDestino.getType(), dtoField));
                } else {
                    fieldDestino.set(classConvert, valueObj);
                }
            } else {
                fieldDestino.set(classConvert, cast(valueObj, fieldDestino.getType(), dtoField));
            }

        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }

    }

    private static Object cast(Object value, Class<?> type, DtoField dtoField) {

        if (type.getAnnotation(Dto.class) != null) {
            return convert(value, type);
        }

        if (type.equals(Time.class) && value.getClass().equals(String.class)) {

            return Time.valueOf(value.toString());
        }

        if (type.equals(Long.class) && value.getClass().equals(BigDecimal.class)) {
            BigDecimal b = (BigDecimal) value;
            return b.setScale(0, BigDecimal.ROUND_UP).longValueExact();
        }

        if (type.equals(Double.class) && value.getClass().equals(BigDecimal.class)) {
            BigDecimal b = (BigDecimal) value;
            return b.doubleValue();
        }

        if (type.equals(Long.class) && value.getClass().equals(Integer.class)) {

            return Long.valueOf(value.toString());
        }

        if (type.equals(Long.class) && value.getClass().equals(BigInteger.class)) {
            BigInteger b = (BigInteger) value;
            return b.longValue();
        }

        if (type.equals(String.class)) {

            if (value.getClass().equals(Date.class) && !dtoField.formatString().isEmpty()) {

                return DateUtil.formatString(value, dtoField.formatString());
            }

            if (!dtoField.charCase().equals(CharCase.DEFAULT)) {
                return charCase(value, dtoField);
            }

            if (value.getClass().equals(byte[].class)) {
                return new String((byte[]) value, StandardCharsets.UTF_8);
            }

            if (!value.getClass().equals(String.class)) {

                return value.toString();
            }
        }

        return value;
    }

    private static Object charCase(Object value, DtoField dtoField) {
        if (dtoField.charCase().equals(CharCase.UPPER_CASE)) {
            return value.toString().toUpperCase();
        } else {
            return value.toString().toLowerCase();
        }
    }

    private static Field getFieldDestino(Class<?> classDto, Class<?> classDest, Field field) throws NoSuchFieldException {
        Field fieldDestino;

        DtoField dtoField = field.getAnnotation(DtoField.class);

        String fieldName = (classDest.getAnnotation(Dto.class) != null && dtoField.atributo().isEmpty()) ? field.getName()
                : dtoField.atributo().isEmpty() ? field.getName() : dtoField.atributo();

        if (classDto.equals(classDest)) {
            fieldDestino = field;
        } else {
            fieldDestino = getDeclaredField(classDest, fieldName);
        }

        fieldDestino.setAccessible(true);

        return fieldDestino;
    }

    private static <T> void convertFromList(Object orig, T classConvert, Field fieldDestino, Field fieldOrigem)
            throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ParameterizedType fieldGenericType = (ParameterizedType) fieldDestino.getGenericType();
        Class<?> fieldType = (Class<?>) fieldGenericType.getActualTypeArguments()[0];

        Method method;

        method = orig.getClass().getMethod("get" + StringUtil.primeiraMaiscula(fieldOrigem.getName()), new Class[] {});
        Object value = method.invoke(orig, new Object[] {});

        if (value instanceof PersistentBag) {
            value = Arrays.asList(((PersistentBag) value).toArray());
        }
        List<Object> valores = valoresCampos(fieldType, (List<?>) value);
        fieldDestino.set(classConvert, valores);

    }

    private static <T> void convertFromObject(Object orig, T classConvert, Field fieldDestino, Field fieldOrigem)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Method method;
        method = orig.getClass().getMethod("get" + StringUtil.primeiraMaiscula(fieldOrigem.getName()), new Class[] {});
        Object value = method.invoke(orig, new Object[] {});

        if (value instanceof PersistentBag) {
            value = Arrays.asList(((PersistentBag) value).toArray());
        }

        DtoField dtoField = fieldDestino.getAnnotation(DtoField.class);
        if (value == null && dtoField != null && !dtoField.ifnull().isEmpty()) {
            value = fieldDestino.getType().cast(dtoField.ifnull());
        }
        if (value != null) {
            Dto destinoDto = value.getClass().getAnnotation(Dto.class);
            Dto origemDto = fieldDestino.getType().getAnnotation(Dto.class);

            if (destinoDto != null) {
                fieldDestino.set(classConvert, convert(value, fieldDestino.getType()));
            } else if (origemDto != null) {

                fieldDestino.set(classConvert, convert(value, fieldDestino.getType()));

            } else {

                if (fieldDestino.getType().equals(String.class) && fieldOrigem.getType().equals(Boolean.class)
                        && fieldOrigem.getAnnotation(ValorBooleano.class) != null) {
                    ValorBooleano valorDefault = fieldOrigem.getAnnotation(ValorBooleano.class);
                    fieldDestino.set(classConvert, value.equals(Boolean.TRUE) ? valorDefault.booleanTrue() : valorDefault.booleanFalse());
                } else

                if (fieldDestino.getType().equals(Boolean.class) && fieldOrigem.getType().equals(String.class)
                        && fieldDestino.getAnnotation(ValorBooleano.class) != null) {
                    ValorBooleano valorDefault = fieldDestino.getAnnotation(ValorBooleano.class);
                    fieldDestino.set(classConvert, value.equals(valorDefault.booleanTrue()));
                } else if (fieldDestino.getType().equals(String.class) && fieldOrigem.getType().equals(Time.class)) {

                    formatTime(classConvert, fieldDestino, fieldOrigem.get(orig));

                } else if (fieldDestino.getType().equals(String.class) && fieldOrigem.getType().equals(Date.class)) {

                    formatDate(classConvert, fieldDestino, fieldOrigem.get(orig));

                } else if (fieldDestino.getType().equals(String.class)) {

                    valueToString(classConvert, fieldDestino, fieldOrigem.get(orig));

                } else if (fieldDestino.getType().equals(byte[].class) && fieldOrigem.getType().equals(String.class)) {
                    fieldDestino.set(classConvert, fieldOrigem.get(orig).toString().getBytes(StandardCharsets.UTF_8));

                } else if (fieldDestino.getType().equals(String.class) && !fieldOrigem.getType().equals(String.class)) {

                    fieldDestino.set(classConvert, fieldOrigem.get(orig).toString());

                } else if (fieldDestino.getType().equals(Date.class) && !fieldOrigem.getType().equals(Date.class)) {

                    parseDate(orig, classConvert, fieldDestino, fieldOrigem, dtoField);

                } else if (fieldDestino.getType().equals(Time.class) && fieldOrigem.getType().equals(String.class)) {

                    parseTime(orig, classConvert, fieldDestino, fieldOrigem, dtoField);

                } else {
                    fieldDestino.set(classConvert, fieldOrigem.get(orig));
                }
            }
        }

    }

    private static <T> void parseDate(Object orig, T classConvert, Field fieldDestino, Field fieldOrigem, DtoField dtoField)
            throws IllegalAccessException {

        String format = DateUtil.FORMATO_YYYY_MM_DD_HH_MM_SS;
        if (dtoField != null && !dtoField.formatString().isEmpty()) {
            format = dtoField.formatString();
        }

        fieldDestino.set(classConvert, DateUtil.parseDate(fieldOrigem.get(orig), format));
    }

    private static <T> void parseTime(Object orig, T classConvert, Field fieldDestino, Field fieldOrigem, DtoField dtoField)
            throws IllegalAccessException {

        String format = DateUtil.FORMATO_HH_MM_SS;
        if (dtoField != null && !dtoField.formatString().isEmpty()) {
            format = dtoField.formatString();
        }

        if (format.contains("-") || format.contains("/")) {
            fieldDestino.set(classConvert, DateUtil.toTime(DateUtil.parseDate(fieldOrigem.get(orig), format)));
        } else {
            fieldDestino.set(classConvert, DateUtil.toTime(fieldOrigem.get(orig).toString(), format));
        }
    }

    private static String formatString(DtoField dtoField) {

        String formatString = DateUtil.FORMATO_YYYY_MM_DD_HH_MM_SS;
        if (!dtoField.formatString().isEmpty()) {
            formatString = dtoField.formatString();
        }
        return formatString;
    }

    private static List<Object> valoresCampos(Class<?> fieldType, List<?> value) {
        List<Object> valores = new ArrayList<>();

        ((List<?>) value).stream().filter(item -> item != null).forEach(item -> valores.add(convert(item, fieldType)));

        return valores;
    }

    private static <T> void formatTime(T classConvert, Field field, Object value) throws IllegalAccessException {

        DtoField dtoField = field.getAnnotation(DtoField.class);
        String format = DateUtil.FORMATO_HH_MM_SS;
        if (dtoField != null && !dtoField.formatString().isEmpty()) {
            format = dtoField.formatString();
        }

        field.set(classConvert, DateUtil.timeToString(value, format));
    }

    private static <T> void valueToString(T classConvert, Field field, Object value) throws IllegalAccessException {

        if (value.getClass().equals(byte[].class)) {
            field.set(classConvert, new String((byte[]) value, StandardCharsets.UTF_8));

        } else if (value instanceof String) {
            convertString(classConvert, field, value);
        } else if (!value.getClass().equals(String.class)) {
            field.set(classConvert, value.toString());
        }

    }

    private static <T> void formatDate(T classConvert, Field field, Object value) throws IllegalAccessException {
        DtoField dtoField = field.getAnnotation(DtoField.class);
        String format = DateUtil.FORMATO_YYYY_MM_DD_HH_MM_SS;
        if (dtoField != null && !dtoField.formatString().isEmpty()) {
            format = dtoField.formatString();
        }

        field.set(classConvert, DateUtil.formatString(value, format));
    }

    private static <T> void convertString(T classConvert, Field field, Object value) throws IllegalAccessException {
        DtoField dtoField = field.getAnnotation(DtoField.class);
        if (dtoField != null && !dtoField.charCase().equals(CharCase.DEFAULT)) {

            if (dtoField.charCase().equals(CharCase.UPPER_CASE)) {
                field.set(classConvert, value.toString().toUpperCase());
            } else {
                field.set(classConvert, value.toString().toLowerCase());
            }

        } else {
            field.set(classConvert, value);
        }
    }

    public static <T> String toScript(T obj) throws BusinessServerException {
        return toScript(Arrays.asList(obj));
    }

    public static <T> String toScript(List<T> obj) throws BusinessServerException {

        StringBuilder scriptInsert = new StringBuilder();
        StringBuilder scriptUpdate = new StringBuilder();
        StringBuilder insertValues = new StringBuilder();

        for (T t : obj) {

            Class<?> dtoObj = t.getClass();

            Dto dto = dtoObj.getAnnotation(Dto.class);

            if (dto == null) {
                throw new BusinessServerException(DTO_SCRIPT, "Objeto não possui a anotação @Dto. Por favor verifique.",
                        FALHA_AO_CONVERTER_DTO_PARA_SCRIPT);

            }

            if (dto.tableName().isEmpty()) {
                throw new BusinessServerException(DTO_SCRIPT, "Nome da tabela não informado. Verifique o parâmetro 'tableName' na anotação @Dto.",
                        FALHA_AO_CONVERTER_DTO_PARA_SCRIPT);
            }

            Field[] fields = getAllFields(dtoObj);

            Field fieldPk = getFieldPk(fields);

            if (fieldPk == null) {
                throw new BusinessServerException(DTO_SCRIPT,
                        "Chave primária não identificada. Adicione a anotação @DtoPk e @DtoColumn sobre a coluna chave.",
                        FALHA_AO_CONVERTER_DTO_PARA_SCRIPT);
            }

            Object valor = null;

            try {
                valor = fieldPk.get(t);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new BusinessServerException(DTO_SCRIPT, "Falha ao recuperar o valor do atributo.", FALHA_AO_CONVERTER_DTO_PARA_SCRIPT, e);
            }

            if (valor != null) {

                DtoColumn dtoColumn = fieldPk.getAnnotation(DtoColumn.class);

                scriptUpdate.append("UPDATE ").append(dto.tableName()).append(" SET ").append(getUpdateValues(t, fields)).append(" WHERE ")
                        .append(dtoColumn.name()).append("=").append(convertString(fieldPk, valor)).append(";");

            } else {

                if (scriptInsert.length() == 0) {

                    scriptInsert.append(getInsertSintax(t, dto.tableName(), fields));

                }

                if (insertValues.length() > 0) {
                    insertValues.append(",");
                }

                insertValues.append("(").append(getInsertValues(t, fields)).append(")");

            }

        }
        if (insertValues.length() > 0) {
            scriptInsert.append(insertValues).append(";");
        }

        return scriptInsert.toString().concat(scriptUpdate.toString());
    }

    private static <T> String getInsertValues(T obj, Field[] fields) throws BusinessServerException {

        StringBuilder colunas = new StringBuilder();

        for (Field field : fields) {

            DtoColumn dtoColumn = field.getAnnotation(DtoColumn.class);
            DtoPk dtoPk = field.getAnnotation(DtoPk.class);

            if (dtoColumn != null) {

                field.setAccessible(true);

                try {

                    if (colunas.length() > IntegerUtil.ZERO) {
                        colunas.append(",");
                    }

                    if (dtoPk != null && !dtoPk.generator().equals("false")) {
                        colunas.append("nextval('" + dtoPk.generator() + "')");
                    } else {

                        Object valor = field.get(obj);

                        colunas.append(convertString(field, valor));
                    }

                } catch (IllegalArgumentException | IllegalAccessException e) {

                    throw new BusinessServerException(DTO_SCRIPT, e.getMessage(), FALHA_AO_CONVERTER_DTO_PARA_SCRIPT, e);
                }
            }

        }
        return colunas.toString();
    }

    private static <T> String getUpdateValues(T obj, Field[] fields) throws BusinessServerException {

        StringBuilder colunas = new StringBuilder();

        for (Field field : fields) {

            DtoColumn dtoColumn = field.getAnnotation(DtoColumn.class);
            DtoPk dtoPk = field.getAnnotation(DtoPk.class);

            if (dtoColumn != null && dtoPk == null) {

                field.setAccessible(true);

                try {
                    Object valor = field.get(obj);

                    if (colunas.length() > IntegerUtil.ZERO) {
                        colunas.append(",");
                    }

                    colunas.append(dtoColumn.name()).append("=").append(convertString(field, valor));

                } catch (IllegalArgumentException | IllegalAccessException e) {

                    throw new BusinessServerException(DTO_SCRIPT, e.getMessage(), FALHA_AO_CONVERTER_DTO_PARA_SCRIPT, e);
                }
            }

        }
        return colunas.toString();
    }

    private static <T> String getInsertSintax(T t, String tableName, Field[] fields) {

        StringBuilder colunas = new StringBuilder();

        StringBuilder cabecalho = new StringBuilder();

        cabecalho.append("INSERT INTO ").append(tableName);

        for (Field field : fields) {

            DtoColumn dtoColumn = field.getAnnotation(DtoColumn.class);

            if (dtoColumn != null) {

                if (colunas.length() > 0) {
                    colunas.append(",");
                }

                colunas.append(dtoColumn.name());

            }

        }

        cabecalho.append("(").append(colunas).append(")").append(" VALUES ");

        return cabecalho.toString();
    }

    private static Field getFieldPk(Field[] fields) {

        Field fieldPk = null;

        for (Field field : fields) {

            DtoColumn dtoColumn = field.getAnnotation(DtoColumn.class);
            DtoPk dtoPk = field.getAnnotation(DtoPk.class);

            if (dtoColumn != null && dtoPk != null) {

                field.setAccessible(true);

                fieldPk = field;

                break;
            }

        }
        return fieldPk;
    }

    private static String convertString(Field field, Object valor) {

        if (valor != null && !valor.toString().isEmpty()) {

            if (field.getType().equals(Date.class)) {
                DtoField dtoField = field.getAnnotation(DtoField.class);

                String formato = DateUtil.FORMATO_YYYY_MM_DD;
                if (dtoField != null && !dtoField.formatString().isEmpty()) {
                    formato = dtoField.formatString();
                }

                return "'".concat(DateUtil.formatString(valor, formato)).concat("'");
            } else if (field.getType().equals(String.class)) {
                return "'".concat(valor.toString().replace("'", "''")).concat("'");
            } else {
                return valor.toString();
            }

        } else {

            DtoField dtoField = field.getAnnotation(DtoField.class);

            if (dtoField != null && !dtoField.ifnull().isEmpty()) {
                return "'".concat(dtoField.ifnull().toString()).concat("'");
            }
        }

        return "null";

    }

    public static <T> String toInsert(List<T> obj) throws BusinessServerException {

        StringBuilder scriptInsert = new StringBuilder();
        StringBuilder insertValues = new StringBuilder();

        for (T t : obj) {

            Class<?> dtoObj = t.getClass();

            Dto dto = dtoObj.getAnnotation(Dto.class);

            if (dto == null) {
                throw new BusinessServerException(DTO_SCRIPT, "Objeto não possui a anotação @Dto. Por favor verifique.",
                        FALHA_AO_CONVERTER_DTO_PARA_SCRIPT);
            }

            if (dto.tableName().isEmpty()) {
                throw new BusinessServerException(DTO_SCRIPT, "Nome da tabela não informado. Verifique o parâmetro 'tableName' na anotação @Dto.",
                        FALHA_AO_CONVERTER_DTO_PARA_SCRIPT);
            }

            Field[] fields = getAllFields(dtoObj);

            Field fieldPk = getFieldPk(fields);

            if (fieldPk == null) {
                throw new BusinessServerException(DTO_SCRIPT,
                        "Chave primária não identificada. Adicione a anotação @DtoPk e @DtoColumn sobre a coluna chave.",
                        FALHA_AO_CONVERTER_DTO_PARA_SCRIPT);
            }

            if (scriptInsert.length() == 0) {
                scriptInsert.append(getInsertSintax(t, dto.tableName(), fields));
            }

            if (insertValues.length() > 0) {
                insertValues.append(",");
            }

            insertValues.append("(").append(getInsertValues(t, fields)).append(")");

        }

        if (insertValues.length() > 0) {
            scriptInsert.append(insertValues).append(";");
        }

        return scriptInsert.toString();
    }

}