var exec = require('cordova/exec'),
	dm = {
	authCode: function(success, error) {
		exec(success, error, "DeviceAppPlugin", "authCode", []);
	},
	messageCount: function(messageCount, success, error) {
        exec(success, error, "DeviceAppPlugin", "messageCount", [messageCount]);
    }
};


module.exports = dm;
