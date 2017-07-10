package com.holy.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("AddressBook")
public class AddressBook {
	
	private List<Person> persons;
	
    public List<Person> getPersons() {
        return persons;
    }
    public void setPersons(List<Person> persons) {
        this.persons = persons;
 
    }

}
