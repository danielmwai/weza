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
package com.tunaweza.core.business.service.course;

import com.tunaweza.core.business.dao.course.CourseDao;
import com.tunaweza.core.business.dao.exceptions.course.CourseExistsException;
import com.tunaweza.core.business.dao.exceptions.course.CourseNotFoundException;
import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.course.EmbeddableModule;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.user.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseDao courseDao;

    
    @Override
    public Course addCourse(Course course)
            throws CourseExistsException {
        courseDao.saveCourse(course);
        try {
            return courseDao.findCourse(course);
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
        }
        return course;

    }

    @Override
    public void editCourse(Course course) {
        courseDao.updateCourse(course);
    }

        public Course findCourseeById(Long id)
            throws CourseNotFoundException {
        return courseDao.findById(id);
    }

    @Override
    public Course findCourseByName(String name)
            throws CourseNotFoundException {
        return courseDao.findCourseByName(name);
    }

    @Override
    public List<Course> listAllCourse() {
        return courseDao.findAll();
    }

    @Override
    public List<User> listUsersByCourse(Course course) {
        return courseDao.getAllUsersByCourse(course
                .getId());
    }

    @Override
    public void saveModulesToCourse(Course course,
            List<EmbeddableModule> module) {
        courseDao.saveModulesToCourse(course, module);

    }

    @Override
    public List<Module> getModulesInCourse(Course course) {
        return courseDao.getModulesInCourse(course
                .getId());
    }

    @Override
    public List<Module> getActiveModulesInCourse(
            Course course) {
        return courseDao
                .getActiveModulesInCourse(course.getId());
    }

    ////////////////
    @Override
    public List<Module> getModulesInCourseNoSession(Course course, String companyDbName) {
        return courseDao.getModulesInCourseNoSession(course
                .getId(), companyDbName);
    }

    @Override
    public Course findCourseById(Long id) throws CourseNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

