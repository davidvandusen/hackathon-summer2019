package com.tensheet.hackathon.summer2019;

public class AccountingAssociate {

    private Integer id;

    private String email;

    private Integer tenureDays;

    private Double averageRetentionPercent;

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTenureDays() {
        return tenureDays;
    }

    public void setTenureDays(Integer tenureDays) {
        this.tenureDays = tenureDays;
    }

    public Double getAverageRetentionPercent() {
        return averageRetentionPercent;
    }

    public void setAverageRetentionPercent(Double averageRetentionPercent) {
        this.averageRetentionPercent = averageRetentionPercent;
    }
}
