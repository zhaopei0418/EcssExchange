
function completeLoading() {
	alert(0);
    setTimeout(function() {
      Ext.get('loading').remove();
      Ext.get('loading-mask').fadeOut({ remove: true });
    }, 250);
}
