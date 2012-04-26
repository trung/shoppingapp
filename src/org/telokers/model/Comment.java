/**
 *
 */
package org.telokers.model;

import java.util.Date;

import org.telokers.service.utils.MiscUtils;

import com.google.appengine.api.datastore.Entity;

/**
 * @author trung
 *
 */
public class Comment extends AbstractModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1641362513456678319L;

	public static enum CommentProperty {
		commentId,
		userId,
		rating,
		comment,
		createdDate,
		productId
	}

	public Comment(String id) {
		super(id);
		entity = new Entity(getKind(), id);
		setCommentId(id);
		setCreatedDate(new Date());
	}

	public void setCreatedDate(Date d) {
		setProperty(CommentProperty.createdDate, d);
	}

	public String getCreatedDateString() {
		return MiscUtils.formatDateTime((Date) getProperty(CommentProperty.createdDate));
	}

	public void setCommentId(String id) {
		setProperty(CommentProperty.commentId, id);
	}

	public String getComment() {
		return (String) getProperty(CommentProperty.comment);
	}

	public String getUserId() {
		return (String) getProperty(CommentProperty.userId);
	}

	public int getRating() {
		Integer r = (Integer) getProperty(CommentProperty.rating);
		return  r == null ? -1 : r.intValue();
	}

	/**
	 * @param entity
	 * @throws Exception
	 */
	public Comment(Entity entity) throws Exception {
		super(entity);
	}

	/* (non-Javadoc)
	 * @see org.telokers.model.AbstractModel#getEntity()
	 */
	@Override
	public Entity getEntity() {
		return entity;
	}

	/**
	 * @return
	 */
	public static String getKind() {
		return Comment.class.getSimpleName();
	}

}
