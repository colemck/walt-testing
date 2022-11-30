package id.walt.verifier.backend

import com.beust.klaxon.Klaxon
import id.walt.auditor.PolicyRequest
import id.walt.webwallet.backend.config.ExternalHostnameUrl
import id.walt.webwallet.backend.config.externalHostnameUrlValueConverter
import java.io.File

data class VerifierConfig(
    @ExternalHostnameUrl val verifierUiUrl: String = "http://localhost:4000",
    @ExternalHostnameUrl val verifierApiUrl: String = "http://localhost:8080/verifier-api",
    val wallets: Map<String, WalletConfiguration> = WalletConfiguration.getDefaultWalletConfigurations(),
    val additionalPolicies: List<PolicyRequest>? = null,
    val allowedWebhookHosts: List<String>? = null
) {
    companion object {
        val CONFIG_FILE = "${id.walt.WALTID_DATA_ROOT}/config/verifier-config.json"
        var config: VerifierConfig

        init {
            val cf = File(CONFIG_FILE)
            config = if (cf.exists()) {
                Klaxon().fieldConverter(ExternalHostnameUrl::class, externalHostnameUrlValueConverter)
                    .parse<VerifierConfig>(cf) ?: VerifierConfig()
            } else {
                VerifierConfig()
            }
        }
    }
}
