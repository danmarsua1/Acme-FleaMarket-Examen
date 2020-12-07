
package acme.features.buyer.letter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.letters.Letter;
import acme.entities.roles.Buyer;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/buyer/letter/")
public class BuyerLetterController extends AbstractController<Buyer, Letter> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private BuyerLetterCreateService	createService;
	
	@Autowired
	private BuyerLetterDeleteService	deleteService;

	@Autowired
	private BuyerLetterShowService		showService;


	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
	}
}
