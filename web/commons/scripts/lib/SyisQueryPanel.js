Ext.namespace('Ext.Syis.lib');

// 列表查询界面
Ext.Syis.lib.SyisQueryPanel = Ext.extend(Ext.Panel, {
	queryConditionTitle: null,
	conditionItems: null,
	gridTbar: null,
	gridColumn: null,
	gridStore: null,
	hyperLinkData: null,
	//queryFormHeight: null,
	doQueryFunc: null,
	selModel: null,
	selNodes: null,
	pageSize: null,
	isPaged: true,
	forceFit: true,
	constructor: function(_config) {
		Ext.QuickTips.init();
		
		if (_config == null) {
			_config = {};
		};
		Ext.apply(this, _config);
		
		var me = this;

		this.query_form = new Ext.form.FormPanel({
			labelAlign: 'right',
			labelWidth: 80,
			frame: true,
			labelSeparator: '：',
			border: false,
			height: 75 + 30 * me.conditionItems.length,//1行105, 2行135, 3行165...
			title: me.queryConditionTitle,
			region: 'north',	
			iconCls: 'btn-form',
			bodyStyle: 'padding: 10px;',
	        items: me.conditionItems,
			buttons: [{
				text: '查询', iconCls: 'btn-search', 
				handler: me.doQueryFunc != null ? function(){
					me.doQueryFunc();
				} : function(){
					me.doQuery(me.query_form.getForm().getValues());
				}
			}, {
				text: '重置', iconCls: 'btn-reset', 
				handler: function(){
					me.resetCondition();
				}
			}]		
		});
		
		me.selectionModel = this.selModel != null ? this.selModel : new Ext.grid.RowSelectionModel({
			singleSelect: true
		});
		
		// 给某列加超链接
		if(me.hyperLinkData instanceof Array){
			for(var j = 0; j < me.hyperLinkData.length; j++){
				for(var i = 0; i < me.gridColumn.length; i++){
					if(me.gridColumn[i].dataIndex == me.hyperLinkData[j]){
						me.gridColumn[i].renderer = function (value, metaData, record, rowIndex, colIndex, store){
							return String.format('<a class="cellClickContent" style="color:blue" href="javascript:void(0)">{0}</a>', (value) ? value: '暂无');
						};
					}
				}
			}
		} else {
			for(var i = 0; i < me.gridColumn.length; i++){
				if(me.gridColumn[i].dataIndex == me.hyperLinkData){
					me.gridColumn[i].renderer = function (value, metaData, record, rowIndex, colIndex, store){
						return String.format('<a class="cellClickContent" style="color:blue" href="javascript:void(0)">{0}</a>', (value) ? value: '暂无');
					};
				}
			}		
		}

		if(me.isPaged){
			var bbar = new Ext.PagingToolbar({
				pageSize: this.pageSize != null ? this.pageSize : 15,
				store: me.gridStore,
				displayInfo: true,
				displayMsg: '显示第 {0} - {1} 条记录, 共 {2} 条',
				emptyMsg: "没有可显示的记录",
				beforePageText: '第',
				afterPageText: '页, 共{0}页',
				listeners: {
					beforerender: function(ptb){
						ptb.first.iconCls = 'page-first';
						ptb.prev.iconCls = 'page-prev';
						ptb.next.iconCls = 'page-next';
						ptb.last.iconCls = 'page-last';
						ptb.refresh.iconCls = 'page-refresh';
					},
					beforechange: function(sender, params){
						if (Ext.isFunction(me.beforePageChange)){
							return me.beforePageChange(sender, params);
						}
					}
				}
			});
		}
		
		this.query_grid = new Ext.grid.GridPanel({
			title: '查询结果',
			sm: this.selectionModel,
			cm: new Ext.grid.ColumnModel(me.gridColumn),
			iconCls: 'btn-form',
			stripeRows: true,//与行变色冲突
			region: 'center',
			border: false,
			frame: true,
			store: me.gridStore,
			tbar: me.gridTbar,
			bbar: bbar,
			viewConfig: {
				scrollOffset: -3,// scrollOffset: -15, 去掉右侧空白区域 具体数值可能需要调整
				forceFit: me.forceFit,
				templates : {
					cell :  new Ext.Template(
						'<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>',
						'<div class="x-grid3-cell-inner x-grid-col-{id}" {attr}>{value}</div>',
						'</td>'
					)
				}
			},
			loadMask: {msg: '正在加载...'},
			listeners: {
				scope: this,
				'cellclick': function(sender, rowIndex, columnIndex, e) {
					if (e.target.className == 'cellClickContent'){
						me.view(columnIndex);
					}
				}
			}
		});
		
		Ext.Syis.lib.SyisQueryPanel.superclass.constructor.call(this, {
			id: 'syisbillQuery',
			layout: 'border',
			items: [this.query_form, this.query_grid],
			height: Ext.getCmp('frmPanel').getInnerHeight()
		});
		
	},
	
	// 查询
	doQuery: function(params){
		if(this.query_form.getForm().isValid()){
			var store = this.query_grid.getStore();
			store.baseParams = params ? params: '';
			this.query();
		}
	},
	
	query: function() {
		this.query_grid.getStore().load({
			params: {
				start: 0,
				limit: this.pageSize != null ? this.pageSize : 15
			}
		});
	},
	
	// 查询条件重置
	resetCondition: function(){
		this.query_form.getForm().reset();
	},
	
	// 修改车辆编号
	syisModifyVe: function(modifyVeUrl, type){
		var me = this;
		var record = me.query_grid.getSelectionModel().getSelected();
		if(!record){
			Ext.Msg.alert('提示', '请选择一条数据', function(){
				return;
			});
		}
 		new Ext.Syis.lib.SyisModifyVeWin({
 			type : type,
 			modifyVeUrl: modifyVeUrl,
			record: record
		}).show();
	},
		
	// 出库确认
	syisConfirmBill : function(confirmBillUrl){
		var me = this;
		var selected = me.query_grid.getSelectionModel().getSelected();
		
		if (!selected) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return false;
		}
		
		var storeStatus = selected.get('storeStatus');
		if (storeStatus == '已出库') {
			Ext.Msg.alert('提示', '此记录已出库,不能再次已出库');
			return false;
		}
		
		new Ext.Syis.lib.SyisConfirmBillWin({
 			confirmBillUrl : confirmBillUrl,
			record : selected
		}).show();
	},
	
	beforePageChange: function(sender, params){
		var store = this.query_grid.getStore();
		
		var params = this.query_form.getForm().getValues();
		store.baseParams = params;
		store.baseParams.start = 0;
		store.baseParams.limit = this.pageSize != null ? this.pageSize : 15;
		return true;
	}
	
});
