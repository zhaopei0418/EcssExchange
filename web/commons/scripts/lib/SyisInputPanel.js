Ext.namespace('Ext.Syis.lib');

Ext.Syis.lib.SyisInputPanel = Ext.extend(Ext.Panel, {
	keyField : null,
	headFormTitle : null,
	headFormTbar : null,
	headItems : null,
	listFormTitle : null,
	listItems : null,
	listGridTitle : null,
	gridColumnModel : null,
	gridStore : null,
	formHeight : null,
	constructor : function(_config) {
		Ext.QuickTips.init();
		
		if (_config == null) {
			_config = {};
		};
		Ext.apply(this, _config);
		
		var me = this;
		
		// 表头信息
		this.head_form = new Ext.form.FormPanel({
			labelAlign : 'right',
			labelWidth : 80,
			frame : true,
			labelSeparator : '：',
			border : false,
			title : me.headFormTitle ? me.headFormTitle : '表头信息',
			autoHeight : true,
			iconCls :'btn-form',
			style : 'border-bottom:1px solid #99BBE8;',
			bodyStyle : 'padding:5px 0px 0px 10px;background-color:#DFE8F6',
            defaults : {
				baseCls : 'x-plain'
			},
	        items : me.headItems
		});
		
		// 表体信息
		this.list_form = new Ext.form.FormPanel({
			labelAlign : 'right',
			labelWidth : 80,
			frame : true,
			labelSeparator : '：',
			border : false,
			title : me.listFormTitle ? me.listFormTitle : '货物信息',
			autoHeight : true,
			iconCls : 'btn-form',
			style : 'border-bottom:1px solid #99BBE8;',
			bodyStyle : 'padding:5px 0px 0px 10px;background-color:#DFE8F6',
            defaults : {
				baseCls : 'x-plain'
			},
	        items : me.listItems	
		});
		
		this.head_panel = new Ext.Panel({
			region : 'north',
			header : false,
			border : false,
			layout : 'fit',
			height : me.formHeight ? me.formHeight : (Ext.isIE ? 400 : 370),
			tbar: me.headFormTbar,
		    items : [this.head_form, this.list_form]
		});
		
		me.selectionModel = new Ext.grid.RowSelectionModel({
			singleSelect : true,
			listeners : {
				rowselect : function(sm, rowIndex, record){
			      	me.list_form.getForm().loadRecord(record);
				}
			}
		});
		
		// 表体列表
		this.list_grid = new Ext.grid.GridPanel({
			sm : me.selectionModel,
			cm : me.gridColumnModel,
			iconCls : 'btn-form',
			stripeRows : true,
			region : 'center',
			border : false,
			autoScroll: true,
			viewConfig : {
				scrollOffset : -3,
				forceFit : true
			},
			store : me.gridStore,
			loadMask : {
				msg : '正在加载...'
			}
		});
		
		Ext.Syis.lib.SyisInputPanel.superclass.constructor.call(this, {
			layout : 'border',
			items : [
				this.head_panel,
				this.list_grid
			],
			height: Ext.getCmp('frmPanel').getInnerHeight() - (this.win != null ? 32 : 0)
		});
		
		this.initHandler();
		
	},
	
	// 得到表头form
	getHeadForm : function(){
		return this.head_form;
	},
	
	// 得到表体form
	getListForm : function(){
		return this.list_form;
	},
	
	// 给列表添加删除数据功能
	gridAddListener : function(){
		var me = this;
		this.list_grid.addListener('rowcontextmenu', function(grid, index, e){
			e.preventDefault();
			if(index < 0) {
				return;
			}
			var rightMenu = new Ext.menu.Menu([{
				text : '删除',
				pressed : true,
				handler : function(){
			        var record = grid.getStore().getAt(index);
			        grid.getStore().remove(record);
			        for(var j = 0; j < grid.getStore().getCount(); j++){
			        	grid.getStore().getAt(j).set('index', j);
			        }
			        me.list_form.getForm().reset();
				}
			}]);
			rightMenu.showAt(e.getPoint());
		}, this);
	},
	
	// 初始化处理
	initHandler : function(){
		var tf = this.head_form.findByType('textfield');
		for(var i = 0; i < tf.length; i++){
			if(tf[i].originReadOnly){
				tf[i].setReadOnly(true);
			}
		}
		/*if(entType == 1){
			this.head_panel.find('name', 'declareCode')[0].setValue(entCode);
			this.head_panel.find('name', 'declareName')[0].setValue(entCnName);
		}*/
		this.gridAddListener();
	},

	
	// 加载出库单表头表体信息
	syisLoadDetail : function(loadURL, params){
		var me = this;
		if(params){
			Ext.Ajax.request({
				url : loadURL,
				params : params,
				success : function(response, opts){
					var result = Ext.decode(response.responseText);
					if(result.success == false){
						Ext.Msg.alert('错误', result.error);
						return;
					}
					var result_head = result.data;
					if(result_head){
						me.head_form.getForm().loadRecord({data : result_head});
					}

					me.list_grid.getStore().load({
						params : params
					});
					
					me.delcareHandler(result_head.billStatus);
					
				},
				failure : function(response, opts){
					Ext.Msg.alert('提示','发生错误，操作失败！');
				}
			});
		}
	},
	
	// 新增操作：清空各个字段及表体列表	
	syisNewBill : function(){
		var me = this;
		me.head_form.getForm().reset();
		me.list_form.getForm().reset();
		me.list_grid.getStore().removeAll();
		me.list_grid.enable();
		me.gridAddListener();
		
		if(Ext.getCmp('headSave')){
			Ext.getCmp('headSave').enable();
		}
		if(Ext.getCmp('headDeclare')){
			Ext.getCmp('headDeclare').enable();
		}
		if(Ext.getCmp('headDelete')){
			Ext.getCmp('headDelete').enable();
		}
		
		var head_tf = this.head_form.findByType('textfield');
		for(var i = 0; i < head_tf.length; i++){
			if(!head_tf[i].originReadOnly){
				head_tf[i].setReadOnly(false);
			}
		}
		
		var list_tf = this.list_form.findByType('textfield');
		for(var i = 0; i < list_tf.length; i++){
			if(!list_tf[i].originReadOnly){
				list_tf[i].setReadOnly(false);
			}
		}
	},
	
	// 暂存操作：保存表头、表体信息
	syisHeadSave : function(saveURL, saveParams, successFunction){
		var me = this;
		
		if(!saveURL || !saveParams || !successFunction){
			return;
		}
		if(me.getHeadForm().getForm().isValid()){
			Ext.Ajax.request({
				url : saveURL,
				params : saveParams,
				success : function(response, opts){
					var result = Ext.decode(response.responseText);
					if(result.success == false){
						Ext.Msg.alert('错误', result.error);
						return;
					}
					Ext.Msg.alert('提示', '暂存成功！', successFunction(response));
				},
				failure : function(response, opts){
					Ext.Msg.alert('提示','发生错误，操作失败！');
				}
			});
		}
	},
	
	// 申报操作：向海关端申报当前表单
	syisHeadDeclare : function(declareURL, params, successFunction){
		var me = this;
		
		if(!declareURL || !params || !successFunction){
			return;
		}
		
		if(me.getHeadForm().getForm().isValid()){
			Ext.Ajax.request({
				url : declareURL,
				params : params,
				success : function(response, opts){
					var result = Ext.decode(response.responseText);
					if(result.success == false){
						Ext.Msg.alert('错误', result.error);
						return;
					}
					Ext.Msg.alert('提示', '申报成功！', successFunction(response));
				},
				failure : function(response, opts){
					Ext.Msg.alert('提示','发生错误，操作失败！');
				}
			});
		}
	},
	
	// 删除操作：删除当前表单数据
	syisHeadDelete : function(deleteURL, type){
		var me = this;
		var uniqueId = me.head_form.find('name', me.keyField)[0].getValue();
		
		if(!uniqueId){
			Ext.Msg.alert('提示', '没有可供删除的数据！');
			return;
		}
		
		if(!deleteURL){
			return;
		}
		
		Ext.Msg.confirm('提示', "您确定要删除[" + (type == 'inbill' ? '入' : '出') + "库单号:" + uniqueId + "]这条记录吗？", function(btn){
			if(btn == 'no') {
				return;
			}
			Ext.Ajax.request({
				url : deleteURL,
				params : {
					'billNo' : uniqueId
				},
				success : function(response, opts){
					var result = Ext.decode(response.responseText);
					if(result.success == false){
						Ext.Msg.alert('错误', result.error);
						return;
					}
					Ext.Msg.alert('提示', '删除成功！', function(){
						if(Ext.getCmp('syisbillWin') && Ext.getCmp('syisbillQuery')){
							Ext.getCmp('syisbillWin').close();
							Ext.getCmp('syisbillQuery').doQuery();
						} else {
							me.syisNewBill();
						}
					});
				},
				failure : function(response, opts){
					Ext.Msg.alert('提示','发生错误，操作失败！');
				}
			});
		});
	},
	
	// 打印操作 ： 打印表头、表体信息
	syisPrint : function(printURL){
		var uniqueId = this.head_form.find('name', this.keyField)[0].getValue();
		if(!uniqueId){
			Ext.Msg.alert('提示', '没有可供打印的数据！');
			return;
		}
		window.open(printURL + '?billNo=' + uniqueId);
	},
	
	// 生成表头表体json格式的string字符串
	buildJsonString : function(headName, listName){
		var me = this;
		var head_form = me.head_form.getForm();
		var list_form = me.list_form.getForm();
		
		var billHead = headName + ':' + Ext.encode(head_form.getValues()) + '';
		
		var list = '';
        for(var i = 0; i < me.list_grid.getStore().getCount(); i++){
        	list += Ext.encode(me.list_grid.getStore().getAt(i).data)+','
        }
		var billList = listName + ':[' + list.substring(0,list.length-1) +']';
		
		return '{' + billHead + ',' + billList + '}';
	},
	
	// 表体添加临时数据
	handleEnter : function(){
		if(this.list_form.getForm().isValid()){
			var grid_Record = Ext.data.Record.create(['billNo','gname','gmodel','packNum']);
			var record = new grid_Record(this.list_form.getForm().getValues());
			if(this.list_form.find('name', 'country')[0]){
				record.set('countryText', this.list_form.find('name', 'country')[0].getRawValue());
			}
			if(this.list_form.find('name', 'unit')[0]){ 
				if(this.list_form.find('name', 'unit')[0].hidden == false){
					record.set('unitText', this.list_form.find('name', 'unit')[0].getRawValue());
				}
			}
			if(this.list_form.find('name', 'logisticsStatus')[0]){
				record.set('logisticsStatusText', this.list_form.find('name', 'logisticsStatus')[0].getRawValue());
			}
			if(this.list_form.find('name', 'currency')[0]){
				record.set('currencyText', this.list_form.find('name', 'currency')[0].getRawValue());
			}
			var selected = this.list_grid.getSelectionModel().getSelected();
			var recIndex = this.list_grid.getStore().indexOf(selected);
			if(selected){
				this.list_grid.getStore().remove(selected);
			}
			if(recIndex != 'undefined' && recIndex >= 0){
				this.list_grid.getStore().insert(recIndex, record);
				//this.list_grid.getSelectionModel().selectRow(recIndex);
			} else {
				this.list_grid.getStore().add(record);
			}
			this.list_form.getForm().reset();
			
			for(var j = 0; j < this.list_grid.getStore().getCount(); j++){
	        	this.list_grid.getStore().getAt(j).set('index', j);
	        }
			        
			// 光标停留在第一个可输入框中
			var textFields = this.list_form.findByType('textfield');
			for(var i = 0; i < textFields.length; i++){
				if(textFields[i].readOnly == false){
					textFields[i].focus();
					break;
				}
			}
		}
	},
	
	// 选择海关代码反填海关名称
	getCustomsName : function(){
		if(this.head_panel.find('name', 'customsCode')[0].isValid()){
			if(!this.head_panel.find('name', 'customsCode')[0].getValue()){
				this.head_panel.find('name', 'customsName')[0].setRawValue('');
				return;
			}
			var customsCode = this.head_panel.find('name', 'customsCode')[0].getValue();
			var params = {'paraName' : 'customsCode'};
			Ext.Ajax.request({
				url : 'parameter/queryParametes',
				params : params,
				success : function(response){
					responseJson = Ext.util.JSON.decode(response.responseText);
					if(responseJson.success){
						var data = responseJson.data;
						if(data.length > 0){
							var flag = true;
							for(var i = 0; i < data.length; i++){
								if(data[i].code == customsCode){
									flag = false;
									this.head_panel.find('name', 'customsName')[0].setValue(data[i].name);
								}
							}
							if(flag){
								Ext.Msg.alert("提示", "查无此申报海关");
								this.head_panel.find('name', 'customsName')[0].setRawValue('');
							}
						}else{
							Ext.Msg.alert("提示", "查无此申报海关");
							this.head_panel.find('name', 'customsName')[0].setRawValue('');
						}
					}
				},
				failure : function(response) {
					Ext.Msg.alert("提示", "请求失败");
					this.head_panel.find('name', 'customsName')[0].setRawValue('');
				},
				scope : this
			});
		}else{
			this.head_panel.find('name', 'customsName')[0].setRawValue('');
		}
	},
	
	fieldCombo : function(field1, field2, allowBlank){
		if(allowBlank == false){
			field1.allowBlank = allowBlank;
			field1.updateStyle();
			field1.clearInvalid();
			field2.allowBlank = allowBlank;
			field2.updateStyle();
			field2.clearInvalid();
		}
		if(allowBlank && field1.getValue() == '' && field2.getValue() == ''){
			field1.allowBlank = allowBlank;
			field1.updateStyle();
			field1.clearInvalid();
			field2.allowBlank = allowBlank;
			field2.updateStyle();
			field2.clearInvalid();
		}
	}
});
