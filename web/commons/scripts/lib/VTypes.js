// 正则表达式的定义
var integerTest = /^[0-9]*$/i;
var decimalTest = /^[0-9]+(.[0-9]*)?$/i;
var customsCodeTest = /^[0-9]{4}$/;
var codeTest = /^[0-9a-zA-Z-_. ]*$/;
var codeUnderlineTest = /^[0-9a-zA-Z-_]*$/;
var idNumberTest = /^[0-9a-zA-Z-_. ~@#$%^&*()+=|\/?><,]*$/;
var phoneTest = /^[0-9-]*$/;
var taxTest = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;

var d6dot2Test = /^(\d{0,6}$)|^(\d{0,6}\.\d{1,2})$/;
var d8dot2Test = /^(\d{0,8}$)|^(\d{0,8}\.\d{1,2})$/;
var d15dot4Test = /^(\d{0,15}$)|^(\d{0,15}\.\d{1,4})$/;
var d14dot5Test = /^(\d{0,14}$)|^(\d{0,14}\.\d{1,5})$/;
var d14dot2Test = /^(\d{0,14}$)|^(\d{0,14}\.\d{1,2})$/;

// vType的定义
Ext.apply(Ext.form.VTypes,{
	integer : function(val, field){
		return integerTest.test(val);
	},
	integerText: '只能输入正整数!',
    integerMask: /^[0-9]$/i
});

Ext.apply(Ext.form.VTypes,{
    decimal : function(val, field){
		return decimalTest.test(val);
	},
	decimalText: '只能输入正实数!',
    decimalMask: /[\d%\.]/i
});

Ext.apply(Ext.form.VTypes,{
    customsCode : function(val, field){
		return customsCodeTest.test(val);
	},
	customsCodeText: '只能输入四位数字海关编码!',
    customsCodeMask: /[0-9]/i
});

Ext.apply(Ext.form.VTypes,{
    code : function(val, field){
		return codeTest.test(val);
	},
	codeText: '只能输入字母或数字!'
});

Ext.apply(Ext.form.VTypes,{
    codeUnderline : function(val, field){
		return codeUnderlineTest.test(val);
	},
	codeUnderlineText: '只能输入字母或数字和下划线!'
});

Ext.apply(Ext.form.VTypes,{
    idNumber : function(val, field){
		return idNumberTest.test(val);
	},
	idNumberText: '请输入正确的身份证号!'
});


Ext.apply(Ext.form.VTypes,{
    phone : function(val, field){
		return phoneTest.test(val);
	},
	phoneText: '请输入正确的电话号码!'
});

Ext.apply(Ext.form.VTypes,{
    tax : function(val, field){
		return taxTest.test(val);
	},
	taxText: '请输入正确的传真号码!'
});

Ext.apply(Ext.form.VTypes,{
    d6dot2 : function(val, field){
		return d6dot2Test.test(val);
	},
	d6dot2Text: '整数不能超过6位，小数不能超过2位'
});

Ext.apply(Ext.form.VTypes,{
    d8dot2 : function(val, field){
		return d8dot2Test.test(val);
	},
	d8dot2Text: '整数不能超过8位，小数不能超过2位'
});

Ext.apply(Ext.form.VTypes,{
    d15dot4 : function(val, field){
		return d15dot4Test.test(val);
	},
	d15dot4Text: '整数不能超过15位，小数不能超过4位'
});

Ext.apply(Ext.form.VTypes,{
    d14dot5 : function(val, field){
		return d14dot5Test.test(val);
	},
	d14dot5Text: '整数不能超过14位，小数不能超过5位'
});

Ext.apply(Ext.form.VTypes,{
    d14dot2 : function(val, field){
		return d14dot2Test.test(val);
	},
	d14dot2Text: '整数不能超过14位，小数不能超过2位'
});
