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

package com.tunaweza.core.business.dao.promotioncodes;

import com.tunaweza.core.business.dao.exceptions.promocode.PromocodeDoesNotExistException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.promocodes.Promocode;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Repository("promocodeDao")
@Transactional
public class PromocodeDaoImpl extends GenericDaoImpl<Promocode> implements PromocodeDao{
	
	
	
	@Override
	public Promocode findPromocodeById(long id) throws PromocodeDoesNotExistException{
		
		Promocode promocode = (Promocode) findById(id);
		if(promocode!=null){
		} else {
                throw new PromocodeDoesNotExistException();
            }
		return promocode;
	}
	
	@Override
	public Promocode savePromocode(Promocode promocode) {
		Promocode prcode = null;
		prcode = saveOrUpdate(promocode);
		return prcode;
	}
	
	@Override
	public List<Promocode> getallPromocodes() {
		return findAll();
	}

}

