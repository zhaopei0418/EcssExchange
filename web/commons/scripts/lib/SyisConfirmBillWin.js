Ext.namespace('Ext.Syis.lib');

Ext.Syis.lib.SyisConfirmBillWin = Ext.extend(Ext.Window, {
	record : null,
	confirmBillUrl : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.apply(this, _cfg);
		
		Ext.QuickTips.init();
		
		var me = this;
		var fieldPlugins = [new Ext.ux.FieldStylePlugin()];
		
		var syisConfirmBillPanel = new Ext.form.FormPanel({
			labelAlign : 'right',
			buttonAlign : 'center',
			width : 350,
			border : false,
			frame : true,
	        items : [
	        	{html : '', height : 10},
	        	{xtype : 'panel', html : '<font size="2">您确定要对入库单号：' + me.record.get('billNo') + '进行出库确认吗？</font>'},
	        	{html : '', height : 10}
	        ],
	        buttons : [
	        	{text : '确定', iconCls : 'btn-ok', handler : function(){
	        		me.confirmBill();
				}},
				{text : '取消', iconCls : 'btn-cancel', handler : function(){
					me.closeWin();
				}}
	        ]
		});

		Ext.Syis.lib.SyisConfirmBillWin.superclass.constructor.call(this, {
			id : 'syisConfirmBillWin',
			title : '出库确认',
			iconCls : 'btn-form',
			width : 350,
			modal : true,
			closable : true,
			resizable : false,
			items : syisConfirmBillPanel
		});
		
	},
	
	confirmBill : function(){
		var me = this;
		Ext.Ajax.request({
			url : me.confirmBillUrl,
			params : {
				'billNo' : me.record.get('billNo')
			},
			success : function(response, opts){
				me.closeWin();
				var response = Ext.decode(response.responseText);
				if (response.success) {
					Ext.Msg.alert('提示', '标记出库成功');
					Ext.getCmp('syisbillQuery').query_grid.getStore().reload();
				} else {
					Ext.Msg.alert('提示', response.error ? response.error : '标记出库失败');
				}
			},
			failure : function(response, opts){
				
			}
		});
	},
	
	closeWin : function(){
		Ext.getCmp('syisConfirmBillWin').close();
	}
	
});
