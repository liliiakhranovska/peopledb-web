package com.kvitkadev.peopledbweb.data;

import com.kvitkadev.peopledbweb.biz.model.Person;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//@Component
public class PersonDataLoader implements ApplicationRunner {

    private PersonRepository personRepository;

    public PersonDataLoader(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (personRepository.count() < 1) {
            List<Person> people = List.of(
                    new Person(null, "Pete", "Snake", LocalDate.of(1950, 1, 1), "dummy@sample.com", new BigDecimal("50000"), null),
                    new Person(null, "Jenifer", "Smith", LocalDate.of(1960, 2, 1), "dummy@sample.com",  new BigDecimal("60000"), null),
                    new Person(null, "Jackson", "Snake", LocalDate.of(1970, 3, 1), "dummy@sample.com", new BigDecimal("70000"), null),
                    new Person(null, "Mark", "Snake", LocalDate.of(1980, 4, 1), "dummy@sample.com", new BigDecimal("80000"), null),
                    new Person(null, "Vishnu", "McGuire", LocalDate.of(1980, 4, 1), "dummy@sample.com", new BigDecimal("80000"), null),
                    new Person(null, "Alice", "Smith", LocalDate.of(1980, 4, 1), "dummy@sample.com", new BigDecimal("80000"), null),
                    new Person(null, "Akira", "Kim", LocalDate.of(1980, 4, 1), "dummy@sample.com", new BigDecimal("80000"), null));
            personRepository.saveAll(people);
        }
    }
}
