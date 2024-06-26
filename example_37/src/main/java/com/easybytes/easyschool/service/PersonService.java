package com.easybytes.easyschool.service;

import com.easybytes.easyschool.constants.EasySchoolConstants;
import com.easybytes.easyschool.model.Person;
import com.easybytes.easyschool.model.Roles;
import com.easybytes.easyschool.repository.PersonRepository;
import com.easybytes.easyschool.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    RolesRepository rolesRepository;

    public PersonService() {
        System.out.println("Person Service Bean initialized");
    }

    public boolean createNewPerson(Person person) {
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(EasySchoolConstants.STUDENT_ROLE);
        person.setRoles(role);
        person = personRepository.save(person);
        if (null != person && person.getPersonId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }
}
