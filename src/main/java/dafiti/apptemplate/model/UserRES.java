package devxp-tech.apptemplate.model;

import devxp-tech.apptemplate.model.entity.UserEntity;

import java.util.UUID;

public class UserRES {

    private UUID id;
    private String userDocumentNumber;
    private String name;
    private String email;

    public UserRES() {
    }

    public UserRES(UserEntity entity) {
        this.id = entity.getId();
        this.userDocumentNumber = entity.getUserDocumentNumber();
        this.name = entity.getInfo().getName();
        this.email = entity.getInfo().getEmail();
    }

    public UUID getId() {
        return id;
    }

    public UserRES setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getUserDocumentNumber() {
        return userDocumentNumber;
    }

    public UserRES setUserDocumentNumber(String userDocumentNumber) {
        this.userDocumentNumber = userDocumentNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserRES setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRES setEmail(String email) {
        this.email = email;
        return this;
    }
}
