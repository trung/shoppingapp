/**
 *
 */
package org.telokers.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.telokers.model.response.JSONFriendly;
import org.telokers.service.utils.JSONUtils;

/**
 * @author trung
 *
 */
@PersistenceCapable
public class Category extends AbstractModel implements JSONFriendly {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Persistent
	private String categoryName;

	@Persistent
	private String categoryDescription;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	/* (non-Javadoc)
	 * @see org.telokers.model.response.JSONFriendly#toJSONString()
	 */
	@Override
	public String toJSONString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"categoryName\":");
		builder.append(JSONUtils.toJSON(categoryName));
		builder.append(", \"categoryDescription\":");
		builder.append(JSONUtils.toJSON(categoryDescription));
		builder.append(", \"id\":");
		builder.append(JSONUtils.toJSON(id));
		builder.append("}");
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Category [categoryName=");
		builder.append(categoryName);
		builder.append(", categoryDescription=");
		builder.append(categoryDescription);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

}
