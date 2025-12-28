#!/bin/bash

# Script de lancement du Minecraft Educational Launcher
# Ce script facilite le démarrage pour les utilisateurs

echo "=================================="
echo "Minecraft Educational Launcher"
echo "Version 2.0.0"
echo "=================================="
echo ""

# Vérifier Java
echo "🔍 Vérification de Java..."
if ! command -v java &> /dev/null; then
    echo "❌ Java n'est pas installé ou n'est pas dans le PATH"
    echo "   Téléchargez Java 17+ sur : https://adoptium.net/"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
echo "✅ Java détecté : version $JAVA_VERSION"

if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "⚠️  Java 17 ou supérieur est recommandé"
fi

echo ""

# Vérifier Maven
echo "🔍 Vérification de Maven..."
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven n'est pas installé ou n'est pas dans le PATH"
    echo "   Téléchargez Maven sur : https://maven.apache.org/"
    exit 1
fi

MVN_VERSION=$(mvn -version 2>&1 | head -n 1)
echo "✅ Maven détecté : $MVN_VERSION"
echo ""

# Demander l'action
echo "Que voulez-vous faire ?"
echo "1) Compiler le projet (clean package)"
echo "2) Lancer l'application (javafx:run)"
echo "3) Compiler ET lancer"
echo "4) Nettoyer le projet (clean)"
echo ""
read -p "Votre choix (1-4) : " choice

case $choice in
    1)
        echo ""
        echo "📦 Compilation du projet..."
        mvn clean package
        ;;
    2)
        echo ""
        echo "🚀 Lancement de l'application..."
        mvn javafx:run
        ;;
    3)
        echo ""
        echo "📦 Compilation du projet..."
        mvn clean package
        if [ $? -eq 0 ]; then
            echo ""
            echo "🚀 Lancement de l'application..."
            mvn javafx:run
        else
            echo "❌ La compilation a échoué"
            exit 1
        fi
        ;;
    4)
        echo ""
        echo "🧹 Nettoyage du projet..."
        mvn clean
        echo "✅ Nettoyage terminé"
        ;;
    *)
        echo "❌ Choix invalide"
        exit 1
        ;;
esac

echo ""
echo "✅ Terminé !"
