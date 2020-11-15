package de.traendy.nocontact.qrcode

import junit.framework.TestCase

class WifiQrCodeContentBuilderTest : TestCase() {

    fun testAddSsid() {
        val builder = WifiQrCodeContentBuilder()
        builder.addSsid("ABC")
        assertEquals("WIFI:S:\"ABC\";;", builder.create())
        builder.addSsid("ABCG")
        assertEquals("WIFI:S:ABCG;;", builder.create())
    }

    fun testAddAuthType() {
        val builder = WifiQrCodeContentBuilder()
        for (authType in WifiAuthType.values()) {
            builder.addAuthType(authType)
            assertTrue(builder.create().contains("WIFI:T:"))
        }
    }

    fun testAddPassword() {
        val builder = WifiQrCodeContentBuilder()
        builder.addPassword("")
        assertEquals("WIFI:;", builder.create())
        builder.addPassword("ABC")
        assertEquals("WIFI:P:\"ABC\";;", builder.create())
        builder.addPassword("ABCG")
        assertEquals("WIFI:P:ABCG;;", builder.create())
    }

    fun testAddHiddenSsid() {
        val builder = WifiQrCodeContentBuilder()
        builder.addHiddenSsid(false)
        assertEquals("WIFI:H:false;;", builder.create())
        builder.addHiddenSsid(true)
        assertEquals("WIFI:H:true;;", builder.create())
    }

    fun testAddEapMethod() {
        val builder = WifiQrCodeContentBuilder()
        builder.addEapMethod("")
        assertEquals("WIFI:;", builder.create())
        builder.addEapMethod("DADA")
        assertEquals("WIFI:E:DADA;;", builder.create())

    }

    fun testAddAnonymousIdentity() {
        val builder = WifiQrCodeContentBuilder()
        builder.addAnonymousIdentity("")
        assertEquals("WIFI:;", builder.create())
        builder.addAnonymousIdentity("Identity")
        assertEquals("WIFI:A:Identity;;", builder.create())
    }

    fun testAddIdentity() {
        val builder = WifiQrCodeContentBuilder()
        builder.addIdentity("")
        assertEquals("WIFI:;", builder.create())
        builder.addIdentity("Identity")
        assertEquals("WIFI:I:Identity;;", builder.create())
    }

    fun testAddPhase2Method() {
        val builder = WifiQrCodeContentBuilder()
        builder.addPhase2Method("")
        assertEquals("WIFI:;", builder.create())
        builder.addPhase2Method("ph2method")
        assertEquals("WIFI:PH2:ph2method;;", builder.create())
    }

    fun testCreate() {
        val builder = WifiQrCodeContentBuilder()
        assertEquals("WIFI:;", builder.create())
        builder.addSsid("SSID")
        assertEquals("WIFI:S:SSID;;", builder.create())
        builder.addAuthType(WifiAuthType.WPA2_EAP)
        assertEquals("WIFI:S:SSID;T:WPA2-EAP;;", builder.create())
        builder.addPassword("ABC")
        assertEquals("WIFI:S:SSID;T:WPA2-EAP;P:\"ABC\";;", builder.create())
        builder.addPassword("ABCG")
        assertEquals("WIFI:S:SSID;T:WPA2-EAP;P:ABCG;;", builder.create())
        builder.addHiddenSsid(false)
        assertEquals("WIFI:S:SSID;T:WPA2-EAP;P:ABCG;H:false;;", builder.create())
        builder.addEapMethod("DADA")
        assertEquals("WIFI:S:SSID;T:WPA2-EAP;P:ABCG;H:false;E:DADA;;", builder.create())
        builder.addAnonymousIdentity("Identity")
        assertEquals("WIFI:S:SSID;T:WPA2-EAP;P:ABCG;H:false;E:DADA;A:Identity;;", builder.create())
        builder.addIdentity("Identity")
        assertEquals(
            "WIFI:S:SSID;T:WPA2-EAP;P:ABCG;H:false;E:DADA;A:Identity;I:Identity;;",
            builder.create()
        )
        builder.addPhase2Method("ph2method")
        assertEquals(
            "WIFI:S:SSID;T:WPA2-EAP;P:ABCG;H:false;E:DADA;A:Identity;I:Identity;PH2:ph2method;;",
            builder.create()
        )
    }
}