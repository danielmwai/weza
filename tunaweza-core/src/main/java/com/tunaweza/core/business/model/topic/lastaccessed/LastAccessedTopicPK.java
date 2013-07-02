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

import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.user.User;
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Embeddable
public class LastAccessedTopicPK implements Serializable
{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "module_id")
	private Module module;
	
	public LastAccessedTopicPK()
	{
	}
	
	public LastAccessedTopicPK(User user, Module module)
	{
		this.user = user;
		this.module = module;
	}

	/**
	 * @return the user
	 */
	public User getUser()
	{
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user)
	{
		this.user = user;
	}

	/**
	 * @return the module
	 */
	public Module getModule()
	{
		return module;
	}

	/**
	 * @param module
	 *            the module to set
	 */
	public void setModule(Module module)
	{
		this.module = module;
	}

	public int hashCode()
	{
		return (int) (module.getName().hashCode() + user.getId());
	}

	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		
		if (!(obj instanceof LastAccessedTopicPK))
			return false;
		
		LastAccessedTopicPK pk = (LastAccessedTopicPK) obj;

		return pk.getModule().getId() == getModule().getId()
				&& pk.getUser().getId() == getUser().getId();
	}

}
