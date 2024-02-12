package ru.samgups.cakestudio.dto;

public class EmailRequest {

    private String firstName;
    private String subjectFace;

    private String lastName;

    private String city;

    private String deliveryWay;

    private String payMethod;

    private String receivingStation;

    private String dateAndTime;

    private String trainNumber;

    private String email;

    private String phone;

    private String vagonNum;

    // Constructors, getters, and setters


    public String getVagonNum() {
        return vagonNum;
    }

    public void setVagonNum(String vagonNum) {
        this.vagonNum = vagonNum;
    }

    public EmailRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSubjectFace() {
        return subjectFace;
    }

    public void setSubjectFace(String subjectFace) {
        this.subjectFace = subjectFace;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDeliveryWay() {
        return deliveryWay;
    }

    public void setDeliveryWay(String deliveryWay) {
        this.deliveryWay = deliveryWay;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getReceivingStation() {
        return receivingStation;
    }

    public void setReceivingStation(String receivingStation) {
        this.receivingStation = receivingStation;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}