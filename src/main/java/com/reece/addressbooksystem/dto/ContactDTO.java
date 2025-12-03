package com.reece.addressbooksystem.dto;

import com.reece.addressbooksystem.model.Contact;
import com.reece.addressbooksystem.model.PhoneNumber;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContactDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private List<String> phoneNumbers;

    public ContactDTO(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumbers = new ArrayList<>();
    }
    public ContactDTO(Contact contact, List<PhoneNumber> phoneNumbers){
        this.id = contact.getId();
        this.firstName = contact.getFirstName();
        this.lastName = contact.getLastName();
        this.fullName = contact.getFirstName() + " " + contact.getLastName();
        this.phoneNumbers = phoneNumbers.stream().map(phoneNumber -> phoneNumber.getNumber()).collect(Collectors.toList());

    }
    public Contact toContact(){
        Contact contact = new Contact();
        contact.setFirstName(this.firstName);
        contact.setLastName(this.lastName);
        return contact;
    }
    public void merge(List<PhoneNumber> phoneNumbers){
        List<String> numbers = phoneNumbers.stream().map(phoneNumber -> phoneNumber.getNumber()).collect(Collectors.toList());
        this.phoneNumbers.addAll(numbers);
    }
}
