
package acme.features.supplier.items;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.customisations.Customisation;
import acme.entities.items.Item;
import acme.entities.requests.RequestEntity;
import acme.entities.roles.Supplier;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class SupplierItemUpdateService implements AbstractUpdateService<Supplier, Item> {

	@Autowired
	private SupplierItemRepository repository;


	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creationMoment");

	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "itemCategory", "description", "price", "photo", "link", "status", "finalMode");
		Collection<RequestEntity> requests = this.repository.findRequestByItemId(entity.getId());
		if (requests != null && requests.size() > 0) {
			boolean hasRequests = true;
			model.setAttribute("hasRequests", hasRequests);
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

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Customisation customisation = this.repository.findCustomisation();
		String[] CustomisationParameter;
		Integer n = 0;

		// Spam título
		if (!errors.hasErrors("title")) {

			Double spam = Double.valueOf(entity.getTitle().split(" ").length) * customisation.getThreshold() / 100.0;
			CustomisationParameter = customisation.getSpamwords().split(",");

			for (String s : CustomisationParameter) {
				String l = entity.getTitle().toLowerCase();
				int i = l.indexOf(s);
				while (i != -1) {
					n++;
					l = l.substring(i + 1);
					i = l.indexOf(s);
				}
				errors.state(request, n <= spam, "title", "supplier.item.form.error.tituloConSpam");

				if (n > spam) {
					break;
				}
			}

		}

		// Spam descripción
		if (!errors.hasErrors("description")) {

			Double spam = Double.valueOf(entity.getDescription().split(" ").length) * customisation.getThreshold() / 100.0;
			CustomisationParameter = customisation.getSpamwords().split(",");

			for (String s : CustomisationParameter) {
				String l = entity.getDescription().toLowerCase();
				int i = l.indexOf(s);
				while (i != -1) {
					n++;
					l = l.substring(i + 1);
					i = l.indexOf(s);
				}
				errors.state(request, n <= spam, "description", "supplier.item.form.error.descripcionConSpam");

				if (n > spam) {
					break;
				}
			}

		}

	}

	@Override
	public void update(final Request<Item> request, final Item entity) {
		assert request != null;
		assert entity != null;

		if (entity.isFinalMode()) {
			entity.setStatus("PUBLISHED");
		}
		this.repository.save(entity);

	}

}
