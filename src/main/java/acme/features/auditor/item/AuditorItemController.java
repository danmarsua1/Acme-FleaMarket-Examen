
package acme.features.auditor.item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.items.Item;
import acme.entities.roles.Auditor;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/auditor/item/")
public class AuditorItemController extends AbstractController<Auditor, Item> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorItemListMineService		listMineService;

	@Autowired
	private AuditorItemListNotMineService	listNotMineService;

	@Autowired
	private AuditorItemShowService			showService;

	@Autowired
	private AuditorItemListService			listService;


	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listMineService);
		super.addCustomCommand(CustomCommand.LIST_NOT_MINE, BasicCommand.LIST, this.listNotMineService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.LIST, this.listService);
	}
}
