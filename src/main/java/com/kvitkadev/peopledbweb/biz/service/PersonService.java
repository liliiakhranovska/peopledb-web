package com.kvitkadev.peopledbweb.biz.service;

import com.kvitkadev.peopledbweb.biz.model.Person;
import com.kvitkadev.peopledbweb.data.FileStorageRepository;
import com.kvitkadev.peopledbweb.data.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final FileStorageRepository storageRepository;

    public PersonService(PersonRepository personRepository, FileStorageRepository storageRepository) {
        this.personRepository = personRepository;
        this.storageRepository = storageRepository;
    }

    public <S extends Person> S save(S entity) {
        return personRepository.save(entity);
    }

    public Optional<Person> findById(Long aLong) {
        return personRepository.findById(aLong);
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public void deleteAllById(Iterable<? extends Long> longs) {
        personRepository.deleteAllById(longs);
    }
}
