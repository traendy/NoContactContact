package de.traendy.nocontact.qrcode

import java.util.regex.Pattern

enum class WifiAuthType {
    NoPass,
    WEP,
    WPA_EAP,
    WPA,
}

class WifiQrCodeContentBuilder {

    private var ssid = ""
    private var authType = ""
    private var password = ""
    private var hidden = ""
    private var eapMethod = ""
    private var anonymousIdentity = ""
    private var identity = ""
    private var phase2Method = ""

    fun addSsid(ssid: String): WifiQrCodeContentBuilder {
        this.ssid = "S:${sanitizeSsid(ssid)}"
        return this
    }

    fun addAuthType(authType: WifiAuthType): WifiQrCodeContentBuilder {
        val temp = when (authType) {
            WifiAuthType.NoPass -> "nopass"
            WifiAuthType.WEP -> "WEP"
            WifiAuthType.WPA_EAP -> "WPA2-EAP"
            WifiAuthType.WPA -> "WPA"
        }
        this.authType = "T:$temp"
        return this
    }

    fun addPassword(password: String): WifiQrCodeContentBuilder {
        this.password = "P:${sanitizePassword(password)}"
        return this
    }

    fun addHiddenSsid(hidden: Boolean): WifiQrCodeContentBuilder {
        if (hidden) {
            this.hidden = "H:true"
        } else {
            this.hidden = "H:false"
        }
        return this
    }

    fun addEapMethod(eapMethod: String): WifiQrCodeContentBuilder {
        this.eapMethod = "E:$eapMethod"
        return this
    }

    fun addAnonymousIdentity(anonymousIdentity: String): WifiQrCodeContentBuilder {
        this.anonymousIdentity = "A:$anonymousIdentity"
        return this
    }

    fun addIdentity(identity: String): WifiQrCodeContentBuilder {
        this.identity = "I:$identity"
        return this
    }

    fun addPhase2Method(phase2Method: String): WifiQrCodeContentBuilder {
        this.phase2Method = "PH2:$phase2Method"
        return this
    }

    private fun sanitizePassword(password: String): String {
        return couldBeInterpretedAsHex(password)
    }

    private fun couldBeInterpretedAsHex(text: String): String {
        val pattern = Pattern.compile("^[0-9A-F]+\$")
        if (pattern.matcher(text).matches()) {
            return "\"$text\""
        }
        return text
    }

    /**
     * Special characters \, ;, , and : should be escaped with a backslash (\) as in MECARD encoding.
     */
    private fun sanitizeSsid(ssid: String): String {
        ssid.replace('\\', '\\')
            .replace(";", "\\;")
            .replace(",", "\\,")
            .replace(":", "\\:")
        return couldBeInterpretedAsHex(ssid)
    }

    fun create(): String {
        return "WIFI:$authType$ssid$password$hidden$eapMethod$anonymousIdentity$identity$phase2Method"
    }
}