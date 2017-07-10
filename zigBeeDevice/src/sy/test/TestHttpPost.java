package sy.test;

import java.net.URLEncoder;

import sy.util.Common;

/**
 * @author: zhuangxd
 * 时间：2013-9-26 上午11:48:07
 */
public class TestHttpPost {

	/**
	 * 
	 */
	public TestHttpPost() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author: zhuangxd
	 * 时间：2013-9-26 上午11:48:08
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// http://192.168.1.123:8080/zigBeeDevice/houseController/add.do?json={"houseIEEE":"4443455322222225","name":"用户2","longitude":"22","latitude":"33","networkAddress":"192.168.1.123","port":"8080","description":"192.168.1.123"}&encodemethod=NONE
		try {
//			Common.sendHttpGet("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do","json=" + "{\"houseIEEE\":\"4443455322222276\",\"name\":\"用户李\",\"longitude\":\"22\",\"latitude\":\"33\",\"networkAddress\":\"192.168.1.123\",\"port\":\"8081\",\"description\":\"192.168.1.123\"}");

//			Common.sendHttp("http://192.168.1.123:8080/zigBeeDevice/houseController/add.do", "json={\"houseIEEE\":\"4443455322222279\",\"name\":\"用户2\",\"longitude\":\"22\",\"latitude\":\"33\",\"networkAddress\":\"192.168.1.123\",\"port\":\"8080\",\"description\":\"192.168.1.123\"}&encodemethod=NONE");
//			Common.sendHttpPost("http://192.168.1.123:8080/zigBeeDevice/houseController/add.do", "json={\"houseIEEE\":\"4443455322222289\",\"name\":\"用户李2\",\"longitude\":\"22\",\"latitude\":\"33\",\"networkAddress\":\"192.168.1.123\",\"port\":\"8080\",\"description\":\"192.168.1.123\"}&encodemethod=NONE");
//			Common.sendHttp("http://192.168.1.123:8080/zigBeeDevice/houseController/update.do", "json={\"houseIEEE\":\"4443455322222276\",\"name\":\"用户李\",\"longitude\":\"22\",\"latitude\":\"33\",\"networkAddress\":\"192.168.1.123\",\"port\":\"8081\",\"description\":\"192.168.1.123\"}&encodemethod=NONE");
//			Common.sendHttpPost("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do", "json={\"houseIEEE\":\"4443455322222276\",\"name\":\"用户李\",\"longitude\":\"22\",\"latitude\":\"33\",\"networkAddress\":\"192.168.1.123\",\"port\":\"8081\",\"description\":\"192.168.1.123\"}&encodemethod=NONE&sign=AAA&callback=android");
//			Common.sendHttpPost("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do", "json={\"houseIEEE\":\"4443455322222276\",\"name\":\"用户李\",\"longitude\":\"22\",\"latitude\":\"33\",\"networkAddress\":\"192.168.1.123\",\"port\":\"8081\",\"description\":\"192.168.1.123\"}&encodemethod=NONE&sign=AAA&callback=android&houseIeee=a0137A0000008131");
//			Common.sendHttpGet("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do","json=" + URLEncoder.encode("{\"houseIEEE\":\"00137A0000007785\",\"name\":\"用户李\",\"longitude\":\"22\",\"latitude\":\"33\",\"networkAddress\":\"192.168.1.123\",\"port\":\"8081\",\"description\":\"192.168.1.123\"}","gbk") + "&encodemethod=NONE&sign=AAA&callback=android");
//			Common.sendHttpGet("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do","json=" + URLEncoder.encode("{\"houseIEEE\":\"00137A0000007785\",\"name\":\"用户李\",\"longitude\":\"22\",\"latitude\":\"33\",\"networkAddress\":\"192.168.1.123\",\"port\":\"8081\",\"description\":\"192.168.1.123\"}","gbk") + "&encodemethod=AES&sign=F5DEDE4F7913ABE934146736FE1CAD42066404E754C76B7DA44E4EAACEA4F2722F367E56BF3AD8E8790DCF09E266AE32C57C004849753FB2E61EA3F9CB758B6F990B5EFA8FBDAFA652B9F6FA2D34B78FB864496054248EA7D03A18FE29B209C045C3576675CB6FE3F2875C23D1416A8E552C6564295DF2A7677EF5D3133CB825A6CE4B83C0FA6D6CFB4AA1D3BBA30F98D018A272448FD7603E98A995076A3834F6001C8D96328A6CAA58876732AA5EDE3BD42C3A1F464AB18DBA441A651D2D71441233DB1548C893C69B8A9D0D4BE68D0CCB2439FC7B304A1065DFC5FDADA58845B5840DC502C1D1C77D81CF5AD3692A9B155993EE6D9D9EC6CC183CEB111D667B2730F3A8ECC9667C6C82638BA857FBA07A13CD6FF34C3FAFF38FAE0125824C&callback=android&houseIeeeSecret=00137A0000007785");
//			Common.sendHttpGet("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do","json=" + URLEncoder.encode("{\"houseIEEE\":\"00137A0000007785\",\"name\":\"用户2\"}","gbk") + "&encodemethod=AES&sign=F5DEDE4F7913ABE934146736FE1CAD42066404E754C76B7DA44E4EAACEA4F2722F367E56BF3AD8E8790DCF09E266AE32C57C004849753FB2E61EA3F9CB758B6FA2D74F4B9E23DEB655645572C54395ADCDFE0940193AB797A6C6E6376000FF21DA8045F8727DCE79E7B319B34DFD9ACD&callback=android&houseIeeeSecret=00137A0000007785");
//			Common.sendHttpGet("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do", URLEncoder.encode("json={\"houseIEEE\":\"00137A0000007785\",\"name\":\"用户2\"}&","gbk") + "encodemethod=AES&sign=F5DEDE4F7913ABE934146736FE1CAD42066404E754C76B7DA44E4EAACEA4F2722F367E56BF3AD8E8790DCF09E266AE32C57C004849753FB2E61EA3F9CB758B6FA2D74F4B9E23DEB655645572C54395ADCDFE0940193AB797A6C6E6376000FF21DA8045F8727DCE79E7B319B34DFD9ACD&callback=android&houseIeeeSecret=00137A0000007785");
//			Common.sendHttpGet("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do","json=" + "{\"houseIEEE\":\"00137A0000007785\",\"name\":\"用户2\"}" + "&encodemethod=NONE&sign=F5DEDE4F7913ABE934146736FE1CAD42066404E754C76B7DA44E4EAACEA4F2722F367E56BF3AD8E8790DCF09E266AE32C57C004849753FB2E61EA3F9CB758B6FA2D74F4B9E23DEB655645572C54395ADCDFE0940193AB797A6C6E6376000FF21DA8045F8727DCE79E7B319B34DFD9ACD&callback=android&houseIeeeSecret=00137A0000007785");
//			TestHttpclient.getUrl("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do?json=" + "{\"houseIEEE\":\"00137A0000007785\",\"name\":\"用户2\"}", "");
//			Common.sendHttpGet("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do?json=" + "{\"houseIEEE\":\"00137A0000007785\"}" + "&encodemethod=NONE&sign=AAA&callback=android");
//			Common.sendHttpGet("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do","json=" + URLEncoder.encode("{\"houseIEEE\":\"00137A0000007785\"}","gbk") + "&encodemethod=AES&sign=F5DEDE4F7913ABE934146736FE1CAD42066404E754C76B7DA44E4EAACEA4F2722F367E56BF3AD8E8790DCF09E266AE32E7C1CA30B5E1AA4FFC0832A88EA4F4B568EE4CA4A4E1372CAC7D861214F98C63&callback=android&houseIeeeSecret=00137A0000007785");
//			Common.sendHttpGet("http://192.168.1.123:8080/zigBeeDevice/roomController/add.do", "json={\"houseIEEE\":    \"00137A0000007785\",\"roomId\":   \"8\",\"roomName\":     \"公共浴室\",\"roomPic\": \"r_pbath.png\"}&callback=wifiRoute&sign=B00C009CD1B69E7B2CED93100D1ED0C62048D313688089B7E52F5432CD2279699FB7BD9BD6672374E3A5560290A3F42C44D225844B0F6E7621410A557C165EA4BF67DFC969137C969C5E50A5E5F70CA45C2671B46D946CEB5094CBCAB7296F4CADAADD133D6B62B339E506D52B2131DBF39982A7650D499E7AC4B22C2F99E32014795167780F788FB2965F9CD9CCC77D&encodemethod=AES&houseIeeeSecret=00137A0000007785");
//			Common.sendHttpGet("http://192.168.1.123:8080/zigBeeDevice/roomController/add.do", "json={\"houseIEEE\":    \"00137A0000007785\",\"roomId\":   \"8\",\"roomName\":     \"公共浴室\",\"roomPic\": \"r_pbath.png\"}&callback=wifiRoute&sign=B00C009CD1B69E7B2CED93100D1ED0C62048D313688089B7E52F5432CD2279699FB7BD9BD6672374E3A5560290A3F42C44D225844B0F6E7621410A557C165EA4BF67DFC969137C969C5E50A5E5F70CA45C2671B46D946CEB5094CBCAB7296F4CADAADD133D6B62B339E506D52B2131DBF39982A7650D499E7AC4B22C2F99E32014795167780F788FB2965F9CD9CCC77D&encodemethod=AES&houseIeeeSecret=00137A0000007785");
			/*Common.sendHttpPost("http://192.168.1.123:8080/zigBeeDevice/roomController/add.do", "json={"+
        "\"houseIEEE\":    \"00137A0000007785\"," +
        "\"roomId\":       \"10\","+
        "\"roomName\":     \"阳台\","+
        "\"roomPic\":      \"r_balcony.png\""+
"}&callback=wifiRoute&sign=B00C009CD1B69E7B2CED93100D1ED0C62048D313688089B7E52F5432CD2279699FB7BD9BD6672374E3A5560290A3F42CE31D220A8DC55B34919DBBD97C11229A84AE7E57DAC39ED01382830425DB75563AEA084E1D7D6156DC3B6932F87F99F776BFEB1DBA5309E2DC6C511A2454384DDB57B66A086D5D8DB7F489729F71C47D&encodemethod=AES&houseIeeeSecret=00137A0000007785");*/
//			AESCodec.password="0000007785NETVOX";
//			String strDecrypt=Httpproxy.urlCodec("B00C009CD1B69E7B2CED93100D1ED0C62048D313688089B7E52F5432CD2279699FB7BD9BD6672374E3A5560290A3F42CE31D220A8DC55B34919DBBD97C11229A84AE7E57DAC39ED01382830425DB75563AEA084E1D7D6156DC3B6932F87F99F776BFEB1DBA5309E2DC6C511A2454384DDB57B66A086D5D8DB7F489729F71C47D", "0000007785NETVOX");

			// ok
			Common.sendHttpGet("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do", "json=" + URLEncoder.encode("{\"houseIEEE\":\"00137A0000007785\",\"name\":\"用户2\"}","gbk") + "&encodemethod=AES&sign=F5DEDE4F7913ABE934146736FE1CAD42066404E754C76B7DA44E4EAACEA4F2722F367E56BF3AD8E8790DCF09E266AE32C57C004849753FB2E61EA3F9CB758B6FA2D74F4B9E23DEB655645572C54395ADCDFE0940193AB797A6C6E6376000FF21DA8045F8727DCE79E7B319B34DFD9ACD&callback=android&houseIeeeSecret=00137A0000007785");
			//			Common.sendHttpPost("http://192.168.1.123:8080/zigBeeDevice/roomController/add.do", "json={\"houseIEEE\":    \"00137A0000007785\",\"roomId\":   \"8\",\"roomName\":     \"公共浴室\",\"roomPic\": \"r_pbath.png\"}&callback=wifiRoute&sign=B00C009CD1B69E7B2CED93100D1ED0C62048D313688089B7E52F5432CD2279699FB7BD9BD6672374E3A5560290A3F42C44D225844B0F6E7621410A557C165EA4BF67DFC969137C969C5E50A5E5F70CA45C2671B46D946CEB5094CBCAB7296F4CADAADD133D6B62B339E506D52B2131DBF39982A7650D499E7AC4B22C2F99E32014795167780F788FB2965F9CD9CCC77D&encodemethod=AES&houseIeeeSecret=00137A0000007785");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
