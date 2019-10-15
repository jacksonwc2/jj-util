package com.jj.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.ScheduleExpression;

import com.jj.util.dto.ScheduleDTO;

public class ScheduleUtil {

    private static final long INTERVALO_UM_DIA = 1439L;

    private static Logger logger = Logger.getLogger(ScheduleUtil.class.getName());

    private static final long UMA_HORA = 59L;
    private static final long MEIA_HORA = 30L;

    private static final String FUSO_HORARIO_SAO_PAULO = "America/Sao_Paulo";

    private ScheduleUtil() {
        // Garante que a classe não seja instanciada.
    }

    public static void atribuirInvervaloHorario(Long intervalo, ScheduleDTO scheduleDTO, ScheduleExpression scheduleExpression) {
        logger.info("==>Executando o método atribuirInvervaloHorario.");

        String[] horarioInicial = scheduleDTO.getHoraInicio().split(":");

        Long horaInicial = Long.valueOf(horarioInicial[IntegerUtil.ZERO]);
        Long minutoInicial = Long.valueOf(horarioInicial[IntegerUtil.UM]);
        Long segundoInicial = Long.valueOf(horarioInicial[IntegerUtil.DOIS]);

        String minutos = "";

        scheduleExpression.hour(horaInicial.toString());

        if (!LongUtil.isNullOrZero(intervalo)) {

            String[] horarioFinal = scheduleDTO.getHoraFim().split(":");

            Long horaFinal = Long.valueOf(horarioFinal[IntegerUtil.ZERO]);
            Long minutoFinal = Long.valueOf(horarioFinal[IntegerUtil.UM]);

            if (!horaInicial.equals(horaFinal)) {
                String horas = calcularHoras(intervalo, horaInicial, horaFinal);
                scheduleExpression.hour(horas);
            }

            Long diff = DateUtil.timeDiff(Time.valueOf(scheduleDTO.getHoraInicio()), Time.valueOf(scheduleDTO.getHoraFim()));

            if (diff <= UMA_HORA) {
                if (diff > intervalo) {

                    if (minutoInicial < minutoFinal) {
                        minutos = calculaMinutos(intervalo, minutoInicial, minutoFinal);
                    } else {
                        minutos = calculaMinutos(intervalo, minutoInicial, UMA_HORA);
                        minutos = calculaMinutos(intervalo, LongUtil.ZERO, minutoFinal, minutos);
                    }

                } else {
                    minutos = minutoInicial.toString();
                }

            } else {

                if (intervalo > UMA_HORA) {
                    minutos = minutoInicial.toString();
                } else if (intervalo > MEIA_HORA) {
                    minutos = intervalo.toString();
                } else {
                    minutos = "*/".concat(intervalo.toString());
                }
            }
            scheduleExpression.minute(minutos);

        } else {
            scheduleExpression.minute(minutoInicial.toString());
        }

        if (!minutos.contains("/")) {
            scheduleExpression.second(segundoInicial.toString());
        }

    }

    private static String calcularHoras(Long intervalo, Long horaInicial, Long horaFinal) {
        logger.info("==>Executando o método calcularHoras.");

        if (intervalo.equals(INTERVALO_UM_DIA)) {
            return horaInicial.toString();
        }

        Long intervaloHoras = LongUtil.UM;

        if (intervalo > UMA_HORA) {
            intervaloHoras = BigDecimalUtil.divide(new BigDecimal(intervalo), new BigDecimal(IntegerUtil.SESSENTA)).longValue();
        }

        String horas = StringUtil.STRING_VAZIA;

        for (Long i = horaInicial; i <= horaFinal; i += intervaloHoras) {
            horas = horas.length() > LongUtil.ZERO ? horas.concat(",").concat(i.toString()) : i.toString();
        }
        return horas;
    }

    private static String calculaMinutos(Long intervalo, Long minutoInicial, Long minutoFinal) {
        logger.info("==>Executando o sobrecarga método calculaMinutos.");

        return calculaMinutos(intervalo, minutoInicial, minutoFinal, null);
    }

    private static String calculaMinutos(Long intervalo, Long minutoInicial, Long minutoFinal, String minutosOrigem) {
        logger.info("==>Executando o método calculaMinutos.");

        String minutos = IfNull.get(minutosOrigem, StringUtil.STRING_VAZIA);

        for (Long i = minutoInicial; i <= minutoFinal; i += intervalo) {
            minutos = minutos.length() > LongUtil.ZERO ? minutos.concat(",").concat(i.toString()) : i.toString();
        }
        return minutos;
    }

    public static ScheduleExpression criarExpressaoAgendamentoDiario(Long intervalo, Date dataAtual, ScheduleDTO scheduleDTO) {
        logger.info("==>Executando o método criarExpressaoAgendamentoDiario.");

        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.timezone(FUSO_HORARIO_SAO_PAULO);

        atribuirInvervaloHorario(intervalo, scheduleDTO, scheduleExpression);

        Date dataInicio = DateUtil.setTime(dataAtual, Time.valueOf(scheduleDTO.getHoraInicio()));

        if (dataAtual.before(dataInicio)) {
            // Desconto 1 segundo para o temporizador iniciar com antecedencia
            dataInicio = DateUtil.addSeconds(dataInicio, -LongUtil.UM);
            scheduleExpression.start(dataInicio);
        }

        return scheduleExpression;
    }

    public static ScheduleExpression criarExpressaoAgendamentoSemanal(Long intervalo, Date dataAtual, ScheduleDTO scheduleDTO) {
        logger.info("==>Executando o método criarExpressaoAgendamentoSemanal.");

        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.timezone(FUSO_HORARIO_SAO_PAULO);
        scheduleExpression.dayOfWeek(scheduleDTO.getValorPeriodo().intValue());

        atribuirInvervaloHorario(intervalo, scheduleDTO, scheduleExpression);

        Date dataInicio = DateUtil.setNextDayOfWeek(dataAtual, scheduleDTO.getValorPeriodo().intValue());
        dataInicio = DateUtil.setTime(dataInicio, Time.valueOf(scheduleDTO.getHoraInicio()));

        if (dataAtual.before(dataInicio)) {
            // Desconto 1 segundo para o temporizador iniciar com antecedencia
            dataInicio = DateUtil.addSeconds(dataInicio, -LongUtil.UM);
            scheduleExpression.start(dataInicio);
        }

        return scheduleExpression;
    }

    public static ScheduleExpression criarExpressaoAgendamentoMensal(Long intervalo, Date dataAtual, ScheduleDTO scheduleDTO) {
        logger.info("==>Executando o método criarExpressaoAgendamentoMensal.");

        Long valorPeriodo;

        Long ultimoDiaMes = DateUtil.getDayOfMonth(DateUtil.lastDayOfMonth(dataAtual)).longValue();

        valorPeriodo = scheduleDTO.getValorPeriodo() > ultimoDiaMes ? ultimoDiaMes : scheduleDTO.getValorPeriodo();

        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.timezone(FUSO_HORARIO_SAO_PAULO);
        scheduleExpression.dayOfMonth(valorPeriodo.intValue());

        atribuirInvervaloHorario(intervalo, scheduleDTO, scheduleExpression);

        Date dataInicio = DateUtil.setDayOfMonth(dataAtual, valorPeriodo.intValue());
        dataInicio = DateUtil.setTime(dataInicio, Time.valueOf(scheduleDTO.getHoraInicio()));

        if (dataAtual.before(dataInicio)) {
            // Desconto 1 segundo para o temporizador iniciar com antecedencia
            dataInicio = DateUtil.addSeconds(dataInicio, -LongUtil.UM);
            scheduleExpression.start(dataInicio);
        }

        return scheduleExpression;
    }

    public static ScheduleExpression criarExpressaoAgendamentoAnual(Long intervalo, Date dataAtual, ScheduleDTO scheduleDTO) {
        logger.info("==>Executando o método criarExpressaoAgendamentoAnual.");

        Long valorPeriodo;

        Long ultimoDiaAno = DateUtil.getDayOfYear(DateUtil.lastDayOfYear(dataAtual)).longValue();

        valorPeriodo = scheduleDTO.getValorPeriodo() > ultimoDiaAno ? ultimoDiaAno : scheduleDTO.getValorPeriodo();

        Date dataPeriodo = DateUtil.getDateByDayOfYear(valorPeriodo);

        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.timezone(FUSO_HORARIO_SAO_PAULO);
        scheduleExpression.month(DateUtil.getMonthOfDate(dataPeriodo));
        scheduleExpression.dayOfMonth(DateUtil.getDayOfMonth(dataPeriodo));

        atribuirInvervaloHorario(intervalo, scheduleDTO, scheduleExpression);

        Date dataInicio = DateUtil.setDayOfYear(dataAtual, valorPeriodo.intValue());
        dataInicio = DateUtil.setTime(dataInicio, Time.valueOf(scheduleDTO.getHoraInicio()));

        if (dataAtual.before(dataInicio)) {
            // Desconto 1 segundo para o temporizador iniciar com antecedencia
            dataInicio = DateUtil.addSeconds(dataInicio, -LongUtil.UM);
            scheduleExpression.start(dataInicio);
        }

        return scheduleExpression;
    }

    public static ScheduleExpression criarAgendamentoPeriodico(Long periodo, Date dataAgendamento) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAgendamento);

        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.timezone(FUSO_HORARIO_SAO_PAULO);
        scheduleExpression.second(calendar.get(Calendar.SECOND));
        scheduleExpression.minute(calendar.get(Calendar.MINUTE));
        scheduleExpression.hour(calendar.get(Calendar.HOUR_OF_DAY));
        if (periodo.equals(LongUtil.TRES)) {
            scheduleExpression.dayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
        } else if (periodo.equals(LongUtil.QUATRO)) {
            scheduleExpression.dayOfMonth(calendar.get(Calendar.DATE));
        }

        return scheduleExpression;
    }

    public static ScheduleExpression criarAgendamentoTarefas(Long flagTipoPeriodo, String valorPeriodo, Time horaExecucao) {

        return criarAgendamentoTarefas(flagTipoPeriodo, valorPeriodo, horaExecucao, new Date());
    }

    public static ScheduleExpression criarAgendamentoTarefas(Long flagTipoPeriodo, String valorPeriodo, Time horaExecucao, Date dataAtual) {
        ScheduleExpression scheduleExpression = new ScheduleExpression();

        scheduleExpression.timezone(FUSO_HORARIO_SAO_PAULO);

        if (horaExecucao != null) {

            Calendar hora = Calendar.getInstance();
            hora.setTime(horaExecucao);
            scheduleExpression.second(hora.get(Calendar.SECOND));
            scheduleExpression.minute(hora.get(Calendar.MINUTE));
            scheduleExpression.hour(hora.get(Calendar.HOUR_OF_DAY));
        }

        switch (flagTipoPeriodo.intValue()) {
            case 1:
                if (StringUtil.isNumeric(valorPeriodo)) {
                    scheduleExpression.minute("*/" + valorPeriodo);
                    scheduleExpression.hour("*");
                    scheduleExpression.second("0");
                }
                break;
            case 2:
                if (StringUtil.isNumeric(valorPeriodo)) {
                    scheduleExpression.dayOfWeek(valorPeriodo);
                }
                break;
            case 3:
                if (StringUtil.isNumeric(valorPeriodo)) {
                    scheduleExpression.dayOfMonth(valorPeriodo);
                }
                break;

            case 4:
                Date mesAno = DateUtil.parseDate(valorPeriodo, DateUtil.FORMATO_DD_MM);
                Calendar dataMes = Calendar.getInstance();
                dataMes.setTime(mesAno);
                scheduleExpression.dayOfMonth(dataMes.get(Calendar.DAY_OF_MONTH));
                scheduleExpression.month(dataMes.get(Calendar.MONTH) + 1);
                break;
            default:
                Date dataFixa = DateUtil.parseDate(valorPeriodo, DateUtil.FORMATO_DD_MM_YYYY);
                Calendar dataUnica = Calendar.getInstance();
                dataUnica.setTime(DateUtil.setTime(dataFixa, DateUtil.getTime(horaExecucao)));

                if (dataAtual.before(dataUnica.getTime())) {
                    scheduleExpression.second(dataUnica.get(Calendar.SECOND));
                    scheduleExpression.minute(dataUnica.get(Calendar.MINUTE));
                    scheduleExpression.hour(dataUnica.get(Calendar.HOUR_OF_DAY));
                    scheduleExpression.dayOfMonth(dataUnica.get(Calendar.DAY_OF_MONTH));
                    scheduleExpression.month(dataUnica.get(Calendar.MONTH) + 1);
                    scheduleExpression.year(dataUnica.get(Calendar.YEAR));
                } else {
                    scheduleExpression = null;
                }

                break;
        }
        return scheduleExpression;
    }
}
