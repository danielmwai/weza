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

package com.tunaweza.core.business.service.user;


import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.dao.user.UserDao;
import com.tunaweza.core.business.model.user.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Service("userCastService")
@Transactional
public class UserCastServiceImpl implements UserCastService {
	public static final Logger LOGGER = Logger
			.getLogger(UserCastServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.user.UserCastService#getUser()
	 */
	public User getUser() {

		long userId = 0;
		try {
			userId = userService.getUserByUsername(
					SecurityContextHolder.getContext().getAuthentication()
							.getName()).getId();
			LOGGER.info("USER ID >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
					+ userId);
		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		User user = null;
		if (userId == 0) {
			user = userService.getSuperUser("rootAdmin", "Root", "Admin");
			LOGGER.info("USER ID >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
					+ user.getId());
		} else {
			LOGGER.info("USER ID >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
					+ userId);

			user = userDao.findById(userId);
		}
		return user;

	}

}
