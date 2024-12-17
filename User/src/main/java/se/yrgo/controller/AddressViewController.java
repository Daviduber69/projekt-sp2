package se.yrgo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import se.yrgo.domain.AddressEntity;
import se.yrgo.domain.UserEntity;
import se.yrgo.service.AddressService;

import java.util.List;

@Controller
@RequestMapping("website/addresses")
public class AddressViewController {
    private final AddressService addressService;

    @Autowired
    public AddressViewController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/list.html")
    public String getAllAddress(Model model) {
        List<AddressEntity> addresses = addressService.getAllAddresses();
        model.addAttribute("addresses", addresses);
        return "addresses";
    }

    @GetMapping("/address/{id}")
    public String showAddressDetails(@PathVariable Long id, Model model) {
        AddressEntity address = addressService.getAddressById(id);
        model.addAttribute("address", address);
        return "addressDetails";
    }
}
