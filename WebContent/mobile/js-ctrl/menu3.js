(function () {
    var that = me.define("menu3", {
        ctrl: function () {

        },

        interest:function(){
            me.show("interest", {
                showType: 0
            });
        },
        reserve:function(){
          me.show("reserve",{
              showType:0
          })
        },
        mine_history:function(){
            me.show("mine_history",{
                showType:0
            })
            //历史活动
            $.ajax({
            	
            })
        }
    });
})();