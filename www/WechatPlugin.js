cordova.define("cordova-plugin-wechat.WechatPlugin", function(require, exports, module) {

var exec = require('cordova/exec');

module.exports = {
    wakeupWechat: function(content){
        exec(
        function(info){//成功回调function
            console.log(info);
        },
        function(message){//失败回调function
            console.log(message);
        },
        "WechatUtils",//feature name
        "wakeupWechat",//action
        [content]//要传递的参数，json格式
        );
    },
    openPublicNumberWithWechat:function(content){
        exec(
        function(info){//成功回调function
           console.log(info);
        },
        function(message){//失败回调function
           console.log(message);
        },
        "WechatUtils",//feature name
        "openPublicNumberWithWechat",//action
        [content]//要传递的参数，json格式
        );
    },
    openUrlWithWechat:function(content){
       exec(
       function(info){//成功回调function
             console.log(info);
       },
       function(message){//失败回调function
             console.log(message);
       },
       "WechatUtils",//feature name
       "openUrlWithWechat",//action
       [content]//要传递的参数，json格式
       );
    }
};

});
