(function () {
    var that = me.define("activity_ship", {
        ctrl: function () {

        }
    });
    
    //发布公告事件
    $(document).on('click','.activity_type .boardbtn',function(){
    	var content = $('.activity_type_content .text_long').val() ;
    	var title = $('.activity_type input[type="hidden"]').val() ;
    	if(""==content||null==content){
    		alert("请输入文本内容");
    	}else{
    		$.ajax({
    			url:"/wxServer/pc/manage/activity/putboard",
    			data:{
    				content:content,
    				title:title
    			},
    			success:function(data){
    				if(parseInt(data)<2){
    					alert('该活动因为关注度不超过2人，无法发布公告!') ;
    				}else{
    					alert('发布公告成功~~');
    				}
    			}
    		});
    	}
    });
})();