package com.timon.rhouse.domain;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity(name=AbstractClient.TBL_NAME)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class AbstractClient extends AbstractEntity<Long> {

    public static final String TBL_NAME="clients";

    public static final String FLD_NAME="name";
    public static final String FLD_USERNAME="username";
    public static final String FLD_EMAIL="email";
    public static final String FLD_PASSWORD="password";
    public static final String FLD_ROLES="roles";
    public static final String FLD_SURNAME="surname";


    @Column(name = FLD_USERNAME,unique = true,nullable = false)
    @NotNull(message = "username can't be null")
    @Size(min = 2,max =30,message = "Length of username can be 2-30")
    private String username;

    @Column(name = FLD_NAME,unique = false,nullable = false)
    @NotNull(message = "name of Client cant  be null")
    @Size(min = 2,max =30,message = "Length of Client name  can be 2-30")
    private String name;


    @Column(name = FLD_SURNAME,unique = false,nullable = false)
    @NotNull(message = "surname of Client cant  be null")
    @Size(min = 2,max =30,message = "Length of Client surname  can be 2-30")
    private String surname;



    @Column(name = FLD_EMAIL,unique = true,nullable = false)
    @NotNull(message = "email of Client cant  be null")
    @Size(min = 2,max =30,message = "Length of Client email  can be 2-30")
    private String email;

    @Column(name = FLD_PASSWORD,unique = false,nullable = false)
    @NotNull(message = "password of Client cant  be null")
    private String password;

    @Column(name = FLD_ROLES,unique = false,nullable = false)
    private String roles;

    public AbstractClient(){

    }

    public AbstractClient(String username,String name,String surname,String email, String password,String roles) {
        this.surname=surname;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = hashPassword(password);
        this.roles=roles;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public void setHashedPass(String password) {
        this.password = password;
    }

    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractClient)) return false;
        if (!super.equals(o)) return false;
        AbstractClient that = (AbstractClient) o;
        return username.equals(that.username) &&
                name.equals(that.name) &&
                surname.equals(that.surname) &&
                email.equals(that.email) &&
                password.equals(that.password)&&
                roles.equals(that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, name, surname, email, password,roles);
    }
}
