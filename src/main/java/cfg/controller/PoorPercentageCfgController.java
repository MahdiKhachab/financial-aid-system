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

import cfg.facade.PoorPercenatageCfgFacade;
import cfg.model.PoorPercentageCfg;
import interfaces.ICRUDOperations;
import utils.Utility;

@Named
@ViewScoped
public class PoorPercentageCfgController extends AbstractController<PoorPercentageCfg> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PoorPercenatageCfgFacade poorPercentageCfgFacade;

	private PoorPercentageCfg poorPercentageCfg;
	private List<PoorPercentageCfg> pageItems = new ArrayList<PoorPercentageCfg>();
	private List<PoorPercentageCfg> filteredList;

	@PostConstruct
	public void init() {
		pageItems = getAbstractFacade().findAll();
	}

	public void add() {
		this.pageItems.add(new PoorPercentageCfg());

	}

	public void delete(PoorPercentageCfg item) {
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
				for (PoorPercentageCfg t : pageItems) {
					if (t.isForDelete()) {
						continue;
					} else {
						if (t.getDescription().isEmpty() || t.getTo() == 0 || t.getFrom() == 0) {
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

			ListIterator<PoorPercentageCfg> rowIterator;

			rowIterator = (ListIterator<PoorPercentageCfg>) pageItems.listIterator();
			PoorPercentageCfg c;
			while (rowIterator.hasNext()) {

				c = rowIterator.next();
				if (c.isForDelete()) {
					try {
						getAbstractFacade().remove(c);
						rowIterator.remove();
					} catch (Exception e) {
						Utility.addMessage(c.getDescription() + " - "
								+ Utility.getMessage("related_with_entity", Utility.BUNDLE_FILE_NAME));
					}

				} else {
					c = getAbstractFacade().save(c);
					rowIterator.set(c);

				}
			}
			Utility.addSuccessMessage("save_success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validationIntersectionValues(PoorPercentageCfg t) {
		try {
			if (t.getTo() == 0 || t.getFrom() == 0) {
				return;
			} else {
				for (PoorPercentageCfg p : pageItems) {
					if (!t.equals(p)) {
						if ((t.getFrom() < p.getFrom() && t.getTo() < p.getFrom())
								|| (t.getFrom() > p.getTo() && t.getTo() > p.getTo())) {
							continue;
						} else {

							t.setFrom(0);
							t.setTo(0);
							Utility.addErrorMessage("pourcentage_intersected");
							return;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			t.setFrom(0);
			t.setTo(0);
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
	// Ajax.oncomplete("PrimeFaces.focus('myForm:poorPercentageCfgTableId:" +
	// rowIndex + ":companyNameId')");
	//
	// return lastPage;
	// }

	public List<PoorPercentageCfg> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<PoorPercentageCfg> filteredList) {
		this.filteredList = filteredList;
	}

	public int getCountItems() {
		return this.pageItems.size();
	}

	public PoorPercenatageCfgFacade getPoorPercentageCfgFacade() {
		return poorPercentageCfgFacade;
	}

	public void setPoorPercentageCfgFacade(PoorPercenatageCfgFacade poorPercentageCfgFacade) {
		this.poorPercentageCfgFacade = poorPercentageCfgFacade;
	}

	public List<PoorPercentageCfg> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<PoorPercentageCfg> pageItems) {
		this.pageItems = pageItems;
	}

	@Override
	protected PoorPercentageCfg getItem() {
		// TODO Auto-generated method stub
		return poorPercentageCfg;
	}

	@Override
	protected void setItem(PoorPercentageCfg item) {
		this.poorPercentageCfg = item;

	}

	@Override
	protected long getId() {
		// TODO Auto-generated method stub
		return poorPercentageCfg.getId();
	}

	@Override
	protected ICRUDOperations<PoorPercentageCfg> getAbstractFacade() {
		// TODO Auto-generated method stub
		return poorPercentageCfgFacade;
	}

}
