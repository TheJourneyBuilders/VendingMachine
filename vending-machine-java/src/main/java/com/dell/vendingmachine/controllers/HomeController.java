package com.dell.vendingmachine.controllers;

import com.dell.vendingmachine.dto.VendingCredit;
import com.dell.vendingmachine.model.Product;
import com.dell.vendingmachine.model.VendingMachine;
import com.dell.vendingmachine.repository.VendingMachineRepository;
import com.dell.vendingmachine.service.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
public class HomeController {

    final
    VendingMachineRepository repository;

    final
    VendingMachineService vendingMachineService;

    public HomeController(VendingMachineRepository repository, VendingMachineService vendingMachineService){
        this.repository = repository;
        this.vendingMachineService = vendingMachineService;
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<VendingMachine> Get(@PathVariable Long id) {
        return new ResponseEntity<VendingMachine>(repository.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping(value="/{id}/add-credit")
    public ResponseEntity<VendingMachine> AddCredit(@PathVariable Long id, @RequestBody VendingCredit credit ) {

        VendingMachine updated = vendingMachineService.AddCredit(id, credit.Credit);
        return new ResponseEntity<VendingMachine>( updated, HttpStatus.OK);
    }

    @PostMapping(value="{id}/buy/{productId}")
    public ResponseEntity<Product> BuyProduct(@PathVariable Long id, @PathVariable Long productId ) {
        Product product = vendingMachineService.BuyProduct(id, productId);
        return new ResponseEntity<Product>( product , HttpStatus.OK);
    }
}
