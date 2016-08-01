Ext.apply(Ext.form.VTypes,{
	password :function(val,field){
	   if(field.initialPassField){
		   var pwd = Ext.getCmp(field.initialPassField);
		   return (val == pwd.getValue());
	   }
	   return true;
	},
	passwordText:'两次输入密码不一致'
})