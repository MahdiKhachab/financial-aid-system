package org.facade;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.model.Worker;
import cfg.model.Service;

@Stateless
@LocalBean
public class WorkerFacade extends AbstractFacade<Worker> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorkerFacade() {
		super(Worker.class);
	}

	public List<Worker> findByService(Service service) {
		try {
			TypedQuery<Worker> query = getEntityManager().createNamedQuery(Worker.findByService, Worker.class);

			query.setParameter("service", service);

			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
