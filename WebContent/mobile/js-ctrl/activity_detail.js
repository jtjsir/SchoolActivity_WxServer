(function () {
    var that = me.define("activity_detail", {
        ctrl: function () {

        },
        user_detail:function(){
            me.show("user_detail",{
                showType:0
            });
        }
    });
    //menu1的点击进入详情
    $(document).on("click","#menu1 .activity li",function(){
        var title = $(this).attr("id") ;
        $.ajax({
            url:"/wxServer/mobile/index/tpl/activity_detail",
            type:"GET",
            success:function(data){
                $('#main').html(data) ;
                $.ajax({
                    url:"/wxServer/mobile/manage/act/query/title",
                    type:"get",
                    data:{
                        act_title:title
                    },
                    success:function(data){
                        if(data==null||"null"==data){
                            alert("没有相应的数据！") ;
                        }else{
                            var act = $(".content .reserve-detail") ;
                            act.html('') ;
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
                            var actdata = ""
                                +" <div class='active-condition red'>"
                                +(status==0?"即将开始":(status==1?"正在进行":"已经结束"))
                                +"</div>"
                                +" <img src='" + img +"'><div>"
                                +"  <div class='activity-tittle'><h4>"
                                + title
                                +"</h4></div>"
                                +"<span class='detail'><i class='fa fa-calendar'></i> "
                                + time
                                + "</span></br>"
                                +" <span class='detail'><i class='fa fa-map-marker'></i>"
                                +address
                                +"</span>"
                                +"<div class='detail follow'><i class='fa fa-heart-o red'></i>(已关注<span class='attention_num'>120</span>人，点击❤<span class='notice-text'>进行关注</span>)</div>"
                                + "</div>"
                                +"<div class='activity-detail'>"
                                +"<p>"
                                +content
                                +"</p>"
                                +"<div>"
                                +"<button class='button' ng-click='user_detail()' id='bookbtn'>我要预约<button></div>"
                            act.append(actdata);

                        }
                        $.ajax({
                        	url:"/wxServer/mobile/activity/booking/limit",
                        	data:{
                        		act_title:title
                        	},
                        	success:function(data){
                        		if(data=="0"){
                        			$("#bookbtn").attr('hidden',true);
                        		}
                        		
                        	}
                        })
                      //获取点赞数
                        $.ajax({
                        	url:"/wxServer/mobile/activity/noticeCount",
                        	data:{
                        		act_title:title
                        	},
                        	success:function(data){
                        		$('.attention_num').text(data) ;
                        	}
                        });
                        //判断是否关注过 改变样式
                        $.ajax({
                            url:"/wxServer/mobile/activity/isnotice",
                            data:{
                                act_title:title
                            },
                            success:function(data){
                                if(data==0||data=="0"){
                                    //没关注过
                                    $(".follow i").attr("class","fa fa-heart-o red") ;
                                    $('.notice-text').text('进行关注') ;
                                }else if(data==1||data=="1"){
                                    //关注过
                                    $('.follow i').attr('class','fa fa-heart red') ;
                                    $('.notice-text').text('取消关注') ;
                                }
                            }
                        });
                        //判断是否预约过 改变按钮样式
                        $.ajax({
                            url:"/wxServer/mobile/activity/isbooked",
                            data:{
                                act_title:title
                            },
                            success:function(data){
                                if(0==data||"0"==data){
                                    //没预约过
                                	$('#bookbtn').text('立即预约') ;
                                }else if("1"==data||1==data){
                                    //预约过
                                    $('#bookbtn').text('取消预约') ;
                                }
                            }
                        });

                        //预约/取消预约
                        $(document).on("click","#bookbtn",function(){
                            var text = $('#bookbtn').text() ;
                            var booked = 1 ;
                            if(text=="取消预约"){
                                booked = 0 ;
                            }
                            $.ajax({
                                url:"/wxServer/mobile/activity/booking",
                                data:{
                                    act_title:title,
                                    isbooked:booked
                                },
                                success:function(data){
                                	if("0"==data){
                                		$('#bookbtn').attr('hidden',true) ;
                                	}else if("1"==data){
                                		$('#bookbtn').text('人已满，无法预约') ;
                                		$('#bookbtn').attr('disabled',true) ;
                                	}else{
                                		if(1==booked){
                                			$("#bookbtn").text("取消预约");
                                			alert("预约成功") ;
                                		}else{
                                			$("#bookbtn").text("立即预约") ;
                                			alert("取消预约成功") ;
                                		}
                                	}
                                }
                            })
                        });

                        //关注/取消关注
                        $(document).on("click",".follow i",function(){
                            var isnotice ;
                            //得到样式
                            var bookclass = $('.follow i').attr('class') ;
                            if(bookclass==null||bookclass=="undefied"||bookclass=='fa fa-heart-o red'){
                                //点击关注
                                $('.follow i').attr('class','fa fa-heart red') ;
                                $('.notice-text').text("取消关注") ;
                                $('.attention_num').text(parseInt($('.attention_num').text())+1) ;
                                isnotice = 1 ;
                            }else{
                                //点击取消关注
                                $('.follow i').attr('class','fa fa-heart-o red') ;
                                $('.notice-text').text("进行关注") ;
                                $('.attention_num').text(parseInt($('.attention_num').text())-1) ;
                                isnotice = 0 ;
                            }

                            $.ajax({
                                url:"/wxServer/mobile/activity/notice",
                                data:{
                                    act_title:title,
                                    isnotice:isnotice
                                },
                                success:function(){
                                }
                            })
                        });
                    }
                })
            }
        });
    });
  //menu2的点击进入详情
    $(document).on("click","#menu2 .activity li",function(){
        var title = $(this).attr("id") ;
        $.ajax({
            url:"/wxServer/mobile/index/tpl/activity_detail",
            type:"GET",
            success:function(data){
                $('#main').html(data) ;
                $.ajax({
                    url:"/wxServer/mobile/manage/act/query/title",
                    type:"get",
                    data:{
                        act_title:title
                    },
                    success:function(data){
                        if(data==null||"null"==data){
                            alert("没有相应的数据！") ;
                        }else{
                            var act = $(".content .reserve-detail") ;
                            act.html('') ;
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
                            var actdata = ""
                                +" <div class='active-condition red'>"
                                +(status==0?"即将开始":(status==1?"正在进行":"已经结束"))
                                +"</div>"
                                +" <img src='" + img +"'><div>"
                                +"  <div class='activity-tittle'><h4>"
                                + title
                                +"</h4></div>"
                                +"<span class='detail'><i class='fa fa-calendar'></i> "
                                + time
                                + "</span></br>"
                                +" <span class='detail'><i class='fa fa-map-marker'></i>"
                                +address
                                +"</span>"
                                +"<div class='detail follow'><i class='fa fa-heart-o red'></i>(已关注<span class='attention_num'>120</span>人，点击❤<span class='notice-text'>进行关注</span>)</div>"
                                + "</div>"
                                +"<div class='activity-detail'>"
                                +"<p>"
                                +content
                                +"</p>"
                                +"<div>"
                            act.append(actdata);

                        }
                        //获取点赞数
                        $.ajax({
                        	url:"/wxServer/mobile/activity/noticeCount",
                        	data:{
                        		act_title:title
                        	},
                        	success:function(data){
                        		$('.attention_num').text(data) ;
                        	}
                        });
                      //判断是否关注过 改变样式
                        $.ajax({
                            url:"/wxServer/mobile/activity/isnotice",
                            data:{
                                act_title:title
                            },
                            success:function(data){
                                if(data==0||data=="0"){
                                    //没关注过
                                    $(".follow i").attr("class","fa fa-heart-o red") ;
                                    $('.notice-text').text('进行关注') ;
                                }else if(data==1||data=="1"){
                                    //关注过
                                    $('.follow i').attr('class','fa fa-heart red') ;
                                    $('.notice-text').text('取消关注') ;
                                }
                            }
                        });
                   
                        //关注/取消关注
                        $(document).on("click",".follow i",function(){
                        	 var isnotice ;
                             //得到样式
                             var bookclass = $('.follow i').attr('class') ;
                             if(bookclass==null||bookclass=="undefied"||bookclass=='fa fa-heart-o red'){
                                 //点击关注
                                 $('.follow i').attr('class','fa fa-heart red') ;
                                 $('.notice-text').text("取消关注") ;
                                 $('.attention_num').text(parseInt($('.attention_num').text())+1) ;
                                 isnotice = 1 ;
                             }else{
                                 //点击取消关注
                                 $('.follow i').attr('class','fa fa-heart-o red') ;
                                 $('.notice-text').text("进行关注") ;
                                 $('.attention_num').text(parseInt($('.attention_num').text())-1) ;
                                 isnotice = 0 ;
                             }

                            $.ajax({
                                url:"/wxServer/mobile/activity/notice",
                                data:{
                                    act_title:title,
                                    isnotice:isnotice
                                },
                                success:function(){
                                }
                            })
                        });
                    }
                });
            }
        });
    });
    //我的关注的点击进入详情
    $(document).on("click",".my-interest ul li",function(){
        var title = $(this).attr("id") ;
        $.ajax({
            url:"/wxServer/mobile/index/tpl/activity_detail",
            type:"GET",
            success:function(data){
                $('#main').html(data) ;
                $.ajax({
                    url:"/wxServer/mobile/manage/act/query/title",
                    type:"get",
                    data:{
                        act_title:title
                    },
                    success:function(data){
                        if(data==null||"null"==data){
                            alert("没有相应的数据！") ;
                        }else{
                            var act = $(".content .reserve-detail") ;
                            act.html('') ;
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
                            var actdata = ""
                                +" <div class='active-condition red'>"
                                +(status==0?"即将开始":(status==1?"正在进行":"已经结束"))
                                +"</div>"
                                +" <img src='" + img +"'><div>"
                                +"  <div class='activity-tittle'><h4>"
                                + title
                                +"</h4></div>"
                                +"<span class='detail'><i class='fa fa-calendar'></i> "
                                + time
                                + "</span></br>"
                                +" <span class='detail'><i class='fa fa-map-marker'></i>"
                                +address
                                +"</span>"
                                +"<div class='detail follow'><i class='fa fa-heart-o red'></i>(已关注<span class='attention_num'>120</span>人，点击❤<span class='notice-text'>进行关注</span>)</div>"
                                + "</div>"
                                +"<div class='activity-detail'>"
                                +"<p>"
                                +content
                                +"</p>"
                                +"<div>"
                                +"<button class='button' ng-click='user_detail()' id='bookbtn'>我要预约<button></div>"
                            act.append(actdata);

                        }
                      //获取点赞数
                        $.ajax({
                        	url:"/wxServer/mobile/activity/noticeCount",
                        	data:{
                        		act_title:title
                        	},
                        	success:function(data){
                        		$('.attention_num').text(data) ;
                        	}
                        });
                        //判断是否关注过 改变样式
                        $.ajax({
                            url:"/wxServer/mobile/activity/isnotice",
                            data:{
                                act_title:title
                            },
                            success:function(data){
                                if(data==0||data=="0"){
                                    //没预约过
                                    $(".follow i").attr("class","fa fa-heart-o red") ;
                                }else if(data==1||data=="1"){
                                    //预约过
                                    $('.follow i').attr('class','fa fa-heart red') ;
                                }
                            }
                        });
                        //判断是否关注过 改变按钮样式
                        $.ajax({
                            url:"/wxServer/mobile/activity/isbooked",
                            data:{
                                act_title:title
                            },
                            success:function(data){
                                if(0==data||"0"==data){
                                    //没预约过
                                	$('#bookbtn').text('立即预约') ;
                                }else if("1"==data||1==data){
                                    //预约过
                                    $('#bookbtn').text('取消预约') ;
                                }
                            }
                        });

                        //预约/取消预约
                        $(document).on("click","#bookbtn",function(){
                            var text = $('#bookbtn').text() ;
                            var booked = 1 ;
                            if(text=="取消预约"){
                                booked = 0 ;
                            }
                            $.ajax({
                                url:"/wxServer/mobile/activity/booking",
                                data:{
                                    act_title:title,
                                    isbooked:booked
                                },
                                success:function(){
                                    if(1==booked){
                                        $("#bookbtn").text("取消预约");
                                        alert("预约成功") ;
                                    }else{
                                        $("#bookbtn").text("我要预约") ;
                                        alert("取消预约成功") ;
                                    }
                                }
                            })
                        });

                        //关注/取消关注
                        $(document).on("click",".follow i",function(){
                            var isnotice ;
                            //得到样式
                            var bookclass = $('.follow i').attr('class') ;
                            if(bookclass==null||bookclass=="undefied"||bookclass=='fa fa-heart-o red'){
                                //点击关注
                                $('.follow i').attr('class','fa fa-heart red') ;
                                $('.notice-text').text("取消关注") ;
                                $('.attention_num').text(parseInt($('.attention_num').text())+1) ;
                                isnotice = 1 ;
                            }else{
                                //点击取消关注
                                $('.follow i').attr('class','fa fa-heart-o red') ;
                                $('.notice-text').text("进行关注") ;
                                $('.attention_num').text(parseInt($('.attention_num').text())-1) ;
                                isnotice = 0 ;
                            }

                            $.ajax({
                                url:"/wxServer/mobile/activity/notice",
                                data:{
                                    act_title:title,
                                    isnotice:isnotice
                                },
                                success:function(){
                                }
                            })
                        });
                    }
                });
            }
        });

        //我的预约的点击进入详情
        $(document).on("click",".my-interest ul li",function(){
            var title = $(this).attr("id") ;
            $.ajax({
                url:"/wxServer/mobile/index/tpl/activity_detail",
                type:"GET",
                success:function(data){
                    $('#main').html(data) ;
                    $.ajax({
                        url:"/wxServer/mobile/manage/act/query/title",
                        type:"get",
                        data:{
                            act_title:title
                        },
                        success:function(data){
                            if(data==null||"null"==data){
                                alert("没有相应的数据！") ;
                            }else{
                                var act = $(".content .reserve-detail") ;
                                act.html('') ;
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
                                var actdata = ""
                                    +" <div class='active-condition red'>"
                                    +(status==0?"即将开始":(status==1?"正在进行":"已经结束"))
                                    +"</div>"
                                    +" <img src='" + img +"'><div>"
                                    +"  <div class='activity-tittle'><h4>"
                                    + title
                                    +"</h4></div>"
                                    +"<span class='detail'><i class='fa fa-calendar'></i> "
                                    + time
                                    + "</span></br>"
                                    +" <span class='detail'><i class='fa fa-map-marker'></i>"
                                    +address
                                    +"</span>"
                                    +"<div class='detail follow'><i class='fa fa-heart-o red'></i>(已关注<span class='attention_num'>120</span>人，点击❤<span class='notice-text'>进行关注</span>)</div>"
                                    + "</div>"
                                    +"<div class='activity-detail'>"
                                    +"<p>"
                                    +content
                                    +"</p>"
                                    +"<div>"
                                    +"<button class='button' ng-click='user_detail()' id='bookbtn'>我要预约<button></div>"
                                act.append(actdata);

                            }
                            $.ajax({
                            	url:"wxServer/mobile/activity/booking/limit",
                            	data:{
                            		act_title:title
                            	},
                            	success:function(data){
                            		if(data=="0"){
                            			$("#bookbtn").attr("hidden",true);
                            		}
                            		
                            	}
                            })
                          //获取点赞数
                            $.ajax({
                            	url:"/wxServer/mobile/activity/noticeCount",
                            	data:{
                            		act_title:title
                            	},
                            	success:function(data){
                            		$('.attention_num').text(data) ;
                            	}
                            });
                            //判断是否关注过 改变样式
                            $.ajax({
                                url:"/wxServer/mobile/activity/isnotice",
                                data:{
                                    act_title:title
                                },
                                success:function(data){
                                    if(data==0||data=="0"){
                                        //没关注过
                                        $(".follow i").attr("class","fa fa-heart-o red") ;
                                    }else if(data==1||data=="1"){
                                        //关注过
                                        $('.follow i').attr('class','fa fa-heart red') ;
                                    }
                                }
                            });
                            //判断是否预约过 改变按钮样式
                            $.ajax({
                                url:"/wxServer/mobile/activity/isbooked",
                                data:{
                                    act_title:title
                                },
                                success:function(data){
                                    if(0==data||"0"==data){
                                        //没预约过
                                    	$('#bookbtn').text('立即预约') ;
                                    }else if("1"==data||1==data){
                                        //预约过
                                        $('#bookbtn').text('取消预约') ;
                                    }
                                }
                            });

                            //预约/取消预约
                            $(document).on("click","#bookbtn",function(){
                                var text = $('#bookbtn').text() ;
                                var booked = 1 ;
                                if(text=="取消预约"){
                                    booked = 0 ;
                                }
                                $.ajax({
                                    url:"/wxServer/mobile/activity/booking",
                                    data:{
                                        act_title:title,
                                        isbooked:booked
                                    },
                                    success:function(){
                                        if(1==booked){
                                            $("#bookbtn").text("取消预约");
                                            alert("预约成功") ;
                                        }else{
                                            $("#bookbtn").text("我要预约") ;
                                            alert("取消预约成功") ;
                                        }
                                    }
                                })
                            });

                            //关注/取消关注
                            $(document).on("click",".follow i",function(){
                                var isnotice ;
                                //得到样式
                                var bookclass = $('.follow i').attr('class') ;
                                if(bookclass==null||bookclass=="undefied"||bookclass=='fa fa-heart-o red'){
                                    //点击关注
                                    $('.follow i').attr('class','fa fa-heart red') ;
                                    $('.notice-text').text("取消关注") ;
                                    $('.attention_num').text(parseInt($('.attention_num').text())+1) ;
                                    isnotice = 1 ;
                                }else{
                                    //点击取消关注
                                    $('.follow i').attr('class','fa fa-heart-o red') ;
                                    $('.notice-text').text("进行关注") ;
                                    $('.attention_num').text(parseInt($('.attention_num').text())-1) ;
                                    isnotice = 0 ;
                                }

                                $.ajax({
                                    url:"/wxServer/mobile/activity/notice",
                                    data:{
                                        act_title:title,
                                        isnotice:isnotice
                                    },
                                    success:function(){
                                    }
                                })
                            });
                        }
                    });
                }
            });
        });

    });
})();