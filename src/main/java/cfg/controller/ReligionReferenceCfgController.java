package cfg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.controller.AbstractController;

import cfg.facade.ReligionReferenceCfgFacade;
import cfg.model.ReligionReferenceCfg;
import interfaces.ICRUDOperations;
import utils.Utility;

@Named
@ViewScoped
public class ReligionReferenceCfgController extends AbstractController<ReligionReferenceCfg> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ReligionReferenceCfgFacade religionReferenceCfgFacade;

	private ReligionReferenceCfg religionReferenceCfg;
	private List<ReligionReferenceCfg> pageItems = new ArrayList<ReligionReferenceCfg>();
	private List<ReligionReferenceCfg> filteredList;

	@PostConstruct
	public void init() {
		pageItems = getAbstractFacade().findAll();
		pageItems.stream().forEach(c -> c.setSaved(true));
	}

	public void add() {
		this.pageItems.add(new ReligionReferenceCfg());

	}

	public void delete(ReligionReferenceCfg item) {
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
				for (ReligionReferenceCfg t : pageItems) {
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

			ListIterator<ReligionReferenceCfg> rowIterator;

			rowIterator = (ListIterator<ReligionReferenceCfg>) pageItems.listIterator();
			ReligionReferenceCfg c;
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
	// Ajax.oncomplete("PrimeFaces.focus('myForm:religionReferenceCfgTableId:" +
	// rowIndex + ":companyNameId')");
	//
	// return lastPage;
	// }

	public List<ReligionReferenceCfg> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<ReligionReferenceCfg> filteredList) {
		this.filteredList = filteredList;
	}

	public int getCountItems() {
		return this.pageItems.size();
	}

	public ReligionReferenceCfgFacade getReligionReferenceCfgFacade() {
		return religionReferenceCfgFacade;
	}

	public void setReligionReferenceCfgFacade(ReligionReferenceCfgFacade religionReferenceCfgFacade) {
		this.religionReferenceCfgFacade = religionReferenceCfgFacade;
	}

	public List<ReligionReferenceCfg> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<ReligionReferenceCfg> pageItems) {
		this.pageItems = pageItems;
	}

	@Override
	protected ReligionReferenceCfg getItem() {
		// TODO Auto-generated method stub
		return religionReferenceCfg;
	}

	@Override
	protected void setItem(ReligionReferenceCfg item) {
		this.religionReferenceCfg = item;

	}

	@Override
	protected long getId() {
		// TODO Auto-generated method stub
		return religionReferenceCfg.getId();
	}

	@Override
	protected ICRUDOperations<ReligionReferenceCfg> getAbstractFacade() {
		// TODO Auto-generated method stub
		return religionReferenceCfgFacade;
	}

}
