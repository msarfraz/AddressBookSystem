package com.reece.addressbooksystem.repository;
import com.reece.addressbooksystem.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Annotation
@Repository

// Interface extending CrudRepository
public interface ContactRepository
        extends JpaRepository<Contact, Long> {
    public List<Contact> findAllByAddressBookId(Long addressBookId);
}

