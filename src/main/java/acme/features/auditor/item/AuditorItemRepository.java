
package acme.features.auditor.item;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.items.Item;
import acme.entities.roles.Auditor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorItemRepository extends AbstractRepository {

	//@Query("select i from Item i where i.auditRecords=?1")
	@Query("select distinct a.item from AuditRecord a where a.auditor.id=?1")
	Collection<Item> findManyByAuditorId(int auditorId);

	@Query("select distinct a.item from AuditRecord a where a.id not in (select a from AuditRecord a where a.auditor.id=?1)")
	Collection<Item> findOthersByAuditorId(int auditorId);

	@Query("select i from Item i where i.id =?1")
	Item findOneById(int id);

	@Query("select a from AuditRecord a where a.item.id=?1 and a.auditor.id=?2")
	Collection<AuditRecord> findAuditRecordByItemIdAuditorId(int itemId, int auditorId);

	@Query("select a.auditor from AuditRecord a where a.auditor.id=?1")
	Auditor findAuditorById(int auditRecordId);

	@Query("select i from Item i where i.finalMode=true")
	Collection<Item> findMany();

}
