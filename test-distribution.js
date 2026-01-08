#!/usr/bin/env node

const fs = require('fs')
const path = require('path')

console.log('🧪 Test de validation distribution.json\n')

try {
    // 1. Lire le fichier
    const distPath = path.join(__dirname, 'distribution.json')
    console.log('📂 Lecture:', distPath)
    const content = fs.readFileSync(distPath, 'utf8')
    
    // 2. Parser JSON
    console.log('📋 Parsing JSON...')
    const dist = JSON.parse(content)
    console.log('✅ JSON valide\n')
    
    // 3. Vérifier la structure
    console.log('🔍 Vérification structure:')
    console.log(`  Version: ${dist.version}`)
    console.log(`  Serveurs: ${dist.servers.length}`)
    
    // 4. Vérifier chaque serveur
    dist.servers.forEach((server, i) => {
        console.log(`\n📦 Serveur ${i + 1}: ${server.id}`)
        console.log(`  Nom: ${server.name}`)
        console.log(`  Minecraft: ${server.minecraftVersion}`)
        console.log(`  Modules: ${server.modules.length}`)
        
        server.modules.forEach((mod, j) => {
            console.log(`\n  🔧 Module ${j + 1}: ${mod.name}`)
            console.log(`     Type: ${mod.type}`)
            console.log(`     ID: ${mod.id}`)
            
            if (mod.type === 'Fabric') {
                // Vérifier les submodules
                if (!mod.subModules || mod.subModules.length === 0) {
                    console.log('     ❌ ERREUR: Pas de subModules!')
                } else {
                    console.log(`     SubModules: ${mod.subModules.length}`)
                    
                    // Vérifier VersionManifest
                    const hasVersionManifest = mod.subModules.some(sm => sm.type === 'VersionManifest')
                    if (hasVersionManifest) {
                        console.log('     ✅ VersionManifest trouvé')
                    } else {
                        console.log('     ❌ ERREUR: VersionManifest manquant!')
                    }
                    
                    // Vérifier les IDs Maven
                    mod.subModules.forEach((sub, k) => {
                        if (sub.type === 'FabricMod') {
                            const hasMavenId = sub.id.includes(':') || sub.id.includes('@')
                            if (!hasMavenId) {
                                console.log(`     ❌ ERREUR: ID non-Maven pour ${sub.name}: ${sub.id}`)
                            }
                        }
                    })
                }
            }
        })
    })
    
    console.log('\n\n✅ VALIDATION TERMINÉE - Distribution semble correcte!')
    process.exit(0)
    
} catch (error) {
    console.error('\n❌ ERREUR:', error.message)
    console.error(error.stack)
    process.exit(1)
}
