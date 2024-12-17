package se.yrgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.data.AddressRepository;
import se.yrgo.domain.AddressEntity;
import se.yrgo.domain.UserEntity;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<AddressEntity> getAllAddresses() {return addressRepository.findAll();}
    @Override
    public AddressEntity createAddress(AddressEntity address) {
        return addressRepository.save(address);
    }
    @Override
    public void deleteAddress(Long id){addressRepository.deleteById(id);}

    @Override
    public AddressEntity getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
}

