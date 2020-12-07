
package acme.features.buyer.letter;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.letters.Letter;
import acme.entities.requests.RequestEntity;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BuyerLetterRepository extends AbstractRepository {

	@Query("select r from Letter r")
	Collection<Letter> findMany();

	@Query("select r from Letter r where r.id =?1")
	Letter findOneById(int id);

	@Query("select r.letter from RequestEntity r where r.id=?1")
	Letter findOneLetterByReqId(int id);

	@Query("select r from RequestEntity r where r.id=?1")
	RequestEntity findReqById(int id);

	
}
