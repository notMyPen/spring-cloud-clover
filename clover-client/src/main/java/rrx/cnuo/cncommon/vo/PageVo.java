package rrx.cnuo.cncommon.vo;

/**
 * 分页参数
 * @author xuhongyu
 * @date 2019年4月11日
 */
public class PageVo {

	/**
	 * 当前页数，从1开始
	 */
    private Integer pageNum = 1;

    /**
     * 每页数据条数
     */
    private Integer pageSize = 10;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
