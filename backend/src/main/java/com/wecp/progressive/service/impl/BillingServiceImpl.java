package com.wecp.progressive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wecp.progressive.entity.Billing;
import com.wecp.progressive.repository.BillingRepository;
import com.wecp.progressive.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService  {
    @Autowired
    private BillingRepository billingRepository;

    @Override
    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    @Override
    public Billing getBillById(int billingId) {
        return billingRepository.findById(billingId).get();
    }

    @Override
    public Integer createBill(Billing billing) {
        return billingRepository.save(billing).getBillingId();
    }

    @Override
    public void deleteBill(int billingId) {
        if(billingRepository.existsById(billingId)){
            billingRepository.deleteById(billingId);
        }
    }

    @Override
    public List<Billing> getBillsByPatientId(int patientId) {
        return billingRepository.findByPatient_PatientId(patientId);
    }

}