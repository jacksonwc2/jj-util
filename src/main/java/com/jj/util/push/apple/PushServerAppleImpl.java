package com.jj.util.push.apple;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Logger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.jj.util.StringUtil;
import com.jj.util.exception.BusinessServerException;
import com.jj.util.push.PushMessage;
import com.jj.util.push.PushServer;

public class PushServerAppleImpl implements PushServer {

    private static final String CERTIFICADO_DESENVOLVIMENTO = "certificados/apple/aps_development.p12";

    private static final String CERTIFICADO_PRODUCAO = "certificados/apple/aps_production.p12";

    private static Logger logger = Logger.getLogger(PushServerAppleImpl.class.getName());

    private static final String FALHA_AO_ENVIAR_A_NOTIFICACAO = "Falha ao enviar a notificação!";

    private static final String PUSH_ERROR = "PUSH_ERROR";

    private static final byte COMMAND = 0;

    private static final String PROTOCOL = "TLS";

    private static final String PASSWORD = "jj2014";

    private static final String KEYSTORE_TYPE = "PKCS12";
    private static final String KEY_ALGORITHM = "SunX509";

    public static final String SANDBOX_GATEWAY_HOST = "gateway.sandbox.push.apple.com";
    public static final int SANDBOX_GATEWAY_PORT = 2195;

    public static final String PRODUCTION_GATEWAY_HOST = "gateway.push.apple.com";
    public static final int PRODUCTION_GATEWAY_PORT = 2195;

    public static final int MAXIMUM_EXPIRY = Integer.MAX_VALUE;

    private SSLSocket sslSocket;

    public PushServerAppleImpl() {

    }

    @Override
    public void sendMessage(String deviceToken, PushMessage pushMessage) throws BusinessServerException {

        try {

            this.connectSocket();

            PayloadApple payloadApple = new PayloadApple(pushMessage.getTitle(), pushMessage.getMessage());

            char[] data = deviceToken.toCharArray();
            byte[] token = Hex.decodeHex(data);

            String payload = payloadApple.build();

            OutputStream outputstream = sslSocket.getOutputStream();

            outputstream.write(this.payloadByteArray(token, payload.getBytes(Charset.forName("UTF-8"))));

            outputstream.flush();
            outputstream.close();

        } catch (IOException | DecoderException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyStoreException
                | KeyManagementException e) {
            try {
                sslSocket.close();
            } catch (IOException e1) {
                logger.info("Não foi possível fechar a conexão com o servidor de push Apple:" + e1);
                throw new BusinessServerException(PUSH_ERROR, e1.toString(), FALHA_AO_ENVIAR_A_NOTIFICACAO);
            }
            logger.info("Não foi possível fechar a conexão com o servidor de push Apple:" + e);
            throw new BusinessServerException(PUSH_ERROR, e.toString(), FALHA_AO_ENVIAR_A_NOTIFICACAO);
        }

    }

    private void connectSocket()
            throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException {

        if (sslSocket == null || sslSocket.isClosed()) {

            String ambiente = System.getProperty("ambiente-desenvolvimento");

            Boolean ambienteDesenvolvimento = StringUtil.isNullOrEmpty(ambiente) ? false : (Boolean.parseBoolean(ambiente) ? true : false);

            KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);

            String certificado = CERTIFICADO_PRODUCAO;
            String urlServico = PRODUCTION_GATEWAY_HOST;

            if (ambienteDesenvolvimento) {
                certificado = CERTIFICADO_DESENVOLVIMENTO;
                urlServico = SANDBOX_GATEWAY_HOST;
            }

            InputStream certStream = getClass().getClassLoader().getResourceAsStream(certificado);

            keyStore.load(certStream, PASSWORD.toCharArray());
            KeyManagerFactory keyMgrFactory = KeyManagerFactory.getInstance(KEY_ALGORITHM);
            keyMgrFactory.init(keyStore, PASSWORD.toCharArray());

            final TrustManagerFactory tmf = TrustManagerFactory.getInstance(KEY_ALGORITHM);
            tmf.init((KeyStore) null);

            SSLContext sslContext = SSLContext.getInstance(PROTOCOL);
            sslContext.init(keyMgrFactory.getKeyManagers(), tmf.getTrustManagers(), null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            sslSocket = (SSLSocket) sslSocketFactory.createSocket(urlServico, PRODUCTION_GATEWAY_PORT);
            String[] cipherSuites = sslSocket.getSupportedCipherSuites();
            sslSocket.setEnabledCipherSuites(cipherSuites);
            sslSocket.startHandshake();
        }
    }

    private byte[] payloadByteArray(final byte[] deviceToken, final byte[] payload) {
        final ByteArrayOutputStream boas = new ByteArrayOutputStream();
        final DataOutputStream dos = new DataOutputStream(boas);

        try {
            dos.writeByte(COMMAND);
            dos.writeShort(deviceToken.length);
            dos.write(deviceToken);
            dos.writeShort(payload.length);
            dos.write(payload);
            return boas.toByteArray();
        } catch (final IOException e) {
            throw new AssertionError(e);
        }
    }

}
