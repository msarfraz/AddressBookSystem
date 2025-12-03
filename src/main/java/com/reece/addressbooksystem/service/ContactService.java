package com.reece.addressbooksystem.service;

import com.reece.addressbooksystem.dto.ContactDTO;
import com.reece.addressbooksystem.model.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {
    // Save operation
    ContactDTO saveContact(ContactDTO contact, Long addressBookId);

    // Read operation
    List<ContactDTO> fetchContactList(Long addressBookId);

    // Read operation
    List<ContactDTO> fetchContactList();

    // Get operation
    ContactDTO getContact(Long id);

    // Update operation
    ContactDTO updateContact(ContactDTO contact,
                                Long contactId);

    // Delete operation
    boolean deleteContactById(Long contactId);
}
