package org.facade;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.model.Deserved;
import org.model.ReceiveMoney;

import cfg.model.HealthyStatusCfg;
import cfg.model.InssuranceCfg;
import cfg.model.PoorPercentageCfg;
import cfg.model.ReligionReferenceCfg;

@Stateless
@LocalBean
public class DeservedFacade extends AbstractFacade<Deserved> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeservedFacade() {
		super(Deserved.class);
	}

	public List<Deserved> findDesrvedsByList(boolean hasCar, int nbChildFrom, int nbChildTo,
			HealthyStatusCfg healthyStatusCfg, PoorPercentageCfg poorPercenatageCfg,
			ReligionReferenceCfg religionReferenceCfg, InssuranceCfg inssuranceCfg , boolean richLogical , String livingArea) {
		StringBuilder query = new StringBuilder();

		StringBuilder where = new StringBuilder();

		query.append(" SELECT d FROM Deserved d");
		where.append("  where d.hasCar = :hasCar and  d.richLogical   = :richLogical  ");

		if (healthyStatusCfg != null) {
			where.append(" and d.healthyStatusCfg = :healthyStatusCfg");
		}
		if (poorPercenatageCfg != null) {
			where.append(" and d.poorPercentageCfg =  :poorPercenatageCfg");
		}
		if (religionReferenceCfg != null) {
			where.append(" and d.religionReferenceCfg =  :religionReferenceCfg");
		}
		if (inssuranceCfg != null) {
			where.append(" and d.inssuranceCfg = :inssuranceCfg");
		}
		if (nbChildTo > 0) {
			where.append(" and d.nbOfIndividuals BETWEEN  :nbChildFrom and :nbChildTo ");

		}
		if(livingArea !=null && livingArea.trim().length() >0 ) {
			where.append(" and d.livingArea =  :livingArea");

		}

		query.append(where);
		TypedQuery<Deserved> typedQuery = getEntityManager().createQuery(query.toString(), Deserved.class);

		typedQuery.setParameter("hasCar", hasCar);
		typedQuery.setParameter("richLogical", richLogical);
		if (healthyStatusCfg != null) {
			typedQuery.setParameter("healthyStatusCfg", healthyStatusCfg);
		}

		if (poorPercenatageCfg != null) {
			typedQuery.setParameter("poorPercenatageCfg", poorPercenatageCfg);
		}
		if (religionReferenceCfg != null) {
			typedQuery.setParameter("religionReferenceCfg", religionReferenceCfg);
		}
		if (inssuranceCfg != null) {
			typedQuery.setParameter("inssuranceCfg", inssuranceCfg);
		}
		if (nbChildTo > 0) {
			typedQuery.setParameter("nbChildFrom", nbChildFrom);
			typedQuery.setParameter("nbChildTo", nbChildTo);
		}
		
		
		if(livingArea !=null && livingArea.trim().length() >0 ) {
			typedQuery.setParameter("livingArea", livingArea);

		}
		return typedQuery.getResultList();

	}

}
