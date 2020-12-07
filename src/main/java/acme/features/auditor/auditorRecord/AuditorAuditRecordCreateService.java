
package acme.features.auditor.auditorRecord;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.items.Item;
import acme.entities.roles.Auditor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class AuditorAuditRecordCreateService implements AbstractCreateService<Auditor, AuditRecord> {

	@Autowired
	AuditorAuditRecordRepository repository;


	@Override
	public boolean authorise(final Request<AuditRecord> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<AuditRecord> request, final AuditRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creationMoment", "auditor", "item", "finalMode", "status");

	}

	@Override
	public void unbind(final Request<AuditRecord> request, final AuditRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("item", entity.getItem().getId());
		request.unbind(entity, model, "title", "creationMoment", "body");
	}

	@Override
	public AuditRecord instantiate(final Request<AuditRecord> request) {
		AuditRecord result;
		result = new AuditRecord();

		int id, itemId;
		Date moment;
		Auditor auditor;
		Item item;
		Principal principal;

		principal = request.getPrincipal();
		id = principal.getActiveRoleId();
		auditor = this.repository.findAuditorById(id);
		result.setAuditor(auditor);

		itemId = request.getModel().getInteger("item");
		item = this.repository.findOneItemById(itemId);
		result.setItem(item);

		moment = new Date(System.currentTimeMillis() - 1);
		result.setCreationMoment(moment);
		result.setFinalMode(false);
		result.setStatus("DRAFT");

		return result;
	}

	@Override
	public void validate(final Request<AuditRecord> request, final AuditRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<AuditRecord> request, final AuditRecord entity) {
		assert request != null;
		assert entity != null;

		Item i;
		int item = request.getModel().getInteger("item");

		Date creationMoment = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(creationMoment);

		i = this.repository.findOneItemById(item);

		entity.setItem(i);
		entity.setFinalMode(false);
		entity.setStatus("DRAFT");

		this.repository.save(entity);

	}

}
