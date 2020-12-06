
package acme.features.supplier.coupons;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.coupons.Coupon;
import acme.entities.roles.Supplier;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/supplier/coupon/")
public class SupplierCouponController extends AbstractController<Supplier, Coupon> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SupplierCouponCreateService	createService;

	@Autowired
	private SupplierCouponDeleteService	deleteService;


	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
	}
}
