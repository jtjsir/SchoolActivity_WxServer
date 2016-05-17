(function () {
    var that = me.define("menu2", {
        ctrl: function () {
        	displayAll() ;
        },
        reply:function(){
            me.show("reply", {
                showType: 0
            });
        },
    });
    
    var displayAll = function(){
    	$.ajax({
    		url:"/wxServer/pc/manage/user/query/all",
    		success:function(data){
    			if(data==null||"null"==data){
    				console.log('没有对应的用户数据') ;
    				$('.message_list').html('没有对应的用户数据~~~')
    			}else{
    				decodeUserJson(data) ;
    			}
    		}
    	});
    }
    
    var decodeUserJson = function(data){
    	var json = JSON.parse(data) ;
    	var len = json.length ;
    	//获取填充元素并置空
    	var messageList = $('.message_list') ;
    	messageList.html('') ;
    	for(var i = 0 ; i < len ;i++){
    		var strContent = "<li>"
    			+"<a>"
    			+"<table class='message_list_text'>"
    			+"<tr>"
    			+"<td><input type='checkbox'><td>"
    			+"<td><img src='" + json[i].headimgurl + "'></td>"
    			+"<td><span class='user-name'>" + json[i].nickname + "</span></td>"
    			+"<td class='note'><input type='button' class='button_gray' value='发送消息'></td>"
    			+"<td><input type='hidden' value='" + json[i].openid+ "'/> "
    			+"</tr></table>"
    			+"<a>";
    		messageList.append(strContent) ;
    	}
    }
    //搜索
    $(document).on('click','.search .button',function(){
    	var uname = $('.search .text').val() ;
    	$.ajax({
    		url:"/wxServer/pc/manage/user/query/some",
    		data:{
    			nickname:uname
    		},
    		success:function(data){
    			if(data==null||"null"==data){
    				console.log('没有对应的用户数据') ;
    				$('.message_list').html('没有对应的用户数据~~~')
    			}else{
    				decodeUserJson(data) ;
    			}
    		}
    	});
    });
    
    //给li元素绑定事件
    $(document).on('click','.message_list li .message_list_text .note',function(){
    	$.ajax({
    		url:"/wxServer/pc/index/tpl/reply",
    		success:function(data){
    			var openid = $('.message_list_text td input[type="hidden"]').val() ;
    			$('#main').html(data) ;
    			//
    			var appendName = "<input type='hidden' value='" + openid+"' />"
    			$('.reply .activity_type').append(appendName) ;
    			
    		}
    	});
    });
})();