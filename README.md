# SecurityCraft - Fabric Port

A Fabric port of the popular [SecurityCraft](https://modrinth.com/mod/security-craft) mod for Minecraft 1.21.1.

## Features

### 🔐 Security Devices
| Block | Description |
|-------|-------------|
| **Keypad** | Passcode-protected block that emits redstone when the correct code is entered |
| **Security Camera** | A camera players can look through from anywhere in their world |
| **Sentry** | Defensive turret that shoots at approaching players/mobs (3 modes: Aggressive, Camouflage, Turret) |
| **Inventory Scanner** | Scans player inventories for forbidden items; can confiscate contraband |
| **Keycard Reader** | Accepts keycards of a required level and emits a redstone pulse |
| **Block Change Detector** | Logs block changes in a configurable radius with player name, time, and position |
| **Rift Stabilizer** | Prevents chorus fruit and ender pearl teleportation within its range |
| **Trophy System** | Intercepts and destroys incoming projectiles |

### 💣 Mines (15 types)
Camouflaged explosive blocks that mimic vanilla blocks. Explode when non-owners break or step on them:
- Laser Mine (glass-like tripwire)
- Stone Mine, Cobblestone Mine, Deepslate Mine
- Dirt Mine, Sand Mine, Gravel Mine, Grass Mine
- Coal, Iron, Gold, Diamond, Emerald, Lapis, Redstone Ore Mines

### 🧱 Reinforced Blocks (30 variants)
Unbreakable versions of vanilla blocks. Created with the Universal Block Reinforcer.
Cannot be destroyed by explosions or survival players. Includes:
- Stone, Cobblestone, Stone Bricks, Deepslate variants
- All 6 wood plank types + logs
- Glass, Stained Glass, Iron Bars
- Obsidian, Dirt, Gravel, Sand, Sandstone
- Bricks, Nether Bricks, Smooth Stone, Iron Block, Bookshelf, Wool

### 🌊 Fake Liquids
- **Fake Water** — Looks like water, damages players who touch it
- **Fake Lava** — Looks like lava, sets players on fire

### 🃏 Keycards & Keys
- **Keycards (Levels I-V)** — Used with Keycard Readers; higher levels open higher-security readers
- **Key** — Standard item for interacting with passcode-protected blocks

### 🔧 Tools
| Item | Description |
|------|-------------|
| **Universal Block Modifier (UBM)** | Configure any SecurityCraft block |
| **Universal Block Reinforcer I/II/III** | Convert vanilla blocks to reinforced variants |
| **Universal Key Remover** | Remove ownership from a SecurityCraft block |
| **Codebreaker** | 33% chance to bypass passcode-protected blocks (3 uses) |
| **Mine Remote Access Tool (MRAT)** | Remotely arm, defuse, or detonate bound mines |
| **Portable Radar** | Detects players within 32 blocks |

### 📦 Modules (12 types)
Right-click modules onto SecurityCraft blocks to install them:
- **Allowlist** — Listed players bypass this block's security
- **Denylist** — Specifically blocks listed players
- **Redstone** — Enables redstone output
- **Smart** — Enables intelligent targeting
- **Speed** — Increases action speed
- **Disguise** — Block looks like another block
- **Harming** — Increases damage dealt
- **Storage** — Additional storage capacity
- **Covert** — Makes block harder to detect
- **Blanking** — Suppresses chat messages
- **Schedule** — Activates on a timed schedule
- **Crowd Control** — Targets mobs in addition to players

## Ownership System
Every SecurityCraft block is owned by the player who placed it. Only the owner can:
- Break the block
- Configure it
- Access passcode-protected storage

## Installation
1. Install [Fabric Loader](https://fabricmc.net/use/installer/) for Minecraft 1.21.1
2. Install [Fabric API](https://modrinth.com/mod/fabric-api)
3. Drop `securitycraft-fabric-1.0.0.jar` into your `mods/` folder

## Building from Source
```bash
./gradlew build
```
The output jar will be in `build/libs/`.

## Requirements
- Minecraft 1.21.1
- Fabric Loader ≥ 0.15.0
- Fabric API
- Java 21

## Credits
Original SecurityCraft mod by [Geforce132](https://github.com/Geforce132/SecurityCraft) — licensed MIT.
