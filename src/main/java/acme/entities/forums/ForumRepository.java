
package acme.entities.forums;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface ForumRepository extends AbstractRepository {

	@Query("select f from Forum f")
	Collection<Forum> findMany();

	@Query("select f from Forum f where f.id =?1")
	Forum findOneById(int id);

}
