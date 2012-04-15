package org.telokers.model;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 *
 * @author trung
 *
 */
@PersistenceCapable
public class Order extends AbstractModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -1245257231623298176L;

	@Persistent
	private Date orderDate;

	@Persistent
	private Long userId;

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}
}
