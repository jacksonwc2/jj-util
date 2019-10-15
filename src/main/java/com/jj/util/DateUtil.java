package com.jj.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

/**
 * Classe responsável por possuir utilitários de validação de data.
 */
@SuppressWarnings(value = "all")
public final class DateUtil {

    private static final String SABADO = "Sábado";
    private static final String SEXTA_FEIRA = "Sexta-feira";
    private static final String QUINTA_FEIRA = "Quinta-feira";
    private static final String QUARTA_FEIRA = "Quarta-feira";
    private static final String TERCA_FEIRA = "Terça-feira";
    private static final String SEGUNDA_FEIRA = "Segunda-feira";
    private static final String DOMINGO = "Domingo";
    private static final String SEPARADOR_BARRA = "/";
    private static final String ANO_PADRAO = "1899";
    private static final String PRIMEIRO_DIA = "01";
    public static final String FINAL_DIA = "23:59:00";
    public static final String INICIO_DIA = "00:00:00";
    public static final String PRIMEIRA_DATA_ETL = "31/12/1899 23:59:59";
    public static final String PRIMEIRA_DATA_BANCO = "1899-12-31 23:59:59";
    public static final String FORMATO_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
    public static final String FORMATO_DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
    public static final String FORMATO_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMATO_YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMATO_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMATO_DD_MM_YYYY_HH_MM_SS_SSS = "dd/MM/yyyy HH:mm:ss.SSS";
    public static final String FORMATO_HH = "HH";

    public static final String FORMATO_HH_MM = "HH:mm";

    public static final String FORMATO_HH_MM_SS = "HH:mm:ss";

    public static final String FORMATO_HH_MM_SS_SSS = "HH:mm:ss:SSS";

    public static final String FORMATO_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMATO_DD_MM_YY_NEW = "dd-MM-yy";
    public static final String FORMATO_DD_MM_YYYY = "dd/MM/yyyy";
    public static final String FORMATO_DD_MM_YY = "dd/MM/yy";
    public static final String FORMATO_DD_MM = "dd/MM";
    public static final String FORMATO_MM_YYYY = "MM/yyyy";
    public static final String FORMATO_DD_MM_YYYY_AS_HH_MM_SS = "dd/MM/yyyy 'às' HH:mm:ss";
    public static final String FORMATO_DD_MM_YYYY_HH_MM_24H = "dd/MM/yyyy HH:mm";

    public static final String FORMATO_DIA_SEMANA_EXTENSO = "EEEEE";
    public static final String FORMATO_DIA_SEMANA_ABREVIADO = "E";
    public static final String FORMATO_DIA_MES_ABREVIADO_ANO = "dd/MMM/yyyy";
    public static final String FORMATO_DATE_MANUTENCAO = "ddMMyyHHmm";

    private static final String DW = "DW";
    public static final Long ZERO = 0L;
    public static final Long UM = 1L;
    public static final Long UMA_SEMANA = 7L;

    private static final int MONDAY = 1;
    private static final int TUESDAY = 2;
    private static final int WEDNESDAY = 3;
    private static final int THURSDAY = 4;
    private static final int FRIDAY = 5;
    private static final int SATURDAY = 6;
    private static final int SUNDAY = 7;

    private static final int JANEIRO = 1;
    private static final int FEVEREIRO = 2;
    private static final int MARCO = 3;
    private static final int ABRIL = 4;
    private static final int MAIO = 5;
    private static final int JUNHO = 6;
    private static final int JULHO = 7;
    private static final int AGOSTO = 8;
    private static final int SETEMBRO = 9;
    private static final int OUTUBRO = 10;
    private static final int NOVEMBRO = 11;
    private static final int DEZEMBRO = 12;

    /**
     * Formatos utilizados em selects
     * 
     * Utilizados com o queryDslTemplate.toChar()
     */
    public static final String FORMATO_HH24_MM = "HH24:mm";
    public static final String FORMATO_HH_MI = "HH:mi";
    public static final String FORMATO_HH24_MI = "HH24:mi";
    public static final String FORMATO_HH24_MM_SS = "HH24:mm:ss";
    public static final String FORMATO_HH_MI_SS = "HH:mi:ss";
    public static final String FORMATO_HH24_MI_SS = "HH24:mi:ss";
    public static final String FORMATO_HH24_MM_SS_SSS = "HH24:mm:ss:SSS";
    public static final String FORMATO_HH_MI_SS_SSS = "HH:mi:ss:SSS";
    public static final String FORMATO_HH24_MI_SS_SSS = "HH24:mi:ss:SSS";
    public static final String FORMATO_DD_MM_YYYY_HH_MM_24 = "dd/MM/yyyy hh24:mi";

    private static final ZoneId FUSO_HORARIO = ZoneId.systemDefault();

    private static Logger logger = Logger.getLogger(DateUtil.class.getName());

    private DateUtil() {
        // Garante que a classe não seja instanciada.
    }

    /**
     * Adiciona/Remove dias da data atual
     * 
     * @param dias
     * @return
     */
    public static Date addDays(Long dias) {
        return addDays(null, dias);
    }

    /**
     * Adiciona/Remove meses em uma data
     *
     * @param data campo data informado, dias quantidade de dias para adicionar
     * @return Retorna <b>Date</b> Obs.: Caso da data informada for nula, ele receberá a data atual da aplicação.
     */
    public static Date addMonth(Date date, Long meses) {
        Date data = date;
        if (data == null) {
            data = getDateTimeNow();
        }
        LocalDateTime currentDateTime = dateToLocalDateTime(data);
        if (meses > ZERO) {
            return Date.from(currentDateTime.plusMonths(meses).atZone(FUSO_HORARIO).toInstant());
        } else {
            return Date.from(currentDateTime.minusMonths(meses * -1).atZone(FUSO_HORARIO).toInstant());
        }
    }

    /**
     * Adiciona/Remove dias em uma data
     *
     * @param data campo data informado, dias quantidade de dias para adicionar
     * @return Retorna <b>Date</b> Obs.: Caso da data informada for nula, ele receberá a data atual da aplicação.
     */
    public static Date addDays(Date date, Long dias) {

        Date data = sqlDateToDate(date);

        if (data == null) {
            data = getDateTimeNow();
        }
        if (dias > ZERO) {
            return Date.from(data.toInstant().plus(Duration.ofDays(dias)));
        } else {
            // Multiplica dias por -1 para que o valor seja positivo, quando
            // negativo estava retornando uma data errada.
            return Date.from(data.toInstant().minus(Duration.ofDays(dias * -1)));
        }
    }

    /**
     * Adiciona/Remove anos em uma data
     *
     * @param data campo data informado, dias quantidade de dias para adicionar
     * @return Retorna <b>Date</b> Obs.: Caso da data informada for nula, ele receberá a data atual da aplicação.
     */
    public static Date addYears(Date date, Long years) {

        Date data = date;
        if (data == null) {
            data = getDateTimeNow();
        }
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        if (years > ZERO) {
            return Date.from(currentDateTime.plusYears(years).atZone(FUSO_HORARIO).toInstant());
        } else {
            return Date.from(currentDateTime.minusYears(years * -1).atZone(FUSO_HORARIO).toInstant());
        }
    }

    /**
     * Acrecenta horas em uma data
     *
     * @param data campo data informado, dias quantidade de horas para adicionar
     * @return Retorna <b>Date</b> Obs.: Caso da data informada for nula, ele receberá a data atual da aplicação.
     */
    public static Date addHours(Date date, Long horas) {

        Date data = date;

        if (data == null) {
            data = getDateTimeNow();
        }
        return Date.from(data.toInstant().plus(Duration.ofHours(horas)));
    }

    /**
     * Ultimo dia do mês
     * 
     * @param data
     * @return Date
     */
    public static Date lastDayOfMonth(Date data) {
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        return Date.from(currentDateTime.with(TemporalAdjusters.lastDayOfMonth()).atZone(FUSO_HORARIO).toInstant());
    }

    /**
     * Ultimo dia do ano
     * 
     * @param data
     * @return Date
     */
    public static Date lastDayOfYear(Date data) {
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        return Date.from(currentDateTime.with(TemporalAdjusters.lastDayOfYear()).atZone(FUSO_HORARIO).toInstant());
    }

    /**
     * Primeiro dia do mês
     * 
     * @param data
     * @return Date
     */
    public static Date firstDayOfMonth(Date data) {

        Date dateValue = sqlDateToDate(data);

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(dateValue.toInstant(), FUSO_HORARIO);
        return Date.from(currentDateTime.with(TemporalAdjusters.firstDayOfMonth()).atZone(FUSO_HORARIO).toInstant());
    }

    /**
     * Retorna a data para o mes passado por parametro ex: 03/01/2018 para o mes 2(fevereiro) => 03/02/2018
     * 
     * @param date
     * @param mes
     * @return
     */

    public static Date getDateOfMonth(Date date, Long mes) {

        return Date.from(LocalDateTime.ofInstant(sqlDateToDate(date).toInstant(), FUSO_HORARIO).toLocalDate().withMonth(mes.intValue()).atStartOfDay()
                .atZone(FUSO_HORARIO).toInstant());
    }

    /**
     * O tipo date quando retornado através de uma consulta pelo querydsl é um java.sql.Date, <br>
     * este formato de data extende do java util, porem é nessario realizar a conversao para utilizar funções da função toInstant();
     * 
     * @param data
     * @return
     */
    private static Date sqlDateToDate(Date data) {

        Date dateValue = null;
        if (data != null && data instanceof java.sql.Date) {
            dateValue = new Date(data.getTime());
        } else {
            dateValue = data;
        }
        return dateValue;
    }

    /**
     * Primeiro dia do ano
     * 
     * @param data
     * @return Date
     */
    public static Date firstDayOfYear(Date data) {
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        return Date.from(currentDateTime.with(TemporalAdjusters.firstDayOfYear()).atZone(FUSO_HORARIO).toInstant());
    }

    /**
     * Incio Dia data 00:00:00
     * 
     * @param data
     * @return Date
     */
    public static Date initialDate(Date data) {
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        return Date.from(currentDateTime.withHour(IntegerUtil.ZERO).withMinute(IntegerUtil.ZERO).withSecond(IntegerUtil.ZERO)
                .withNano(IntegerUtil.ZERO).atZone(FUSO_HORARIO).toInstant());
    }

    /**
     * Fim Dia data 23:59:59
     * 
     * @param data
     * @return Date
     */
    public static Date finalDate(Date data) {
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        return Date.from(currentDateTime.withHour(IntegerUtil.VINTE_TRES).withMinute(IntegerUtil.CINQUENTA_NOVE)
                .withSecond(IntegerUtil.CINQUENTA_NOVE).atZone(FUSO_HORARIO).toInstant());
    }

    /**
     * Incio Dia data 00:00:00
     * 
     * @param String
     * @return String com final 00:00:00
     */
    public static String initialDate(String data) {
        return formatDateTimeForDB(initialDate(parseDate(data)));
    }

    /**
     * Fim Dia data 23:59:59
     * 
     * @param String
     * @return String com final 23:59:59
     */
    public static String finalDate(String data) {
        return formatDateTimeForDB(finalDate(parseDate(data)));
    }

    /**
     * Verifica se a data esta no mês corrente?
     * 
     * @param data
     * @return true/false
     */
    public static Boolean isActualMonth(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        LocalDateTime nowDate = LocalDateTime.ofInstant(getDateTimeNow().toInstant(), FUSO_HORARIO);
        return existeDiferenca(ChronoUnit.MONTHS.between(parameterDate.toLocalDate(), nowDate.toLocalDate()));
    }

    /**
     * Verifica se a data esta na semana corrente?
     * 
     * @param data
     * @return true/false
     */
    public static Boolean isActualWeek(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        LocalDateTime nowDate = LocalDateTime.ofInstant(getDateTimeNow().toInstant(), FUSO_HORARIO);
        return existeDiferenca(ChronoUnit.WEEKS.between(parameterDate.toLocalDate(), nowDate.toLocalDate()));
    }

    /**
     * Verifica se a data esta no dia corrente?
     * 
     * @param data
     * @return true/false
     */
    public static Boolean isActualDay(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        LocalDateTime nowDate = LocalDateTime.ofInstant(getDateTimeNow().toInstant(), FUSO_HORARIO);
        return existeDiferenca(ChronoUnit.DAYS.between(parameterDate.toLocalDate(), nowDate.toLocalDate()));
    }

    /**
     * Comparar duas datas para ver se esta no mesmo dia do mesmo mês do mesmo ANO.
     * 
     * @param dataFromdataFromBase
     * @param comparation
     * @return true/false
     */
    public static Boolean isDayEquals(Date dataFromdataFromBase, Date comparation) {
        LocalDateTime dataFromBase = LocalDateTime.ofInstant(dataFromdataFromBase.toInstant(), FUSO_HORARIO);
        LocalDateTime dataCompar = LocalDateTime.ofInstant(comparation.toInstant(), FUSO_HORARIO);
        return existeDiferenca(ChronoUnit.DAYS.between(dataFromBase.toLocalDate(), dataCompar.toLocalDate()));
    }

    /**
     * Comparar duas datas para ver se esta na mesma semana
     * 
     * @param dataFromdataFromBase
     * @param comparation
     * @return true/false
     */
    public static Boolean isWeekEquals(Date dataFromdataFromBase, Date comparation) {
        LocalDateTime dataFromBase = LocalDateTime.ofInstant(initialDateOfWeek(dataFromdataFromBase).toInstant(), FUSO_HORARIO);
        LocalDateTime dataCompar = LocalDateTime.ofInstant(initialDateOfWeek(comparation).toInstant(), FUSO_HORARIO);
        return existeDiferenca(ChronoUnit.WEEKS.between(dataFromBase.toLocalDate(), dataCompar.toLocalDate()));
    }

    /**
     * Comparar duas datas para ver se esta no mesmo mês.
     * 
     * @param dataFromdataFromBase
     * @param comparation
     * @return true/false
     */
    public static Boolean isMonthEquals(Date dataFromdataFromBase, Date comparation) {
        LocalDateTime dataFromBase = LocalDateTime.ofInstant(firstDayOfMonth(dataFromdataFromBase).toInstant(), FUSO_HORARIO);
        LocalDateTime dataCompar = LocalDateTime.ofInstant(firstDayOfMonth(comparation).toInstant(), FUSO_HORARIO);
        return existeDiferenca(ChronoUnit.MONTHS.between(dataFromBase.toLocalDate(), dataCompar.toLocalDate()));
    }

    /**
     * Retornar hora de uma data
     * 
     * @param data
     * @return Integer - Hora
     */
    public static Integer getHour(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(sqlDateToDate(data).toInstant(), FUSO_HORARIO);
        return parameterDate.getHour();
    }

    public static Integer getMinutes(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(sqlDateToDate(data).toInstant(), FUSO_HORARIO);
        return parameterDate.getMinute();
    }

    public static Integer getSeconds(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(sqlDateToDate(data).toInstant(), FUSO_HORARIO);
        return parameterDate.getSecond();
    }

    /**
     * Retornar o ano de uma data
     * 
     * @param data
     * @return Integer - Ano
     */
    public static Integer getYear(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(sqlDateToDate(data).toInstant(), FUSO_HORARIO);
        return parameterDate.getYear();
    }

    /**
     * Retornar o mes de uma data
     * 
     * @param data
     * @return Integer - mes
     */
    public static Integer getMonth(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(sqlDateToDate(data).toInstant(), FUSO_HORARIO);
        return parameterDate.getMonthValue();
    }

    /**
     * Retornar o dia de uma data
     * 
     * @param data
     * @return Ineteger - Dia
     */
    public static Integer getDayOfMonth(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(sqlDateToDate(data).toInstant(), FUSO_HORARIO);
        return parameterDate.getDayOfMonth();
    }

    /**
     * Retornar o dia da semana para a data informada
     * 
     * @param data
     * @return Integer - Dia da semana
     */
    public static Integer getDayOfWeek(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(sqlDateToDate(data).toInstant(), FUSO_HORARIO);
        return parameterDate.getDayOfWeek().getValue();
    }

    /**
     * Formatar datas
     * 
     * @param data
     * @return String 25/03/1988
     */
    public static String formatDate(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(sqlDateToDate(data).toInstant(), FUSO_HORARIO);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return parameterDate.format(formatador);
    }

    /**
     * Formatar datas
     * 
     * @param data
     * @return String 2015-05-01
     */
    public static String formatDateForDB(Date data) {

        LocalDate parameterDate = Instant.ofEpochMilli(sqlDateToDate(data).getTime()).atZone(FUSO_HORARIO).toLocalDate();

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern(FORMATO_YYYY_MM_DD);
        return parameterDate.format(formatador);
    }

    /**
     * Formatar date time
     * 
     * @param data
     * @return String yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateTimeForDB(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(sqlDateToDate(data).toInstant(), FUSO_HORARIO);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern(FORMATO_YYYY_MM_DD_HH_MM_SS);
        return parameterDate.format(formatador);
    }

    /**
     * Formatar horas
     * 
     * @param data
     * @return String 13
     */
    public static String formatHour(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(sqlDateToDate(data).toInstant(), FUSO_HORARIO);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH");
        if ("23".equals(parameterDate.format(formatador))) {
            return parameterDate.format(formatador) + ":59";
        }
        return parameterDate.format(formatador);
    }

    /**
     * Adiciona ou remove minutos de uma data
     *
     * @param Data campo data informado, quantidade de minutos
     */
    public static Date getDateTimeNowMinutes(Long minutos) {

        if (minutos == null) {
            return getDateTimeNow();
        }
        return Date.from(getDateTimeNow().toInstant().plus(Duration.ofMinutes(minutos)));
    }

    /**
     * Obtem a data de agora
     * 
     * @return Date
     */
    public static Date getDateTimeNow() {
        return Date.from(obterInstante());
    }

    /**
     * Obtem Hora do instante
     * 
     * @return Instant
     */
    private static Instant obterInstante() {
        return Instant.now();
    }

    /**
     * Obtem um Date tipo SQL
     * 
     * @return java.sql.Date
     */
    public static java.sql.Date getSQLDateTimeNow() {
        return (java.sql.Date) java.sql.Date.from(obterInstante());
    }

    /**
     * Verifica se o objeto é uma data
     * 
     * @param valor
     * @return true/false
     */
    public static Boolean isDate(Object valor) {

        if (valor != null) {
            String stringDate = valor.toString();

            if (valor instanceof Date) {
                return true;
            }

            DateFormat formatter = new SimpleDateFormat(FORMATO_YYYY_MM_DD);
            formatter.setTimeZone(TimeZone.getTimeZone(FUSO_HORARIO));
            formatter.setLenient(false);
            try {
                formatter.parse(stringDate);
                return true;
            } catch (ParseException e) {
                logger.fine("O valor não é uma data " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    /**
     * Cast de um Long para uma Data
     * 
     * @param valor
     * @return Date
     */
    public static Date parseDate(Long valor) {
        return new Date(valor);
    }

    /**
     * Converte um valor Object para Data
     * 
     * @param valor
     * @return Date
     */
    public static Date parseDate(Object valor) {
        return parseDate(valor, FORMATO_YYYY_MM_DD);
    }

    /**
     * Converte um valor Object para Data com determinado formato
     * 
     * @param valor
     * @param format
     * @return Date
     */
    public static Date parseDate(Object valor, String format) {
        Date date = null;
        if (StringUtil.isNullOrEmpty(format)) {
            logger.severe("O formato de data é um parâmetro obrigatório no método parseDate(Object valor, String format). Por favor verifique.");
            return date;
        }

        if (valor != null && !"".equals(valor.toString())) {
            String stringDate = valor.toString();
            String formatoDestino = format;

            if (valor instanceof Date) {
                return (Date) valor;
            }
            if (valor instanceof String && formatoDestino.contains(":") && !stringDate.contains(":")) {
                stringDate += " 00:00:00";
            }
            if (valor instanceof String && stringDate.contains(":") && !formatoDestino.contains(":")) {
                formatoDestino += " HH:mm:ss";
            }

            if (formatoDestino.contains(":")) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatoDestino);

                if (formatoDestino.equals(FORMATO_YYYY_MM_DD_T_HH_MM_SS)) {
                    formatter = DateTimeFormatter.ISO_DATE_TIME;
                }

                LocalDateTime dateTime = LocalDateTime.parse(stringDate, formatter);

                return Date.from(dateTime.atZone(FUSO_HORARIO).toInstant());

            } else {

                if (formatoDestino.equals(FORMATO_MM_YYYY)) {

                    String[] valores = stringDate.split(SEPARADOR_BARRA, 2);

                    if (Integer.valueOf(valores[0]) < IntegerUtil.DEZ && valores[0].length() == IntegerUtil.UM) {
                        stringDate = "0".concat(valores[0]).concat(SEPARADOR_BARRA).concat(valores[1]);
                    }

                    formatoDestino = FORMATO_DD_MM_YYYY;
                    stringDate = PRIMEIRO_DIA.concat(SEPARADOR_BARRA).concat(stringDate);
                }

                if (formatoDestino.equals(FORMATO_DD_MM)) {

                    String[] valores = stringDate.split(SEPARADOR_BARRA, 2);

                    if (Integer.valueOf(valores[0]) < IntegerUtil.DEZ && valores[0].length() == IntegerUtil.UM) {
                        valores[0] = "0".concat(valores[0]);
                    }

                    if (Integer.valueOf(valores[1]) < IntegerUtil.DEZ && valores[1].length() == IntegerUtil.UM) {
                        valores[1] = "0".concat(valores[1]);
                    }

                    stringDate = valores[0].concat(SEPARADOR_BARRA).concat(valores[1]);

                    formatoDestino = FORMATO_DD_MM_YYYY;
                    stringDate = stringDate.concat(SEPARADOR_BARRA).concat(ANO_PADRAO);
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatoDestino);

                LocalDate dateTime = LocalDate.parse(stringDate, formatter);

                return Date.from(dateTime.atStartOfDay().atZone(FUSO_HORARIO).toInstant());

            }

        }

        return date;
    }

    /**
     * Formata a Data atual para um formato específico
     * 
     * @param date
     * @param format
     * @return Date
     */
    public static Date formatDate(Date date, String format) {
        Date retorno = null;

        if (date != null) {

            if (format.contains(":")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

                LocalDateTime parameterDate = LocalDateTime.ofInstant(date.toInstant(), FUSO_HORARIO);

                LocalDateTime dateTime = LocalDateTime.parse(parameterDate.format(formatter), formatter);

                retorno = Date.from(dateTime.atZone(FUSO_HORARIO).toInstant());

            } else {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

                LocalDate localDateOrigem = Instant.ofEpochMilli(date.getTime()).atZone(FUSO_HORARIO).toLocalDate();

                LocalDate localDate = LocalDate.parse(localDateOrigem.format(formatter), formatter);

                retorno = Date.from(localDate.atStartOfDay().atZone(FUSO_HORARIO).toInstant());

            }

        }

        return retorno;
    }

    /**
     * Formata objeto para String
     * 
     * @param valor
     * @param format
     * @return String
     */
    public static String formatString(Object valor, String format) {

        if (valor == null) {
            return null;
        }
        Date data = null;

        if (valor instanceof Time) {

            return valor.toString();

        }
        if (valor instanceof Date) {

            data = (Date) valor;

            if (format.equals(DW)) {
                return dayOfWeekName(data);
            }

        }

        if (valor instanceof Long) {

            data = parseDate((Long) valor);

        }

        if (data != null) {

            if (format.contains(":") || format.equals(FORMATO_HH)) {

                return formatStringDateTime(data, format);

            } else {

                return formatStringDate(data, format);

            }
        }

        return valor.toString();
    }

    public static String formatStringDateTime(Date data, String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        LocalDateTime parameterDate = LocalDateTime.ofInstant(sqlDateToDate(data).toInstant(), FUSO_HORARIO);

        return parameterDate.format(formatter);
    }

    private static String formatStringDate(Date data, String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        LocalDate localDateOrigem = Instant.ofEpochMilli(data.getTime()).atZone(FUSO_HORARIO).toLocalDate();

        return localDateOrigem.format(formatter);
    }

    /*
     * Retorna o dia da semana como Inteiro 1 á 7
     */
    public static Integer dayOfWeekNumber(Date date) {
        return LocalDateTime.ofInstant(sqlDateToDate(date).toInstant(), FUSO_HORARIO).getDayOfWeek().getValue();
    }

    /*
     * Retorna o dia do mês
     */
    public static Integer dayOfMonthNumber(Date date) {
        return LocalDateTime.ofInstant(sqlDateToDate(date).toInstant(), FUSO_HORARIO).getDayOfMonth();
    }

    /**
     * Recupera o dia correspondente do dia da semana
     * 
     * @param day
     * @return String
     */
    public static String dayNameOfWeek(Integer day) {
        switch (day) {
            case SUNDAY:
                return DOMINGO;
            case MONDAY:
                return SEGUNDA_FEIRA;
            case TUESDAY:
                return TERCA_FEIRA;
            case WEDNESDAY:
                return QUARTA_FEIRA;
            case THURSDAY:
                return QUINTA_FEIRA;
            case FRIDAY:
                return SEXTA_FEIRA;
            case SATURDAY:
                return SABADO;
            default:
                return "";
        }
    }

    /**
     * Recupera o dia correspondente do dia da semana
     * 
     * @param day
     * @return ChaveValoVO
     */
    public static ChaveValorVO dayNameOfWeek(Long day) {
        switch (day.intValue()) {
            case SUNDAY:
                return new ChaveValorVO(DOMINGO, LongUtil.UM);
            case MONDAY:
                return new ChaveValorVO(SEGUNDA_FEIRA, LongUtil.DOIS);
            case TUESDAY:
                return new ChaveValorVO(TERCA_FEIRA, LongUtil.TRES);
            case WEDNESDAY:
                return new ChaveValorVO(QUARTA_FEIRA, LongUtil.QUATRO);
            case THURSDAY:
                return new ChaveValorVO(QUINTA_FEIRA, LongUtil.CINCO);
            case FRIDAY:
                return new ChaveValorVO(SEXTA_FEIRA, LongUtil.SEIS);
            case SATURDAY:
                return new ChaveValorVO(SABADO, LongUtil.SETE);
            default:
                return new ChaveValorVO("", LongUtil.ZERO);

        }
    }

    private static String dayOfWeekName(Date date) {
        return dayNameOfWeek(dayOfWeekNumber(date));
    }

    /**
     * Retorna o dia da semana abreviado - Geralmente para mobile
     * 
     * @param day
     * @return String
     */
    public static String dayNameOfWeekAbbreviated(int day) {
        switch (day) {
            case SUNDAY:
                return "Dom";
            case MONDAY:
                return "Seg";
            case TUESDAY:
                return "Ter";
            case WEDNESDAY:
                return "Qua";
            case THURSDAY:
                return "Qui";
            case FRIDAY:
                return "Sex";
            case SATURDAY:
                return "Sáb";
            default:
                return "";
        }
    }

    /**
     * Nome do mês abreviado - Geralmente para Mobile
     * 
     * @return
     */
    public static Map<Integer, String> nameOfMonthAbbreviated() {

        Map<Integer, String> meses = new HashMap<Integer, String>();
        meses.put(JANEIRO, "JAN");
        meses.put(FEVEREIRO, "FEV");
        meses.put(MARCO, "MAR");
        meses.put(ABRIL, "ABR");
        meses.put(MAIO, "MAIO");
        meses.put(JUNHO, "JUN");
        meses.put(JULHO, "JUL");
        meses.put(AGOSTO, "AGO");
        meses.put(SETEMBRO, "SET");
        meses.put(OUTUBRO, "OUT");
        meses.put(NOVEMBRO, "NOV");
        meses.put(DEZEMBRO, "DEZ");
        return meses;

    }

    /**
     * Considera o domingo como primeiro dia da semana = Segunda - 1 dia.
     * 
     * @param data
     * @return Date
     */
    public static Date initialDateOfWeek(Date data) {
        Long dayOfWeek = Long.valueOf(getDayOfWeek(data)) - IntegerUtil.UM;
        return DateUtil.addDays(data, -dayOfWeek);
    }

    /**
     * Considera a segunda como primeiro dia da semana.
     * 
     * @param data
     * @return Date
     */
    public static Date initialDateOfWeekSegunda(Date data) {
        Long dayOfWeek = Long.valueOf(getDayOfWeek(data));
        return DateUtil.addDays(data, -dayOfWeek + LongUtil.UM);
    }

    /**
     * Ultima data da semana
     * 
     * @param data
     * @return Date
     */
    public static Date lastDateOfWeek(Date data) {
        return DateUtil.addDays(initialDateOfWeek(data), LongUtil.SEIS);
    }

    /**
     * Intervalo de dias entre duas datas
     * 
     * @param Date dataStart, Date dataFinish
     * @return Long
     */
    public static Long intervaloDiasEntreDatas(Date dataStart, Date dataFinish) {
        try {
            return ChronoUnit.DAYS.between(LocalDate.parse(formatString(dataStart, FORMATO_YYYY_MM_DD)),
                    LocalDate.parse(formatString(dataFinish, FORMATO_YYYY_MM_DD)));

        } catch (Exception e) {
            return 0L;
        }

    }

    /**
     * Intervalo de meses entre duas datas
     * 
     * @param Date dataStart, Date dataFinish
     * @return Long
     */
    public static Long intervaloMesesEntreDatas(Date dataStart, Date dataFinish) {
        try {
            return Period.between(LocalDate.parse(formatString(dataStart, FORMATO_YYYY_MM_DD)),
                    LocalDate.parse(formatString(dataFinish, FORMATO_YYYY_MM_DD))).toTotalMonths();

        } catch (Exception e) {
            return LongUtil.ZERO;
        }
    }

    /**
     * Intervalo de horas entre duas datas
     * 
     * @param Date dataStart, Date dataFinish
     * @return Long
     */
    public static Long intervaloHorasEntreDatas(Date dataStart, Date dataFinish) {
        try {

            long diff = dataFinish.getTime() - dataStart.getTime();
            return diff / (IntegerUtil.SESSENTA * IntegerUtil.SESSENTA * IntegerUtil.MIL);

        } catch (Exception e) {
            return LongUtil.ZERO;
        }
    }

    /**
     * Intervalo de minutos entre duas datas
     * 
     * @param Date dataStart, Date dataFinish
     * @return Long
     */
    public static Long intervaloMinutosEntreDatas(Date dataStart, Date dataFinish) {
        try {
            long diff = dataFinish.getTime() - dataStart.getTime();
            return diff / (IntegerUtil.SESSENTA * IntegerUtil.MIL);
        } catch (Exception e) {
            return LongUtil.ZERO;
        }
    }

    /**
     * Intervalo de segundos entre duas datas
     * 
     * @param Date dataStart, Date dataFinish
     * @return Long
     */
    public static Long intervaloSegundosEntreDatas(Date dataStart, Date dataFinish) {
        try {
            long diff = dataFinish.getTime() - dataStart.getTime();
            return diff / IntegerUtil.MIL;
        } catch (Exception e) {
            return LongUtil.ZERO;
        }
    }

    /**
     * Retorna o número do dia da semana conforme a descrição do dia
     * 
     * @param identificador
     * @return Integer dia 0 - 7
     */
    public static Integer dayOfWeekNumber(String identificador) {
        switch (identificador) {
            case DOMINGO:
            case "Dom":
                return IntegerUtil.SETE;
            case SEGUNDA_FEIRA:
            case "Seg":
                return IntegerUtil.UM;
            case TERCA_FEIRA:
            case "Ter":
                return IntegerUtil.DOIS;
            case QUARTA_FEIRA:
            case "Qua":
                return IntegerUtil.TRES;
            case QUINTA_FEIRA:
            case "Qui":
                return IntegerUtil.QUATRO;
            case SEXTA_FEIRA:
            case "Sex":
                return IntegerUtil.CINCO;
            case SABADO:
            case "Sáb":
                return IntegerUtil.SEIS;
            default:
                return IntegerUtil.ZERO;
        }
    }

    /**
     * Verifica diferente entre datas
     * 
     * @param diferenca
     * @return
     */
    private static Boolean existeDiferenca(Long diferenca) {
        return diferenca == ZERO;
    }

    /**
     * Adiciona minutos a uma data existente
     * 
     * @param date
     * @param minutos
     * @return Date + minutos informados
     */
    public static Date addMinutes(Date date, Long minutos) {
        Date data = sqlDateToDate(date);

        if (data == null) {
            data = getDateTimeNow();
        }
        return Date.from(data.toInstant().plus(Duration.ofMinutes(minutos)));
    }

    /**
     * Adiciona segundos a uma data existente
     * 
     * @param date
     * @param minutos
     * @return Date + segundos informados
     */
    public static Date addSeconds(Date date, Long seconds) {
        Date data = sqlDateToDate(date);

        if (data == null) {
            data = getDateTimeNow();
        }
        return Date.from(data.toInstant().plus(Duration.ofSeconds(seconds)));
    }

    /**
     * Verifica no intervalo de datas quantos dias o dia da semana especifico existe.
     * 
     * @param dataStart
     * @param dataFinish
     * @param dayOfWeek
     * @return Long
     */
    public static Long adquirirNumeroDias(Date dataStart, Date dataFinish, DayOfWeek dayOfWeek) {
        Long qtdDiaEncontrado = 0L;

        LocalDate startDate = LocalDate.parse(formatString(dataStart, FORMATO_YYYY_MM_DD));
        LocalDate endDate = LocalDate.parse(formatString(dataFinish, FORMATO_YYYY_MM_DD));
        while (!startDate.isAfter(endDate)) {
            if (startDate.getDayOfWeek().equals(dayOfWeek)) {
                qtdDiaEncontrado++;
            }
            startDate = startDate.plusDays(1);
        }

        return qtdDiaEncontrado;
    }

    /**
     * Converte o valor para o formato Time.
     * 
     * @param object
     * @return
     */
    public static Time parseTime(Object object) {
        String time = object.toString();
        while (time.length() <= 5) {
            time = time.concat(":00");
        }

        return Time.valueOf(time);

    }

    /**
     * Converte LocalDate to Date
     * 
     * @param localDate
     * @return Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(FUSO_HORARIO).toInstant();
        return Date.from(instant);
    }

    /**
     * Converte LocalDate to Date
     * 
     * @param localDate
     * @return Date
     */
    public static Date localDateToDate(LocalDate localDate, Long month) {
        Instant instant = localDate.withMonth(month.intValue()).atStartOfDay().atZone(FUSO_HORARIO).toInstant();
        return Date.from(instant);
    }

    /**
     * Converte Date to LocalDate
     * 
     * @param date
     * @return LocalDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, FUSO_HORARIO).toLocalDate();
    }

    /**
     * Converte Date to LocalDate
     * 
     * @param date
     * @return LocalDate
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = Instant.ofEpochMilli(sqlDateToDate(date).getTime());
        return LocalDateTime.ofInstant(instant, FUSO_HORARIO);
    }

    /**
     * Converte Date para Time
     * 
     * @param date
     * @return
     */
    public static Time getTime(Date date) {

        return Time.valueOf(formatString(date, "HH:mm:ss"));
    }

    /**
     * Adiciona dias na semana
     * 
     * @param date
     * @param semana
     * @return
     */
    public static Date addWeek(Date date, long semana) {
        Date data = sqlDateToDate(date);
        if (data == null) {
            data = getDateTimeNow();
        }
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        if (semana > ZERO) {
            return Date.from(currentDateTime.plusWeeks(semana).atZone(FUSO_HORARIO).toInstant());
        } else {
            return Date.from(currentDateTime.minusWeeks(semana * -1).atZone(FUSO_HORARIO).toInstant());
        }
    }

    /**
     * Retorna a diferença em minutos.
     * 
     * @param horaInicioLista
     * @param horaFimLista
     * @return
     */
    public static Long timeDiff(Time horaInicioLista, Time horaFimLista) {

        Long diff = horaInicioLista.getTime() - horaFimLista.getTime();

        diff = diff < 0 ? diff * -1 : diff;

        diff = diff > 0 ? diff / 60000 : diff;

        return diff;
    }

    /**
     * Retorna o dia do ano de 0..366
     * 
     * @param data
     * @return
     */
    public static Integer getDayOfYear(Date data) {
        LocalDateTime currentDateTime = dateToLocalDateTime(data);
        return currentDateTime.getDayOfYear();
    }

    /**
     * Retorna um Date recebendo como parâmetro o dia do mês.;
     * 
     * @param day
     * @return
     */
    public static Date getDateByDayOfMonth(Long day) {

        LocalDateTime currentDateTime = dateToLocalDateTime(getDateTimeNow());

        return Date.from(currentDateTime.withDayOfMonth(day.intValue()).atZone(FUSO_HORARIO).toInstant());

    }

    /**
     * Retorna um Date recebendo como parâmetro o dia do ano.
     * 
     * @param day
     * @return
     */
    public static Date getDateByDayOfYear(Long day) {

        LocalDateTime currentDateTime = dateToLocalDateTime(getDateTimeNow());

        return Date.from(currentDateTime.withDayOfYear(day.intValue()).atZone(FUSO_HORARIO).toInstant());

    }

    /**
     * Retorna o mês da data passada como parâmetro.
     * 
     * @param data
     * @return
     */
    public static Integer getMonthOfDate(Date data) {
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        return parameterDate.getMonthValue();
    }

    /**
     * Atribui um horário para uma data.
     * 
     * @param data
     * @param time
     * @return
     */
    public static Date setTime(Date data, Time time) {

        String[] horario = time.toString().split(":");

        Integer hora = Integer.valueOf(horario[IntegerUtil.ZERO]);
        Integer menuto = Integer.valueOf(horario[IntegerUtil.UM]);
        Integer segundo = Integer.valueOf(horario[IntegerUtil.DOIS]);

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO).withHour(hora).withMinute(menuto).withSecond(segundo);

        return Date.from(currentDateTime.atZone(FUSO_HORARIO).toInstant());
    }

    /**
     * Atribui a data o proximo dia da semana.
     * 
     * @param data
     * @param dayOfWeek
     * @return
     */
    public static Date setNextDayOfWeek(Date data, Integer dayOfWeek) {

        Date dataRetorno = data;

        Integer dayWeek = getDayOfWeek(data);

        Long dias = LongUtil.ZERO;

        if (dayWeek.equals(dayOfWeek)) {
            return data;
        }

        if (dayWeek < dayOfWeek) {

            dias = dayOfWeek.longValue() - dayWeek.longValue();

            dataRetorno = addDays(data, dias);

        } else {

            dayWeek = dayWeek - dayOfWeek;

            dataRetorno = addDays(data, -dayWeek.longValue());

            dataRetorno = addDays(dataRetorno, LongUtil.SETE);

        }

        return dataRetorno;
    }

    /**
     * Atribui um dia na data informada
     * 
     * @param data
     * @param dayOfMonth
     * @return
     */
    public static Date setDayOfMonth(Date data, Integer dayOfMonth) {

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO).withDayOfMonth(dayOfMonth);

        return Date.from(currentDateTime.atZone(FUSO_HORARIO).toInstant());
    }

    /**
     * Atribui um dia do ano;
     * 
     * @param data
     * @param dayOfYear
     * @return
     */
    public static Date setDayOfYear(Date data, Integer dayOfYear) {

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO).withDayOfYear(dayOfYear);

        return Date.from(currentDateTime.atZone(FUSO_HORARIO).toInstant());
    }

    /**
     * Converte string para Time
     * 
     * @param time format "HH:mm:ss"
     * @return
     */
    public static Time toTime(String time) {

        return toTime(time, "HH:mm:ss");

    }

    public static Time toTime(String time, String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        LocalTime dateTime = LocalTime.parse(time, formatter);

        return Time.valueOf(dateTime);

    }

    public static LocalTime toLocalTime(String time, String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        return LocalTime.parse(time, formatter);

    }

    public static LocalTime toLocalTime(String time) {

        return toLocalTime(time, "HH:mm:ss");

    }

    public static String timeToString(Object time, String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_HH_MM_SS);

        DateTimeFormatter horaSaida = DateTimeFormatter.ofPattern(format);

        LocalTime localTime = LocalTime.parse(time.toString(), formatter);

        return localTime.format(horaSaida);

    }

    public static String timeToString(Object time) {

        return timeToString(time, FORMATO_HH_MM_SS);
    }

    public static Time toTime(Date time) {
        return toTime(formatString(time, FORMATO_HH_MM_SS));
    }

    /**
     * Ultimo dia do mês
     * 
     * @param data
     * @return {@link Long}
     */
    public static Long lastDayOfTheMonth(Date data) {

        Date dateValue = sqlDateToDate(data);

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(dateValue.toInstant(), FUSO_HORARIO);

        return Long.valueOf(currentDateTime.with(TemporalAdjusters.lastDayOfMonth()).atZone(FUSO_HORARIO).getDayOfMonth());
    }

    public static Date lastHourOfDay() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalTime localTime = LocalTime.parse("23:59:59", formatter);

        Instant instant = localTime.atDate(LocalDate.now()).atZone(FUSO_HORARIO).toInstant();

        return Date.from(instant);

    }

    public static Date nextHour(Date data) {

        LocalDateTime date = dateToLocalDateTime(data).plusHours(LongUtil.UM);

        return Date.from(date.withMinute(IntegerUtil.ZERO).withSecond(IntegerUtil.ZERO).atZone(FUSO_HORARIO).toInstant());
    }

    public static Date nextDay(Date data) {

        LocalDate date = dateToLocalDate(data).plusDays(LongUtil.UM);

        return Date.from(date.atStartOfDay().atZone(FUSO_HORARIO).toInstant());

    }

    public static Date nextMonth(Date data) {

        LocalDate date = dateToLocalDate(data).plusMonths(LongUtil.UM);

        return Date.from(date.atStartOfDay().atZone(FUSO_HORARIO).toInstant());
    }

    public static Date nextYear(Date data) {

        LocalDate date = dateToLocalDate(data).plusYears(LongUtil.UM);

        return Date.from(date.atStartOfDay().atZone(FUSO_HORARIO).toInstant());
    }

    public static Long intervaloSemanas(Date dataInicial, Date dataFinal) {

        LocalDateTime inicio = LocalDateTime.ofInstant(dataInicial.toInstant(), FUSO_HORARIO);
        LocalDateTime fim = LocalDateTime.ofInstant(dataFinal.toInstant(), FUSO_HORARIO);
        return ChronoUnit.WEEKS.between(inicio.toLocalDate(), fim.toLocalDate());

    }

    public static Date nextWeek(Date data) {

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO).plusWeeks(IntegerUtil.UM);

        return Date.from(currentDateTime.atZone(FUSO_HORARIO).toInstant());
    }

    public static Integer getWeekOfMonth(Date dataInicial) {

        Integer diaMes = getDayOfMonth(dataInicial);

        Integer mod = diaMes % IntegerUtil.SETE;

        return (diaMes / IntegerUtil.SETE) + mod;
    }

    public static String nomeMesPorId(Long identificador) {
        switch (identificador.intValue()) {
            case JANEIRO:
                return "JANEIRO";
            case FEVEREIRO:
                return "FEVEREIRO";
            case MARCO:
                return "MARÇO";
            case ABRIL:
                return "ABRIL";
            case MAIO:
                return "MAIO";
            case JUNHO:
                return "JUNHO";
            case JULHO:
                return "JULHO";
            case AGOSTO:
                return "AGOSTO";
            case SETEMBRO:
                return "SETEMBRO";
            case OUTUBRO:
                return "OUTUBRO";
            case NOVEMBRO:
                return "NOVEMBRO";
            case DEZEMBRO:
                return "DEZEMBRO";
            default:
                return StringUtil.STRING_VAZIA;

        }
    }

    public static String nomeMesPorIdAbbreviated(Long identificador) {
        switch (identificador.intValue()) {
            case JANEIRO:
                return "JAN";
            case FEVEREIRO:
                return "FEV";
            case MARCO:
                return "MAR";
            case ABRIL:
                return "ABR";
            case MAIO:
                return "MAI";
            case JUNHO:
                return "JUN";
            case JULHO:
                return "JUL";
            case AGOSTO:
                return "AGO";
            case SETEMBRO:
                return "SET";
            case OUTUBRO:
                return "OUT";
            case NOVEMBRO:
                return "NOV";
            case DEZEMBRO:
                return "DEZ";
            default:
                return StringUtil.STRING_VAZIA;
        }
    }

    public static Date concatHour(Date dataAtual, String hora) {
        return parseDate(formatString(dataAtual, FORMATO_DD_MM_YYYY).concat(" ").concat(hora), FORMATO_DD_MM_YYYY_HH_MM_SS);

    }

    public static Date concatHour(Date dataAtual, String hora, String formato) {
        return parseDate(formatString(dataAtual, FORMATO_DD_MM_YYYY).concat(" ").concat(hora), formato);
    }

    public static String setDiaSemana(Long valueOf) {
        Date dataAtual = getDateTimeNow();
        for (int i = 0; i < 7; i++) {
            if (dayOfWeekNumber(dataAtual) == valueOf.intValue()) {
                return formatString(dataAtual, DateUtil.FORMATO_YYYY_MM_DD);
            }
            dataAtual = nextDay(dataAtual);
        }
        return null;
    }

    public static String setDiaSemana(Long valueOf, Date dataReferencia) {

        for (int i = 0; i < 7; i++) {
            if (dayOfWeekNumber(dataReferencia) == valueOf.intValue()) {
                return formatString(dataReferencia, DateUtil.FORMATO_YYYY_MM_DD);
            }
            dataReferencia = nextDay(dataReferencia);
        }
        return null;
    }

    public static Long strToMilli(String strTime) {
        long retVal = 0;
        String hour = strTime.substring(0, 2);
        String min = strTime.substring(3, 5);
        String sec = strTime.substring(6, 8);
        String milli = strTime.substring(9, 12);
        int h = Integer.parseInt(hour);
        int m = Integer.parseInt(min);
        int s = Integer.parseInt(sec);
        int ms = Integer.parseInt(milli);

        long lH = h * 60 * 60l * 1000;
        long lM = m * 60l * 1000;
        long lS = s * 1000l;

        retVal = lH + lM + lS + ms;
        return retVal;

    }

    /**
     * Função que converte uma data para o formato YYYYMMDD. Este formato é utilizado na execução manual do etl.
     * 
     * @param data
     * @return Long
     */
    public static Long formatLong(Date data) {

        return Long.valueOf(formatStringDate(data, FORMATO_YYYY_MM_DD).replaceAll("-", ""));
    }

    /**
     * Função que converte uma data para o formato YYYYMMDD. Este formato é utilizado na execução manual do etl.
     * 
     * @param data
     * @return Long
     */
    public static Long formatLong(LocalDate localDateInicial) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_YYYY_MM_DD);

        return Long.valueOf(localDateInicial.format(formatter).replaceAll("-", ""));

    }

    public static String converterDataBanco(String dataBr) {
        String[] arrayData = dataBr.split("/");
        if (arrayData.length != 3) {
            return null;
        }
        return arrayData[2].concat("-").concat(arrayData[1]).concat("-").concat(arrayData[0]);

    }

    public static Timestamp parseTimeStamp(String data) {

        Date dataConvertida = parseDate(data);

        Timestamp retorno = null;

        if (dataConvertida != null) {
            retorno = new Timestamp(dataConvertida.getTime());
        }

        return retorno;
    }

    public static boolean maiorOuIgual(Date date1, Date data2) {

        Date dataInicial = initialDate(date1);
        Date dataFinal = initialDate(data2);

        return dataInicial.getTime() == (dataFinal.getTime()) || dataInicial.after(dataFinal);
    }

    public static Boolean maior(Date date1, Date data2) {

        Date dataInicial = DateUtil.formatDate(date1, DateUtil.FORMATO_DD_MM_YYYY_HH_MM_SS);
        Date dataFinal = DateUtil.formatDate(data2, DateUtil.FORMATO_DD_MM_YYYY_HH_MM_SS);

        return dataInicial.after(dataFinal);
    }

    public static String timeDiff(Date dataInicio, Date dataFim) {

        Long segundos = ChronoUnit.SECONDS.between(LocalDateTime.ofInstant(dataInicio.toInstant(), FUSO_HORARIO),
                LocalDateTime.ofInstant(dataFim.toInstant(), FUSO_HORARIO));

        Double minutos = Math.floor(segundos / LongUtil.SESSENTA);

        Double horas = Math.floor(minutos.longValue() / LongUtil.SESSENTA);

        Long minutosRestantes = minutos.longValue() - (horas.longValue() * LongUtil.SESSENTA);

        Long segundosRestantes = segundos - (horas.longValue() * LongUtil.SESSENTA * LongUtil.SESSENTA) - (minutosRestantes * LongUtil.SESSENTA);

        return String.format("%02d", horas.longValue()).concat(":")
                .concat(String.format("%02d", minutosRestantes).concat(":").concat(String.format("%02d", segundosRestantes)));

    }

    public static Boolean between(Date dataComparar, Date dataInicial, Date dataFinal) {

        if (dataComparar == null) {
            return null;
        }

        return dataComparar.after(dataInicial) && dataComparar.before(dataFinal);
    }

}
