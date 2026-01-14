# Project Brief: FalloutApp

## Overview
FalloutApp is a Kotlin Multiplatform (KMP) character management application for Fallout tabletop role-playing games. It allows players to create, manage, and track their characters during gameplay sessions.

## Core Requirements

### Character Management
- Create new characters with SPECIAL stats (Strength, Perception, Endurance, Charisma, Intelligence, Agility, Luck)
- Track 12 skills: Athletics, Engineering, Guns, Medicine, Melee, Sabotage, Science, Sneak, Social Sciences, Speech, Survival, Throwing
- Manage character progression through levels and milestones
- Support for perks with stat/skill requirements

### Vitals Tracking
- Health/Damage tracking with armor damage reduction
- Stress tracking (max 5)
- Fatigue tracking (linked to radiation)
- Radiation tracking (affects minimum fatigue)

### Inventory System
- Loadout items (equipped for quick access, weight-limited by strength)
- Inventory items (stored, separate weight limit)
- Item types: Basic items, Stackable items, Weapons (with ammo), Armor (with durability and toughness)
- Equip/unequip functionality

### Perk System
- Characters can acquire perks based on requirements
- Requirements can be stat-based or skill-based

## Target Platforms
- **Android** (primary)
- **iOS** (via Compose Multiplatform)
- **Web** (JS and WASM targets)

## Project Goals
1. Provide a comprehensive digital character sheet for Fallout TTRPG
2. Persist character data across sessions
3. Support multiple platforms with shared codebase
4. Clean, intuitive UI following Material Design 3

## Success Criteria
- Users can create and manage multiple characters
- All character data persists between app sessions
- App runs on Android, iOS, and web browsers
- Smooth user experience for gameplay tracking
