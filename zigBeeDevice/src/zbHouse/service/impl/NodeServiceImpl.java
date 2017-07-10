package zbHouse.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.util.PropertiesUtil;

import zbHouse.editor.Node2Many;
import zbHouse.editor.Room2Many;
import zbHouse.model.Node;
import zbHouse.pageModel.DataGrid;
import zbHouse.service.NodeServiceI;

@Service("nodeService")
public class NodeServiceImpl implements NodeServiceI {
	private List<Node> cache = new ArrayList(100);
	private int cacheSize = Integer.parseInt(PropertiesUtil.getProperty("batch.node"));
	private Room2Many roomCheck;
	private BaseDaoI<Node> nodeDao;
	public BaseDaoI<Node> getNodeDao() {
		return nodeDao;
	}
	@Autowired
	public void setNodeDao(BaseDaoI<Node> nodeDao) {
		this.nodeDao = nodeDao;
	}

	private static final Logger log = Logger.getLogger(NodeServiceImpl.class);
	//数据库的长连接
	private Connection conn = null;	
	public Room2Many getRoomCheck() {
		return roomCheck;
	}
	@Autowired
	public void setRoomCheck(Room2Many roomCheck) {
		this.roomCheck = roomCheck;
	}

	private Node2Many nodeCheck;	

	public Node2Many getNodeCheck() {
		return nodeCheck;
	}
	@Autowired
	public void setNodeCheck(Node2Many nodeCheck) {
		this.nodeCheck = nodeCheck;
	}
	
	@Override
	public Node keyUpdate(Node node) {
		
		return node;
		// TODO Auto-generated method stub
	}

	/**
	 * 将Map对象转换为Node对象
	 * @param map
	 * @return
	 */
	private Node convertMapToNode(ResultSet rs) {
		Node oldNode = new Node();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			oldNode.setId(rs.getLong(1));
			oldNode.setHouseIeee(rs.getString(2));
			oldNode.setRoomId(rs.getLong(3));
			oldNode.setIeee(rs.getString(4));
			oldNode.setType(rs.getString(5));
			oldNode.setIp(rs.getString(6));
			oldNode.setNodeId(rs.getLong(7));
			oldNode.setNodeType(rs.getShort(8));
			oldNode.setNodeName(rs.getString(9));
			oldNode.setCreatetime(rs.getString(10)==null?null:sdf.parse(rs.getString(10)));
			oldNode.setLasttime(rs.getString(11)==null?null:sdf.parse(rs.getString(11)));
		} catch(Exception e) {
			log.info("nodeServiceImpl convertMapToNode", e);
		}
		return oldNode;
	}
	
	@Override
	public Node saveOrUpdate(Node newdata,final Map<String, Object> params) {
		cache.add(newdata);
//		log.info("Node save or update batch count=" + this.cache.size());
		//批量更新
		if(this.cache.size()>cacheSize){
			dingshiSave();
//			log.info("定时保存nodeBegin......");
//			final List<Node> tmpList = this.cache;
//			this.cache = new ArrayList<Node>(this.cacheSize + 10);
////			new Thread() {
////	    		public void run() {
//	    			log.info("Node saveorupdate begin, tmpList.size: " + tmpList.size());
//	    			StringBuilder sql = new StringBuilder("REPLACE INTO Node(id,houseIeee,roomId,ieee,type,ip,nodeId,nodeName,createtime,lasttime,nodeType) VALUES");
//	    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    			Connection conn = null;
//	    			Statement stmt = null;
//	    			ResultSet rs = null;
//	    			try {
//	    				Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
//	    				conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
//	    						PropertiesUtil.getProperty("jdbc.user"),
//	    						PropertiesUtil.getProperty("jdbc.password"));
//	    				stmt = conn.createStatement();
//	    				for(Node nv: tmpList) {	 
//	    					Node olddata =null;
//	    					String hql = "select * from Node  where houseIeee = '"+nv.getHouseIeee()+"' and "+"ieee ='"+nv.getIeee()+"'";
//	    					rs = stmt.executeQuery(hql);
//	    					while(rs.next()) {
//	    						olddata = convertMapToNode(rs);
//	    					}
//	    					String sql1="select * from Room  where roomId = '" + nv.getRoomId()+ "'";
//	    					rs = stmt.executeQuery(sql1);
//	    					if(rs == null){
//	    						nv.setRoomId(-1);
//	    					}
//	    					if(olddata!= null){
//	    						BeanUtils.copyProperties(nv, olddata,new String[]{"id","createtime"});
//	    						sql.append("(").append(olddata.getId()).append(",'").append(olddata.getHouseIeee()).append("',")
//	    	    				.append(olddata.getRoomId()).append(",'").append(olddata.getIeee()).append("','").append(olddata.getType())
//	    	    				.append("','").append(olddata.getIp()).append("','").append(olddata.getNodeId()).append("','").append(olddata.getNodeName()).append("','")
//	    	    				.append(sdf.format(olddata.getCreatetime())).append("','").append(sdf.format(olddata.getLasttime())).append("',").append(nv.getNodeType())
//	    	    				.append("),");
//	    					}else{
//	    						sql.append("(").append(nv.getId()).append(",'").append(nv.getHouseIeee()).append("',")
//	    	    				.append(nv.getRoomId()).append(",'").append(nv.getIeee()).append("','").append(nv.getType())
//	    	    				.append("','").append(nv.getIp()).append("','").append(nv.getNodeId()).append("','").append(nv.getNodeName()).append("','")
//	    	    				.append(sdf.format(nv.getCreatetime())).append("','").append(sdf.format(nv.getLasttime())).append("',").append(nv.getNodeType())
//	    	    				.append("),");
//	    					}
//	    				}
//	    				String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
//	    				int i = stmt.executeUpdate(sqlStr);
//	    				log.info("Node saveorupdate end, result: " +i);
//	    			} catch(Exception e) {
//	    				log.info("save or update Node exception", e);
//    				} finally {
//	    				try {
//	    					if(rs != null)
//	    						rs.close();
//	    					if(stmt != null)
//	    						stmt.close();
//	    					if(conn != null)
//	    						conn.close();
//	    				} catch(Exception e) {
//	    					log.info("jdbc close exception", e);
//	    				}
//	    			}
//	    			log.info("定时保存nodeEnd......");
////    			}
////			}.start();
		}
		return newdata;	
	}
	
	@Override
	public int delete(Map<String, Object> params) {
		
		int dcount=0;
		DataGrid dg = this.find(params);
		if(dg.getTotal()!=0){
			nodeCheck.nodeDeleteCheck(dg);
			String hql = "delete Node t ";		
			hql = addWhere2(hql, params);
			dcount=nodeCheck.getNodeDao().executeHql(hql);
		}
		return dcount;

		// TODO Auto-generated method stub
		
	}
	
	@Override
	public DataGrid find(Map<String, Object> params) {

		DataGrid dg = new DataGrid();
		String hql = "from Node t ";
		hql = addWhere2(hql, params);
		String totalHql = "select count(*) " + hql;	
		
		List<Node> l=roomCheck.getNodeDao().find(hql);
		dg.setTotal(roomCheck.getNodeDao().count(totalHql));
		dg.setRows(l);
		return dg;
		// TODO Auto-generated method stub		
	}
	private String addWhere(String hql,Node node, Map<String, Object> params) {
		if (node!= null){
			params.put("houseIeee", node.getHouseIeee());
			params.put("ieee", node.getIeee());
			hql += "where t.houseIeee =:houseIeee and t.ieee =:ieee ";
		}
		return hql;
	}
	private String addWhere2(String hql, Map<String, Object> params) {
		if (params!= null){
			boolean first=true;
			hql += "where ";
			  for (String key : params.keySet()) {
				if(first){  
					first=false;
					hql += "t." + key + " ='" + params.get(key)+ "'";
				}else{
					hql += " and t." + key + " ='" + params.get(key)+ "'";					
				}
			}			
		}
		return hql;
	}	
	
	
	@Override
	public int dingshiSave() {
		if(cache.size()==0){
			log.info("定时保存node，缓存为空");
			return 0;
		}
		log.info("定时保存nodeBegin......");
		final List<Node> tmpList = this.cache;
		this.cache = new ArrayList<Node>(this.cacheSize + 10);
//    	log.info("Node saveorupdate begin, tmpList.size: " + tmpList.size());
    	Thread th = new Thread() {
    		@Override
			public void run() {
    			StringBuilder sql = new StringBuilder("REPLACE INTO Node(id,houseIeee,roomId,ieee,type,ip,nodeId,nodeName,createtime,lasttime,nodeType) VALUES");
    	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	    	Connection conn = null;
    			Statement stmt = null;
    			ResultSet rs = null;
    			try {
    				Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
    				conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
    						PropertiesUtil.getProperty("jdbc.user"),
    						PropertiesUtil.getProperty("jdbc.password"));
    				stmt = conn.createStatement();
    	    		for(Node nv: tmpList) {	 
    	    			Node olddata =null;
    	    			String hql = "select * from Node  where houseIeee = '"+nv.getHouseIeee()+"' and "+"ieee ='"+nv.getIeee()+"'";
    	    			rs = stmt.executeQuery(hql);
    	    			while(rs.next()) {
    	    				olddata = convertMapToNode(rs);
    	    			}
    	    			String sql1="select * from Room  where roomId = '" + nv.getRoomId()+ "'";
    	    			rs = stmt.executeQuery(sql1);
    	    			if(rs == null){
    	    				nv.setRoomId(-1);
    	    			}
    	    			if(olddata!= null){
    	    				BeanUtils.copyProperties(nv, olddata,new String[]{"id","createtime"});
    	    				sql.append("(").append(olddata.getId()).append(",'").append(olddata.getHouseIeee()).append("',")
    	    	    		.append(olddata.getRoomId()).append(",'").append(olddata.getIeee()).append("','").append(olddata.getType())
    	    	    		.append("','").append(olddata.getIp()).append("','").append(olddata.getNodeId()).append("','").append(olddata.getNodeName()).append("','")
    	    	    		.append(sdf.format(olddata.getCreatetime())).append("','").append(sdf.format(olddata.getLasttime())).append("',").append(nv.getNodeType())
    	    				.append("),");
    	    			}else{
    	    				sql.append("(").append(nv.getId()).append(",'").append(nv.getHouseIeee()).append("',")
    	    	    		.append(nv.getRoomId()).append(",'").append(nv.getIeee()).append("','").append(nv.getType())
    	    	    		.append("','").append(nv.getIp()).append("','").append(nv.getNodeId()).append("','").append(nv.getNodeName()).append("','")
    	    	    		.append(sdf.format(nv.getCreatetime())).append("','").append(sdf.format(nv.getLasttime())).append("',").append(nv.getNodeType())
    	    				.append("),");
    	    			}
    	    		}
    	    		String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
    	    		int i = stmt.executeUpdate(sqlStr);
    	    		log.info("Node saveorupdate end, result: " +i);
    	    	} catch(Exception e) {
    	    		log.info("save or update Node exception", e);
    			} finally {
    	    		try {
    	    			if(rs != null)
    	    				rs.close();
    	    			if(stmt != null)
    	    				stmt.close();
    	    			if(conn != null)
    	    				conn.close();
    	    		} catch(Exception e) {
    	    			log.info("jdbc close exception", e);
    	    		}
    	    	}
    			log.info("定时保存nodeEnd......");
//    	    	log.info("定时保存node，缓存为:"+tmpList.size());
    		}
    	};
		th.setName("saveNodeThread");
		th.start();
		return 1;
	}
}
