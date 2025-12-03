package com.reece.addressbooksystem.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "phone_number")
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String number;

    @ManyToOne
    @JoinColumn(name="contactId", referencedColumnName = "id")
    private Contact contact;

    public PhoneNumber(String number, Contact contact){
        this.number = number;
        this.contact = contact;
    }
    public PhoneNumber(Long id, String number){
        this.number = number;
        this.id = id;
    }
}
