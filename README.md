# LAB 11 – GPS et Google Maps sous Android

## 📌 Objectif

Ce projet Android a pour objectif d'intégrer Google Maps et les services de localisation afin de :

* Afficher une carte Google Maps dans l'application.
* Demander la permission d'accès à la localisation.
* Écouter les changements de position de l'utilisateur.
* Afficher un marqueur (Marker) sur la position actuelle.
* Centrer et zoomer automatiquement la carte sur la localisation.
* Détecter si le GPS est désactivé et proposer à l'utilisateur de l'activer.

---

## 🛠 Technologies utilisées

* Java
* Android Studio
* Google Maps SDK for Android
* Android LocationManager
* Google Play Services

---

## 📂 Structure du projet

Le projet contient principalement les fichiers suivants :

```text
app/
├── java/
│   └── MapsActivity.java
├── res/
│   ├── layout/
│   │   └── activity_maps.xml
│   └── values/
│       └── google_maps_api.xml
└── AndroidManifest.xml
```

---

## ⚙️ Configuration de Google Maps

### 1. Créer une clé API Google Maps

1. Accéder à Google Cloud Console.
2. Créer ou sélectionner un projet.
3. Activer **Maps SDK for Android**.
4. Générer une clé API.

### 2. Ajouter la clé API

Dans le fichier :

```text
res/values/google_maps_api.xml
```

Remplacer la valeur :

```xml
<string name="google_maps_key">
    VOTRE_CLE_API
</string>
```

par votre propre clé API.

---

## 🔐 Permissions requises

Ajouter les permissions suivantes dans le fichier `AndroidManifest.xml` :

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.INTERNET"/>
```

L'application demande également la permission de localisation à l'exécution (Runtime Permission).

---

## 📍 Fonctionnalités implémentées

### Affichage de Google Maps

La carte est chargée via un `SupportMapFragment` et initialisée dans la méthode :

```java
onMapReady()
```

### Géolocalisation

L'application utilise :

```java
LocationManager
```

pour recevoir les mises à jour de position via :

```java
LocationManager.NETWORK_PROVIDER
```

ou

```java
LocationManager.GPS_PROVIDER
```

---

### Gestion des changements de position

À chaque nouvelle position :

* récupération de la latitude et de la longitude ;
* mise à jour du marqueur ;
* déplacement automatique de la caméra ;
* zoom sur la position actuelle.

---

### Détection du GPS désactivé

Lorsque le GPS est désactivé, une boîte de dialogue s'affiche pour proposer à l'utilisateur d'activer les services de localisation.

---

## 🚀 Exécution du projet

1. Cloner le dépôt :

```bash
git clone https://github.com/votre-utilisateur/nom-du-projet.git
```

2. Ouvrir le projet dans Android Studio.

3. Ajouter votre clé Google Maps API.

4. Connecter un appareil Android ou lancer un émulateur.

5. Exécuter l'application.

---

## 📱 Résultat attendu

L'application doit :

* afficher une carte Google Maps ;
* demander l'autorisation d'accéder à la localisation ;
* afficher la position actuelle de l'utilisateur ;
* déplacer automatiquement la carte lors des changements de position ;
* afficher un message lorsque le GPS est désactivé.

---

## 👨‍💻 Auteur

Projet réalisé dans le cadre du laboratoire Android « GPS et Google Maps ».

---

## 📄 Licence

Projet académique destiné à l'apprentissage du développement Android et de la géolocalisation avec Google Maps.
