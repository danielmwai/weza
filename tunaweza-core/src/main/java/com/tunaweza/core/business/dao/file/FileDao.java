/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.file;

import java.util.List;

/**
 *
 * @author Daniel Mwai
 */
public interface FileDao 
{
	/**
	 * 
	 * @return list of <code>File</code>
	 */
	public List<File> getFiles();
	
	/**
	 * 
	 * @param id
	 * @return list of <code>File</code>
	 */
    public File getFile(Long id);
    
    /**
     * 
     * @param file
     * @return <code>File</code>
     */
    public File saveFile(File file);
    
    /**
     * 
     * @param id
     */
    public void removeFile(Long id);

}
