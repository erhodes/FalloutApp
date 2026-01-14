# Progress: FalloutApp

## What Works

### Core Application
- ✅ Kotlin Multiplatform project structure established
- ✅ Compose Multiplatform UI framework integrated
- ✅ Navigation between screens using Navigation Compose
- ✅ Koin dependency injection configured
- ✅ Material Design 3 theming applied

### Character Management
- ✅ Character creation with SPECIAL stat allocation
- ✅ Character list display with selection
- ✅ Character deletion
- ✅ Active character state management
- ✅ Character persistence via KStore

### Stats & Skills
- ✅ SPECIAL stats (S.P.E.C.I.A.L.) - 7 stats with enum
- ✅ 12 skills with display names
- ✅ Major/minor skill selection during creation
- ✅ Skill allocation during level-up
- ✅ Skill cap based on level (`5 + level/2`)

### Vitals System
- ✅ Damage tracking with armor integration
- ✅ Healing functionality
- ✅ Stress tracking (0-5 range)
- ✅ Fatigue tracking (affected by radiation)
- ✅ Radiation tracking (sets minimum fatigue)

### Inventory System
- ✅ Item template system (WeaponTemplate, ArmorTemplate, StackableItemTemplate)
- ✅ Item instances (Weapon, Armor, StackableItem, BasicItem)
- ✅ Loadout with weight limit (strength + 4)
- ✅ Inventory with weight limit (10)
- ✅ Equip/unequip items
- ✅ Add/remove items
- ✅ Stackable item count management
- ✅ Weapon ammo tracking

### Armor System
- ✅ Armor toughness reduces damage taken
- ✅ Armor durability tracking
- ✅ Armor damage accumulation
- ✅ Armor repair functionality
- ✅ Single equipped armor slot

### Perk System
- ✅ Perk model with requirements
- ✅ StatRequirement implementation
- ✅ SkillRequirement implementation
- ✅ Perk acquisition with requirement checking
- ✅ Perk removal
- ✅ Perk selection screen

### Progression
- ✅ Level tracking
- ✅ Milestone tracking (3 milestones = 1 level)
- ✅ Skill bonuses on milestone/level-up

### Platform Support
- ✅ Android builds working
- ✅ Web (JS) builds configured
- ✅ Web (WASM) builds configured
- ✅ Desktop (JVM) builds working - foundation for server
- ⏸️ iOS builds (commented out, but structure exists)

## What's Left to Build

### Planned Features
- [ ] More item templates in ItemDataSource
- [ ] More perk definitions in PerkDataSource
- [ ] Character export/import
- [ ] Multiple campaign/save file support
- [ ] Search/filter for items and perks
- [ ] Character portrait/image support

### Technical Improvements
- [ ] Re-enable iOS builds
- [x] Desktop (JVM) target support (completed - foundation for server)
- [ ] Unit tests for Character logic
- [ ] UI tests for screens
- [ ] Error handling improvements (user feedback on failures)
- [ ] Loading/saving indicators
- [ ] Offline-first data sync (if cloud sync added)

### UI Enhancements
- [ ] Dark/light theme toggle
- [ ] Custom Fallout-themed styling
- [ ] Animations for state changes
- [ ] Tablet/desktop adaptive layouts
- [ ] Accessibility improvements

## Current Status
**Phase**: Core functionality complete, ready for content expansion

The application has all core character sheet functionality implemented:
- Characters can be created, edited, and deleted
- All SPECIAL stats and skills are tracked
- Vitals (health, stress, fatigue, radiation) work correctly
- Inventory and loadout systems are functional
- Perks can be acquired and managed

The main gap is **content** - the app needs more item templates and perk definitions to be fully usable for actual gameplay.

## Known Issues

### Bugs
- None currently documented

### Technical Debt
- `aserialtest/` folder contains experimental code (Car, Ship, Vehicle) that may be cleanup candidates
- iOS targets commented out - may need attention if iOS deployment is needed
- Some TODO comments in CharacterViewModel regarding DI improvements

### Potential Improvements
- CharacterRepository saves on every change - could batch saves for performance
- Item/Perk templates hardcoded - could load from JSON files
- No validation feedback to user when operations fail silently

## Evolution of Project Decisions

### Initial Setup
- Started as Kotlin Multiplatform Compose project
- Chose Koin for DI due to multiplatform support
- Adopted KStore for cross-platform persistence

### Architecture Choices
- MVVM pattern with StateFlow for reactivity
- Repository pattern to abstract data layer
- Template pattern for items (template → instance)

### Current State
- Stable architecture suitable for feature expansion
- Clean separation of concerns
- Ready for content population
