package cfg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.controller.AbstractController;
import org.facade.DeadNeedFacade;
import org.omnifaces.util.Ajax;

import cfg.facade.ServiceFacade;
import cfg.model.Service;
import interfaces.ICRUDOperations;
import utils.Utility;

@Named
@ViewScoped
public class ServiceController extends AbstractController<Service> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceFacade serviceFacade;

	private List<Service> pageItems = new ArrayList<Service>();
	private List<Service> filteredList;

	private Service item;

	@PostConstruct
	public void init() {
		pageItems = getAbstractFacade().findAll();
		pageItems.stream().forEach(s -> s.setSaved(true));
	}

	public List<Service> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<Service> pageItems) {
		this.pageItems = pageItems;
	}

	public List<Service> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Service> filteredList) {
		this.filteredList = filteredList;
	}

	@Inject
	private DeadNeedFacade deadNeedFacade;

	public void delete(ActionEvent actionEvent) {
		try {
			Integer rowId = (Integer) actionEvent.getComponent().getAttributes().get("rowId");
			FacesContext context = FacesContext.getCurrentInstance();
			Service item = context.getApplication().evaluateExpressionGet(context, "#{item}", Service.class);

			if (!item.isForDelete() && deadNeedFacade.findByService(item) != null
					&& deadNeedFacade.findByService(item).size() > 0) {

				Utility.addWarningMessage("service_cant_delete_related_with_dead_needs");
				Ajax.oncomplete("toggleRemove('" + rowId + "')");
				return;
			}

			item.setForDelete(item.isForDelete() ? false : true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add() {
		this.pageItems.add(new Service());
	}

	public void save() {
		try {
			boolean hasEmptyValue = false;

			if (pageItems != null && pageItems.size() > 0) {
				for (Service t : pageItems) {
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

			ListIterator<Service> rowIterator;

			rowIterator = (ListIterator<Service>) pageItems.listIterator();
			Service c;
			while (rowIterator.hasNext()) {

				c = rowIterator.next();
				if (c.isForDelete()) {
					getAbstractFacade().remove(c);
					rowIterator.remove();
				} else {
					c = getAbstractFacade().save(c);
					rowIterator.set(c);

				}
			}
			Utility.addSuccessMessage("save_success");
			pageItems.stream().forEach(s -> s.setSaved(true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Service getItem() {
		// TODO Auto-generated method stub
		return this.item;
	}

	@Override
	protected void setItem(Service item) {
		this.item = item;
	}

	@Override
	protected long getId() {
		// TODO Auto-generated method stub
		return item.getId();
	}

	@Override
	protected ICRUDOperations<Service> getAbstractFacade() {
		// TODO Auto-generated method stub
		return serviceFacade;
	}

	public int getCount() {
		return this.pageItems.size();
	}

}
