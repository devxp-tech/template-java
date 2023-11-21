package devxp-tech.apptemplate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserREQ {
    @NotEmpty
    @Pattern(regexp = "\\d{11}", message = "invalid")
    private String userDocumentNumber;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    public String getUserDocumentNumber() {
        return userDocumentNumber;
    }

    public UserREQ setUserDocumentNumber(String userDocumentNumber) {
        this.userDocumentNumber = userDocumentNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserREQ setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserREQ setEmail(String email) {
        this.email = email;
        return this;
    }
}
