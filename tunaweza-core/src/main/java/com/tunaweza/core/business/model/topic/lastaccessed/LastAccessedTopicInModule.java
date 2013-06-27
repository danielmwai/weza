/*
 * The MIT License
 *
 * Copyright 2013 Daniel Mwai <naistech.gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tunaweza.core.business.model.topic.lastaccessed;
import com.tunaweza.core.business.model.persistence.PersistentEntity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Entity
@Table(name = LastAccessedTopicInModule.TABLE_NAME)
public class LastAccessedTopicInModule implements PersistentEntity
{
	public static final String TABLE_NAME = "last_accessed_topic_in_module";

	@EmbeddedId
	private LastAccessedTopicPK lastAccessedTopicPK;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name = "last_accessed_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastAccessedDate;

	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;
	
	@Version
	@Column(name = "VERSION")
	private int version = 0;
	
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the primary key identifier.
	 * 
	 * @param id
	 *            Primary key identifier.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Returns the version number (used by Hibernate to manage optimistic
	 * locking).
	 * 
	 * @return version Version number.
	 */
	public int getVersion() {
		return this.version;
	}

	/**
	 * <b>IMPORTANT NOTE:</b> The version property must <em>never</em> be
	 * altered by the application in any way. To artificially increase the
	 * version number see the LockMode.WRITE in the Hibernate reference manual.
	 * 
	 * @param version
	 *            Version number.
	 */
	public void setVersion(final int version) {
		this.version = version;
	}

	/**
	 * @return the topic
	 */
	public Topic getTopic()
	{
		return topic;
	}

	/**
	 * @param topic
	 *            the topic to set
	 */
	public void setTopic(Topic topic)
	{
		this.topic = topic;
	}

	/**
	 * @return the lastAccessedTopicPK
	 */
	public LastAccessedTopicPK getLastAccessedTopicPK()
	{
		return lastAccessedTopicPK;
	}

	/**
	 * @param lastAccessedTopicPK
	 *            the lastAccessedTopicPK to set
	 */
	public void setLastAccessedTopicPK(LastAccessedTopicPK lastAccessedTopicPK)
	{
		this.lastAccessedTopicPK = lastAccessedTopicPK;
	}

	/**
	 * @return the lastAccessedDate
	 */
	public Date getLastAccessedDate()
	{
		return lastAccessedDate;
	}

	/**
	 * @param lastAccessedDate
	 *            the lastAccessedDate to set
	 */
	public void setLastAccessedDate(Date lastAccessedDate)
	{
		this.lastAccessedDate = lastAccessedDate;
	}

}
