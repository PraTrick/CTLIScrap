package com.centurylink.xprsr.util;

import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.centurylink.xprsr.dto.TicketData;

public class SendMail {

    private static final String SMTP_HOST_NAME = "mailgate.uswc.uswest.com";
    private static final String emailSubjectTxt = "Test Mail";
    private static final String emailFromAddress = "aa49438@centurylink.com";
    private static final String[] emailList = { "aa49438@centurylink.com" };
    private Integer totalTickets = 0;
    private Integer totalAssignedTickets = 0;
    private Integer totalPendingTickets = 0;
    private Integer totalRestrictedTickets = 0;

    public void sendMail(ArrayList<TicketData> listToDisplay,
            TreeMap<String, String> newListToDisplay, TreeMap<Integer, ArrayList<String>> restrictedList) {

        try {
            System.setProperty("java.net.preferIPv4Stack", "true");

            String message = buildBodyMessage(listToDisplay, newListToDisplay, restrictedList);
            
            System.out.println(message);

            Properties props = System.getProperties();
            props.put("mail.smtp.host", SMTP_HOST_NAME);
            Session session = Session.getDefaultInstance(props, null);

            session.setDebug(true);

            Message msg = new MimeMessage(session);

            InternetAddress addressFrom = new InternetAddress(emailFromAddress);
            msg.setFrom(addressFrom);

            InternetAddress[] addressTo = new InternetAddress[emailList.length];
            for (int i = 0; i < emailList.length; i++) {
                addressTo[i] = new InternetAddress(emailList[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, addressTo);

            msg.setSubject(emailSubjectTxt);
            msg.setContent(message, "text/html");

            Transport.send(msg);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    private String buildBodyMessage(ArrayList<TicketData> listToDisplay,
            TreeMap<String, String> newListToDisplay, TreeMap<Integer, ArrayList<String>> restrictedList) {
        StringBuilder sBuilder = new StringBuilder();
        
        sBuilder.append("<html>");
        sBuilder.append("<head>");
        sBuilder.append("<style type=\"text/css\">");
        sBuilder.append("tr:nth-child(even) {"
                + "background-color: #66C2E0;"
                + "}"
                + "tr:nth-child(odd) {"
                + "background-color: #FFD175;"
                + "}");
        sBuilder.append("</style>");
        sBuilder.append("</head>");
        sBuilder.append("<body>");
        sBuilder.append("<p>Hi Team,</p>");

        sBuilder.append("<p>Please find below the current Ticket assignments.</p>");

        sBuilder.append("<p>Please create the entries in portal and also delegate below tickets to yourself "
                + "and also put them to 'Work In Progress'.</p>");
        
        totalRestrictedTickets = restrictedList.size();
        totalTickets = listToDisplay.size() + totalRestrictedTickets;
        
        for (TicketData ticketInfo : listToDisplay) {
            if(ticketInfo.getStatus().equalsIgnoreCase("Pending"))
                totalPendingTickets++;
            else
                totalAssignedTickets++;
        }
        
        sBuilder.append("Total No. of Tickets: " + totalTickets);
        sBuilder.append("<br>");
        sBuilder.append("Total No. of Assigned Tickets: " + totalAssignedTickets);
        sBuilder.append("<br>");
        sBuilder.append("Total No. of Pending Tickets: " + totalPendingTickets);
        sBuilder.append("<br>");
        sBuilder.append("Total No. of Ignored Tickets: " + totalRestrictedTickets);
        
        sBuilder.append("<br>");
        sBuilder.append("<br>");
        sBuilder.append("<b>New Assigned Tickets</b>");
        sBuilder.append("<br>");

        sBuilder.append("<table border=\"1\" bordercolor=\"#000000\" style=\"background-color:#FFD175\" "
                + "width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" width = \"50%\" style=\"font-size: 11px\">");

        sBuilder.append("<tr>");
        sBuilder.append("<th>Individual+:</th><th>Case ID+:</th>");
        sBuilder.append("</tr>");

        for (Entry<String, String> newTicketInfo : newListToDisplay.entrySet()) {
            sBuilder.append("<tr>");
            sBuilder.append("<td>" + newTicketInfo.getKey() + "</td><td>"
                    + newTicketInfo.getValue() + "</td>");
            sBuilder.append("</tr>");
        }
        
        sBuilder.append("</table>");
        sBuilder.append("<br>");
        sBuilder.append("<b>Tickets</b>");
        sBuilder.append("<br>");

        sBuilder.append("<table border=\"1\" bordercolor=\"#000000\" style=\"background-color:#FFD175\" "
                + "width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" width = \"50%\" style=\"font-size: 11px\">");
        
        sBuilder.append("<tr>");
        sBuilder.append("<th>Type:</th><th>Case ID+:</th><th>Individual+:</th><th>Status:</th><th>Create Time:</th>"
                + "<th>Summary:</th><th>Item:</th><th>Severity:</th><th>Group+:</th>");
        sBuilder.append("</tr>");

        for (TicketData ticketInfo : listToDisplay) {
            sBuilder.append("<tr>");
            sBuilder.append("<td>" + ticketInfo.getType() + "</td><td>"
                    + ticketInfo.getCaseID() + "</td><td>"
                    + ticketInfo.getIndividual() + "</td><td>"
                    + ticketInfo.getStatus() + "</td><td>"
                    + ticketInfo.getCreateTime() + "</td><td>"
                    + ticketInfo.getSummary() + "</td><td>"
                    + ticketInfo.getItem() + "</td><td>"
                    + ticketInfo.getSeverity() + "</td><td>"
                    + ticketInfo.getGroup() + "</td>");
            sBuilder.append("</tr>");
        }

        sBuilder.append("</table>");
        sBuilder.append("</body></html>");

        return sBuilder.toString();
    }
}
