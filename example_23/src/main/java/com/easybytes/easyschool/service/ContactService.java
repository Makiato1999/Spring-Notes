package com.easybytes.easyschool.service;


import com.easybytes.easyschool.controller.ContactController;
import com.easybytes.easyschool.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContactService {
    //private static Logger log = LoggerFactory.getLogger(ContactController.class);
    /**
     * Save contact details into DB
     * @param  contact
     * @return boolean
     */
    public boolean  saveMessageDetails(Contact contact) {
        boolean isSaved = true;
        //TODO -  Need to persist the data into the DB table
        log.info(contact.toString());
        return isSaved;
    }
}
