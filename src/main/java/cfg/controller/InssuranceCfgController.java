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

import cfg.facade.InssuranceCfgFacade;
import cfg.model.InssuranceCfg;
import interfaces.ICRUDOperations;
import utils.Utility;

@Named
@ViewScoped
public class InssuranceCfgController extends AbstractController<InssuranceCfg> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private InssuranceCfgFacade inssuranceCfgFacade;

	private InssuranceCfg inssuranceCfg;
	private List<InssuranceCfg> pageItems = new ArrayList<InssuranceCfg>();

	@PostConstruct
	public void init() {
		pageItems = getAbstractFacade().findAll();
		pageItems.stream().forEach(c -> c.setSaved(true));
	}

	public void add() {
		this.pageItems.add(new InssuranceCfg());
	}

	public void delete(InssuranceCfg item) {
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
				for (InssuranceCfg t : pageItems) {
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

			ListIterator<InssuranceCfg> rowIterator;

			rowIterator = (ListIterator<InssuranceCfg>) pageItems.listIterator();
			InssuranceCfg c;
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
	// int lastPage;
	//
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
	// Ajax.oncomplete("PF('pageItemsTableWgVar').filter();");
	// return lastPage;
	// }

	public int getCountItems() {
		return this.pageItems.size();
	}

	public InssuranceCfg getInssuranceCfg() {
		return inssuranceCfg;
	}

	public void setInssuranceCfg(InssuranceCfg inssuranceCfg) {
		this.inssuranceCfg = inssuranceCfg;
	}

	public List<InssuranceCfg> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<InssuranceCfg> pageItems) {
		this.pageItems = pageItems;
	}

	@Override
	protected InssuranceCfg getItem() {
		return inssuranceCfg;
	}

	@Override
	protected void setItem(InssuranceCfg item) {
		this.inssuranceCfg = item;
	}

	@Override
	protected long getId() {
		return inssuranceCfg.getId();
	}

	@Override
	protected ICRUDOperations<InssuranceCfg> getAbstractFacade() {
		return inssuranceCfgFacade;
	}

}
