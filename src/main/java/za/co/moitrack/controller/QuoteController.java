package za.co.moitrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import za.co.moitrack.data.model.ServerResponse;
import za.co.moitrack.data.model.User;
import za.co.moitrack.service.MailService;

@RestController
@RequestMapping(value=QuoteController.QUOTE)
public class QuoteController extends MainController {
    public static final String QUOTE = "quote";
    private static final String REQUEST = "request";
    public static final String HOME_REQUEST = HOME + REQUEST;
    @Autowired
    private MailService mailService;
    @Autowired
    private MessageSource messageSource;

    @PutMapping(HOME_REQUEST)
    public ServerResponse requestQuote(@RequestBody User user) {
        String message;
        boolean status = true;
        ServerResponse serverResponse = new ServerResponse();
        try {
            mailService.requestQuote(user);
            message = messageSource.getMessage("quote.request.success", null, null);
        } catch(Exception ex) {
            message = messageSource.getMessage("quote.request.failure", null, null);
            status = false;
        }
        serverResponse.setStatus(status);
        serverResponse.setMessage(message);

        return serverResponse;
    }
}
