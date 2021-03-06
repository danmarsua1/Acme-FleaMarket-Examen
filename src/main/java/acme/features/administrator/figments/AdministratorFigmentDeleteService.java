package acme.features.administrator.figments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.figments.Figment;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractDeleteService;

@Service
public class AdministratorFigmentDeleteService implements AbstractDeleteService<Administrator, Figment> {

	// Internal state ------------------------------------------------------------------
	@Autowired
	AdministratorFigmentRepository repository;
	
	@Override
	public boolean authorise(Request<Figment> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(Request<Figment> request, Figment entity, Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(Request<Figment> request, Figment entity, Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "nameInventor", "description","creationMoment", "minMoney", "maxMoney");
	}

	@Override
	public Figment findOne(Request<Figment> request) {
		assert request != null;

		Figment result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(Request<Figment> request, Figment entity, Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(Request<Figment> request, Figment entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
		
	}

}
