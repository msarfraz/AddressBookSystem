package com.reece.addressbooksystem.service;

import com.reece.addressbooksystem.dto.ContactDTO;
import com.reece.addressbooksystem.model.AddressBook;
import com.reece.addressbooksystem.model.Contact;
import com.reece.addressbooksystem.model.PhoneNumber;
import com.reece.addressbooksystem.repository.AddressBookRepository;
import com.reece.addressbooksystem.repository.ContactRepository;
import com.reece.addressbooksystem.repository.PhoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class ContactServiceImpl implements ContactService{
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    AddressBookRepository addressBookRepository;

    @Override
    public ContactDTO saveContact(ContactDTO contactDTO, Long addressBookId) {
        var addressBook = addressBookRepository.findById(addressBookId);
        if(!addressBook.isPresent())
            return null;
        Contact contactModel = new Contact(contactDTO.getFirstName(), contactDTO.getLastName(), addressBook.get());

        var insertedContact = contactRepository.save(contactModel);
         contactDTO.setId(insertedContact.getId());
        contactModel.setId(insertedContact.getId());

        for(var number: contactDTO.getPhoneNumbers()){
            PhoneNumber phoneNumber = new PhoneNumber(number, insertedContact);
            phoneRepository.save(phoneNumber);
        }
        return getContact(insertedContact.getId());
    }

    @Override
    public List<ContactDTO> fetchContactList(Long addressBookId) {
        List<ContactDTO> contactDTOS = new ArrayList<>();
        List<Contact> contacts = contactRepository.findAllByAddressBookId(addressBookId);
        for (Contact contact: contacts){
            List<PhoneNumber> phoneNumbers = phoneRepository.findAllByContactId(contact.getId());
            ContactDTO contactDTO = new ContactDTO(contact, phoneNumbers);
            contactDTOS.add(contactDTO);
        }
        return contactDTOS;
    }
    @Override
    public List<ContactDTO> fetchContactList() {
        HashMap<String, ContactDTO> contactDTOS = new HashMap<>();
        List<Contact> contacts = contactRepository.findAll();
        for (Contact contact: contacts){
            List<PhoneNumber> phoneNumbers = phoneRepository.findAllByContactId(contact.getId());

            ContactDTO contactDTO = new ContactDTO(contact, phoneNumbers);
            if(contactDTOS.containsKey(contactDTO.getFullName())){
                // merge both contacts
                contactDTOS.get(contactDTO.getFullName()).merge(phoneNumbers);
            }
            else
            contactDTOS.put(contactDTO.getFullName(), contactDTO);
        }
        return contactDTOS.values().stream().toList();
    }

    @Override
    public ContactDTO getContact(Long id) {
        var contact = contactRepository.findById(id);

        if(contact.isPresent()){
            var phones = phoneRepository.findAllByContactId(id);
            ContactDTO contactDTO = new ContactDTO(contact.get(), phones);
            return contactDTO;
        }

        return null;
    }

    @Override
    public ContactDTO updateContact(ContactDTO contactDTO, Long contactId) {
        var contact = contactRepository.findById(contactId);
        if(contact.isPresent()){
            contact.get().setFirstName(contactDTO.getFirstName());
            contact.get().setLastName(contactDTO.getLastName());
            contactRepository.save(contact.get());

            List<PhoneNumber> phoneNumbers = phoneRepository.findAllByContactId(contactId);
            List<String> updatedNumbers = new ArrayList<>(contactDTO.getPhoneNumbers());

            for(var phoneNumber: phoneNumbers){
                if(updatedNumbers.stream().filter(p-> p.equalsIgnoreCase(phoneNumber.getNumber())).count()> 0){
                    //phone number exist in the update
                    updatedNumbers.removeIf(u->u.equalsIgnoreCase(phoneNumber.getNumber()));
                }
                else{
                    // phone number doesn't exist in the update, it should be deleted in repo
                    phoneRepository.deleteById(phoneNumber.getId());
                }
            }
            //add the remaining phones in repository
            for(var phoneNumber: updatedNumbers){
                phoneRepository.save(new PhoneNumber(phoneNumber, contact.get()));
            }
            return getContact(contactId);
        }
        return null;

    }
    @Transactional
    @Override
    public boolean deleteContactById(Long contactId) {
        var contact = contactRepository.findById(contactId);
        if(contact.isPresent()){
            phoneRepository.deleteAllByContactId(contactId);
            contactRepository.deleteById(contactId);
            return true;

        }
        return false;
    }
}
