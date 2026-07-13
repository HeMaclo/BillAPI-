![BillAPI Banner](https://img.shields.io/badge/BillAPI-1.0--A-blue?style=flat-square&logo=minecraft)
![Fabric](https://img.shields.io/badge/Fabric-1.21.1-green?style=flat-square)
![Java](https://img.shields.io/badge/Java-21+-orange?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-red?style=flat-square)

# 🎯 BillAPI - Mod for Minecraft Fabric

**BillAPI** is a powerful and convenient mod for Minecraft 1.21.1, developed on the **Fabric** platform. The mod includes three core features to enhance your gaming experience: **Autoclicky**, **AimAssist**, and **ESP**.

> **⚠️ Caution:** This mod is intended for entertainment and learning purposes only. Its use on servers may be prohibited by administration. Use it wisely! 😄

---

## ✨ Features

### 🖱️ **Autoclicky**
Automatically attacks mobs and players when you look at them.

**Features:**
- ✅ Two click modes: **1.8 PvP** and **1.9+ PvP**
- ✅ Adjustable delay between clicks (1-20 ticks)
- ✅ Separate control for left and right clicks
- ✅ Automatic triggering when looking at a hitbox
- 🎮 Default Keybind: **V**

**Settings:**
| Parameter | Value | Description |
|-----------|-------|-------------|
| Mode | 1.8 / 1.9+ | Click mode (1.8 - fast, 1.9+ - with delays) |
| Delay | 1-20 | Delay between clicks (in ticks) |
| LMB | On/Off | Left click (attack) |
| RMB | On/Off | Right click (interaction) |

---

### 🎯 **AimAssist**
A smooth and legitimate aim assist for improved accuracy.

**Features:**
- ✅ Automatically finds the nearest player
- ✅ Smooth and natural snapping to the head
- ✅ Adjustable sensitivity (0.0 - 1.0)
- ✅ Adjustable snap speed
- ✅ Maximum activation distance
- ✅ "Hold" key mode for better control
- 🎮 Activation Key: **C**
- 🎮 Hold Key: **ALT**

**Settings:**
| Parameter | Value | Description |
|-----------|-------|-------------|
| Sensitivity | 0.0-1.0 | Aim speed (1.0 = instantaneous) |
| Speed | 1-20 | Duration of smooth aim (in ticks) |
| Distance | 1-64 | Maximum range to find players (blocks) |
| Smoothness | On/Off | Enable smooth aiming instead of snapping |

---

### 👻 **ESP**
Visual highlighting of players and mobs through walls.

**Features:**
- ✅ Highlight players
- ✅ Highlight hostile mobs
- ✅ Highlight animals (optional)
- ✅ Customizable box colors
- ✅ Renders through walls (without blocking view)
- 🎮 Default Keybind: **E**

**Settings:**
| Parameter | Value | Description |
|-----------|-------|-------------|
| Show Players | On/Off | Highlight other players |
| Show Mobs | On/Off | Highlight hostile mobs |
| Show Animals | On/Off | Highlight animals |
| Box Color | RGB | Highlight color (green by default) |

---

## 🚀 Installation

### Requirements
- **Minecraft Java Edition** version **1.21.1**
- **Fabric Loader** version **0.15.11+**
- **Java 21+** on your computer

### Steps

1.  **Download Fabric Installer** from [fabric.net](https://fabricmc.net/)
2.  **Install Fabric** for version 1.21.1
3.  **Download the BillAPI jar file** from [Releases](https://github.com/HeMaclo/BillAPI-/releases)
4.  **Place the file** in your `.minecraft/mods/` folder
5.  **Install Mod Menu** (optional, but recommended):
    - Download from [CurseForge](https://www.curseforge.com/minecraft/mods/modmenu)
    - Place it in your `.minecraft/mods/` folder
6.  **Launch Minecraft** and enjoy! 🎮

---

## ⚙️ Configuration

### Opening the Settings Menu

**Method 1: Via Mod Menu (recommended)**
1.  On the main menu, click **"Mods"**
2.  Find **"BillAPI"** in the list
3.  Click the config button (usually next to the mod name)
4.  The settings menu will open

**Method 2: Hotkeys**
- Press **V**, **C**, **E** to quickly toggle features on/off
- Hold **ALT** to use AimAssist in hold mode

### Config File Location

The config is saved to:
```

%appdata%/.minecraft/config/billapi/settings.json

```

You can edit it manually, but using the in-game menu is recommended.

---

## 🎮 Controls

### Default Keybinds

| Feature | Keybind | Action |
|---------|---------|--------|
| **AutoClicky** | V | Toggle On/Off |
| **AimAssist** | C | Toggle On/Off |
| **AimAssist Hold** | ALT | Hold to use |
| **ESP** | E | Toggle On/Off |

All keybinds can be remapped in **Controls** → **Key Binds**.

---

## 📁 Project Structure

```

BillAPI/
├── src/main/java/dev/zete1x/billapi/
│   ├── BillAPI.java                         # Main entry point
│   ├── config/
│   │   ├── ConfigManager.java               # Config management
│   │   └── KeyBindings.java                 # Hotkey management
│   ├── features/
│   │   ├── autoclicky/
│   │   │   ├── AutoClickyFeature.java       # Autoclicky logic
│   │   │   └── ClickMode.java               # Click modes
│   │   ├── aimasist/
│   │   │   └── AimAssistFeature.java        # AimAssist logic
│   │   └── esp/
│   │       └── ESPFeature.java              # ESP logic
│   ├── gui/
│   │   ├── BillAPIScreen.java               # Main settings screen
│   │   └── ModMenuIntegration.java          # Mod Menu integration
│   ├── mixin/
│   │   └── ClientPlayerInteractionMixin.java # Mixins for hooks
│   └── util/
│       └── RenderUtils.java                 # Rendering utilities
├── src/main/resources/
│   ├── fabric.mod.json                      # Mod manifest
│   └── billapi.mixins.json                  # Mixin configuration
├── build.gradle                             # Gradle configuration
├── gradle.properties                        # Dependency versions
└── README.md                                # This file

```

---

## 🛠️ Development

### Building the Project

```bash
# Clone the repository
git clone https://github.com/HeMaclo/BillAPI-.git
cd BillAPI-

# Build the mod
./gradlew build

# The built jar file will be in build/libs/
```

Requirements for Development

· Java 21+ JDK
· Gradle 8.0+
· IDE (recommended: IntelliJ IDEA or VSCode)

Code Structure

The entire code is written in pure Java 21 without using Kotlin. Each class contains:

· ✅ Clear and concise comments with a touch of humor
· ✅ Proper structure and design patterns
· ✅ JavaDoc for public methods

---

🐛 Known Issues

· AimAssist might be too smooth at low sensitivity values - adjust the sensitivity to your liking
· On servers with anti-cheat, the mod may be detected - use it carefully!

---

🤝 Contributing

Want to help with development? 🚀

1. Fork the repository
2. Create a new branch (git checkout -b feature/amazing-feature)
3. Commit your changes (git commit -m 'Add amazing feature')
4. Push to the branch (git push origin feature/amazing-feature)
5. Open a Pull Request

---

📝 License

This project is distributed under the MIT license. For more details, see the LICENSE file.

---

👨‍💻 Authors

· Zete1x_Dev - Main Developer

---

🙏 Thank You!

Thank you for using BillAPI! If you like the mod, please give it a ⭐ on GitHub!

Useful Links

· 📖 Fabric Wiki
· 🔨 Fabric API Docs
· 🎮 Minecraft Wiki
· 🐛 Report an Issue

---

Made with ❤️ for the Minecraft Community 🎮✨

```
 _______   __    ___      ___            __         _______   __     
|   _  "\ |" \  |"  |    |"  |          /""\       |   __ "\ |" \    
(. |_)  :)||  | ||  |    ||  |         /    \      (. |__) :)||  |   
|:     \/ |:  | |:  |    |:  |        /' /\  \     |:  ____/ |:  |   
(|  _  \\ |.  |  \  |___  \  |___    //  __'  \    (|  /     |.  |   
|: |_)  :)/\  |\( \_|:  \( \_|:  \  /   /  \\  \  /|__/ \    /\  |\  
(_______/(__\_|_)\_______)\_______)(___/    \___)(_______)  (__\_|_) 
