
package acme.features.administrator.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.advertisements.Advertisement;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(n) from New n")
	Integer numberNews();

	@Query("select count(m) from MaterialSheet m")
	Integer numberMaterialSheets();

	@Query("select count(t) from ToolSheet t")
	Integer numberToolSheets();

	@Query("select count(s) from Suggestion s")
	Integer numberSuggestions();

	@Query("select count(f) from Figment f")
	Integer numberFigments();

	@Query("select count(a) from Advertisement a where CURRENT_TIMESTAMP >= a.creationMoment and CURRENT_TIMESTAMP < a.displayPeriod")
	Integer numberAdvertisement();

	@Query("select min (a.item) from Advertisement a")
	Double minDiscountAdvertisements();

	@Query("select max (a.item) from Advertisement a")
	Double maxDiscountAdvertisements();

	@Query("select count(a) from Advertisement a where a.discounts LIKE '%SMALL%'")
	Double averageSmallDiscountAdvertisements();

	@Query("select count(a) from Advertisement a where a.discounts LIKE '%AVERAGE%'")
	Double averageAverageDiscountAdvertisements();

	@Query("select count(a) from Advertisement a where a.discounts LIKE '%LARGE%'")
	Double averageLargeDiscountAdvertisements();

	@Query("select a from Advertisement a")
	Collection<Advertisement> stddevDiscountAdvertisements();

	@Query("select count(s) from Supplier s")
	Double numberSuppliers();

	@Query("select count(i) from Item i")
	Double numberItems();

	@Query("select count(r) from RequestEntity r")
	Double numberRequests();

	@Query("select count(b) from Buyer b")
	Double numberBuyers();
	
	//EXAMEN
	
	@Query("select 1.0 * count(c) / (select count(i) FROM Item i) from Coupon c")
	Double ratioOfItemsGrouped();
	
	@Query("select 1.0 * count(l)/ (select count(r) FROM RequestEntity r)  from Letter l where l.link!=''")
	Double ratioOfRequestsHaveALink();
	
	@Query("select 1.0 * count(l) / (select count(r) FROM RequestEntity r) from Letter l where l.password!=''")
	Double ratioOfRequestsHavePassword();
}
