Ext.ns("App");
Ext.ns("Ext.app");
App.init = function() {

	Ext.QuickTips.init();// 这句为表单验证必需的代码
	Ext.BLANK_IMAGE_URL = __ctxPath + '/commons/scripts/ext/resources/images/default/s.gif';
	var frameContent = null;
	var cookies = Ext.util.Cookies;

	var pWidth = cookies.get('pWidth');
	var pHeight = cookies.get('pHeight');
	var certainSize = /[0-9_]/i.test(pWidth) && /[0-9_]/i.test(pHeight);

	var pWidth = certainSize ? pWidth : (Ext.lib.Dom.getViewWidth(true)<980?980:Ext.lib.Dom.getViewWidth(true));
	var pHeight = certainSize ? pHeight : (Ext.lib.Dom.getViewHeight(true)<600?600:Ext.lib.Dom.getViewHeight(true));
	
	var flagW = document.body.clientWidth>980;
	var flagH = document.body.clientHeight>600;
	if(flagW && flagH){
		document.body.style.overflowX = 'hidden';
		document.body.style.overflowY = 'hidden';
	}
	
	var viewW = pWidth;
	var treePanelW = 180;
	var frmPanelW = viewW - treePanelW-7;
	var frmContentW = viewW - treePanelW-8;
	var panW = pWidth - treePanelW;
	Ext.get("content").setWidth(viewW);
	
	var viewH = pHeight;
	var frmPanelH = viewH - 62;
	var frmContentH = viewH - 62;
	var treePanelH = viewH - 62;
 	Ext.get("content").setHeight(viewH);
	
	var frmPanel = new Ext.Panel({
		id : 'frmPanel',
		margin : 0,
		split : true,
		region : 'center',
		border : false,
		frame : false,
		autoScroll: false,
		html : String.format('<img width="{0}px" height="{1}px" src="' + __ctxPath
				+ '/commons/images/welcome.jpg"></img>',
				frmContentW, frmContentH)
	});
//	var pos = '当前位置：';
//	var treeLoader = new Ext.tree.TreeLoader({
//				dataUrl : 'treeJson.json',
//				listeners:{
//					load:function(){
//						treeRootNode.eachChild(function(node){						  
//							if(node.hasChildNodes()){
//							  node.getUI().getIconEl().src=__ctxPath + '/commons/images/parentNode.png';
//							  node.eachChild(function(leafNode){
//							    if(leafNode.isLeaf()){	
//							     leafNode.getUI().getIconEl().src=__ctxPath + '/common/images/leafNode.png';
//							    }
//							   
//							  },this);
//							
//							}
//						},this);
//					}
//				}
//			});
//	var treeRootNode = new Ext.tree.AsyncTreeNode({
//	  
//	});
//	var treePanel = new Ext.tree.TreePanel({
//				id : 'treepanel',
//				baseCls : 'mainLeftTree',
//				region : 'west',
//				bodyStyle:'padding-top:20px;',	
//				width : treePanelW,
//				height : treePanelH,
//				header : false,
////				useArrows : true,
//				autoScroll : true,
//				monitorResize : true,
//				lines : false,
//				border : false,
//				rootVisible : false,
//				trackMouseOver : true,
//				loader : treeLoader,
//				root : treeRootNode,
//				listeners : {
//					'click' : function(node, event, root) {
//						if (node.leaf) {
//							frmPanel.load({
//										url : node.attributes.value,
//										callback : function() {
//										},
//										scripts : true
//									})
//							var temp = node.text;
//							while (node.parentNode.text) {
//								temp = node.parentNode.text + ' > ' + temp;
//								node = node.parentNode;
//							}
//							pos += temp;
//							Ext.getCmp('userpos').setText(pos);
//							pos = '当前位置：';
//						} else if (!node.leaf) {
//							node.parentNode.collapseChildNodes();
//							node.expand();
//						}
//					}
//				}
//
//			});
//	treePanel.expandAll();
//	var treePanel = new Ext.Panel( {
//		id : 'treepanel',
//		baseCls : 'mainLeftTree',
//		header : false,
//		autoScroll : true,
//		html : '<ul class="container"></ul>',
//		region : 'west',
//		layout : 'accordion',
//		split : true,
//		width : treePanelW,
//		height : treePanelH,
//		minSize : 180,
//		maxSize : 200,
//		margins : '0 0 0 2'
//	});
//	var bannerPanel = new Ext.Panel({
//		width : pWidth,
//		height : 68,
//		renderTo : 'banner',
//		id : 'bannerPanel',
//		margin : 0,
//		region : 'north',
//		layout : 'fit',
//		border : false,
//		frame : false,
//		defaults : {
//			autoScroll : true
//		},
//		html : String.format('<img align="center" width="{0}px" height="{1}px" src="' + __ctxPath
//				+ '/common/images/banner.png"></img>',
//				viewW, 68)
//
//	});

	new Ext.Panel({
		renderTo : 'content',
		width : viewW,
		height : viewH,
		margins : 0,
		layout : 'border',
		//autoScroll : false,
		//bodyStyle : 'overflow-x:auto; overflow-y:auto',
		items : [
			{
				region : 'north',
				border : false,
				html :  '<div id="banner">' +
						'<span style="float:left; margin:13px 0 0 40px;">' +
						'	<a style="font-size:32px; font-family:微软雅黑; color:#FFF;">河南跨境贸易服务系统</a>' +
						'</span>' +
						'<span style="position:absolute; bottom:6px; right:25px; font-family:微软雅黑; color:#FFF; font-size:15px;">' +
						'	<a id="username" style="font-size:15px; float:left;">您好，'+ userName +'</a>' +
						'	<a id="change" style="float:left; cursor:pointer; margin-left:20px; height:20px;" ' +
						'		onMouseOver="this.style.color=\'yellow\'" onMouseOut="this.style.color=\'white\'"' +
						'		onClick="if(ukey.checkSCAClient()){if(ukey.checkUkey()){App.ChangePasswordDialog.show();}else{alert(\'请使用ukey登陆！\')}}">' +
						'		<img src="commons/images/mima.png" style="float:left; margin-right:5px;">修改key密码</a>' +
						'	<a id="logout" style=":active:(background:#00aa00); float:left; cursor:pointer; margin-left:20px;" ' +
						'		onMouseOver="this.style.color=\'yellow\'" onMouseOut="this.style.color=\'white\'"' +
						'		onClick="if (confirm(\'是否确定退出系统？\')) { window.location = \'quit.jsp\' }">' +
						//'		onClick="Ext.MessageBox.confirm(\'提示\', \'是否确定退出系统？\', function(btn){if(btn==\'yes\')window.location=\'quit.jsp\'});">' +
						'		<img src="commons/images/tuichu.png" style="float:left; margin-right:5px;">退出系统</a>' +
						'</span>' +
						'</div>'
			}, 
//			treePanel, 
			//frmPanel,
				{
				
				id:'frmPanel',
				region : 'center',
				xtype:'uploadPanel',
				border : false,
				fileSize : 1024*10,//限制文件大小
				uploadUrl : 'upload/excel',
				flashUrl : 'swfupload.swf',
				filePostName : 'file', //后台接收参数
				fileTypes : '*.xls;*.xlsx',//可上传文件类型
				postParams : {savePath:'upload\\'} //上传文件存放目录
			}
 
		]
	});
	function changeSize(sender) {
		var expires = new Date('1/10/2999 03:05:01 PM GMT-0600');
		cookies.set("pWidth", sender.pWidth, expires);
		cookies.set("pHeight", sender.pHeight, expires);
		cookies.set("pWidth", sender.pWidth, expires);
		cookies.set("pHeight", sender.pHeight, expires);
		window.location = __ctxPath + '/main.jsp';
	}
	function getFrameContent() {
		if (frameContent == null) {
			frameContent = Ext
					.get(Ext.DomQuery.selectNode("iframe#frmContent"));
		}
		return frameContent;
	}
	function logout() {
		if (confirm('是否确定退出系统？')) {
			location.href = __ctxPath+"/security/logout";
		}
	}
	function showUserInfo() {
		userInfoForm.getForm().load({
			url : 'security/loadUserInfo',
			success : function(form, action) {
				Ext.getCmp('customsCodeCombo')
						.setValue(action.result.data.customsCode);
			},
			failure : function(form, action) {
				Ext.Msg.alert('系统异常', "载入用户信息失败。");
			}
		});
		userInfoWin.show();
	}
	window.onresize = function(){
		var flagW = document.body.clientWidth>pWidth;
		var flagH = document.body.clientHeight>pHeight;
		if(flagW){
			document.body.style.overflowX = 'hidden';
		}else{
			document.body.style.overflowX = 'auto';
		}
		if(flagH){
			document.body.style.overflowY = 'hidden';
		}else{
			document.body.style.overflowY = 'auto';
		}
	}
};