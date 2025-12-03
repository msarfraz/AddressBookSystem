package com.reece.addressbooksystem.web;

import com.reece.addressbooksystem.api.ContactAPI;
import com.reece.addressbooksystem.dto.ContactDTO;
import com.reece.addressbooksystem.model.Contact;
import com.reece.addressbooksystem.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class ContactController implements ContactAPI {
    @Autowired
    ContactService contactService;

    @Override
    public List<ContactDTO> getAllAddressbookContacts(Long addressBookId) {
        return contactService.fetchContactList(addressBookId);
    }

    @Override
    public List<ContactDTO> getAllContacts() {
        return contactService.fetchContactList();
    }

    @Override
    public ResponseEntity<ContactDTO> getContact(Long contactId) {
        ContactDTO contact = contactService.getContact(contactId);
        if(contact != null){
            return ResponseEntity.ok(contact) ;
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<ContactDTO> createContact(Long addressBookId, ContactDTO contact) {
        var newContact = contactService.saveContact(contact, addressBookId);
        if(newContact == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.created(URI.create("/"+newContact.getId())).body(newContact);
    }

    @Override
    public ResponseEntity deleteContact(Long contactId) {
        boolean result = contactService.deleteContactById(contactId);
        if(result)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }
}
