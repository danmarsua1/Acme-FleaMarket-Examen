
package acme.features.buyer.request;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.coupons.Coupon;
import acme.entities.items.Item;
import acme.entities.requests.RequestEntity;
import acme.entities.requests.RequestEntityStatus;
import acme.entities.roles.Buyer;
import acme.features.authenticated.item.AuthenticatedItemRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class BuyerRequestCreateService implements AbstractCreateService<Buyer, RequestEntity> {

	// Internal state ------------------------------------------------------------------
	@Autowired
	BuyerRequestRepository		repository;

	@Autowired
	AuthenticatedItemRepository	itemRepository;


	@Override
	public boolean authorise(final Request<RequestEntity> request) {
		assert request != null;
		System.out.println("AUTHORISE");

		return true;
	}

	@Override
	public void bind(final Request<RequestEntity> request, final RequestEntity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		System.out.println("BIND");

		request.bind(entity, errors, "item");

	}

	@Override
	public void unbind(final Request<RequestEntity> request, final RequestEntity entity, final Model model) {

		assert request != null;
		assert entity != null;
		assert model != null;
		System.out.println("UNBIND");

		request.unbind(entity, model, "ticker", "quantity", "notes");

		Integer idItem = request.getModel().getInteger("item");
		model.setAttribute("item", idItem);

	}

	@Override
	public RequestEntity instantiate(final Request<RequestEntity> request) {
		RequestEntity result = new RequestEntity();

		Date moment = new Date(System.currentTimeMillis() - 1);
		result.setCreation(moment);

		result.setStatus(RequestEntityStatus.PENDING);

		Integer itemId = request.getModel().getInteger("item");
		Item item = this.itemRepository.findOneById(itemId);
		item.getRequests().add(result);
		result.setItem(item);

		Principal principal = request.getPrincipal();
		Buyer buyer = this.repository.findBuyerById(principal.getActiveRoleId());
		buyer.getRequests().add(result);
		result.setBuyer(buyer);

		//GENERAR TICKER
		String ticker = "";

		do {
			ticker = this.createTicker(result);
		} while (!this.isTickerUnique(ticker, result));

		result.setTicker(ticker);

		return result;
	}

	@Override
	public void validate(final Request<RequestEntity> request, final RequestEntity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		System.out.println("VALIDATE");
	}

	@Override
	public void create(final Request<RequestEntity> request, final RequestEntity entity) {
		assert request != null;
		assert entity != null;
		System.out.println("CREATE");

		Date moment = new Date(System.currentTimeMillis() - 1);
		entity.setCreation(moment);

		System.out.println(entity.getItem().getTitle());
		System.out.println(entity.getBuyer().getEmail());

		Double quantity = entity.getQuantity();
		Double price = entity.getItem().getPrice().getAmount();

		Double total = quantity * price;

		Coupon coupon = entity.getItem().getCoupon();

		Money money = new Money();

		if (coupon != null) {

			Money minMoney = coupon.getMinMoney();
			Money maxMoney = coupon.getMaxMoney();

			//Condición para aplicar el descuento
			if (quantity == 1) {
				money.setCurrency("€");
				money.setAmount(total);
			} else if (quantity == 2 || quantity == 3) {
				Double discount = minMoney.getAmount();
				total = total - discount;
				money.setCurrency("€");
				money.setAmount(total);
			} else if (quantity > 3) {
				Double discount = maxMoney.getAmount();
				total = total - discount;
				money.setCurrency("€");
				money.setAmount(total);
			}

		}

		entity.setTotalPrice(money);

		this.repository.save(entity);
	}

	//Other business methods
	public String numbersSecuency() {

		final char[] elementos = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
		};

		final char[] conjunto = new char[6];

		final String secuency;

		for (int i = 0; i < 6; i++) {
			final int el = (int) (Math.random() * 10);
			conjunto[i] = elementos[el];
		}

		secuency = new String(conjunto).toUpperCase();
		return secuency;

	}

	public String createTicker(final RequestEntity entity) {

		//The ticker must be as follow: CCC-YY-NNNNNN
		String ticker = new String();

		//Get category item
		String categoryItem = entity.getItem().getItemCategory();
		String category = categoryItem.substring(0, 3).toUpperCase();

		//Get creation year
		Date creationDate = entity.getCreation();

		final Calendar date = new GregorianCalendar();
		date.setTime(creationDate);

		String year = String.valueOf(date.get(Calendar.YEAR)).substring(2);

		ticker = category + "-" + year + "-" + this.numbersSecuency();

		return ticker;

	}

	public Boolean isTickerUnique(final String ticker, final RequestEntity entity) {

		Boolean result = true;

		final ArrayList<RequestEntity> requests = new ArrayList<>(this.repository.findMany());

		final ArrayList<String> tickers = new ArrayList<>();

		for (final RequestEntity s : requests) {
			tickers.add(s.getTicker());
		}

		if (tickers.contains(ticker)) {
			result = false;
			this.createTicker(entity);
		}

		return result;

	}

}
