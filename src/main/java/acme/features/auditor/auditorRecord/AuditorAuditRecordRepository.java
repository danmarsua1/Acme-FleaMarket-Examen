
package acme.features.auditor.auditorRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.items.Item;
import acme.entities.roles.Auditor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	//@Query("select i.auditRecord from Item i where i.auditRecord=?1 and i.auditRecord.auditor.id=?1")
	@Query("select a from AuditRecord a where a.auditor.id=?1")
	Collection<AuditRecord> findManyByAuditorId(int auditorId);

	//@Query("select distinct a from AuditRecord a where a.id not in (select i.auditRecord.id from Item i where i.auditRecord.auditor.id = ?1)")
	@Query("select distinct a from AuditRecord a where a.id not in (select a from AuditRecord a where a.auditor.id=?1)")
	Collection<AuditRecord> findOthersByAuditorId(int auditorId);

	@Query("select a from AuditRecord a where a.id =?1")
	AuditRecord findOneById(int id);

	@Query("select a from AuditRecord a where a.auditor.id=?1 and a.item.id=?2")
	Collection<AuditRecord> findManyByAuditorIdByItemId(int activeRoleId, int itemId);

	@Query("select a from AuditRecord a where a.auditor.id!=?1 and a.item.id=?2")
	Collection<AuditRecord> findManyByNotAuditorIdByItemId(int activeRoleId, int itemId);

	@Query("select a from AuditRecord a where a.item.id=?1")
	Collection<AuditRecord> findManyByItemId(int id);

	@Query("select a.auditor from AuditRecord a where a.auditor.id=?1")
	Auditor findOneAuditorById(int id);

	@Query("select a.item from AuditRecord a where a.item.id=?1")
	Item findOneItemById(int itemId);

	@Query("select a from Auditor a where a.id=?1")
	Auditor findAuditorById(int id);

}
