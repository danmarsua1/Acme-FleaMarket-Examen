
package acme.features.supplier.items;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.coupons.Coupon;
import acme.entities.items.Item;
import acme.entities.requests.RequestEntity;
import acme.entities.roles.Supplier;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class SupplierItemShowService implements AbstractShowService<Supplier, Item> {

	@Autowired
	SupplierItemRepository repository;


	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;

		//		boolean result;
		//		int itemId;
		//		Item item;
		//		Supplier supplier;
		//		Principal principal;
		//
		//		itemId = request.getModel().getInteger("id");
		//		item = this.repository.findOneById(itemId);
		//		supplier = item.getSupplier();
		//		principal = request.getPrincipal();
		//		result = supplier.getUserAccount().getId() == principal.getAccountId();

		return true;
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		//Fecha actual
		Calendar today = Calendar.getInstance();
		int todayYear = today.get(Calendar.YEAR);
		int todayMonth = today.get(Calendar.MONTH);
		int todayDay = today.get(Calendar.DAY_OF_MONTH);

		LocalDate now = LocalDate.of(todayYear, todayMonth, todayDay);

		//Fecha creacion
		Calendar creation = new GregorianCalendar();
		creation.setTime(entity.getCreationMoment());
		int creationYear = creation.get(Calendar.YEAR);
		int creationMonth = creation.get(Calendar.MONTH);
		int creationDay = creation.get(Calendar.DAY_OF_MONTH);

		LocalDate creationDate = LocalDate.of(creationYear, creationMonth, creationDay);

		long daysBetween = ChronoUnit.DAYS.between(creationDate, now);
		if (daysBetween <= 7) {
			entity.setNewItem(true);
		}

		request.unbind(entity, model, "ticker", "creationMoment", "title", "itemCategory", "description", "price", "photo", "link", "newItem", "status", "finalMode", "coupon.description", "coupon.minMoney", "coupon.maxMoney", "coupon.code");

		Collection<RequestEntity> requests = this.repository.findRequestByItemId(entity.getId());
		if (requests != null && requests.size() > 0) {
			boolean hasRequests = true;
			model.setAttribute("hasRequests", hasRequests);
		}

		Coupon coupon = this.repository.findCouponByItemId(entity.getId());
		if (coupon != null) {
			model.setAttribute("hasCoupon", true);

		} else {
			model.setAttribute("hasCoupon", false);
		}

	}

	@Override
	public Item findOne(final Request<Item> request) {
		assert request != null;

		Item result;
		int id;

		id = request.getModel().getInteger("id");

		result = this.repository.findOneById(id);
		return result;
	}

}
