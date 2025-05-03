package com.example.demo.data.dto;

/*
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
*/
import com.example.demo.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

//@JsonPropertyOrder({"id", "first_name", "last_name", "address", "gender"})
@JsonFilter("PersonFilter")
public class PersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

//    @JsonProperty("first_name")
    private String firstName;

//    @JsonProperty("last_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDay;

    private String address;

    private String sensitiveData;

//    @JsonIgnore
    @JsonSerialize(using = GenderSerializer.class)
    private String gender;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phonenumber;

    public PersonDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSensitiveData() {
        return sensitiveData;
    }

    public void setSensitiveData(String sensitiveData) {
        this.sensitiveData = sensitiveData;
    }

}
