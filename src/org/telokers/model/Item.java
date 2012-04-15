/**
 *
 */
package org.telokers.model;

import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.telokers.model.response.JSONFriendly;
import org.telokers.service.utils.JSONUtils;

import com.google.appengine.api.datastore.Key;

/**
 * @author trung
 *
 */
@PersistenceCapable
public class Item extends AbstractModel implements JSONFriendly {

	/**
	 *
	 */
	private static final long serialVersionUID = 1744279467890897132L;

	@Persistent
	private String itemName;

	@Persistent
	private String itemDescription;

	@Persistent
	private Long quantity;

	@Persistent
	private Set<Key> categoryIds;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	/**
	 * @param categoryIds the categoryIds to set
	 */
	public void setCategoryIds(Set<Key> categoryIds) {
		this.categoryIds = categoryIds;
	}

	/**
	 * @return the categoryIds
	 */
	public Set<Key> getCategoryIds() {
		return categoryIds;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	/* (non-Javadoc)
	 * @see org.telokers.model.response.JSONFriendly#toJSONString()
	 */
	@Override
	public String toJSONString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"itemName\":");
		builder.append(JSONUtils.toJSON(itemName));
		builder.append(", \"itemDescription\":");
		builder.append(JSONUtils.toJSON(itemDescription));
		builder.append(", \"quantity\":");
		builder.append(JSONUtils.toJSON(quantity));
		builder.append(", \"categoryIds\":");
		builder.append(JSONUtils.toJSON(categoryIds));
		builder.append(", \"id\":");
		builder.append(JSONUtils.toJSON(id));
		builder.append("}");
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [itemName=");
		builder.append(itemName);
		builder.append(", itemDescription=");
		builder.append(itemDescription);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", categoryIds=");
		builder.append(categoryIds);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}
