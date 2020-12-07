package acme.features.supplier.request;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.requests.RequestEntity;
import acme.entities.roles.Supplier;
import acme.features.administrator.auditor.AdministratorAuditorListService;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/supplier/request-entity/")
public class SupplierRequestController  extends AbstractController<Supplier, RequestEntity>{


	// Internal state ---------------------------------------------------------

	@Autowired
	private SupplierRequestListMineService	listMineService;
	
	@Autowired
	private SupplierRequestListService		listService;

	@Autowired
	private SupplierRequestShowService	showService;

	@Autowired
	private SupplierRequestUpdateService updateService;


	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listMineService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
		super.addBasicCommand(BasicCommand.LIST, this.listService);
	}
}
