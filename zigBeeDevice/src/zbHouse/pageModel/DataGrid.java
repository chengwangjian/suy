package zbHouse.pageModel;

import java.util.ArrayList;
import java.util.List;

public class DataGrid {

	private long total = 0L;
	private List rows = new ArrayList();

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
