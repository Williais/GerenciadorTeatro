package br.edu.ifpb.teatro.util;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class Mensageiro {

    private static Session conectarServidor() {
        final String emailRemetente = "projetofinalpoo@gmail.com";
        final String senhaDeApp = "iigo eihk xjaz psoy";

        //config do servidor do gmail - vi que era padrão isso
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        //autenticando
        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailRemetente, senhaDeApp);
            }
        });
    }

    public static void enviarContrato(String emailDestinatario, String nomeArquivo) {
        try {
            Message message = new MimeMessage(conectarServidor());
            message.setFrom(new InternetAddress("projetofinalpoo@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailDestinatario));
            message.setSubject("Contrato de aluguel do teatro");

            MimeBodyPart texto = new MimeBodyPart();
            texto.setText("Meu Nobre, Segue o Anexo do contrato de aluguel do teatro");

            MimeBodyPart anexo = new MimeBodyPart();
            File arquivo = new File(nomeArquivo);
            anexo.attachFile(arquivo);

            Multipart pacote = new MimeMultipart();// junta tanto o texto quanto o anexo em um pacote
            pacote.addBodyPart(texto);
            pacote.addBodyPart(anexo);

            message.setContent(pacote);// usado pra colocar o pacote na mensagem

            System.out.println("enviando e-mail para " + emailDestinatario);
            Transport.send(message); // enviando
            System.out.println("e-mail enviado com sucesso com o anexo: " + nomeArquivo);

        } catch (Exception e) {
            System.out.println("erro ao tentar enviar o e-mail: " + e.getMessage());
        }
    }

    public static void enviarCodigoRecuperacao(String emailDestinatario, String codigoGerado) {
        try {
            Message message = new MimeMessage(conectarServidor());
            message.setFrom(new InternetAddress("projetofinalpoo@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailDestinatario));
            message.setSubject("Código de Recuperação - Sistema Teatro");

            message.setText("Fala, Meu Nobre!\n\nSeu código de verificação para redefinir a senha é: " + codigoGerado);

            System.out.println("enviando codigo de recuperação para " + emailDestinatario);
            Transport.send(message);
            System.out.println("codigo enviado com sucesso");

        } catch (Exception e) {
            throw new RuntimeException("Falha na rede ao tentar enviar o código: " + e.getMessage());
        }
    }
}
