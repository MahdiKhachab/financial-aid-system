package org.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.model.Attachment;

@Stateless
@LocalBean
public class AttachmentFacade extends AbstractFacade<Attachment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AttachmentFacade() {
		super(Attachment.class);
	}
}
