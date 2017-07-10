package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modescenemain entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modescenemain")
public class Modescenemain implements java.io.Serializable,com.flywind.app.data.Intermain {

	// Fields

	private long id;
	private Long houseId;
	private Long sceneId;
	private String groupName;
	private Long oldId;

	// Constructors

	/** default constructor */
	public Modescenemain() {
	}

	/** full constructor */
	public Modescenemain(long houseId, long sceneId, String groupName,
			Long oldId) {
		this.houseId = houseId;
		this.sceneId = sceneId;
		this.groupName = groupName;
		this.oldId = oldId;
	}

	// Property accessors
	@Override
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "HouseID")
	public Long getHouseId() {
		return this.houseId;
	}

	@Override
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	@Column(name = "SceneID")
	public Long getSceneId() {
		return this.sceneId;
	}

	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}

	@Column(name = "GroupName")
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	@Column(name = "oldId")
	public Long getOldId() {
		return this.oldId;
	}

	@Override
	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}

}