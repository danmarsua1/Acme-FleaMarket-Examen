
package acme.features.authenticated.item;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.items.Item;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedItemRepository extends AbstractRepository {

	@Query("select i from Item i where i.finalMode=true")
	Collection<Item> findMany();

	@Query("select i from Item i where i.id =?1")
	Item findOneById(int id);
}
