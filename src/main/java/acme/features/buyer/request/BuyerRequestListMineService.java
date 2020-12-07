
package acme.features.buyer.request;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.requests.RequestEntity;
import acme.entities.roles.Buyer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class BuyerRequestListMineService implements AbstractListService<Buyer, RequestEntity> {

	@Autowired
	BuyerRequestRepository repository;


	@Override
	public boolean authorise(final Request<RequestEntity> request) {
		assert request != null;
		return true;

	}

	@Override
	public void unbind(final Request<RequestEntity> request, final RequestEntity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "creation", "quantity", "totalPrice");
	}

	@Override
	public Collection<RequestEntity> findMany(final Request<RequestEntity> request) {
		assert request != null;

		Collection<RequestEntity> result;
		Principal principal;

		principal = request.getPrincipal();

		result = this.repository.findManyByBuyerId(principal.getActiveRoleId());

		return result;
	}

}
