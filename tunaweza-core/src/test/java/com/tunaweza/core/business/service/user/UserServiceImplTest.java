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

import com.tunaweza.core.business.model.course.EmbeddableCourse;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.user.User;
import java.util.Date;
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
public class UserServiceImplTest {
    
    public UserServiceImplTest() {
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
     * Test of addUser method, of class UserServiceImpl.
     */
    @Test
    public void testAddUser_8args() throws Exception {
        System.out.println("addUser");
        long id = 0L;
        String firstName = "";
        String lastName = "";
        String email = "";
        String password = "";
        String modStartDate = "";
        String locationId = "";
        String currentModId = "";
        UserServiceImpl instance = new UserServiceImpl();
        instance.addUser(id, firstName, lastName, email, password, modStartDate, locationId, currentModId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addUser method, of class UserServiceImpl.
     */
    @Test
    public void testAddUser_User() throws Exception {
        System.out.println("addUser");
        User user = null;
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.addUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveUser method, of class UserServiceImpl.
     */
    @Test
    public void testSaveUser() {
        System.out.println("saveUser");
        User user = null;
        UserServiceImpl instance = new UserServiceImpl();
        instance.saveUser(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateUser method, of class UserServiceImpl.
     */
    @Test
    public void testUpdateUser_8args() throws Exception {
        System.out.println("updateUser");
        long id = 0L;
        String firstName = "";
        String lastName = "";
        String email = "";
        String password = "";
        String modStartDate = "";
        String locationId = "";
        String currentModId = "";
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.updateUser(id, firstName, lastName, email, password, modStartDate, locationId, currentModId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateUser method, of class UserServiceImpl.
     */
    @Test
    public void testUpdateUser_User() throws Exception {
        System.out.println("updateUser");
        User user = null;
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.updateUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addUserToGroup method, of class UserServiceImpl.
     */
    @Test
    public void testAddUserToGroup() throws Exception {
        System.out.println("addUserToGroup");
        Group group = null;
        User user = null;
        UserServiceImpl instance = new UserServiceImpl();
        instance.addUserToGroup(group, user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enableUser method, of class UserServiceImpl.
     */
    @Test
    public void testEnableUser() throws Exception {
        System.out.println("enableUser");
        String userId = "";
        UserServiceImpl instance = new UserServiceImpl();
        boolean expResult = false;
        boolean result = instance.enableUser(userId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of disableUser method, of class UserServiceImpl.
     */
    @Test
    public void testDisableUser() throws Exception {
        System.out.println("disableUser");
        String userId = "";
        UserServiceImpl instance = new UserServiceImpl();
        boolean expResult = false;
        boolean result = instance.disableUser(userId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserByUsername method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserByUsername() throws Exception {
        System.out.println("getUserByUsername");
        String username = "";
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.getUserByUsername(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserByEmail method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserByEmail() throws Exception {
        System.out.println("getUserByEmail");
        String email = "";
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.getUserByEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserById method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserById() throws Exception {
        System.out.println("getUserById");
        long userId = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.getUserById(userId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserByRegNo method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserByRegNo() throws Exception {
        System.out.println("getUserByRegNo");
        String regNo = "";
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.getUserByRegNo(regNo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserByPassword method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserByPassword() {
        System.out.println("getUserByPassword");
        String password = "";
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUserByPassword(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByFirstName method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByFirstName() {
        System.out.println("getUsersByFirstName");
        String firstName = "";
        long companyId = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUsersByFirstName(firstName, companyId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByFirstNameAndRole method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByFirstNameAndRole() {
        System.out.println("getUsersByFirstNameAndRole");
        String firstName = "";
        String role = "";
        long companyId = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUsersByFirstNameAndRole(firstName, role, companyId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByLastName method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByLastName() {
        System.out.println("getUsersByLastName");
        String lastName = "";
        long companyId = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUsersByLastName(lastName, companyId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByLastNameAndRole method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByLastNameAndRole() {
        System.out.println("getUsersByLastNameAndRole");
        String lastName = "";
        String role = "";
        long companyId = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUsersByLastNameAndRole(lastName, role, companyId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByName method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByName() {
        System.out.println("getUsersByName");
        String firstName = "";
        String lastName = "";
        long companyId = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUsersByName(firstName, lastName, companyId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByLocation method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByLocation() throws Exception {
        System.out.println("getUsersByLocation");
        long locationId = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUsersByLocation(locationId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByRole method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByRole() throws Exception {
        System.out.println("getUsersByRole");
        String role = "";
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUsersByRole(role);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByRoleCompanyId method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByRoleCompanyId() throws Exception {
        System.out.println("getUsersByRoleCompanyId");
        String role = "";
        long companyId = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUsersByRoleCompanyId(role, companyId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByModule method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByModule() {
        System.out.println("getUsersByModule");
        Long moduleId = null;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUsersByModule(moduleId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllUsers method, of class UserServiceImpl.
     */
    @Test
    public void testGetAllUsers_0args() {
        System.out.println("getAllUsers");
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getAllUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllUsers method, of class UserServiceImpl.
     */
    @Test
    public void testGetAllUsers_String() {
        System.out.println("getAllUsers");
        String role = "";
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getAllUsers(role);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByRole_CompanyId method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByRole_CompanyId() {
        System.out.println("getUsersByRole_CompanyId");
        String role = "";
        long company_id = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUsersByRole_CompanyId(role, company_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countUsers method, of class UserServiceImpl.
     */
    @Test
    public void testCountUsers_0args() {
        System.out.println("countUsers");
        UserServiceImpl instance = new UserServiceImpl();
        int expResult = 0;
        int result = instance.countUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countUsers method, of class UserServiceImpl.
     */
    @Test
    public void testCountUsers_String() {
        System.out.println("countUsers");
        String role = "";
        UserServiceImpl instance = new UserServiceImpl();
        int expResult = 0;
        int result = instance.countUsers(role);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalUsers method, of class UserServiceImpl.
     */
    @Test
    public void testGetTotalUsers() {
        System.out.println("getTotalUsers");
        UserServiceImpl instance = new UserServiceImpl();
        int expResult = 0;
        int result = instance.getTotalUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllStudents method, of class UserServiceImpl.
     */
    @Test
    public void testGetAllStudents() {
        System.out.println("getAllStudents");
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getAllStudents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserExcerciseByUsername method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserExcerciseByUsername() {
        System.out.println("getUserExcerciseByUsername");
        String username = "";
        UserServiceImpl instance = new UserServiceImpl();
        List<StudentExercise> expResult = null;
        List<StudentExercise> result = instance.getUserExcerciseByUsername(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserExcerciseByEmail method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserExcerciseByEmail() {
        System.out.println("getUserExcerciseByEmail");
        String email = "";
        UserServiceImpl instance = new UserServiceImpl();
        List<StudentExercise> expResult = null;
        List<StudentExercise> result = instance.getUserExcerciseByEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserExerciseByRegNo method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserExerciseByRegNo() {
        System.out.println("getUserExerciseByRegNo");
        String regNo = "";
        UserServiceImpl instance = new UserServiceImpl();
        List<StudentExercise> expResult = null;
        List<StudentExercise> result = instance.getUserExerciseByRegNo(regNo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnabledUsers method, of class UserServiceImpl.
     */
    @Test
    public void testGetEnabledUsers() {
        System.out.println("getEnabledUsers");
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getEnabledUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDisabledUsers method, of class UserServiceImpl.
     */
    @Test
    public void testGetDisabledUsers() {
        System.out.println("getDisabledUsers");
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getDisabledUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByLastLogin method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByLastLogin() {
        System.out.println("getUsersByLastLogin");
        Date lastLoginDate = null;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUsersByLastLogin(lastLoginDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByCreationDate method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByCreationDate() {
        System.out.println("getUsersByCreationDate");
        Date startDate = null;
        Date endDate = null;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getUsersByCreationDate(startDate, endDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isSuperuser method, of class UserServiceImpl.
     */
    @Test
    public void testIsSuperuser() {
        System.out.println("isSuperuser");
        String username = "";
        UserServiceImpl instance = new UserServiceImpl();
        boolean expResult = false;
        boolean result = instance.isSuperuser(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isStudent method, of class UserServiceImpl.
     */
    @Test
    public void testIsStudent_String() throws Exception {
        System.out.println("isStudent");
        String username = "";
        UserServiceImpl instance = new UserServiceImpl();
        boolean expResult = false;
        boolean result = instance.isStudent(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isStudent method, of class UserServiceImpl.
     */
    @Test
    public void testIsStudent_User() throws Exception {
        System.out.println("isStudent");
        User user = null;
        UserServiceImpl instance = new UserServiceImpl();
        boolean expResult = false;
        boolean result = instance.isStudent(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEnabled method, of class UserServiceImpl.
     */
    @Test
    public void testIsEnabled() throws Exception {
        System.out.println("isEnabled");
        String username = "";
        UserServiceImpl instance = new UserServiceImpl();
        boolean expResult = false;
        boolean result = instance.isEnabled(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentModuleId method, of class UserServiceImpl.
     */
    @Test
    public void testGetCurrentModuleId() {
        System.out.println("getCurrentModuleId");
        String username = "";
        UserServiceImpl instance = new UserServiceImpl();
        Long expResult = null;
        Long result = instance.getCurrentModuleId(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getModuleStartDate method, of class UserServiceImpl.
     */
    @Test
    public void testGetModuleStartDate() {
        System.out.println("getModuleStartDate");
        String username = "";
        UserServiceImpl instance = new UserServiceImpl();
        Date expResult = null;
        Date result = instance.getModuleStartDate(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchUsers method, of class UserServiceImpl.
     */
    @Test
    public void testSearchUsers_3args() {
        System.out.println("searchUsers");
        String firstName = "";
        String lastName = "";
        long companyId = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.searchUsers(firstName, lastName, companyId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchUsers method, of class UserServiceImpl.
     */
    @Test
    public void testSearchUsers_4args() {
        System.out.println("searchUsers");
        String firstName = "";
        String lastName = "";
        String role = "";
        long companyId = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.searchUsers(firstName, lastName, role, companyId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUserModuleAndStartDate method, of class UserServiceImpl.
     */
    @Test
    public void testSetUserModuleAndStartDate() {
        System.out.println("setUserModuleAndStartDate");
        long userId = 0L;
        String currentModule = "";
        Date moduleStartDate = null;
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.setUserModuleAndStartDate(userId, currentModule, moduleStartDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPaginatedUsers method, of class UserServiceImpl.
     */
    @Test
    public void testGetPaginatedUsers_int_int() {
        System.out.println("getPaginatedUsers");
        int startIndex = 0;
        int pageSize = 0;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getPaginatedUsers(startIndex, pageSize);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPaginatedUsers method, of class UserServiceImpl.
     */
    @Test
    public void testGetPaginatedUsers_3args() {
        System.out.println("getPaginatedUsers");
        int startIndex = 0;
        int pageSize = 0;
        String role = "";
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getPaginatedUsers(startIndex, pageSize, role);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPaginatedUsersByRole_CompanyId method, of class UserServiceImpl.
     */
    @Test
    public void testGetPaginatedUsersByRole_CompanyId() {
        System.out.println("getPaginatedUsersByRole_CompanyId");
        int startIndex = 0;
        int pageSize = 0;
        String role = "";
        long company_id = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getPaginatedUsersByRole_CompanyId(startIndex, pageSize, role, company_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPaginatedUsersByRole method, of class UserServiceImpl.
     */
    @Test
    public void testGetPaginatedUsersByRole() {
        System.out.println("getPaginatedUsersByRole");
        int startIndex = 0;
        int pageSize = 0;
        String role = "";
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
        List<User> result = instance.getPaginatedUsersByRole(startIndex, pageSize, role);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mailStudentsNotLoggedLastFiveDays method, of class UserServiceImpl.
     */
    @Test
    public void testMailStudentsNotLoggedLastFiveDays() {
        System.out.println("mailStudentsNotLoggedLastFiveDays");
        UserServiceImpl instance = new UserServiceImpl();
        instance.mailStudentsNotLoggedLastFiveDays();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByCompanyId method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByCompanyId() throws Exception {
        System.out.println("getUsersByCompanyId");
        long companyId = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
//        List<User> result = instance.getUsersByCompanyId(companyId);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsersByCompanyName method, of class UserServiceImpl.
     */
    @Test
    public void testGetUsersByCompanyName() throws Exception {
        System.out.println("getUsersByCompanyName");
        String companyName = "";
        UserServiceImpl instance = new UserServiceImpl();
        List<User> expResult = null;
//        List<User> result = instance.getUsersByCompanyName(companyName);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEnabledAndHasCourseTemplate method, of class UserServiceImpl.
     */
    @Test
    public void testIsEnabledAndHasCourseTemplate() {
        System.out.println("isEnabledAndHasCourseTemplate");
        User user = null;
        UserServiceImpl instance = new UserServiceImpl();
        boolean expResult = false;
        boolean result = instance.isEnabledAndHasCourse(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logInUser method, of class UserServiceImpl.
     */
    @Test
    public void testLogInUser() throws Exception {
        System.out.println("logInUser");
        String username = "";
        String password = "";
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.logInUser(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAutoPass method, of class UserServiceImpl.
     */
    @Test
    public void testGetAutoPass() throws Exception {
        System.out.println("getAutoPass");
        User user = null;
        String newPassword = "";
        UserServiceImpl instance = new UserServiceImpl();
        instance.getAutoPass(user, newPassword);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMail method, of class UserServiceImpl.
     */
    @Test
    public void testSendMail() throws Exception {
        System.out.println("sendMail");
        User user = null;
        String password = "";
        UserServiceImpl instance = new UserServiceImpl();
        instance.sendMail(user, password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserAuthority method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserAuthority() {
        System.out.println("getUserAuthority");
        String username = "";
        UserServiceImpl instance = new UserServiceImpl();
        String expResult = "";
        String result = instance.getUserAuthority(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generatePassword method, of class UserServiceImpl.
     */
    @Test
    public void testGeneratePassword() {
        System.out.println("generatePassword");
        UserServiceImpl instance = new UserServiceImpl();
        String expResult = "";
        String result = instance.generatePassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enableDisableUser method, of class UserServiceImpl.
     */
    @Test
    public void testEnableDisableUser() throws Exception {
        System.out.println("enableDisableUser");
        String userId = "";
        UserServiceImpl instance = new UserServiceImpl();
        instance.enableDisableUser(userId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enableDisableCloudUser method, of class UserServiceImpl.
     */
    @Test
    public void testEnableDisableCloudUser() throws Exception {
        System.out.println("enableDisableCloudUser");
        String userId = "";
        UserServiceImpl instance = new UserServiceImpl();
        instance.enableDisableCloudUser(userId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enableDisable method, of class UserServiceImpl.
     */
    @Test
    public void testEnableDisable() throws Exception {
        System.out.println("enableDisable");
        String userId = "";
        UserServiceImpl instance = new UserServiceImpl();
        instance.enableDisable(userId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserCourseTemplate method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserCourse() {
        System.out.println("getUserCourseTemplate");
        long userId = 0L;
        UserServiceImpl instance = new UserServiceImpl();
        List<EmbeddableCourse> expResult = null;
        List<EmbeddableCourse> result = instance.getUserCourse(userId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUserCourseTemplate method, of class UserServiceImpl.
     */
    @Test
    public void testSetUserCourse() {
        System.out.println("setUserCourseTemplate");
        long userId = 0L;
        List<EmbeddableCourse> courseList = null;
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.setUserCourse(userId, courseList);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNextRegistrationNumber method, of class UserServiceImpl.
     */
    @Test
    public void testGetNextRegistrationNumber() {
        System.out.println("getNextRegistrationNumber");
        UserServiceImpl instance = new UserServiceImpl();
        int expResult = 0;
        int result = instance.getNextRegistrationNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deletePreviousUsername method, of class UserServiceImpl.
     */
    @Test
    public void testDeletePreviousUsername() {
        System.out.println("deletePreviousUsername");
        String oldUsername = "";
        String role = "";
        UserServiceImpl instance = new UserServiceImpl();
        instance.deletePreviousUsername(oldUsername, role);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSuperUser method, of class UserServiceImpl.
     */
    @Test
    public void testGetSuperUser() {
        System.out.println("getSuperUser");
        String username = "";
        String firstName = "";
        String lastName = "";
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.getSuperUser(username, firstName, lastName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
