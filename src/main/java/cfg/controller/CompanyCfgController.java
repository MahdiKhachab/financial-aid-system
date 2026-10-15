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

import cfg.facade.CompanyCfgFacade;
import cfg.model.CompanyCfg;
import interfaces.ICRUDOperations;
import utils.Utility;

@Named
@ViewScoped
public class CompanyCfgController extends AbstractController<CompanyCfg> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CompanyCfgFacade companyCfgFacade;

	private CompanyCfg companyCfg;
	private List<CompanyCfg> pageItems = new ArrayList<CompanyCfg>();
	private List<CompanyCfg> filteredList;

	@PostConstruct
	public void init() {
		pageItems = getAbstractFacade().findAll();
		pageItems.stream().forEach(c -> c.setSaved(true));

	}

	public void add() {
		this.pageItems.add(new CompanyCfg());

	}

	public void delete(CompanyCfg item) {
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
				for (CompanyCfg t : pageItems) {
					if (t.isForDelete()) {
						continue;
					} else {
						if (t.getName() == null || t.getName().equals("")) {
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

			ListIterator<CompanyCfg> rowIterator;

			rowIterator = (ListIterator<CompanyCfg>) pageItems.listIterator();
			CompanyCfg c;
			while (rowIterator.hasNext()) {

				c = rowIterator.next();
				if (c.isForDelete()) {

					try {
						getAbstractFacade().remove(c);
						rowIterator.remove();
					} catch (Exception e) {
						Utility.addMessage(c.getName() + " - "
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
	// Ajax.oncomplete("PrimeFaces.focus('myForm:companyCfgTableId:" + rowIndex +
	// ":companyNameId')");
	//
	// return lastPage;
	// }

	public void editRow(CompanyCfg c) {
		c.setSaved(false);
	}

	public List<CompanyCfg> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<CompanyCfg> filteredList) {
		this.filteredList = filteredList;
	}

	public int getCountItems() {
		return this.pageItems.size();
	}

	public CompanyCfgFacade getCompanyCfgFacade() {
		return companyCfgFacade;
	}

	public void setCompanyCfgFacade(CompanyCfgFacade companyCfgFacade) {
		this.companyCfgFacade = companyCfgFacade;
	}

	public List<CompanyCfg> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<CompanyCfg> pageItems) {
		this.pageItems = pageItems;
	}

	@Override
	protected CompanyCfg getItem() {
		// TODO Auto-generated method stub
		return companyCfg;
	}

	@Override
	protected void setItem(CompanyCfg item) {
		this.companyCfg = item;

	}

	@Override
	protected long getId() {
		// TODO Auto-generated method stub
		return companyCfg.getId();
	}

	@Override
	protected ICRUDOperations<CompanyCfg> getAbstractFacade() {
		// TODO Auto-generated method stub
		return companyCfgFacade;
	}

}
