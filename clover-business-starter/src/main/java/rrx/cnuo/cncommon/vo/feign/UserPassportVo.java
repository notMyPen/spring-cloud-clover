package rrx.cnuo.cncommon.vo.feign;

public class UserPassportVo {

	private Long uid;

    private String registTel;

    private String miniOpenId;

    private String nickName;

    private String avatarUrl;

    private Integer cardNum;

    private Boolean creditFee;
    
    private Boolean creditPass;

    private Byte boardStatus;
    
    private Byte price;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getRegistTel() {
		return registTel;
	}

	public void setRegistTel(String registTel) {
		this.registTel = registTel;
	}

	public String getMiniOpenId() {
		return miniOpenId;
	}

	public void setMiniOpenId(String miniOpenId) {
		this.miniOpenId = miniOpenId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Integer getCardNum() {
		return cardNum;
	}

	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}

	public Boolean getCreditFee() {
		return creditFee;
	}

	public void setCreditFee(Boolean creditFee) {
		this.creditFee = creditFee;
	}

	public Boolean getCreditPass() {
		return creditPass;
	}

	public void setCreditPass(Boolean creditPass) {
		this.creditPass = creditPass;
	}

	public Byte getBoardStatus() {
		return boardStatus;
	}

	public void setBoardStatus(Byte boardStatus) {
		this.boardStatus = boardStatus;
	}

	public Byte getPrice() {
		return price;
	}

	public void setPrice(Byte price) {
		this.price = price;
	}
    
}
