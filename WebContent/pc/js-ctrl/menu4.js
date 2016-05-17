(function () {
    var that = me.define("menu4", {
        ctrl: function () {
        	loadHeadNum() ;
        },

        ship1:function(){
            me.show("ship1", {
                showType: 0
            });
        },
        ship2:function(){
            me.show("ship2",{
                showType:0
            })
        },
        ship3:function(){
            me.show("ship3",{
                showType:0
            })
        }
    });
    
    //加载头部数目
    var loadHeadNum = function(){
    	//已发送条数
    	var alreadySend = $('.content5_head .alreadySend') ;
    	var acceptLetter = $('.content5_head .acceptLetter') ;
    	var readySend = $('.content5_head .readySend') ;
    	$.ajax({
			url:"/wxServer/pc/manage/activity/count/newsactivity",
			success:function(data){
				var json = JSON.parse(data) ;
				var newsSended = json.newsSended ;
				var newsNotSend = json.newsNotSend ;
				var letterinfo = json.letterinfo ;
				alreadySend.children('span').text(newsSended);
				acceptLetter.children('span').text(letterinfo);
				readySend.children('span').text(newsNotSend);
			}
		});
    }
    
//    //bind头部事件
//    var bindHeadEvent = function(){
//    	var alreadySend = '.content5_head .alreadySend';
//    	var acceptLetter = '.content5_head .acceptLetter' ;
//    	var readySend = $('.content5_head .readySend') ;
//    	
//    	//已发送事件
//    	$(document).on('click',alreadySend,function(){
//    		
//    	});
//    	//收到投稿消息事件
//    	$(document).on('click',acceptLetter,function(){
//    		
//    	});
//    	
//    	//活动素材事件
//    	$(document).on('click',readySend,function(){})
//    }
})();