package sy.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sy.model.Messagehistory;

public abstract interface MessagehistoryServiceI
{
  public abstract List<Messagehistory> findList(String paramString1, String paramString2, String paramString3, Map<String, Object> paramMap);

  public abstract int exportMessageExcel(Map<String, Object> paramMap, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

  public abstract List<Map> successCount(Map<String, Object> paramMap);

  public abstract List<Map> findList2(String paramString1, String paramString2, String paramString3, Map<String, Object> paramMap);

  public abstract List<Map> findmessage(String paramString1, String paramString2, String paramString3, Map<String, Object> paramMap);
}