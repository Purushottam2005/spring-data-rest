package org.emaginalabs.example.springdata.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;


@Entity(name = "todo")
public class Todo extends AbstractPersistable<Long> {

	public static final int MAX_LENGTH_DESCRIPTION = 500;
	public static final int MAX_LENGTH_TITLE = 100;


	@Column(nullable = false)
	protected String title;


	@Column(nullable = false)
	protected String description;

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@javax.persistence.Column(nullable = true)
	protected Date due;

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@javax.persistence.Column(nullable = true)
	protected Date reminderme;


	@javax.persistence.ManyToOne
	protected Priority priority;

	@Column(name = "creation_time", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime creationTime;

	@Column(name = "modification_time", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime modificationTime;


	public Todo() {

	}

	public void update(String description, String title) {
		this.description = description;
		this.title = title;
	}

	@PrePersist
	public void prePersist() {
		DateTime now = DateTime.now();
		creationTime = now;
		modificationTime = now;
	}

	@PreUpdate
	public void preUpdate() {
		modificationTime = DateTime.now();
	}

	public String getTitle() {
		return this.title;
	}


	public String getDescription() {
		return this.description;
	}


	public Date getDue() {
		return this.due;
	}

	public Date getReminderme() {
		return this.reminderme;
	}


	public Priority getPriority() {
		return this.priority;
	}

	public void setTitle(String myTitle) {
		this.title = myTitle;
	}


	public void setDescription(String myDescription) {
		this.description = myDescription;
	}

	public void setDue(Date myDue) {
		this.due = myDue;
	}

	public void setReminderme(Date myReminderme) {
		this.reminderme = myReminderme;
	}

	public void setPriority(Priority myPriority) {
		this.priority = myPriority;
	}


	public void unsetTitle() {
		this.title = "";
	}


	public void unsetDescription() {
		this.description = "";
	}


	public void unsetDue() {
		this.due = new Date();
	}


	public void unsetReminderme() {
		this.reminderme = new Date();
	}

	public static class Builder {

		private Todo built;

		public Builder(String title) {
			built = new Todo();
			built.title = title;
		}

		public Todo build() {
			return built;
		}

		public Builder description(String description) {
			built.description = description;
			return this;
		}
	}

	public static Builder getBuilder(String title) {
		return new Builder(title);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

