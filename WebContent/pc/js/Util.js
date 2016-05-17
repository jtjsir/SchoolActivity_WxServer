var Util = (function () {
	var that;
	var obj = function () {
		this.ajaxCount = 0;
		that = this;
		that.popId = [];

	};

	obj.prototype = {
		getQueryString: function (name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var r = window.location.search.substr(1).match(reg);
			if (r != null) return unescape(r[2]);
			return "";
		},

		info: function (msg, success) {
			$(".util_pop_info").remove();

			var innerDiv = document.createElement("div");
			innerDiv.className = "util_pop_info";
			if (success) {
				innerDiv.className += " success";
			}
			innerDiv.innerHTML = msg;

			document.body.appendChild(innerDiv);
			setTimeout(function () {
				$(".util_pop_info").remove();
			}, 3000);
		},

		getApiUrl: function (method, param) {
			var url;
			if (method && method.indexOf("http") >= 0)
				url = method;
			else {
				url = Config.api.replace("{method}", method);
			}

			if (param) {
				if (url.indexOf("?") < 0) url += "?";
				for (var key in param) {
					url += key + "=" + param[key] + "&";
				}
				url = url.substring(0, url.length - 1);
			}
			return url;
		},

		ajax: function (option, callback, noloading) {
		    if (!noloading) that.showLoading();

		    var startTime = new Date();
		    me.ajax(option, function (data) {
		        if (!noloading) that.hideLoading();

		        if (me._param.config.debug) {
		        	console.group && console.group("ajax");
		        	console.log("%c链接", "font-weight:bold;");
		        	console.log(option.url);
		        	console.log("%c参数", "font-weight:bold;");
		        	console.log(JSON.stringify(option.data))
		        	console.log("%c返回", "font-weight:bold;");
		        	console.log(data.data);
		        	console.log("%c执行时间 " + (new Date() - startTime), "font-weight:bold;");
		        	console.groupEnd && console.groupEnd();
		        }
		        
		        if (data.resultCode) {
		            data.resultMsg && Util.info(data.resultMsg);

		            if (data.resultCode == 100) {
		        		location.replace("login.html");
		        		return;
		        	}

		        	return;
		        }

		        callback(data.data);
		    }, function (msg, status) {
		        if (!noloading) that.hideLoading();
		        if (status != 0) Util.info("请重试");
		    });
		},

		showLoading: function () {
			this.ajaxCount++;

			var loading = document.getElementById("loading");
			if (!loading) {
				loading = document.createElement("div");
				loading.innerHTML = "<img src='images/loading.gif' />";
				loading.id = "loading";
				document.body.appendChild(loading);
			}
		},

		hideLoading: function () {
			this.ajaxCount--;
			if (this.ajaxCount <= 0) {
				var loading = document.getElementById("loading");
				if (loading) {
					document.body.removeChild(loading);
				}
				this.ajaxCount = 0;
			}
		},

		toMap: function(arr, idField){
			if (!arr) return {};

			var map = {};
			arr.forEach(function(item){
				map[item[idField]] = item;
			});
			return map;
		},

		isPhone: function(phone) {
			return /^1[358]\d{9}$/.test(phone);
		},

		isNum: function (num) {
			return /^\d+(\.\d+)?$/.test(num);
		},

		page: function (arr, pageIndex, pageSize) {
			if (!arr || arr.length == 0) return [];

			arr = angular.copy(arr);
			return arr.splice(pageIndex * pageSize, pageSize);
		},

		decentString: function (str) {
			return /^[a-zA-Z_\d]+$/.test(str);
		},

		decentStringZH: function (str) {
			return /^[a-zA-Z_\d\u4e00-\u9fa5]+$/.test(str);
		},

		idnumber: function (str) {
			var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
			return reg.test(str);
		},

		isMail: function (str) {
			return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(str);
		},

		doUploadFile: function (data, accept, maxSize, callBack) {
		    File.upload({
		        multi: false,
		        url: Config.upload_api,
		        param: data,
		        accept: accept,
		        before: function (files) {
		            if(!maxSize){
		            	if (files[0].size > maxSize * 1024) {
		            		Util.info("图像大小不能超过"+maxSize+"KB");
		            		return false;
		            	}
		            }
		            Util.showLoading();
		        },
		        after: function (data) {
		            Util.hideLoading();
		            data = JSON.parse(data).data;
		            callBack && callBack(data);
		        },
		        error: function () {
		            Util.hideLoading();
		            Util.info("上传失败");
		        },
		        progress: function () { }
		    });
		},
	};

	return new obj();
})();

