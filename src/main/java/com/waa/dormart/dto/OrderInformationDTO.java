package com.waa.dormart.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class OrderInformationDTO {

    @NotEmpty
    private String cardNumber;

    @Pattern(regexp = "^[0-9]{3,4}$", message = "{model.regex.error}")
    private String cvv;

    @NotEmpty(message = "{model.notEmpty.error}")
    private String city;

    @NotEmpty(message = "{model.notEmpty.error}")
    private String state;

    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "{model.regex.error}")
    private String zipcode;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
