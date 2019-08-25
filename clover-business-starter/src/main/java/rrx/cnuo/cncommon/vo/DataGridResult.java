package rrx.cnuo.cncommon.vo;

import java.util.List;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "分页查询返回实体")
public class DataGridResult<T> {

	@ApiModelProperty(value = "总数量",required = true)
    private long total;

	@ApiModelProperty(value = "当前页数据",required = true)
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
