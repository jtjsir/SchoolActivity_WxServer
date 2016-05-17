(function () {
    var that = me.define("activity_history", {
        ctrl: function () {
        },
        activity_bbs:function(){
            me.show("activity_bbs", {
                showType: 0
            });
        }

    });
    
  //绑定按钮事件
	//只是保存
	$(document).on('click','.historysavebtn .button_gray',function(){
		actionPath = "/wxServer/pc/activity/add" + "?isSend=0" ;
		$('.activity_type').attr('action',actionPath).submit() ;
	});
	//保存并群发
	$(document).on('click','.historysavebtn .button',function(){
		actionPath = "/wxServer/pc/activity/add" + "?isSend=1" ;
		$('.activity_type').attr('action',actionPath).submit() ;
	});
	
	//发布公告
	$(document).on('click','.historysavebtn .button_yellow',function(){
		var title = $('.activity_type_content input[type="hidden"]').val() ;
		$.ajax({
			url:"/wxServer/pc/index/tpl/activity_bbs",
			success:function(data){
				$('#main').html(data) ;
				
				$('.activity_type').append("<input type='hidden' value='" + title + "'/>") ;
			}
		});
	});
	//删除
	$(document).on('click','.historysavebtn .button_delete',function(){
		var title = $('.activity_type_content input[name="title"]').val() ;
		$.ajax({
		 url:"/wxServer/pc/activity/delete",
		 data:{
			 type:"news",
			 title:title
		 },
		 success:function(){
			 alert('删除成功') ;
			 $('#main').html('') ;
		 }
	 });
 });
})();