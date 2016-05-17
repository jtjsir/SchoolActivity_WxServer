(function () {
    var that = me.define("ship1", {
        ctrl: function () {
        	//加载页面
        	createHtml() ;
        	//bind event
        	bindSearchEve() ;
        },
        activity_history:function(){
            me.show("activity_history", {
                showType: 0
            });
        }

    });
    var bindSearchEve = function(){
    	$(document).on('click','.main .search .ship1searchbtn',function(){
    		var title = $(this).parent().children('.text').val() ;
    		$.ajax({
        		url:"/wxServer/pc/manage/activity/query/some",
        		data:{
        			msgType:"news",
        			title:title,
        			status:"1"
        		},
        		success:function(data){
        			if(data==null||"null"==data||data=="[]"){
        				console.log('没有相应的活动');
        				$('.activity_simple').html('没有相应的活动');
        			}else{
        				$('.activity_simple').html('') ;
        				decodeJsonToHtml(data) ;
        			}
        		}
        	});
    	});
    }
    
    var decodeJsonToHtml = function(data){
    	var appendNode = $('.activity_simple') ;
    	appendNode.html('');
    	var json = JSON.parse(data) ;
    	var len = json.length ;
    	for(var i = 0; i <len ;i++){
    		var str = "<li class='ship1'>" 
    			+"<a>"
    			+"<p class='activity_time'>" + json[i].time + "</p>"
    			+"<p class='activity_title'>" + json[i].title + "</p>"
    			+"<img src='" +json[i].url +  "'>"
    			+"<span class='activity_abs'>" + json[i].content.substring(0,5) + ".."+"</span>"
    			+"</a>"
    			+"</li>";
    		appendNode.append(str) ;
    	}
    }
    
    var createHtml = function(){
    	$.ajax({
    		url:"/wxServer/pc/manage/activity/query/all",
    		data:{
    			msgType:"news",
    			status:"1"
    		},
    		success:function(data){
    			if(data==null||"null"==data){
    				console.log('没有人投稿');
    				$('.activity_simple').html('没有人进行投稿~~');
    			}else{
    				decodeJsonToHtml(data) ;
    			}
    		}
    	});
    }
    
  //为li添加事件
    $(document).on('click','.main .activity_simple .ship1',function(){
    	var title = $(this).children().children('.activity_title').text() ;
    	$.ajax({
    		url:"/wxServer/pc/index/tpl/activity_history",
    		success:function(data){
    			$('#main').html(data) ;
    			
    			//已发送的活动不允许提交修改只可删除和发布公告
    			$('.historysavebtn .button_gray').attr('disabled',true) ;
    			$('.historysavebtn .button').attr('disabled',true) ;
    			//根据title得到wxnews
    			$.ajax({
    				url:"/wxServer/pc/manage/activity/query/news",
    				data:{
    					title:title
    				},
    				success:function(data){
    					if(data=="null"||data==null){
    						alert("查询不到相应的活动信息") ;
    						$('.activity_type').html("查询不到相应的活动信息");
    					}else{
    						var json = JSON.parse(data) ;
    						$('.activity_type_content input[name="title"]').val(json.title) ;
    						$('.activity_type_content').append('<input type="hidden" value="' + json.title + '"/>');
    						$('.activity_type_content input[name="title"]').attr('disabled',true) ;
    						$('.activity_type_content input[name="author"]').val(json.author) ;
    						$('.activity_type_content input[name="author"]').attr('disabled',true) ;
    						$('.activity_type_content textarea[name="content"]').val(json.content) ;
    						$('.activity_type_content textarea[name="content"]').attr('disabled',true) ;
    						$('.activity_type_content input[name="address"]').val(json.address) ;
    						$('.activity_type_content input[name="address"]').attr('disabled',true) ;
    						$('.activity_type_content input[name="time"]').val(json.time) ;
    						$('.activity_type_content input[name="time"]').attr('disabled',true) ;
    					}
    				}
    			});
    		}
    	});
    });
})();