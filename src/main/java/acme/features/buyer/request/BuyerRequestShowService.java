
package acme.features.buyer.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.coupons.Coupon;
import acme.entities.letters.Letter;
import acme.entities.requests.RequestEntity;
import acme.entities.roles.Buyer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class BuyerRequestShowService implements AbstractShowService<Buyer, RequestEntity> {

	@Autowired
	BuyerRequestRepository repository;


	@Override
	public boolean authorise(final Request<RequestEntity> request) {
		assert request != null;

		boolean result;
		int itemId;
		RequestEntity item;
		Buyer buyer;
		Principal principal;

		itemId = request.getModel().getInteger("id");
		item = this.repository.findOneById(itemId);
		buyer = item.getBuyer();
		principal = request.getPrincipal();
		result = buyer.getUserAccount().getId() == principal.getAccountId();

		return result;
	}

	@Override
	public void unbind(final Request<RequestEntity> request, final RequestEntity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "creation", "quantity", "notes", "totalPrice", "item.title", "letter.description", "letter.link", "letter.password", "letter.status");
		
		Letter letter = this.repository.findLetterByReqId(entity.getId());
		if (letter != null) {
			model.setAttribute("hasLetter", true);
			if(letter.getStatus().equals("ACCEPTED")) {
				model.setAttribute("isAccepted", true);
			}else {
				model.setAttribute("isAccepted", false);
			}
		} else {
			model.setAttribute("hasLetter", false);
		}
		
		Coupon coupon = this.repository.findCouponByItemId(entity.getId());
		if (coupon != null) {
			model.setAttribute("hasCoupon", true);

		} else {
			model.setAttribute("hasCoupon", false);
		}

	}

	@Override
	public RequestEntity findOne(final Request<RequestEntity> request) {
		assert request != null;

		RequestEntity result;
		int id;

		id = request.getModel().getInteger("id");

		result = this.repository.findOneById(id);
		return result;
	}

}
