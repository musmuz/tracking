package za.co.moitrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import za.co.moitrack.data.model.User;
import za.co.moitrack.data.model.Vehicle;

import java.util.Collection;

@Service
public class MailContentBuilder {
    @Autowired
    private TemplateEngine templateEngine;

    public String buildReviewLetter(int fullVehicleList, Collection<Vehicle> vehicles, int numberOfUnits, boolean daily) {
        Context context = new Context();
        context.setVariable("vehicleSize", fullVehicleList);
        context.setVariable("numberOfUnits", numberOfUnits);
//        context.setVariable("total", total);
        context.setVariable("diff", vehicles.size());
        context.setVariable("nonRecordingVehicles", vehicles);
        return daily == true ? templateEngine.process("trackingUnitsReporting", context) : templateEngine.process("trackingUnitsWeeklyReporting", context);
    }

    public String buildRequestQuoteEmail(User user) {
        Context context = new Context();
        context.setVariable("firstName", user.getName());
        context.setVariable("surname", user.getSurname());
        context.setVariable("email", user.getEmailAddress());
        context.setVariable("cellphone", user.getCellphoneNumber());

        return templateEngine.process("requestQuote", context);
    }
}
