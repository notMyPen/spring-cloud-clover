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

@ApiModel("用户的观念")
public class UserConcept {

	@ApiModelProperty(value = "结婚计划：0-未知、1-愿意闪婚、2-一年内、3-两年内、4-时机成熟就结婚")
	private Byte marryPlan;

	@ApiModelProperty(value = "对孩子的计划：0-未知、1-要一个孩子、2-要多个孩子、3-不想要孩子、4-视情况而定")
    private Byte childPlan;

	@ApiModelProperty(value = "理想的伴侣类型")
    private List<String> idealPartnerTypeShowList = new ArrayList<String>();
	
	@JsonIgnore
	private String idealPartnerTypeList;

	@ApiModelProperty(value = "对花钱的态度：0-未知、1-少花多存、2-花一半存一半、3-多花少存、4-月光、5-及时行乐花了再说")
    private Byte consumeAttitude;

	@ApiModelProperty(value = "遇到心仪的异性时是否会主动追求：1-会,爱情要靠自己争取、2-不会主动追求但会有所暗示、3-不会,等待对方主动靠近")
    private Byte activePursuit;

	@ApiModelProperty(value = "是否介意对方感情经历：1-必须交待清楚才能开始我和Ta之间的感情、2-有点介意,希望Ta能坦诚相告、3-完全不介意")
    private Byte mindEmotionExperiences;

	@ApiModelProperty(value = "单身原因")
    private List<String> singleReasonShowList = new ArrayList<String>();
	
	@JsonIgnore
	private String singleReasonList;

	@ApiModelProperty(value = "宗教信仰：0-未知、1-无宗教信仰、2-佛教、3-道教、4-基督教、5-伊斯兰教、6-印度教、7-萨满教、8-其他教派")
    private Byte faith;

	public Byte getMarryPlan() {
		return marryPlan;
	}

	public void setMarryPlan(Byte marryPlan) {
		this.marryPlan = marryPlan;
	}

	public Byte getChildPlan() {
		return childPlan;
	}

	public void setChildPlan(Byte childPlan) {
		this.childPlan = childPlan;
	}

	public List<String> getIdealPartnerTypeShowList() {
		return idealPartnerTypeShowList;
	}

	public void setIdealPartnerTypeShowList(List<String> idealPartnerTypeShowList) {
		this.idealPartnerTypeShowList = idealPartnerTypeShowList;
	}

	public String getIdealPartnerTypeList() {
		return idealPartnerTypeList;
	}

	public void setIdealPartnerTypeList(String idealPartnerTypeList) {
		this.idealPartnerTypeList = idealPartnerTypeList;
		if(StringUtils.isNotBlank(idealPartnerTypeList)) {
			JSONArray jsonArr = JSON.parseArray(idealPartnerTypeList);
			for(int i=0;i<jsonArr.size();i++) {
				this.idealPartnerTypeShowList.add(UserConst.IdealPartnerType.getIdealPartnerType(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public Byte getConsumeAttitude() {
		return consumeAttitude;
	}

	public void setConsumeAttitude(Byte consumeAttitude) {
		this.consumeAttitude = consumeAttitude;
	}

	public Byte getActivePursuit() {
		return activePursuit;
	}

	public void setActivePursuit(Byte activePursuit) {
		this.activePursuit = activePursuit;
	}

	public Byte getMindEmotionExperiences() {
		return mindEmotionExperiences;
	}

	public void setMindEmotionExperiences(Byte mindEmotionExperiences) {
		this.mindEmotionExperiences = mindEmotionExperiences;
	}

	public List<String> getSingleReasonShowList() {
		return singleReasonShowList;
	}

	public void setSingleReasonShowList(List<String> singleReasonShowList) {
		this.singleReasonShowList = singleReasonShowList;
	}

	public String getSingleReasonList() {
		return singleReasonList;
	}

	public void setSingleReasonList(String singleReasonList) {
		this.singleReasonList = singleReasonList;
		if(StringUtils.isNotBlank(singleReasonList)) {
			JSONArray jsonArr = JSON.parseArray(singleReasonList);
			for(int i=0;i<jsonArr.size();i++) {
				this.singleReasonShowList.add(UserConst.SingleReason.getSingleReason(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public Byte getFaith() {
		return faith;
	}

	public void setFaith(Byte faith) {
		this.faith = faith;
	}
	
}
