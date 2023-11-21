package devxp-tech.apptemplate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserMailNotification {
    @Email
    private String mailTo;
    @NotEmpty
    private String message;

    public String getMailTo() {
        return mailTo;
    }

    public UserMailNotification setMailTo(String mailTo) {
        this.mailTo = mailTo;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public UserMailNotification setMessage(String message) {
        this.message = message;
        return this;
    }
}
