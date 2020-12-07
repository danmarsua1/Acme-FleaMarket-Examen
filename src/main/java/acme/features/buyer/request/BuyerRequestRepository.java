
package acme.features.buyer.request;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.coupons.Coupon;
import acme.entities.letters.Letter;
import acme.entities.requests.RequestEntity;
import acme.entities.roles.Buyer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BuyerRequestRepository extends AbstractRepository {

	@Query("select r from RequestEntity r")
	Collection<RequestEntity> findMany();

	@Query("select r from RequestEntity r where r.buyer.id=?1")
	Collection<RequestEntity> findManyByBuyerId(int buyerId);

	@Query("select r from RequestEntity r where r.id =?1")
	RequestEntity findOneById(int id);

	@Query("select b from Buyer b where b.id =?1")
	Buyer findBuyerById(int activeRoleId);

	@Query("select i.letter from RequestEntity i where i.id=?1")
	Letter findLetterByReqId(int id);

	@Query("select i.item.coupon from RequestEntity i where i.id=?1")
	Coupon findCouponByItemId(int id);
}
