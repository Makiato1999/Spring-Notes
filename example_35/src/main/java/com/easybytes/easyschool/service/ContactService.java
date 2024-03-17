package com.easybytes.easyschool.service;


import com.easybytes.easyschool.EasyschoolApplication;
import com.easybytes.easyschool.constants.EasySchoolConstants;
import com.easybytes.easyschool.controller.ContactController;
import com.easybytes.easyschool.model.Contact;
import com.easybytes.easyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
//@RequestScope
//@SessionScope
//@ApplicationScope
public class ContactService {
    // private int counter = 0;
    @Autowired
    private ContactRepository contactRepository;

    public ContactService() {
        System.out.println("Contact Service Bean initialized");
    }
    //private static Logger log = LoggerFactory.getLogger(ContactController.class);
    /**
     * Save contact details into DB
     * @param  contact
     * @return boolean
     */
    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = false;
        //TODO -  Need to persist the data into the DB table
        contact.setStatus(EasySchoolConstants.OPEN);
        contact.setCreatedBy(EasySchoolConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());
        Contact savedContact = contactRepository.save(contact);
        if (savedContact != null && savedContact.getContactID() > 0) {
            isSaved = true;
        }
        return isSaved;
    }

    /*
    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

     */

    public List<Contact> findMsgWithOpenStatus() {
        List<Contact> contactMsgs = contactRepository.findByStatus(EasySchoolConstants.OPEN);
        return contactMsgs;
    }

    public boolean updateMsgStatus(int contactID, String updateBy) {
        boolean isUpdated = false;
        Optional<Contact> contact = contactRepository.findById(contactID);
        contact.ifPresent(contact1 -> {
            contact1.setStatus(EasySchoolConstants.CLOSE);
            contact1.setUpdatedBy(updateBy);
            contact1.setUpdatedAt(LocalDateTime.now());
        });
        Contact updatedContact = contactRepository.save(contact.get());
        if (null != updatedContact && updatedContact.getUpdatedBy() != null) {
            isUpdated = true;
        }

        /* before example 35
        int result = contactRepository.updateMsgStatus(contactID, EasySchoolConstants.CLOSE, updateBy);
        // 如果返回值是0，则没有记录被更新。这可能是因为没有找到匹配CONTACT_ID的记录。
        // 如果返回值是1或更大的数字，这意味着相应数量的记录已经被成功更新。通常情况下，基于CONTACT_ID更新应该只影响一条记录，因为ID通常是唯一的。
        if (result > 0) {
            isUpdated = true;
        }

         */
        return isUpdated;
    }
}
