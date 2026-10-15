package cfg.facade;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.facade.AbstractFacade;

import cfg.model.SubmissionDetailsCfg;

@Stateless
@LocalBean
public class SubmissionDetailsCfgFacade extends AbstractFacade<SubmissionDetailsCfg> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubmissionDetailsCfgFacade() {
		super(SubmissionDetailsCfg.class);
	}

	public void updateValuesFromSponsor(SubmissionDetailsCfg submissionDetailsCfg, double value, boolean isLira)
			throws Exception {

		if (isLira) {
			submissionDetailsCfg.setBalance_ll(submissionDetailsCfg.getBalance_ll() + value);
			this.save(submissionDetailsCfg);
		} else {
			submissionDetailsCfg.setBalance_dd(submissionDetailsCfg.getBalance_dd() + value);

		}

		this.save(submissionDetailsCfg);

	}

	// hayde yaane jey men T2dim
	public void updateValueFromAid(SubmissionDetailsCfg submissionDetailsCfg, double value, boolean isLira)
			throws Exception {

		if (isLira) {
			submissionDetailsCfg.setBalance_ll(submissionDetailsCfg.getBalance_ll() - value);
			this.save(submissionDetailsCfg);
		} else {
			submissionDetailsCfg.setBalance_dd(submissionDetailsCfg.getBalance_dd() - value);

		}

		this.save(submissionDetailsCfg);

	}

	// Exchange tosrif
	public void exchange(SubmissionDetailsCfg submissionDetailsCfg, double valueLira, double valueDD) throws Exception {
		submissionDetailsCfg.setBalance_ll(submissionDetailsCfg.getBalance_ll() + valueLira);
		submissionDetailsCfg.setBalance_dd(submissionDetailsCfg.getBalance_dd() - valueDD);
		this.save(submissionDetailsCfg);

	}

}
