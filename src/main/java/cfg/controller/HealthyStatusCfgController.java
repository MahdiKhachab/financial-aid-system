package cfg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.controller.AbstractController;

import cfg.facade.HealthyStatusCfgFacade;
import cfg.model.HealthyStatusCfg;
import interfaces.ICRUDOperations;
import utils.Utility;

@Named
@ViewScoped
public class HealthyStatusCfgController extends AbstractController<HealthyStatusCfg> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Inject
	private HealthyStatusCfgFacade healthyStatusCfgFacade;

	private HealthyStatusCfg healthyStatusCfg;
	private List<HealthyStatusCfg> pageItems = new ArrayList<HealthyStatusCfg>();
	private List<HealthyStatusCfg> filteredList;

	@PostConstruct
	public void init() {
		pageItems = getAbstractFacade().findAll();
		pageItems.stream().forEach(c -> c.setSaved(true));
	}

	public void add() {
		this.pageItems.add(new HealthyStatusCfg());

	}

	public void delete(HealthyStatusCfg item) {
		if (item.isForDelete())
			item.setForDelete(false);
		else
			item.setForDelete(true);
	}

	@Override
	public void save() throws Exception {
		try {
			boolean hasEmptyValue = false;

			if (pageItems != null && pageItems.size() > 0) {
				for (HealthyStatusCfg t : pageItems) {
					if (t.isForDelete()) {
						continue;
					} else {
						if (t.getStatus() == null || t.getStatus().equals("")) {
							t.setError(true);
							hasEmptyValue = true;

						} else
							t.setError(false);
					}
				}
			}
			if (hasEmptyValue) {

				Utility.addErrorMessage("table_has_values_empty_msg");
				return;
			}

			ListIterator<HealthyStatusCfg> rowIterator;

			rowIterator = (ListIterator<HealthyStatusCfg>) pageItems.listIterator();
			HealthyStatusCfg c;
			while (rowIterator.hasNext()) {

				c = rowIterator.next();
				if (c.isForDelete()) {
					try {
						getAbstractFacade().remove(c);
						rowIterator.remove();
					} catch (Exception e) {
						Utility.addMessage(c.getStatus() + " - "
								+ Utility.getMessage("related_with_entity", Utility.BUNDLE_FILE_NAME));
					}

				} else {
					c = getAbstractFacade().save(c);
					c.setSaved(true);
					rowIterator.set(c);

				}
			}
			Utility.addSuccessMessage("save_success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public int getFirstRowInLastPage(String dataTableId) {
	//
	// int lastPage;
	// final DataTable d = (DataTable)
	// FacesContext.getCurrentInstance().getViewRoot()
	// .findComponent(ComponentUtils.findComponentClientId(dataTableId));
	// if (d.getRowCount() % d.getRows() == 0) {
	// lastPage = (d.getRowCount() - d.getRows());
	// } else {
	// lastPage = (d.getRowCount() / d.getRows()) * d.getRows();
	// }
	// if (lastPage < 0)
	// lastPage = 0;
	// d.setFirst(lastPage);
	//
	// int rowIndex = 0;
	// if (pageItems.size() > 1)
	// rowIndex = pageItems.size() - 1;
	// Ajax.oncomplete("PrimeFaces.focus('myForm:healthyStatusCfgTableId:" +
	// rowIndex + ":companyNameId')");
	//
	// return lastPage;
	// }

	public List<HealthyStatusCfg> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<HealthyStatusCfg> filteredList) {
		this.filteredList = filteredList;
	}

	public int getCountItems() {
		return this.pageItems.size();
	}

	public HealthyStatusCfgFacade getHealthyStatusCfgFacade() {
		return healthyStatusCfgFacade;
	}

	public void setHealthyStatusCfgFacade(HealthyStatusCfgFacade healthyStatusCfgFacade) {
		this.healthyStatusCfgFacade = healthyStatusCfgFacade;
	}

	public List<HealthyStatusCfg> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<HealthyStatusCfg> pageItems) {
		this.pageItems = pageItems;
	}

	@Override
	protected HealthyStatusCfg getItem() {
		// TODO Auto-generated method stub
		return healthyStatusCfg;
	}

	@Override
	protected void setItem(HealthyStatusCfg item) {
		this.healthyStatusCfg = item;

	}

	@Override
	protected long getId() {
		// TODO Auto-generated method stub
		return healthyStatusCfg.getId();
	}

	@Override
	protected ICRUDOperations<HealthyStatusCfg> getAbstractFacade() {
		// TODO Auto-generated method stub
		return healthyStatusCfgFacade;
	}

}
