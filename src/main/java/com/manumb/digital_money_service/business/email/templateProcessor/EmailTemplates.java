package com.manumb.digital_money_service.business.email.templateProcessor;

public class EmailTemplates {

    private static final String confirmEmailHtml = "<!DOCTYPE html><html lang=es><head><meta charset=UTF-8 /><meta name=viewport content=\"width=device-width, " +
            "initial-scale=1.0\" /><style>body{margin:0;padding:0;font-family:Arial,sans-serif}p{margin:0}table{border-collapse:collapse;width:100%}" +
            ".container{max-width:534px;margin:auto}.header,.footer{background-color:#5b4fb9;color:#fff}.header img{display:block;margin:auto}" +
            ".button{display:inline-block;width:257px;height:45px;color:#fff;background-color:#2f3349;border-radius:12px;text-align:center;" +
            "line-height:45px;text-decoration:none;font-weight:bold}.text-center{text-align:center}.main-text{font-size:20px;font-weight:300}" +
            ".secondary-text{font-size:15px;font-weight:300}</style><meta charset=UTF-8 /><meta name=viewport content=\"width=device-width," +
            " initial-scale=1.0\" /><link rel=preconnect href=https://fonts.googleapis.com /><link rel=preconnect href=https://fonts.gstatic.com" +
            " crossorigin /><link href=\"https://fonts.googleapis.com/css2?family=Archivo:ital,wght@0,100..900;1,100..900&display=swap\" rel=stylesheet" +
            " /><title>Email Confirmation</title></head><body><table class=container><tr><td class=header style=\"height: 80px;\"> style=\"max-width: 100%" +
            "; height: auto;\" /></td></tr><tr><td style=\"padding: 20px;\" class=text-center><p style=\"font-size: 15px; margin: 30px 0; font-weight: 300; text-align: left;\"" +
            ">Hola {{name}},</p><div class=text-center style=\"margin-bottom: 15px;\"><p class=main-text style=\"font-size: 20px; margin-bottom: 5px;\">Gracias por registrarte e" +
            "n <span style=\"font-weight: 600;\">Digital Money Service</span></p><p>Es hora de confirmar tu correo electrónico</p></div><div class=\"text-center secondary-text\" style=" +
            "\"margin-bottom: 15px; font-weight: 300; font-size: 15px;\"><p>Para confirmar tu cuenta, copia el siguiente token de confirmacion y pegalo en el body del endpoint " +
            "confirmEmail.</p></div></div></td></tr><tr><td class" +
            "=footer style=\"height: 55px;\"></td></tr></table></body></html>";


    private static final String recoverPasswordHtml = "<!DOCTYPE html><html lang=es><head><meta charset=UTF-8 /><meta name=viewport content=\"width=device-width, initial-scale=1.0\"" +
            " /><style>body{margin:0;padding:0;font-family:Arial,sans-serif}p{margin:0}table{border-collapse:collapse;width:100%}.container{max-width:534px;margin:auto}.header," +
            ".footer{background-color:#5b4fb9;color:#fff}.header img{display:block;margin:auto}.button{display:inline-block;width:257px;height:45px;color:#fff;background-color:#2f3349;" +
            "border-radius:12px;text-align:center;line-height:45px;text-decoration:none;font-weight:bold}.text-center{text-align:center}.main-text{font-size:20px;font-weight:300}" +
            ".secondary-text{font-size:15px;font-weight:300}</style><meta charset=UTF-8 /><meta name=viewport content=\"width=device-width, initial-scale=1.0\" /><link rel=preconnect" +
            " href=https://fonts.googleapis.com /><link rel=preconnect href=https://fonts.gstatic.com crossorigin /><link href=\"https://fonts.googleapis.com/css2?family=Archivo:ital," +
            "wght@0,100..900;1,100..900&display=swap\" rel=stylesheet /><title>Email Confirmation</title></head><body><table class=container><tr><td class=header style=\"height: 80px;\">" +
            " style=\"max-width: 100%; height: auto;\" /></td></tr><tr><td style=\"padding: 20px;\" class=text-center><p style=\"font-size: " +
            "15px; margin: 30px 0; font-weight: 300; text-align: left;\">Hola {{name}},</p><div class=\"text-center secondary-text\" style=\"margin-bottom: 15px; font-weight: 300; font-size" +
            ": 15px;\"><p>Se solicitó una recuperación de contraseña en tu cuenta <span style=\"font-weight: 600;\">{{email}}</span>. Para continuar, copia el siguiente token de reseteo de" +
            " contraseña y pegalo en el body del endpoint modifyPassword.</p></div></td></tr><tr><t" +
            "d class=footer style=\"height: 55px;\"></td></tr></table></body></html>";

    public static String getConfirmEmailHtml() {
        return confirmEmailHtml;
    }

    public static String getRecoverPasswordHtml() {
        return recoverPasswordHtml;
    }
}
