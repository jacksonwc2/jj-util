package com.jj.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Utilitário para registrar como log o tempo de execução de processos.
 *
 */
public class SpentTimeLogger {

    private static final String TEMPO = "Tempo ";
    public static final String MINUTES = "MINUTES";
    public static final String SECONDS = "SECONDS";
    public static final String MILLISECONDS = "MILLISECONDS";
    private String processName = "";
    private Long timeStart = 0L;
    private Long timeStop = 0L;
    private Long processando = 0L;
    private static Map<String, SpentTimeLogger> listSpentTimeLogger = new HashMap<String, SpentTimeLogger>();

    private static Logger logger = Logger.getLogger(SpentTimeLogger.class.getName());

    private SpentTimeLogger(String processName) {
        this.processName = processName;
    }

    /**
     * Recebe como parâmetro a classe em que foi iniciado o processo. <br>
     * Ex: <br>
     * SpentTimeLogger.getInstance(MonitorVendaServiceImpl.class).start("adquirirMonitorVenda");
     * 
     * @param clazz
     * @return
     */
    public static SpentTimeLogger from(Class<?> clazz) {
        if (listSpentTimeLogger.containsKey(clazz.getSimpleName())) {
            return listSpentTimeLogger.get(clazz.getSimpleName());
        } else {
            listSpentTimeLogger.put(clazz.getSimpleName(), new SpentTimeLogger(clazz.getSimpleName()));
        }
        return listSpentTimeLogger.get(clazz.getSimpleName());
    }

    public static SpentTimeLogger from(String chave) {

        if (listSpentTimeLogger.containsKey(chave)) {
            return listSpentTimeLogger.get(chave);
        } else {
            listSpentTimeLogger.put(chave, new SpentTimeLogger(chave));
        }
        return listSpentTimeLogger.get(chave);
    }

    /**
     * Registra o inicio da execução.
     */
    public void start() {
        this.setTimeStart(System.currentTimeMillis());

    }

    /**
     * Registra o inicio da execução. Aguarda como parâmetro uma descrição do processo.
     */
    public void start(String processName) {
        this.setTimeStart(System.currentTimeMillis());
        this.setProcessName(" " + processName);

    }

    /**
     * Registra log por padrão em milisegundos.
     */
    public void stop() {
        stop(TimeLogger.SECONDS);
    }

    /**
     * Registra log conforme parâmetro: TimeSpentLogger.SECONDS,TimeSpentLogger.MINUTES,TimeSpentLogger.MILLISECONDS
     * 
     * @param type
     */
    public void stop(TimeLogger timeLogger) {

        if (this.processando > 0L) {
            this.processando = 0L;
            this.setTimeStop(System.currentTimeMillis());

            if (this.getTimeStart().equals(0L)) {
                logger.info(
                        "O tempo inicial não pode ser zero, verifique se o SpentTimeLogger foi iniciado através do método start() ou se foi resetado através do método clear().");
                this.setTimeStart(this.getTimeStop());
            }
            showIn(timeLogger);
        }

    }

    public String getProcessName() {
        return this.processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
        this.processando = 1L;
    }

    public Long getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Long timeStop) {
        this.timeStop = timeStop;
    }

    private void showIn(TimeLogger timeLogger) {
        Long spent = this.getTimeStop() - this.getTimeStart();

        if (timeLogger.equals(TimeLogger.MINUTES)) {
            Double result = (spent.doubleValue() / DoubleUtil.MIL) / DoubleUtil.SESSENTA;
            logger.info(TEMPO + processName + ": " + result + "min");
        } else if (timeLogger.equals(TimeLogger.SECONDS)) {
            Double result = spent.doubleValue() / DoubleUtil.MIL;
            logger.info(TEMPO + processName + ": " + result + "s");
        } else {
            logger.info(TEMPO + processName + ": " + spent + "ms");
        }

    }

    /**
     * Remover todas as instâncias
     */
    public static void clear() {
        listSpentTimeLogger.clear();

    }

}
