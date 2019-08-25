package rrx.cnuo.service.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户择偶条件vo")
public class UserSpouseSelectionVo {

	@ApiModelProperty(value = "年龄上限：18岁-60岁/0-不限")
    private Byte ageBgn;

	@ApiModelProperty(value = "年龄下限：18岁-60岁/0-不限")
    private Byte ageEnd;

	@ApiModelProperty(value = "身高(单位cm)上限：140cm - 200cm /0-不限")
    private Short heightBgn;

	@ApiModelProperty(value = "身高(单位cm)下限：140cm - 200cm /0-不限")
    private Short heightEnd;

	@ApiModelProperty(value = "身材(数字类型的数组)：-1-不限、0-一般、11-瘦长(男)、12-运动员型(男)、13-较胖(男)、14-体格魁梧(男)、15-壮实(男)、21-瘦长(女)、22-苗条(女)、23-高大美丽(女)、24-丰满(女)、25-富线条美(女)")
    private String figureList;

	@ApiModelProperty(value = "Ta的故乡省份id(数字类型的数组)")
    private String homeProvinceIdList;

	@ApiModelProperty(value = "Ta的工作生活地省份id(数字类型的数组)")
    private String workProvinceIdList;

	@ApiModelProperty(value = "Ta的婚姻状态：0-不限；1-未婚；2-离异；3-丧偶")
    private Byte maritalStatus;

	@ApiModelProperty(value = "希望Ta有无子女(数字类型的数组)：0-不限、1-没有孩子、2-有孩子且住在一起、3-有孩子偶尔一起住、4-有孩子但不在身边")
    private String haveChildrenList;

	@ApiModelProperty(value = "Ta的学历要求(数字类型的数组)：0-不限、1-博士、2-硕士、3-本科、4-大专")
    private String academicList;

	@ApiModelProperty(value = "Ta的毕业学校类型(数字类型的数组)：0-不限、1-985，2-211，3-985且211、4-一般全日制大学，5-海外院校")
    private String schoolTypeList;

	@ApiModelProperty(value = "Ta的所在公司类型(数字类型的数组)：0-不限、1-央企、2-国企、3-事业单位、4-私企、5-外企、6-其他")
    private String companyTypeList;

	@ApiModelProperty(value = "Ta的所在行业类型(数字类型的数组)：0-不限、1-计算机/互联网/通信、2-公务员/事业单位、3-教师、4-医生、5-护士、6-空乘人员、7-生产/工艺/制造、8-商业/服务业/个体经营、9-金融/银行/投资/保险、10-文化/广告/传媒、11-娱乐/艺术/表演、12-律师/法务、13-教育/培训/管理咨询、14-建筑/房地产/物业、15-消费零售/贸易/交通物流、16-酒店旅游、17-现代农业、18在校学生、19-其他")
    private String industryTypeList;

	@ApiModelProperty(value = "Ta的职场职级(数字类型的数组)：0-不限、1-企业主或单位负责人、2-高层管理、3-中层管理、4-普通职员、5-其他")
    private String rankTypeList;

	@ApiModelProperty(value = "Ta的年收入(数字类型的数组)：0-不限；1-10W以下、2-10W-20W、3-20W-30W、4-30W-50W、5-50W-100W、6-100W以上")
    private String yearIncomeList;

	@ApiModelProperty(value = "Ta的住房情况(数字类型的数组)：0-不限；1-和家人同住、2-已购房有贷款、3-已购房无贷款、4-租房、5-打算婚后买房、6-住单位宿舍")
    private String houseStatusList;

	@ApiModelProperty(value = "Ta的购车情况：0-不限；1 - 已购车;2 - 未购车;")
    private Byte carStatus;

	@ApiModelProperty(value = "Ta的资产水平(数字类型的数组)：0-不限、1-所在城市基本生活水平、2-所在城市小康生活水平、3-所在城市家境优渥")
    private String assetLevelList;

	@ApiModelProperty(value = "Ta的和父母的关系(数字类型的数组：0-不限、1-像朋友一样、2-尊重孝顺、3-长伴依赖、4-各自独立")
    private String relationWithParentsList;

	@ApiModelProperty(value = "希望Ta是独生子女(数字类型的数组：0-不限、1-独生子女、2-有哥哥、3-有姐姐、4-有弟弟、5-有妹妹")
    private String onlyChildList;

	@ApiModelProperty(value = "Ta的结婚计划(数字类型的数组)：0-不限、1-愿意闪婚、2-一年内、3-两年内、4-时机成熟就结婚")
    private String marryPlanList;

	@ApiModelProperty(value = "Ta对孩子的计划(数字类型的数组)：0-不限、1-要一个孩子、2-要多个孩子、3-不想要孩子、4-视情况而定")
    private String childPlanList;

	@ApiModelProperty(value = "Ta对花钱的态度(数字类型的数组)：0-不限、1-少花多存、2-花一半存一半、3-多花少存、4-月光、5-及时行乐花了再说")
    private String consumeAttitudeList;

	@ApiModelProperty(value = "Ta的宗教信仰：0-不限、1-无宗教信仰、2-佛教、3-道教、4-基督教、5-伊斯兰教、6-印度教、7-萨满教、8-其他教派")
    private Byte faith;

	@ApiModelProperty(value = "Ta的吸烟喝酒态度：0-不限、1-不吸烟不饮酒、2-不吸烟可以饮酒、3-可以吸烟不要饮酒")
    private Byte smokDrink;

	@ApiModelProperty(value = "Ta对家务活的态度：0-不限、1-家务小能手、2-分工合作、3-不太会但愿意为了对方学习")
    private Byte housework;
}