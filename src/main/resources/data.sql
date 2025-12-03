INSERT INTO address_book (name) VALUES ( 'Customer');
INSERT INTO address_book (name) VALUES ('Personal');

INSERT INTO contact (first_name, last_name, address_book_id) VALUES ('joan', 'ark', 1);
INSERT INTO contact (first_name, last_name, address_book_id) VALUES ('adam', 'langley', 1);
INSERT INTO contact (first_name, last_name, address_book_id) VALUES ('mansoor', 'sarfraz', 2);

INSERT INTO phone_number (contact_id, number) VALUES (1, '123445677');
INSERT INTO phone_number (contact_id, number) VALUES (1, '776654321');
INSERT INTO phone_number (contact_id, number) VALUES (2, '873394849');
INSERT INTO phone_number (contact_id, number) VALUES (3, '785938389');

