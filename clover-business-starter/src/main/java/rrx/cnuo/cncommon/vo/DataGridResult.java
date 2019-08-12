package rrx.cnuo.cncommon.vo;

import java.util.List;

import com.github.pagehelper.PageInfo;

public class DataGridResult<T> {

    private long total;

    private List<T> rowsList;

    public DataGridResult() {
    }

    public DataGridResult(List<T> rowsList) {
        this.total = new PageInfo<T>(rowsList).getTotal();
        this.rowsList = rowsList;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRowsList() {
        return rowsList;
    }

    public void setRowsList(List<T> rowsList) {
        this.rowsList = rowsList;
    }

}
