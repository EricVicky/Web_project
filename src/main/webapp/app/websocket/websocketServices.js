'use strict';
var websocket = angular.module('websocket', []);

websocket.factory('websocketService', function($log) {

	var parentUrl = parent.window.location.href;
	var baseUrl = parentUrl.split("#", 1)[0];
	var restUrl = baseUrl + "rest";

	return {
		connect : function(endPoint, success) {
			var socket = {
				client : null,
				stomp : null
			}

			var url = restUrl + endPoint;

			try {
				$log.info("start to init socket with endPoint " + endPoint
						+ "...");
				socket.client = new SockJS(url);
				socket.stomp = Stomp.over(socket.client);
				socket.stomp.connect({}, function(frame) {
					$log.info("WebSocket Connected to " + endPoint + ": "
							+ frame);
					success(socket);
				});

				socket.client.onclose = function() {
					socket.stomp.disconnect();
					$log.info("Socket connection to " + endPoint
							+ " is closed!");
				};
			} catch (err) {
				console.log(err);
			}
			return socket;
		},
	};
});