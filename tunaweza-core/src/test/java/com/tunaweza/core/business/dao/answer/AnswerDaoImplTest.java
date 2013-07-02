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
package com.tunaweza.core.business.dao.answer;

import com.tunaweza.core.business.model.answer.Answer;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel Mwai <naistech.gmail.com>
 */
public class AnswerDaoImplTest {
    
    public AnswerDaoImplTest() {
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
     * Test of findAnswerById method, of class AnswerDaoImpl.
     */
    @Test
    public void testFindAnswerById() throws Exception {
        System.out.println("findAnswerById");
        long uid = 0L;
        AnswerDaoImpl instance = new AnswerDaoImpl();
        Answer expResult = null;
        Answer result = instance.findAnswerById(uid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAnswerByText method, of class AnswerDaoImpl.
     */
    @Test
    public void testFindAnswerByText() throws Exception {
        System.out.println("findAnswerByText");
        String text = "";
        AnswerDaoImpl instance = new AnswerDaoImpl();
        Answer expResult = null;
        Answer result = instance.findAnswerByText(text);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAnswer method, of class AnswerDaoImpl.
     */
    @Test
    public void testFindAnswer() throws Exception {
        System.out.println("findAnswer");
        Answer answer = null;
        AnswerDaoImpl instance = new AnswerDaoImpl();
        Answer expResult = null;
        Answer result = instance.findAnswer(answer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAnswer method, of class AnswerDaoImpl.
     */
    @Test
    public void testAddAnswer() throws Exception {
        System.out.println("addAnswer");
        Answer answer = null;
        AnswerDaoImpl instance = new AnswerDaoImpl();
        Answer expResult = null;
        Answer result = instance.addAnswer(answer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllAnswers method, of class AnswerDaoImpl.
     */
    @Test
    public void testGetAllAnswers() {
        System.out.println("getAllAnswers");
        AnswerDaoImpl instance = new AnswerDaoImpl();
        List<Answer> expResult = null;
        List<Answer> result = instance.getAllAnswers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllAnswersByQuestion method, of class AnswerDaoImpl.
     */
    @Test
    public void testGetAllAnswersByQuestion() {
        System.out.println("getAllAnswersByQuestion");
        long questionId = 0L;
        AnswerDaoImpl instance = new AnswerDaoImpl();
        List<Answer> expResult = null;
        List<Answer> result = instance.getAllAnswersByQuestion(questionId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateAnswer method, of class AnswerDaoImpl.
     */
    @Test
    public void testUpdateAnswer() {
        System.out.println("updateAnswer");
        Answer answer = null;
        AnswerDaoImpl instance = new AnswerDaoImpl();
        instance.updateAnswer(answer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
