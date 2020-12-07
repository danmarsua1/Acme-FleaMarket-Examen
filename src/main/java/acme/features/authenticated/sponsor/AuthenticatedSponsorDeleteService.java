
package acme.features.authenticated.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.Banner;
import acme.entities.creditCards.CreditCard;
import acme.entities.roles.Sponsor;
import acme.features.sponsor.banner.SponsorBannerRepository;
import acme.features.sponsor.creditCard.SponsorCreditCardRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractDeleteService;

@Service
public class AuthenticatedSponsorDeleteService implements AbstractDeleteService<Authenticated, Sponsor> {

	// Internal state ------------------------------------------------------------------
	@Autowired
	AuthenticatedSponsorRepository	repository;

	@Autowired
	SponsorCreditCardRepository		creditCardRepository;

	@Autowired
	SponsorBannerRepository			bannerRepository;


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

		request.bind(entity, errors, "banner");
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
	public Sponsor findOne(final Request<Sponsor> request) {
		assert request != null;

		Sponsor result;
		Principal principal;
		int userAccountId;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();

		result = this.repository.findOneSponsorByUserAccountId(userAccountId);

		return result;
	}

	@Override
	public void validate(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Sponsor> request, final Sponsor entity) {
		assert request != null;
		assert entity != null;

		Integer idCC = entity.getCreditCard().getId();

		CreditCard creditCard = this.creditCardRepository.findOneById(idCC);
		entity.setCreditCard(null);

		this.repository.save(entity);

		Banner banner = this.bannerRepository.isLinkedByCreditCardId(idCC);

		if (banner != null) {
			banner.setCreditCard(null);
			this.bannerRepository.save(banner);
		}

		this.creditCardRepository.delete(creditCard);

	}

}
