# System Patterns: FalloutApp

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                         UI Layer                            │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────────────────┐│
│  │ Screens     │ │ Components  │ │ Navigation (NavHost)    ││
│  │ (Composable)│ │ (Composable)│ │                         ││
│  └──────┬──────┘ └──────┬──────┘ └────────────┬────────────┘│
└─────────┼───────────────┼──────────────────────┼────────────┘
          │               │                      │
          ▼               ▼                      ▼
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│  ┌─────────────────────┐  ┌─────────────────────────────┐   │
│  │ ViewModels          │  │ UiState classes             │   │
│  │ (StateFlow-based)   │  │ (CharacterUiState, etc.)    │   │
│  └──────────┬──────────┘  └─────────────────────────────┘   │
└─────────────┼───────────────────────────────────────────────┘
              │
              ▼
┌─────────────────────────────────────────────────────────────┐
│                    Repository Layer                         │
│  ┌────────────────────┐ ┌────────────────┐ ┌──────────────┐ │
│  │ CharacterRepository│ │ ItemRepository │ │ PerkRepository││
│  └─────────┬──────────┘ └───────┬────────┘ └───────┬──────┘ │
└────────────┼────────────────────┼──────────────────┼────────┘
             │                    │                  │
             ▼                    ▼                  ▼
┌─────────────────────────────────────────────────────────────┐
│                      Data Layer                             │
│  ┌────────────────────┐ ┌────────────────┐ ┌──────────────┐ │
│  │ CharacterDataSource│ │ ItemDataSource │ │ PerkDataSource││
│  │ (KStore)           │ │ (In-memory)    │ │ (In-memory)   ││
│  └────────────────────┘ └────────────────┘ └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## Key Technical Decisions

### Kotlin Multiplatform (KMP)
- **commonMain**: All business logic, models, repositories, ViewModels
- **androidMain**: Android-specific implementations (Storage, Logger)
- **iosMain**: iOS-specific implementations
- **webMain**: Web-specific implementations (JS/WASM)
- Enables maximum code reuse across platforms

### Compose Multiplatform
- Declarative UI framework shared across all platforms
- Material Design 3 components
- Navigation Compose for screen routing
- Compose Resources for strings and drawables

### Dependency Injection (Koin)
- Single `appModule` provides all repositories as singletons
- ViewModels use `KoinComponent` interface to inject dependencies
- Platform-agnostic DI setup in commonMain

### State Management
- **StateFlow**: Used for reactive UI state updates
- **MutableStateFlow**: Internal state in ViewModels
- **mutableStateListOf**: Compose-observable list for character collection
- Changes propagate automatically to UI

## Design Patterns

### MVVM (Model-View-ViewModel)
- **Model**: Character, Item, Perk, etc. in `model/` package
- **View**: Composable screens in `ui/` package
- **ViewModel**: CharacterViewModel, ItemViewModel in `presentation/` package

### Repository Pattern
- Repositories abstract data operations from ViewModels
- Single source of truth for domain objects
- Handle persistence through DataSources

### Template Pattern (Items)
- `ItemTemplate` defines item types (templates/blueprints)
- `Item` represents actual instances owned by characters
- Templates: `WeaponTemplate`, `ArmorTemplate`, `StackableItemTemplate`
- Instances: `Weapon`, `Armor`, `StackableItem`, `BasicItem`

### Sealed Hierarchy (Requirements)
- `Requirement` interface for perk prerequisites
- Implementations: `StatRequirement`, `SkillRequirement`
- Extensible for future requirement types

## Component Relationships

### Character ↔ Items
- Character has `loadout: ArrayList<Item>` for equipped items
- Character has `inventory: ArrayList<Item>` for stored items
- Character has `equippedArmor: Armor?` for worn armor
- Weight limits: `loadoutLimit = strength + 4`, `inventoryLimit = 10`

### Character ↔ Perks
- Character has `perks: HashSet<Perk>`
- Perks have `requirements: List<Requirement>`
- Character can only gain perks if `qualifiesForPerk()` returns true

### Character ↔ Skills
- Skills stored as `ArrayList<Int>` (12 values)
- Max skill value depends on level: `5 + level/2`
- Skills enum provides display names

## Critical Implementation Paths

### Character Creation Flow
1. `CharacterCreationViewModel.startNewCreation()` → init state
2. User allocates SPECIAL stats (total 12 points)
3. User selects major/minor skills
4. User enters name → `addCharacter()` creates Character
5. Navigate to BonusSkillsScreen for initial skill allocation
6. `CharacterRepository.add()` persists character

### Damage Processing
1. `Character.takeDamage(amount)` called
2. If armor equipped: armor absorbs up to toughness value
3. Excess damage goes to armor's durability
4. If armor durability exceeded: remaining damage to character
5. Character's `damageTaken` increases

### Item Management
1. Items acquired via `AcquireItemScreen` → template selected
2. `CharacterRepository.addNewItemToCharacter()` creates instance from template
3. Item goes to inventory
4. `equipItem()` moves to loadout (if weight allows)
5. `unequipItem()` returns to inventory

### Persistence
1. Any mutation in CharacterRepository calls `saveCharacters()`
2. `CharacterDataSource.saveCharacters()` serializes to KStore
3. On app start: `CharacterDataSource.loadCharacters()` restores state
4. Uses kotlinx.serialization for JSON encoding

## Navigation Structure

```
CharacterList ──┬──> CharacterCreation ──> BonusSkillsScreen ──> CharacterList
                │
                └──> CharacterScreen ──┬──> AddItemScreen ──> CharacterScreen
                                       │
                                       ├──> BonusSkillsScreen ──> CharacterScreen
                                       │
                                       └──> PerkSelectScreen ──> CharacterScreen
```
