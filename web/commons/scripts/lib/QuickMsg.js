/** 
  * 信息提示框，显示后迅速消失 
  * 使用：Ext.QuickMsg.show('提示', "删除成功！" ,'250px',3,Ext.getBody(),[10,10],'t-t',true,true);
  */ 
Ext.QuickMsg = function() { 
	var msgCt; 
	function createBox(t, s, isClose) { 
		var html = new Array(); 
		html.push('<div id="msg" class="msg" onclick="Ext.QuickMsg.close()">'); 
		html.push('<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc">'); 
		html.push('</div></div></div>'); 
		html.push('<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">'); 
		if (t) { 
			html.push('<h3 class="msg-h3">'); 
			html.push(t); 
			html.push('</h3>'); 
		} 
		if (isClose) { 
			html.push('<span class="msg-close" onclick="Ext.QuickMsg.close()"></span>'); 
		} 
		html.push('<div style="text-align:center;">' + s + '</div>'); 
		html.push('</div></div></div>'); 
		html.push('<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>'); 
		html.push('</div>'); 
		return html.join(''); 
	} 
	return { 
		/** <img src="'+closeImageUrl+'" mce_src="'+closeImageUrl+'"/> 
		* 显示信息 
		* title:标题 
		* msg:提示信息 
		* time：显示时间，超时后自动消失 
		* alignEl：对齐到该对象的左下角 
		* offsets[Array]：横向偏移像素，比如：[200,0]标识右移200个像素 
		* positon：位置 
		* animate[boolean]：是否开启动画  
		* isClose[boolean]：是否可关闭 
		*/ 
		show : function(title, msg, width, time, alignEl, offsets, position, animate, isClose) { 
			width = width ? width : '250px'; 
			time = time ? time : 2; 
			alignEl = alignEl ? alignEl : document; 
			//alert(alignEl.id);  
			position = position ? position : 't-t'; 
			animate = animate ? animate : false; 
			this.close(); 
			if (!msgCt) { 
				msgCt = Ext.DomHelper.insertFirst(document.body, { 
					id : 'msg-div' 
				}, true); 
				msgCt.setWidth(width); 
			} 
			//采用默认动画将div的最中央对齐到alignEl的左下角,并右移200个象素,且不能超出窗口  
			msgCt.alignTo(document, position, offsets, animate); 
				var m = Ext.DomHelper.append(msgCt, { 
					html : createBox(title, msg, isClose) 
				}, true); 
			m.slideIn('t').pause(time).ghost("t", { 
				remove : true 
			});//元素从上滑入效果,可不带参数调用下同    
		}, 

		//提示信息  
		alert : function(msg, field, alignEl, width) { 
			width = width ? width : '150px'; 
			var html = '<span style="font-size:13px;" mce_style="font-size:13px;">' + msg + '</span>';
			this.show('', html, '150px', 2, field, [120, 0], 't-t', true, true); 
		}, 
	
		close : function() { 
			var div = document.getElementById('msg-div'); 
			if (div) { 
				div.style.display = 'none'; 
			} 
			msgCt = ''; 
		} 
	}; 
}();