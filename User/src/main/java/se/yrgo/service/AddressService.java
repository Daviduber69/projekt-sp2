package se.yrgo.service;

import se.yrgo.domain.AddressEntity;

import java.util.List;

public interface AddressService {
    List<AddressEntity> getAllAddresses();

    public AddressEntity createAddress(AddressEntity address);
    public void deleteAddress(Long id);

    AddressEntity getAddressById(Long id);
}
