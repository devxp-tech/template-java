package devxp-tech.apptemplate.model.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import devxp-tech.apptemplate.model.UserREQ;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_sample_table")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class UserEntity {

    @Id
    @NotNull
    private UUID id;

    @Column
    private String userDocumentNumber;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private UserInfoEntity info;

    @Column
    private ZonedDateTime createdAt;

    @Column
    private ZonedDateTime updatedAt;

    public UserEntity() {
    }

    public UserEntity(UserREQ userREQ) {
        this.id = UUID.randomUUID();
        this.userDocumentNumber = userREQ.getUserDocumentNumber();
        this.info = new UserInfoEntity(userREQ.getName(), userREQ.getEmail());
        this.createdAt = ZonedDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UserEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getUserDocumentNumber() {
        return userDocumentNumber;
    }

    public UserEntity setUserDocumentNumber(String userDocumentNumber) {
        this.userDocumentNumber = userDocumentNumber;
        return this;
    }

    public UserInfoEntity getInfo() {
        return info;
    }

    public UserEntity setInfo(UserInfoEntity info) {
        this.info = info;
        return this;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public UserEntity setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UserEntity setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
