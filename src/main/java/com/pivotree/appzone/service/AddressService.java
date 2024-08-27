package com.pivotree.appzone.service;

import com.pivotree.appzone.entity.Address;
import com.pivotree.appzone.entity.User;
import com.pivotree.appzone.intemplate.AddressInput;
import com.pivotree.appzone.intemplate.UpdateAddressInput;
import com.pivotree.appzone.outtemplate.AddressOutput;
import com.pivotree.appzone.repository.AddressRepository;
import com.pivotree.appzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public Address addAddress(AddressInput addressInput) {
        User user = getUserByUsername(addressInput.getUsername());
        Address address = createAddress(addressInput);
        address.setUser(user);
        addressRepository.save(address);
        return address;
    }



    public void save(Address billingAddress) {
        addressRepository.save(billingAddress);
    }

    public List<AddressOutput> getAllAddressesByUsername(String username) {
        List<Address> addresses = addressRepository.findAllByUsername(username);
        return addresses.stream().map(this::convertToOutput).collect(Collectors.toList());
    }

    private AddressOutput convertToOutput(Address address) {
        AddressOutput output = new AddressOutput();
        output.setLine1(address.getLine1());
        output.setLine2(address.getLine2());
        output.setCity(address.getCity());
        output.setState(address.getState());
        output.setCountry(address.getCountry());
        output.setPinCode(address.getPinCode());
        output.setAddressType(address.getAddressType());
        return output;
    }

    private User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findUser(username);
        return optionalUser.orElseThrow(() -> new RuntimeException("User with username " + username + " not found."));
    }



    private Address createAddress(AddressInput addressInput) {
        Address address=new Address(addressInput);
        addressRepository.save(address);
        return address;
    }


    public void updateAddress(UpdateAddressInput addressInput, String username) {
        Long addressId = addressInput.getId();
        if (addressId == null) {
            throw new RuntimeException("Address ID is required");
        }

        // Check if the address with the given ID exists
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isEmpty()) {
            throw new RuntimeException("Address with ID " + addressId + " not found");
        }

        // Get the existing address object from Optional
        Address address = optionalAddress.get();

        // Update the fields of the existing address
        setAddressFields(address, addressInput);

        // Save the updated address
        addressRepository.save(address);
    }

    private void setAddressFields(Address address, UpdateAddressInput addressInput) {
        address.setAddressType(addressInput.getAddressType());
        address.setLine1(addressInput.getLine1());
        address.setLine2(addressInput.getLine2());
        address.setCity(addressInput.getCity());
        address.setState(addressInput.getState());
        address.setCountry(addressInput.getCountry());
        address.setPinCode(addressInput.getPinCode());
    }
}
