const { DistributionAPI } = require('helios-core/common')

const ConfigManager = require('./configmanager')

// Nemesis Launcher Distribution URL
// null = charge depuis le fichier local distribution.json (pas de pull distant)
// Pour utiliser un serveur distant, mettre l'URL ici (ex: 'https://nemesis-launcher.azuriom.cloud/distribution.json')
exports.REMOTE_DISTRO_URL = null

const api = new DistributionAPI(
    ConfigManager.getLauncherDirectory(),
    null, // Injected forcefully by the preloader.
    null, // Injected forcefully by the preloader.
    exports.REMOTE_DISTRO_URL,
    true  // devMode: true pour utiliser le fichier local distribution_dev.json
)

exports.DistroAPI = api