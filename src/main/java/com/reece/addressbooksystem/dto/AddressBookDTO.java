package com.reece.addressbooksystem.dto;

import com.reece.addressbooksystem.model.Contact;
import com.reece.addressbooksystem.model.PhoneNumber;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddressBookDTO {
    private String name;

    private List<Contact> contacts;
    public AddressBookDTO(){
        this.contacts = new ArrayList<>();
    }

}
