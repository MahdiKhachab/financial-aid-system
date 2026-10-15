package org.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({

		@NamedQuery(name = ProvideAidRabet.GET_RABIT_NAME, query = "select DISTINCT (r.rabetName) from ProvideAidRabet r"),
		@NamedQuery(name = ProvideAidRabet.GET_All_RABIT_GIVES, query = "select r from ProvideAidRabet r where r.rabetName = :name "),
		@NamedQuery(name = ProvideAidRabet.GET_All_RABIT_WITHDATE, query = "select p from   ProvideAidRabet p where   p.deliveryDate between :dateFrom and :dateTo and p.submissionDetailsCfg = :submissionDetailsCfg")

})

@Entity
public class ProvideAidRabet extends ProvideAid implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String GET_RABIT_NAME = "Get_Rabit_Name";
	public static final String GET_All_RABIT_GIVES = "Get_Rabit_Gives";

	public static final String GET_All_RABIT_WITHDATE = "Get_All_RABIT_WITH_DATE";

	private String rabetName;

	private String delagate;

	public String getRabetName() {
		return rabetName;
	}

	public void setRabetName(String rabetName) {
		this.rabetName = rabetName;
	}

	public String getDelagate() {
		return delagate;
	}

	public void setDelagate(String delagate) {
		this.delagate = delagate;
	}

}
