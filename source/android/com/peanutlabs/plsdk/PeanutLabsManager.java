package com.peanutlabs.plsdk;

import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

public class PeanutLabsManager implements IToolbarEventsHandler {
	private static PeanutLabsManager _instance;
	private static Map<String, String> _customVars = null;
	
	private IRewardsCenterEventsHandler _eventsHandler;
	private int _appId = -1;
	private String _appKey = "";
	private String _endUserId = "";
	private String _plUserId = "";
	private String _rewardsCenterUrl = "";
	private String _dob = "";
	private String _sex = "";
	
	private Activity _parentContext;

	private PeanutLabsManager() {
	}

	public static PeanutLabsManager getInstance() {
		if (_instance == null) {
			_instance = new PeanutLabsManager();
			_customVars = new HashMap<String, String>();
		}
		return _instance;
	}

	public void setApplicationId(int application_id) {
		_appId = application_id;
	}

	public void setApplicationKey(String key) {
		_appKey = key;
	}

	public void setEndUserId(String end_user_id) {
		_endUserId = end_user_id;
	}

	public void setUserId(String user_id) {
		_plUserId = user_id;
	}

	public void setRewardsCenterEventsHandler(IRewardsCenterEventsHandler object) {
		_eventsHandler = object;
	}

	public String getRewardCenterUrl() {
		return _rewardsCenterUrl;
	}

	public void setDob(String _dob) {
		this._dob = _dob;
	}

	public void setSex(String _sex) {
		this._sex = _sex;
	}
	
	public void addCustomParameters(String key, String value) {
		_customVars.put(key, value);
	}

	public void openRewardsCenter(Activity context) throws InvalidParameterException {
		_parentContext = context;
		if(_plUserId.equals("")) {
			_plUserId = generatePlUserId();
		}
		
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("http://www.peanutlabs.com/userGreeting.php?userId=").append(_plUserId);
		
		if(Pattern.matches("^[0-9]{2}-[0-9]{2}-[0-9]{4}", _dob)) {
			urlBuilder.append("&dob=").append(_dob);
		}
		
		
		if(Pattern.matches("^[1-2]$", _sex)) {
			urlBuilder.append("&sex=").append(_sex);
		}
		
		String cc = Locale.getDefault().getCountry();
			
		if(!cc.equals("")) {
			urlBuilder.append("&zl=").append(cc.toLowerCase());
		}
		
		int counter = 1;
		
		for(String key : _customVars.keySet()) {
			if(Pattern.matches("^[a-zA-Z0-9]*$", key) && Pattern.matches("^[a-zA-Z0-9]*$", _customVars.get(key))) {
				urlBuilder.append("&var_key_").append(counter).append("=").append(key);
				urlBuilder.append("&var_val_").append(counter).append("=").append(_customVars.get(key));
			}
			counter++;
		}
		
		_plUserId = "";
		_rewardsCenterUrl = urlBuilder.toString();
		
		Intent intent = new Intent(_parentContext, RewardsCenterActivity.class);
		RewardsCenterActivity.TOOLBAR_EVENTS_HANDLER = this;
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);		
		_parentContext.startActivity(intent);
		if(_eventsHandler != null) {
			_eventsHandler.onRewardsCenterOpened();
		}
	}

	@Override
	public void donePushed() {
		if (_eventsHandler != null) {
			_eventsHandler.onRewardsCenterClosed();
		}
	}

	private String generatePlUserId() {
		if (_endUserId.equals("") || _appId == -1 || _appKey.equals("")) {
			throw new InvalidParameterException(
					"Id(s) must be set before calling this method.");
		}
		String hash = md5(_endUserId + Integer.toString(_appId) + _appKey);
		String userGo = hash.substring(0, 10);
		return _endUserId + "-" + Integer.toString(_appId) + "-" + userGo;
	}

	public final String md5(final String s) {
		final String md_5 = "MD5";
		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance(md_5);
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			StringBuilder hexString = new StringBuilder();
			for (byte aMessageDigest : messageDigest) {
				String h = Integer.toHexString(0xFF & aMessageDigest);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
