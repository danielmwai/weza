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

package com.tunaweza.core.business.model.status;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public enum Status {

  STATUS_EXERCISE_NOT_READ_YET,
  STATUS_EXERCISE_READ_BUT_NOT_YET_SUBMITTED, 
  STATUS_EXERCISE_SUBMITTED_BUT_AWAITING_FEEDBACK,
  STATUS_EXERCISE_PASSED,
  STATUS_EXERCISE_MARKED_AND_PASSED_AND_FEEDBACK_READ_AND_CLOSED,
  STATUS_EXERCISE_MARKED_AND_REQUIRES_MORE_WORK,
  STATUS_EXERCISE_MARKED_AND_REQUIRES_MORE_WORK_AND_FEEDBACK_READ_BUT_NOT_YET_RESUBMITTED, 
  STATUS_MODULE_CLOSED,
  STATUS_MODULE_AWAITING_STUDENT,
  STATUS_TOPIC_COMPLETED,
  STATUS_TAKE_QUIZ,
  STATUS_MODULE_AWAITING_FEEDBACK, 
  STATUS_MODULE_FEEDBACK_NOT_YET_READ,
  STATUS_MODULE_REQUIRES_MORE_WORK,
  STATUS_MENTOR_HAS_NOT_MARKED,
  STATUS_MENTOR_HAS_MARKED,
  MODULE_NOT_STARTED,
  COURSE_AWAITING_EVALUATION,
  MODULE_STARTED_AWAITING_EVALUATION,
  MODULE_COMPLETED_AWAITING_EVALUATION,
  MODULE_STARTED_EVALUATION_PASSED,
  MODULE_COMPLETED_EVALUATION_PASSED, MODULE_EVALUATION_DISABLED;
  
}