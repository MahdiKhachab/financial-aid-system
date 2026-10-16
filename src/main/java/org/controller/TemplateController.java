package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import utils.MenuItem;

@Named
@SessionScoped
public class TemplateController implements Serializable {
	private static final long serialVersionUID = -1L;
	private List<MenuItem> menu = new ArrayList<MenuItem>();

	String x = "1";

	@PostConstruct
	public void init() {

		menu = fillMenuItems();
	}

	public List<MenuItem> fillMenuItems() {
		List<MenuItem> menu = new ArrayList<>();
		MenuItem m1 = null;
		MenuItem m2 = null;
		

		// --- index ---//

		m1 = new MenuItem("1", getValue("home"), "mdi mdi-home-outline", "", getValue("home"), true);

		m2 = new MenuItem(getValue("home"), "mdi mdi-home-outline", "home", true, "addButton");
		m1.addChild(m2);

		menu.add(m1);

		// --- Configuration ---//

		m1 = new MenuItem("2", getValue("configurations"), "mdi mdi-settings-outline", "configuration", getValue("configurations"),
				true);

		m2 = new MenuItem(getValue("company_cfg_list"), "mdi mdi-domain", "company-cfg-list", true,
				"addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("healthy_status_cfg"), "mdi mdi-heart-pulse", "healthy-status-cfg", true,
				"addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("insurance_party_list"), "mdi mdi-shield-account", "inssurance-cfg-list",
				true, "addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("religion_reference_cfg_list"), "mdi mdi-book-open-page-variant",
				"religion-reference-cfg-list", true, "addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("poor_percentage"), "mdi mdi-percent", "poorPercentageCfg-list", true, "addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("submissionCfg-list"), "mdi mdi-cash-multiple", "submissionCfg-list", true,
				"addButton");
		m1.addChild(m2);
		m2 = new MenuItem(getValue("service_list"), "mdi mdi-calendar-heart", "service-list", true, "addButton");
		m1.addChild(m2);

		// when click tokdimet details yeftah el navbar
		m2 = new MenuItem(getValue("submissionCfg-list"), "  mdi mdi-currency-usd  ", "submission-cfg-card", true,
				"visibility: hidden;");
		m1.addChild(m2);

		menu.add(m1);

		// --- Card ---//

		m1 = new MenuItem("3", getValue("cards"), "mdi mdi-folder-account", "", getValue("cards"), true);

		m2 = new MenuItem(getValue("deserved-list"), "mdi mdi-account-heart", "deserved-list", true, "addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("sponsor"), "mdi mdi-account-card-details", "sponsor-list", true, "addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("worker_list"), "mdi mdi-worker", "worker-list", true, "addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("dead_person"), "mdi mdi-account-box-outline", "dead-list", true, "addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("stores"), "mdi mdi-store", "stores-list", true, "addButton");
		m1.addChild(m2);

		// when click tokdimet details yeftah el navbar
		m2 = new MenuItem(getValue("submissionCfg-list"), "  mdi mdi-currency-usd  ", "sponsor-card", true,
				"visibility: hidden;");
		m1.addChild(m2);
		m2 = new MenuItem(getValue("submissionCfg-list"), "  mdi mdi-currency-usd  ", "stores-card", true,
				"visibility: hidden;");
		m1.addChild(m2);

		
		
		m2 = new MenuItem(getValue("submissionCfg-list"), "  mdi mdi-currency-usd  ", "deserved-card", true,
				"visibility: hidden;");
		m1.addChild(m2);
		
		
		
		menu.add(m1);

		// --- Services ---//

		m1 = new MenuItem("4", getValue("services"), "mdi mdi-charity", "", getValue("cards"), true);

		m2 = new MenuItem(getValue("recieve_money"), "mdi mdi-inbox-arrow-down", "receive_money", true,
				"addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("mediator_list"), "mdi mdi-swap-horizontal", "mediator-list", true,
				"addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("provide_aid"), "mdi mdi-send", "provide-aid-basic", true, "addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("currency_exchange"), "mdi mdi-bank-transfer",
				"currency-exchange-select-submission", true, "addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("add_purchase_voucher"), "mdi mdi-ticket-confirmation", "purchase-voucher", true,
				"addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("distribution_dead_need"), "mdi mdi-package-variant-closed", "needDeadDetails-list", true,
				"addButton");
		m1.addChild(m2);

		// show navbar when open this page
		// Hide Style
		m2 = new MenuItem(getValue("recieve_money"), " mdi mdi-account-supervisor  ", "receive_money_take", true,
				"visibility: hidden;");

		m1.addChild(m2);

		m2 = new MenuItem(getValue("recieve_money"), " mdi mdi-account-supervisor  ", "direct-submission-aid", true,
				"visibility: hidden;");

		m1.addChild(m2);

		m2 = new MenuItem(getValue("recieve_money"), " mdi mdi-account-supervisor  ", "direct-submission-aid-give",
				true, "visibility: hidden;");

		m1.addChild(m2);

		m2 = new MenuItem(getValue("recieve_money"), " mdi mdi-account-supervisor  ", "submit-company", true,
				"visibility: hidden;");

		m1.addChild(m2);

		m2 = new MenuItem(getValue("recieve_money"), " mdi mdi-account-supervisor  ", "submit-with-conditions-aid",
				true, "visibility: hidden;");

		m1.addChild(m2);

		m2 = new MenuItem(getValue("recieve_money"), " mdi mdi-account-supervisor  ", "submit-a-voucher-aid", true,
				"visibility: hidden;");

		m1.addChild(m2);

		m2 = new MenuItem(getValue("recieve_money"), " mdi mdi-account-supervisor  ", "submit-with-repport-aid", true,
				"visibility: hidden;");

		m1.addChild(m2);

		m1.addChild(m2);

		m2 = new MenuItem(getValue("recieve_money"), " mdi mdi-account-supervisor  ", "submit-rabit", true,
				"visibility: hidden;");

		m1.addChild(m2);

		m2 = new MenuItem(getValue("recieve_money"), " mdi mdi-account-supervisor  ", "person_desrved_repport", true,
				"visibility: hidden;");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("recieve_money"), " mdi mdi-account-supervisor  ", "currency-exchange-action", true,
				"visibility: hidden;");

		m1.addChild(m2);

		menu.add(m1);
		// --- reports ---//

		m1 = new MenuItem("5", getValue("reports"), "mdi mdi-chart-bar", "", getValue("reports"), true);

		m2 = new MenuItem(getValue("sponsor_repport"), "mdi mdi-file-account", "sponsor_repport", true,
				"addButton");
		m1.addChild(m2);

		
		m2 = new MenuItem(getValue("dead_need_report"), "mdi mdi-file-document-box-outline", "deadNeed-report", true,
				"addButton");
		m1.addChild(m2);
		
		m2 = new MenuItem(getValue("remaining_needs_report"), "mdi mdi-clipboard-text-outline", "remainingNeeds-report", true,
				"addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("repport_rabit"), "mdi mdi-account-group", "rabit_list", true, "addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("repport_company"), "mdi mdi-briefcase-outline", "company_list", true, "addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("purchase_voucher_repport"), "mdi mdi-ticket-outline", "purchase-voucher-repport",
				true, "addButton");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("stores_repport"), "mdi mdi-store", "stores_repport", true, "addButton");
		m1.addChild(m2);
		
		m2 = new MenuItem(getValue("mediator_report"), "mdi mdi-swap-horizontal", "mediator-report", true,
				"addButton");
		m1.addChild(m2);
		

		m2 = new MenuItem(getValue("general_repport"), "mdi mdi-view-dashboard-outline", "external-repport", true,
					"addButton");
			m1.addChild(m2);
		

		// show navbar when open this page

		m2 = new MenuItem(getValue("sponsor_repport"), " mdi mdi-account-supervisor  ", "sponsor_repport_take", true,
				"visibility: hidden;");
		m1.addChild(m2);
		
		m2 = new MenuItem(getValue("sponsor_repport"), " mdi mdi-account-supervisor  ", "company_list_repport", true,
				"visibility: hidden;");
		m1.addChild(m2);

		m2 = new MenuItem(getValue("sponsor_repport"), " mdi mdi-account-supervisor  ", "rabit_list_repport", true,
				"visibility: hidden;");
		m1.addChild(m2);
		
		m2 = new MenuItem(getValue("dead_need_report"), " mdi mdi-account-supervisor   ", "deadNeed-report", true,
				"visibility: hidden;");
		m1.addChild(m2);
		
		m2 = new MenuItem(getValue("remaining_needs_report"), "  dripicons-graph-line   ", "remainingNeeds-report", true,
				"visibility: hidden;");
		m1.addChild(m2);
		
		m2 = new MenuItem(getValue("mediator_report"), "  mdi mdi-account-supervisor    ", "mediator-report", true,
				"visibility: hidden;");
		m1.addChild(m2);

		

		m2 = new MenuItem(getValue("general_repport"), "  mdi mdi-account-supervisor    ", "repport-deatils", true,
				"visibility: hidden;");
		m1.addChild(m2);
		
		/**/
		menu.add(m1);
		return menu;
	}

	private String getValue(java.lang.String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("resources.Bundle");
		return bundle.getString(key);
	}

	public List<MenuItem> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuItem> menu) {
		this.menu = menu;
	}

	public List<FacesMessage> getMessages() {

		List<FacesMessage> uniqueMessages = new ArrayList<>();

		for (FacesMessage message : FacesContext.getCurrentInstance().getMessageList()) {

			if (uniqueMessages.isEmpty()) {

				uniqueMessages.add(message);

			} else {

				boolean addMessage = true;

				for (FacesMessage currentMessage : uniqueMessages) {

					if (currentMessage.getDetail().equals(message.getDetail())) {
						addMessage = false;
						break;
					}
				}

				if (addMessage) {
					uniqueMessages.add(message);
				}

			}

		}

		return uniqueMessages;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

}
