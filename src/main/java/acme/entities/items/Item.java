
package acme.entities.items;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.entities.coupons.Coupon;
import acme.entities.requests.RequestEntity;
import acme.entities.roles.Supplier;
import acme.entities.specificationSheets.SpecificationSheet;
import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item extends DomainEntity {

	private static final long			serialVersionUID	= 1L;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^[A-Z]{3}[-][0-9]{2}[-][0-9]{6}$", message = "{supplier.item.ticker.pattern}")
	private String						ticker;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date						creationMoment;

	@NotBlank
	private String						title;

	@NotBlank
	private String						itemCategory;

	@NotBlank
	private String						description;

	@Valid
	@NotNull
	private Money						price;

	@URL
	private String						photo;

	@URL
	private String						link;

	private boolean						finalMode;

	@Pattern(regexp = "^(DRAFT|PUBLISHED)$")
	private String						status;

	// Derived attributes -----------------------------------------------------

	private boolean						newItem;

	// Relationships ----------------------------------------------------------

	@Valid
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	private SpecificationSheet			specificationSheet;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Supplier					supplier;

	//	@NotNull
	//	@Valid
	//	@OneToMany(mappedBy="item")
	//	private Collection<AuditRecord>	auditRecords;

	@Valid
	@OneToMany(mappedBy = "item")
	private Collection<RequestEntity>	requests;

	@OneToOne(optional = true)
	private Coupon						coupon;

}
