package org.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name = ProvideAidDesrvedPerson.PERSON_DESRVED_WITHOUT_DATE, query = "select p from   ProvideAidDesrvedPerson p where p.desrvedPerson = :desrvedPerson") , 

	@NamedQuery(name = ProvideAidDesrvedPerson.PERSON_DESRVED_WITH_DATE, query = "select p from   ProvideAidDesrvedPerson p where   p.deliveryDate between :dateFrom and :dateTo and p.submissionDetailsCfg = :submissionDetailsCfg")

})

@Entity
public class ProvideAidDesrvedPerson extends ProvideAid implements Serializable { 
	private static final long serialVersionUID = 1L;

	public static  final String  PERSON_DESRVED_WITHOUT_DATE = "Person_Desrved_Without_date" ;
	public static  final String  PERSON_DESRVED_WITH_DATE = "Person_Desrved_With_date" ;

	private Deserved desrvedPerson;

	public Deserved getDesrvedPerson() {
		return desrvedPerson;
	}

	public void setDesrvedPerson(Deserved desrvedPerson) {
		this.desrvedPerson = desrvedPerson;
	}

}
