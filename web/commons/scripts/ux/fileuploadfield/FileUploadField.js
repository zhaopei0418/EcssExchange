/*!
 * Ext JS Library 3.2.1
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
/*
 * 
 * @class Ext.ux.form.FileUploadField
 * @extends Ext.form.TwinTriggerField
 * Ext.ux.form.FileUploadField
 *
 * examples like this :
 *
 *{
 *	xtype:'fileuploadfield',
 *	id:'file1',
 *	fieldLabel:'上传附件',
 *	allowBlank:false,
 *	fileFieldName:'myfile',
 *	fileFieldId:'myfile',
 *	vtype:'fileType',
 *	vtypeText:'上传文件必须是xls,doc,txt,ppt类型文件中的一种!!!',
 *	fileTypes:['xls','doc','txt','ppt'],
 *	enableClearValue:true,
 *	toolTip:true
 *}
 */
Ext.ns('Ext.ux.form');

Ext.ux.form.FileUploadField = Ext.extend(Ext.form.TwinTriggerField, {
	
	editable : false,
	
	readOnly : true,
	
	blankText : '请选择要上传的文件！',
	
	//clear trgger
	trigger1Class : 'x-form-clear-trigger',
	//select trigger
    trigger2Class : 'x-form-search-trigger',
	
	//the hidden <input type='file'/> id
	fileFieldId : Ext.id(),
	
	//the hidden <input type='file'/> name
//	fileFieldName : Ext.id(),
	
	fileTypes : [],
	
	//toolTip Mixed
	toolTip : null,
	
	//enable show clear trigger
	enableClearValue : false,
	
	//hide trigger1
	hideTrigger1 : true,
	
    initComponent : function(){
        Ext.ux.form.FileUploadField.superclass.initComponent.call(this);
		
		this.triggerConfig = {
            tag:'span', cls:'x-form-twin-triggers', cn:[
            {tag: "img", id:'triger-clear'+this.fileFieldId, src: Ext.BLANK_IMAGE_URL, style:'visibility:visible',cls: "x-form-trigger " + this.trigger1Class},
            {tag:'span', cls:'ux-cabinet', cn:[
            	{tag: "div", id:'wrap'+this.fileFieldId,cls:"ux-input-file-wrapper",cn: [
                {tag: "input", name: 'file', cls : 'ux-file',type: "file", id: this.fileFieldId}
                ]},
                {tag: "img", id:"triger-search"+this.fileFieldId, src: Ext.BLANK_IMAGE_URL, cls: "x-form-trigger " + this.trigger2Class}
       		]}
        ]};
		
		this.hideTrigger1 = true;
		
		if(this.enableClearValue){
			this.onTrigger1Click = this.clearValue;
		}
		
		this.addEvents(
            /**
             * @event fileselected
             * Fires when the underlying file input field's value has changed from the user
             * selecting a new file from the system file selection dialog.
             * @param {Ext.ux.form.FileUploadField} this
             * @param {String} value The file value returned by the underlying file input field
             */
            'fileselected',
			
			/**
             * @event clearvalue
             * Fires when the clear value trigger is clicked
             * @param {Ext.ux.form.FileUploadField}
             * @param {String} the value will be cleared
             */
			'clearvalue'
        );
    },
	
	//override
	onRender : function(ct, position){
		Ext.ux.form.FileUploadField.superclass.onRender.call(this,ct,position);
		this.fileField = Ext.getDom(this.fileFieldId);
		this.fileFieldWrap = Ext.get('wrap' + this.fileFieldId);
		this.initSelectorStyle();
		//init toolTip
		if(this.toolTip){
			this.toolTip = new Ext.ToolTip({
				target:this.getEl(),
				autoHide:true,
				anchor: 'bottom',
				showDelay:100,
				anchorToTarget:true,
				trackMouse: true,
				renderTo: document.body,
				html:'',
				listeners: { 
					scope:this,
					'beforeshow': function(tip) {
						tip.body.dom.innerHTML = this.getValue();
					},
					'show': function(tip){
						if(tip.body.dom.innerHTML.trim() == '' || !this.toolTip){
							tip.hide();
						}
					}
				}
			});
		}
	},
	
	//init events
    initEvents : function(){
		Ext.ux.form.FileUploadField.superclass.initEvents.call(this);
		Ext.get(this.fileField).on('change',function(){
			this.setValue(this.fileField.value);
			if(this.fireEvent('fileselected',this,this.fileField.value) !== false && this.enableClearValue){
				this.triggers[0].show();
			}
		},this);
	},
	
	//clear value by click clear value trigger
	clearValue : function(){
		var v = this.fileField.value || this.getValue();
		Ext.get(this.fileField).remove();
		this.fileField = Ext.DomHelper.append(this.fileFieldWrap, {
			tag: "input",
			name: 'file',
			type: "file",
			cls: "ux-file",
			id: this.fileFieldId
		},false);
		//rebind event on the new fileField
		Ext.get(this.fileField).on('change',function(){
			this.setValue(this.fileField.value);
			if(this.fireEvent('fileselected',this,this.fileField.value) !== false && this.enableClearValue){
				this.triggers[0].show();
			}
		},this);
		this.initSelectorStyle();
		this.setValue('');
		this.triggers[0].hide();
		this.fireEvent('clearvalue', this, v);
    },
	
	validateFileTypes :function(){
    	var filePath = this.fileField.value;
		var currentFilePrefix = filePath.substring(filePath.lastIndexOf('.') + 1);
		alert(currentFilePrefix);
    	
    },
	
	//override
    reset : function(){
        Ext.ux.form.FileUploadField.superclass.reset.call(this);
        if(this.fileField){
			this.clearValue();
		}
    },
	
	//init the trigger2 style
	initSelectorStyle : function(){
		Ext.get(this.fileField).setOpacity(0);
		this.fileFieldWrap.alignTo(Ext.get('triger-search' + this.fileFieldId),'tl?');
	},
	
	//prevent trigger hidden when set readOnly = true
    updateEditState: function(){
        if(this.rendered){
            if (this.readOnly) {
                this.el.dom.readOnly = true;
                this.el.addClass('x-trigger-noedit');
                this.mun(this.el, 'click', this.onTriggerClick, this);
            } else {
                if (!this.editable) {
                    this.el.dom.readOnly = true;
                    this.el.addClass('x-trigger-noedit');
                    this.mon(this.el, 'click', this.onTriggerClick, this);
                } else {
                    this.el.dom.readOnly = false;
                    this.el.removeClass('x-trigger-noedit');
                    this.mun(this.el, 'click', this.onTriggerClick, this);
                }
                this.trigger.setDisplayed(!this.hideTrigger);
            }
            this.onResize(this.width || this.wrap.getWidth());
        }
    },

    // private
    onDestroy: function(){
        Ext.ux.form.FileUploadField.superclass.onDestroy.call(this);
        Ext.destroy(this.fileField, this.fileFieldWrap);
    },
    
    onDisable: function(){
        Ext.ux.form.FileUploadField.superclass.onDisable.call(this);
        this.doDisable(true);
    },
	
	onEnable: function(){
        Ext.ux.form.FileUploadField.superclass.onEnable.call(this);
        this.doDisable(false);

    },
    
    // private
    doDisable: function(disabled){
        this.fileField.disabled = disabled;
    }
});
/**
* customized vtype for validate file types when upload
*
**/
Ext.apply(Ext.form.VTypes, {
    fileType : function(val, field){
    	var filePath = field.fileField.value;
		var currentFilePrefix = filePath.substring(filePath.lastIndexOf('.') + 1);
    	if(field.fileTypes.length > 0 && !Ext.isEmpty(filePath)){
			var temp = [];
			for(var i=0;i<field.fileTypes.length;i++){
				temp[field.fileTypes[i].toLowerCase()] = true;
			}
			if(!temp[currentFilePrefix.toLowerCase()]){
				return false;
			}
    	}
    	return true;
    },
    fileTypeText:'上传文件的格式不符合要求，请重新选择后再上传！'
});

Ext.reg('fileuploadfield', Ext.ux.form.FileUploadField);