package com.sao.tutorial.rediscachedemo.controller;

import com.sao.tutorial.rediscachedemo.dto.PersonDto;
import com.sao.tutorial.rediscachedemo.jpa.entity.PersonEntity;
import com.sao.tutorial.rediscachedemo.jpa.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class PersonController {

    private final PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/updatePerson", method = RequestMethod.POST)
    @CachePut(value = "persons", key = "#result.id")
    public PersonDto updatePerson(final PersonDto personDto) {
        final PersonEntity personEntity = new PersonEntity();
        personEntity.setId(personDto.getId());
        personEntity.setFirstName(personDto.getFirstName());
        personEntity.setLastName(personDto.getLastName());
        personEntity.setAge(personDto.getAge());
        personRepository.save(personEntity);
        return modelMapper.map(personEntity, PersonDto.class);
    }

    @RequestMapping(value = "/findPersonById", method = RequestMethod.POST)
    @Cacheable(value = "persons", key = "#id")
    public PersonDto findPersonById(@RequestParam(value = "id") final Long id) {
        final Optional<PersonEntity> personEntity = personRepository.findById(id);
        return modelMapper.map(personEntity.orElseGet(() -> new PersonEntity()), PersonDto.class);
    }

    @RequestMapping(value = "/getPersons", method = RequestMethod.GET)
    public List<PersonDto> getPersons() {
        final List<PersonEntity> personEntities = personRepository.findAll();
        return personEntities.stream().map(personEntity -> modelMapper.map(personEntity, PersonDto.class)).collect(Collectors.toList());
    }
}
