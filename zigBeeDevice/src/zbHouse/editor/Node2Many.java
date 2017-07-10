package zbHouse.editor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import zbHouse.model.Device;
import zbHouse.model.Node;
import zbHouse.pageModel.DataGrid;

@Service("nodeCheck")
public class Node2Many {
	
	private BaseDaoI<Node> nodeDao;
	private BaseDaoI<Device> deviceDao;
	
	public BaseDaoI<Node> getNodeDao() {
		return nodeDao;
	}
	@Autowired
	public void setNodeDao(BaseDaoI<Node> nodeDao) {
		this.nodeDao = nodeDao;
	}
	
	public BaseDaoI<Device> getDeviceDao() {
		return this.deviceDao;
	}

	@Autowired
	public void setDeviceDao(BaseDaoI<Device> deviceDao) {
		this.deviceDao = deviceDao;
	}

	public void nodeUpdateCheck(){
		
	}
	
	public void nodeDeleteCheck(DataGrid dg){
		List<Node> ns=dg.getRows();
		for(Node node:ns){
			String getHql = "FROM Device WHERE nodeId='"+node.getId()+"'";
			List<Device> device = deviceDao.find(getHql);
			for(Device d:device){
				String hql="update Device t set t.nodeId = '-1' where t.id ='" + d.getId()+ "' and houseIeee <> ieee";
				deviceDao.executeHql(hql);
			}
		}
	}
	
	public Device deviceUpdateCheck(Device device){
		String hql="from Node t where t.nodeId ='" + device.getNodeId()+ "'";
		if(nodeDao.get(hql) == null){
			device.setNodeId(-1L);
		}
		return device;
	}
	
}
