
package acme.features.administrator.chart;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorChartRepository extends AbstractRepository {

	@Query("select n.category,count(n) FROM New n group by n.category order by n.category")
	Object[] findNewsByCategory();

	@Query("select (count(n)),(select count(nn) from New nn where LOWER(nn.category) NOT LIKE '%warning%') from New n where LOWER(n.category) LIKE '%warning%' group by n.category order by n.category")
	Object[] findRatioWarningNews();

	@Query("select a.discounts,count(a) FROM Advertisement a group by a.discounts order by a.discounts")
	Object[] findAdvertisementByDiscount();

	@Query("select i.itemCategory,count(i) FROM Item i group by i.itemCategory order by i.itemCategory")
	Object[] findItemsByCategory();

	@Query("select count(s), (select count(ss) from Sponsor ss where ss.creditCard.expired=false), (select count(sss) from Sponsor sss where sss.creditCard.expired=true) from Sponsor s where s.creditCard is null")
	Object[] findSponsorByCreditCard();

	@Query("select a.status,count(a) FROM RequestEntity a group by a.status order by a.status")
	Object[] findRequestStatus();

	@Query("select date(a.creation),count(a) FROM RequestEntity a where a.creation > ?1 and a.status = 2 group by day(a.creation)")
	Object[] findRejectedRequestsLastThreeWeeks(Date d);

	@Query("select date(a.creation),count(a) FROM RequestEntity a where a.creation > ?1 and a.status = 0 group by day(a.creation)")
	Object[] findPendingRequestsLastThreeWeeks(Date d);

	@Query("select date(a.creation),count(a) FROM RequestEntity a where a.creation > ?1 and a.status = 1 group by day(a.creation)")
	Object[] findAcceptedRequestsLastThreeWeeks(Date d);
}
