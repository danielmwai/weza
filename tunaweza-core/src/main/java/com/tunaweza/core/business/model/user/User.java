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
package com.tunaweza.core.business.model.user;

import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.student.Student;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Entity
@Table(name = User.TABLE_NAME)
public class User implements Comparable<User>, UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "users";
    public static final String JOIN_ROLE = "username";

    @Column(name = "is_enabled")
    private int is_enabled = 1;

    public int getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(int is_enabled) {
        this.is_enabled = is_enabled;
    }
    @Column
	private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "username", nullable = false, unique = true, columnDefinition = "varchar(255) default 'User 1'")
    private String username;

    @Column(name = "email", nullable = true, unique = true)
    private String email;

    @Column(name = "password", nullable = false, unique = false)
    private String password;
     @Column
	private String salt;
    @Column(name = "superuser", unique = false, columnDefinition = "boolean default false")
    private boolean superuser = false;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column
    private boolean accountNonExpired = true;

    @Column
    private boolean credentialsNonExpired = true;

    @Column
    private boolean accountNonLocked = true;
    @ManyToOne
    @JoinColumn(name = "id_location")
    private Location location;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP")
    private Calendar creationDate;

    /*
     * @ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
     * CascadeType.MERGE }, fetch = FetchType.LAZY)
     * 
     * @JoinTable(name = "authorities", uniqueConstraints = {
     * 
     * @UniqueConstraint(columnNames = { User.JOIN_ROLE, Role.JOIN_USER }) })
     */
    @ManyToMany
    @JoinTable(name = "authorities",
            joinColumns = {
                @JoinColumn(name = "username", referencedColumnName = "username")}, inverseJoinColumns = {
                @JoinColumn(name = "authority", referencedColumnName = "role_name")})
    private List<Role> roles;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Mentor mentor;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Student student;
//	
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "id_company")
//	private Company userCompany;

    @ManyToMany
    @Embedded
    @JoinTable(name = "user_groups",
            joinColumns = {
                @JoinColumn(name = "userid", referencedColumnName = "id")}, inverseJoinColumns = {
                @JoinColumn(name = "user_group", referencedColumnName = "id")})
    List<Group> groups;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * *************************************************************************
     * getters & setters
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isSuperuser() {
        return superuser;
    }

    public void setSuperuser(boolean superuser) {
        this.superuser = superuser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the mentor
     */
    public Mentor getMentor() {
        return mentor;
    }

    /**
     * @param mentor the mentor to set
     */
    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(User user) {
        if (user.getId() > getId()) {
            return -1;

        } else if (user.getId() < getId()) {
            return 1;
        }
        return 0;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
//
//	/**
//	 * @return the userCompany
//	 */
//	public Company getUserCompany() {
//		return userCompany;
//	}
//
//	/**
//	 * @param userCompany the userCompany to set
//	 */
//	public void setUserCompany(Company userCompany) {
//		this.userCompany = userCompany;
//	}

    /**
     * @return the groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

   

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>(
                groups.size());
        for (Group group : groups) {
            auths.add((GrantedAuthority) group);
        }
        return auths;
    }
}
