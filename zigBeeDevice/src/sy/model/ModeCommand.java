package sy.model;

import java.util.List;

public class ModeCommand {
private List<Command>shows;
private Long houseid;
private String picName;
private String modename;
private Long moderoomid;
private Long modeid;
private String description;
	

public List<Command> getShows() {
	return shows;
}

public void setShows(List<Command> shows) {
	this.shows = shows;
}
public Long getHouseid() {
	return houseid;
}

public void setHouseid(Long houseid) {
	this.houseid = houseid;
}


public String getPicName() {
	return picName;
}

public void setPicName(String picName) {
	this.picName = picName;
}

public String getModename() {
	return modename;
}

public void setModename(String modename) {
	this.modename = modename;
}

public Long getModeroomid() {
	return moderoomid;
}

public void setModeroomid(Long moderoomid) {
	this.moderoomid = moderoomid;
}

public Long getModeid() {
	return modeid;
}

public void setModeid(Long modeid) {
	this.modeid = modeid;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}
}
