$(function(){
	    	//只是保存
	    	$(document).on('click','.detailsavebtn .button_gray',function(){
	    		actionPath = "/wxServer/pc/activity/add" + "?isSend=0" ;
	    		$('.activity_type').attr('action',actionPath).submit() ;
	    	});
	    	//保存并群发
	    	$(document).on('click','.detailsavebtn .button',function(){
	    		actionPath = "/wxServer/pc/activity/add" + "?isSend=1" ;
	    		$('.activity_type').attr('action',actionPath).submit() ;
	    	});
	    	//删除
		 $(document).on('click','.detailsavebtn .button_delete',function(){
			 var title = $('.activity_type_content input[name="title"]').val() ;
			 $.ajax({
				 url:"/wxServer/pc/activity/delete",
				 data:{
					 type:"letter",
					 title:title
				 },
				 success:function(){
					 alert('删除成功') ;
					 $('#main').html('') ;
				 }
			 });
		 });
		 
	 
});
