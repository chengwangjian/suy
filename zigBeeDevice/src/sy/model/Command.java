package sy.model;



public class Command {
private int id=0;
private Long deviceid;
private int  actid;
private boolean test;
private Long roomid;
private String act;
private int destType;
private String name="amcroSubName";

private String param;

private Long delaySec;
public Long getDeviceid() {
	return deviceid;
}
public void setDeviceid(Long deviceid) {
	this.deviceid = deviceid;
}

public boolean isTest() {
	return test;
}
public void setTest(boolean test) {
	this.test = test;
}
public Long getRoomid() {
	return roomid;
}
public void setRoomid(Long roomid) {
	this.roomid = roomid;
}

public Long getDelaySec() {
	return delaySec;
}
public void setDelaySec(Long delaySec) {
	this.delaySec = delaySec;
}
public int getActid() {
	return actid;
}
public void setActid(int actid) {
	this.actid = actid;
}
public String getParam() {
	return param;
}
public void setParam(String param) {
	this.param = param;
}
public String getAct() {
	return act;
}
public void setAct(String act) {
	this.act = act;
}
public int getDestType() {
	return destType;
}
public void setDestType(int destType) {
	this.destType = destType;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}



}
