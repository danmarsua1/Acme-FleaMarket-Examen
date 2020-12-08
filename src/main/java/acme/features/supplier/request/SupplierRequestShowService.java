
package acme.features.supplier.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.letters.Letter;
import acme.entities.requests.RequestEntity;
import acme.entities.roles.Supplier;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class SupplierRequestShowService implements AbstractShowService<Supplier, RequestEntity> {

	@Autowired
	SupplierRequestRepository repository;


	@Override
	public boolean authorise(final Request<RequestEntity> request) {
		assert request != null;

		boolean result;
		int requestId;
		RequestEntity r;
		Supplier supplier;
		Principal principal;

		requestId = request.getModel().getInteger("id");
		r = this.repository.findOneById(requestId);
		supplier = r.getItem().getSupplier();
		principal = request.getPrincipal();
		result = supplier.getUserAccount().getId() == principal.getAccountId();

		return result;
	}

	@Override
	public void unbind(final Request<RequestEntity> request, final RequestEntity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		String referenceItem = entity.getItem().getTicker();
		model.setAttribute("referenceItem", referenceItem);
		String itemSupplier = entity.getItem().getSupplier().getUserAccount().getUsername();
		model.setAttribute("itemSupplier", itemSupplier);

		request.unbind(entity, model, "ticker", "creation", "quantity", "status", "notes", "totalPrice", "rejectionJustification", "letter.description", "letter.link", "letter.password", "letter.status");

		Letter letter = this.repository.findLetterByReqId(entity.getId());

		if (letter != null) {
			model.setAttribute("hasLetter", true);
			if (letter.getStatus().equals("PENDING")) {
				model.setAttribute("isPending", true);
			} else {
				model.setAttribute("isPending", false);
			}
		} else {
			model.setAttribute("hasLetter", false);
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
