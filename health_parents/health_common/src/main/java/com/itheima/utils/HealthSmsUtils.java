package com.itheima.utils;


import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送工具类
 */
public class HealthSmsUtils {
	public static final String VALIDATE_CODE = "SMS_175535689";
	public static final String SIGN_NAME = "传智播客";
	public static final String ORDER_CODE = "SMS_175571393";


	public static CommonResponse sendSms(String phone, String templateCode, String param, String signName){
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fv9BrhJmpChTCGpru5w", "yG7K6p1ksZ3ktI7h3CsiMIe9IzDwXd");
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("RegionId", "cn-hangzhou");
		request.putQueryParameter("PhoneNumbers", phone);
		request.putQueryParameter("SignName", signName);
		request.putQueryParameter("TemplateCode", templateCode);
		request.putQueryParameter("TemplateParam", param);
		try {
			CommonResponse response = client.getCommonResponse(request);
			return response;
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args){
		String code = "5536";
		Map map = new HashMap<>();
		map.put("code",code);
		String jsonCode = JSON.toJSONString(map);
		System.out.println(jsonCode);
		HealthSmsUtils.sendSms("17793494052",HealthSmsUtils.VALIDATE_CODE,jsonCode,HealthSmsUtils.SIGN_NAME);
	}
}
