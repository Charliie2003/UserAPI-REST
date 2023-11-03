package com.swagger.openapi.service.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String first_name;
    private String second_name;
    private String first_surname;
    private String email;
    private String sex;
    private String sexual_orientation;
    @Indexed(expireAfterSeconds = 86400)
    private Date expireAt;
    private List<String> physical_features;

    private Date birth_date;

    private double money;



    public User() {
        // Constructor vacío necesario para MongoDB
        this.id = UUID.randomUUID().toString(); // Genera un UUID único y lo asigna como el id
        this.expireAt = new Date(System.currentTimeMillis() + 86400000); // Sets expireAt to the current time + 24 hours (86400 seconds)
    }


    public User(String first_name, String second_name, String first_surname, String email, String sex, String sexual_orientation,Date expireAt, List<String> physical_features, Date date_birth, double money) {
        this.id = UUID.randomUUID().toString(); // Genera un UUID único y lo asigna como el id
        this.first_name = first_name;
        this.second_name = second_name;
        this.first_surname = first_surname;
        this.email = email;
        this.sex = sex;
        this.sexual_orientation = sexual_orientation;
        this.expireAt = expireAt;
        this.physical_features = physical_features;
        this.birth_date = date_birth;
        this.money = money;

    }
    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getFirst_surname() {
        return first_surname;
    }

    public void setFirst_surname(String first_surname) {
        this.first_surname = first_surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSexual_orientation() {
        return sexual_orientation;
    }

    public void setSexual_orientation(String sexual_orientation) {
        this.sexual_orientation = sexual_orientation;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public List<String> getPhysical_features() {
        return physical_features;
    }

    public void setPhysical_features(List<String> physical_features) {
        this.physical_features = physical_features;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}





