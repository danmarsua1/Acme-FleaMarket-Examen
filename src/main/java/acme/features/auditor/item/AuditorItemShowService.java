
package acme.features.auditor.item;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.items.Item;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class AuditorItemShowService implements AbstractShowService<Auditor, Item> {

	@Autowired
	AuditorItemRepository repository;


	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;

		//Si tengo este codigo de abajo no no se puede realizar el show de los items que no son mios
		//		boolean result;
		//		int itemId;
		//		Item item;
		//		Auditor auditor;
		//		Principal principal;
		//
		//		itemId = request.getModel().getInteger("id");
		//		item = this.repository.findOneById(itemId);
		//		auditor = item.getAuditRecord().getAuditor();
		//		principal = request.getPrincipal();
		//		result = auditor.getUserAccount().getId() == principal.getAccountId();

		return true;
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		int itemId;

		itemId = request.getModel().getInteger("id");
		String uri = request.getServletRequest().getHeader("Referer");
		if (uri.contains("list-mine") || uri.contains("list")) {
			model.setAttribute("myAuditRecord", true);
		}
		model.setAttribute("item", itemId);

		//Fecha actual
		Calendar today = Calendar.getInstance();
		int todayYear = today.get(Calendar.YEAR);
		int todayMonth = today.get(Calendar.MONTH);
		int todayDay = today.get(Calendar.DAY_OF_MONTH);

		LocalDate now = LocalDate.of(todayYear, todayMonth, todayDay);

		//Fecha creacion
		Calendar creation = new GregorianCalendar();
		creation.setTime(entity.getCreationMoment());
		int creationYear = creation.get(Calendar.YEAR);
		int creationMonth = creation.get(Calendar.MONTH);
		int creationDay = creation.get(Calendar.DAY_OF_MONTH);

		LocalDate creationDate = LocalDate.of(creationYear, creationMonth, creationDay);

		long daysBetween = ChronoUnit.DAYS.between(creationDate, now);
		if (daysBetween <= 7) {
			entity.setNewItem(true);
		}

		request.unbind(entity, model, "ticker", "creationMoment", "title", "itemCategory", "description", "price", "photo", "link", "newItem");

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

}
