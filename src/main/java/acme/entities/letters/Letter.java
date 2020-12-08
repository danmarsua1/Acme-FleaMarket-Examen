
package acme.entities.letters;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Letter extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	//Atributes------------------------------------------

	@NotBlank
	private String				description;

	@URL
	private String				link;

	@Pattern(regexp = "^((?=[^ ]{10,})(?=(.*[a-z].*){1,})(?=(.*[A-Z].*){1,})(?=(.*[0-9].*){1,})((.*\\p{P}.*){1,}))?$", message = "{buyer.letter.error.password}")
	private String				password;

	@NotNull
	@Pattern(regexp = "^(PENDING|ACCEPTED|REJECTED)$", message = "{letter.error.status}")
	private String				status;					// PENDIENTE, ACEPTADO O RECHAZADO

}
