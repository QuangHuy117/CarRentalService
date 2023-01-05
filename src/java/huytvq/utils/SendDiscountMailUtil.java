/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytvq.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
public class SendDiscountMailUtil implements Serializable {

    private static final String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String digits = "0123456789";
    private static final String alpha_digit = alphabets + digits;

    public String getRandomCodeDiscount() {
        String code = "";
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            int number = rnd.nextInt(alpha_digit.length() - 1);
            code += alpha_digit.charAt(number);
        }
        return code;
    }

    public boolean SendDiscountMail(String toEmail, String codeConfirm) {
        boolean test = false;
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 10);  // number of days to add      
        String date = (String) (formattedDate.format(c.getTime()));

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
            message.setSubject("Discount Code");
            message.setText("Thank you for registering our Car Rental Service. \n"
                    + "You have received a discount code for your first bill. \n"
                    + "Please enter the code below to receive a discount."
                    + "\n\n " + codeConfirm + "\n\n"
                    + "Discount code will expired in " + date + "\n" 
                    + "* This is an automatically generated email, please do not reply.\n");

            Transport.send(message);
            test = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }

}
