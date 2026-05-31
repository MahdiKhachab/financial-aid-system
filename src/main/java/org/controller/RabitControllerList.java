package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.dtos.StanderDto;
import org.facade.ProvideAidFacade;
import org.model.Deserved;

@ViewScoped
@Named
public class RabitControllerList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ProvideAidFacade provideAidFacade;

	private List<String> rabitNameList = new ArrayList<>();

	private List<StanderDto> pageItems = new ArrayList<>();
	private List<StanderDto> filteredList;

	@PostConstruct
	public void init() {
		rabitNameList = provideAidFacade.getAllNamesRabit();
		int c = 0;
		for (String var : rabitNameList) {
			pageItems.add(new StanderDto(c, var));
			c++;
		}

	}

	public List<String> getRabitNameList() {
		return rabitNameList;
	}

	public void setRabitNameList(List<String> rabitNameList) {
		this.rabitNameList = rabitNameList;
	}

	public List<StanderDto> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<StanderDto> pageItems) {
		this.pageItems = pageItems;
	}

	public List<StanderDto> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<StanderDto> filteredList) {
		this.filteredList = filteredList;
	}
	
	
	public int getCountItems() {
		return pageItems.size();
	}

}
