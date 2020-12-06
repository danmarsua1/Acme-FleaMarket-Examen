
package acme.forms;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard implements Serializable {

	private static final long	serialVersionUID	= 1L;

	Integer						numberNews;

	Integer						numberMaterialSheets;

	Integer						numberToolSheets;

	Integer						numberSuggestions;

	Integer						numberFigments;

	Integer						numberAdvertisement;

	Double						minDiscountAdvertisements;

	String						minDiscount;

	Double						maxDiscountAdvertisements;

	String						maxDiscount;

	Double						averageSmallDiscountAdvertisements;

	Double						averageAverageDiscountAdvertisements;

	Double						averageLargeDiscountAdvertisements;

	Double						stddevSDiscountAdvertisements;

	Double						stddevADiscountAdvertisements;

	Double						stddevLDiscountAdvertisements;

	Double						averageItemsPerSupplier;

	Double						averageRequestsPerSupplier;

	Double						averageRequestsPerBuyer;

}
