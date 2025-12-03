package com.reece.addressbooksystem.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstName;
    String lastName;

    @ManyToOne
    @JoinColumn(name="addressBookId", referencedColumnName = "id")
    private AddressBook addressBook;

    public Contact(String firstName, String lastName, AddressBook addressBook) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressBook = addressBook;
    }
}
