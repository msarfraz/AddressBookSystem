package com.reece.addressbooksystem.api;

import com.reece.addressbooksystem.dto.AddressBookDTO;
import com.reece.addressbooksystem.dto.ContactDTO;
import com.reece.addressbooksystem.model.Contact;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RequestMapping("/api")
public interface ContactAPI {
    @Operation(summary = "Get all contacts of address book", responses = {
            @ApiResponse(description = "Contact Details List", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO[].class))),
    })
   @GetMapping("v1/address-books/{addressBookId}/contacts")
    public List<ContactDTO> getAllAddressbookContacts(@PathVariable Long addressBookId) ;

    @Operation(summary = "Get all unique contacts from all address books", responses = {
            @ApiResponse(description = "Contact Details List", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO[].class))),
    })
    @GetMapping("v1/contacts")
    public List<ContactDTO> getAllContacts() ;

    @Operation(summary = "Get contact details", responses = {
            @ApiResponse(description = "Contact Details", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))),
    })
    @GetMapping("v1/contacts/{contactId}")
    public ResponseEntity<ContactDTO> getContact(
            @Parameter(description = "contact id")
            @PathVariable(
            name = "contactId",
            required = true)
                                   Long contactId
            ) ;

    @Operation(summary = "Create a new contact", responses = {
            @ApiResponse(description = "Contact Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))),
    })
    @PostMapping("v1/address-books/{addressBookId}/contacts")
    ResponseEntity<ContactDTO> createContact(@PathVariable Long addressBookId, @RequestBody ContactDTO contact);


    @Operation(summary = "Delete a contact", responses = {
            @ApiResponse(description = "Successful Deletion", responseCode = "204", content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
            })
    @DeleteMapping("v1/contacts/{contactId}")
    public ResponseEntity deleteContact(
            @Parameter(description = "contact id")
            @PathVariable( name = "contactId", required = true) Long contactId) ;
}
