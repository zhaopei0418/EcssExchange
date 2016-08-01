Ext.namespace('Ext.Syis.lib');

Ext.Syis.lib.SyisModifyVeWin = Ext.extend(Ext.Window, {
	type : null,
	record : null,
	modifyVeUrl : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.apply(this, _cfg);
		
		Ext.QuickTips.init();
		
		var me = this;
		var fieldPlugins = [new Ext.ux.FieldStylePlugin()];
		
		var items = null;
		if(me.type == 'inbill'){
			items = [
        		{xtype : 'textfield', fieldLabel : '入库单号', name : 'billNo', width : 150, value : me.record.data.billNo, readOnly : true, plugins: fieldPlugins},
        		{xtype : 'textfield', fieldLabel : '原车辆编号', name : 'veNo', width : 150, value : me.record.data.veNo, readOnly : true, plugins: fieldPlugins},
        		{xtype : 'textfield', fieldLabel : '新车辆编号', name : 'veNo2', width : 150}
	        ]
		} else {
			items = [
        		{xtype : 'textfield', fieldLabel : '出库单号', name : 'outBillNo', width : 150, value : me.record.data.outBillNo, readOnly : true, plugins: fieldPlugins},
        		{xtype : 'textfield', fieldLabel : '原车辆编号', name : 'veNo', width : 150, value : me.record.data.veNo, readOnly : true, plugins: fieldPlugins},
        		{xtype : 'textfield', fieldLabel : '新车辆编号', name : 'veNo2', width : 150}
	        ]
		}
		
		me.syisModifyVePanel = new Ext.form.FormPanel({
			labelAlign : 'right',
			buttonAlign : 'center',
			labelWidth : 120,
			labelSeparator : '：',
			border : false,
			frame : true,
            defaults : {
				baseCls : 'x-plain'
			},
	        items : items,
	        buttons : [
	        	{text : '确定', iconCls : 'btn-ok', handler : function(){
	        		me.modifyVe();
				}},
				{text : '取消', iconCls : 'btn-cancel', handler : function(){
					me.closeWin();
				}}
	        ]
		});

		Ext.Syis.lib.SyisModifyVeWin.superclass.constructor.call(this, {
			id : 'syisModifyVeWin',
			title : '修改车辆编号',
			iconCls : 'btn-form',
			width : 350,
			modal : true,
			closable : true,
			resizable : false,
			items : me.syisModifyVePanel
		});
		
	},
	
	modifyVe : function(){
		var me = this;
		var params = null;
		if(me.type == 'inbill'){
			params = {
				'id' : me.record.data.id,
				'billNo' : me.syisModifyVePanel.find('name','billNo')[0].getValue(),
				'veNo' : me.syisModifyVePanel.find('name','veNo2')[0].getValue()
			}
		} else {
			params = {
				'id' : me.record.data.id,
				'outBillNo' : me.syisModifyVePanel.find('name','outBillNo')[0].getValue(),
				'veNo' : me.syisModifyVePanel.find('name','veNo2')[0].getValue()
			}
		}
		Ext.Ajax.request({
			url : me.modifyVeUrl,
			params : params,
			success : function(response, opts){
				me.closeWin();
				Ext.getCmp('syisbillQuery').query_grid.getStore().reload();
			},
			failure : function(response, opts){
				
			}
		});
	},
	
	closeWin : function(){
		Ext.getCmp('syisModifyVeWin').close();
	}
	
});
