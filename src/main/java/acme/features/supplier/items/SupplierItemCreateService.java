
package acme.features.supplier.items;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.customisations.Customisation;
import acme.entities.items.Item;
import acme.entities.roles.Supplier;
import acme.entities.sections.Section;
import acme.entities.specificationSheets.SpecificationSheet;
import acme.features.supplier.sections.SupplierSectionRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class SupplierItemCreateService implements AbstractCreateService<Supplier, Item> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SupplierItemRepository		repository;

	@Autowired
	private SupplierSectionRepository	sectionRepository;


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

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "ticker", "itemCategory", "description", "price", "photo", "link");

	}

	@Override
	public Item instantiate(final Request<Item> request) {

		Item result;
		result = new Item();
		Date moment;

		Supplier supplier;
		SpecificationSheet ss = new SpecificationSheet();
		Collection<Section> sections = new ArrayList<Section>();
		Section section = new Section();

		supplier = this.repository.findSupplierById(request.getPrincipal().getActiveRoleId());
		result.setSupplier(supplier);

		moment = new Date(System.currentTimeMillis() - 1);
		result.setCreationMoment(moment);
		result.setStatus("DRAFT");
		result.setFinalMode(false);

		section.setIndexer(this.createIndexer());
		section.setDescription("Seccion Principal");
		section.setTitle("Seccion Principal");
		sections.add(section);
		ss.setSections(sections);
		result.setSpecificationSheet(ss);

		//GENERAR TICKER
		String ticker = "";

		do {
			ticker = this.createTicker(result);
		} while (!this.isTickerUnique(ticker, result));

		result.setTicker(ticker);

		return result;
	}

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//
		//		Collection<String> tickers = this.repository.findAllTickers();
		//		String tickerUpdate = this.repository.findOneTickerById(request.getModel().getInteger("id"));
		//		tickers.remove(tickerUpdate);

		Customisation customisation = this.repository.findCustomisation();
		String[] CustomisationParameter;
		Integer n = 0;

		//
		//		// Tickers repetidos
		//		if (!errors.hasErrors("ticker")) {
		//			errors.state(request, !tickers.contains(entity.getTicker()), "ticker", "supplier.item.form.error.tickerRepetido");
		//		}
		//

		//Section indexers in its specification sheet are unique
		//		Collection<Section> sections = this.repository.findAllSections();
		//		if(!errors.hasErrors("specificationSheet")) {
		//			for(Section s: entity.getSpecificationSheet().getSections()) {
		//				errors.state(request,!sections.contains(s.getIndexer()),  "specificationSheet", "supplier.item.form.error.indexSectionNoUnico");
		//			}
		//
		//		}

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
		//		// Ticker incorrecto
		//		if (!errors.hasErrors("ticker")) {
		//			List<String> res = new ArrayList<>();
		//			Date moment = new Date(System.currentTimeMillis() - 1);
		//			Integer year = moment.getYear() + 1900;
		//
		//			res.add(entity.getTicker().substring(0, entity.getTicker().indexOf("-")));
		//			res.add(entity.getTicker().substring(entity.getTicker().indexOf("-") + 1, entity.getTicker().indexOf("-") + 3));
		//			res.add(entity.getTicker().substring(entity.getTicker().indexOf("-") + 4, entity.getTicker().length()));
		//
		//			boolean result = res.get(0).matches("[A-Z ]+") && (res.get(0).equals(entity.getSupplier().getItemCategory().substring(0, 1).toUpperCase()) || res.get(0).equals(entity.getSupplier().getItemCategory().substring(0, 2).toUpperCase())
		//							|| res.get(0).equals(entity.getSupplier().getItemCategory().substring(0, 3).toUpperCase())) && res.get(1).equals(year.toString().substring(2)) && res.get(2).matches("^[0-9]{6}$");
		//
		//			errors.state(request, result, "ticker", "supplier.item.form.error.tickerIncorrecto");
		//		}
		//
	}

	@Override
	public void create(final Request<Item> request, final Item entity) {
		assert request != null;
		assert entity != null;

		//GENERAR TICKER
		String ticker = "";

		do {
			ticker = this.createTicker(entity);
		} while (!this.isTickerUnique(ticker, entity));

		entity.setTicker(ticker);

		Date creationMoment;
		creationMoment = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(creationMoment);

		//Cuando se crea un item el estado esta en borrardor y no en modo final
		entity.setStatus("DRAFT");
		entity.setFinalMode(false);
		entity.setNewItem(true);

		this.repository.save(entity);

	}

	//Other business methods
	public String numbersSecuency() {

		final char[] elementos = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
		};

		final char[] conjunto = new char[6];

		final String secuency;

		for (int i = 0; i < 6; i++) {
			final int el = (int) (Math.random() * 10);
			conjunto[i] = elementos[el];
		}

		secuency = new String(conjunto).toUpperCase();
		return secuency;

	}

	public String createTicker(final Item entity) {

		//The ticker must be as follow: CCC-YY-NNNNNN
		String ticker = new String();

		//Get category item
		String categoryItem = entity.getItemCategory();
		String category = "";

		if (categoryItem != null) {
			category = categoryItem.substring(0, 3).toUpperCase();
		} else {
			category = "XXX";
		}

		//Get creation year
		Date creationDate = entity.getCreationMoment();

		final Calendar date = new GregorianCalendar();
		date.setTime(creationDate);

		String year = String.valueOf(date.get(Calendar.YEAR)).substring(2);

		ticker = category + "-" + year + "-" + this.numbersSecuency();

		return ticker;

	}

	public Boolean isTickerUnique(final String ticker, final Item entity) {

		Boolean result = true;

		Collection<Item> items = new ArrayList<>(this.repository.findMany());

		List<String> tickers = new ArrayList<>();

		for (final Item s : items) {
			tickers.add(s.getTicker());
		}

		if (tickers.contains(ticker)) {
			result = false;
			this.createTicker(entity);
		}

		return result;

	}

	public Integer createIndexer() {

		Collection<Section> sections = new ArrayList<>(this.sectionRepository.findMany());

		List<Integer> indexers = new ArrayList<>();

		for (final Section s : sections) {
			indexers.add(s.getIndexer());
		}

		Integer indexer = Collections.max(indexers) + 1;

		return indexer;

	}

}
