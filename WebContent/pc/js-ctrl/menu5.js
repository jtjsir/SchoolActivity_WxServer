(function () {
    var that = me.define("menu5", {
        ctrl: function () {
        	loadDaysMsg(0,"all") ;
        	$('.content5_head .allmsg').attr('class','allmsg active');
        	$('.content5_head .storedmsg').attr('class','storedmsg') ;
        },
        reply:function(){
            me.show("reply", {
                showType: 0
            });
        },
    });
    
    //默认加载今天的全部消息
    var loadDaysMsg = function(days,msgType){
    	$.ajax({
    		url:"/wxServer/pc/manage/msg/querydays",
    		data:{
    			days:days,
    			msgType:msgType
    		},
    		success:function(data){
    			if(data==null||data=="null"){
    				console.log("没有消息") ;
    				$('.message_list').html('没有消息记录') ;
    			}else{
    				decodeJsonToHtml(data) ;
    			}
    		}
    	});
    }
    
    var decodeJsonToHtml = function(data){
    	var json = JSON.parse(data) ;
    	var len = json.length ;
    	var msgList = $('.message_list') ;
    	msgList.html('') ;
    	for(var i = 0; i < len ;i++){
    		var appendStr = "<li>"
    			+"<a>"
    			+"<img src='" + json[i].headimgurl + "'>"
    			+"<table class='message_list_text'>"
    			+"<tr>"
    			+"<td class='user-name'>" + json[i].nickname + "</td>"
    			+"<td class='message_content'>" + json[i].msg + "</td>"
    			+"<td class='message_time'>" + json[i].time + "</td>"
    			+"<td class='collection'>" + "<i class='fa " + (json[i].isstored=="1"?"fa-star":"fa-star-o") + "'>" +"</i></td>"
    			+"<td><input type='hidden' name='openid' value='" + json[i].openid+ "'/> "
    			+"<td><input type='hidden' name='msgid' value='" + json[i].id+ "'/> "
    			+"</tr>"
    			+"</table>"
    			+"</a>"
    			+"</li>";
    		msgList.append(appendStr) ;
    	}
    }
    //为昵称绑定事件
    $(document).on('click','.message_list_text .user-name',function(){
    	var openid = $(this).parent().children('td').children('input[name="openid"]').val() ;
    	$.ajax({
    		url:"/wxServer/pc/index/tpl/reply",
    		success:function(data){
    			$('#main').html(data) ;
    			//
    			var appendName = "<input type='hidden' value='" + openid+"' />"
    			$('.reply .activity_type').append(appendName) ;
    			
    		}
    	});
    });
    
    //为搜藏绑定事件
    $(document).on('click','.message_list_text .collection',function(){
    	var storeclass= $(this).children().attr('class') ;
    	var msgid = $(this).parent().children('td').children('input[name="msgid"]').val() ;
    	var isstored ;
    	if(storeclass=="fa fa-star"){
    		//表明已经搜藏，正要取消搜藏
    		isstored = 0 ;
    		$(this).children().attr('class','fa fa-star-o');
    	}else{
    		isstored = 1 ;
    		$(this).children().attr('class','fa fa-star');
    	}
    	//是否隐藏
    	var storeActive = $('.content5_head .storedmsg').attr('class')=="storedmsg"?"no":"yes" ;
    	if(storeActive=="yes"){
    		//隐藏该条消息
    		$(this).parent().parent().parent().parent().parent().attr('hidden',true) ;
    	}
    	$.ajax({
    		url:"/wxServer/pc/manage/msg/store",
    		data:{
    			msgid:msgid,
    			isstored:isstored
    		},
    		success:function(){
    			if(1==isstored){
    				alert('搜藏成功') ;
    			}else{
    				alert('取消搜藏成功') ;
    			}
    		}
    	});
    });
    
    //全部消息点击事件
    $(document).on('click','.content5_head .allmsg',function(){
    	$('.content5_head .allmsg').attr('class','allmsg active');
    	$('.content5_head .storedmsg').attr('class','storedmsg') ;
    	var days = $('.content5 select option:selected').val() ;
    	loadDaysMsg(days,"all") ;
    });
    //时间点击事件
    $(document).on('change','.content5 select',function(){
    	var days = $(this).children('option:selected').val() ;
    	var activeScope = $('.content5_head .storedmsg').attr('class')=="storedmsg"?"all":"some" ;
    	loadDaysMsg(days,activeScope) ;
    })
    //搜藏消息点击事件
    $(document).on('click','.content5_head .storedmsg',function(){
    	$(this).attr('class','storedmsg active')
    	$('.content5_head .allmsg').attr('class','allmsg') ;
    	var days = $('.content5 select option:selected').val() ;
    	loadDaysMsg(days,"some") ;
    })
})();