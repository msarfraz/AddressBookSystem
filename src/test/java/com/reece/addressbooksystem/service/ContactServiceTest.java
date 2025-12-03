package com.reece.addressbooksystem.service;

import com.reece.addressbooksystem.dto.ContactDTO;
import com.reece.addressbooksystem.model.AddressBook;
import com.reece.addressbooksystem.model.Contact;
import com.reece.addressbooksystem.model.PhoneNumber;
import com.reece.addressbooksystem.repository.AddressBookRepository;
import com.reece.addressbooksystem.repository.ContactRepository;
import com.reece.addressbooksystem.repository.PhoneRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {
    @InjectMocks
    ContactServiceImpl contactService;

    @Mock
    ContactRepository contactRepository;
    @Mock
    PhoneRepository phoneRepository;
    @Mock
    AddressBookRepository addressBookRepository;

    Contact personalContact;
    Contact customerContact1;
    Contact customerContact2;
    PhoneNumber phoneNumber1, phoneNumber2, phoneNumber3;


    @BeforeEach
    public void init(){
        personalContact = new Contact(1l, "Mansoor", "Sarfraz", new AddressBook(1l, "Customer"));
        customerContact1 = new Contact(2l, "Eva", "Wong", new AddressBook(1l, "Customer"));
        customerContact2 = new Contact(3l, "Eva", "Wong", new AddressBook(2l, "Personal"));

        phoneNumber1 = new PhoneNumber(1l, "11111", personalContact);
        phoneNumber2 = new PhoneNumber(2l, "22222", customerContact1);
        phoneNumber3 = new PhoneNumber(3l, "33333", customerContact2);



    }

    @Test
    public void fetchContactList_success(){
        when(contactRepository.findAllByAddressBookId(1l)).thenReturn(List.of(personalContact, customerContact1));
        when(phoneRepository.findAllByContactId(1l)).thenReturn(List.of(phoneNumber1));
        when(phoneRepository.findAllByContactId(2l)).thenReturn(List.of(phoneNumber2));

        List<ContactDTO> contacts = contactService.fetchContactList(1l);

        Assertions.assertEquals(2, contacts.size());
        Assertions.assertEquals(1, contacts.get(0).getPhoneNumbers().size());
    }
    @Test
    public void fetchContactList_empty(){
        when(contactRepository.findAllByAddressBookId(1l)).thenReturn(List.of());

        List<ContactDTO> contacts = contactService.fetchContactList(1l);

        Assertions.assertEquals(0, contacts.size());
     }
    @Test
    public void fetchAllContactList_success(){
        when(contactRepository.findAll()).thenReturn(List.of(personalContact, customerContact1, customerContact2));
        when(phoneRepository.findAllByContactId(1l)).thenReturn(List.of(phoneNumber1));
        when(phoneRepository.findAllByContactId(2l)).thenReturn(List.of(phoneNumber2));
        when(phoneRepository.findAllByContactId(3l)).thenReturn(List.of(phoneNumber3));

        List<ContactDTO> contacts = contactService.fetchContactList();

        Assertions.assertEquals(2, contacts.size());
        Assertions.assertEquals(2, contacts.get(0).getPhoneNumbers().size());// merged contact
        Assertions.assertEquals(1, contacts.get(1).getPhoneNumbers().size());
    }

    @Test
    public void fetchAllContactList_empty(){
        when(contactRepository.findAll()).thenReturn(List.of());

        List<ContactDTO> contacts = contactService.fetchContactList();

        Assertions.assertEquals(0, contacts.size());
    }

    @Test
    public void getContact_success(){
        when(contactRepository.findById(1l)).thenReturn(Optional.of(personalContact));
        when(phoneRepository.findAllByContactId(1l)).thenReturn(List.of(phoneNumber1));

        ContactDTO contact = contactService.getContact(1l);

        Assertions.assertEquals(1, contact.getId());
        Assertions.assertEquals(1, contact.getPhoneNumbers().size());
    }
    @Test
    public void getContact_notfound(){
        when(contactRepository.findById(1l)).thenReturn(Optional.empty());

        ContactDTO contact = contactService.getContact(1l);

        Assertions.assertNull(contact);
    }
    @Test
    public void saveContact_success(){
        ContactDTO contactDTO = new ContactDTO(0l, "mansoor", "sarfraz", "mansoor sarfraz", List.of("12345"));
        Contact contactModel = contactDTO.toContact();
        contactModel.setId(1l);
        when(addressBookRepository.findById(1l)).thenReturn(Optional.of(new AddressBook(1l, "Personal")));
        when(contactRepository.save(any(Contact.class))).thenReturn(contactModel);
        when(contactRepository.findById(1l)).thenReturn(Optional.of(personalContact));
        when(phoneRepository.findAllByContactId(1l)).thenReturn(List.of(phoneNumber1));

        ContactDTO contact = contactService.saveContact(contactDTO, 1l);

        Assertions.assertEquals(1, contact.getId());
        Assertions.assertEquals(1, contact.getPhoneNumbers().size());
    }
    @Test
    public void saveContact_invalidAddressBook(){
        ContactDTO contactDTO = new ContactDTO(0l, "mansoor", "sarfraz", "mansoor sarfraz", List.of("12345"));
        when(addressBookRepository.findById(1l)).thenReturn(Optional.empty());

        ContactDTO contact = contactService.saveContact(contactDTO, 1l);

        Assertions.assertNull( contact);
    }

    @Test
    public void deleteContact_success(){
        when(contactRepository.findById(1l)).thenReturn(Optional.of(personalContact));
        doNothing().when(contactRepository).deleteById(1l);
        doNothing().when(phoneRepository).deleteAllByContactId(1l);

        boolean result = contactService.deleteContactById( 1l);

        Assertions.assertEquals(true, result);

        verify(contactRepository, times(1)).deleteById(1l);
        verify(phoneRepository, times(1)).deleteAllByContactId(1l);
    }
    @Test
    public void deleteContact_invalidContact(){
        when(contactRepository.findById(1l)).thenReturn(Optional.empty());

        boolean result = contactService.deleteContactById( 1l);

        Assertions.assertEquals(false, result);
    }
    @Test
    public void updateContact_success(){
        ContactDTO contactDTO = new ContactDTO(0l, "mansoor", "sarfraz", "mansoor sarfraz", List.of("11111","22222"));
        Contact contactModel = contactDTO.toContact();
        contactModel.setId(1l);
        when(contactRepository.findById(1l)).thenReturn(Optional.of(contactModel));
        when(contactRepository.save(any(Contact.class))).thenReturn(contactModel);

        doNothing().when(phoneRepository).deleteById(anyLong());
        when(phoneRepository.save(any(PhoneNumber.class))).thenReturn(phoneNumber2);
        when(phoneRepository.findAllByContactId(1l))
                .thenReturn(List.of(new PhoneNumber(1l, "11111"), new PhoneNumber(3l,"33333")))
                .thenReturn(List.of(new PhoneNumber(1l, "11111"), new PhoneNumber(2l,"22222")));


        ContactDTO contact = contactService.updateContact(contactDTO,1l);

        Assertions.assertEquals(1, contact.getId());
        Assertions.assertEquals(2, contact.getPhoneNumbers().size());
        Assertions.assertIterableEquals(List.of("11111","22222"), contact.getPhoneNumbers());
        verify(contactRepository, times(1)).save(any(Contact.class));
        verify(phoneRepository, times(1)).deleteById(3l);
        verify(phoneRepository, times(1)).save(any(PhoneNumber.class));
    }
    @Test
    public void updateContact_invalidContact(){
        ContactDTO contactDTO = new ContactDTO(0l, "mansoor", "sarfraz", "mansoor sarfraz", List.of("12345"));
        when(contactRepository.findById(1l)).thenReturn(Optional.empty());

        ContactDTO contact = contactService.updateContact(contactDTO, 1l);

        Assertions.assertNull( contact);
    }
}
