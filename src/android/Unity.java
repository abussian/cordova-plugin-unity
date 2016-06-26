package android;

import org.apache.cordova.LOG;
import ru.andremoniy.jcocoa.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import static ru.andremoniy.jcocoa.MathEx.*;
import static ru.andremoniy.jcocoa.Utils.*;
import static ru.andremoniy.jcocoa.NSException.*;
import static ru.andremoniy.jcocoa.Constants.*;
import static ru.andremoniy.jcocoa.Frameworks.Kernel.Versions.A.Headers.mach.Imessage.*;

public class Unity {
	public CDVPlugin initWithWebView(UIWebView theWebView) {
		LOG.d("initWithWebView: theWebView");
		objc_msgSend(this, "initWithWebView", null, theWebView);
		if (logic(this)) {
			_appDelegate = (UnityAppController) objc_msgSend(objc_msgSend(UIApplication, "sharedApplication", null),
					"delegate", null);
			_unityView = objc_field(_appDelegate, "unityView");
			_rootView = objc_field(_appDelegate, "rootView");
		}
		return (CDVPlugin) (this);
	}

	public void show(CDVInvokedUrlCommand command) {
		CDVPluginResult pluginResult = (CDVPluginResult) (null);
		Double x = (double) (objc_msgSend(objc_msgSend(command, "argumentAtIndex", null, 0), "floatValue", null));
		Double y = (double) (objc_msgSend(objc_msgSend(command, "argumentAtIndex", null, 1), "floatValue", null));
		Double width = (double) (objc_msgSend(objc_msgSend(command, "argumentAtIndex", null, 2), "floatValue", null));
		Double height = (double) (objc_msgSend(objc_msgSend(command, "argumentAtIndex", null, 3), "floatValue", null));
		LOG.d("show: [%f,%f,%f,%f]", x, y, width, height);
		CGRect unityFrame = (CGRect) (CGRectMake(x, y, width, height));
		_set(_unityView, "frame", unityFrame);
		objc_msgSend(_rootView, "addSubview", null, _unityView);
		pluginResult = (CDVPluginResult) (objc_msgSend(CDVPluginResult, "resultWithStatusmessageAsBool", null,
				CDVCommandStatus_OK, true));
		objc_msgSend(this.commandDelegate, "sendPluginResultcallbackId", null, pluginResult,
				objc_field(command, "callbackId"));
	}

	public void hide(CDVInvokedUrlCommand command) {
		CDVPluginResult pluginResult = (CDVPluginResult) (null);
		LOG.d("hide:");
		objc_msgSend(_unityView, "removeFromSuperview", null);
		pluginResult = (CDVPluginResult) (objc_msgSend(CDVPluginResult, "resultWithStatusmessageAsBool", null,
				CDVCommandStatus_OK, true));
		objc_msgSend(this.commandDelegate, "sendPluginResultcallbackId", null, pluginResult,
				objc_field(command, "callbackId"));
	}

	public void pause(CDVInvokedUrlCommand command) {
		CDVPluginResult pluginResult = (CDVPluginResult) (null);
		bool state = (bool) (objc_msgSend(objc_msgSend(command, "argumentAtIndex", null, 0), "boolValue", null));
		LOG.d("pause: [%d]", logic(state) ? 1 : 0);
		UnityPause(state);
		pluginResult = (CDVPluginResult) (objc_msgSend(CDVPluginResult, "resultWithStatusmessageAsBool", null,
				CDVCommandStatus_OK, true));
		objc_msgSend(this.commandDelegate, "sendPluginResultcallbackId", null, pluginResult,
				objc_field(command, "callbackId"));
	}

	public void sendMessage(CDVInvokedUrlCommand command) {
		CDVPluginResult pluginResult = (CDVPluginResult) (null);
		String message = (String) (objc_msgSend(command.arguments, "objectAtIndex", null, 0));
		LOG.d("sendMessage: [%@]", message);
		UnitySendMessage("DeviceCommunication", "MobileToUnity", objc_msgSend(message._static, "UTF8String", null));
		pluginResult = (CDVPluginResult) (objc_msgSend(CDVPluginResult, "resultWithStatusmessageAsBool", null,
				CDVCommandStatus_OK, true));
		objc_msgSend(this.commandDelegate, "sendPluginResultcallbackId", null, pluginResult,
				objc_field(command, "callbackId"));
	}

	public Unity init() {
		return this;
	}

	public Unity autorelease() {
		return this; // TODO
	}

	public Unity release() {
		return this; // TODO
	}

}
