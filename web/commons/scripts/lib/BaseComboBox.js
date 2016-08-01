/**表格父类***/
Ext.namespace("Ext.cneport.lib");
Ext.cneport.lib.BaseComboBox = Ext.extend(Ext.form.ComboBox, {	
	queryParam: 'queryValue', 	
	minChars: 0,
	paraName: null,
	paraQueryUrl: '../frame/para!query.action',
	triggerAction:'all',
	valueField: 'code', 
	displayField: 'value',	
	nameField: '',	
	initComponent : function(){
        Ext.cneport.lib.BaseComboBox.superclass.initComponent.call(this);
        var me = this;
        if (me.paraName){
        	me.store = new Ext.data.JsonStore({
				root: 'topics', 
				totalProperty: 'totalCount',
				baseParams:{
					paraName: me.paraName
				},	
				fields: ['code', 'value'], 
				autoLoad: false,				
				url: me.paraQueryUrl
			});
			me.pageSize = me.pageSize?me.pageSize:10;
			me.listWidth = me.listWidth?me.listWidth:205; 
        }
    },
	initList : function(){
        if(!this.list){
            var cls = 'x-combo-list',
                listParent = Ext.getDom(this.getListParent() || Ext.getBody()),
                zindex = parseInt(Ext.fly(listParent).getStyle('z-index'), 10);

            if (!zindex) {
                zindex = this.getParentZIndex();
            }

            this.list = new Ext.Layer({
                parentEl: listParent,
                shadow: this.shadow,
                cls: [cls, this.listClass].join(' '),
                constrain:false,
                zindex: (zindex || 12000) + 5
            });

            var lw = this.listWidth || Math.max(this.wrap.getWidth(), this.minListWidth);
            this.list.setSize(lw, 0);
            this.list.swallowEvent('mousewheel');
            this.assetHeight = 0;
            if(this.syncFont !== false){
                this.list.setStyle('font-size', this.el.getStyle('font-size'));
            }
            if(this.title){
                this.header = this.list.createChild({cls:cls+'-hd', html: this.title});
                this.assetHeight += this.header.getHeight();
            }

            this.innerList = this.list.createChild({cls:cls+'-inner'});
            this.mon(this.innerList, 'mouseover', this.onViewOver, this);
            this.mon(this.innerList, 'mousemove', this.onViewMove, this);
            this.innerList.setWidth(lw - this.list.getFrameWidth('lr'));

            if(this.pageSize){
                this.footer = this.list.createChild({cls:cls+'-ft'});
                this.pageTb = new Ext.PagingToolbar({
                    store: this.store,
                    pageSize: this.pageSize,
                    beforePageText : '',
                    afterPageText : '/{0}',                    
                    renderTo:this.footer
                });
                this.assetHeight += this.footer.getHeight();
            }

            if(!this.tpl){                
                this.tpl = '<tpl for=".">' +
                '<div class="'+cls+'-item">' +
                	'<div style="float:left">' +
                	'{' + this.valueField + '}'+
                	'</div>'+
                	'<div style="float:right">' +
                		'{' + (this.nameField != '' ? this.nameField : this.displayField) + '}'+
                	'</div>'+
                	'<div style="clear:both"></div>'+
               ' </div></tpl>';                
            }

            
            this.view = new Ext.DataView({
                applyTo: this.innerList,
                tpl: this.tpl,
                singleSelect: true,
                selectedClass: this.selectedClass,
                itemSelector: this.itemSelector || '.' + cls + '-item',
                emptyText: this.listEmptyText,
                deferEmptyText: false
            });

            this.mon(this.view, {
                containerclick : this.onViewClick,
                click : this.onViewClick,
                scope :this
            });

            this.bindStore(this.store, true);

            if(this.resizable){
                this.resizer = new Ext.Resizable(this.list,  {
                   pinned:true, handles:'se'
                });
                this.mon(this.resizer, 'resize', function(r, w, h){
                    this.maxHeight = h-this.handleHeight-this.list.getFrameWidth('tb')-this.assetHeight;
                    this.listWidth = w;
                    this.innerList.setWidth(w - this.list.getFrameWidth('lr'));
                    this.restrictHeight();
                }, this);

                this[this.pageSize?'footer':'innerList'].setStyle('margin-bottom', this.handleHeight+'px');
            }
        }
    },
    doQuery : function(q, forceAll, noSelect){
        q = Ext.isEmpty(q) ? '' : q;
        var qe = {
            query: q,
            forceAll: forceAll,
            combo: this,
            cancel:false
        };
        if(this.fireEvent('beforequery', qe)===false || qe.cancel){
            return false;
        }
        q = qe.query;
        forceAll = qe.forceAll;
        if(forceAll === true || (q.length >= this.minChars)){
            if(this.lastQuery !== q){
                this.lastQuery = q;
                if(this.mode == 'local'){
                    this.selectedIndex = -1;
                    if(forceAll){
                        this.store.clearFilter();
                    }else{
                        this.store.filter([{
							fn:function(record) {
								var value = this.store.data.createValueMatcher(q, true, true, false);
								return value.test(record.get(this.displayField)) || value.test(record.get(this.valueField));
							},
							scope: this
                        }]);
                    }
                    this.onLoad();
                }else{
                    this.store.baseParams[this.queryParam] = q;
                    this.store.noSelect = noSelect;
                    this.store.load({
                        params: this.getParams(q)
                    });
                    if (!noSelect){
                    	this.expand();
                    }
                }
            }else{
                this.selectedIndex = -1;
                this.onLoad();
            }
        }
    },
    assertValue  : function(){
        var val = this.getRawValue(),
            rec = this.findRecord(this.displayField, val);

        if(!rec && this.forceSelection){
            if(val.length > 0 && val != this.emptyText){
                this.el.dom.value = Ext.value(this.lastSelectionText, '');
                this.applyEmptyText();
            }else{
                this.clearValue();
            }
        }else{
            if(rec){
                
                
                
                if (val == rec.get(this.displayField) && this.value == rec.get(this.valueField)){
                    return;
                }
                val = rec.get(this.valueField || this.displayField);
            }
            this.setValueFromCache(val);
        }
    },
    onSelect : function(record, index){
        if(this.fireEvent('beforeselect', this, record, index) !== false){
            this.setValueFromCache(record.data[this.valueField || this.displayField]);
            this.collapse();
            this.fireEvent('select', this, record, index);
        }
    },
    setValueFromCache: function(v){
    	Ext.cneport.lib.BaseComboBox.superclass.setValue.call(this, v);
    },    
    setValue: function(v){
        var text = v;
        if (this.mode=='local'){
	        if(this.valueField){
	            var r = this.findRecord(this.valueField, v);
	            if(r){
	                text = r.data[this.displayField];
	            }else if(Ext.isDefined(this.valueNotFoundText)){
	                text = this.valueNotFoundText;
	            }
	        }
	        this.lastSelectionText = text;
	        if(this.hiddenField){
	            this.hiddenField.value = Ext.value(v, '');
	        }
	        Ext.form.ComboBox.superclass.setValue.call(this, text);
        	this.value = v;
        	return this;
        }
        if(this.mode == 'remote'){
        	 var text = v;
        if(this.valueField){
            var r = this.findRecord(this.valueField, v);
            if(r){
                text = r.data[this.displayField];
	        }else if(Ext.isDefined(this.valueNotFoundText)){
	                text = this.valueNotFoundText;
	            }
	        }
	        this.lastSelectionText = text;
	        if(this.hiddenField){
	            this.hiddenField.value = Ext.value(v, '');
	        }
	        Ext.form.ComboBox.superclass.setValue.call(this, text);
	        this.value = v;
	        return this;
        }
       	this.initList();
        this.doQuery(v,null,true);
        return this;       
    },
    onLoad : function(){
       	if (this.store.noSelect && this.store.getCount() > 0){
           	this.select(0, true);
           	var r = this.store.getAt(0);
           	this.setValueFromCache(r.data[this.valueField || this.displayField]);
           	return;
       	}        
        if(!this.hasFocus){
			return;
        }
        if(this.store.getCount() > 0 || this.listEmptyText){
            this.expand();
            this.restrictHeight();
            if(this.lastQuery == this.allQuery){
                if(this.editable){
                    this.el.dom.select();
                }

                if(this.autoSelect !== false && !this.selectByValue(this.value, true)){
                    this.select(0, true);
                }
            }else{
                if(this.autoSelect !== false){
                    this.selectNext();
                }
                if(this.typeAhead && this.lastKey != Ext.EventObject.BACKSPACE && this.lastKey != Ext.EventObject.DELETE){
                    this.taTask.delay(this.typeAheadDelay);
                }
            }
        }else{
            this.collapse();
        }
    },
    
    selectNext : function() {
		var a = this.store.getCount();
		var s = this.store;
		if (a > 0) {
			if (this.selectedIndex == -1) {
				if(this.store.find('code', this.getRawValue())){
					this.select(this.store.find('code', this.getRawValue()));
				}else{
					this.select(0)
				}
			} else {
				if (this.selectedIndex < a - 1) {
					this.select(this.selectedIndex + 1)
				}
			}
		}
	},
    getParentZIndex : function(){
    	var a;
    	if(this.ownerCt){
    		this.findParentBy(function(b){
    			a = parseInt(b.getPositionEl().getStyle("z-index"),10);
    			return !!a
    		})
    	}
    	return a
    },
    reset : function(){
    	this.clearValue();
    }
});
Ext.reg('eportcombo', Ext.cneport.lib.BaseComboBox);