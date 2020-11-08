package de.traendy.featureflag

enum class FeatureFlag(
    override val key: String,
    override val title: String,
    override val explanation: String,
    override val defaultValue: Boolean = true
) : Feature {
    BOTTOM_SHEET_ADD_DIALOG(
        "feature.bottomsheet.add.dialog",
        "Add Code bottom sheet dialog",
        "Enables bottom sheet dialog to add new qr codes."
    ),
    SHARE_CONTENT(
        "feature.share.content",
        "Share content button",
        "Allows the user to share qr code content."
    ),
    MAIL_QR_CODE(
        "feature.mail.qrcode",
        "Adds mail Qr Code",
        "Allows the user to generate a email based qr code."
    )
}
