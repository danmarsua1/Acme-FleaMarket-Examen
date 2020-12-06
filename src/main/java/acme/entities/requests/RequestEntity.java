
package acme.entities.requests;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import acme.entities.items.Item;
import acme.entities.roles.Buyer;
import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RequestEntity extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^[A-Z]{3}[-][0-9]{2}[-][0-9]{6}$", message = "{buyer.request.ticker.pattern}")
	private String				ticker;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				creation;

	@NotNull
	@Min(0)
	private Double				quantity;

	private String				notes;

	// Derived attributes -----------------------------------------------------

	@NotNull
	private RequestEntityStatus	status;					// PENDIENTE, ACEPTADO O RECHAZADO

	private String				rejectionJustification;			// OBLIGATORIO AL RECHAZAR

	private Money				totalPrice;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Item				item;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Buyer				buyer;

}
