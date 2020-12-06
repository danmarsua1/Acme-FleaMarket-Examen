
package acme.features.supplier.coupons;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.coupons.Coupon;
import acme.entities.items.Item;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface SupplierCouponRepository extends AbstractRepository {

	@Query("select i.coupon from Item i where i.id =?1")
	Coupon findCouponByItem(int id);

	@Query("select c.code from Coupon c where c.id = ?1")
	String findOneTickerById(int id);

	@Query("select c from Coupon c")
	Collection<Coupon> findMany();

	@Query("select i.coupon from Item i where i.id=?1")
	Coupon findOneCouponByItemId(int id);

	@Query("select i from Item i where i.id=?1")
	Item findItemById(int id);
}
