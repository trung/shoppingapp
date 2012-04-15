/**
 *
 */
package org.telokers.model.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.telokers.model.AbstractModel;
import org.telokers.model.Item;

/**
 *
 * A relational index entity for {@link Item}
 *
 * @author trung
 *
 */
@PersistenceCapable
public class ItemIndex extends AbstractModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -1090797161802951233L;

	@Persistent
	private Item item;

	@Persistent
	private List<String> tags = new ArrayList<String>();

	public ItemIndex(Item item) {
		this.item = item;
	}

	public void addTags(Collection<String> tags) {
		this.tags.addAll(tags);
	}

	/**
	 *
	 * @param tag
	 */
	public void addTag(String tag) {
		tags.add(tag);
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

}
