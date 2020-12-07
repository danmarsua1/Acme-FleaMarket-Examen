
package acme.features.buyer.letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.letters.Letter;
import acme.entities.requests.RequestEntity;
import acme.entities.roles.Buyer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class BuyerLetterDeleteService implements AbstractDeleteService<Buyer, Letter> {

	@Autowired
	private BuyerLetterRepository repository;
	

	@Override
	public boolean authorise(final Request<Letter> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Letter> request, final Letter entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "req");

	}

	@Override
	public void unbind(final Request<Letter> request, final Letter entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "description", "link", "password", "status");

		Integer req = request.getModel().getInteger("req");
		model.setAttribute("req", req);
	}

	@Override
	public Letter findOne(final Request<Letter> request) {
		assert request != null;
		Letter result;

		int id;
		id = request.getModel().getInteger("req");
		result = this.repository.findOneLetterByReqId(id);
		return result;

	}

	@Override
	public void validate(final Request<Letter> request, final Letter entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void delete(final Request<Letter> request, final Letter entity) {
		assert request != null;
		assert entity != null;

		Integer id = request.getModel().getInteger("req");
		RequestEntity req = this.repository.findReqById(id);
		req.setLetter(null);

		this.repository.delete(entity);

	}

}
