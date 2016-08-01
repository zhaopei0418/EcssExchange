Ext.ns('App.login');
App.login.LoginWindow = Ext.extend(Ext.Viewport, {
	
	constructor: function(_config) {
		if (_config == null) {
			_config = {};
		}
		Ext.apply(this, _config);
		
		this.pWidth = 1024;
		this.pHeight = 800;
		
		if (Ext.isIE) {
			document.body.style.marginLeft = (document.body.offsetWidth - this.pWidth) / 2;
		}
		document.body.style.marginTop = (document.body.offsetHeight - this.pHeight + 120) / 2 < 0 ? 0: (document.body.offsetHeight - this.pHeight + 120) / 2;

		App.login.LoginWindow.superclass.constructor.call(this, _config);

		Ext.QuickTips.init();

		this.viewW = this.pWidth - 15;

		Ext.get("dddd").setWidth(this.viewW);

		this.viewH = this.pHeight;

		this.form = new Ext.FormPanel({
			renderTo: 'dddd',
			id: 'iCLoginPanel',
			border: false,
			layout: 'absolute',
			width: this.viewW,
			height: this.viewH - 120,
			margins: 0,
			paddding: 0,
			frame: false,
			html: "<img src='./commons/images/login.jpg' height=768px width=1024px/>",//图片按实际尺寸显示，避免模糊
			items: [{
				xtype: 'textfield',
				x: 412,
				y: 257,
				name: 'loginName',
				//value:'ent',
				//minLength: 2,
				maxLength: 30,
				maxLengthText: '用户名最大输入30个字符',
				width: 150,
				allowBlank: false,
				blankText: '用户名不能为空'
			},/**
				new ukey.formatTextField({
					size:8,
					x: 480,
					y: 360,
					id: 'passWord',
					name: 'passWord',
					inputType: 'password',
					//minLength: 3,
					maxLength: 8,
					maxLengthText: '密码最大输入8个字符',
					width: 110,
					allowBlank: false,
					blankText: '密码不能为空',
					anchor: '63%'
				}),**/
			{
				xtype: 'textfield',
				x: 412,
				y: 292,
				id: 'passWord',
				name: 'passWord',
				//value:'ent',
				inputType: 'password',
				//minLength: 3,
				maxLength: 20,
				maxLengthText: '密码最大输入20个字符',
				width: 150,
				allowBlank: false,
				blankText: '密码不能为空'
			},
			new Ext.ux.ImageButton({
				imgPath: './commons/images/login.png',
				x: 425,
				y: 342,
				imgWidth: 70,
				imgHeight: 26,
				tooltip: '登录',
				handler: iCLogin
			}), 
			new Ext.ux.ImageButton({
				imgPath: './commons/images/clear.png',
				x: 520,
				y: 342,
				imgWidth: 70,
				imgHeight: 26,
				tooltip: '重置',
				handler: function() {
					Ext.getCmp('iCLoginPanel').getForm().reset();
				}
			})],
			listeners: {
				'render': function(panel) {
					panel.body.on('keypress', function(e) {
						if (e.keyCode == 13)
							iCLogin();
					});
				}
			}
		});
		
		this.form.find('name', 'loginName')[0].focus(true, true);
	    //iCLogin(this.form);
		
	}
});

function Login(loginForm) {
	//var loginForm = Ext.getCmp('iCLoginPanel').getForm();
	if (loginForm.isValid()) {
		
		loginForm.submit({
			waitTitle: '提示',
			waitMsg: '正在登录......',
			
			url:'user/login',
			method: 'post',
			success: function(form, action) {
				if (action.result.success) {
					window.location.href = 'main.jsp';
				} else {
					Ext.Msg.alert('提示', '登录失败');
				}
			},
			failure: function(form, action) {
				Ext.Msg.alert('提示', action.result.info);
			}
		});
	}
	
}

function iCLogin() {
	var loginForm = Ext.getCmp('iCLoginPanel').getForm();
	var password = Ext.getCmp('passWord').getValue();
	if (loginForm.isValid()) {
		Login(loginForm);
	} 
	else {
		if (ukey.checkSCAClient()) {
			if (ukey.checkmanagepin(password)) {
				var icNo = ukey.checkgetinfoCardId();
				//alert("icNo: " + icNo);
				Ext.Ajax.request({
							url : 'user/loginByIcNo?icNo=' + icNo,
							// params: { icNo: icNo},
							method : 'post',
							success : function(response, action) {
								var json = Ext.decode(response.responseText);
								if (json.success) {
									window.location.href = 'main.jsp';
								} else {
									Ext.Msg.alert('提示', json.info);
								}
							},
							failure : function(json, action) {
								Ext.Msg.alert('提示', 'Ukey(卡)登录失败');
							}
						});
			}
//			else{
//				alert('Ukey(卡)密码错误！');
//			}
		}
	}
}
