package zbHouse.editor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import zbHouse.model.Mode;
import zbHouse.model.Node;
import zbHouse.model.Room;
import zbHouse.pageModel.DataGrid;

@Service("roomCheck")
public class Room2Many {

	private BaseDaoI<Room> roomDao;
	private BaseDaoI<Mode> modeDao;
	private BaseDaoI<Node> nodeDao;
	
	public BaseDaoI<Room> getRoomDao() {
		return this.roomDao;
	}

	@Autowired
	public void setRoomDao(BaseDaoI<Room> roomDao) {
		this.roomDao = roomDao;
	}
	
	public BaseDaoI<Mode> getModeDao() {
		return modeDao;
	}
	
	@Autowired
	public void setModeDao(BaseDaoI<Mode> modeDao) {
		this.modeDao = modeDao;
	}
	
	public BaseDaoI<Node> getNodeDao() {
		return nodeDao;
	}
	@Autowired
	public void setNodeDao(BaseDaoI<Node> nodeDao) {
		this.nodeDao = nodeDao;
	}
	
	public void roomUpdateCheck(){
		
	}
	public void roomDeleteCheck(DataGrid dg){
		List<Room> rs=dg.getRows();
		for(Room room:rs){
			String hql="update Mode t set t.roomId = '-1' where t.roomId = '" + room.getRoomId() + "'";
			modeDao.executeHql(hql);
			hql="update Node t set t.roomId = '-1' where t.roomId = '" + room.getRoomId() + "'";		
			nodeDao.executeHql(hql);
		}			
	}
	
	public Mode modeUpdateCheck(Mode mode){
		String hql="from Room t where t.roomId = '" + mode.getRoomId()+ "'";
		if(roomDao.get(hql) == null){
			mode.setRoomId(-1);
		}
		return mode;
	}	
	
	public Node nodeUpdateCheck(Node node){
		String hql="from Room t where t.roomId = '" + node.getRoomId()+ "'";
		if(roomDao.get(hql) == null){
			node.setRoomId(-1);
		}
		return node;
	}	
}
