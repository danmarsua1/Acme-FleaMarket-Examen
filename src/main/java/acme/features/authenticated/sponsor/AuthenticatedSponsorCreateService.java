
package acme.features.authenticated.sponsor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.Banner;
import acme.entities.creditCards.CreditCard;
import acme.entities.roles.Sponsor;
import acme.features.sponsor.creditCard.SponsorCreditCardRepository;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedSponsorCreateService implements AbstractCreateService<Authenticated, Sponsor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedSponsorRepository	repository;

	@Autowired
	private SponsorCreditCardRepository		creditCardRepository;


	@Override
	public boolean authorise(final Request<Sponsor> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Sponsor> request, final Sponsor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "firmName");
		request.unbind(entity, model, "creditCard.holderName", "creditCard.number", "creditCard.brand", "creditCard.month", "creditCard.year", "creditCard.cvv");

	}

	@Override
	public Sponsor instantiate(final Request<Sponsor> request) {
		assert request != null;

		Sponsor result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		result = new Sponsor();
		result.setUserAccount(userAccount);

		List<Banner> banners = new ArrayList<Banner>();

		result.setBanners(banners);

		return result;
	}

	@Override
	public void validate(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Model model = request.getModel();

		String holderName = (String) model.getAttribute("creditCard.holderName");
		String number = (String) model.getAttribute("creditCard.number");
		String brand = (String) model.getAttribute("creditCard.brand");
		Integer month = model.getInteger("creditCard.month");
		Integer year = model.getInteger("creditCard.year");
		String cvv = (String) model.getAttribute("creditCard.cvv");

		//Si alguno de los campos de la tarjeta de crédito esta rellenado, se activa la validación
		if (holderName.length() > 0 || number.length() > 0 || brand.length() > 0 || month != null || year != null || cvv.length() > 0) {

			//Check holder name
			if (holderName.length() <= 0) {
				errors.state(request, false, "creditCard.holderName", "javax.validation.constraints.NotBlank.message");
			}

			//Check creditcard number
			if (number.length() <= 0) {
				errors.state(request, false, "creditCard.number", "javax.validation.constraints.NotBlank.message");
			}
			if (!AuthenticatedSponsorCreateService.validateCreditCardNumber(number)) {
				errors.state(request, false, "creditCard.number", "org.hibernate.validator.constraints.CreditCardNumber.message");
			}

			//Check brand
			if (brand.length() <= 0) {
				errors.state(request, false, "creditCard.brand", "javax.validation.constraints.NotBlank.message");
			}

			//Check month
			if (month == null) {
				errors.state(request, false, "creditCard.month", "javax.validation.constraints.NotNull.message");
			} else {
				if (month < 1 || month > 12) {
					errors.state(request, false, "creditCard.month", "creditCard.error.month");
				}
			}

			//Check year
			if (year == null) {
				errors.state(request, false, "creditCard.year", "javax.validation.constraints.NotNull.message");
			} else {
				if (!String.valueOf(year).matches("^\\d{4}$")) {
					errors.state(request, false, "creditCard.year", "creditCard.error.year");
				}
			}

			//Check CVV
			if (cvv.length() <= 0) {
				errors.state(request, false, "creditCard.cvv", "javax.validation.constraints.NotBlank.message");
			}
			if (!cvv.matches("^\\d{3,4}$")) {
				errors.state(request, false, "creditCard.cvv", "creditCard.error.cvv");
			}

		}

	}

	@Override
	public void create(final Request<Sponsor> request, final Sponsor entity) {
		assert request != null;
		assert entity != null;

		CreditCard creditCard = new CreditCard();

		Model model = request.getModel();

		String holderName = (String) model.getAttribute("creditCard.holderName");
		String number = (String) model.getAttribute("creditCard.number");
		String brand = (String) model.getAttribute("creditCard.brand");
		Integer month = model.getInteger("creditCard.month");
		Integer year = model.getInteger("creditCard.year");
		String cvv = (String) model.getAttribute("creditCard.cvv");

		if (holderName.length() > 0 && number.length() > 0 && brand.length() > 0 && month != null && year != null && cvv.length() > 0) {

			creditCard.setHolderName(holderName);
			creditCard.setNumber(number);
			creditCard.setBrand(brand);
			creditCard.setMonth(month);
			creditCard.setYear(year);
			creditCard.setCvv(cvv);

			entity.setCreditCard(creditCard);
			this.creditCardRepository.save(creditCard);

		} else {
			entity.setCreditCard(null);
		}

		this.repository.save(entity);

	}

	@Override
	public void onSuccess(final Request<Sponsor> request, final Response<Sponsor> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

	//Other business methods--------------------------------------------------------------------------------
	private static boolean validateCreditCardNumber(final String str) {

		boolean res;

		int[] ints = new int[str.length()];
		for (int i = 0; i < str.length(); i++) {
			ints[i] = Integer.parseInt(str.substring(i, i + 1));
		}
		for (int i = ints.length - 2; i >= 0; i = i - 2) {
			int j = ints[i];
			j = j * 2;
			if (j > 9) {
				j = j % 10 + 1;
			}
			ints[i] = j;
		}
		int sum = 0;
		for (int j : ints) {
			sum += j;
		}
		if (sum % 10 == 0) {
			res = true;
		} else {
			res = false;
		}

		return res;

	}

}
