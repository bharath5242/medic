package com.wecp.progressive.controller;

import com.wecp.progressive.entity.Billing;
import com.wecp.progressive.service.BillingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping
    public ResponseEntity<List<Billing>> getAllBills() {
        return new ResponseEntity<>(billingService.getAllBills(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> createBill(@RequestBody Billing billing) {
        return new ResponseEntity<>(billingService.createBill(billing), HttpStatus.CREATED);
    }

    @DeleteMapping("/{billingId}")
    public ResponseEntity<Integer> deleteBill(@RequestBody Billing billing) {
        billingService.deleteBill(billing.getBillingId());
        return new ResponseEntity<>(billing.getBillingId(), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{billingId}")
    public ResponseEntity<List<Billing>> getBillsByBillingID(@PathVariable int billingId) {
        List<Billing> temp = new ArrayList<>();
        temp.add(billingService.getBillById(billingId));
        return new ResponseEntity<>(temp, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Billing>> getBillsByPatient(@PathVariable int patientId) {
        return new ResponseEntity<>(billingService.getBillsByPatientId(patientId), HttpStatus.OK);
    }
}
