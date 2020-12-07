package acme.features.supplier.request;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.requests.RequestEntity;
import acme.entities.roles.Supplier;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class SupplierRequestListService implements AbstractListService<Supplier, RequestEntity>{
	
	@Autowired
	SupplierRequestRepository repository;

	@Override
	public boolean authorise(Request<RequestEntity> request) {
		assert request != null;
		return request.getPrincipal().hasRole(Supplier.class);
	}

	@Override
	public void unbind(Request<RequestEntity> request, RequestEntity entity, Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "creation");
	
	}

	@Override
	public Collection<RequestEntity> findMany(Request<RequestEntity> request) {
		assert request != null;

		Collection<RequestEntity> result;
		Principal principal;
		
		principal =request.getPrincipal();

		result = this.repository.findManyRequestEntity(principal.getActiveRoleId());

		return result;
	}


}
