package rrx.cnuo.service.vo.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rrx.cnuo.service.accessory.consts.UserConst;

@ApiModel("用户的家庭情况")
public class UserFamilySituation {

	@ApiModelProperty(value = "父母情况：0-未知、1-父母健在、2-单亲家庭、3-父亲健在、4-母亲健在、5-父母均离世")
	private Byte parentalSituation;

	@ApiModelProperty(value = "和父母的关系")
    private List<String> relationWithParentsShowList = new ArrayList<String>();
	
	@JsonIgnore
	private String relationWithParentsList;

	@ApiModelProperty(value = "独生子女：0-未知、1-独生子女、2-有兄弟姐妹")
    private Byte onlyChild;

	@ApiModelProperty(value = "有无哥哥（onlyChild=2时显示）")
    private Boolean haveBrother;

	@ApiModelProperty(value = "有无弟弟（onlyChild=2时显示）")
    private Boolean haveYoungerBrother;

	@ApiModelProperty(value = "有无姐姐（onlyChild=2时显示）")
    private Boolean haveSister;

	@ApiModelProperty(value = "有无妹妹（onlyChild=2时显示）")
    private Boolean haveYoungerSister;

	public Byte getParentalSituation() {
		return parentalSituation;
	}

	public void setParentalSituation(Byte parentalSituation) {
		this.parentalSituation = parentalSituation;
	}

	public List<String> getRelationWithParentsShowList() {
		return relationWithParentsShowList;
	}

	public void setRelationWithParentsShowList(List<String> relationWithParentsShowList) {
		this.relationWithParentsShowList = relationWithParentsShowList;
	}

	public String getRelationWithParentsList() {
		return relationWithParentsList;
	}

	public void setRelationWithParentsList(String relationWithParentsList) {
		this.relationWithParentsList = relationWithParentsList;
		if(StringUtils.isNotBlank(relationWithParentsList)) {
			JSONArray jsonArr = JSON.parseArray(relationWithParentsList);
			for(int i=0;i<jsonArr.size();i++) {
				this.relationWithParentsShowList.add(UserConst.RelationWithParents.getRelationWithParents(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public Byte getOnlyChild() {
		return onlyChild;
	}

	public void setOnlyChild(Byte onlyChild) {
		this.onlyChild = onlyChild;
	}

	public Boolean getHaveBrother() {
		return haveBrother;
	}

	public void setHaveBrother(Boolean haveBrother) {
		this.haveBrother = haveBrother;
	}

	public Boolean getHaveYoungerBrother() {
		return haveYoungerBrother;
	}

	public void setHaveYoungerBrother(Boolean haveYoungerBrother) {
		this.haveYoungerBrother = haveYoungerBrother;
	}

	public Boolean getHaveSister() {
		return haveSister;
	}

	public void setHaveSister(Boolean haveSister) {
		this.haveSister = haveSister;
	}

	public Boolean getHaveYoungerSister() {
		return haveYoungerSister;
	}

	public void setHaveYoungerSister(Boolean haveYoungerSister) {
		this.haveYoungerSister = haveYoungerSister;
	}
	
}
