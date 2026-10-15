package cfg.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.model.MainEntity;

@Entity
@Table(name = "cfg_submissiondetails")
public class SubmissionDetailsCfg extends MainEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String type;

	private double balance_dd =0;

	private double balance_ll =0;

	@Transient
	private boolean error;
	@Transient
	private boolean forDelete;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isForDelete() {
		return forDelete;
	}

	public void setForDelete(boolean forDelete) {
		this.forDelete = forDelete;
	}

	public SubmissionDetailsCfg() {
		// TODO Auto-generated constructor stub
	}

	public double getBalance_dd() {
		return balance_dd;
	}

	public void setBalance_dd(double balance_dd) {
		this.balance_dd = balance_dd;
	}

	public double getBalance_ll() {
		return balance_ll;
	}

	public void setBalance_ll(double balance_ll) {
		this.balance_ll = balance_ll;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type ;
	}

}
