package Listeners;

import java.util.Date;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import org.model.MainEntity;

public class MainEntityListener {

	
	@PrePersist
	public void creationDate(MainEntity mainEntity) {
		if(mainEntity.getCreationDate()==null) {
			mainEntity.setCreationDate(new Date());
		}
	}
	
	@PreUpdate
	public void lastUpdate(MainEntity entity) {
		entity.setLastUpdate(new Date());
	}
	
}
