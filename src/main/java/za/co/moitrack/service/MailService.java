package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import za.co.moitrack.data.model.User;
import za.co.moitrack.data.model.Vehicle;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class MailService {
    @Autowired
    private MailContentBuilder mailContentBuilder;
    @Autowired
    private JavaMailSender mailSender;
    private Log log = LogFactory.getLog(this.getClass().getName());
    @Value("${message.from}")
    private String from;
    @Value("${email.recipient.list}")
    private String emailList;

    public void sendNumberOfReportingUnits(int fullVehicleList, Collection<Vehicle> vehicles, List<Vehicle> trackingUnits, boolean daily) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(from);
            messageHelper.setTo(from);

            List<String> myList = this.createEmailList();
            for(String email: myList) {
                messageHelper.addCc(email);
            }

            messageHelper.setSubject("Moitrack - Unit Health Reporting");
            String content = mailContentBuilder.buildReviewLetter(fullVehicleList, vehicles, trackingUnits.size(), daily);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            log.info(e);
        }
    }

    public void requestQuote(User user) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(from);
            messageHelper.setTo(from);

            List<String> myList = this.createEmailList();
            for(String email: myList) {
                messageHelper.addCc(email);
            }

            messageHelper.setSubject("Moitrack App - Request Quote");
            String content = mailContentBuilder.buildRequestQuoteEmail(user);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            log.info(e);
        }
    }

    private List<String> createEmailList() {
        return Arrays.asList(emailList.split(","));
    }
}
