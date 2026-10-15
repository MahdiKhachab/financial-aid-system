package org.facade;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.model.DeadNeed;
import org.model.DeadPerson;
import org.model.NeedDetails;
import org.model.Worker;

@Stateless
@LocalBean
public class NeedDetailsFacade extends AbstractFacade<NeedDetails> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NeedDetailsFacade() {
		super(NeedDetails.class);
	}

	public List<NeedDetails> findByDeadNeed(DeadNeed deadNeed) {
		TypedQuery<NeedDetails> query = getEntityManager().createNamedQuery(NeedDetails.findByDeadNeed,
				NeedDetails.class);
		query.setParameter("need", deadNeed);
		return query.getResultList();
	}

	public List<NeedDetails> findByDeadNeedWithConditions(DeadNeed deadNeed, boolean finished) {
		try {
			TypedQuery<NeedDetails> query = getEntityManager()
					.createNamedQuery(NeedDetails.findByDeadNeedWithConditions, NeedDetails.class);
			query.setParameter("need", deadNeed);
			query.setParameter("finished", finished);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<NeedDetails> findByDeadPerson(DeadPerson deadPerson) {
		TypedQuery<NeedDetails> query = getEntityManager().createNamedQuery(NeedDetails.findByDeadPerson,
				NeedDetails.class);
		query.setParameter("deadPerson", deadPerson);
		return query.getResultList();
	}

	public boolean needInServiceIfExiste(DeadNeed deadNeed) {
		TypedQuery<DeadNeed> query = getEntityManager().createNamedQuery(NeedDetails.findByDeadNeed, DeadNeed.class);
		query.setParameter("need", deadNeed);
		if (query.getResultList().size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean findByWorker(Worker worker) {
		TypedQuery<Worker> query = getEntityManager().createNamedQuery(NeedDetails.findByWorker, Worker.class);
		query.setParameter("worker", worker);

		if (query.getResultList() != null && query.getResultList().size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
