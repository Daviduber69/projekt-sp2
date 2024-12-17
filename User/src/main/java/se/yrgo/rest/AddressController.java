package se.yrgo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yrgo.domain.AddressEntity;
import se.yrgo.domain.UserEntity;
import se.yrgo.service.AddressService;

import java.util.List;
import java.util.ListResourceBundle;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {this.addressService = addressService;}

    @GetMapping
    public AddressList getAllAddresses() {
        List<AddressEntity> address = addressService.getAllAddresses();
        return new AddressList(address);
    }

    @PostMapping
    public ResponseEntity<AddressEntity> createAddress(@RequestBody AddressEntity address) {
        AddressEntity createAddress = addressService.createAddress(address);
        return new ResponseEntity<>(createAddress, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
