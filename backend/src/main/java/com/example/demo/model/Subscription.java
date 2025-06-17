package com.example.demo.model;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "subscription", schema = "public")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "reminder_enabled")
    private  boolean reminderEnabled = true;

    @Column(name = "user_email")
    private String userEmail;

    public Subscription() {
    }

    public Subscription(String serviceName, LocalDate startDate, LocalDate endDate, BigDecimal cost, String userEmail) {
        this.serviceName = serviceName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.userEmail = userEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public  String getServiceName(){
        return  serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public boolean isReminderEnabled() {
        return reminderEnabled;
    }

    public void setReminderEnabled(boolean reminderEnabled) {
        this.reminderEnabled = reminderEnabled;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


}
