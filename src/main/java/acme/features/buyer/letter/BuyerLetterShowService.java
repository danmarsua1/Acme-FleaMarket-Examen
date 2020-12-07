
package acme.features.buyer.letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.letters.Letter;
import acme.entities.roles.Buyer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class BuyerLetterShowService implements AbstractShowService<Buyer, Letter> {

	@Autowired
	BuyerLetterRepository repository;


	@Override
	public boolean authorise(final Request<Letter> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Letter> request, final Letter entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "link", "description", "password");
	}

	@Override
	public Letter findOne(final Request<Letter> request) {
		assert request != null;

		Letter result;
		int id;

		id = request.getModel().getInteger("id");

		result = this.repository.findOneById(id);
		return result;
	}

}
