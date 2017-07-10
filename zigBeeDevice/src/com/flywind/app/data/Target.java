package com.flywind.app.data;





public class Target {
    private Long roomid;
	private Long houseId;
	private Long modeId;	
	private Long macroId;	
	private Long id;
	private Long mid;
	private Long epId;	
	private Integer behavior;
	private Long dest;	
	private Long type;
	private Long desttype;
	private String act;
	private Long ccid;
	public Long getCcid() {
		return ccid;
	}
	public void setCcid(Long ccid) {
		this.ccid = ccid;
	}
	
	
	public Target() {

	}	
	public Target(long type) {
		super();
		this.type=type;
	}
	public Long getHouseId() {
		return houseId;
	}
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getModeId() {
		return modeId;
	}
	public void setModeId(Long modeId) {
		this.modeId = modeId;
	}
	public Long getMacroId() {
		return macroId;
	}
	public void setMacroId(Long macroId) {
		this.macroId = macroId;
	}
	public Long getMid() {
		return mid;
	}
	public void setMid(Long mid) {
		this.mid = mid;
	}	
	public Long getEpId() {
		return epId;
	}
	public void setEpId(Long epId) {
		this.epId = epId;
	}
	public Integer getBehavior() {
		return behavior;
	}
	public void setBehavior(Integer behavior) {
		this.behavior = behavior;
	}
	
	public Long getDest() {
		return dest;
	}
	public void setDest(Long dest) {
		this.dest = dest;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getDesttype() {
		return desttype;
	}
	public void setDesttype(Long desttype) {
		this.desttype = desttype;
	}
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}
	public Long getRoomid() {
		return roomid;
	}
	public void setRoomid(Long roomid) {
		this.roomid = roomid;
	}


}
