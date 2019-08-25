package rrx.cnuo.service.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户详情vo")
public class UserDetailInfoVo {

	@ApiModelProperty(value = "所在单位名称")
    private String companyName;

	@ApiModelProperty(value = "所在单位类型：0-未知、1-央企、2-国企、3-事业单位、4-私企、5-外企、6-其他")
    private Byte companyType;

	@ApiModelProperty(value = "所在行业：0-未知、1-计算机/互联网/通信、2-公务员/事业单位、3-教师、4-医生、5-护士、6-空乘人员、7-生产/工艺/制造、8-商业/服务业/个体经营、9-金融/银行/投资/保险、10-文化/广告/传媒、11-娱乐/艺术/表演、12-律师/法务、13-教育/培训/管理咨询、14-建筑/房地产/物业、15-消费零售/贸易/交通物流、16-酒店旅游、17-现代农业、18在校学生、19-其他")
    private Byte industryType;

	@ApiModelProperty(value = "所处职级：0-未知、1-企业主或单位负责人、2-高层管理、3-中层管理、4-普通职员、5-其他")
    private Byte rankType;

    private Byte dishonestCreditResult;

    private Byte duotouLendResult;

    @ApiModelProperty(value = "父母情况：0-未知、1-父母健在、2-单亲家庭、3-父亲健在、4-母亲健在、5-父母均离世")
    private Byte parentalSituation;

    @ApiModelProperty(value = "和父母的关系(数字类型的数组)：1-像朋友一样、2-尊重孝顺、3-长伴依赖、4-各自独立")
    private String relationWithParentsList;

    @ApiModelProperty(value = "是否是独生子女：0-未知、1-独生子女、2-有兄弟姐妹")
    private Byte onlyChild;

    @ApiModelProperty(value = "有无哥哥")
    private Boolean haveBrother;

    @ApiModelProperty(value = "有无弟弟")
    private Boolean haveYoungerBrother;

    @ApiModelProperty(value = "有无姐姐")
    private Boolean haveSister;

    @ApiModelProperty(value = "有无妹妹")
    private Boolean haveYoungerSister;

    @ApiModelProperty(value = "兴趣爱好(数字类型的数组)：1-音乐、2-电影、3-摄影、4-健身、5-跑步、6-游泳、7-潜水、8-徒步、9-攀岩、10-跳伞、11-滑雪、12-极限挑战、13-宠物、14-旅行、15-乐器、16-表演、17-看书、18-绘画、19-收藏、20-美食、21-手工、22-赛车、23-网游、24-cosplay、25-其他")
    private String interestList;

    @ApiModelProperty(value = "喜欢吃的(数字类型的数组)：1-川菜、2-湘菜、3-粤菜、4-鲁菜、5-徽菜、6-江浙菜、7-淮扬菜、8-西北菜、9-东北菜、10-北京菜、11-云贵菜、12-港式、13-日料、14-韩餐、15-泰餐、16-意餐、17-法餐、18-德国肘子、19-西班牙火腿、20-墨西哥甩饼、21-土尔其烤肉、22-甜品、23-其他")
    private String likeEatList;

    @ApiModelProperty(value = "结婚计划：0-未知、1-愿意闪婚、2-一年内、3-两年内、4-时机成熟就结婚")
    private Byte marryPlan;

    @ApiModelProperty(value = "对孩子的计划：0-未知、1-要一个孩子、2-要多个孩子、3-不想要孩子、4-视情况而定")
    private Byte childPlan;

    @ApiModelProperty(value = "理想的伴侣类型(数字类型的数组)：11-成熟稳重(男)、12-温暖阳光(男)、13-寡言内秀(男)、14-活跃幽默(男)、21-温柔贤淑(女)、22-活泼可爱(女)、23-爽朗直率(女)、24-理性独立(女)")
    private String idealPartnerTypeList;

    @ApiModelProperty(value = "对花钱的态度：0-未知、1-少花多存、2-花一半存一半、3-多花少存、4-月光、5-及时行乐花了再说")
    private Byte consumeAttitude;

    @ApiModelProperty(value = "遇到心仪的异性时是否会主动追求：1-会,爱情要靠自己争取、2-不会主动追求但会有所暗示、3-不会,等待对方主动靠近")
    private Byte activePursuit;

    @ApiModelProperty(value = "是否介意对方感情经历：1-必须交待清楚才能开始我和Ta之间的感情、2-有点介意,希望Ta能坦诚相告、3-完全不介意")
    private Byte mindEmotionExperiences;

    @ApiModelProperty(value = "单身原因(数字类型的数组)：1-生活圈子太小、2-不够积极主动、3-工作太忙、4-择偶标准高、5-经济压力、6-父母影响、7-情感曾经受挫、8-对婚姻没有安全感、9-崇尚单身主义、10-对自己不自信、11-性格原因、12-暂时不想脱单、13-觉得自己年龄还小、14-不知道如何与异性相处、15-其他")
    private String singleReasonList;

    @ApiModelProperty(value = "宗教信仰：0-未知、1-无宗教信仰、2-佛教、3-道教、4-基督教、5-伊斯兰教、6-印度教、7-萨满教、8-其他教派")
    private Byte faith;

    @ApiModelProperty(value = "吸烟喝酒状况：0-未知、1-不吸烟不饮酒、2-不吸烟饮酒、3-吸烟不饮酒、4-烟酒不离手")
    private Byte smokDrink;

    @ApiModelProperty(value = "对家务活的态度：0-未知、1-家务小能手、2-分工合作、3-不太会但愿意为了对方学习、4-对方能承包家务就太好了")
    private Byte housework;
}