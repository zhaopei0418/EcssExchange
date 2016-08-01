Ext.ns('Ext.app.ukey');
ukey = Ext.app.ukey;

//测试算法，包括pem编解码，sha1
ukey.checkalgthm =  function(){ 
	var strpropt;
	var strTemp1, strTemp2;
	
	//PEM编解码------------------------------------------------------------------------------------------------------------------
	//PEM编码
	SCAClient.strInputData ="This is Data For Pem";
	SCAClient.EncodePEM();
	if (SCAClient.nResult!= 0)
 	{
		strpropt = "EncodePEM errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
		alert(strpropt);
		return;
 	}
 	strpropt = "EncodePEM Succeed!The Encoded Data is " + SCAClient.strOutputData;
 	alert(strpropt);
 	
 	
 	//PEM解码-----------------------------------------------------------------------------------------------------------
 	SCAClient.strInputData = SCAClient.strOutputData;
 	SCAClient.DecodePEM();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "DecodePEM errocode is "+ SCAClient.nResult + "errreason is" + SCAClient.strResult;
		alert(strpropt);
		return;
 	}
 	if (SCAClient.strOutputData != "This is Data For Pem")
 	{
 		return;
 	}
 	strpropt = "DecodePEM Succeed!The Encoded Data is "+ SCAClient.strOutputData;
	alert(strpropt);
	
	//SHA1---------------------------------------------------------------------------------------------------------------
	SCAClient.strInputData = "对这段中文字符串进行运算";
	SCAClient.SHA1Digest();
	if (SCAClient.nResult!= 0)
 	{
		strpropt = "SHA1Digest errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
		alert(strpropt);
		return;
 	}
 	strpropt = "SHA1Digest Succeed!The Encoded Data is "+ SCAClient.strOutputData;
	alert(strpropt);
}

//获得设备卡号
ukey.checkgetinfoCardId = function()
{
	var strpropt;
	//打开设备
 	SCAClient.InitEnv();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "InitEnv errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
		alert(strpropt);
		return;
 	}
 	//alert("Init Succeed!");
 	
 	//获得卡号	
	SCAClient.GetCardID();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "GetCardID errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
		alert(strpropt);
		SCAClient.CloseEnv();
		return;
 	}
 	strpropt = SCAClient.strOutputData;
 // alert(strpropt);
// 	//获得签名证书号
// 	SCAClient.GetCertNo();
// 	if (SCAClient.nResult!= 0)
// 	{
//		strpropt = "GetCertNo errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
//		alert(strpropt);
//		SCAClient.CloseEnv();
//		return;
// 	}
// 	strpropt = "GetCertNo Succeed  " + SCAClient.strOutputData;
// 	alert(strpropt);
 	
 	SCAClient.CloseEnv();
 	return strpropt;
}

//获得设备签名证书号
ukey.checkgetinfoSign = function()
{
	var strpropt;
	//打开设备
 	SCAClient.InitEnv();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "InitEnv errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
		alert(strpropt);
		return;
 	}
 	//alert("Init Succeed!");
 	
// 	//获得卡号	
//	SCAClient.GetCardID();
// 	if (SCAClient.nResult!= 0)
// 	{
//		strpropt = "GetCardID errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
//		alert(strpropt);
//		SCAClient.CloseEnv();
//		return;
// 	}
// 	strpropt = "GetCardID Succeed " + SCAClient.strOutputData;
// 	alert(strpropt);
 	//获得签名证书号
 	SCAClient.GetCertNo();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "GetCertNo errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
		alert(strpropt);
		SCAClient.CloseEnv();
		return;
 	}
 	strpropt = SCAClient.strOutputData;
 	//alert(strpropt);	
 	
 	SCAClient.CloseEnv();
 	return strpropt;
}

//测试是否插入Ukey
ukey.checkUkey = function ()
{
	var strpropt;
 	//打开设备
 	SCAClient.InitEnv();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "InitEnv errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
		//alert(strpropt);
		return false;
 	}
 	//alert("读取设备成功!");
 	SCAClient.CloseEnv();
	
 	return true;	 	
}

//测试是否IE浏览器 以及是否安装驱动程序
ukey.checkSCAClient = function() {
	var strHtml = "<br><font color='#FF0000'>UKey只支持IE，要使用Ukey登陆请使用IE浏览器。</font>";
	var strHtml1 = "<br><font color='#FF0000'>UKey驱动未安装!点击这里<a href='"
			+ __ctxPath
			+ "/commons/plugin/usercard_cert.exe'>执行安装</a>,安装后请刷新页面或重新进入。</font>";

	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
			.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
			.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
			.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
			.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
	if (Sys.ie) {// Js判断为IE浏览器
		try{
			if(document.all.SCAClient.object==null){
				Ext.Msg.alert('提示', strHtml1);
			} else {
				return true;
			}
		} catch(err) {
			Ext.Msg.alert('提示', err);
		}
		
	} else {
		Ext.Msg.alert('提示', strHtml);
	}
	return false;
}

//测试口令是否正确
ukey.checkmanagepin = function (password)
{
	var strpropt;
 	//打开设备
 	SCAClient.InitEnv();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "InitEnv errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
		alert(strpropt);
		return;
 	}
 	//alert("Init Succeed!");
 	
 	//验证PIN
 	SCAClient.strPin = password;
 	SCAClient.VerifyPin();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "VerifyPin errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;;
		alert(strpropt);
		//alert('登陆密码错误！');
		SCAClient.CloseEnv();
		return false;
 	}
 	//alert("VerifyPin Succeed!");
 	SCAClient.CloseEnv();
 	
 	return true;	
 	
}

//测试口令管理
ukey.changekmanagepin = function (oldpassword,newpassword)
{
	var strpropt;
 	//打开设备
 	SCAClient.InitEnv();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "InitEnv errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
		alert(strpropt);
		return;
 	}
 	//alert("Init Succeed!");

 	 	
 	//将PIN修改成默认PIN，方便循环测试
 	SCAClient.strPin = oldpassword;
 	SCAClient.strNewPin = newpassword;
 	SCAClient.ChangePin();
 	if (SCAClient.nResult != 0)
 	{
 		strpropt = "ChangePin errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
		alert(strpropt);
		SCAClient.CloseEnv();
		return false;	
 	}
 	alert("ChangPin Succeed!");
 	
 	SCAClient.CloseEnv();
 	return true;
}

//测试签名数据
ukey.checksigndata = function()
{
	//比较数据SHA1Digest, SignHashData和数据直接SignPlainData结果是否相同
	var strpropt;
	var strTemp1 ;
	var strTemp2;
	
	SCAClient.strInputData = "对这段数据进行运算";
	SCAClient.SHA1Digest();
	if (SCAClient.nResult!= 0)
 	{
		strpropt = "SHA1Digest errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
		alert(strpropt);
		return;
 	}
 	strpropt = "SHA1Digest Succeed!The Encoded Data is "+ SCAClient.strOutputData;
	alert(strpropt);
	
	
	//打开设备
 	SCAClient.InitEnv();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "InitEnv errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;
		alert(strpropt);
		return;
 	}
 	alert("Init Succeed!");
 	
 	SCAClient.strPin = "88888888";
 	SCAClient.VerifyPin();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "VerifyPin errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;;
		alert(strpropt);
		SCAClient.CloseEnv();
		return;
 	}
 	alert("VerifyPin Succeed!");
 	
	
 	//对hash数据进行签名
 	strTemp1 = SCAClient.strOutputData;
 	SCAClient.strInputData = strTemp1;
 	SCAClient.SignHashData();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "SignHashData errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;;
		alert(strpropt);
		SCAClient.CloseEnv();
		return;
 	}
 	alert("SignHashData Succeed!");
 	strTemp1 = SCAClient.strOutputData;
 	
 	//对原文数据进行签名
 	SCAClient.strInputData = "对这段数据进行运算";
 	SCAClient.SignPlainData();
 	if (SCAClient.nResult!= 0)
 	{
		strpropt = "SignPlainData errocode is " + SCAClient.nResult + "errreason is" + SCAClient.strResult;;
		alert(strpropt);
		SCAClient.CloseEnv();
		return;
 	}
 	alert("SignPlainData Succeed!");
 	strTemp2 = SCAClient.strOutputData;
 	
 	if (strTemp1 != strTemp2)
 	{
 		alert("SignHashData and SignPlainData error!");
 		return;
 	}
 	else
 	{
	 	alert("SignPlainData Succeed!");
	 	alert(strTemp2);
 	}
	
}
//只能输入八位的TextField
ukey.formatTextField = Ext.extend(Ext.form.TextField, {
			constructor : function(_config) {

				if (_config == null) {
					_config = {};
				};
				Ext.apply(this, _config);
				ukey.formatTextField.superclass.constructor.call(this, {
							size : 8,
							initValue : function() {
								if (this.value !== undefined) {
									this.setValue(this.value);
								} else if (this.el.dom.value.length > 0) {
									this.setValue(this.el.dom.value);
								}
								this.el.dom.size = this.size;
								if (!isNaN(this.maxLength)
										&& (this.maxLength * 1) > 0
										&& (this.maxLength != Number.MAX_VALUE)) {
									this.el.dom.maxLength = this.maxLength * 1;
								}
							}
						});
			}

		});