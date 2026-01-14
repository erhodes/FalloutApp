# Product Context: FalloutApp

## Why This Project Exists
FalloutApp was created to provide players of the Fallout tabletop RPG with a digital character management tool. Physical character sheets can be cumbersome during gameplay sessions, and tracking changes to health, inventory, and progression by hand is error-prone. This app digitizes the character sheet experience, making it faster and more reliable to manage characters during play.

## Problems It Solves

### Manual Tracking Complexity
- **Health & Armor Calculations**: The game involves armor that absorbs damage based on toughness, tracks its own damage, and breaks when exceeded. This is complex to track manually.
- **Weight Management**: Characters have separate loadout and inventory weight limits based on stats. The app automatically calculates and enforces these limits.
- **Radiation/Fatigue Relationship**: Radiation increases minimum fatigue - the app handles this automatically.

### Session Continuity
- Character data persists between sessions using KStore
- No need to remember or write down current state
- Multiple characters can be managed for different campaigns

### Multi-Platform Access
- Players can use their preferred device (Android phone, tablet, or web browser)
- Shared codebase ensures consistent experience across platforms

## How It Should Work

### User Flow
1. **Character List** → View all saved characters, create new ones, or delete existing
2. **Character Creation** → Set name, allocate SPECIAL stats, choose major/minor skills, gain initial skill bonuses
3. **Character Screen** → Main gameplay interface showing:
   - Vitals (HP, Stress, Fatigue, Radiation)
   - Stats (SPECIAL values)
   - Skills (with current values)
   - Perks (acquired abilities)
   - Loadout (equipped items)
   - Inventory (stored items)
4. **Item Acquisition** → Add items from available item templates
5. **Skill Progression** → Allocate skill bonuses when leveling up
6. **Perk Selection** → Choose new perks based on character qualifications

### Key Interactions
- Tap buttons to modify damage/heal, stress, fatigue, radiation
- Equip/unequip items between loadout and inventory
- Increase/decrease stackable item counts
- Add/remove ammo from weapons
- View and manage perks

## User Experience Goals

### Simplicity
- One-tap modifications for common gameplay actions
- Clear visual hierarchy for different character aspects
- Minimal navigation required for frequent tasks

### Reliability
- Auto-save on all changes
- No data loss between sessions
- Consistent behavior across platforms

### Game Accuracy
- All calculations match tabletop rules
- Weight limits, skill caps, and perk requirements enforced correctly
- Armor damage reduction follows game mechanics

### Responsiveness
- Fast UI updates when state changes
- Smooth scrolling through character information
- Quick navigation between screens
