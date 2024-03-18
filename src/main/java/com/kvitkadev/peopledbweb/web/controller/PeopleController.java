package com.kvitkadev.peopledbweb.web.controller;

import com.kvitkadev.peopledbweb.biz.model.Person;
import com.kvitkadev.peopledbweb.biz.service.PersonService;
import com.kvitkadev.peopledbweb.data.FileStorageRepository;
import com.kvitkadev.peopledbweb.data.PersonRepository;
import com.kvitkadev.peopledbweb.exception.StorageException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Controller
@RequestMapping("/people")
@Log4j2
public class PeopleController {

    public static final String DISPO = """
             attachment; filename="%s"
            """;
    private PersonRepository personRepository;
    private FileStorageRepository fileStorageRepository;
    private PersonService personService;

    public PeopleController(PersonRepository personRepository,
                            FileStorageRepository fileStorageRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.fileStorageRepository = fileStorageRepository;
        this.personService = personService;
    }

    @ModelAttribute("people")
    public Iterable<Person> getPeople() {
        return personRepository.findAll();
    }

    @ModelAttribute
    public Person getPerson() {
        return new Person();
    }

    @GetMapping
    public String showPeoplePage() {
        return "people";
    }

    @GetMapping("/images/{resource}")
    public ResponseEntity<Resource> getResource(@PathVariable String resource) {
        System.out.println("getResource: " + resource);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, format(DISPO, resource))
                .body(fileStorageRepository.findByName(resource));
    }

//    @ExceptionHandler (StorageException.class)
//    public String handleException() {
//
//    }

    @PostMapping
    public String savePerson(Model model, @Valid Person person, Errors errors, @RequestParam("photoFilename") MultipartFile photoFile) throws IOException {
        log.info(person);
        log.info("Filename: " + photoFile.getOriginalFilename());
        log.info("File size: " + photoFile.getSize());
        log.info("Error: " + errors);
        if (!errors.hasErrors()) {
            try {
                personService.save(person, photoFile.getInputStream());
                personRepository.save(person);
                fileStorageRepository.save(photoFile.getOriginalFilename(), photoFile.getInputStream());
                return "redirect:people";
            } catch (StorageException e) {
                model.addAttribute("errorMsg", "System is currently unable to accept photo files at this time.");
                return "people";
            }
        }
        return "people";
    }

    @PostMapping(params = "delete=true")
    public String deletePeople(@RequestParam Optional<List<Long>> selections) {
        log.info(selections);
        selections.ifPresent(personRepository::deleteAllById);
        return "redirect:people";
    }

    @PostMapping(params = "edit=true")
    public String editPerson(@RequestParam Optional<List<Long>> selections, Model model) {
        log.info(selections);
        if (selections.isPresent()) {
            Long id = selections.get().getFirst();
            Optional<Person> person = personRepository.findById(id);
            model.addAttribute("person", person);
        }
        return "people";
    }

}
