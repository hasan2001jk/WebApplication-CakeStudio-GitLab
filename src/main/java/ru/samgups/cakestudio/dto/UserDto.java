package ru.samgups.cakestudio.dto;

public class UserDto {
    private Long id;
    private String fio;
    private String email;
    private String phone;
    private Double discount = 10.0;

    private String password;
    // Constructors, getters, and setters


    public UserDto() {
    }

    public UserDto(Long id, String fio, String email, String phone, Double discount,String password) {
        this.id = id;
        this.fio = fio;
        this.email = email;
        this.phone = phone;
        this.discount = discount;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
