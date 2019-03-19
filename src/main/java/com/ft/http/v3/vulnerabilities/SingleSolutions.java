package com.ft.http.v3.vulnerabilities;

//{
//    "additionalInformation": null,
//    "appliesTo": null,
//    "estimate": "PT1H30M",
//    "id": "ssl-replace-self-signed-cert",
//    "steps": {
//        "html": "<p><p>获取一个非自签名的TLS/SSL服务器证书，并将其安装在服务器上。获取新证书所需的精确指令取决于组织要求。一般情况下，需要生成一个证书请求，并将此请求保存为文件。然后将此文件发送到证书颁发机构（CA）进行处理。这个文件之后会被发往至某个认证中心进行处理。各组织可能有其内部证书颁发机构。如果没有，必须向受信任的证书颁发机构购买，例如<a href=\"http://www.thawte.com\">Thawte</a> 或者<a href=\"http://www.verisign.com\">Verisign</a>.</p></p>",
//        "text": "Obtain a new TLS/SSL server certificate that is NOT self-signed and install it on the server. The exact instructions for obtaining a new certificate depend on your organization's requirements. Generally, you will need to generate a certificate request and save the request as a file. This file is then sent to a Certificate Authority (CA) for processing. Your organization may have its own internal Certificate Authority. If not, you may have to pay for a certificate from a trusted external Certificate Authority, such as Thawte (http://www.thawte.com) or Verisign (http://www.verisign.com)."
//    },
//    "summary": {
//        "html": "更替TLS/SSL自签名证书。",
//        "text": "Replace TLS/SSL self-signed certificate"
//    },
//    "type": "CONFIGURATION"
//}

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SingleSolutions {

    public static class HtmoAndText {
        private String html;
        private String text;

        @JsonCreator
        public HtmoAndText(@JsonProperty("html") String html,
                                     @JsonProperty("text") String text) {
            this.html = html;
            this.text = text;
        }
    }

    public static class Steps {
        private String html;
        private String text;

        @JsonCreator
        public Steps(@JsonProperty("html") String html,
                     @JsonProperty("text") String text) {
            this.html = html;
            this.text = text;
        }
    }

    @JsonCreator
    public SingleSolutions(@JsonProperty("additionalInformation") HtmoAndText additionalInformation,
                           @JsonProperty("appliesTo") String appliesTo,
                           @JsonProperty("estimate") String estimate,
                           @JsonProperty("id") String id,
                           @JsonProperty("steps") HtmoAndText steps,
                           @JsonProperty("summary") HtmoAndText summary,
                           @JsonProperty("type") String type,
                           @JsonProperty("error") String error) {
        this.additionalInformation = additionalInformation;
        this.appliesTo = appliesTo;
        this.estimate = estimate;
        this.id = id;
        this.steps = steps;
        this.summary = summary;
        this.type = type;
        this.error = error;
    }

    private HtmoAndText additionalInformation;
    private String appliesTo;
    private String estimate;
    private String id;
    private HtmoAndText steps;
    private HtmoAndText summary;
    private String type;
    private String error;
}
