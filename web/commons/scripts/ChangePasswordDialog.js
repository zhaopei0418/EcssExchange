Ext.ns("App");

App.ChangePasswordDialog = function() {

	var fieldPlugins = [new Ext.ux.FieldStylePlugin()];
	
    return {

        getForm: function() {
            var frm = new Ext.form.FormPanel({
                url:'user/changePassword',
                labelAlign: 'left',
                buttonAlign: 'center',
                bodyStyle: 'padding:10px 0 0 30px;',
                frame: true,
                labelWidth: 80,
                defaultType: 'textfield',
                defaults: {
                    inputType: 'password',
                    allowBlank: false,
                    anchor: '90%',
                    enableKeyEvents: true
                },
                items: [{
                    name: 'oldPassword',
                    fieldLabel: '旧密码',
					maxLength:256,
					allowBlank : false, plugins : fieldPlugins,
                    listeners: {
                        scope: this,
                        keypress: function(field, e) {
                            if (e.getKey() == 13) {
                                var obj = this.frm.form.findField("newPassword");
                                if (obj) obj.focus();
                            }
                        },
                        blur: function(field){
                        	field.clearInvalid();
                        }
                    }
                },
                {
                    name: 'newPassword',
                    fieldLabel: '新密码',
                    id: "newPassword",
                    maxLength: 256,
					allowBlank : false, plugins : fieldPlugins,
                    listeners: {
                        scope: this,
                        keypress: function(field, e) {
                            if (e.getKey() == 13) {
                                var obj = this.frm.form.findField("confirmPassword");
                                if (obj) obj.focus();
                            }
                        },
                        blur: function(field){
                        	field.clearInvalid();
                        }
                    }
                },
                {
                    name: 'confirmPassword',
                    fieldLabel: '确认密码',
                    vtype: 'password',
                    maxLength:256,
                    initialPassField: 'newPassword',
					allowBlank : false, plugins : fieldPlugins,
                    listeners: {
                        scope: this,
                        keypress: function(field, e) {
                            if (e.getKey() == 13) {
                                this.submit();
                            }
                        },
                        blur: function(field){
                        	field.clearInvalid();
                        }
                    }
                }],
                buttons: [{
                    text: '确定',
                    scope: this,
                    iconCls : 'btn-ok',
                    handler: function() {
					var oldPassword = this.frm.form.findField("oldPassword").getValue();
					var newPassword = this.frm.form.findField("newPassword").getValue();
					var confirmPassword = this.frm.form.findField("confirmPassword").getValue();
						if(!ukey.checkmanagepin(oldPassword)){
							return;
						}
						if(newPassword.length!=8){
							alert("key的密码必须为8位！");
							return;
						}
						if(newPassword!=confirmPassword){
							alert("两次密码输入不一致");
							return;
						}
						if (ukey.changekmanagepin(oldPassword, newPassword)) {
							this.submit();
						}
                    }
                },
                {
                    text: '重置',
                    scope: this,
                    iconCls : 'btn-reset',
                    handler: function() {
                        this.frm.form.reset()
                    }
                }]
            });
            return frm;
        },

        getDialog: function() {
            this.frm = this.getForm();
            var dlg = new Ext.Window({
                width: 400,
                height: 170,
                title: '修改密码',
                plain: true,
                //closable: true,
                resizable: false,
                frame: true,
                layout: 'fit',
                closeAction: 'hide',
                border: false,
                modal: true,
                items: [this.frm]
            });
            return dlg;
        },

        submit: function() {
            if (this.frm.form.isValid()) {
               this.frm.form.submit({
                    waitTitle: '修改密码',
                    waitMsg: '修改密码中......',
                    scope: this,
                    method: 'post',
                    params: "",
                    success: function(form, action) {
                        var info = action.result.info;
                        this.dlg.hide();
                        Ext.Msg.alert('信息', info);
                    },
                    failure: function(form, action) {
                       var info = action.result.info;
	    			   Ext.Msg.alert('提示', info);
                    }
                });
            }
        },

        show: function() {
            if (!this.dlg) {
                this.dlg = this.getDialog();
            }
            this.dlg.show();
        }

    };
} ();
