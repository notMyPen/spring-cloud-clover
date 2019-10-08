package rrx.cnuo.service.accessory.consts;

/**
 * 用户信息枚举
 * @author xuhongyu
 * @date 2019年8月28日
 */
public class UserConst {

	public static final int USER_MAX_FORMID_CNT = 30;//用户最大存储formid个数
	public static final int TICKET_EXPIRE = 60*60*10; //10个小时
    public static final int AGE_MIN = 18; //用户的最小年龄
    public static final int AGE_MAX = 50; //用户的最大年龄
    
	/**
	 * 性别：0-未知；1-男；2-女
	 * @author xuhongyu
	 * @date 2019年8月26日
	 */
	public enum Gender {
        UNKNOWN((byte)0,"未知"),
        MALE((byte)1,"男"),
    	FEMALE((byte)2,"女");
    	
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private Gender(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}

		public static Gender getGender(Byte code) {
			for (Gender nTmUnit : Gender.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
		
		public static Gender getOppositeGender(byte code) {
			if(code == UNKNOWN.code) {
				return null;
			}else if(code == MALE.code) {
				return FEMALE;
			}else {
				return MALE;
			}
		}
    }
	
	/**
	 * 和父母的关系：1-像朋友一样、2-尊重孝顺、3-长伴依赖、4-各自独立
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum RelationWithParents{
		LikeFriend((byte)1,"像朋友一样"),
		Respect((byte)2,"尊重孝顺"),
		Dependence((byte)3,"长伴依赖"),
		Independent((byte)4,"各自独立");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private RelationWithParents(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static RelationWithParents getRelationWithParents(Byte code) {
			for (RelationWithParents nTmUnit : RelationWithParents.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 兴趣爱好：1-音乐、2-电影、3-摄影、4-健身、5-跑步、6-游泳、7-潜水、8-徒步、9-攀岩、10-跳伞、11-滑雪、12-极限挑战、13-宠物、
	 * 14-旅行、15-乐器、16-表演、17-看书、18-绘画、19-收藏、20-美食、21-手工、22-赛车、23-网游、24-cosplay、25-其他
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum Interest {
		Music((byte)1,"音乐"),
		Film((byte)2,"电影"),
		Photography((byte)3,"摄影"),
		Fitness((byte)4,"健身"),
		Run((byte)5,"跑步"),
		Swim((byte)6,"游泳"),
		Diving((byte)7,"潜水"),
		OnFoot((byte)8,"徒步"),
		RockClimbing((byte)9,"攀岩"),
		Parachute((byte)10,"跳伞"),
		Ski((byte)11,"滑雪"),
		ExtremeChallenge((byte)12,"极限挑战"),
		Pet((byte)13,"宠物"),
		Travel((byte)14,"旅行"),
		MusicalInstrument((byte)15,"乐器"),
		Performance((byte)16,"表演"),
		Reading((byte)17,"看书"),
		Painting((byte)18,"绘画"),
		Collection((byte)19,"收藏"),
		Food((byte)20,"美食"),
		Manual((byte)21,"手工"),
		RacingCar((byte)22,"赛车"),
		OnlineGames((byte)23,"网游"),
		Cosplay((byte)24,"cosplay"),
		Other((byte)25,"其他");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private Interest(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static Interest getInterest(Byte code) {
			for (Interest nTmUnit : Interest.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 喜欢吃的：1-川菜、2-湘菜、3-粤菜、4-鲁菜、5-徽菜、6-江浙菜、7-淮扬菜、8-西北菜、9-东北菜、10-北京菜、11-云贵菜、12-港式、
	 * 13-日料、14-韩餐、15-泰餐、16-意餐、17-法餐、18-德国肘子、19-西班牙火腿、20-墨西哥甩饼、21-土尔其烤肉、22-甜品、23-其他
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum LikeEat {
		ChuanCai((byte)1,"川菜"),
		XiangCai((byte)2,"湘菜"),
		YueCai((byte)3,"粤菜"),
		LuCai((byte)4,"鲁菜"),
		HuiCai((byte)5,"徽菜"),
		ZhejiangCai((byte)6,"江浙菜"),
		HuaiyangCai((byte)7,"淮扬菜"),
		XibeiCai((byte)8,"西北菜"),
		DongbeiCai((byte)9,"东北菜"),
		BeijingCai((byte)10,"北京菜"),
		YunguiCai((byte)11,"云贵菜"),
		HongKongFood((byte)12,"港式"),
		JapanFood((byte)13,"日料"),
		KoreaFood((byte)14,"韩餐"),
		ThailandFood((byte)15,"泰餐"),
		ItalianFood((byte)16,"意餐"),
		FrenchFood((byte)17,"法餐"),
		GermanElbow((byte)18,"德国肘子"),
		SpanishHam((byte)19,"西班牙火腿"),
		MexicanCrepeCake((byte)20,"墨西哥甩饼"),
		TurkishBarbecue((byte)21,"土尔其烤肉"),
		Dessert((byte)22,"甜品"),
		Other((byte)23,"其他");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private LikeEat(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static LikeEat getLikeEat(Byte code) {
			for (LikeEat nTmUnit : LikeEat.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 所在行业类型：0-不限、1-计算机/互联网/通信、2-公务员/事业单位、3-教师、4-医生、5-护士、6-空乘人员、7-生产/工艺/制造、8-商业/服务业/个体经营、9-金融/银行/投资/保险、
	 * 10-文化/广告/传媒、11-娱乐/艺术/表演、12-律师/法务、13-教育/培训/管理咨询、14-建筑/房地产/物业、15-消费零售/贸易/交通物流、16-酒店旅游、17-现代农业、18在校学生、19-其他
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum IndustryType {
		NotLimited((byte)0,"不限"),
		ComputerInternet((byte)1,"计算机/互联网/通信"),
		CivilServants((byte)2,"公务员/事业单位"),
		Teacher((byte)3,"教师"),
		Doctor((byte)4,"医生"),
		Nurse((byte)5,"护士"),
		Aircrew((byte)6,"空乘人员"),
		Production((byte)7,"生产/工艺/制造"),
		Business((byte)8,"商业/服务业/个体经营"),
		Finance((byte)9,"金融/银行/投资/保险"),
		Culture((byte)10,"文化/广告/传媒"),
		Entertainment((byte)11,"娱乐/艺术/表演"),
		Lawyer((byte)12,"律师/法务"),
		Education((byte)13,"教育/培训/管理咨询"),
		Construction((byte)14,"建筑/房地产/物业"),
		Consumer((byte)15,"消费零售/贸易/交通物流"),
		HotelTourism((byte)16,"酒店旅游"),
		Agriculture((byte)17,"现代农业"),
		Student((byte)18,"在校学生"),
		Other((byte)19,"其他");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private IndustryType(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static IndustryType getIndustryType(Byte code) {
			for (IndustryType nTmUnit : IndustryType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 单身原因：1-生活圈子太小、2-不够积极主动、3-工作太忙、4-择偶标准高、5-经济压力、6-父母影响、7-情感曾经受挫、8-对婚姻没有安全感、9-崇尚单身主义、
	 * 	10-对自己不自信、11-性格原因、12-暂时不想脱单、13-觉得自己年龄还小、14-不知道如何与异性相处、15-其他
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum SingleReason {
		SmallCircle((byte)1,"生活圈子太小"),
		NotPositive((byte)2,"不够积极主动"),
		Busy((byte)3,"工作太忙"),
		StandardHeight((byte)4,"择偶标准高"),
		EconomicPressure((byte)5,"经济压力"),
		ParentalInfluence((byte)6,"父母影响"),
		EmotionsFrustrated((byte)7,"情感曾经受挫"),
		NoSecurity((byte)8,"对婚姻没有安全感"),
		Singleism((byte)9,"崇尚单身主义"),
		NotConfident((byte)10,"对自己不自信"),
		Character((byte)11,"性格原因"),
		WishSingle((byte)12,"暂时不想脱单"),
		Young((byte)13,"觉得自己年龄还小"),
		GetAlongHard((byte)14,"不知道如何与异性相处"),
		Other((byte)15,"其他");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private SingleReason(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static SingleReason getSingleReason(Byte code) {
			for (SingleReason nTmUnit : SingleReason.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 理想的伴侣类型：11-成熟稳重(男)、12-温暖阳光(男)、13-寡言内秀(男)、14-活跃幽默(男)、21-温柔贤淑(女)、22-活泼可爱(女)、23-爽朗直率(女)、24-理性独立(女)
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum IdealPartnerType {
		General((byte)0,"一般"),
		MatureStable((byte)11,"成熟稳重(男)"),
		Warm((byte)12,"温暖阳光(男)"),
		WidowedShow((byte)13,"寡言内秀(男)"),
		ActiveHumor((byte)14,"活跃幽默(男)"),
		GentleCharming((byte)21,"温柔贤淑(女)"),
		EnergeticCute((byte)22,"活泼可爱(女)"),
		SleekStraightforward((byte)23,"爽朗直率(女)"),
		RationalIndependence((byte)24,"理性独立(女)");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private IdealPartnerType(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static IdealPartnerType getIdealPartnerType(Byte code) {
			for (IdealPartnerType nTmUnit : IdealPartnerType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 身材：0-一般、11-瘦长(男)、12-运动员型(男)、13-较胖(男)、14-体格魁梧(男)、15-壮实(男)、21-瘦长(女)、22-苗条(女)、23-高大美丽(女)、24-丰满(女)、25-富线条美(女)
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum Figure {
		General((byte)0,"一般"),
		SlenderMale((byte)11,"瘦长(男)"),
		Athlete((byte)12,"运动员型(男)"),
		Fatter((byte)13,"较胖(男)"),
		Burly((byte)14,"体格魁梧(男)"),
		Sturdy((byte)15,"壮实(男)"),
		SlenderFamale((byte)21,"瘦长(女)"),
		Slim((byte)22,"苗条(女)"),
		TallBeautiful((byte)23,"高大美丽(女)"),
		Full((byte)24,"丰满(女)"),
		RichLineBeauty((byte)25,"富线条美(女)");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private Figure(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static Figure getFigure(Byte code) {
			for (Figure nTmUnit : Figure.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 属相：0-未知；按顺序1-12分别表示鼠、牛、虎、兔、龙、蛇、马、羊、猴、鸡、狗、猪
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum Zodiac {
		UNKNOWN((byte)0,"未知"),
		Mouse((byte)1,"鼠"),
		Cattle((byte)2,"牛"),
		Tiger((byte)3,"虎"),
		Rabbit((byte)4,"兔"),
		Dragon((byte)5,"龙"),
		Snake((byte)6,"蛇"),
		Horse((byte)7,"马"),
		Sheep((byte)8,"羊"),
		Monkey((byte)9,"猴"),
		Chicken((byte)10,"鸡"),
		Dog((byte)11,"狗"),
		Pig((byte)12,"猪");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private Zodiac(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static Zodiac getZodiac(Byte code) {
			for (Zodiac nTmUnit : Zodiac.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 星座：0-未知；按顺序1-12依次表示白羊座、金牛座、双子座、巨蟹座、狮子座、处女座、天秤座、天蝎座、射手座、摩羯座、水瓶座、双鱼座
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum Constellation {
		UNKNOWN((byte)0,"未知"),
		Aries((byte)1,"白羊座"),
		Taurus((byte)2,"金牛座"),
		Gemini((byte)3,"双子座"),
		Cancer((byte)4,"巨蟹座"),
		Leo((byte)5,"狮子座"),
		Virgo((byte)6,"处女座"),
		Libra((byte)7,"天秤座"),
		Scorpio((byte)8,"天蝎座"),
		Sagittarius((byte)9,"射手座"),
		Capricorn((byte)10,"摩羯座"),
		Aquarius((byte)11,"水瓶座"),
		Pisces((byte)12,"双鱼座");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private Constellation(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static Constellation getConstellation(Byte code) {
			for (Constellation nTmUnit : Constellation.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 花钱的态度：0-不限、1-少花多存、2-花一半存一半、3-多花少存、4-月光、5-及时行乐花了再说
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum ConsumeAttitude{
		Unlimited((byte)0,"不限"),
		LessFlowers((byte)1,"少花多存"),
		SpendHalf((byte)2,"花一半存一半"),
		MoreFlowers((byte)3,"多花少存"),
		SpendAllMoney((byte)4,"月光"),
		CarpeDiem((byte)5,"及时行乐花了再说");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private ConsumeAttitude(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static ConsumeAttitude getConsumeAttitude(Byte code) {
			for (ConsumeAttitude nTmUnit : ConsumeAttitude.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 对孩子的计划：0-不限、1-要一个孩子、2-要多个孩子、3-不想要孩子、4-视情况而定
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum ChildPlan{
		Unlimited((byte)0,"不限"),
		OneChild((byte)1,"要一个孩子"),
		MoreChilden((byte)2,"要多个孩子"),
		NoneChild((byte)3,"不想要孩子"),
		ItAllDepends((byte)4,"视情况而定");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private ChildPlan(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static ChildPlan getChildPlan(Byte code) {
			for (ChildPlan nTmUnit : ChildPlan.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 结婚计划：0-不限、1-愿意闪婚、2-一年内、3-两年内、4-时机成熟就结婚
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum MarryPlan{
		Unlimited((byte)0,"不限"),
		FlashMarriage((byte)1,"愿意闪婚"),
		WithinOneYear((byte)2,"一年内"),
		WithinTwoYear((byte)3,"两年内"),
		OpportunityRipe((byte)4,"时机成熟就结婚");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private MarryPlan(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static MarryPlan getMarryPlan(Byte code) {
			for (MarryPlan nTmUnit : MarryPlan.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 希望Ta是独生子女：0-不限、1-独生子女、2-有哥哥、3-有姐姐、4-有弟弟、5-有妹妹
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum OnlyChild{
		Unlimited((byte)0,"不限"),
		OnlyOne((byte)1,"独生子女"),
		HaveBrothers((byte)2,"有哥哥"),
		HaveSisters((byte)3,"有姐姐"),
		HaveYoungerBrothers((byte)4,"有弟弟"),
		HaveYoungerSisters((byte)5,"有妹妹");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private OnlyChild(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static OnlyChild getOnlyChild(Byte code) {
			for (OnlyChild nTmUnit : OnlyChild.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 职场职级：0-不限、1-企业主或单位负责人、2-高层管理、3-中层管理、4-普通职员、5-其他
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum RankType{
		Unlimited((byte)0,"不限"),
		Master((byte)1,"企业主或单位负责人"),
		TopManagement((byte)2,"高层管理"),
		MiddleManagement((byte)3,"中层管理"),
		GeneralStaff((byte)4,"普通职员"),
		Other((byte)5,"其他");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private RankType(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static RankType getRankType(Byte code) {
			for (RankType nTmUnit : RankType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 住房情况：0-不限；1-和家人同住、2-已购房有贷款、3-已购房无贷款、4-租房、5-打算婚后买房、6-住单位宿舍
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum HouseStatus{
		Unlimited((byte)0,"不限"),
		LivingWithFamily((byte)1,"和家人同住"),
		PurchasedAndLoaned((byte)2,"已购房有贷款"),
		PurchasedWithoutLoan((byte)3,"已购房无贷款"),
		Renting((byte)4,"租房"),
		BuyingHouseAfterMarriage((byte)5,"打算婚后买房"),
		Accommodation((byte)6,"住单位宿舍");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private HouseStatus(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static HouseStatus getHouseStatus(Byte code) {
			for (HouseStatus nTmUnit : HouseStatus.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 年收入：0-不限；1-10W以下、2-10W-20W、3-20W-30W、4-30W-50W、5-50W-100W、6-100W以上
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum YearIncome{
		Unlimited((byte)0,"不限"),
		FirstClass((byte)1,"1-10W以下"),
		SecondClass((byte)2,"10W-20W"),
		ThreeClass((byte)3,"20W-30W"),
		FourClass((byte)4,"30W-50W"),
		FiveClass((byte)5,"50W-100W"),
		SixClass((byte)6,"100W以上");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private YearIncome(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static YearIncome getYearIncome(Byte code) {
			for (YearIncome nTmUnit : YearIncome.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 公司类型：0-不限、1-央企、2-国企、3-事业单位、4-私企、5-外企、6-其他
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum CompanyType{
		Unlimited((byte)0,"不限"),
		CentralEnterprises((byte)1,"央企"),
		StateOwnedEenterprise((byte)2,"国企"),
		Government((byte)3,"事业单位"),
		PrivateEnterprise((byte)4,"私企"),
		ForeignEnterprise((byte)5,"外企"),
		Other((byte)6,"其他");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private CompanyType(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static CompanyType getCompanyType(Byte code) {
			for (CompanyType nTmUnit : CompanyType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 毕业学校类型：0-不限、1-985，2-211，3-985且211、4-一般全日制大学，5-海外院校
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum SchoolType{
		Unlimited((byte)0,"不限"),
		Type985((byte)1,"985"),
		Type211((byte)2,"211"),
		Type985And211((byte)3,"985且211"),
		GeneralSchool((byte)4,"一般全日制大学"),
		OverseasSchool((byte)5,"海外院校");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private SchoolType(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static SchoolType getSchoolType(Byte code) {
			for (SchoolType nTmUnit : SchoolType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 资产水平：0-不限、1-所在城市基本生活水平、2-所在城市小康生活水平、3-所在城市家境优渥
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum AssetLevel{
		Unlimited((byte)0,"不限"),
		BasicLivingStandard((byte)1,"所在城市基本生活水平"),
		ComfortableLivingStandard((byte)2,"所在城市小康生活水平"),
		Munificent((byte)3,"所在城市家境优渥");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private AssetLevel(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static AssetLevel getAssetLevel(Byte code) {
			for (AssetLevel nTmUnit : AssetLevel.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 学历要求：0-不限、1-博士、2-硕士、3-本科、4-大专
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum Academic{
		Unlimited((byte)0,"不限"),
		Doctor((byte)1,"博士"),
		Master((byte)2,"硕士"),
		Undergraduate((byte)3,"本科"),
		JuniorCollege((byte)4,"大专");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private Academic(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static Academic getAcademic(Byte code) {
			for (Academic nTmUnit : Academic.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * Ta有无子女的要求：0-不限、1-没有孩子、2-有孩子且住在一起、3-有孩子偶尔一起住、4-有孩子但不在身边
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum HaveChildren{
		Unlimited((byte)0,"不限"),
		NoChildren((byte)1,"没有孩子"),
		HaveChildrenAround((byte)2,"有孩子且住在一起"),
		SometimesChildrenTogether((byte)3,"有孩子偶尔一起住"),
		HaveChildrenNotAround((byte)4,"有孩子但不在身边");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private HaveChildren(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static HaveChildren getHaveChildren(Byte code) {
			for (HaveChildren nTmUnit : HaveChildren.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 认证状态分类：0.未认证 1.待完善 2.认证中 3.认证成功 4.认证失败 5.已过期
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum CreditStatus{
		UnCredit((byte)0,"未认证"),
		ToBePerfected((byte)1,"待完善"),
		InCredit((byte)2,"认证中"),
		Succuss((byte)3,"认证成功"),
		Fail((byte)4,"认证失败"),
		Expire((byte)5,"已过期");
		
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private CreditStatus(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static CreditStatus getCreditStatus(Byte code) {
			for (CreditStatus nTmUnit : CreditStatus.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
}
