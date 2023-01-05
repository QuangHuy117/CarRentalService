/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.utils;

import java.io.Serializable;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Admin
 */
public class SendMailUtil implements Serializable {

    public String getRandomCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public boolean sendMail(String toEmail, String nameUser, int codeConfirm) {
        boolean test = false;

        String fromEmail = "huytvqse140522@fpt.edu.vn";
        String password = "tranvanquanghuy1172000";

        try {

            Properties pr = new Properties();
            pr.put("mail.smtp.host", "smtp.gmail.com");
            pr.put("mail.smtp.port", "587");
            pr.put("mail.smtp.auth", "true");
            pr.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(pr, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "Car Rental Service"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("User Email Verification");
            message.setText("Thank you for registering our Car Rental Service. \n"
                    + "Please verify your account using the code below and enter into verification form: "
                    + "\n\n " + codeConfirm + "\n\n"
                    + "* This is an automatically generated email, please do not reply.\n");

            Transport.send(message);
            test = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }

}
