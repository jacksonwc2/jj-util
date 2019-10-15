package com.jj.util;

import java.nio.charset.Charset;
import java.util.Map;

public class ConfigByteUtil {

    private static ConfigByteUtil configByteUtil;
    private Charset isoCharset = Charset.forName("ISO-8859-1");

    private byte[] configuracaoByte;
    private int[] versao;
    private Integer posicao = IntegerUtil.ZERO;
    private Integer nivel = IntegerUtil.ZERO;
    private String retorno = StringUtil.STRING_VAZIA;

    /**
     * Definir o byte array que irá ser utilizado para pesquisar a configuração
     * 
     * @param configuracao
     * @return
     */
    public static ConfigByteUtil from(byte[] configuracao) {
        configByteUtil = new ConfigByteUtil();
        configByteUtil.configuracaoByte = configuracao;

        return configByteUtil;
    }

    /**
     * Pesquisar uma configuração com base em uma chave
     * 
     * @param chave
     * @return
     */
    public String adquirirConfiguracao(String chave) {
        reset();
        return configByteUtil.adquirirConfiguracaoPorChave(chave);
    }

    private void reset() {
        configByteUtil.posicao = IntegerUtil.ZERO;
        configByteUtil.nivel = IntegerUtil.ZERO;
        configByteUtil.retorno = StringUtil.STRING_VAZIA;
        configByteUtil.versao = null;
    }

    /**
     * Pesquisar configurações com base em várias chaves
     * 
     * @param configuracoes
     */
    public void adquirirConfiguracao(Map<String, String> configuracoes) {
        configuracoes.forEach((chave, valor) -> {
            reset();
            configuracoes.put(chave, configByteUtil.adquirirConfiguracaoPorChave(chave));
        });
    }

    private String adquirirConfiguracaoPorChave(String chave) {
        // fica em loop até não encontrar a configuração da chave ou então chegar ao fim do byte array
        while (Boolean.TRUE) {
            // essa chamada vai setar para a variável "retorno" a primeira "string" de configuração, geralmente será a chave
            configByteUtil.leituraBinaria();

            // o loop é cancelado caso tenha encontrado a chave ou então tenha chegado ao fim do byte array
            if (configByteUtil.retorno.equals(chave) || (configByteUtil.posicao >= configByteUtil.configuracaoByte.length)) {
                // se atingiu o tamanho do byte array deve zerar a string para não voltar lixo

                if (configByteUtil.posicao >= configByteUtil.configuracaoByte.length) {
                    configByteUtil.retorno = StringUtil.STRING_VAZIA;
                }

                // se a chave foi encontrada, o proximo passo é adquirir o valor dessa configuração, para isso basta chamar novamente
                // o metodo abaixo que o retorno será a própria configuração
                if (configByteUtil.retorno.equals(chave)) {
                    configByteUtil.leituraBinaria();

                    // o retorno é valido apenas de o nível do segmento for maior que zero.
                    // Dessa forma se nível for menor que zero limpa o retorno para evitar possíveis problemas ao utilizar uma
                    // configuração que supostamente não é válida
                    if (configByteUtil.nivel < IntegerUtil.UM) {
                        configByteUtil.retorno = StringUtil.STRING_VAZIA;
                    }
                }

                break;
            }

        }

        return configByteUtil.retorno;

    }

    private void leituraBinaria() {
        // apenas procura a configuração se a posição do "ponteiro" for menor que o tamanho do byte array
        if (configByteUtil.posicao < configByteUtil.configuracaoByte.length) {

            // quando a posição do "ponteiro" é zero significa que é a primeira leitura no byte array,
            // neste caso a posição [0] do byte array é a versão da configuração
            if (configByteUtil.posicao.equals(IntegerUtil.ZERO)) {
                // seta a versão conforme a leitura da configuração do byte array
                configByteUtil.setVersao(ByteUtil.getByteArray(configByteUtil.configuracaoByte, configByteUtil.posicao, IntegerUtil.UM));
                configByteUtil.posicao++;
            }

            try {
                // os proximos dois elementos do byte array correpondem ao nível e ao comprimento do segmento respectivamente
                int[] byteArray = ByteUtil.getByteArray(configByteUtil.configuracaoByte, configByteUtil.posicao, IntegerUtil.DOIS);

                // do byte array anterior de duas posições, pega a primeira correspondente ao nível e "decodifica". Logo após avança o "ponteiro" para a posição 3
                int[] nivelSegmento = { byteArray[IntegerUtil.ZERO] };
                configByteUtil.setNivel(Base256Util.toInteger(nivelSegmento));
                configByteUtil.posicao += IntegerUtil.DOIS;
                nivelSegmento = null;

                // do byte array anterior de duas posições, pega a segunda correspondente ao comprimento do segmento. Logo após avança o "ponteiro" para a posição 4
                byteArray = ByteUtil.getByteArray(configByteUtil.configuracaoByte, configByteUtil.posicao,
                        (configByteUtil.configuracaoByte.length - configByteUtil.posicao));
                configByteUtil.posicao++;

                // a posição zero do byte array anterior corresponde ao tamanho do segmento. Com base na posição atual do "ponteiro"
                // até o tamanho do segmento é extraida a string. Logo após avança o "ponteiro" para a posição posicao+tamanhoSegmento
                int tamanhoSegmento = byteArray[IntegerUtil.ZERO];
                byteArray = ByteUtil.getByteArray(configByteUtil.configuracaoByte, configByteUtil.posicao, tamanhoSegmento);
                configByteUtil.posicao += tamanhoSegmento;

                // transcreve o array de int para um array de byte
                byte[] retornoByte = new byte[byteArray.length];
                for (int i = 0; i < byteArray.length; i++) {
                    retornoByte[i] = (byte) byteArray[i];
                }
                configByteUtil.retorno = new String(retornoByte, isoCharset);

            } catch (Exception e) {

                e.getMessage();
            }

        }
    }

    public int[] getVersao() {
        return versao.clone();
    }

    public void setVersao(int[] versao) {
        this.versao = versao.clone();
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
}