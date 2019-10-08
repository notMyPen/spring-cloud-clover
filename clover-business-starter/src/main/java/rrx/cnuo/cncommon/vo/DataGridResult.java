package rrx.cnuo.cncommon.vo;

import java.util.List;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "分页查询返回实体")
public class DataGridResult<T> {

	@ApiModelProperty(value = "总数量",required = true)
    private long total;
	
	@ApiModelProperty(value = "请求标记：初始值0。之后后端在结果中返回什么，前端传什么回去",required = true)
	private int requestTag;
	
	@ApiModelProperty(value = "当前返回列表最后一条数据的时间戳",required = true)
	private long cursor;

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

	public int getRequestTag() {
		return requestTag;
	}

	public void setRequestTag(int requestTag) {
		this.requestTag = requestTag;
	}

	public long getCursor() {
		return cursor;
	}

	public void setCursor(long cursor) {
		this.cursor = cursor;
	}
	
}
