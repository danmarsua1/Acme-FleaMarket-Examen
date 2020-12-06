
package acme.features.supplier.coupons;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.coupons.Coupon;
import acme.entities.items.Item;
import acme.entities.roles.Supplier;
import acme.features.supplier.items.SupplierItemRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class SupplierCouponCreateService implements AbstractCreateService<Supplier, Coupon> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SupplierCouponRepository	repository;

	@Autowired
	private SupplierItemRepository		itemRepository;


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

		System.out.println("BIND");

		request.bind(entity, errors, "item");

	}

	@Override
	public void unbind(final Request<Coupon> request, final Coupon entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		System.out.println("UNBIND");

		request.unbind(entity, model, "description", "minMoney", "maxMoney", "code");

		Integer item = request.getModel().getInteger("item");
		model.setAttribute("item", item);

	}

	@Override
	public Coupon instantiate(final Request<Coupon> request) {

		Coupon result;
		result = new Coupon();

		System.out.println("INSTANTIATE");

		//GENERAR TICKER
		String ticker = "";

		ticker = this.createTicker(result);

		result.setCode(ticker);

		return result;
	}

	@Override
	public void validate(final Request<Coupon> request, final Coupon entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		System.out.println("VALIDATE");

		boolean isMinMoneyEuro, isMaxMoneyEuro, isMinMoneyLowerThanMaxMoney;

		if (!errors.hasErrors("minMoney")) {
			String minMoney = entity.getMinMoney().getCurrency();
			isMinMoneyEuro = minMoney.equals("€") || minMoney.equals("EUR");
			errors.state(request, isMinMoneyEuro, "minMoney", "supplier.coupon.form.error.minMoney");
		}

		if (!errors.hasErrors("maxMoney")) {
			String maxMoney = entity.getMaxMoney().getCurrency();
			isMaxMoneyEuro = maxMoney.equals("€") || maxMoney.equals("EUR");
			errors.state(request, isMaxMoneyEuro, "maxMoney", "supplier.coupon.form.error.maxMoney");
		}

		if (!errors.hasErrors("minMoney") && !errors.hasErrors("maxMoney")) {
			Double minMoney = entity.getMinMoney().getAmount();
			Double maxMoney = entity.getMaxMoney().getAmount();
			isMinMoneyLowerThanMaxMoney = minMoney < maxMoney;
			errors.state(request, isMinMoneyLowerThanMaxMoney, "minMoney", "supplier.coupon.form.error.minMoneyLower");
		}

		Integer id = request.getModel().getInteger("item");
		Item item = this.repository.findItemById(id);

		if (!errors.hasErrors("minMoney") && !errors.hasErrors("maxMoney")) {

			Double price = item.getPrice().getAmount();
			Double maxMoney = entity.getMaxMoney().getAmount();

			if (maxMoney >= price) {
				errors.state(request, false, "maxMoney", "supplier.coupon.form.error.maxGreaterThanPrice");
			}

		}

	}

	@Override
	public void create(final Request<Coupon> request, final Coupon entity) {
		assert request != null;
		assert entity != null;

		System.out.println("CREATE");

		//GENERAR TICKER
		String ticker = "";

		ticker = this.createTicker(entity);

		entity.setCode(ticker);

		this.repository.save(entity);

		Integer id = request.getModel().getInteger("item");
		Item item = this.repository.findItemById(id);

		item.setCoupon(entity);

		this.itemRepository.save(item);

	}

	//Other business methods

	public String createTicker(final Coupon entity) {

		//The ticker must be as follow: yyddmm
		String ticker = new String();

		//Get creation year
		Date creationDate = new Date(System.currentTimeMillis() - 1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String current = sdf.format(creationDate);

		System.out.println(current);

		ticker = current;

		return ticker;

	}

}
