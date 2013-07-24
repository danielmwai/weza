/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.promocodes;

import com.tunaweza.core.business.dao.exceptions.promocode.PromocodeDoesNotExistException;
import com.tunaweza.core.business.model.promocodes.Promocode;
import java.util.List;

/**
 *
 * @author naistech
 */
public interface PromocodeService {
    
/**
	 * 
	 * @param promocode
	 * @return Promocode
	 */
	
	public Promocode addPromocode(Promocode promocode);
	
	/**
	 * 
	 * @param id
	 * @return Promocode
	 * @throws PromocodeDoesNotExistException
	 */
	public Promocode findPromocodeById(long id) throws PromocodeDoesNotExistException;
	
	/**
	 * 
	 * @return List<License>
	 */
	public List<Promocode> listAllPromocodes();
}