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
package com.tunaweza.core.business.model.file;

import com.tunaweza.core.business.model.answer.GoodAnswer;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import java.sql.Blob;
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
public class FileTest {
    
    public FileTest() {
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
     * Test of getFileName method, of class File.
     */
    @Test
    public void testGetFileName() {
        System.out.println("getFileName");
        File instance = new File();
        String expResult = "";
        String result = instance.getFileName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFileName method, of class File.
     */
    @Test
    public void testSetFileName() {
        System.out.println("setFileName");
        String fileName = "";
        File instance = new File();
        instance.setFileName(fileName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContentType method, of class File.
     */
    @Test
    public void testGetContentType() {
        System.out.println("getContentType");
        File instance = new File();
        String expResult = "";
        String result = instance.getContentType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setContentType method, of class File.
     */
//    @Test
//    public void testSetContentType() {
//        System.out.println("setContentType");
//        String contentType = "";
//        File instance = new File();
//        instance.setContentType(contentType);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFile method, of class File.
//     */
//    @Test
//    public void testGetFile() {
//        System.out.println("getFile");
//        File instance = new File();
//        Blob expResult = null;
//        Blob result = instance.getFile();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setFile method, of class File.
//     */
//    @Test
//    public void testSetFile() {
//        System.out.println("setFile");
//        Blob file = null;
//        File instance = new File();
//        instance.setFile(file);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getExercise method, of class File.
//     */
//    @Test
//    public void testGetExercise() {
//        System.out.println("getExercise");
//        File instance = new File();
//        Topic expResult = null;
//        Topic result = instance.getExercise();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setExercise method, of class File.
//     */
//    @Test
//    public void testSetExercise() {
//        System.out.println("setExercise");
//        Topic exercise = null;
//        File instance = new File();
//        instance.setExercise(exercise);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGoodAnswer method, of class File.
//     */
//    @Test
//    public void testGetGoodAnswer() {
//        System.out.println("getGoodAnswer");
//        File instance = new File();
//        GoodAnswer expResult = null;
//        GoodAnswer result = instance.getGoodAnswer();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setGoodAnswer method, of class File.
//     */
//    @Test
//    public void testSetGoodAnswer() {
//        System.out.println("setGoodAnswer");
//        GoodAnswer goodAnswer = null;
//        File instance = new File();
//        instance.setGoodAnswer(goodAnswer);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setExerciseTransaction method, of class File.
//     */
//    @Test
//    public void testSetExerciseTransaction() {
//        System.out.println("setExerciseTransaction");
//        ExerciseTransaction exerciseTransaction = null;
//        File instance = new File();
//        instance.setExerciseTransaction(exerciseTransaction);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getExerciseTransaction method, of class File.
     */
    @Test
    public void testGetExerciseTransaction() {
        System.out.println("getExerciseTransaction");
        File instance = new File();
        ExerciseTransaction expResult = null;
        ExerciseTransaction result = instance.getExerciseTransaction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
