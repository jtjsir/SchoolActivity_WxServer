(function () {
    var that = me.define("ship2", {
        ctrl: function () {
        	//加载页面
        	createHtml() ;
        	//bind event
        	bindSearchEve() ;
        },
        activity_detail:function(){
            me.show("activity_detail", {
                showType: 0
            });
        }

    });
    
    var bindSearchEve = function(){
    	$(document).on('click','.main .search .shipsearchbtn',function(){
    		var title = $(this).parent().children('.text').val() ;
    		$.ajax({
        		url:"/wxServer/pc/manage/activity/query/some",
        		data:{
        			msgType:"letter",
        			title:title
        		},
        		success:function(data){
        			if(data==null||"null"==data||"[]"==data){
        				console.log('没有人投稿');
        				$('.activity_simple').html('没有人进行投稿~~');
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
    		var str = "<li class='ship2'>" 
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
    			msgType:"letter"
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
    $(document).on('click','.main .activity_simple .ship2',function(){
    	var title = $(this).children().children('.activity_title').text() ;
    	$.ajax({
    		url:"/wxServer/pc/index/tpl/activity_detail",
    		success:function(data){
    			$('#main').html(data) ;
    			
//    			$('.activity_type').append("<input type='hidden' value='" + title + "'/>");
    			//根据title得到letter_info
    			$.ajax({
    				url:"/wxServer/pc/manage/activity/query/letter",
    				data:{
    					title:title
    				},
    				success:function(data){
    					if(data=="null"||data==null){
    						alert("查询不到相应的投稿信息") ;
    						$('.activity_type').html("查询不到相应的投稿信息");
    					}else{
    						var json = JSON.parse(data) ;
    						$('.activity_type_content input[name="title"]').val(json.title) ;
    						$('.activity_type_content input[name="author"]').val("秋秋秋") ;
    						$('.activity_type_content textarea[name="content"]').val(json.content) ; ;
    						$('.activity_type_content input[name="address"]').val(json.address) ;
    						$('.activity_type_content input[name="time"]').val(json.time) ;
    					}
    				}
    			});
    		}
    	});
    });
})();