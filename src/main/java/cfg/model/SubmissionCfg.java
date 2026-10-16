package cfg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.model.MainEntity;

@NamedQueries({
	@NamedQuery(name = SubmissionCfg.CHECK_IF_SUBMISSION_EXISTS, query = "SELECT sub FROM SubmissionCfg sub WHERE sub.name = :name " )})


@Entity
@Table(name = "cfg_submission")
public class SubmissionCfg extends MainEntity implements Serializable {

	public static final String CHECK_IF_SUBMISSION_EXISTS = "SubmissionCfg_check_if_exists";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String name;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "SUBMISSION_ID")
	private List<SubmissionDetailsCfg> submissionDetailsCfgList = new ArrayList<>();

	public SubmissionCfg() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SubmissionDetailsCfg> getSubmissionDetailsList() {
		return submissionDetailsCfgList;
	}

	public void setSubmissionDetailsList(List<SubmissionDetailsCfg> submissionDetailsList) {
		this.submissionDetailsCfgList = submissionDetailsList;
	}

	public void addToSubmissionDetails(SubmissionDetailsCfg sd) {
		this.submissionDetailsCfgList.add(sd);
	}

	public void removeFromSubmissionDetails(SubmissionDetailsCfg sd) {
		this.submissionDetailsCfgList.remove(sd);
	}
}
