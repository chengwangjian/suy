package sy.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.DevicewarnhistoryHouseidYear;
import sy.model.FarmWarnHandle;
import sy.service.FarmWarnHandleServiceI;

@Service("FarmWarnHandleService")
public class FarmWarnHandleServiceImpl implements FarmWarnHandleServiceI {

	@Autowired
	private BaseDaoI<FarmWarnHandle> farmWarnHandleDao;

	@Autowired
	private BaseDaoI<DevicewarnhistoryHouseidYear> devicewarnhistoryHouseidYearDao;

	@Override
	public List<FarmWarnHandle> find(Map<String, Object> jsonMap) {
		StringBuffer hql = new StringBuffer();
		hql.append("from FarmWarnHandle  where houseieee = :houseieee and handletime between :beginTime and :endTime");
		Map<String, Object> params = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startTime = sdf.parse((String) jsonMap.get("beginTime"));
			Date endTime = sdf.parse((String) jsonMap.get("endTime"));
			params.put("houseieee", (String) jsonMap.get("houseIeee"));
			params.put("beginTime", startTime);
			params.put("endTime", endTime);
			/*
			 * params.put("handletime", farmWarnHandle.getHandletime());
			 * params.put("note", farmWarnHandle.getNote());
			 */
			List<FarmWarnHandle> t = farmWarnHandleDao.find(hql.toString(), params);
			return t;
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int update(Map<String, Object> jsonMap) {
		FarmWarnHandle farmWarnHandle = new FarmWarnHandle();
		farmWarnHandle.setHouseieee((String) jsonMap.get("houseIeee"));
		farmWarnHandle.setHandletime(new Date());
		farmWarnHandle.setNote((String) jsonMap.get("note"));
		farmWarnHandleDao.save(farmWarnHandle);
		String warmgId = (String) jsonMap.get("warnMsgId").toString().replace("[", "(").replace("]", ")");
		Calendar calendar = DateUtils.toCalendar(new Date());
		int year = calendar.get(Calendar.YEAR);
		String sql = "update devicewarnhistory_" + farmWarnHandle.getHouseieee() + "_" + year
				+ " set hanlde_flag = 1 where message_id in " + warmgId + "";
		devicewarnhistoryHouseidYearDao.executeSql2(sql);

		return 0;
	}

	@Override
	public int delete(FarmWarnHandle farmWarnHandle) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<FarmWarnHandle> find1(String startRow, String pageSize, String orderBy, FarmWarnHandle farmWarnHandle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FarmWarnHandle add(FarmWarnHandle farmWarnHandle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addbatchapp(List<FarmWarnHandle> list) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int appupdatereadstate(FarmWarnHandle farmWarnHandle) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCount(Map<String, Object> update) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(FarmWarnHandle farmWarnHandle) {
		// TODO Auto-generated method stub
		return 0;
	}

}
