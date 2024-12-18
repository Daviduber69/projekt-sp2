package se.yrgo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yrgo.data.AddressRepository;
import se.yrgo.domain.AddressEntity;


import java.util.List;
import java.util.ListResourceBundle;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AddressController {

    private final AddressRepository addressRepository;
    @Autowired
    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @RequestMapping(value = "/address", method = RequestMethod.GET)
    public AddressList getAllAddresses() {
        List<AddressEntity> address = addressRepository.findAll();
        return new AddressList(address);
    }

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public ResponseEntity<AddressEntity> createAddress(@RequestBody AddressEntity address) {
        AddressEntity createAddress = addressRepository.save(address);
        return new ResponseEntity<>(createAddress, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/address/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
