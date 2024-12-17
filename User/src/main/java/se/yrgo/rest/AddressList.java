package se.yrgo.rest;

import se.yrgo.domain.AddressEntity;
import se.yrgo.domain.UserEntity;

import java.util.List;

public class AddressList {
    private List<AddressEntity> address;

    public AddressList() {}

    public AddressList(List<AddressEntity> address) {this.address = address;}

    public List<AddressEntity> getAddress() {
        return address;
    }

    public void setAddress(List<AddressEntity> address) {
        this.address = address;
    }
}
