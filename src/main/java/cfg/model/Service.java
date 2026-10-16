package cfg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.model.MainEntity;
import org.model.Worker;

@Entity
@Table(name = "Service")
public class Service extends MainEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String name;

	private boolean counted;

	@Transient
	private boolean error;
	@Transient
	private boolean forDelete;
	@Transient
	private boolean saved=false;
	
	@ManyToMany(mappedBy = "services")
	private List<Worker> workers = new ArrayList<Worker>();

	public Service() {
		// TODO Auto-generated constructor stub
	}

	public boolean isCounted() {
		return counted;
	}

	public void setCounted(boolean counted) {
		this.counted = counted;
	}

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	
	

}
