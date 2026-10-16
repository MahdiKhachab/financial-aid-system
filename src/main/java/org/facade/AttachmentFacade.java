package org.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.model.Attachment;

@Service
@Transactional
public class AttachmentFacade extends AbstractFacade<Attachment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AttachmentFacade() {
		super(Attachment.class);
	}
}
