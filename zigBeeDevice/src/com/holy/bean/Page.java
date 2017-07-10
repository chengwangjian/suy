package com.holy.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;



@XStreamAlias("Page")
public class Page {
	
	public  AddressBook addressBook;
	
	public AddressBook getAddressBook() {
        return addressBook;
    }
 
    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

}
