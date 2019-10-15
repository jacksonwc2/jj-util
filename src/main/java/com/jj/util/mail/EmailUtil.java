package com.jj.util.mail;

import java.io.File;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailConstants;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.jj.util.ListUtil;
import com.jj.util.LongUtil;
import com.jj.util.StringUtil;
import com.jj.util.dto.MailDTO;
import com.jj.util.logger.LoggerUtil;

public class EmailUtil {

    private EmailUtil() {

    }

    @Inject
    @LoggerUtil
    private static Logger logger;

    /**
     * Faz o envio de emails através de um servidor smtp para uma lista de usuarios <br>
     * Possivel enviar aquivos como anexo. <br>
     * Necessita ser enviado autentiticao para conectar-se ao servidor
     * 
     * @param mailDTO Configuracao do email para o envio. necessario adicionar host e autentificacao para acessar o smtp
     * @return Retorna a confirmação do envio do email.
     * 
     */
    public static Boolean enviarEmail(MailDTO mailDTO) {

        if (StringUtil.isNullOrEmpty(mailDTO.getMensagem()) || StringUtil.isNullOrEmpty(mailDTO.getAssunto())
                || ListUtil.isNullOrEmpty(mailDTO.getDestinatarios())) {
            return Boolean.FALSE;
        }

        try {
            HtmlEmail email = new HtmlEmail();
            EmailAttachment attachment = null;

            if (!ListUtil.isNullOrEmpty(mailDTO.getAnexo())) {

                for (File arquivo : mailDTO.getAnexo()) {
                    if (arquivo != null) {
                        attachment = new EmailAttachment();
                        attachment.setPath(arquivo.getPath()); // Obtem o caminho do arquivo
                        attachment.setDisposition(EmailAttachment.ATTACHMENT);
                        attachment.setDescription(arquivo.getName());
                        attachment.setName(arquivo.getName()); // Obtem o nome do arquivo

                        email.attach(attachment);

                    }

                }
            }

            email.setAuthentication(mailDTO.getEmailRemetente(), mailDTO.getSenhaEmail()); // Autentificacao do servidor
            email.setHostName(mailDTO.getHost()); // host do email. Ex: smtp.jj.com.br
            email.setCharset(EmailConstants.ISO_8859_1); // charset para html
            email.setFrom(mailDTO.getEmailRemetente(), mailDTO.getNomeRemetente()); // usuario e nome do remetente
            email.setSubject(mailDTO.getAssunto()); // assunto da mensagem
            email.setHtmlMsg(mailDTO.getMensagem()); // corpo da mensagem formatada em html

            // verifica se é o servidor usa ssl e define a porta para o protocolo.
            if (mailDTO.getFlagSsl().equals(LongUtil.UM)) {
                email.setSslSmtpPort(mailDTO.getPorta().toString());
            } else {
                email.setSmtpPort(mailDTO.getPorta().intValue());
            }
            
            email.setStartTLSRequired(mailDTO.getStartTLS());

            // LISTA DOS USUARIOS QUE RECEBERÃO UMA CÓPIA DO EMAIL
            for (String destinatario : mailDTO.getDestinatarios()) {
                email.addBcc(destinatario, mailDTO.getNomeRemetente());
            }

            email.send(); // send the email

            return Boolean.TRUE;
        } catch (EmailException e) {
            System.out.println("==> ERRO AO ENVIAR EMAIL = " + e.getMessage() + " | CAUSA= " + e.getCause().getMessage());
            return Boolean.FALSE;
        }

    }

}