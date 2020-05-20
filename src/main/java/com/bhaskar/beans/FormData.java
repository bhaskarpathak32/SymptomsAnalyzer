package com.bhaskar.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "usertable")
@JsonIgnoreProperties
public class FormData implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@SequenceGenerator(name = "seq", sequenceName = "seq", allocationSize = 1)
	private Long id;
	public long age;
	public String name;
	public long cough;
	public long fever;
	public long headache;
	public long chest_pain;
	public long runny_nose;
	public long sneeze;
	public long breath_difficulty;
	public long bleeding_gum;
	public long gum_pain;
	public long nasal_congestion;
	public long clogged_ear;
	public long throat_pain;
	public long ear_pain;
	public long wheezing;
	public long rash;
	public long nausea;
	public int status;
	@JsonIgnore
	@Transient
	public String description;

}
