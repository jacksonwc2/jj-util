package com.jj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class DateUtilTest {

    private static final ZoneId FUSO_HORARIO = ZoneId.systemDefault();
    private static final String SABADO = "Sábado";
    private static final String SEXTA_FEIRA = "Sexta-feira";
    private static final String QUINTA_FEIRA = "Quinta-feira";
    private static final String QUARTA_FEIRA = "Quarta-feira";
    private static final String TERCA_FEIRA = "Terça-feira";
    private static final String SEGUNDA_FEIRA = "Segunda-feira";
    private static final String DOMINGO = "Domingo";

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

    // Teste necessário para ar se o construtor é privado, e para não diminuir porcentagem de coverage
    @Test
    public void ConstructorIsPrivateTest() throws Exception {
        Constructor<DateUtil> constructor = null;
        constructor = DateUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void addDaysTest() {
        Long dias = LongUtil.UM;
        assertEquals(DateUtil.addDays(dias), DateUtil.addDays(null, dias));
    }

    @Test
    public void addYearsPositivoTest() {
        Date data = new Date();
        Long anos = LongUtil.DOIS;
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        assertEquals(DateUtil.addYears(data, anos), Date.from(currentDateTime.plusYears(anos).atZone(FUSO_HORARIO).toInstant()));
    }

    @Test
    public void addYearsNegativoTest() {
        Date data = new Date();
        Long anos = -LongUtil.DOIS;
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        assertEquals(DateUtil.addYears(data, anos), Date.from(currentDateTime.minusYears(anos * -1).atZone(FUSO_HORARIO).toInstant()));
    }

    @Test
    public void addHoursDateNullTest() {
        Long horas = LongUtil.DOIS;
        assertEquals(DateUtil.addHours(null, horas), Date.from(DateUtil.getDateTimeNow().toInstant().plus(Duration.ofHours(horas))));
    }

    @Test
    public void addHoursTest() {
        Date date = new Date();
        Long horas = LongUtil.DOIS;
        assertEquals(DateUtil.addHours(date, horas), Date.from(date.toInstant().plus(Duration.ofHours(horas))));
    }

    @Test
    public void addMonthPositivoTest() {
        Date data = new Date();
        Long meses = LongUtil.UM;
        LocalDateTime currentDateTime = DateUtil.dateToLocalDateTime(data);
        assertEquals(DateUtil.addMonth(data, meses), Date.from(currentDateTime.plusMonths(meses).atZone(FUSO_HORARIO).toInstant()));
    }

    @Test
    public void addMonthNegativoTest() {
        Date data = new Date();
        Long meses = -LongUtil.UM;
        LocalDateTime currentDateTime = DateUtil.dateToLocalDateTime(data);
        assertEquals(DateUtil.addMonth(data, meses), Date.from(currentDateTime.minusMonths(meses * -1).atZone(FUSO_HORARIO).toInstant()));
    }

    @Test
    public void addMonthDateNullTest() {
        Long meses = LongUtil.UM;
        LocalDateTime currentDateTime = DateUtil.dateToLocalDateTime(DateUtil.getDateTimeNow());
        assertEquals(DateUtil.addMonth(null, meses), Date.from(currentDateTime.plusMonths(meses).atZone(FUSO_HORARIO).toInstant()));
    }

    @Test
    public void isActualMonthTest() {
        Date data = new Date();
        assertTrue(DateUtil.isActualMonth(data));
    }

    @Test
    public void isActualWeekTest() {
        Date data = new Date();
        assertTrue(DateUtil.isActualWeek(data));
    }

    @Test
    public void isActualDayTest() {
        Date data = new Date();
        assertTrue(DateUtil.isActualDay(data));
    }

    @Test
    public void isDayEqualsTest() {
        Date data = new Date();
        Date data2 = new Date();
        assertTrue(DateUtil.isDayEquals(data, data2));
    }

    @Test
    public void getHourTest() {
        Date data = new Date();
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        Integer hora = parameterDate.getHour();
        assertEquals(DateUtil.getHour(data), hora);
    }

    @Test
    public void getYearTest() {
        Date data = new Date();
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        Integer ano = parameterDate.getYear();
        assertEquals(DateUtil.getYear(data), ano);
    }

    @Test
    public void getgetDayOfMonthTest() {
        Date data = new Date();
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        Integer dia = parameterDate.getDayOfMonth();
        assertEquals(DateUtil.getDayOfMonth(data), dia);
    }

    @Test
    public void getMonthTest() {
        Date data = new Date();
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        Integer mes = parameterDate.getMonthValue();
        assertEquals(DateUtil.getMonth(data), mes);
    }

    @Test
    public void lastDayOfYearTest() {
        Date data = new Date();
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        Date dia = Date.from(parameterDate.with(TemporalAdjusters.lastDayOfYear()).atZone(FUSO_HORARIO).toInstant());
        assertEquals(DateUtil.lastDayOfYear(data), dia);
    }

    @Test
    public void firstDayOfYearTest() {
        Date data = new Date();
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        Date dia = Date.from(parameterDate.with(TemporalAdjusters.firstDayOfYear()).atZone(FUSO_HORARIO).toInstant());
        assertEquals(DateUtil.firstDayOfYear(data), dia);
    }

    @Test
    public void initialDateDateTest() {
        Date data = new Date();
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        Date dia = Date.from(parameterDate.withHour(IntegerUtil.ZERO).withMinute(IntegerUtil.ZERO).withSecond(IntegerUtil.ZERO)
                .withNano(IntegerUtil.ZERO).atZone(FUSO_HORARIO).toInstant());
        assertEquals(DateUtil.initialDate(data), dia);
    }

    @Test
    public void initialDateStringTest() {
        String data = "2017-09-08 09:40:00";
        assertEquals(DateUtil.initialDate(data), DateUtil.formatDateTimeForDB(DateUtil.initialDate(DateUtil.parseDate(data))));
    }

    @Test
    public void finalDateStringTest() {
        String data = "2017-09-08 09:40:00";
        assertEquals(DateUtil.finalDate(data), DateUtil.formatDateTimeForDB(DateUtil.finalDate(DateUtil.parseDate(data))));
    }

    @Test
    public void finalDateTest() {
        Date data = new Date();
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        Date dia = Date.from(parameterDate.withHour(IntegerUtil.VINTE_TRES).withMinute(IntegerUtil.CINQUENTA_NOVE)
                .withSecond(IntegerUtil.CINQUENTA_NOVE).atZone(FUSO_HORARIO).toInstant());
        assertEquals(DateUtil.finalDate(data), dia);
    }

    @Test
    public void formatDateForDBTest() {
        Date data = new Date();
        LocalDate parameterDate = Instant.ofEpochMilli(data.getTime()).atZone(FUSO_HORARIO).toLocalDate();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = parameterDate.format(formatador);
        assertEquals(DateUtil.formatDateForDB(data), dataFormatada);
    }

    @Test
    public void formatDateTimeForDBTest() {
        Date data = new Date();
        LocalDateTime parameterDate = LocalDateTime.ofInstant(data.toInstant(), FUSO_HORARIO);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataFormatada = parameterDate.format(formatador);
        assertEquals(DateUtil.formatDateTimeForDB(data), dataFormatada);
    }

    @Test
    public void getDateTimeNowMinutesTest() {
        Long minutos = LongUtil.DOIS;
        // Declarado direto no equals para não ter diferença de segundos
        assertEquals(DateUtil.getDateTimeNowMinutes(minutos), Date.from(DateUtil.getDateTimeNow().toInstant().plus(Duration.ofMinutes(minutos))));
    }

    @Test
    public void getDateTimeNowMinutesNullTest() {
        // Declarado direto no equals para não ter diferença de segundos
        assertEquals(DateUtil.getDateTimeNowMinutes(null), DateUtil.getDateTimeNow());
    }

    @Test
    public void isDateNullTest() {
        assertFalse(DateUtil.isDate(null));
    }

    @Test
    public void isDateDateTest() {
        Date data = new Date();
        assertTrue(DateUtil.isDate(data));
    }

    @Test
    public void isDateStringTest() {
        String data = "2017-09-08 09:40:00";
        assertTrue(DateUtil.isDate(data));
    }

    @Test
    public void isDateStringFalseTest() {
        String data = "2017*312*123";
        assertFalse(DateUtil.isDate(data));
    }

    @Test
    public void initialDateOfWeekTest() {
        assertTrue(DateUtil.initialDateOfWeek(DateUtil.parseDate("2015-03-29")).equals(DateUtil.parseDate("2015-03-23")));
        assertTrue(DateUtil.initialDateOfWeek(DateUtil.parseDate("2015-03-12")).equals(DateUtil.parseDate("2015-03-09")));
        assertTrue(DateUtil.initialDateOfWeek(DateUtil.parseDate("2015-02-01")).equals(DateUtil.parseDate("2015-01-26")));
    }

    @Test
    public void initialDateOfMonthTest() {
        assertTrue(DateUtil.lastDayOfMonth(DateUtil.parseDate("2015-03-10")).equals(DateUtil.parseDate("2015-03-31")));
    }

    @Test
    public void initialDateOfMonthLongTrintaUmDiasTest() {
        assertTrue(DateUtil.lastDayOfTheMonth(DateUtil.parseDate("2015-03-10")).equals(LongUtil.TRINTA_UM));
    }

    @Test
    public void initialDateOfMonthLongVinteOitoDiasTest() {
        assertTrue(DateUtil.lastDayOfTheMonth(DateUtil.parseDate("2015-02-10")).equals(LongUtil.VINTE_OITO));
    }

    @Test
    public void initialDateOfMonthLongTrintaDiasTest() {
        assertTrue(DateUtil.lastDayOfTheMonth(DateUtil.parseDate("2015-04-10")).equals(LongUtil.TRINTA));
    }

    @Test
    public void testLastDateOfWeekTest() {
        assertTrue(DateUtil.lastDateOfWeek(DateUtil.parseDate("2015-03-30")).equals(DateUtil.parseDate("2015-04-05")));
        assertTrue(DateUtil.lastDateOfWeek(DateUtil.parseDate("2015-03-31")).equals(DateUtil.parseDate("2015-04-05")));
        assertTrue(DateUtil.lastDateOfWeek(DateUtil.parseDate("2015-04-06")).equals(DateUtil.parseDate("2015-04-12")));
    }

    @Test
    public void isWeekEqualsTest() {
        assertTrue(DateUtil.isWeekEquals(DateUtil.parseDate("2015-03-30"), DateUtil.parseDate("2015-04-05")));
        assertFalse(DateUtil.isWeekEquals(DateUtil.parseDate("2015-03-30"), DateUtil.parseDate("2015-03-29")));
    }

    @Test
    public void isMonthEqualsTest() {
        assertTrue(DateUtil.isMonthEquals(DateUtil.parseDate("2015-03-25"), DateUtil.parseDate("2015-03-05")));
        assertTrue(DateUtil.isMonthEquals(DateUtil.parseDate("2015-01-01"), DateUtil.parseDate("2015-01-02")));
        assertFalse(DateUtil.isMonthEquals(DateUtil.parseDate("2015-01-01"), DateUtil.parseDate("2015-02-01")));
        assertFalse(DateUtil.isMonthEquals(DateUtil.parseDate("2015-03-25"), DateUtil.parseDate("2015-04-25")));
        assertFalse(DateUtil.isMonthEquals(DateUtil.parseDate("2015-03-25"), DateUtil.parseDate("2015-04-01")));
    }

    @Test
    public void adquirirNumeroDiasTest() {
        assertTrue(DateUtil.adquirirNumeroDias(DateUtil.parseDate("2015-01-01"), DateUtil.parseDate("2015-01-31"), DayOfWeek.SUNDAY).equals(4L));
        assertTrue(DateUtil.adquirirNumeroDias(DateUtil.parseDate("2015-05-15"), DateUtil.parseDate("2015-05-31"), DayOfWeek.SUNDAY).equals(3L));
    }

    @Test
    public void intervaloEntreDatasTest() {
        assertTrue(DateUtil.intervaloDiasEntreDatas(DateUtil.parseDate("2015-04-15"), DateUtil.parseDate("2015-04-25")).equals(10L));
    }

    @Test
    public void intervaloEntreMesesTest() {
        assertTrue(DateUtil.intervaloMesesEntreDatas(DateUtil.parseDate("2014-04-14"), DateUtil.parseDate("2015-04-14")).equals(12L));
        assertTrue(DateUtil.intervaloMesesEntreDatas(DateUtil.parseDate("2015-04-14"), DateUtil.parseDate("2015-04-18")).equals(0L));
        assertTrue(DateUtil.intervaloMesesEntreDatas(DateUtil.parseDate("2015-04-20"), DateUtil.parseDate("2015-05-18")).equals(0L));
    }

    @Test
    public void testConversaoDeDatasTest() {
        assertTrue(DateUtil.formatString(DateUtil.parseDate("2015-06-03 08:49:52", "yyyy-MM-dd HH:mm:ss"), "dd/MM/yyyy HH:mm:ss")
                .equals(DateUtil.formatString("03/06/2015 08:49:52", "dd/MM/yyyy HH:mm:ss")));
    }

    @Test
    public void parseDateLongTest() {
        Long valor = LongUtil.UM;
        assertEquals(DateUtil.parseDate(valor), new Date(valor));
    }

    @Test
    public void dayOfMonthNumberTest() {
        Date date = new Date();
        Integer dia = LocalDateTime.ofInstant(date.toInstant(), FUSO_HORARIO).getDayOfMonth();
        assertEquals(DateUtil.dayOfMonthNumber(date), dia);
    }

    @Test
    public void dayNameOfWeekIntegerTest() {
        for (int i = 1; i < 9; i++) {
            Integer dia = i;
            String diaSemana = "";
            switch (dia) {
                case SUNDAY:
                    diaSemana = DOMINGO;
                    break;
                case MONDAY:
                    diaSemana = SEGUNDA_FEIRA;
                    break;
                case TUESDAY:
                    diaSemana = TERCA_FEIRA;
                    break;
                case WEDNESDAY:
                    diaSemana = QUARTA_FEIRA;
                    break;
                case THURSDAY:
                    diaSemana = QUINTA_FEIRA;
                    break;
                case FRIDAY:
                    diaSemana = SEXTA_FEIRA;
                    break;
                case SATURDAY:
                    diaSemana = SABADO;
                    break;
                default:
                    diaSemana = "";
                    break;
            }
            assertEquals(DateUtil.dayNameOfWeek(i), diaSemana);
        }
    }

    @Test
    public void dayNameOfWeekAbbreviatedTest() {
        for (int i = 1; i < 9; i++) {
            int dia = i;
            String diaSemana = "";
            switch (dia) {
                case SUNDAY:
                    diaSemana = "Dom";
                    break;
                case MONDAY:
                    diaSemana = "Seg";
                    break;
                case TUESDAY:
                    diaSemana = "Ter";
                    break;
                case WEDNESDAY:
                    diaSemana = "Qua";
                    break;
                case THURSDAY:
                    diaSemana = "Qui";
                    break;
                case FRIDAY:
                    diaSemana = "Sex";
                    break;
                case SATURDAY:
                    diaSemana = "Sáb";
                    break;
                default:
                    diaSemana = "";
                    break;
            }
            assertEquals(DateUtil.dayNameOfWeekAbbreviated(i), diaSemana);
        }
    }

    @Test
    public void dayNameOfWeekChaveValorVOTest() {
        for (Long i = LongUtil.UM; i < LongUtil.NOVE; i++) {
            Long dia = i;
            ChaveValorVO teste = new ChaveValorVO();
            switch (dia.intValue()) {
                case SUNDAY:
                    teste.setChave(LongUtil.UM);
                    teste.setValor(DOMINGO);
                    break;
                case MONDAY:
                    teste.setValor(SEGUNDA_FEIRA);
                    teste.setChave(LongUtil.DOIS);
                    break;
                case TUESDAY:
                    teste.setValor(TERCA_FEIRA);
                    teste.setChave(LongUtil.TRES);
                    break;
                case WEDNESDAY:
                    teste.setValor(QUARTA_FEIRA);
                    teste.setChave(LongUtil.QUATRO);
                    break;
                case THURSDAY:
                    teste.setValor(QUINTA_FEIRA);
                    teste.setChave(LongUtil.CINCO);
                    break;
                case FRIDAY:
                    teste.setValor(SEXTA_FEIRA);
                    teste.setChave(LongUtil.SEIS);
                    break;
                case SATURDAY:
                    teste.setValor(SABADO);
                    teste.setChave(LongUtil.SETE);
                    break;
                default:
                    teste.setValor("");
                    teste.setChave(LongUtil.ZERO);
                    break;
            }
            assertEquals((DateUtil.dayNameOfWeek(i).getChave()), teste.getChave());
            assertEquals((DateUtil.dayNameOfWeek(i).getValor()), teste.getValor());
        }
    }

    @Test
    public void nameOfMonthAbbreviatedTest() {
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

        assertEquals(DateUtil.nameOfMonthAbbreviated(), meses);
    }

    @Test
    public void initialDateOfWeekSegunda() {
        Date dateInicial = DateUtil.parseDate("2015-07-01");
        Date dateFinal = DateUtil.parseDate("2015-11-01");
        Date dataAtual = DateUtil.parseDate("2015-11-20");
        Map<String, Date> datas = new TreeMap<String, Date>();
        for (int i = 0; i < 100; i++) {
            dateInicial = DateUtil.initialDateOfWeekSegunda(DateUtil.addWeek(dataAtual, -Long.valueOf(i)));
            dateFinal = DateUtil.lastDateOfWeek(dateInicial);
            datas.put(DateUtil.formatDate(dateInicial), dateFinal);
        }
        List<String> stringDatas = new ArrayList<String>();
        stringDatas.add("03/08/2015");
        stringDatas.add("10/08/2015");
        stringDatas.add("17/08/2015");
        stringDatas.add("24/08/2015");
        stringDatas.add("31/08/2015");
        stringDatas.add("07/09/2015");
        stringDatas.add("14/09/2015");
        stringDatas.add("21/09/2015");
        stringDatas.add("28/09/2015");
        stringDatas.add("02/11/2015");
        stringDatas.add("09/11/2015");
        stringDatas.add("05/10/2015");
        stringDatas.add("12/10/2015");
        stringDatas.add("19/10/2015");
        stringDatas.add("26/10/2015");
        stringDatas.add("10/10/3000");

        for (String itemData : stringDatas) {
            if (getData(datas, itemData) == null) {
                System.out.println("Data não encontrada == >> " + itemData);
                assertFalse(false);
            } else {
                assertTrue(true);
            }
        }
    }

    private Date getData(Map<String, Date> datas, String data) {
        return datas.get(data);
    }
}
