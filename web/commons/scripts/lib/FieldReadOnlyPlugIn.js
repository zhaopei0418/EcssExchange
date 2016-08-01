Ext.namespace('Ext.ux');

Ext.ux.FieldStylePlugin = function(){
	 this.init = function(f){

		if(f.allowBlank == false){
			f.fieldLabel = f.fieldLabel + '<span style="color:red;position:relative;top:3px">*</span>';
		}
		
		f.updateStyle = function(){
			var el = f.getEl();  
			if (!el) {
				return;
			}			
			el.removeClass("x-item-allowblank"); 
			el.removeClass("x-item-readonly"); 
			if (f.readOnly === true){
				el.addClass("x-item-readonly"); 
				if(f.getXType() == 'datefield' && f.readOnly){
					f.disabled=true;
					f.onTriggerClick = function(){return}
				}
				return;
			}
			if (f.allowBlank === false){
				
				el.addClass("x-item-allowblank"); 
				return;
			}
		};
		
	 	f.setReadOnlyOrg = f.setReadOnly;
	 	f.setReadOnly = function(value){
	 		f.setReadOnlyOrg(value);
	 		f.updateStyle();
	 	};
	 	
	 	f.setAllowBlank = function(value){
		    f.allowBlank = value;
	 		f.updateStyle();
	 	};
	 	
	 	f.setMaxLength = function(value){
		 	if (f.rendered){
		 		if (value < Number.MAX_VALUE){
		 			f.getEl().dom.maxLength = value;
		 		}		 		
		 	}	 
		 	f.maxLength = value;	
	 	};
	 	
	 	f.onRenderOrg = f.onRender;
	 	f.onRender = function(ct, position){
	 		f.onRenderOrg(ct, position);	 		
	 		f.setMaxLength(f.maxLength);
	 		f.updateStyle();	 	
	 	} 
	 }
};
