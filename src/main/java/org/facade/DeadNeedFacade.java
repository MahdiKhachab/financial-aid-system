package org.facade;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.TypedQuery;

import org.model.DeadNeed;

import cfg.model.Service;

@org.springframework.stereotype.Service
@Transactional
public class DeadNeedFacade extends AbstractFacade<DeadNeed> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeadNeedFacade() {
		super(DeadNeed.class);
	}

	public List<DeadNeed> findByService(Service service) {

		TypedQuery<DeadNeed> query = getEntityManager().createNamedQuery(DeadNeed.findByService, DeadNeed.class);

		query.setParameter("service", service);

		return query.getResultList();
	}

}
