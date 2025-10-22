package com.wecp.progressive.service;

import com.wecp.progressive.entity.Billing;

import java.util.List;

public interface BillingService {

    List<Billing> getAllBills();

    Billing getBillById(int billingId);

    Integer createBill(Billing billing);

    void deleteBill(int billingId);

    List<Billing> getBillsByPatientId(int patientId);
}
