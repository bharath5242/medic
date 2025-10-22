package com.wecp.progressive.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer billingId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    Patient patient;

    double amount;
    Date dateOfIssue;
    Date dueDate;
    String status;
    
    public Billing() {
    }
    
    public Billing(Patient patient, double amount, Date dateOfIssue, Date dueDate, String status) {
        this.patient = patient;
        this.amount = amount;
        this.dateOfIssue = dateOfIssue;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Integer getBillingId() {
        return billingId;
    }
    public void setBillingId(Integer billingId) {
        this.billingId = billingId;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public Date getDateOfIssue() {
        return dateOfIssue;
    }
    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    
}