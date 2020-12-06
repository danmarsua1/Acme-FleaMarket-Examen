
package acme.entities.coupons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Coupon extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	//Atributes------------------------------------------

	@NotBlank
	private String				description;

	@Valid
	@NotNull
	private Money				minMoney;

	@Valid
	@NotNull
	private Money				maxMoney;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^[0-9]{6}$", message = "yyddmm")
	private String				code;

	// Relationships ----------------------------------------------------------

}
