package com.example.phone_book.repository;

import com.example.phone_book.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface ContactRepository extends MongoRepository<Contact, String> {
}
