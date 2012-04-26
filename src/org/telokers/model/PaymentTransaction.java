/**
 *
 */
package org.telokers.model;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Entity;

/**
 * @author trung
 *
 */
public class PaymentTransaction extends AbstractModel {

	public static enum PaymentTransactionProperty {
		id,
		userId,
		productIds,
		refId,
		timestamp,
		status
	}

	public PaymentTransaction(String key) {
		super(key);
		entity = new Entity(getKind(), key);
	}

	public void setId(String key) {
		setProperty(PaymentTransactionProperty.id, key);
	}

	public void setUserId(String userId) {
		setProperty(PaymentTransactionProperty.userId, userId);
	}

	public void setProductIds(List<String> ids) {
		setProperty(PaymentTransactionProperty.productIds, ids);
	}

	public void setRefId(String refId) {
		setProperty(PaymentTransactionProperty.refId, refId);
	}

	public void setTimestamp(Date d) {
		setProperty(PaymentTransactionProperty.timestamp, d);
	}

	public void setStatus(String status) {
		setProperty(PaymentTransactionProperty.status, status);
	}

	/**
	 * @return
	 */
	public static String getKind() {
		return PaymentTransaction.class.getSimpleName();
	}

	/**
	 * @param entity
	 * @throws Exception
	 */
	public PaymentTransaction(Entity entity) throws Exception {
		super(entity);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.telokers.model.AbstractModel#getEntity()
	 */
	@Override
	public Entity getEntity() {
		return entity;
	}


}
