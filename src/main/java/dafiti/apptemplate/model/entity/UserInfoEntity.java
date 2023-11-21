package devxp-tech.apptemplate.model.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class UserInfoEntity {
    private String name;
    private String email;

    public UserInfoEntity() {
    }

    public UserInfoEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public UserInfoEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserInfoEntity setEmail(String email) {
        this.email = email;
        return this;
    }
}
