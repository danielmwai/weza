/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.model.topic.lastaccessed;

import com.tunaweza.core.business.model.topic.Topic;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author naistech
 */
public class LastAccessedTopicInModuleTest {
    
    public LastAccessedTopicInModuleTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class LastAccessedTopicInModule.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        LastAccessedTopicInModule instance = new LastAccessedTopicInModule();
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class LastAccessedTopicInModule.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = null;
        LastAccessedTopicInModule instance = new LastAccessedTopicInModule();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVersion method, of class LastAccessedTopicInModule.
     */
    @Test
    public void testGetVersion() {
        System.out.println("getVersion");
        LastAccessedTopicInModule instance = new LastAccessedTopicInModule();
        int expResult = 0;
        int result = instance.getVersion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVersion method, of class LastAccessedTopicInModule.
     */
    @Test
    public void testSetVersion() {
        System.out.println("setVersion");
        int version = 0;
        LastAccessedTopicInModule instance = new LastAccessedTopicInModule();
        instance.setVersion(version);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTopic method, of class LastAccessedTopicInModule.
     */
    @Test
    public void testGetTopic() {
        System.out.println("getTopic");
        LastAccessedTopicInModule instance = new LastAccessedTopicInModule();
        Topic expResult = null;
        Topic result = instance.getTopic();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTopic method, of class LastAccessedTopicInModule.
     */
    @Test
    public void testSetTopic() {
        System.out.println("setTopic");
        Topic topic = null;
        LastAccessedTopicInModule instance = new LastAccessedTopicInModule();
        instance.setTopic(topic);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastAccessedTopicPK method, of class LastAccessedTopicInModule.
     */
    @Test
    public void testGetLastAccessedTopicPK() {
        System.out.println("getLastAccessedTopicPK");
        LastAccessedTopicInModule instance = new LastAccessedTopicInModule();
        LastAccessedTopicPK expResult = null;
        LastAccessedTopicPK result = instance.getLastAccessedTopicPK();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLastAccessedTopicPK method, of class LastAccessedTopicInModule.
     */
    @Test
    public void testSetLastAccessedTopicPK() {
        System.out.println("setLastAccessedTopicPK");
        LastAccessedTopicPK lastAccessedTopicPK = null;
        LastAccessedTopicInModule instance = new LastAccessedTopicInModule();
        instance.setLastAccessedTopicPK(lastAccessedTopicPK);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastAccessedDate method, of class LastAccessedTopicInModule.
     */
    @Test
    public void testGetLastAccessedDate() {
        System.out.println("getLastAccessedDate");
        LastAccessedTopicInModule instance = new LastAccessedTopicInModule();
        Date expResult = null;
        Date result = instance.getLastAccessedDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLastAccessedDate method, of class LastAccessedTopicInModule.
     */
    @Test
    public void testSetLastAccessedDate() {
        System.out.println("setLastAccessedDate");
        Date lastAccessedDate = null;
        LastAccessedTopicInModule instance = new LastAccessedTopicInModule();
        instance.setLastAccessedDate(lastAccessedDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
