package telran.java58.person.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.java58.person.dao.PersonRepository;
import telran.java58.person.dto.*;
import telran.java58.person.dto.exception.PersonExsistsException;
import telran.java58.person.dto.exception.PersonNotFoundException;
import telran.java58.person.model.Address;
import telran.java58.person.model.Child;
import telran.java58.person.model.Employee;
import telran.java58.person.model.Person;

import java.time.LocalDate;
import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public void addPerson(PersonDto personDto) {
        if(personRepository.existsById(personDto.getId())){
            throw new PersonExsistsException();
        }
        if(personDto instanceof EmployeeDto){
            personRepository.save(modelMapper.map(personDto, Employee.class));
            return;
        }
        if(personDto instanceof ChildDto){
            personRepository.save(modelMapper.map(personDto, Child.class));
            return;
        }
        personRepository.save(modelMapper.map(personDto, Person.class));
    }

    @Override
    public PersonDto getPerson(int id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return mapToDto(person);
        }


    @Override
    @Transactional
    public PersonDto deletePerson(int id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personRepository.delete(person);
        return mapToDto(person);
    }

    @Override
    @Transactional
    public PersonDto updatePersonName(Integer id, String newName) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setName(newName);
        return mapToDto(person);
    }



    @Override
    @Transactional
    public PersonDto updatePersonAddress(Integer id, AddressDto newAddress) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setAddress(modelMapper.map(newAddress, Address.class));
        return mapToDto(person);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public PersonDto[] findPersonsByName(String name) {
//        return personRepository.findStreamByNameIgnoreCase(name)
//                .map(p -> modelMapper.map(p, PersonDto.class))
//                .toArray(PersonDto[]::new);
//
//    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto[] findPersonsByName(String name) {
        return personRepository.findStreamByNameIgnoreCase(name)
                .map(this::mapToDto)
                .toArray(PersonDto[]::new);
//        return modelMapper.map(personRepository.findArrayByNameIgnoreCase(name), PersonDto[].class);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto[] findPersonsByCity(String city) {
        return personRepository.findStreamByAddressCityIgnoreCase(city)
                .map(this::mapToDto)
                .toArray(PersonDto[]::new);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto[] findPersonsBetweenAges(Integer minAge, Integer maxAge) {
        LocalDate from = LocalDate.now().minusYears(maxAge);
        LocalDate to = LocalDate.now().minusYears(minAge);
        return personRepository.findStreamByBirthDateBetween(from, to)
                .map(this::mapToDto)
                .toArray(PersonDto[]::new);

//        return modelMapper.map(personRepository.findArrayByBirthDateBetween(from, to), PersonDto[].class);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<CityPopulationDto> getCityPopulation() {
        return personRepository.getCitiesPopulation();
    }

    @Override
    @Transactional(readOnly = true)
    public ChildDto[] getAllChildren() {
        return personRepository.findAllChildren()
                .map(this::mapToDto)
                .toArray(ChildDto[]::new);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto[] findEmployeesBySalary(Integer minSalary, Integer maxSalary) {
        return personRepository.findEmployeesBySalary(minSalary, maxSalary)
                .map(this::mapToDto)
                .toArray(EmployeeDto[]::new);
    }

    @Override
    public void run(String... args) throws Exception {
        if (personRepository.count() == 0) {
            Person person = new Person(1000, "John", LocalDate.of(1985,3,11),
                    new Address("Tel Aviv", "Ben Gvirol", 81));
            Child child = new Child(2000, "Peter", LocalDate.of(2019, 7, 5),
                    new Address("Ashkelon", "Bar Kohva", 21), "Shalom");
            Employee employee = new Employee(3000, "Mary", LocalDate.of(1995, 11, 23),
                    new Address("Rehovot", "Ben Herzel", 7), "Microsoft", 20000);
            personRepository.saveAll(Arrays.asList(person, child, employee));
        }
    }

    private PersonDto mapToDto(Person person) {
        if(person instanceof Child){
            return modelMapper.map(person, ChildDto.class);
        }
        if(person instanceof Employee){
            return modelMapper.map(person, EmployeeDto.class);
        }
        return modelMapper.map(person, PersonDto.class);
    }


}
