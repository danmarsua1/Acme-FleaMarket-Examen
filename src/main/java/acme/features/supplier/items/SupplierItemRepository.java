
package acme.features.supplier.items;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.coupons.Coupon;
import acme.entities.customisations.Customisation;
import acme.entities.items.Item;
import acme.entities.requests.RequestEntity;
import acme.entities.roles.Supplier;
import acme.entities.sections.Section;
import acme.entities.specificationSheets.SpecificationSheet;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface SupplierItemRepository extends AbstractRepository {

	@Query("select i from Item i where i.supplier.id=?1")
	Collection<Item> findManyBySupplierId(int supplierId);

	@Query("select i from Item i where i.id =?1")
	Item findOneById(int id);

	@Query("select s from Supplier s where s.id =?1")
	Supplier findSupplierById(int id);

	@Query("select i.ticker from Item i where i.id = ?1")
	String findOneTickerById(int id);

	@Query("select i.ticker from Item i")
	Collection<String> findAllTickers();

	@Query("select c from Customisation c")
	Customisation findCustomisation();

	@Query("select i.requests from Item i where i.id=?1")
	Collection<RequestEntity> findRequestByItemId(int id);

	@Query("select i.specificationSheet from Item i where i.id=?1")
	SpecificationSheet findSpecificationSheetById(int id);

	@Query("select i.specificationSheet.sections from Item i where i.id=?1")
	Collection<Section> findAllSections();

	@Query("select i from Item i")
	Collection<Item> findMany();

	@Query("select i.coupon from Item i where i.id=?1")
	Coupon findCouponByItemId(int id);
}
