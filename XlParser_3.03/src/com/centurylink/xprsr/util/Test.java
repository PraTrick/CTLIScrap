package com.centurylink.xprsr.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Test {

    private static final String SMTP_HOST_NAME = "mailgate.uswc.uswest.com";

    private static final String emailMsgTxt = "Message text";
    private static final String emailSubjectTxt = "subject";
    private static final String emailFromAddress = "aa49438@centurylink.com";
    private static final String[] emailList = { "aa49438@centurylink.com" };

    public static void main(String args[]) throws Exception {
        Test smtpMailSender = new Test();
        smtpMailSender.postMail(emailList, emailSubjectTxt, emailMsgTxt,
                emailFromAddress);
        System.out.println("Sucessfully Sent mail to All Users");
    }

    public void postMail(String recipients[], String subject, String message,
            String from) throws MessagingException {

        System.setProperty("java.net.preferIPv4Stack", "true");

        StringBuilder sBuilder = new StringBuilder();

        message = sBuilder.toString();

        Properties props = System.getProperties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        Session session = Session.getDefaultInstance(props, null);

        session.setDebug(true);

        Message msg = new MimeMessage(session);

        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);

        msg.setSubject(subject);
        msg.setContent(message, "text/html");

        Transport.send(msg);
    }
}
