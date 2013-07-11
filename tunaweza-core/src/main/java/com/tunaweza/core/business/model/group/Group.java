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

package com.tunaweza.core.business.model.group;
import com.tunaweza.core.business.model.course.EmbeddableCourse;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Entity
@Table(name = Group.TABLE_NAME)
public class Group implements
Comparable<Group>{
     @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "company_group";

	@Column(name = "description")
	private String description;

	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
/*	@ManyToMany(mappedBy = "groups",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	List<User> users;*/
	
	@ElementCollection
	@CollectionTable(name="user_group", joinColumns=@JoinColumn(name = "company_group_id"))
	private List<EmbeddableUser> user;
	
	@ElementCollection
	@CollectionTable(name = "group_courses", joinColumns = @JoinColumn(name = "company_group_id"))
	private List<EmbeddableCourse> courses;
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	
	
	public List<EmbeddableCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<EmbeddableCourse> courses) {
		this.courses = courses;
	}

	public int compareTo(Group group) {
		if (group.getId() > getId())
		{
			return -1;

		}
		else if (group.getId() < getId())
		{
			return 1;
		}
		return 0;
	}

	public List<EmbeddableUser> getUser() {
		return user;
	}

	public void setUser(List<EmbeddableUser> user) {
		this.user = user;
	}
	
}
