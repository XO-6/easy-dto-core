package org.easydto;

import org.easydto.domain.CoolPerson;
import org.easydto.domain.Person;
import org.easydto.domain.PersonRole;
import org.easydto.proxy.Dto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ConversionTest {

    @Test
    public void testSimpleStringProperty(){
        Person person = new Person();
        person.name("John Doe");

        Dto<Person> personDto = Dto.from(person);
        Assertions.assertEquals("John Doe", personDto.getProperty("name"));
    }

    @Test
    public void testSimpleExtendProperty(){
        CoolPerson person = new CoolPerson();
        person.isCool = true;
        person.name("John Doe");

        Dto<Person> personDto = Dto.from(person);
        Assertions.assertEquals(true, personDto.getProperty("isCool"));
        Assertions.assertEquals("John Doe", personDto.getProperty("name"));
        System.out.println(personDto.getProperty("coolThings"));
        System.out.println(personDto.getProperty("coolPersons"));
    }

    @Test
    public void testProfile(){
        Person person = new Person();
        person.address = "Kolkata";

        Dto<Person> securePersonDto = Dto.from(person, "SECURE");
        Assertions.assertEquals("Kolkata", securePersonDto.getProperty("address"));


        Dto<Person> regularPersonDto = Dto.from(person);
        Assertions.assertEquals("Kolkata", regularPersonDto.getProperty("address"));


        Dto<Person> insecurePersonDto = Dto.from(person, "INSECURE");
        Assertions.assertNull(insecurePersonDto.getProperty("address"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNestedProperty(){
        Person person = new Person();
        person.name("John Doe");

        person.contact = new Person.Contact();
        person.contact.email = "john@example.com";
        person.contact.phone = "1234-55-66";

        Dto<Person> personDto = Dto.from(person);

        Assertions.assertEquals("John Doe", personDto.getProperty("name"));

        Dto<Person.Contact> contactDto = (Dto<Person.Contact>) personDto.getProperty("contact");
        Assertions.assertEquals("john@example.com", contactDto.getProperty("email"));
        Assertions.assertEquals("1234-55-66", contactDto.getProperty("phone"));
    }

    @Test
    public void testCustomPropertyName(){
        Person person = new Person();
        person.sex = "male";

        Dto<Person> personDto = Dto.from(person);

        Assertions.assertEquals("male", personDto.getProperty("gender"));
    }

    @Test
    public void testWrapperProperty(){
        Person person = new Person();
        person.age = 15L;

        Dto<Person> personDto = Dto.from(person);

        Assertions.assertEquals(15L, personDto.getProperty("age"));
    }

    @Test
    public void testUUIDProperty(){
        UUID uuid = UUID.randomUUID();

        Person person = new Person();
        person.id = uuid;

        Dto<Person> personDto = Dto.from(person);

        Assertions.assertEquals(uuid, personDto.getProperty("id"));
    }

    @Test
    public void testEnumProperty(){

        Person person = new Person();
        person.role = PersonRole.STUDENT;

        Dto<Person> personDto = Dto.from(person);

        Assertions.assertEquals(PersonRole.STUDENT, personDto.getProperty("role"));
    }
}
