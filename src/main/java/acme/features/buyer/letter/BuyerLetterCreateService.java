
package acme.features.buyer.letter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.letters.Letter;
import acme.entities.requests.RequestEntity;
import acme.entities.roles.Buyer;
import acme.features.buyer.request.BuyerRequestRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class BuyerLetterCreateService implements AbstractCreateService<Buyer, Letter> {

	// Internal state
	// ------------------------------------------------------------------
	@Autowired
	BuyerLetterRepository repository;
	
	@Autowired
	BuyerRequestRepository requestRepository;


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
		
		request.unbind(entity, model, "description", "link", "password");

		Integer idreq = request.getModel().getInteger("req");
		model.setAttribute("req", idreq);

	}

	@Override
	public Letter instantiate(final Request<Letter> request) {
		Letter result = new Letter();
		
		result.setStatus("PENDING");
		return result;
	}

	@Override
	public void validate(final Request<Letter> request, final Letter entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (entity.getPassword().length()>0 && entity.getLink().length()==0) {
			
			errors.state(request, false, "link", "buyer.letter.form.error.linkCanNotBeEmpty");
		}

		
	}

	@Override
	public void create(final Request<Letter> request, final Letter entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
		
		Integer id = request.getModel().getInteger("req");
		RequestEntity req = this.repository.findReqById(id);

		req.setLetter(entity);
		
		this.requestRepository.save(req);

	}

	

}
