
package acme.features.supplier.coupons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.coupons.Coupon;
import acme.entities.items.Item;
import acme.entities.roles.Supplier;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class SupplierCouponDeleteService implements AbstractDeleteService<Supplier, Coupon> {

	@Autowired
	private SupplierCouponRepository repository;


	@Override
	public boolean authorise(final Request<Coupon> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Coupon> request, final Coupon entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "item");

	}

	@Override
	public void unbind(final Request<Coupon> request, final Coupon entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "description", "minMoney", "maxMoney", "code");

		Integer item = request.getModel().getInteger("item");
		model.setAttribute("item", item);
	}

	@Override
	public Coupon findOne(final Request<Coupon> request) {
		assert request != null;
		Coupon result;

		int id;
		id = request.getModel().getInteger("item");
		result = this.repository.findOneCouponByItemId(id);
		return result;

	}

	@Override
	public void validate(final Request<Coupon> request, final Coupon entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void delete(final Request<Coupon> request, final Coupon entity) {
		assert request != null;
		assert entity != null;

		Integer id = request.getModel().getInteger("item");
		Item item = this.repository.findItemById(id);
		item.setCoupon(null);

		this.repository.delete(entity);

	}

}
