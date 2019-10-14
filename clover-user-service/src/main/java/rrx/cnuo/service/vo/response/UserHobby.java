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

@ApiModel("用户的兴趣")
public class UserHobby {

	@ApiModelProperty(value = "爱好")
	private List<String> interestShowList = new ArrayList<String>();
	
	@JsonIgnore
	private String interestList;

	@ApiModelProperty(value = "喜欢吃的")
    private List<String> likeEatShowList = new ArrayList<String>();
	
	@JsonIgnore
	private String likeEatList;

	public List<String> getInterestShowList() {
		return interestShowList;
	}

	public void setInterestShowList(List<String> interestShowList) {
		this.interestShowList = interestShowList;
	}

	public String getInterestList() {
		return interestList;
	}

	public void setInterestList(String interestList) {
		this.interestList = interestList;
		if(StringUtils.isNotBlank(interestList)) {
			JSONArray jsonArr = JSON.parseArray(interestList);
			for(int i=0;i<jsonArr.size();i++) {
				this.interestShowList.add(UserConst.Interest.getInterest(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getLikeEatShowList() {
		return likeEatShowList;
	}

	public void setLikeEatShowList(List<String> likeEatShowList) {
		this.likeEatShowList = likeEatShowList;
	}

	public String getLikeEatList() {
		return likeEatList;
	}

	public void setLikeEatList(String likeEatList) {
		this.likeEatList = likeEatList;
		if(StringUtils.isNotBlank(likeEatList)) {
			JSONArray jsonArr = JSON.parseArray(likeEatList);
			for(int i=0;i<jsonArr.size();i++) {
				this.likeEatShowList.add(UserConst.LikeEat.getLikeEat(jsonArr.getByte(i)).getMessage());
			}
		}
	}
	
}
