package com.bjsxt.oa.manager.impl;

import com.bjsxt.oa.PagerModel;
import com.bjsxt.oa.manager.PersonManager;
import com.bjsxt.oa.model.Organization;
import com.bjsxt.oa.model.Person;

public class PersonManagerImpl extends AbstractManager implements PersonManager {

	public void addPerson(Person person, int orgId) {
		if(orgId != 0){
			person.setOrg((Organization)this.getHibernateTemplate().load(Organization.class, orgId));
		}
		getHibernateTemplate().save(person);
	}

	public void delPerson(int personId) {
		getHibernateTemplate().delete(findPerson(personId));
	}

	public Person findPerson(int personId) {
		
		return (Person)getHibernateTemplate().load(Person.class, personId);
	}

	public PagerModel searchPersons() {
		
		return searchPaginated("from Person");
	}

	public void updatePerson(Person person, int orgId) {
		if(orgId != 0){
			person.setOrg((Organization)this.getHibernateTemplate().load(Organization.class, orgId));
		}
		getHibernateTemplate().update(person);
	}
	
	public PagerModel searchPersons(int orgId) {
		return searchPaginated("select p from Person p where p.org.id = "+orgId);
	}
}
