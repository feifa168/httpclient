<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/commonTag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="${basePath}"/>
    <title><spring:message code="temp.tempconfig"/></title>
    <%@include file="../common/head.html" %>
    <link type="text/css" rel="stylesheet" href="resources/js/vendor/ionsilder/css/ion.rangeSlider.css">
    <link type="text/css" rel="stylesheet" href="resources/js/vendor/ionsilder/css/ion.rangeSlider.skinNice.css">
    <%@include file="../common/globalParam.jsp" %>
    <link rel="stylesheet" href="resources/css/treetable/screen.css" media="screen" />
	<link rel="stylesheet" href="resources/css/treetable/jquery.treetable.css" />
	<link rel="stylesheet" href="resources/css/treetable/jquery.treetable.theme.default.css">
</head>
<body class="skin-blue-light sidebar-mini fixed">
<!-- Site wrapper -->
<div class="wrapper">
    <%@ include file="../common/commonHeader.jsp" %>
    <%@ include file="../common/commonAside.jsp" %>
    <div class="content-wrapper">
        <section class="content">
            <form id="temp_form" class="form-horizontal" role="form" data-parsley-validate>
                <input type="hidden" value="${flag }" name="flag">
                <div class="nav-tabs-custom vertical-tabs">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab_1" data-toggle="tab"><spring:message code="temp.baseinfo"/></a></li>
                        <li><a href="#tab_2" data-toggle="tab"><spring:message code="temp.discoverscan"/></a></li>
                        <li <c:if test="${st.general.disableVulnScan ne 0}">class="hide"</c:if>><a href="#tab_3" data-toggle="tab" data-show-from="st_vulnOnly"><spring:message code="temp.vulncheck"/></a></li>
                        <li <c:if test="${st.general.disableWebSpider ne 0}">class="hide"</c:if>><a href="#tab_4" data-toggle="tab" data-show-from="st_ws_on"><spring:message code="temp.webscan"/></a></li>
                        <%-- <li <c:if test="${st.general.disablePolicyScan ne 0}">class="hide"</c:if>><a href="#tab_5" data-toggle="tab" data-show-from="st_PolicyConfig"><spring:message code="policy.policy"/> </a></li> --%>
                    </ul>
                    <div class="tab-content form-horizontal">
                        <div class="tab-pane active" id="tab_1">
                            <h5><spring:message code="temp.info100"/></h5>
                            <h4><spring:message code="temp.checktype"/></h4>
                            <p><spring:message code="temp.info101"/></p>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="checkbox checkbox-inline">
                                        <input type="checkbox" id="st_discoveryOnly" checked="checked" disabled="disabled" name="st_discoveryOnlyRadio" value="1">
                                        <label for="st_discoveryOnly"><spring:message code="temp.discoverscan"/></label>
                                    </div>
                                    <div class="checkbox checkbox-inline">
                                        <input type="checkbox" id="st_vulnOnly" name="general.disableVulnScan" <c:if test="${st.general.disableVulnScan eq 0}">checked="checked"</c:if>>
                                        <label for="st_vulnOnly"><spring:message code="temp.vulncheck"/></label>
                                    </div>
                                    <div class="checkbox checkbox-inline">
                                        <input type="checkbox" id="st_ws_on" name="general.disableWebSpider" <c:if test="${st.general.disableWebSpider eq 0}">checked="checked"</c:if>>
                                        <label for="st_ws_on"><spring:message code="temp.webscan"/></label>
                                    </div>
                                    
                                    <%-- <div class="checkbox checkbox-inline">
                                        <input type="checkbox" id="st_PolicyConfig" name="general.disablePolicyScan" <c:if test="${st.general.disablePolicyScan eq 0}">checked="checked"</c:if>>
                                        <label for="st_PolicyConfig">Policy</label>
                                    </div> --%>
                                    <input type="hidden" name="general.disablePolicyScan" value="${st.general.disablePolicyScan }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="st_name" class="col-sm-3 control-label text-red"><spring:message code="temp.name"/></label>
                                <div class="col-sm-4">
                                    <input type="text" id="st_name" value="${st.templateDescription.title }" name="templateDescription.title" class="form-control" required>
                                    <input type="hidden" id="id" value="${st.id }" name="id">
                                    <input type="hidden" value="${st.general.invulnerableStorage }" name="general.invulnerableStorage">
                                    <input type="hidden" value="${st.general.discoveryOnly }" name="general.discoveryOnly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="st_desc" class="col-sm-3 control-label text-red"><spring:message code="temp.description"/></label>
                                <div class="col-sm-4">
                                    <textarea id="st_desc" class="form-control" name="templateDescription.content" required>${st.templateDescription.content}</textarea>
                                </div>
                            </div>
                            <%-- <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <div class="checkbox checkbox-primary">
                                        <input type="checkbox" id="st_enhancedLogging" name="log.debugLogging.enabled"
                                               <c:if test="${st.log.debugLogging.enabled eq '1' }">checked="checked"</c:if>
                                               data-dialog="<spring:message code="temp.info102"/>" value="${st.log.debugLogging.enabled }">
                                        <label for="st_enhancedLogging" title="<spring:message code="temp.info103"/>"><spring:message code="temp.enablelog"/></label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <div class="checkbox checkbox-primary">
                                        <input type="checkbox" id="st_windowsServices" name="log.aces.level"
                                               <c:if test="${st.log.aces.level eq 'full' }">checked="checked"</c:if>
                                               data-dialog="<spring:message code="temp.info104"/>" value="${st.log.aces.level }">
                                        <label for="st_windowsServices" title="<spring:message code="temp.info105"/>"><spring:message code="temp.info106"/></label>
                                    </div>
                                </div>
                            </div> --%>
                            <div class="form-group">
                                <label for="st_scanThreads" class="col-sm-3 control-label"><spring:message code="temp.maxassets"/></label>
                                <div class="col-sm-4">
                                    <input type="text" id="st_scanThreads" name="general.scanThreads" value="${st.general.scanThreads}" class="form-control" size="3" maxlength="3">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="st_hostThreads" class="col-sm-3 control-label"><spring:message code="temp.maxprogress"/></label>
                                <div class="col-sm-4">
                                    <input type="text" id="st_hostThreads" name="general.hostThreads" value="${st.general.hostThreads}" size="3" maxlength="3" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane" id="tab_2">
                            <div class="nav-tabs-custom">
                                <ul class="nav nav-tabs">
                                    <li class="active"><a href="#vscan_1" data-toggle="tab"><spring:message code="temp.discoverasset"/></a></li>
                                    <li><a href="#vscan_2" data-toggle="tab"><spring:message code="temp.discoverserver"/></a></li>
                                    <li><a href="#vscan_3" data-toggle="tab"><spring:message code="temp.discoverperformance"/></a></li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active" id="vscan_1">
                                        <p><spring:message code="temp.info107"/></p>
                                        <h4><spring:message code="temp.verifyingassets"/></h4>
                                        <p><spring:message code="temp.info108"/></p>
                                        <div class="checkbox checkbox-primary">
                                            <input type="checkbox" id="st_dd_icmp" name="deviceDiscovery.checkHosts.icmpHostCheck.enabled"
                                                   value="${st.deviceDiscovery.checkHosts.icmpHostCheck.enabled }"
                                                   <c:if test="${st.deviceDiscovery.checkHosts.icmpHostCheck.enabled eq 1 }">checked="checked"</c:if>>
                                            <label for="st_dd_icmp" title="<spring:message code="temp.info109"/>"><spring:message code="temp.icmpping"/></label>
                                        </div>
                                        <div class="checkbox checkbox-primary">
                                            <input type="checkbox" id="st_dd_arp" name="deviceDiscovery.checkHosts.arpHostCheck.enabled" value="${st.deviceDiscovery.checkHosts.arpHostCheck.enabled }"
                                                   <c:if test="${st.deviceDiscovery.checkHosts.arpHostCheck.enabled eq 1 }">checked="checked"</c:if>>
                                            <label for="st_dd_arp"
                                                   title="<spring:message code="temp.sendarpingtip"/>"><spring:message code="temp.sendarpping"/></label>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-3">
                                                <div class="checkbox checkbox-primary">
                                                    <input type="checkbox" id="st_dd_tcp" name="deviceDiscovery.checkHosts.tcpHostCheck.enabled"
                                                           value="${st.deviceDiscovery.checkHosts.tcpHostCheck.enabled }"
                                                           <c:if test="${st.deviceDiscovery.checkHosts.tcpHostCheck.enabled eq '1' }">checked="checked"</c:if>>
                                                    <label for="st_dd_tcp" title="<spring:message code="temp.info110"/>"><spring:message code="temp.sendtcpdata"/></label>
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <input type="text" id="st_dd_tcpports" name="deviceDiscovery.checkHosts.tcpHostCheck.portList" placeholder="21,22,23,25,53,80,110,111,135,139,143,443,445,993,995,1723,3306,3389,5900,8080" 
                                                       value="${st.deviceDiscovery.checkHosts.tcpHostCheck.portList }" class="form-control" title="<spring:message code="temp.info111"/>" <c:if test="${st.deviceDiscovery.checkHosts.tcpHostCheck.enabled ne '1' }">disabled</c:if>>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-3">
                                                <div class="checkbox checkbox-primary">
                                                    <input type="checkbox" id="st_dd_udp" name="deviceDiscovery.checkHosts.udpHostCheck.enabled"
                                                           value="${st.deviceDiscovery.checkHosts.udpHostCheck.enabled }"
                                                           <c:if test="${st.deviceDiscovery.checkHosts.udpHostCheck.enabled eq '1' }">checked="checked"</c:if>>
                                                    <label for="st_dd_udp" title="<spring:message code="temp.info112"/>"><spring:message code="temp.sendudpdata"/></label>
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <input type="text" id="st_dd_udpports" name="deviceDiscovery.checkHosts.udpHostCheck.portList" placeholder="53,67,68,69,123,135,137,138,139,161,162,445,500,514,520,631,1434,1900,4500,5353,49152"
                                                       value="${st.deviceDiscovery.checkHosts.udpHostCheck.portList }" class="form-control" title="<spring:message code="temp.info111"/>" <c:if test="${st.deviceDiscovery.checkHosts.udpHostCheck.enabled ne '1' }">disabled</c:if>>
                                            </div>
                                        </div>
                                        <div class="checkbox checkbox-primary">
                                            <input type="checkbox" id="st_dd_tcp_rst" name="deviceDiscovery.checkHosts.ignoreTCPReset.enabled"
                                                   value="${st.deviceDiscovery.checkHosts.ignoreTCPReset.enabled }"
                                                   <c:if test="${st.deviceDiscovery.checkHosts.ignoreTCPReset.enabled eq 1 }">checked="checked"</c:if>>
                                            <label for="st_dd_tcp_rst"
                                                   title="<spring:message code="temp.donottreattcptip"/>"><spring:message code="temp.donottreattcp"/></label>
                                        </div>
                                        <h4><spring:message code="temp.collectinformation"/></h4>
                                        <p><spring:message code="temp.collectinformation1"/></p>
                                        <div class="checkbox checkbox-primary">
                                            <input type="checkbox" id="st_dd_netpen" name="deviceDiscovery.networkDiscovery.enabled" value="${st.deviceDiscovery.networkDiscovery.enabled }"
                                                   <c:if test="${st.deviceDiscovery.networkDiscovery.enabled eq 1 }">checked="checked"</c:if>>
                                            <label for="st_dd_netpen" title="<spring:message code="temp.info113"/>"><spring:message code="temp.findnetasset"/></label>
                                        </div>
                                        <div class="checkbox checkbox-primary">
                                            <input type="checkbox" id="st_dd_whois" name="spamRelay.doWhoisLookups" value="${st.spamRelay.doWhoisLookups }"
                                            <c:if test="${st.spamRelay.doWhoisLookups eq '1' }">checked</c:if>>
                                            <label for="st_dd_whois" title="<spring:message code="temp.info114"/>"><spring:message code="temp.collectwhoisinfo"/></label>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-4">
                                                <div class="checkbox checkbox-primary">
                                                    <input type="checkbox" id="st_dd_osenabled" name="spamRelay.ipFingerprintEnabled" value="${st.spamRelay.ipFingerprintEnabled }"
                                                           <c:if test="${st.spamRelay.ipFingerprintEnabled eq 1 }">checked</c:if>>
                                                    <label for="st_dd_osenabled" title="<spring:message code="temp.info115"/>"><spring:message code="temp.fingerprintidentifystack"/></label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3"><spring:message code="temp.fingerprintreidentify"/></label>
                                            <div class="col-sm-4">
                                                <input type="text" id="st_dd_osretries" name="spamRelay.oSGuessRetryCount" value="${st.spamRelay.oSGuessRetryCount }" class="form-control" maxlength="3"
                                                       title="<spring:message code="temp.info115"/>">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3"><spring:message code="temp.minvalue"/></label>
                                            <div class="col-sm-4">
                                                <input type="text" id="st_dd_osthresh" name="spamRelay.ipFingerprintLowThreshold" value="${st.spamRelay.ipFingerprintLowThreshold }"
                                                       class="form-control" maxlength="5" 
                                                       title="<spring:message code="temp.info116"/>">
                                            </div>
                                        </div>
                                       <%--  <h4><spring:message code="temp.reportmacadr"/></h4>
                                        <p><spring:message code="temp.macinfo"/></p> --%>
                                        <div class="form-group" style="display:none;">
                                            <label class="col-sm-3" for="st_dd_trustmac"><spring:message code="temp.trustmacadrfile"/></label>
                                            <div class="col-sm-4">
                                                <input type="text" id="st_dd_trustmac" name="spamRelay.trustedMacAddressFile" value="${st.spamRelay.trustedMacAddressFile}" class="form-control"
                                                       title="<spring:message code="temp.info117"/>">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane" id="vscan_2">
                                        <p><spring:message code="temp.info118"/></p>
                                        <h4><spring:message code="temp.tcpscan"/></h4>
                                        <p><spring:message code="temp.selscantargetport"/></p>
                                        <div class="form-group">
                                            <label class="col-sm-2" for="st_sd_tcpmeth"><spring:message code="temp.method"/></label>
                                            <div class="col-sm-4">
                                                <select id="st_sd_tcpmeth" name="serviceDiscovery.tcpPortScan.method" class="form-control" size="1" onchange="updateRateLimit()">
                                                    <option value="syn" <c:if test="${st.serviceDiscovery.tcpPortScan.method eq 'syn'}">selected='selected'</c:if>>
                                                        <spring:message code="scanTemplate.syn"/>
                                                    </option>
                                                    <option value="synrst" <c:if test="${st.serviceDiscovery.tcpPortScan.method eq 'synrst'}">selected='selected'</c:if>>
                                                        <spring:message code="scanTemplate.synrst"/>
                                                    </option>
                                                    <option value="synfin" <c:if test="${st.serviceDiscovery.tcpPortScan.method eq 'synfin'}">selected='selected'</c:if>>
                                                        <spring:message code="scanTemplate.synfin"/>
                                                    </option>
                                                    <option value="synece" <c:if test="${st.serviceDiscovery.tcpPortScan.method eq 'synece'}">selected='selected'</c:if>>
                                                        <spring:message code="scanTemplate.synece"/>
                                                    </option>
                                                    <option value="connect" <c:if test="${st.serviceDiscovery.tcpPortScan.method eq 'connect'}">selected='selected'</c:if>>
                                                        <spring:message code="scanTemplate.fullConnect"/>
                                                    </option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2" for="st_sd_tcp"><spring:message code="temp.scanport"/></label>
                                            <div class="col-sm-4">
                                                <select id="st_sd_tcp" name="serviceDiscovery.tcpPortScan.mode" class="form-control" size="1">
                                                    <option value="default" <c:if test="${st.serviceDiscovery.tcpPortScan.mode eq 'default'}">selected='selected'</c:if>><spring:message
                                                            code="scanTemplate.defaultPort"/></option>
                                                    <option value="full" <c:if test="${st.serviceDiscovery.tcpPortScan.mode eq 'full'}">selected='selected'</c:if>><spring:message
                                                            code="scanTemplate.allPort"/></option>
                                                    <option value="custom" <c:if test="${st.serviceDiscovery.tcpPortScan.mode eq 'custom'}">selected='selected'</c:if>><spring:message
                                                            code="scanTemplate.customPort"/></option>
                                                    <option value="none" <c:if test="${st.serviceDiscovery.tcpPortScan.mode eq 'none'}">selected='selected'</c:if>><spring:message
                                                            code="scanTemplate.noPort"/></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2" for="st_sd_tcpports"><spring:message code="temp.extraport"/></label>
                                            <div class="col-sm-4">
                                                <input type="text" id="st_sd_tcpports" name="serviceDiscovery.tcpPortScan.portList" value="${st.serviceDiscovery.tcpPortScan.portList}"
                                                       class="form-control" placeholder="1-1040" title="<spring:message code="temp.portscope"/>">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2" for="st_sd_excluded_tcpports"><spring:message code="temp.excludeport"/></label>
                                            <div class="col-sm-4">
                                                <input type="text" id="st_sd_excluded_tcpports" name="serviceDiscovery.excludedTCPPortScan.portList"
                                                       value="${st.serviceDiscovery.excludedTCPPortScan.portList}" class="form-control" title="<spring:message code="temp.excludeportscope"/>">
                                            </div>
                                        </div>
                                        <h4><spring:message code="temp.udpscan"/></h4>
                                        <p><spring:message code="temp.seludpscandata"/></p>
                                        <div class="form-group">
                                            <label class="col-sm-2" for="st_sd_udp"><spring:message code="temp.scanport"/></label>
                                            <div class="col-sm-4">
                                                <select id="st_sd_udp" name="serviceDiscovery.udpPortScan.mode" class="form-control" size="1" title="<spring:message code="temp.seltargetpoergroup"/>">
                                                    <option value="default" <c:if test="${st.serviceDiscovery.udpPortScan.mode eq 'default'}">selected='selected'</c:if>><spring:message
                                                            code="scanTemplate.defaultPort"/></option>
                                                    <option value="full" <c:if test="${st.serviceDiscovery.udpPortScan.mode eq 'full'}">selected='selected'</c:if>><spring:message
                                                            code="scanTemplate.allPort"/></option>
                                                    <option value="custom" <c:if test="${st.serviceDiscovery.udpPortScan.mode eq 'custom'}">selected='selected'</c:if>><spring:message
                                                            code="scanTemplate.customPort"/></option>
                                                    <option value="none" <c:if test="${st.serviceDiscovery.udpPortScan.mode eq 'none'}">selected='selected'</c:if>><spring:message
                                                            code="scanTemplate.noPort"/></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2" for="st_sd_udpports"><spring:message code="temp.extraport"/></label>
                                            <div class="col-sm-4">
                                                <input type="text" id="st_sd_udpports" name="serviceDiscovery.udpPortScan.portList" value="${st.serviceDiscovery.udpPortScan.portList}"
                                                       class="form-control" title="<spring:message code="temp.portscope"/>">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2" for="st_sd_excluded_udpports"><spring:message code="temp.excludeport"/></label>
                                            <div class="col-sm-4">
                                                <input type="text" id="st_sd_excluded_udpports" name="serviceDiscovery.excludedUDPPortScan.portList"
                                                       value="${st.serviceDiscovery.excludedUDPPortScan.portList}" class="form-control" title="<spring:message code="temp.excludeportscope"/>">
                                            </div>
                                        </div>
                                        <%-- <h4><spring:message code="temp.servernamefile"/></h4>
                                        <p><spring:message code="temp.info119"/></p> --%>
                                        <div class="form-group" style="display:none;">
                                            <label class="col-sm-2" for="st_sd_defnames"><spring:message code="temp.filename"/></label>
                                            <div class="col-sm-4">
                                                <input type="text" id="st_sd_defnames" name="spamRelay.defaultServicePropertiesFile" value="${st.spamRelay.defaultServicePropertiesFile }"
                                                       class="form-control" title="<spring:message code="temp.changefilename"/>">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane" id="vscan_3">
                                        <p><spring:message code="temp.info120"/></p>
                                        <button type="button" class="btn btn-sm btn-primary" onclick="resetPerformance();"><spring:message code="temp.reset"/></button>
                                        <h5><spring:message code="temp.maxretry"/></h5>
                                        <p><spring:message code="temp.info121"/></p>
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-6">
                                                <input id="maxretriesslider" type="text" data-from="${st.discoveryPerformance.maxRetries.maxretries}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2"><spring:message code="temp.retrylimit"/></label>
                                            <div class="col-sm-10 form-inline">
                                                <div class="form-group">
                                                    <label for="st_pd_mintimeout"><spring:message code="temp.info122"/></label>
                                                    <input type="text" id="st_pd_maxretries" name="discoveryPerformance.maxRetries.maxretries" value="${st.discoveryPerformance.maxRetries.maxretries}"
                                                           class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 超时间隔 -->
                                        <h5><spring:message code="temp.timeoverinterval"/></h5>
                                        <h5><spring:message code="temp.info123"/></h5>
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-6">
                                                <input id="inittimeoutslider" type="text" data-from="${st.discoveryPerformance.initTimeout.inittimeout}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2"><spring:message code="temp.firsttimeover"/></label>
                                            <div class="col-sm-10 form-inline">
                                                <div class="form-group">
                                                    <label for="st_pd_mintimeout"><spring:message code="temp.info124"/></label>
                                                    <input type="text" id="st_pd_inittimeout" name="discoveryPerformance.initTimeout.inittimeout"
                                                           value="${st.discoveryPerformance.initTimeout.inittimeout}" class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-6">
                                                <input id="timeoutrangeslider" type="text" data-from="${st.discoveryPerformance.minTimeout.mintimeout}" data-to="${st.discoveryPerformance.maxTimeout.maxtimeout}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2"><spring:message code="temp.timeoverinterval"/></label>
                                            <div class="col-sm-10 form-inline">
                                                <div class="form-group">
                                                    <label for="st_pd_mintimeout"><spring:message code="temp.mintimeout"/></label>
                                                    <input id="st_pd_mintimeout" name="discoveryPerformance.minTimeout.mintimeout" value="${st.discoveryPerformance.minTimeout.mintimeout}" type="text"
                                                           class="form-control">
                                                </div>
                                                <div class="form-group">
                                                    <label for="st_pd_maxtimeout"><spring:message code="temp.maxtimeout"/></label>
                                                    <input id="st_pd_maxtimeout" name="discoveryPerformance.maxTimeout.maxtimeout" value="${st.discoveryPerformance.maxTimeout.maxtimeout}" type="text"
                                                           class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 扫描延迟 -->
                                        <h5><spring:message code="temp.scandelay"/></h5>
                                        <h5><spring:message code="temp.delaytime"/></h5>
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-6">
                                                <input id="scandelayrangeslider" type="text" data-from="${st.discoveryPerformance.minScanDelay.minscandelay}" data-to="${st.discoveryPerformance.maxScanDelay.maxscandelay}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2"><spring:message code="temp.scandelay"/></label>
                                            <div class="col-sm-10 form-inline">
                                                <div class="form-group">
                                                    <label for="st_pd_minscandelay"><spring:message code="temp.mintimeout"/></label>
                                                    <input id="st_pd_minscandelay" name="discoveryPerformance.minScanDelay.minscandelay" value="${st.discoveryPerformance.minScanDelay.minscandelay}"
                                                           type="text" class="form-control">
                                                </div>
                                                <div class="form-group">
                                                    <label for="st_pd_maxscandelay"><spring:message code="temp.maxtimeout"/></label>
                                                    <input id="st_pd_maxscandelay" name="discoveryPerformance.maxScanDelay.maxscandelay" value="${st.discoveryPerformance.maxScanDelay.maxscandelay}"
                                                           type="text" class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 数据包每秒速率 -->
                                        <h5><spring:message code="temp.packetrate"/></h5>
                                        <h5><spring:message code="temp.info125"/></h5>
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-6">
                                                <div class="checkbox checkbox-inline">
                                                    <input id="st_pd_defeatrate" type="checkbox" name="discoveryPerformance.defeatRate.enabled" value="${st.discoveryPerformance.defeatRate.enabled}"
                                                           <c:if test="${st.discoveryPerformance.defeatRate.enabled eq '1' }">checked="checked"</c:if>
                                                           title="<spring:message code="temp.info126"/>" onclick="verifyDefeatRateLimitUnchecked(this);">
                                                    <label for="st_pd_defeatrate"><spring:message code="temp.defaultratelimit"/></label>
                                                </div>
                                                <p id="div_warn_defeatrate"><spring:message code="temp.info127"/></p>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-6">
                                                <input id="raterangeslider" type="text" data-from="${st.discoveryPerformance.minRate.minrate}" data-to="${st.discoveryPerformance.maxRate.maxrate}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2"><spring:message code="temp.rate"/></label>
                                            <div class="col-sm-10 form-inline">
                                                <div class="form-group">
                                                    <label for="st_pd_minrate"><spring:message code="temp.minpacketsize"/></label>
                                                    <input id="st_pd_minrate" name="discoveryPerformance.minRate.minrate" type="text" class="form-control">
                                                </div>
                                                <div class="form-group">
                                                    <label for="st_pd_maxrate"><spring:message code="temp.maxpacketsize"/></label>
                                                    <input id="st_pd_maxrate" name="discoveryPerformance.maxRate.maxrate" type="text" class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 并行性 -->
                                        <h5><spring:message code="temp.parallelism"/></h5>
                                        <h5><spring:message code="temp.info128"/></h5>
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-6">
                                                <input id="parallelismrangeslider" type="text" data-from="${st.discoveryPerformance.minParallelism.minparallelism}" data-to="${st.discoveryPerformance.maxParallelism.maxparallelism}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2"> <spring:message code="temp.connectrequire"/></label>
                                            <div class="col-sm-10 form-inline">
                                                <div class="form-group">
                                                    <label for="st_pd_minparallelism"><spring:message code="temp.minsamerequire"/></label>
                                                    <input id="st_pd_minparallelism" name="discoveryPerformance.minParallelism.minparallelism"
                                                           value="${st.discoveryPerformance.minParallelism.minparallelism}" type="text" class="form-control">
                                                </div>
                                                <div class="form-group">
                                                    <label for="st_pd_maxparallelism"><spring:message code="temp.maxsamerequire"/></label>
                                                    <input id="st_pd_maxparallelism" name="discoveryPerformance.maxParallelism.maxparallelism"
                                                           value="${st.discoveryPerformance.maxParallelism.maxparallelism}" type="text" class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 漏洞检测 -->
                        <div class="tab-pane" id="tab_3">
                            <div class="nav-tabs-custom">
                                <ul class="nav nav-tabs">
                                    <li class="active"><a href="#vcks_1" data-toggle="tab"><spring:message code="temp.vulnchecks"/></a></li>
                                    <li><a href="#vcks_2" data-toggle="tab"><spring:message code="temp.filesearch"/></a></li>
                                    <li><a href="#vcks_3" data-toggle="tab"><spring:message code="temp.spamforwarding"/></a></li>
                                    <li><a href="#vcks_4" data-toggle="tab"><spring:message code="temp.databaseserver"/></a></li>
                                    <li><a href="#vcks_5" data-toggle="tab"><spring:message code="temp.emailserver"/></a></li>
                                    <li><a href="#vcks_6" data-toggle="tab"><spring:message code="temp.cvsserver"/></a></li>
                                    <li><a href="#vcks_7" data-toggle="tab"><spring:message code="temp.dhcpserver"/></a></li>
                                    <li><a href="#vcks_8" data-toggle="tab"><spring:message code="temp.telnetserver"/></a></li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active" id="vcks_1">
                                        <ul class="list-unstyled">
                                            <li><spring:message code="temp.info201"/>
                                                <ul>
                                                    <li><spring:message code="temp.info202"/></li>
                                                    <li><spring:message code="temp.info203"/></li>
                                                    <li><spring:message code="temp.info204"/></li>
                                                    <li><spring:message code="temp.info205"/></li>
                                                    <li><spring:message code="temp.info206"/></li>
                                                    <li><spring:message code="temp.info207"/></li>
                                                    <li><spring:message code="temp.info208"/></li>
                                                </ul>
                                            </li>
                                            <li><spring:message code="temp.info209"/></li>
                                        </ul>
                                        <h4><spring:message code="temp.checkconfig"/></h4>
                                        <div class="form-group">
                                            <div class="checkbox">
                                                <div class="checkbox checkbox-inline">
                                                    <input id="st_vcks_unsafe" type="checkbox"
                                                           <c:if test="${st.vulnerabilityChecks.unsafe eq 1 }">checked='checked'</c:if> name="vulnerabilityChecks.unsafe"
                                                           value="${st.vulnerabilityChecks.unsafe }"
                                                           data-dialog="<spring:message code="temp.info210"/>">
                                                    <label for="st_vcks_unsafe"><spring:message code="temp.unsafecheck"/></label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="checkbox">
                                                <div class="checkbox checkbox-inline">
                                                    <input id="st_vcks_potential" type="checkbox"
                                                           <c:if test="${st.vulnerabilityChecks.potential eq 1 }">checked='checked'</c:if> name="vulnerabilityChecks.potential"
                                                           value="${st.vulnerabilityChecks.potential }"
                                                           data-dialog="<spring:message code="temp.info211"/>">
                                                    <label for="st_vcks_potential"><spring:message code="temp.potentialcheck"/></label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="checkbox">
                                                <div class="checkbox checkbox-inline">
                                                    <input id="st_vcks_correlate" type="checkbox"
                                                           <c:if test="${st.vulnerabilityChecks.correlate eq 1 }">checked='checked'</c:if> name="vulnerabilityChecks.correlate"
                                                           value="${st.vulnerabilityChecks.correlate }">
                                                    <label for="st_vcks_correlate"><spring:message code="temp.info212"/></label>
                                                </div>
                                            </div>
                                        </div>
                                        <h5><spring:message code="temp.selectedcheck"/></h5>
                                        <div class="panel-group accordion" id="vuln-check">
                                        	<!-- 按漏洞类型 -->
                                            <div class="panel panel-bordered panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-parent="#vuln-cat" data-toggle="collapse" href="#vuln-cat-body" aria-expanded="false" title="<spring:message code="temp.info213"/>" class="collapsed">
                                                            <spring:message code="temp.accordingcategory"/><span id="vulnCategoriesHeaderCount">&nbsp;(<spring:message code="temp.total2"/> &nbsp;${categories })</span>
                                                            <i class="fa fa-plus pull-right"></i>
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div class="panel-collapse collapse" id="vuln-cat-body" aria-expanded="false">
                                                    <div class="panel-body">
                                                        <div class="box box-solid">
                                                            <div class="box-header with-border">
                                                                <h5 class="box-title"><spring:message code="status.enabled"/></h5>
                                                                <div class="box-tools pull-right">
                                                                    <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#dialog_vuln_cats"
                                                                            data-addTo="#enabledVulnCatListing" data-exclude="#disabledVulnCatListing"><spring:message code="temp.addcategory"/>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <div class="box-body">
                                                                <table class="table table-bordered table-striped" id="enabledVulnCatListing">
                                                                    <thead>
                                                                    <tr>
                                                                        <th><spring:message code="temp.vulncategory"/></th>
                                                                        <th width="10%"><spring:message code="temp.vuln"/></th>
                                                                        <th width="5%"><spring:message code="action.remove"/></th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <c:if test="${not empty st.vulnerabilityChecks.enabled.vulnList }">
                                                                        <c:forEach items="${st.vulnerabilityChecks.enabled.vulnList }" var="cateItem" varStatus="status">
                                                                            <tr>
                                                                                <td>
                                                                                        ${cateItem.name}
                                                                                </td>
                                                                                <td></td>
                                                                                <td><i class="fa fa-trash-o data-delte-js" data-table="enabledVulnCatListing"></i></td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </c:if>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                        <div class="box box-solid">
                                                            <div class="box-header with-border">
                                                                <h5 class="box-title"><spring:message code="status.disabled"/></h5>
                                                                <div class="box-tools pull-right">
                                                                    <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#dialog_vuln_cats"
                                                                            data-addTo="#disabledVulnCatListing" data-exclude="#enabledVulnCatListing"><spring:message code="temp.removecategory"/>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <div class="box-body">
                                                                <table class="table table-bordered table-striped" id="disabledVulnCatListing">
                                                                    <thead>
                                                                    <tr>
                                                                        <th><spring:message code="temp.vulncategory"/></th>
                                                                        <th width="10%"><spring:message code="temp.vuln"/></th>
                                                                        <th width="5%"><spring:message code="temp.delete"/></th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <c:if test="${not empty st.vulnerabilityChecks.disabled.vulnList }">
                                                                        <c:forEach items="${st.vulnerabilityChecks.disabled.vulnList }" var="cateItem" varStatus="status">
                                                                            <tr>
                                                                                <td>
                                                                                        ${cateItem.name }
                                                                                </td>
                                                                                <td></td>
                                                                                <td><i class="fa fa-trash-o data-delte-js" data-table="disabledVulnCatListing"></i></td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </c:if>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                        <div class="modal fade bs-example-modal-lg" id="dialog_vuln_cats" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
                                                            <div class="modal-dialog modal-lg" role="document">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span>
                                                                        </button>
                                                                        <h4 class="modal-title"><spring:message code="temp.selvulncate"/></h4>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <table id="dynTable_VulnCategorySynopsis" class="table table-bordered table-striped">
                                                                            <thead>
                                                                            <tr>
                                                                                <th>
                                                                                    <input id="selectall_VulnCategorySynopsis" type="checkbox" title="<spring:message code="temp.selall"/>">
                                                                                    <label for="selectall_VulnCategorySynopsis" title="<spring:message code="temp.selall"/>"></label>
                                                                                </th>
                                                                                <th><spring:message code="temp.vulncategory"/></th>
                                                                                <th><spring:message code="temp.vuln"/></th>
                                                                            </tr>
                                                                            </thead>
                                                                        </table>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-primary" id="dialog_vuln_cats_save"><spring:message code="action.save"/></button>
                                                                        <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="action.cancel"/></button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!--检查类型 -->
                                            <div class="panel panel-bordered panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-parent="#vuln-checkType" data-toggle="collapse" href="#vuln-checkType-body" aria-expanded="false" title="<spring:message code="temp.info213"/>"
                                                           class="collapsed">
                                                            <spring:message code="temp.accordchecktype"/><span id="vulnCheckTypeHeaderCount">&nbsp;(<spring:message code="temp.total2"/>&nbsp;${checkTypes })</span>
                                                            <i class="fa fa-plus pull-right"></i>
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div class="panel-collapse collapse" id="vuln-checkType-body" aria-expanded="false">
                                                    <div class="panel-body">
                                                        <!-- 添加检查类型 -->
                                                        <div class="box box-solid">
                                                            <div class="box-header with-border">
                                                                <h5 class="box-title"><spring:message code="status.enabled"/></h5>
                                                                <div class="box-tools pull-right">
                                                                    <button type="button" class="btn btn-sm btn-primary pull-right" data-toggle="modal" data-target="#dialog_vulnck_cats"
                                                                            data-addTo="#enabledVulnCheckTypeListing"
                                                                            data-exclude="#disabledVulnCheckTypeListing">
                                                                        <spring:message code="temp.addchecktype"/>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <div class="box-body">
                                                                <table class="table table-bordered table-striped" id="enabledVulnCheckTypeListing">
                                                                    <thead>
                                                                    <tr>
                                                                        <th><spring:message code="temp.vulnchecktype"/></th>
                                                                        <th><spring:message code="temp.check"/></th>
                                                                        <th><spring:message code="temp.delete"/></th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <c:if test="${not empty st.vulnerabilityChecks.enabled.checkType }">
                                                                        <c:forEach items="${st.vulnerabilityChecks.enabled.checkType }" var="checkItem" varStatus="status">
                                                                            <tr>
                                                                                <td>
                                                                                        ${checkItem.name }
                                                                                </td>
                                                                                <td></td>
                                                                                <td><i class="fa fa-trash-o data-delte-js" data-table="enabledVulnCheckTypeListing"></i></td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </c:if>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                        <!-- 移除检查类型 -->
                                                        <div class="box box-solid">
                                                            <div class="box-header with-border">
                                                                <h5 class="box-title"><spring:message code="status.disabled"/></h5>
                                                                <div class="box-tools pull-right">
                                                                    <button type="button" class="btn btn-sm btn-primary pull-right" data-toggle="modal" data-target="#dialog_vulnck_cats"
                                                                            data-addTo="#disabledVulnCheckTypeListing"
                                                                            data-exclude="#enabledVulnCheckTypeListing">
                                                                        <spring:message code="temp.removechecktype"/>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <div class="box-body">
                                                                <table class="table table-bordered table-striped" id="disabledVulnCheckTypeListing">
                                                                    <thead>
                                                                    <tr>
                                                                        <th><spring:message code="temp.vulnchecktype"/></th>
                                                                        <th><spring:message code="temp.check"/></th>
                                                                        <th><spring:message code="temp.delete"/></th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <c:if test="${not empty st.vulnerabilityChecks.disabled.checkType }">
                                                                        <c:forEach items="${st.vulnerabilityChecks.disabled.checkType }" var="checkItem" varStatus="status">
                                                                            <tr>
                                                                                <td>${checkItem.name }
                                                                                </td>
                                                                                <td></td>
                                                                                <td><i class="fa fa-trash-o data-delte-js" data-table="disabledVulnCheckTypeListing"></i></td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </c:if>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                        <!-- 按检查类型添加弹窗 -->
                                                        <div class="modal fade bs-example-modal-lg" id="dialog_vulnck_cats" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
                                                            <div class="modal-dialog modal-lg" role="document">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span>
                                                                        </button>
                                                                        <h4 class="modal-title"><spring:message code="temp.selvulncate"/></h4>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <table id="dynTable_VulnCheckCategorySynopsis" class="table table-bordered table-striped">
                                                                            <thead>
                                                                            <tr>
                                                                                <th width="5%">
                                                                                    <input id="selectall_VulnCheckCategorySynopsis" type="checkbox" title="<spring:message code="temp.selall"/>">
                                                                                    <label for="selectall_VulnCheckCategorySynopsis" title="<spring:message code="temp.selall"/>"></label>
                                                                                </th>
                                                                                <th><spring:message code="temp.vulnchecktype"/></th>
                                                                                <th width="10%"><spring:message code="temp.check"/></th>
                                                                            </tr>
                                                                            </thead>
                                                                        </table>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-primary" id="dialog_vulnck_cats_save"><spring:message code="action.save"/></button>
                                                                        <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="action.cancel"/></button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- 按单个检查 -->
                                            <div class="panel panel-bordered panel-info">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-parent="#vuln-checking" data-toggle="collapse" href="#vuln-checking-body" aria-expanded="false" title="<spring:message code="temp.info213"/>"
                                                           class="collapsed">
                                                            <spring:message code="temp.accordsinglecheck"/><span id="vulnCheckHeaderCount">&nbsp;(<spring:message code="temp.total2"/>&nbsp;${checks })</span>
                                                            <i class="fa fa-plus pull-right"></i>
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div class="panel-collapse collapse" id="vuln-checking-body" aria-expanded="false">
                                                    <div class="panel-body">
                                                        <!-- 添加检查 -->
                                                        <div class="box box-solid">
                                                            <div class="box-header with-border">
                                                                <h5 class="box-title"><spring:message code="status.enabled"/></h5>
                                                                <div class="box-tools pull-right">
                                                                    <button type="button" class="btn btn-sm btn-primary pull-right" data-toggle="modal" data-target="#dialog_vulnck_search"
                                                                            data-addTo="#enabledVulnCheckListing"
                                                                            data-exclude="#disabledVulnCheckListing"><spring:message code="temp.addcheck"/>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <div class="box-body">
                                                                <table class="table table-bordered table-striped" id="enabledVulnCheckListing">
                                                                    <thead>
                                                                    <tr>
                                                                    	<th>id</th>
                                                                        <th><spring:message code="temp.vulnchecks"/></th>
                                                                        <th><spring:message code="temp.checktype"/></th>
                                                                        <th><spring:message code="temp.delete"/></th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <c:if test="${not empty st.vulnerabilityChecks.enabled.checkList }">
                                                                        <c:forEach items="${st.vulnerabilityChecks.enabled.checkList }" var="vulnItem" varStatus="status">
                                                                            <tr>
                                                                            	<td>${vulnItem.id }</td>
                                                                                <td>
                                                                                        ${vulnItem.value }
                                                                                </td>
                                                                                <td>
                                                                                        ${vulnItem.type }
                                                                                </td>
                                                                                <td><i class="fa fa-trash-o data-delte-js" data-table="enabledVulnCheckListing"></i></td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </c:if>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                        <!-- 移除检查 -->
                                                        <div class="box box-solid">
                                                            <div class="box-header with-border">
                                                                <h5 class="box-title"><spring:message code="status.disabled"/></h5>
                                                                <div class="box-tools pull-right">
                                                                    <button type="button" class="btn btn-sm btn-primary pull-right" data-toggle="modal" data-target="#dialog_vulnck_search"
                                                                            data-addTo="#disabledVulnCheckListing"
                                                                            data-exclude="#enabledVulnCheckListing"><spring:message code="temp.removecheck"/>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <div class="box-body">
                                                                <table class="table table-bordered table-striped" id="disabledVulnCheckListing">
                                                                    <thead>
                                                                    <tr>
                                                                    	<th>id</th>
                                                                        <th><spring:message code="temp.vulnchecks"/></th>
                                                                        <th><spring:message code="temp.checktype"/></th>
                                                                        <th><spring:message code="temp.delete"/></th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <c:if test="${not empty st.vulnerabilityChecks.disabled.checkList }">
                                                                        <c:forEach items="${st.vulnerabilityChecks.disabled.checkList }" var="vulnItem" varStatus="status">
                                                                            <tr>
                                                                            	<td>${vulnItem.id }</td>
                                                                                <td>
                                                                                        ${vulnItem.value }</td>
                                                                                <td>
                                                                                        ${vulnItem.type }
                                                                                </td>
                                                                                <td><i class="fa fa-trash-o data-delte-js" data-table="disabledVulnCheckListing"></i></td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </c:if>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                        <!--按单个检查添加弹窗-->
                                                        <div class="modal fade bs-example-modal-lg" id="dialog_vulnck_search" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
                                                            <div class="modal-dialog" style="width:70%;" role="document">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span>
                                                                        </button>
                                                                        <h4 class="modal-title"><spring:message code="temp.searchvulncheck"/></h4>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <p><spring:message code="temp.info214"/></p>
                                                                        <div class="form-group">
                                                                            <label for="vck_search_phrase" class="col-sm-2 control-label">
                                                                                <spring:message code="temp.searchstandart"/>
                                                                                <span class="glyphicon glyphicon-question-sign" data-toggle="tooltip" data-container="body" data-placement="bottom"
                                                                                      title="<spring:message code="temp.info215"/>"></span>
                                                                            </label>
                                                                            <div class="col-sm-4">
                                                                                <div class="input-group">
                                                                                    <input type="text" id="vck_search_phrase" class="form-control">
                                                                                    <span class="input-group-btn">
                                                                            <input type="button" class="btn btn-primary" value="<spring:message code="temp.search"/>" onclick="searchVulnChecks();">
                                                                        </span>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group">
                                                                            <div class="col-sm-6">
                                                                                <div class="checkbox checkbox-inline">
                                                                                    <input id="vck_search_all" type="checkbox" checked>
                                                                                    <label for="vck_search_all"><spring:message code="temp.containall"/></label>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <table id="dynTable_VulnCheckSynopsis" class="table table-bordered table-striped hide">
                                                                            <thead>
                                                                            <tr>
                                                                                <th width="5%">
                                                                                    <input id="selectall_VulnCheckSynopsis" type="checkbox" title="<spring:message code="temp.selall"/>">
                                                                                    <label for="selectall_VulnCheckSynopsis" title="<spring:message code="temp.selall"/>"></label>
                                                                                </th>
                                                                                <th><spring:message code="temp.vulnchecks"/></th>
                                                                                <th><spring:message code="temp.vulnid"/></th>
                                                                                <th><spring:message code="temp.vuln"/></th>
                                                                                <th width="5%"><spring:message code="temp.severity"/></th>
                                                                                <th width="8%"><spring:message code="temp.category"/></th>
                                                                                <th width="10%"><spring:message code="temp.checktype"/></th>
                                                                            </tr>
                                                                            </thead>
                                                                        </table>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-primary" id="dialog_vulnck_search_save"><spring:message code="action.save"/></button>
                                                                        <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="action.cancel"/></button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane" id="vcks_2">
                                        <p><spring:message code="temp.info216"/></p>
                                        <h4><spring:message code="temp.filesearchlist"/></h4>
                                        <table id="fileSearchListing" class="table table-bordered table-striped">
                                            <thead>
                                            <tr>
                                                <th><spring:message code="status.enabled"/></th>
                                                <th><spring:message code="temp.description"/></th>
                                                <th><spring:message code="temp.modetype"/></th>
                                                <th><spring:message code="temp.mode"/></th>
                                                <th><spring:message code="action.edit"/></th>
                                                <th><spring:message code="action.remove"/></th>
                                            </tr>
                                            </thead>
                                        </table>
                                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#file_search_modal"><spring:message code="temp.addfilesearch"/></button>
                                        <%@ include file="../commondialog/file-search-dialog.jsp" %>
                                    </div>
                                    <div class="tab-pane" id="vcks_3">
                                        <dl>
                                            <dd><spring:message code="temp.info217"/></dd>
                                            <dd><spring:message code="temp.info218"/></dd>
                                            <dd><spring:message code="temp.info219"/></dd>
                                        </dl>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_spam_email"><spring:message code="temp.outeremailadr"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_spam_email" name="spamRelay.emailAddr" value="${st.spamRelay.emailAddr }" type="text" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_spam_referer"><spring:message code="temp.httpreferer"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_spam_referer" name="spamRelay.httpReferer" value="${st.spamRelay.httpReferer }" type="text" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane" id="vcks_4">
                                        <dl>
                                            <dd><spring:message code="temp.info220"/>
                                                <spring:message code="temp.info221"/>
                                            </dd>
                                        </dl>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_dbms_db2_defdb"> <spring:message code="temp.connectdb2"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_dbms_db2_defdb" name="databaseServers.db2Database" value="${st.databaseServers.db2Database }" type="text" class="form-control"
                                                       maxlength="18">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_dbms_pgsql_defdb"><spring:message code="temp.connectpostgre"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_dbms_pgsql_defdb" name="databaseServers.postgresDatabase" value="${st.databaseServers.postgresDatabase }" type="text" class="form-control"
                                                       maxlength="63">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_dbms_oracle_defdb"><spring:message code="temp.connectoracle"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_dbms_oracle_defdb" name="databaseServers.oracleInstances" value="${st.databaseServers.oracleInstances }" type="text" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane" id="vcks_5">
                                        <h5><spring:message code="temp.info222"/></h5>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_smtp_timeout"><spring:message code="temp.readtimeout"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_smtp_timeout" name="mailServers.readTimeout" value="${st.mailServers.readTimeout }" type="text" class="form-control" maxlength="6">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_smtp_timeacc"><spring:message code="temp.difvalue"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_smtp_timeacc" name="mailServers.timeAccuracyThreshold" value="${st.mailServers.timeAccuracyThreshold }" type="text" class="form-control"
                                                       maxlength="8">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane" id="vcks_6">
                                        <h5><spring:message code="temp.info223"/></h5>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_cvs_root"><spring:message code="temp.cvsdirect"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_cvs_root" name="cvsServers.cvsRepositoryRoot" value="${st.cvsServers.cvsRepositoryRoot }" type="text" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane" id="vcks_7">
                                        <h5><spring:message code="temp.info224"/></h5>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_dhcp_pool"><spring:message code="temp.dhcpadrscope"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_dhcp_pool" name="dhcpServers.dhcpPoolRange" value="${st.dhcpServers.dhcpPoolRange }" type="text" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane" id="vcks_8">
                                        <h5><spring:message code="temp.info225"/></h5>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_telnet_charset"><spring:message code="temp.usecode"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_telnet_charset" name="telnetServers.terminalCharset" value="${st.telnetServers.terminalCharset }" type="text" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_telnet_relogin"><spring:message code="temp.loginregular"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_telnet_relogin" name="telnetServers.regexLoginPrompts" value="${st.telnetServers.regexLoginPrompts }" type="text" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_telnet_repw"><spring:message code="temp.pwdregular"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_telnet_repw" name="telnetServers.regexFalseNegativeMsg" value="${st.telnetServers.regexFalseNegativeMsg }" type="text"
                                                       class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_telnet_refail"><spring:message code="temp.loginfileregular"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_telnet_refail" name="telnetServers.regexFailedLoginMsg" value="${st.telnetServers.regexFailedLoginMsg }" type="text" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3" for="st_telnet_reneg"><spring:message code="temp.suspiciousloginregular"/></label>
                                            <div class="col-sm-4">
                                                <input id="st_telnet_reneg" name="telnetServers.regexPasswordPrompts" value="${st.telnetServers.regexPasswordPrompts }" type="text"
                                                       class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Web扫描 -->
                        <div class="tab-pane" id="tab_4">
                            <p><%-- <spring:message code="temp.info226"/>
                                <spring:message code="temp.info227"/></p> --%>
                            <h4><spring:message code="temp.globelconfig"/></h4>
                            <div class="form-group">
                                <div class="col-sm-5">
                                    <div class="checkbox checkbox-inline">
                                        <input type="checkbox" id="st_ws_urlqry" name="webSpidering.includeURLQueries" class="checkbox"
                                               <c:if test="${st.webSpidering.includeURLQueries eq '1'}">checked='checked'</c:if> value="${st.webSpidering.includeURLQueries }">
                                        <label for="st_ws_urlqry"><spring:message code="temp.includeURLQueries"/></label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-5">
                                    <div class="checkbox checkbox-inline">
                                        <input type="checkbox" id="st_ws_persistent" name="webSpidering.singleScanPersistent" class="checkbox"
                                               <c:if test="${st.webSpidering.singleScanPersistent eq 1}">checked='checked'</c:if> value="${st.webSpidering.singleScanPersistent }">
                                        <label for="st_ws_persistent"><spring:message code="temp.singleScanPersistent"/></label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_user_agent_string"> <spring:message code="temp.userAgent"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_user_agent_string" value="${st.webSpidering.userAgent }" name="webSpidering.userAgent" class="form-control"/>
                                </div>
                            </div>
                            <!--  -->
                            <h4><spring:message code="temp.weakcredcheck"/></h4>
                            <div class="form-group">
                                <div class="col-sm-10">
                                    <div class="checkbox checkbox-inline">
                                        <input type="checkbox" id="st_ws_common_passwords"
                                               <c:if test="${st.webSpidering.checkCommonPasswords eq 1}">checked='checked'</c:if> value="${st.webSpidering.checkCommonPasswords}"
                                               name="webSpidering.checkCommonPasswords">
                                        <label for="st_ws_common_passwords"><spring:message code="temp.checkcommonpwd"/></label>
                                        <p><spring:message code="temp.info228"/></p>
                                    </div>
                                </div>
                            </div>
                            <h4><spring:message code="temp.performance"/></h4>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_max_fgn_host"><spring:message code="temp.maxouterhost"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_max_fgn_host" name="webSpidering.maxForeignHostCache" value="${st.webSpidering.maxForeignHostCache }" class="form-control"
                                           maxlength="3" value="100"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_readtimeout"><spring:message code="temp.crawlerresponsetime"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_readtimeout" name="webSpidering.readTimeout" value="${st.webSpidering.readTimeout }" class="form-control" maxlength="7"
                                           value="120000"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_maxdepth"><spring:message code="temp.maxDepth"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_maxdepth" name="webSpidering.maxDepth" value="${st.webSpidering.maxDepth }" class="form-control" maxlength="3" value="6"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_maxtime"><spring:message code="temp.maxcrawlingtime"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_maxtime" name="webSpidering.maxTimePerWebsite" value="${st.webSpidering.maxTimePerWebsite }" class="form-control" maxlength="7"
                                           value="0"/>
                                </div>
                                <div class="col-sm-2">0 = <spring:message code="temp.nolimit"/></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_maxpages"><spring:message code="temp.maxPages"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_maxpages" name="webSpidering.maxPagesPerWebsite" value="${st.webSpidering.maxPagesPerWebsite }" class="form-control" maxlength="7"
                                           value="10000"/>
                                </div>
                                <div class="col-sm-2">0 = <spring:message code="temp.nolimit"/></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_max_retries"><spring:message code="temp.maxRetries"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_max_retries" name="webSpidering.maxRetries" value="${st.webSpidering.maxRetries }" class="form-control" maxlength="3" value="2"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_threads"><spring:message code="temp.maxThreads"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_threads" name="webSpidering.maxThreadsPerEndpoint" value="${st.webSpidering.maxThreadsPerEndpoint }" class="form-control" maxlength="3"
                                           value="3"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_skip_daemons"><spring:message code="temp.skipDaemons"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_skip_daemons" name="webSpidering.skipDaemons" value="${st.webSpidering.skipDaemons }" class="form-control"
                                           value="Virata-EmWeb, Allegro-Software-RomPager, JetDirect, HP JetDirect, HP Web Jetadmin, HP-ChaiSOE, HP-ChaiServer, CUPS, DigitalV6-HTTPD, Rapid Logic, Agranat-EmWeb, cisco-IOS, RAC_ONE_HTTP, RMC Webserver, EWS-NIC3, EMWHTTPD, IOS, ESWeb"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_max_ref_dist"><spring:message code="temp.maxRefDistance"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_max_ref_dist" name="webSpidering.maxRefDistance" value="${st.webSpidering.maxRefDistance }" class="form-control" maxlength="3"
                                           value="6"/>
                                </div>
                            </div>
                            <h4><spring:message code="temp.patterns"/></h4>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_sens_field_regex"><spring:message code="temp.sensDataNameRegex"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_sens_field_regex" name="webSpidering.sensDataNameRegex" value="${st.webSpidering.sensDataNameRegex }" class="form-control"
                                           value="(p|pass)(word|phrase|wd|code)"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_sens_content_regex"><spring:message code="temp.sensContentRegex"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_sens_content_regex" name="webSpidering.sensContentRegex" value="${st.webSpidering.sensContentRegex }" class="form-control"/>
                                </div>
                            </div>
                            <h4><spring:message code="temp.path"/></h4>
                            <div class="form-group">
                                <div class="col-sm-4">
                                    <div class="checkbox checkbox-inline">
                                        <input type="checkbox" id="st_ws_robots" name="webSpidering.honorRobots"
                                               <c:if test="${st.webSpidering.honorRobots eq 1}">checked='checked'</c:if> value="${st.webSpidering.honorRobots }">
                                        <label for="st_ws_robots"><spring:message code="temp.honorRobots"/></label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_bootstrap_paths"><spring:message code="temp.bootstrapPaths"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_bootstrap_paths" name="webSpidering.bootstrapPaths" value="${st.webSpidering.bootstrapPaths }" class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3" for="st_ws_excluded_paths"><spring:message code="temp.excludedPaths"/></label>
                                <div class="col-sm-6">
                                    <input type="text" id="st_ws_excluded_paths" name="webSpidering.excludedPaths" value="${st.webSpidering.excludedPaths }" class="form-control"/>
                                </div>
                            </div>
                            <h4><spring:message code="temp.restrictions"/></h4>
                            <div class="form-group">
                                <div class="col-sm-9">
                                    <div class="checkbox checkbox-inline">
                                        <input type="checkbox" id="st_ws_skip_printers" name="webSpidering.skipPrinters"
                                               <c:if test="${st.webSpidering.skipPrinters eq 1}">checked='checked'</c:if> value="${st.webSpidering.skipPrinters }">
                                        <label for="st_ws_skip_printers"><spring:message code="temp.info300"/></label>
                                        <p><spring:message code="temp.info301"/></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="tab-pane" id="tab_5">
                            <h4><spring:message code="policy.scapset"/></h4>
                        	<div class="form-group">
                                <div class="checkbox">
                                    <div class="checkbox checkbox-inline">
                                        <input id="st_persistARFResultsCheckBox" type="checkbox"
                                               <c:if test="${st.policies.persistARFResults.enabled eq 1 }">checked='checked'</c:if> name="policies.persistARFResults.enabled"
                                               value="${st.policies.persistARFResults.enabled }">
                                        <label for="st_persistARFResultsCheckBox"><spring:message code="policy.storescapdata"/></label>
                                    </div>
                                </div>
                            </div> 
                        	<div class="form-group">
                                <div class="checkbox">
                                    <div class="checkbox checkbox-inline">
                                        <input id="st_enableRecursiveFileSystemSearchCheckBox" type="checkbox"
                                               <c:if test="${st.policies.enableRecursiveFileSystemSearch.enabled eq 1 }">checked='checked'</c:if> name="policies.enableRecursiveFileSystemSearch.enabled"
                                               value="${st.policies.enableRecursiveFileSystemSearch.enabled }">
                                        <label for="st_enableRecursiveFileSystemSearchCheckBox"><spring:message code="policy.enablerecursive"/></label>
                                    </div>
                                </div>
                            </div> 
                            <h4><spring:message code="policy.selectedpolicy"/></h4>
	                        <div class="panel-group accordion" id="tab_group_policy">
								<div class="panel panel-bordered panel-info">
									<div class="panel-heading">
										<h4 class="panel-title">
											<div class="checkbox checkbox-single ml10" style="padding-top:10px !important;float:left;"><input type="checkbox" name="cisCheckAll"><label></label></div>
											<a data-toggle="collapse" data-parent="#tab_group_policy" href="#policy_cis">
												<i class="fa fa-plus pull-left"></i><span>CIS (${fn:length(cisPolicy)})</span>
											</a>
										</h4>
									</div>
									<div id="policy_cis" class="panel-collapse collapse">
										<div class="panel-body table-scrollable no-padding">
											<table id="tb_cis_policy">
									        	<tbody>
									        		<c:if test="${cisPolicy!=null && fn:length(cisPolicy) > 0}">
														<c:forEach items="${cisPolicy }" var="policy" varStatus="status">
												        	<tr>
												        		<td>
												        			<div class="checkbox"><input type="checkbox" name="cisCheckBox"
												        			<c:forEach items="${st.policies.enabled.list }" var="bk" varStatus="index">
													        			<c:if test="${(policy.benchmarkScope eq bk.scope) && 
													        						(policy.benchmarkName eq bk.benchmarkID) &&
													        						(policy.benchmarkVersion eq bk.benchmarkVersion) &&
													        						(policy.policyName eq bk.policy.policyID)}">
													        				checked='checked'
													        			</c:if>
												        			</c:forEach>
												        			><label></label>
												        			</div>
												        		</td>
													            <td>
													            	<span class="file pl20-i">${policy.policyTitle }</span>
													            	<input type="hidden" value="${policy.benchmarkScope }" name="scope">
													            	<input type="hidden" value="${policy.benchmarkName }" name="benchmarkID">
													            	<input type="hidden" value="${policy.benchmarkVersion }" name="benchmarkVersion">
													            	<input type="hidden" value="${policy.policyName }" name="policyID">
													            </td>
												          	</tr>
												        </c:forEach>
												     </c:if>
										        </tbody>
										     </table>
										</div>
									</div>
								</div>
								<div class="panel panel-bordered panel-info">
									<div class="panel-heading">
										<h4 class="panel-title">
											<div class="checkbox checkbox-single ml10" style="padding-top:10px !important;float:left;"><input type="checkbox" name="customCheckAll"><label></label></div>
											<a data-toggle="collapse" data-parent="#tab_group_policy" href="#policy_custom" >
												<i class="fa fa-plus pull-left"></i><span>Custom (${fn:length(customPolicy)})</span>
											</a>
										</h4>
									</div>
									<div id="policy_custom" class="panel-collapse collapse">
										<div class="panel-body table-scrollable no-padding">
											<table id="tb_custom_policy">
									        	<tbody>
									        		<c:if test="${customPolicy!=null && fn:length(customPolicy) > 0}">
														<c:forEach items="${customPolicy }" var="policy" varStatus="status">
												        	<tr>
												        		<td><div class="checkbox"><input type="checkbox" name="customCheckBox"
													        		<c:forEach items="${st.policies.enabled.list }" var="bk" varStatus="index">
													        			<c:if test="${(policy.benchmarkScope eq bk.scope) && 
													        						(policy.benchmarkName eq bk.benchmarkID) &&
													        						(policy.benchmarkVersion eq bk.benchmarkVersion) &&
													        						(policy.policyName eq bk.policy.policyID)}">
													        				checked='checked'
													        			</c:if>
												        			</c:forEach>
												        		><label></label></div></td>
													            <td>
													            	<span class="file pl20-i">${policy.policyTitle }</span>
													            	<input type="hidden" value="${policy.benchmarkScope }" name="scope">
													            	<input type="hidden" value="${policy.benchmarkName }" name="benchmarkID">
													            	<input type="hidden" value="${policy.benchmarkVersion }" name="benchmarkVersion">
													            	<input type="hidden" value="${policy.policyName }" name="policyID">
													            </td>
												          	</tr>
												        </c:forEach>
												     </c:if>
										        </tbody>
										     </table>
										</div>
									</div>
								</div>
							</div>
						</div>
                        <div class="tab-footer">
                            <div class="pull-right">
                                <button type="submit" class="btn btn-primary" <c:if test="${defaultTemp }">disabled</c:if>><spring:message code="action.save"/></button>
                                <button type="reset" class="btn btn-default" onclick="returnToList('scan/template/list');"><spring:message code="action.cancel"/></button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </section>
    </div>
    <%@ include file="../common/commonFooter.jsp" %>
</div>
<%@ include file="../common/lowVersion.jsp" %>
<%@ include file="../common/commonJavaScript.jsp" %>
<script type="text/javascript" src="resources/js/vendor/ionsilder/js/ion.rangeSlider.min.js"></script>
<script type="text/javascript" src="resources/js/template/template-dialog.js"></script>
<script type="text/javascript" src="resources/js/template/template-config.js"></script>
</body>
</html>
