package telran.java58.person.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import telran.java58.person.dto.CityPopulationDto;
import telran.java58.person.model.Child;
import telran.java58.person.model.Employee;
import telran.java58.person.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Stream<Person> findStreamByNameIgnoreCase(String name);

    Person[] findArrayByNameIgnoreCase(String name);

    Stream<Person> findStreamByAddressCityIgnoreCase(String city);

    @Query("select p from Person p where p.address.city=?1")
    Person[] findArrayByAddressCity(String city);

    Person[] findArrayByBirthDateBetween(LocalDate from, LocalDate to);
    Stream<Person> findStreamByBirthDateBetween(LocalDate from, LocalDate to);

//    @Query("SELECT new telran.java58.person.dto.CityPopulationDto(p.address.city, COUNT(p))" + "FROM Person p GROUP BY p.address.city")

    @Query("select new telran.java58.person.dto.CityPopulationDto(p.address.city, count(p) ) from Person p group by p.address.city order by count(p) ")
    List<CityPopulationDto> getCitiesPopulation();

    @Query("select p from Person p where type(p) = Child")
    Stream<Child> findAllChildren();

    @Query("select e from Employee e where e.salary between ?1 and ?2")
    Stream<Employee> findEmployeesBySalary(Integer minSalary, Integer maxSalary);
}
