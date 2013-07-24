/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.promocodes;

import com.tunaweza.core.business.dao.exceptions.promocode.PromocodeDoesNotExistException;
import com.tunaweza.core.business.dao.promotioncodes.PromocodeDao;
import com.tunaweza.core.business.model.promocodes.Promocode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */

@Service("promocodeService")
@Transactional
public class PromocodeServiceImpl implements PromocodeService{
	
@Autowired
PromocodeDao promocodeDao;

	@Override
	public Promocode addPromocode(Promocode promocode) {
		
		return promocodeDao.savePromocode(promocode);
	}

	@Override
	public Promocode findPromocodeById(long id) throws PromocodeDoesNotExistException {
		return promocodeDao.findPromocodeById(id);
	}

	@Override
	public List<Promocode> listAllPromocodes() {
		return promocodeDao.getallPromocodes();
	}

}
