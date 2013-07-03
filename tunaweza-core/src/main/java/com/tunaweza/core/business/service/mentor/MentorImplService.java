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

package com.tunaweza.core.business.service.mentor;

import com.tunaweza.core.business.dao.exceptions.mentor.MentorExistsException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorNotFoundException;
import com.tunaweza.core.business.dao.mentor.MentorDao;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.exercise.ExerciseTransaction;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */


@Service("mentorService")
@Transactional
public class MentorImplService implements MentorService{

	@Autowired
	MentorDao mentorDao;

	@Override
	public Mentor addMentor(Mentor mentor)
			throws MentorExistsException {
		return mentorDao.saveMentor(mentor);

	}
	
	@Override
	public Mentor findMentorById(long id)
			throws MentorNotFoundException {
		return mentorDao.findById(id);
	}

	@Override
	public Mentor findMentorByName(String name)
			throws MentorNotFoundException {
		return mentorDao.findMentorByName(name);
	}

	@Override
	public List<Mentor> listAllMentor() {
		return mentorDao.findAll();
	}

	@Override
	public List<Mentor> listMentorsByMentor(long mentorId) {
		return mentorDao.getAllMentorsByMentor(mentorId);
	}

	@Override
	public void saveExercisesToMentor(Mentor mentor,
			List<Topic> exercises) {
		mentorDao.saveExercisesToMentor(mentor, exercises);

	}

	@Override
	public List<Topic> getExercisesInMentor(long mentorId) {
		return mentorDao.getExercisesInMentor(mentorId);
	}

	@Override
	public List<Topic> getActiveExercisesInMentor(
			long mentorId) {
		return mentorDao.
				getActiveExercisesInMentor(mentorId);
	}

	@Override
	public void editMentor(Mentor mentor) throws MentorExistsException {
		mentorDao.updateMentor(mentor);
		
	}

	@Override
	public int countExerciseTransactions(long moduleId, long mentorId) {
		return mentorDao.countExerciseTransactions(moduleId, mentorId);
	}

	@Override
	public List<ExerciseTransaction> getExerciseTransactions(
			long moduleId, long mentorId) {
		return mentorDao.getExerciseTransactions(moduleId, mentorId);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.mentor.MentorTemplateService#getPaginatedExerciseTransactions
	 * (int, int, long, long)
	 */
	@Override
	public List<ExerciseTransaction> getPaginatedExerciseTransactions(int startIndex, int pageSize, long moduleId, long mentorId) {
		return mentorDao.getPaginatedExerciseTransactionsList(startIndex, pageSize, moduleId, mentorId);
	}
	
	@Override
	public int numberOfExercisesByMentor(long mentorId, long moduleId) {
		// TODO Auto-generated method stub
		return mentorDao.numberOfExercisesByMentor(mentorId, moduleId);
	}



    @Override
    public Mentor getMentorById(Long mentorId) throws MentorNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mentor saveMentor(Mentor mentor) throws MentorExistsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mentor getMentorByUser(User user) throws MentorNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Mentor> getMentorsByMentor(long mentorId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveMentorsToMentor(Mentor mentor, List<Mentor> Mentors) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> getTransactionLastThreeMonths(String mentorId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getNameLastMonths() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAvergeTimeToRead(String mentorId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAvergeTimeToResponse(String mentorId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAvergeofList(List<Long> listofDiff) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Mentor> listAllMentors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

