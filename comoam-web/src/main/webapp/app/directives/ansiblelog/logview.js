'use strict';

angular.module('monitor').directive( 'ansiblelog', function($log, WizardHandler, websocketService ) {
					return {
						restrict : 'EA',
						replace : true,
						scope : {
							ansibleSteps : '=',
							channel: '=',
							endponit: '@'
						},
						controller : [ '$scope', '$element', '$log', 'WizardHandler', 'websocketService', function($scope, $element, $log, WizardHandler, websocketService) {
									var taskgroup = new Array();
									$scope.nologshow = true;
									$scope.loadingshow = true;
									$scope.nextstep = "Start";
									$scope.$on('$destroy', function() {
										websocketService.disconnect();
									});
									$scope.logtail = function(data) {
										$scope.socket = websocketService.connect($scope.endponit, function( socket) { socket.stomp.subscribe(
															$scope.channel,
															$scope.showlog);
												});
									};
									$scope.showlog = function(data) {
										var tasks = $('#tasks');
										var task = $('#task');
										var loadpos = $('#loadpos');
										//$log.info(data);
										var log = JSON3.parse(data.body);
										if (log.result != ''){
											if (log.result == "succeed") {
												$scope.$apply(function() {
													$scope.loadingshow = false;
													WizardHandler.wizard().next();
													WizardHandler.wizard().finish();
												});
											}
											$scope.loadingshow = false;
											websocketService.disconnect();
											return;
										}
										if(!log.step){
											return;
										}
										if ($scope.nextstep != log.step) {
											$scope.nextstep = log.step;
											$scope.$apply(function() {
                                                var realStepNumber = $scope.ansibleSteps.indexOf(log.step) + 1;
                                                while(WizardHandler.wizard().currentStepNumber() < realStepNumber){
                                                    WizardHandler.wizard().next();
                                                }
											})
										}
										if (log.task != null && log.task != "") {
											$scope.$apply(function() {
												$scope.nologshow = false;
											});
											tasks.append("<i class=\"fa fa-check\" style=\"color:green\"></i>"
															+ "&nbsp;&nbsp;"
															+ log.task + "<br>");
											taskgroup.push(log.task);
											var taskhtml = "";
											var loadinghtml="";
											var startIndex = taskgroup.length > 10 ? taskgroup.length - 10
													: 0;
											for (var n = startIndex; n < taskgroup.length; n++) {
												taskhtml = taskhtml
														+ "<i class=\"fa fa-check\" style=\"color:green\"></i>"
														+ "&nbsp;&nbsp;"
														+ taskgroup[n] + "<br>";
											}
											task.html(taskhtml);
											if(taskgroup.length<10){
												$scope.loadingpos=10+(taskgroup.length-1)*20;
											}else{
												$scope.loadingpos=190;
											}
											loadinghtml = "<img src=\"images/spinner.gif\" style=\"padding-left:280px;padding-top:"+$scope.loadingpos+"px\"/>";
											loadpos.html(loadinghtml);
										}
										var logviewer = $('#logviewer');
										logviewer.append(log.logMsg + "<br>");
										logviewer.scrollTop(logviewer[0].scrollHeight - logviewer.height());
									};
									if ($scope.channel != null && $scope.channel!=''){
										$scope.logtail();
									}
								} ],
						templateUrl : "app/directives/ansiblelog/logviewer.html",
						link : function(scope, ele, attrs, ctrl) {
						}
					}
				});
