package cfg.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.model.MainEntity;

@Entity
@Table(name = "cfg_healthystatus")
public class HealthyStatusCfg extends MainEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String status;

	@Transient
	private boolean error;
	@Transient
	private boolean forDelete;
	@Transient
	private boolean saved = false;

	public HealthyStatusCfg() {
		// TODO Auto-generated constructor stub
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

}
