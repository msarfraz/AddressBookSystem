package com.reece.addressbooksystem.api;

import com.reece.addressbooksystem.dto.AddressBookDTO;
import com.reece.addressbooksystem.dto.ContactDTO;
import com.reece.addressbooksystem.model.Contact;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/address-books")
public interface AddressBookAPI {
    @Operation(summary = "Create a new address book", responses = {
            @ApiResponse(description = "Address Book Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressBookDTO.class))),
             })
    @PostMapping
    ResponseEntity<Contact> createAddressBook(@RequestBody AddressBookDTO addressBook);

    @Operation(summary = "Get an address book by id", responses = {
            @ApiResponse(description = "Address Book Details", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressBookDTO.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressBookDTO> getAddressBook(
            @Parameter(description = "address book id")
            @PathVariable(
            name = "id",
            required = true)
                                   int addressBookId) ;
    @Operation(summary = "Get all address books", responses = {
            @ApiResponse(description = "Address Books List", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressBookDTO[].class))),
    })
    @GetMapping
    public ResponseEntity<List<AddressBookDTO>> getAllAddressBooks() ;
}
