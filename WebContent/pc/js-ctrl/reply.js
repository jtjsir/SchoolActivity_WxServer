(function () {
    var that = me.define("reply", {
        ctrl: function () {
        }
    });
    $(document).on('click','.reply .activity_type .replybtn',function(){
    	bindSend() ;
    });
    
    //绑定事件
    var bindSend = function(){
    	var openid = $('.reply .activity_type input[type="hidden"]').val() ;
    	var content = $('.activity_type_content .text_long').val() ;
    	if(content==""||content==null){
    		alert('请输入回复的内容') ;
    	}else{
    		$.ajax({
    			url:"/wxServer/pc/manage/msg/reply",
    			data:{
    				openid:openid,
    				content:content
    			},
    			success:function(){
    				alert('发送消息成功~~~~');
    			}
    		});
    	}
    }
})();