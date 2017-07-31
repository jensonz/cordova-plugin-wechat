package org.apache.cordova.wechat;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.tencent.mm.sdk.modelbiz.JumpToBizProfile;
import com.tencent.mm.sdk.modelbiz.JumpToBizWebview;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by jenson on 2017/7/28.
 */

public class WechatUtils extends CordovaPlugin {

    public static final String APP_ID = "wxd930ea5d5a258f4f";//应用唯一标识，在微信开放平台提交应用审核通过后获得
    public static final String SECRET="";//应用密钥AppSecret，在微信开放平台提交应用审核通过后获得


    private static  CallbackContext callbackContext;
    private IWXAPI wxAPI;

    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
        initWXAPI();
    }

    /***
     * init wechat api
     */
    protected void initWXAPI() {
        if (wxAPI == null) {
            wxAPI = WXAPIFactory.createWXAPI(cordova.getActivity(), APP_ID, true);
            wxAPI.registerApp(APP_ID);
        }
    }

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("wakeupWechat".equals(action)) {
            JSONObject r = new JSONObject();
            r.put("value","work");
            WechatUtils.callbackContext = callbackContext;
            AlertDialog.Builder builder = new AlertDialog.Builder(cordova.getActivity());
            builder.setTitle("提示");
            builder.setMessage(args.getString(0)+" work");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    WechatUtils.callbackContext.success("点击了确定");
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    WechatUtils.callbackContext.error("点击了取消");
                }
            });
            builder.create().show();
        }else  if("openPublicNumberWithWechat".equals(action)){
            JumpToBizProfile.Req req = new JumpToBizProfile.Req();
            req.toUserName = " gh_74a0677cf94f "; //公众号原始ID
            req.profileType = JumpToBizProfile.JUMP_TO_NORMAL_BIZ_PROFILE;
            req.extMsg = "extMsg";
            wxAPI.sendReq(req);
        }else  if("openUrlWithWechat".equals(action)){
            JumpToBizWebview.Req req = new JumpToBizWebview.Req();
            req.toUserName="http://www.baidu.com";
            req.webType=0;

            req.extMsg="open url";

            wxAPI.sendReq(req);
        }
        else {
            callbackContext.error("Faild");
            return false;
        }
        return super.execute(action, args, WechatUtils.callbackContext);
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
