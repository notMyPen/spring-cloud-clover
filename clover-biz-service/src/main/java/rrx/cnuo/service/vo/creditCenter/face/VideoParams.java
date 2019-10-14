package rrx.cnuo.service.vo.creditCenter.face;

import lombok.Data;

@Data
public class VideoParams {

	private String endpoint;
	private String bucketName;
	private String accessKeyId;
	private String accessKeySecret;
	private String objectName;
}
