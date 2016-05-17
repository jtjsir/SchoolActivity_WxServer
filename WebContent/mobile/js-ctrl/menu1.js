(function () {
    var that = me.define("menu1", {
        ctrl: function () {
            that.menu1();
        },
        menu1:function(){
            $.ajax({
                url:"/wxServer/mobile/manage/act/query/near/all",
                type:"get",
                success:function(data){
                	if(null==data||"null"==data){
                		alert("没有相应的数据！") ;
                	}else{
                    var act = $("#menu1 .activity") ;
                    act.html('');
                    var json = JSON.parse(data);
                    var size = json.length;
                    for(var i = 0;i<size;i++){
                        var id = json[i].id;
                        var title = json[i].title;
                        var content = json[i].content;
                        var img = json[i].url;
                        var address = json[i].address;
                        var time = json[i].time;
                        var type = json[i].type;
                        var status = json[i].status;
                        var people = json[i].people;
                        var limit = json[i].limit;
                        var actdata = " <li id='"+ title +"'>"
                            +" <div class='active-condition red'>"
                            +(status==0?"即将开始":(status==1?"正在进行":"已经结束"))
                            +"</div>"
                            +" <img src='" + img +"'>"
                            +"  <div class='activity-tittle'><h4>"
                            + title
                            +"</h4></div>"
                            +"<span class='detail'><i class='fa fa-calendar'></i> "
                            + time
                            + "</span></br>"
                            +" <span class='detail'><i class='fa fa-map-marker'></i>"
                            +address
                            +"</span>"
                            +"<div class='follow red'><span><i class='fa fa-heart-o'></i>120</span></div>"
                            + "</li>"
                        act.append(actdata);
                    }
                	}
                }
            })
        }
    });
    
    $(document).on("click",".content .search form input[name='button']",function(){
    	var query = $(".content .search form .search-text").val() ;
    	if(null==query||""==query){
    		alert("请输入相应的查询数据！") ;
    	}else{
    		$.ajax({
    			url:"/wxServer/mobile/manage/act/query/title",
    			data:{
    				act_title:query
    			},
    			success:function(data){
    				if(data==null||"null"==data){
    					alert("没有相应的数据！") ;
    				}else{
    					var act = $("#menu1 .activity") ;
                        act.html('');
                        var json = JSON.parse(data);
                            var id = json.id;
                            var title = json.title;
                            var content = json.content;
                            var img = json.url;
                            var address = json.address;
                            var time = json.time;
                            var type = json.type;
                            var status = json.status;
                            var people = json.people;
                            var limit = json.limit;
                            var actdata = " <li id='"+ title +"'>"
                                +" <div class='active-condition red'>"
                                +(status==0?"即将开始":(status==1?"正在进行":"已经结束"))
                                +"</div>"
                                +" <img src='" + img +"'>"
                                +"  <div class='activity-tittle'><h4>"
                                + title
                                +"</h4></div>"
                                +"<span class='detail'><i class='fa fa-calendar'></i> "
                                + time
                                + "</span></br>"
                                +" <span class='detail'><i class='fa fa-map-marker'></i>"
                                +address
                                +"</span>"
                                +"<div class='follow red'><span><i class='fa fa-heart-o'></i>120</span></div>"
                                + "</li>"
                            act.append(actdata);
    				}
    			}
    		});
    	}
    });
})();

$(document).ready(function(){
    $.ajax({
        url:"/wxServer/mobile/manage/act/query/near/all",
        type:"get",
        success:function(data){
            var act = $("#menu1 .activity") ;
            var json = JSON.parse(data);
            var size = json.length;
            for(var i = 0;i<size;i++){
                var id = json[i].id;
                var title = json[i].title;
                var content = json[i].content;
                var img = json[i].url;
                var address = json[i].address;
                var time = json[i].time;
                var type = json[i].type;
                var status = json[i].status;
                var people = json[i].people;
                var limit = json[i].limit;
                var actdata = "<li id='"+ title +"'>"
                    +" <div class='active-condition red'>"
                    +(status==0?"即将开始":(status==1?"正在进行":"已经结束"))
                    +"</div>"
                    +" <img src='" + img +"'>"
                    +"  <div class='activity-tittle'><h4>"
                    + title
                    +"</h4></div>"
                    +"<span class='detail'><i class='fa fa-calendar'></i> "
                    + time
                    + "</span></br>"
                    +" <span class='detail'><i class='fa fa-map-marker'></i>"
                    +address
                    +"</span>"
                        +"<div class='follow red'><span><i class='fa fa-heart-o'></i>120</span></div>"
                    + "</li>"
                act.append(actdata);
            }

        }

    })

})