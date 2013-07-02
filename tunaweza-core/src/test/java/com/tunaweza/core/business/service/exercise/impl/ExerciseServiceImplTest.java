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
package com.tunaweza.core.business.service.exercise.impl;

import com.tunaweza.core.business.Dao.exceptions.module.ModuleDoesNotExistException;
import com.tunaweza.core.business.Dao.exceptions.student.StudentExerciseExistsException;
import com.tunaweza.core.business.Dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.service.exercise.ExerciseBean;
import com.tunaweza.core.business.service.exercise.ExerciseService;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author Daniel Mwai <naistech.gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class ExerciseServiceImplTest {
@Autowired 
	private ExerciseService exerciseService;
    public ExerciseServiceImplTest() {
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
     * Test of getStudentExerciseByModule method, of class ExerciseServiceImpl.
     */
    @Test
    public void testGetStudentExerciseByModule() {
//        

        try {
            List<ExerciseBean> exerciseBeanList = exerciseService.getStudentExerciseByModule("1", "1");
            int expectedResult = 5;
            int actualResult = exerciseBeanList.size();
            assertEquals(expectedResult, actualResult);

        } catch (NumberFormatException e) {
            fail("getStudentExerciseByModule failed: NumberFormatException");
        } catch (StudentExerciseExistsException e) {
            fail("getStudentExerciseByModule failed: StudentExerciseExistsException");
        } catch (UserDoesNotExistException e) {
            fail("getStudentExerciseByModule failed :UserDoesNotExistException");
        } catch (ModuleDoesNotExistException e) {
            fail("getStudentExerciseByModule failed :ModuleDoesNotExistException");
        }
    }
}
