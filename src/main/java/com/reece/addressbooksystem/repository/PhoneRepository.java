package com.reece.addressbooksystem.repository;

import com.reece.addressbooksystem.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneNumber, Long> {
    public List<PhoneNumber> findAllByContactId(Long contactId);
    public void deleteAllByContactId(Long contactId);
}
