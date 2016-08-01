Ext.namespace('Ext.Syis.lib');

Ext.Syis.lib.SyisWin = Ext.extend(Ext.Window, {
	loadByField : null,
	detailPanel : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.apply(this, _cfg);
		
		Ext.QuickTips.init();
		
		Ext.Syis.lib.SyisWin.superclass.constructor.call(this, {
			id : 'syisbillWin',
			items : this.detailPanel,
			scope : this,
			iconCls : 'btn-form',
			title : '单据详情',
			closable : true,
			resizable : false,
			draggable : false,
			renderTo : 'syisbillQuery',
			width : Ext.getCmp('frmPanel').getInnerWidth(),
			height : Ext.getCmp('frmPanel').getInnerHeight()
		});
		
		this.detailPanel.loadDetail(this.loadByField);
	}
});

Ext.namespace('Ext.Syis.lib');
Ext.Syis.lib.ParaStore = Ext.extend(Ext.data.JsonStore, {
	paraName : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.apply(this, _cfg);
		Ext.QuickTips.init();
		
		Ext.Syis.lib.ParaStore.superclass.constructor.call(this, {
			autoLoad: false,
			url : 'parameter/queryParametes',
			baseParams : {paraName : this.paraName},
			root : 'data',
			fields : ['code', 'name']
		});
	}
});

// combo下拉筛选
var comboBeforeQuery = function(e){
	try {
		var combo = e.combo;
		if (!e.forceAll) {
			var value = e.query;
			if (value != null && value != '') {
				combo.store.reload({
					callback: function(){
						combo.store.filterBy(function(rec, id){
							var valueField = rec.get(combo.valueField);
							var displayField = rec.get(combo.displayField);
							var valueFound = false;
							var nameFound = false;
							
							if (valueField) {
								valueFound = (valueField.indexOf(value) != -1);
							}
							if (displayField) {
								nameFound = (displayField.indexOf(value) != -1);
							}
							return valueFound || nameFound;
						});
					}
				});
			}
			else {
				combo.store.clearFilter();
			}
			combo.onLoad();
			combo.expand();
			return false;
		}
		else {
			combo.store.clearFilter();
		}
	}
	catch(e){}
};

var starMark = '<font color="red">*</font>';

var chooseLineAlert = function(){
	Ext.MessageBox.show({
		title: '警告',
		msg: '请选择一条记录!',
		icon: Ext.MessageBox.WARNING,
		buttons: Ext.MessageBox.OK
	});
};

