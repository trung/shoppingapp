package org.telokers.model;

import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 *
 * @author trung
 *
 */
@PersistenceCapable
public class OrderItem extends AbstractModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 5902863751984777770L;

	@Persistent
	private Long orderId;

	@Persistent
	private Set<Long> itemIds;

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setItemIds(Set<Long> itemIds) {
		this.itemIds = itemIds;
	}

	public Set<Long> getItemIds() {
		return itemIds;
	}

}
