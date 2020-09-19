package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class MailAgentService {

    public static String sendMail(String to, String challengeLink,String senderName) {
        boolean sent=false;
        System.out.println("mailTo "+to);
        String from= "MailAgent@lichesspro.com";
        System.out.println("challenge Link "+challengeLink);
        final String username = "emailapikey";//change accordingly
        final String password = "wSsVR61y+UH5X/h/lTz7ceduywlUVFL+EUh10Vup6XT6SKqU98c6xkfOVg7xGqIfGGJtRTZE8O0uzRwC1WAMjdx+w1wCDiiF9mqRe1U4J3x17qnvhDzMXm5ekhWOLIIIzgtsk2FlGsEn+g==";//change accordingly

        // Mention the SMTP server address. Below Pepipost's SMTP server is being used to send email
        String host = "smtp.transmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Challenge invitation");

            // This mail will have two parts, BODY and embedded image

            // Put the content of your message
            message.setContent(
                    "<!doctype html>\n" +
                            "<html ⚡4email>\n" +
                            " <head><meta charset=\"utf-8\"><style amp4email-boilerplate>body{visibility:hidden}</style><script async src=\"https://cdn.ampproject.org/v0.js\"></script> \n" +
                            "   \n" +
                            "  <style amp-custom>\n" +
                            "a[x-apple-data-detectors] {\n" +
                            "    color:inherit;\n" +
                            "    text-decoration:none;\n" +
                            "    font-size:inherit;\n" +
                            "    font-family:inherit;\n" +
                            "    font-weight:inherit;\n" +
                            "    line-height:inherit;\n" +
                            "}\n" +
                            ".es-desk-hidden {\n" +
                            "    display:none;\n" +
                            "    float:left;\n" +
                            "    overflow:hidden;\n" +
                            "    width:0;\n" +
                            "    max-height:0;\n" +
                            "    line-height:0;\n" +
                            "}\n" +
                            "s {\n" +
                            "    text-decoration:line-through;\n" +
                            "}\n" +
                            "body {\n" +
                            "    width:100%;\n" +
                            "    font-family:\"arial\", \"helvetica neue\", \"helvetica\", \"sans-serif\";\n" +
                            "}\n" +
                            "table {\n" +
                            "    border-collapse:collapse;\n" +
                            "    border-spacing:0px;\n" +
                            "}\n" +
                            "table td, html, body, .es-wrapper {\n" +
                            "    padding:0;\n" +
                            "    Margin:0;\n" +
                            "}\n" +
                            ".es-content, .es-header, .es-footer {\n" +
                            "    table-layout:fixed;\n" +
                            "    width:100%;\n" +
                            "}\n" +
                            "p, hr {\n" +
                            "    Margin:0;\n" +
                            "}\n" +
                            "h1, h2, h3, h4, h5 {\n" +
                            "    Margin:0;\n" +
                            "    line-height:120%;\n" +
                            "    font-family:\"arial\", \"helvetica neue\", \"helvetica\", \"sans-serif\";\n" +
                            "}\n" +
                            ".cont{\n" +
                            "  position: relative;\n" +
                            "  text-align: center;\n" +
                            "}\n" +
                            ".cent{\n" +
                            "  position: absolute;\n" +
                            "  top: 50%;\n" +
                            "  left: 50%;\n" +
                            "  transform: translate(-50%, -50%);\n" +
                            "}\n" +
                            ".es-left {\n" +
                            "    float:left;\n" +
                            "}\n" +
                            ".es-right {\n" +
                            "    float:right;\n" +
                            "}\n" +
                            ".es-p5 {\n" +
                            "    padding:5px;\n" +
                            "}\n" +
                            ".es-p5t {\n" +
                            "    padding-top:5px;\n" +
                            "}\n" +
                            ".es-p5b {\n" +
                            "    padding-bottom:5px;\n" +
                            "}\n" +
                            ".es-p5l {\n" +
                            "    padding-left:5px;\n" +
                            "}\n" +
                            ".es-p5r {\n" +
                            "    padding-right:5px;\n" +
                            "}\n" +
                            ".es-p10 {\n" +
                            "    padding:10px;\n" +
                            "}\n" +
                            ".es-p10t {\n" +
                            "    padding-top:10px;\n" +
                            "}\n" +
                            ".es-p10b {\n" +
                            "    padding-bottom:10px;\n" +
                            "}\n" +
                            ".es-p10l {\n" +
                            "    padding-left:10px;\n" +
                            "}\n" +
                            ".es-p10r {\n" +
                            "    padding-right:10px;\n" +
                            "}\n" +
                            ".es-p15 {\n" +
                            "    padding:15px;\n" +
                            "}\n" +
                            ".es-p15t {\n" +
                            "    padding-top:15px;\n" +
                            "}\n" +
                            ".es-p15b {\n" +
                            "    padding-bottom:15px;\n" +
                            "}\n" +
                            ".es-p15l {\n" +
                            "    padding-left:15px;\n" +
                            "}\n" +
                            ".es-p15r {\n" +
                            "    padding-right:15px;\n" +
                            "}\n" +
                            ".es-p20 {\n" +
                            "    padding:20px;\n" +
                            "}\n" +
                            ".es-p20t {\n" +
                            "    padding-top:20px;\n" +
                            "}\n" +
                            ".es-p20b {\n" +
                            "    padding-bottom:20px;\n" +
                            "}\n" +
                            ".es-p20l {\n" +
                            "    padding-left:20px;\n" +
                            "}\n" +
                            ".es-p20r {\n" +
                            "    padding-right:20px;\n" +
                            "}\n" +
                            ".es-p25 {\n" +
                            "    padding:25px;\n" +
                            "}\n" +
                            ".es-p25t {\n" +
                            "    padding-top:25px;\n" +
                            "}\n" +
                            ".es-p25b {\n" +
                            "    padding-bottom:25px;\n" +
                            "}\n" +
                            ".es-p25l {\n" +
                            "    padding-left:25px;\n" +
                            "}\n" +
                            ".es-p25r {\n" +
                            "    padding-right:25px;\n" +
                            "}\n" +
                            ".es-p30 {\n" +
                            "    padding:30px;\n" +
                            "}\n" +
                            ".es-p30t {\n" +
                            "    padding-top:30px;\n" +
                            "}\n" +
                            ".es-p30b {\n" +
                            "    padding-bottom:30px;\n" +
                            "}\n" +
                            ".es-p30l {\n" +
                            "    padding-left:30px;\n" +
                            "}\n" +
                            ".es-p30r {\n" +
                            "    padding-right:30px;\n" +
                            "}\n" +
                            ".es-p35 {\n" +
                            "    padding:35px;\n" +
                            "}\n" +
                            ".es-p35t {\n" +
                            "    padding-top:35px;\n" +
                            "}\n" +
                            ".es-p35b {\n" +
                            "    padding-bottom:35px;\n" +
                            "}\n" +
                            ".es-p35l {\n" +
                            "    padding-left:35px;\n" +
                            "}\n" +
                            ".es-p35r {\n" +
                            "    padding-right:35px;\n" +
                            "}\n" +
                            ".es-p40 {\n" +
                            "    padding:40px;\n" +
                            "}\n" +
                            ".es-p40t {\n" +
                            "    padding-top:40px;\n" +
                            "}\n" +
                            ".es-p40b {\n" +
                            "    padding-bottom:40px;\n" +
                            "}\n" +
                            ".es-p40l {\n" +
                            "    padding-left:40px;\n" +
                            "}\n" +
                            ".es-p40r {\n" +
                            "    padding-right:40px;\n" +
                            "}\n" +
                            ".es-menu td {\n" +
                            "    border:0;\n" +
                            "}\n" +
                            "a {\n" +
                            "    font-family:\"arial\", \"helvetica neue\", \"helvetica\", \"sans-serif\";\n" +
                            "    font-size:14px;\n" +
                            "    text-decoration:underline;\n" +
                            "}\n" +
                            "h1 {\n" +
                            "    font-size:30px;\n" +
                            "    font-style:normal;\n" +
                            "    font-weight:normal;\n" +
                            "    color:#333333;\n" +
                            "}\n" +
                            "h1 a {\n" +
                            "    font-size:30px;\n" +
                            "}\n" +
                            "h2 {\n" +
                            "    font-size:24px;\n" +
                            "    font-style:normal;\n" +
                            "    font-weight:normal;\n" +
                            "    color:#333333;\n" +
                            "}\n" +
                            "h2 a {\n" +
                            "    font-size:24px;\n" +
                            "}\n" +
                            "h3 {\n" +
                            "    font-size:20px;\n" +
                            "    font-style:normal;\n" +
                            "    font-weight:bold;\n" +
                            "    color:#333333;\n" +
                            "}\n" +
                            "h3 a {\n" +
                            "    font-size:20px;\n" +
                            "}\n" +
                            "p, ul li, ol li {\n" +
                            "    font-size:14px;\n" +
                            "    font-family:\"arial\", \"helvetica neue\", \"helvetica\", \"sans-serif\";\n" +
                            "    line-height:150%;\n" +
                            "}\n" +
                            "ul li, ol li {\n" +
                            "    Margin-bottom:15px;\n" +
                            "}\n" +
                            ".es-menu td a {\n" +
                            "    text-decoration:none;\n" +
                            "    display:block;\n" +
                            "}\n" +
                            ".es-menu amp-img, .es-button amp-img {\n" +
                            "    vertical-align:middle;\n" +
                            "}\n" +
                            ".es-wrapper {\n" +
                            "    width:100%;\n" +
                            "    height:100%;\n" +
                            "}\n" +
                            ".es-wrapper-color {\n" +
                            "    background-color:#F6F6F6;\n" +
                            "}\n" +
                            ".es-content-body {\n" +
                            "    background-color:#FFFFFF;\n" +
                            "}\n" +
                            ".es-content-body p, .es-content-body ul li, .es-content-body ol li {\n" +
                            "    color:#333333;\n" +
                            "}\n" +
                            ".es-content-body a {\n" +
                            "    color:#6AA84F;\n" +
                            "}\n" +
                            ".es-header {\n" +
                            "    background-color:transparent;\n" +
                            "}\n" +
                            ".es-header-body {\n" +
                            "    background-color:#FFFFFF;\n" +
                            "}\n" +
                            ".es-header-body p, .es-header-body ul li, .es-header-body ol li {\n" +
                            "    color:#CCCCCC;\n" +
                            "    font-size:12px;\n" +
                            "}\n" +
                            ".es-header-body a {\n" +
                            "    color:#CCCCCC;\n" +
                            "    font-size:12px;\n" +
                            "}\n" +
                            ".es-footer {\n" +
                            "    background-color:transparent;\n" +
                            "}\n" +
                            ".es-footer-body {\n" +
                            "    background-color:#EFEFEF;\n" +
                            "}\n" +
                            ".es-footer-body p, .es-footer-body ul li, .es-footer-body ol li {\n" +
                            "    color:#666666;\n" +
                            "    font-size:12px;\n" +
                            "}\n" +
                            ".es-footer-body a {\n" +
                            "    color:#333333;\n" +
                            "    font-size:12px;\n" +
                            "}\n" +
                            ".es-infoblock, .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li {\n" +
                            "    line-height:120%;\n" +
                            "    font-size:12px;\n" +
                            "    color:#CCCCCC;\n" +
                            "}\n" +
                            ".es-infoblock a {\n" +
                            "    font-size:12px;\n" +
                            "    color:#CCCCCC;\n" +
                            "}\n" +
                            "a.es-button {\n" +
                            "    border-style:solid;\n" +
                            "    border-color:#6AA84F;\n" +
                            "    border-width:10px 20px 10px 20px;\n" +
                            "    display:inline-block;\n" +
                            "    background:#6AA84F;\n" +
                            "    border-radius:5px;\n" +
                            "    font-size:18px;\n" +
                            "    font-family:\"arial\", \"helvetica neue\", \"helvetica\", \"sans-serif\";\n" +
                            "    font-weight:normal;\n" +
                            "    font-style:normal;\n" +
                            "    line-height:120%;\n" +
                            "    color:#FFFFFF;\n" +
                            "    text-decoration:none;\n" +
                            "    width:auto;\n" +
                            "    text-align:center;\n" +
                            "}\n" +
                            ".es-button-border {\n" +
                            "    border-style:solid solid solid solid;\n" +
                            "    border-color:#FFFFFF #FFFFFF #FFFFFF #FFFFFF;\n" +
                            "    background:#2CB543;\n" +
                            "    border-width:0px 0px 0px 0px;\n" +
                            "    display:inline-block;\n" +
                            "    border-radius:5px;\n" +
                            "    width:auto;\n" +
                            "}\n" +
                            "@media only screen and (max-width:600px) {p, ul li, ol li, a { font-size:16px; line-height:150% } h1 { font-size:30px; text-align:center; line-height:120% } h2 { font-size:26px; text-align:center; line-height:120% } h3 { font-size:20px; text-align:center; line-height:120% } h1 a { font-size:30px } h2 a { font-size:26px } h3 a { font-size:20px } .es-menu td a { font-size:16px } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:12px } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px } *[class=\"gmail-fix\"] { display:none } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left } .es-m-txt-r amp-img { float:right } .es-m-txt-c amp-img { margin:0 auto } .es-m-txt-l amp-img { float:left } .es-button-border { display:block } a.es-button { font-size:20px; display:block; border-width:10px 0px 10px 0px } .es-btn-fw { border-width:10px 0px; text-align:center } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100% } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%; max-width:600px } .es-adapt-td { display:block; width:100% } .adapt-img { width:100%; height:auto } td.es-m-p0 { padding:0px } td.es-m-p0r { padding-right:0px } td.es-m-p0l { padding-left:0px } td.es-m-p0t { padding-top:0px } td.es-m-p0b { padding-bottom:0 } td.es-m-p20b { padding-bottom:20px } .es-mobile-hidden, .es-hidden { display:none } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto; overflow:visible; float:none; max-height:inherit; line-height:inherit } tr.es-desk-hidden { display:table-row } table.es-desk-hidden { display:table } td.es-desk-menu-hidden { display:table-cell } table.es-table-not-adapt, .esd-block-html table { width:auto } table.es-social { display:inline-block } table.es-social td { display:inline-block } }\n" +
                            "</style> \n" +
                            " </head> \n" +
                            " <body> \n" +
                            "  <div class=\"es-wrapper-color\"> \n" +
                            "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                            "     <tr> \n" +
                            "      <td valign=\"top\"> \n" +
                            "         <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\"> \n" +
                            "         <tr> \n" +
                            "          <td align=\"center\"> \n" +
                            "           <table class=\"es-content-body\" style=\"background-color: transparent\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n" +
                            "             <tr> \n" +
                            "              <td class=\"es-p15t es-p10b es-p15r es-p15l\" align=\"left\" bgcolor=\"#ffffff\" style=\"background-color: #ffffff\"> \n" +
                            "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                            "                 <tr> \n" +
                            "                  <td width=\"570\" valign=\"top\" align=\"center\"> \n" +
                            "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\"> \n" +
                            "                     <tr> \n" +
                            "                      <td align=\"center\"><p style=\"line-height: 150%\"><h3 style=\"font-family:'Times New Roman' \">Challenge Invitation<br>From,<br>"+senderName+"</h3></p></td> \n" +
                            "                     </tr> \n" +
                            "                   </table></td> \n" +
                            "                 </tr> \n" +
                            "               </table></td> \n" +
                            "             </tr> \n" +
                            "           </table></td> \n" +
                            "         </tr> \n" +
                            "       </table> \n" +
                            "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n" +
                            "         <tr></tr> \n" +
                            "         <tr> \n" +
                            "          <td align=\"center\"> \n" +
                            "           <table class=\"es-header-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n" +
                            "             <tr> \n" +
                            "              <td  align=\"left\"> \n" +
                            "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                            "                      <td align=\"center\" style=\"font-size: 30px\"><a href=\"https://lichesspro.com\" target=\"_blank\"><img src=\"https://lichesspro.com/emailImages/logo2.png\" alt=\"LichesPro Logo\" title=\"LichesPro Logo\" style=\"display: block\" width=\"200\" height=\"200\" layout=\"responsive\"></img></a></td> \n" +
                            "                     </tr> \n" +
                            "                   </table></td> \n" +
                            "                 </tr> \n" +
                            "               </table></td> \n" +
                            "             </tr> \n" +
                            "           </table></td> \n" +
                            "         </tr> \n" +
                            "       </table> \n" +
                            "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n" +
                            "         <tr> \n" +
                            "          <td align=\"center\"> \n" +
                            "           <table class=\"es-content-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"> \n" +
                            "             <tr> \n" +
                            "              <td align=\"left\"> \n" +
                            "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                            "                 <tr> \n" +
                            "                  <td width=\"600\" valign=\"top\" align=\"center\"> \n" +
                            "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\"> \n" +
                            "                     <tr> \n" +
                            "                      <div class=\"cont\">\n" +
                            "                      <td style=\"position: relative\" align=\"center\"><img class=\"adapt-img\" src=\"https://lichesspro.com/emailImages/bg.png\" alt=\"Chess\" title=\"Challenge Invitation\" width=\"600\" style=\"display: block; opacity: 0.9\" height=\"300\" layout=\"responsive\" >\n" +
                            "                      </div></img></td>\n" +
                            "                    </div>\n" +
                            "                     </tr> \n" +
                            "                   </table></td> \n" +
                            "                 </tr> \n" +
                            "               </table></td> \n" +
                            "             </tr> \n" +
                            "           </table></td> \n" +
                            "         </tr> \n" +
                            "       </table> \n" +
                            "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n" +
                            "         <tr> \n" +
                            "          <td align=\"center\"> \n" +
                            "           <table class=\"es-content-body\" style=\"background-color: #ffffff\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"> \n" +
                            "             <tr> \n" +
                            "              <td class=\"es-p20t es-p20r es-p20l\" align=\"left\"> \n" +
                            "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                            "                 <tr> \n" +
                            "                  <td width=\"560\" valign=\"top\" align=\"center\"> \n" +
                            "                   <table style=\"border-bottom:1px solid #efefef\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\"> \n" +
                            "                     <tr> \n" +
                            "                      <td class=\"es-p15t es-p30b\" align=\"center\"><span class=\"es-button-border\" style=\"background: #6aa84f none repeat scroll 0% 0%\"><a href=\""+challengeLink+"\" class=\"es-button\" target=\"_blank\" style=\"background: #6aa84f none repeat scroll 0% 0%;border-color: #6aa84f\">Accept Challenge</a></span></td> \n" +
                            "                     </tr> \n" +
                            "                   </table></td> \n" +
                            "                 </tr> \n" +
                            "               </table></td> \n" +
                            "             </tr> \n" +
                            "           </table></td> \n" +
                            "         </tr> \n" +
                            "       </table> \n" +
                            "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n" +
                            "         <tr> \n" +
                            "          <td align=\"center\"> \n" +
                            "           <table class=\"es-content-body\" style=\"background-color: #ffffff\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"> \n" +
                            "             <tr> \n" +
                            "              <td class=\"es-p20t es-p20r es-p20l\" align=\"left\"> \n" +
                            "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                            "                 <tr> \n" +
                            "                  <td width=\"560\" valign=\"top\" align=\"center\"> \n" +
                            "                   <table style=\"border-top:1px solid #efefef\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\"> \n" +
                            "                     <tr> \n" +
                            "                      <td class=\"es-p25t es-p10b\" align=\"center\"><h2>In Lichesspro we have the following features:</h2></td> \n" +
                            "                     </tr> \n" +
                            "                   </table></td> \n" +
                            "                 </tr> \n" +
                            "               </table></td> \n" +
                            "             </tr> \n" +
                            "             <tr> \n" +
                            "              <td class=\"es-p20r es-p20l\" align=\"left\"> \n" +
                            "               <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"> \n" +
                            "                 <tr> \n" +
                            "                  <td width=\"270\" align=\"left\"> \n" +
                            "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\"> \n" +
                            "                     <tr> \n" +
                            "                      <td class=\"es-p10t\" align=\"left\"> \n" +
                            "                       <ul> \n" +
                            "                        <li style=\"line-height: 150%\">Play chess with ur friends<br></li> \n" +
                            "                        <li style=\"line-height: 150%\">Create Challenges</li> \n" +
                            "                        <li style=\"line-height: 150%\">Compete with an AI opponent</li> \n" +
                            "                       </ul></td> \n" +
                            "                     </tr> \n" +
                            "                   </table></td> \n" +
                            "                 </tr> \n" +
                            "               </table> \n" +
                            "               <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\"> \n" +
                            "                 <tr> \n" +
                            "                  <td width=\"270\" align=\"left\"> \n" +
                            "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\"> \n" +
                            "                     <tr> \n" +
                            "                      <td class=\"es-p10t\" align=\"left\"> \n" +
                            "                       <ul> \n" +
                            "                        <li style=\"line-height: 150%\">Sit and watch a chess game just by uploading a pgn file&nbsp;</li> \n" +
                            "                        <li style=\"line-height: 150%\">Review your own game from game history</li> \n" +
                            "                       </ul></td> \n" +
                            "                     </tr> \n" +
                            "                   </table></td> \n" +
                            "                 </tr> \n" +
                            "               </table></td> \n" +
                            "             </tr> \n" +
                            "             <tr> \n" +
                            "              <td class=\"es-p30b es-p20r es-p20l\" align=\"left\"> \n" +
                            "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"> \n" +
                            "                 <tr> \n" +
                            "                  <td width=\"560\" valign=\"top\" align=\"center\"> \n" +
                            "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\"> \n" +
                            "                     <tr> \n" +
                            "                      <td class=\"es-p15t\" align=\"center\"><span class=\"es-button-border\"><a href=\"https://lichesspro.com\" class=\"es-button\" target=\"_blank\">Visit Our Page</a></span></td> \n" +
                            "                     </tr> \n" +
                            "                   </table></td> \n" +
                            "                 </tr> \n" +
                            "               </table></td> \n" +
                            "             </tr> \n" +
                            "           </table></td> \n" +
                            "         </tr> \n" +
                            "       </table></td> \n" +
                            "     </tr> \n" +
                            "   </table> \n" +
                            "  </div> \n" +
                            "  <div class=\"banner-toolbar\"></div>  \n" +
                            " </body>\n" +
                            "</html>",
                    "text/html");

            // Send message
            Transport.send(message);
            sent = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        if (sent) {
            return "{\"sendStatus\":\"sent\"}";
        } else
            return "{\"sendStatus\":\"failed\"}";
    }

}



